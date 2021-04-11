package org.openjfx.ledicom.entities.inspection;

public class CheckupType {
    private int id;
    private String typeName;

    public CheckupType(int id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }

    public CheckupType() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
