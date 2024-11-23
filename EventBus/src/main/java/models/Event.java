package models;

import java.util.Map;

public class Event {
    private final EventID id;
    private final String name;
    private final Map<String, String> attributes;
    private final Timestamp timestamp;

    public Event(EventID id, String name, Map<String, String> attributes, Timestamp timestamp) {
        this.id = id;
        this.name = name;
        this.attributes = attributes;
        this.timestamp = timestamp;
    }

    public EventID getId() {
        return id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                '}';
    }
}
