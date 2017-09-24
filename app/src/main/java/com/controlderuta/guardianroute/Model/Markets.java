package com.controlderuta.guardianroute.Model;

import java.lang.ref.SoftReference;

/**
 * Created by eduin on 24/08/2017.
 */

public class Markets {


    private String code;
    private String icon;
    private String iconface;
    private double latitud;
    private double longitud;
    private String id;
    private String childlastname;
    private String childname;
    private double distance;
    private String name;
    private String lastname;
    private String check;
    private String messageuser;
    private String phone;

    public Markets() {
    }

    public Markets(String code, String icon, String iconface, double latitud, double longitud, String id, String childlastname, String childname, double distance, String name, String lastname, String check, String messageuser, String phone) {
        this.code = code;
        this.icon = icon;
        this.iconface = iconface;
        this.latitud = latitud;
        this.longitud = longitud;
        this.id = id;
        this.childlastname = childlastname;
        this.childname = childname;
        this.distance = distance;
        this.name = name;
        this.lastname = lastname;
        this.check = check;
        this.messageuser = messageuser;
        this.phone = phone;
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

    public String getIconface() {
        return iconface;
    }

    public void setIconface(String iconface) {
        this.iconface = iconface;
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

    public String getChildlastname() {
        return childlastname;
    }

    public void setChildlastname(String childlastname) {
        this.childlastname = childlastname;
    }

    public String getChildname() {
        return childname;
    }

    public void setChildname(String childname) {
        this.childname = childname;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
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

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getMessageuser() {
        return messageuser;
    }

    public void setMessageuser(String messageuser) {
        this.messageuser = messageuser;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}