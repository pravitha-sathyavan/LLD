import exception.RetryLimitExceededException;
import models.*;
import retry.RetryAlgorithm;
import utils.KeyedExecutor;
import utils.Timer;

import java.util.*;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventBus {

    private final KeyedExecutor executor;
    /*   Manages the execution of tasks in a thread-safe and asynchronous manner.
         Ensures sequential processing for tasks associated with the same key (e.g., a specific topic or subscription) */

    private final Map<Topic, List<Event>> buses;
    /*   Purpose: Stores all events for each topic.
         Key: Topic (represents the name or category of events).
         Value: List<Event> (the list of events published to this topic). */

    private final Map<Topic, Set<Subscription>> subscriptions;
    /*   Manages the subscriptions for each topic.
         Key: Topic (the topic of interest).
         Value: Set<Subscription> (all subscribers interested in this topic).
         Usage:  Determines which subscribers should receive events when they are published. */

    private final Map<Topic, Map<EntityID, Index>> subscriberIndexes;
    /*   Tracks the last processed event for each subscriber in a topic.
         Key: Topic (the topic to which the subscriber belongs).
         Value: Map<EntityID, Index>:
               Key: EntityID (the unique identifier of the subscriber).
               Value: Index (the position of the last event processed by this subscriber).
         Allows polling subscribers to resume from where they left off.
         Ensures sequential delivery of events to each subscriber.*/

    private final Map<Topic, Map<EventID, Index>> eventIndex;
    /*    Provides an index to quickly locate events by their ID.
          Key: Topic (the topic where the event was published).
          Value: Map<EventID, Index>:
                 Key: EventID (the unique identifier of the event).
                 Value: Index (the position of the event in the event list for the topic */

    private final Map<Topic, ConcurrentSkipListMap<Timestamp, Index>> timestampIndex;
    /*    Indexes events based on their timestamps for fast access.
          Key: Topic (the topic where the event was published).
          Value: ConcurrentSkipListMap<Timestamp, Index>:
                Key: Timestamp (the timestamp of the event).
                Value: Index (the position of the event in the topic's event list).
          Enables functionality like resetting a subscriber's position after a specific timestamp (setIndexAfterTimestamp).
          Useful for time-based queries or batch processing. */

    private final RetryAlgorithm<Event, Void> retryAlgorithm;
    /*.   Handles retries for event delivery in case of transient failures.
          Applied in the push method to retry delivery to push-based subscribers.
          Helps ensure reliability by attempting multiple retries before failing. */

    private final EventBus deadLetterQueue;
    /*    If an event cannot be successfully delivered after retries, it is sent to the deadLetterQueue for further handling.*/

    private final Timer timer;

    public EventBus(final int threads,
                    final RetryAlgorithm<Event, Void> retryAlgorithm,
                    final EventBus deadLetterQueue,
                    final Timer timer) {
        this.retryAlgorithm = retryAlgorithm;
        this.deadLetterQueue = deadLetterQueue;
        this.timer = timer;
        this.buses = new ConcurrentHashMap<>();
        this.executor = new KeyedExecutor(threads);
        this.subscriptions = new ConcurrentHashMap<>();
        this.subscriberIndexes = new ConcurrentHashMap<>();
        this.timestampIndex = new ConcurrentHashMap<>();
        this.eventIndex = new ConcurrentHashMap<>();
    }

    public CompletionStage<Void> publish(Topic topic, Event event) {
        return executor.submit(topic.getName(), () -> addEventToBus(topic, event));
    }
    /*
    Input:
    Topic topic = new Topic("News");
    Event event = new Event("Breaking News");
    eventBus.publish(topic, event);

    Execution:
    The publish method submits a task to executor.
    The task calls addEventToBus with the given topic and event.
    The event is added to the event bus, and any push-based subscribers are notified.

    Result:
    A CompletionStage<Void> is returned to the caller, which completes when the task finishes.
    */

    private void addEventToBus(Topic topic, Event event) {
        final Index currentIndex = new Index(buses.get(topic).size());
        timestampIndex.get(topic).put(event.getTimestamp(), currentIndex);
        eventIndex.get(topic).put(event.getId(), currentIndex);
        buses.get(topic).add(event);
        subscriptions.getOrDefault(topic, Collections.newSetFromMap(new ConcurrentHashMap<>()))
                .stream()
                .filter(subscription -> SubscriptionType.PUSH.equals(subscription.getType()))
                .forEach(subscription -> push(event, subscription));
    }
    /*
    Add Event: Adds a new event to the specified topic's event list.
    Indexing: Updates multiple indices (by timestamp and event ID) to enable efficient lookup.
    Push Notifications: Notifies all push-based subscribers for the topic when a new event is added.
    */

    public CompletionStage<Event> poll(Topic topic, EntityID subscriber) {
        return executor.get(topic.getName() + subscriber.getId(), () -> {
            final Index index = subscriberIndexes.get(topic).get(subscriber);
            try {
                final Event event = buses.get(topic).get(index.getVal());
                subscriberIndexes.get(topic).put(subscriber, index.increment());
                return event;
            } catch (IndexOutOfBoundsException exception) {
                return null;
            }
        });
    }
    /*
    Retrieves the next unread event for the given subscriber from the specified topic.
    Updates the subscriber's index to point to the next event, indicating progress in event consumption.
    Returns null if there are no more events for the subscriber.
    */


    private void push(Event event, Subscription subscription) {
        executor.submit(subscription.getTopicId().getName() + subscription.getSubscriberId(),
                () -> {
                    try {
                        retryAlgorithm.attempt(subscription.handler(), event, 0);
                    } catch (RetryLimitExceededException e) {
                        if (deadLetterQueue != null) {
                            deadLetterQueue.publish(subscription.getTopicId(),
                                    new FailureEvent(event, e, timer.getTime()));
                        } else {
                            e.printStackTrace();
                        }
                    }
                });
    }
    /*
    The executor.submit ensures that the task is executed asynchronously in a thread-safe manner.
    A unique key (topic name + subscriber ID) is used to guarantee sequential execution for this specific subscription.
    Notifies a push-based subscriber for the topic when a new event is added.
     */


    public void registerTopic(Topic topic) {
        buses.put(topic, new CopyOnWriteArrayList<>());
        subscriptions.put(topic, Collections.newSetFromMap(new ConcurrentHashMap<>()));
        subscriberIndexes.put(topic, new ConcurrentHashMap<>());
        timestampIndex.put(topic, new ConcurrentSkipListMap<>());
        eventIndex.put(topic, new ConcurrentHashMap<>());
    }
    /*
    This method registers a new topic in the EventBus.
    It sets up and initializes all the data structures required to
    */

    public CompletionStage<Void> subscribe(final Subscription subscription) {
        return executor.submit(subscription.getTopicId().getName(), () -> {
            final Topic topicId = subscription.getTopicId();
            subscriptions.get(topicId).add(subscription);
            subscriberIndexes.get(topicId).put(subscription.getSubscriberId(),
                    new Index(buses.get(topicId).size()));
        });
    }
    /*
    Adds a Subscription to a topic, enabling the subscriber to receive events.
    Associates the subscriber with an initial index in the event list of the topic.
    The operation is asynchronous and executed via the KeyedExecutor.
    */

    public CompletionStage<Void> setIndexAfterTimestamp(Topic topic, EntityID subscriber, Timestamp timestamp) {
        return executor.submit(topic.getName() + subscriber.getId(), () -> {
            final Map.Entry<Timestamp, Index> entry = timestampIndex.get(topic).higherEntry(timestamp);
            if (entry == null) {
                subscriberIndexes.get(topic).put(subscriber, new Index(buses.get(topic).size()));
            } else {
                final Index indexLessThanEquals = entry.getValue();
                subscriberIndexes.get(topic).put(subscriber, indexLessThanEquals);
            }
        });
    }
    /*
    To allow a subscriber to start consuming events from a specific timestamp onward.
    If there are no events after the given timestamp, the subscriber's index is updated to the end of the current event list.
    The method uses KeyedExecutor to ensure that operations involving the same topic and subscriber are executed sequentially.
    If an event with a timestamp greater than the given timestamp is found, the subscriber's index is set to the corresponding Index of that event.
    */

    public CompletionStage<Void> setIndexAfterEvent(Topic topic, EntityID subscriber, EventID eventId) {
        return executor.submit(topic.getName() + subscriber.getId(), () -> {
            final Index eIndex = eventIndex.get(topic).get(eventId);
            subscriberIndexes.get(topic).put(subscriber, eIndex.increment());
        });
    }
    /*
    To set a subscriber's index to start consuming events from the event immediately after the specified EventID in the given topic.
    */

    public CompletionStage<Event> getEvent(Topic topic, EventID eventId) {
        return executor.get(topic.getName(), () -> {
            Index index = eventIndex.get(topic).get(eventId);
            return buses.get(topic).get(index.getVal());
        });
    }
    /*
    The getEvent method is designed to retrieve a specific event from the event bus for a given topic, using the event's ID (eventId).
    The method returns a CompletionStage<Event>, indicating that the retrieval is asynchronous.
     */
}


