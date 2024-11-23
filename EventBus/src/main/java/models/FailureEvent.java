package models;

import java.util.UUID;

public class FailureEvent extends Event {
    private final Throwable error;
    private final Timestamp timestamp;

    public FailureEvent(Event event, Throwable error, Timestamp timestamp) {
        super(new EventID(UUID.randomUUID().toString()), event.getName(), event.getAttributes(), event.getTimestamp());
        this.error = error;
        this.timestamp = timestamp;
    }
}
