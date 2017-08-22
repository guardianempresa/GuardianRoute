package com.controlderuta.guardianroute.Model;

/**
 * Created by eduin on 22/08/2017.
 */

public class UserList {

    private String id;
    private String code;
    private String name;
    private String lastname;
    private Double latitud;
    private Double longitud;
    private String phone;

    public UserList() {
    }

    public UserList(String id, String code, String name, String lastname, Double latitud, Double longitud, String phone) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.lastname = lastname;
        this.latitud = latitud;
        this.longitud = longitud;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
}
