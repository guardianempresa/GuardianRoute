package com.controlderuta.guardianroute.Model;

/**
 * Created by eduin on 14/07/2017.
 */

public class User {

    private String code;
    private String nombre;
    private String apellido;
    private String contacto1;
    private String contacto2;

    public User() {
    }

    public User(String code, String nombre, String apellido, String contacto1, String contacto2) {
        this.code = code;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contacto1 = contacto1;
        this.contacto2 = contacto2;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getContacto1() {
        return contacto1;
    }

    public void setContacto1(String contacto1) {
        this.contacto1 = contacto1;
    }

    public String getContacto2() {
        return contacto2;
    }

    public void setContacto2(String contacto2) {
        this.contacto2 = contacto2;
    }
}
