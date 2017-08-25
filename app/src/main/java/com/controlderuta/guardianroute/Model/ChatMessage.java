package com.controlderuta.guardianroute.Model;

import java.util.Date;

/**
 * Created by eduin on 23/08/2017.
 */



public class ChatMessage {


    private String coderoute;
    private String iduserroute;
    private String idroute;
    private String idkeymensaje;
    private String messagetext;
    private String messageuser;



    public ChatMessage() {
    }

    public ChatMessage(String coderoute, String iduserroute, String idroute, String idkeymensaje, String messagetext, String messageuser) {
        this.coderoute = coderoute;
        this.iduserroute = iduserroute;
        this.idroute = idroute;
        this.idkeymensaje = idkeymensaje;
        this.messagetext = messagetext;
        this.messageuser = messageuser;

    }

    public String getCoderoute() {
        return coderoute;
    }

    public void setCoderoute(String coderoute) {
        this.coderoute = coderoute;
    }

    public String getIduserroute() {
        return iduserroute;
    }

    public void setIduserroute(String iduserroute) {
        this.iduserroute = iduserroute;
    }

    public String getIdroute() {
        return idroute;
    }

    public void setIdroute(String idroute) {
        this.idroute = idroute;
    }

    public String getIdkeymensaje() {
        return idkeymensaje;
    }

    public void setIdkeymensaje(String idkeymensaje) {
        this.idkeymensaje = idkeymensaje;
    }

    public String getMessagetext() {
        return messagetext;
    }

    public void setMessagetext(String messagetext) {
        this.messagetext = messagetext;
    }

    public String getMessageuser() {
        return messageuser;
    }

    public void setMessageuser(String messageuser) {
        this.messageuser = messageuser;
    }

}