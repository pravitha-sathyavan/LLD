import models.*;
import org.junit.Assert;
import org.junit.Test;
import retry.PeriodicRetry;
import utils.Timer;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class EventBusTest {

    public static final Topic TOPIC_1 = new Topic("topic-1");

    @Test
    public void defaultBehavior() throws ExecutionException, InterruptedException {
        final Timer timer = new Timer();
        EventBus eventBus = new EventBus(1,
                new PeriodicRetry<>(5, 1000), null, timer);
        final EventID eventId1 = new EventID("event-id-1");
        eventBus.registerTopic(TOPIC_1);
        eventBus.publish(TOPIC_1, getEvent(timer, eventId1));
        final EntityID subscriberId = new EntityID("subscriber-1");
        eventBus.subscribe(new Subscription(TOPIC_1, subscriberId, SubscriptionType.PULL,
                (event) -> true, (event) -> null));
        final Event event = eventBus.poll(TOPIC_1, subscriberId).toCompletableFuture().get();
        Assert.assertNull(event);


        final EventID eventId2 = new EventID("event-id-2");
        eventBus.publish(TOPIC_1, getEvent(timer, eventId2));
        final Event event2 = eventBus.poll(TOPIC_1, subscriberId).toCompletableFuture().get();
        Assert.assertNotNull(event2);
        Assert.assertEquals(event2.getId(), eventId2);
    }

    @Test
    public void setIndexAfter() throws ExecutionException, InterruptedException {
        final Timer timer = new Timer();
        EventBus eventBus = new EventBus(1,
                new PeriodicRetry<>(5, 1000), null, timer);
        final EventID eventId1 = new EventID("event-id-1"),
                eventId2 = new EventID("event-id-2"),
                eventId3 = new EventID("event-id-3"),
                eventId4 = new EventID("event-id-4");
        eventBus.registerTopic(TOPIC_1);
        final Event event1 = getEvent(timer, eventId1);
        eventBus.publish(TOPIC_1, event1);
        eventBus.publish(TOPIC_1, getEvent(timer, eventId2));
        eventBus.publish(TOPIC_1, getEvent(timer, eventId3));
        eventBus.publish(TOPIC_1, getEvent(timer, eventId4));
        final EntityID subscriberId = new EntityID("subscriber-1");
        eventBus.subscribe(new Subscription(TOPIC_1, subscriberId, SubscriptionType.PULL,
                (event) -> true, (event) -> null));
        final Event event = eventBus.poll(TOPIC_1, subscriberId).toCompletableFuture().get();
        Assert.assertNull(event);
        eventBus.setIndexAfterEvent(TOPIC_1, subscriberId, eventId2);

        Event resetEventPolled = eventBus.poll(TOPIC_1, subscriberId).toCompletableFuture().get();
        Assert.assertNotNull(resetEventPolled);
        Assert.assertEquals(eventId3, resetEventPolled.getId());
        resetEventPolled = eventBus.poll(TOPIC_1, subscriberId).toCompletableFuture().get();
        Assert.assertNotNull(resetEventPolled);
        Assert.assertEquals(eventId4, resetEventPolled.getId());
        resetEventPolled = eventBus.poll(TOPIC_1, subscriberId).toCompletableFuture().get();
        Assert.assertNull(resetEventPolled);

        eventBus.setIndexAfterTimestamp(TOPIC_1, subscriberId, event1.getTimestamp());

        resetEventPolled = eventBus.poll(TOPIC_1, subscriberId).toCompletableFuture().get();
        Assert.assertNotNull(resetEventPolled);
        Assert.assertEquals(eventId2, resetEventPolled.getId());
        resetEventPolled = eventBus.poll(TOPIC_1, subscriberId).toCompletableFuture().get();
        Assert.assertNotNull(resetEventPolled);
        Assert.assertEquals(eventId3, resetEventPolled.getId());
        resetEventPolled = eventBus.poll(TOPIC_1, subscriberId).toCompletableFuture().get();
        Assert.assertNotNull(resetEventPolled);
        Assert.assertEquals(eventId4, resetEventPolled.getId());
        resetEventPolled = eventBus.poll(TOPIC_1, subscriberId).toCompletableFuture().get();
        Assert.assertNull(resetEventPolled);
    }

    private Event getEvent(Timer timer, EventID eventId1) {
        return new Event(eventId1, "event-name-1", new HashMap<>(), timer.getTime());
    }
}
