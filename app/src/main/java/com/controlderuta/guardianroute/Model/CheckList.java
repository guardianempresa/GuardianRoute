package com.controlderuta.guardianroute.Model;

/**
 * Created by eduin on 26/08/2017.
 */

public class CheckList {


    private float distance;
    private String name;
    private String lastname;
    private String icon;
    private String id;

    public CheckList() {
    }

    public CheckList(float distance, String name, String lastname, String icon, String id) {
        this.distance = distance;
        this.name = name;
        this.lastname = lastname;
        this.icon = icon;
        this.id = id;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
