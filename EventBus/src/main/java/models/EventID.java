package models;

public class EventID {
    private final String id;

    public EventID(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }
}
