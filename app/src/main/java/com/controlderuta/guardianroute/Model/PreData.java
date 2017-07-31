package com.controlderuta.guardianroute.Model;

import android.content.Intent;

/**
 * Created by eduin on 31/07/2017.
 */

public class PreData {

    private String id;
    private String nameconductor;
    private String lastnameconductor;
    private String mobileconductor;
    private String codempresa;
    private String codigoruta;

    public PreData(String id, String nameconductor, String lastnameconductor, String mobileconductor, String codempresa, String codigoruta) {
        this.id = id;
        this.nameconductor = nameconductor;
        this.lastnameconductor = lastnameconductor;
        this.mobileconductor = mobileconductor;
        this.codempresa = codempresa;
        this.codigoruta = codigoruta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameconductor() {
        return nameconductor;
    }

    public void setNameconductor(String nameconductor) {
        this.nameconductor = nameconductor;
    }

    public String getLastnameconductor() {
        return lastnameconductor;
    }

    public void setLastnameconductor(String lastnameconductor) {
        this.lastnameconductor = lastnameconductor;
    }

    public String getMobileconductor() {
        return mobileconductor;
    }

    public void setMobileconductor(String mobileconductor) {
        this.mobileconductor = mobileconductor;
    }

    public String getCodempresa() {
        return codempresa;
    }

    public void setCodempresa(String codempresa) {
        this.codempresa = codempresa;
    }

    public String getCodigoruta() {
        return codigoruta;
    }

    public void setCodigoruta(String codigoruta) {
        this.codigoruta = codigoruta;
    }

    public PreData() {
    }


}
