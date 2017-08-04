package com.controlderuta.guardianroute.Model;

/**
 * Created by eduin on 3/08/2017.
 */

public class RouteData {


    private String id;
    private String codeRoute;
    private String nameRoute;
    private double longitud;
    private double latitud;
    private int tipRoute;
    private int estado;
    private int tipNotification;
    private int alertDist;
    private float acumDist;
    private int time;

    public RouteData() {
    }

    public RouteData(String id, String codeRoute, String nameRoute, double longitud, double latitud, int tipRoute, int estado, int tipNotification, int alertDist, float acumDist, int time) {
        this.id = id;
        this.codeRoute = codeRoute;
        this.nameRoute = nameRoute;
        this.longitud = longitud;
        this.latitud = latitud;
        this.tipRoute = tipRoute;
        this.estado = estado;
        this.tipNotification = tipNotification;
        this.alertDist = alertDist;
        this.acumDist = acumDist;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodeRoute() {
        return codeRoute;
    }

    public void setCodeRoute(String codeRoute) {
        this.codeRoute = codeRoute;
    }

    public String getNameRoute() {
        return nameRoute;
    }

    public void setNameRoute(String nameRoute) {
        this.nameRoute = nameRoute;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public int getTipRoute() {
        return tipRoute;
    }

    public void setTipRoute(int tipRoute) {
        this.tipRoute = tipRoute;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getTipNotification() {
        return tipNotification;
    }

    public void setTipNotification(int tipNotification) {
        this.tipNotification = tipNotification;
    }

    public int getAlertDist() {
        return alertDist;
    }

    public void setAlertDist(int alertDist) {
        this.alertDist = alertDist;
    }

    public float getAcumDist() {
        return acumDist;
    }

    public void setAcumDist(float acumDist) {
        this.acumDist = acumDist;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
