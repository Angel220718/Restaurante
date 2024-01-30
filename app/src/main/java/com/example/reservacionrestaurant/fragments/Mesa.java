package com.example.reservacionrestaurant.fragments;

public class Mesa {
    private String estadoMesa;
    private String informacionAdicional;
    private int numeroMesa;

    // Constructor vacío necesario para Firestore
    public Mesa() {
    }

    public Mesa(String estadoMesa, String informacionAdicional, int numeroMesa) {
        this.estadoMesa = estadoMesa;
        this.informacionAdicional = informacionAdicional;
        this.numeroMesa = numeroMesa;
    }

    public String getEstadoMesa() {
        return estadoMesa;
    }

    public void setEstadoMesa(String estadoMesa) {
        this.estadoMesa = estadoMesa;
    }

    public String getInformacionAdicional() {
        return informacionAdicional;
    }

    public void setInformacionAdicional(String informacionAdicional) {
        this.informacionAdicional = informacionAdicional;
    }

    public int getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(int numeroMesa) {
        this.numeroMesa = numeroMesa;
    }
}


