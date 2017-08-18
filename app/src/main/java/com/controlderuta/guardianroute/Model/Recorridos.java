package com.controlderuta.guardianroute.Model;

/**
 * Created by diego on 18/08/2017.
 */

public class Recorridos {

    private double  latitud;
    private double  longitud;
    private double  latitudllegada;
    private double  longitudllegada;
    private int estado;
    private String alerta;
    private String nombre;
    private int alertDist;
    private float acumDist;
    private int time;
    private int tipRoute;

    public Recorridos() {
    }

    public Recorridos(double latitud, double longitud, double latitudllegada, double longitudllegada, int estado, String alerta, String nombre, int alertDist, float acumDist, int time, int tipRoute) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.latitudllegada = latitudllegada;
        this.longitudllegada = longitudllegada;
        this.estado = estado;
        this.alerta = alerta;
        this.nombre = nombre;
        this.alertDist = alertDist;
        this.acumDist = acumDist;
        this.time = time;
        this.tipRoute = tipRoute;
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

    public double getLatitudllegada() {
        return latitudllegada;
    }

    public void setLatitudllegada(double latitudllegada) {
        this.latitudllegada = latitudllegada;
    }

    public double getLongitudllegada() {
        return longitudllegada;
    }

    public void setLongitudllegada(double longitudllegada) {
        this.longitudllegada = longitudllegada;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getAlerta() {
        return alerta;
    }

    public void setAlerta(String alerta) {
        this.alerta = alerta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public int getTipRoute() {
        return tipRoute;
    }

    public void setTipRoute(int tipRoute) {
        this.tipRoute = tipRoute;
    }
}
