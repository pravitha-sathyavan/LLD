package models;

import java.util.function.Function;

public class Subscription {
    private final Topic topicId;
    private final EntityID subscriberId;
    private final SubscriptionType type;
    private final Function<Event, Boolean> precondition;
    private final Function<Event, Void> handler;

    public Subscription(final Topic topicId,
                        final EntityID subscriberId,
                        final SubscriptionType type,
                        final Function<Event, Boolean> precondition,
                        final Function<Event, Void> handler) {
        this.topicId = topicId;
        this.subscriberId = subscriberId;
        this.type = type;
        this.precondition = precondition;
        this.handler = handler;
    }

    public Function<Event, Void> handler() {
        return handler;
    }

    public SubscriptionType getType() {
        return type;
    }

    public Topic getTopicId() {
        return topicId;
    }

    public EntityID getSubscriberId() {
        return subscriberId;
    }
}
