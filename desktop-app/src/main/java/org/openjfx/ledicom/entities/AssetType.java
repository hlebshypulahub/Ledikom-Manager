package org.openjfx.ledicom.entities;

public class AssetType {
    private String type;
    private int Id;

    public AssetType() {
    }

    public AssetType(String type, int id) {
        this.type = type;
        Id = id;
    }

    @Override
    public String toString() {
        return Id + " - " + type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
