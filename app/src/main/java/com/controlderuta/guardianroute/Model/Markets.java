package com.controlderuta.guardianroute.Model;

import java.lang.ref.SoftReference;

/**
 * Created by eduin on 24/08/2017.
 */

public class Markets {

    private String code;
    private String icon;
    private double latitud;
    private double longitud;
    private String id;
    private String name;
    private String lastname;
    private String check;

    public Markets() {
    }

    public Markets(String code, String icon, double latitud, double longitud, String id, String name, String lastname, String check) {
        this.code = code;
        this.icon = icon;
        this.latitud = latitud;
        this.longitud = longitud;
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.check = check;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }
}