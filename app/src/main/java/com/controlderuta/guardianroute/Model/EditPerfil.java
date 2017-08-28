package com.controlderuta.guardianroute.Model;

/**
 * Created by eduin on 28/08/2017.
 */

public class EditPerfil {

    private String lastnameconductor;
    private String nameconductor;
    private String mobileconductor;

    public EditPerfil() {
    }

    public EditPerfil(String lastnameconductor, String nameconductor, String mobileconductor) {
        this.lastnameconductor = lastnameconductor;
        this.nameconductor = nameconductor;
        this.mobileconductor = mobileconductor;
    }

    public String getLastnameconductor() {
        return lastnameconductor;
    }

    public void setLastnameconductor(String lastnameconductor) {
        this.lastnameconductor = lastnameconductor;
    }

    public String getNameconductor() {
        return nameconductor;
    }

    public void setNameconductor(String nameconductor) {
        this.nameconductor = nameconductor;
    }

    public String getMobileconductor() {
        return mobileconductor;
    }

    public void setMobileconductor(String mobileconductor) {
        this.mobileconductor = mobileconductor;
    }
}
