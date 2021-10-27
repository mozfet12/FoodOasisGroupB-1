package com.groupB.foodoasis.Classes;

public class NearLocationDetailsModelClass {
    String name;
    String icon;
    String place_id;
    double latitude;
    double longitude;

    public NearLocationDetailsModelClass() {
    }

    public NearLocationDetailsModelClass(String name, String icon, String place_id, double latitude, double longitude) {
        this.name = name;
        this.icon = icon;
        this.place_id = place_id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
