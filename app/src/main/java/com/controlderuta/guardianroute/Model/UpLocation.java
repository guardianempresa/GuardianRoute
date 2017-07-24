package com.controlderuta.guardianroute.Model;

public class UpLocation {
    private String id;
    private double latitudup;
    private double longitudup;
    private String nombreruta;
    private String conductor;
    private String matricula;
    private String contac1;



    public UpLocation() {
    }

    public UpLocation(String id, double latitudup, double longitudup, String nombreruta, String conductor, String matricula, String contac1) {
        this.id = id;
        this.latitudup = latitudup;
        this.longitudup = longitudup;
        this.nombreruta = nombreruta;
        this.conductor = conductor;
        this.matricula = matricula;
        this.contac1 = contac1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLatitudup() {
        return latitudup;
    }

    public void setLatitudup(double latitudup) {
        this.latitudup = latitudup;
    }

    public double getLongitudup() {
        return longitudup;
    }

    public String getNombre() {
        return nombreruta;
    }

    public void setNombre(String nombre) {
        this.nombreruta = nombre;
    }

    public String getApellido() {
        return conductor;
    }

    public void setApellido(String apellido) {
        this.conductor = apellido;
    }

    public String getContac1() {
        return contac1;
    }

    public void setContac1(String contac1) {
        this.contac1 = contac1;
    }

    public String getContac2() {
        return contac1;
    }

    public void setContac2(String contac2) {
        this.contac1 = contac2;
    }




    public void setLongitudup(double longitudup) {
        this.longitudup = longitudup;
    }
}