package com.example.reservacionrestaurant.fragments;

public class Reserva {
    private String nombreCliente;
    private String estadoMesa;

    public Reserva() {
        // Constructor vacío requerido para Firebase
    }

    public Reserva(String nombreCliente, String estadoMesa) {
        this.nombreCliente = nombreCliente;
        this.estadoMesa = estadoMesa;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getEstadoMesa() {
        return estadoMesa;
    }
}

