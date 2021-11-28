package com.groupB.foodoasis.Classes;

public class NearLocationDetailsModelClass {
    String name;
    String icon;
    String place_id;
    double latitude;
    double longitude;
    int is_favourite = 0;

    public NearLocationDetailsModelClass() {
    }

    public NearLocationDetailsModelClass(String name, String icon, String place_id, double latitude, double longitude, int is_favourite) {
        this.name = name;
        this.icon = icon;
        this.place_id = place_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.is_favourite = is_favourite;
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

    public int getIs_favourite() {
        return is_favourite;
    }

    public void setIs_favourite(int is_favourite) {
        this.is_favourite = is_favourite;
    }
}
