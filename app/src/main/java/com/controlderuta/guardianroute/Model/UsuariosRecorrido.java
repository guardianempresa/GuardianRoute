package com.controlderuta.guardianroute.Model;

/**
 * Created by eduin on 21/08/2017.
 */

public class UsuariosRecorrido {
    private String tokenusuarios;

    public UsuariosRecorrido() {
    }

    public UsuariosRecorrido(String tokenusuarios) {
        this.tokenusuarios = tokenusuarios;
    }

    public String getTokenusuarios() {
        return tokenusuarios;
    }

    public void setTokenusuarios(String tokenusuarios) {
        this.tokenusuarios = tokenusuarios;
    }
}