package org.openjfx.ledicom.entities;

public class Asset {
    private int Id;
    private Facility facility;
    private AssetType assetType;
    private String name;
    private String number;

    public Asset() {
    }

    public Asset(Facility facility, AssetType assetType, String name) {
        this.facility = facility;
        this.assetType = assetType;
        this.name = name;
    }

    public Asset(int id, String name, String number) {
        Id = id;
        this.name = name;
        this.number = number;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public AssetType getAssetType() {
        return assetType;
    }

    public void setAssetType(AssetType assetType) {
        this.assetType = assetType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
