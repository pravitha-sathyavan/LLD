package models;

public class Entity {
    private final EntityID entityID;
    private final String name;
    private final String ipAddress;

    public Entity(EntityID entityID, String name, String ipAddress) {
        this.entityID = entityID;
        this.name = name;
        this.ipAddress = ipAddress;
    }
}
