package com.controlderuta.guardianroute.Model;

/**
 * Created by eduin on 20/08/2017.
 */

public class DataListRoute {

    private String id;
    private String name;
    private String code;
    private String lastname;
    private Double latitud;
    private Double longitud;
    private String phone;
    private String messageuser;

    public DataListRoute() {
    }

    public DataListRoute(String id, String name, String code, String lastname, Double latitud, Double longitud, String phone, String messageuser) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.lastname = lastname;
        this.latitud = latitud;
        this.longitud = longitud;
        this.phone = phone;
        this.messageuser = messageuser;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessageuser() {
        return messageuser;
    }

    public void setMessageuser(String messageuser) {
        this.messageuser = messageuser;
    }
}