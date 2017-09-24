package com.controlderuta.guardianroute.Model;

/**
 * Created by eduin on 9/09/2017.
 */

public class DriverVsTravel {

    private String id;
    private String name;

    public DriverVsTravel() {
    }

    public DriverVsTravel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
