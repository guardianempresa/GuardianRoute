package com.controlderuta.guardianroute.Model;

/**
 * Created by diego on 19/08/2017.
 */

public class DataUsuarios {
    private String nameuser;
    private String lastnameuser;
    private String mobileuser1;
    private String getMobileuser2;
    private double latitud;
    private double longitud;

    public DataUsuarios() {
    }

    public DataUsuarios(String nameuser, String lastnameuser, String mobileuser1, String getMobileuser2, double latitud, double longitud) {
        this.nameuser = nameuser;
        this.lastnameuser = lastnameuser;
        this.mobileuser1 = mobileuser1;
        this.getMobileuser2 = getMobileuser2;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getNameuser() {
        return nameuser;
    }

    public void setNameuser(String nameuser) {
        this.nameuser = nameuser;
    }

    public String getLastnameuser() {
        return lastnameuser;
    }

    public void setLastnameuser(String lastnameuser) {
        this.lastnameuser = lastnameuser;
    }

    public String getMobileuser1() {
        return mobileuser1;
    }

    public void setMobileuser1(String mobileuser1) {
        this.mobileuser1 = mobileuser1;
    }

    public String getGetMobileuser2() {
        return getMobileuser2;
    }

    public void setGetMobileuser2(String getMobileuser2) {
        this.getMobileuser2 = getMobileuser2;
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
}
