package com.example.reservacionrestaurant.fragments;

import java.util.ArrayList;

public class Reserva {
    private static int contadorReservas = 0; // Variable estática para el conteo de reservas

    private int id;


    private String NombreRestaurante;
    private String nombreCliente;
    private ArrayList<Integer> mesasSeleccionadas;
    private String fechaReserva;
    private String horaReserva;
    private String estadoMesa; // Nuevo campo para el estado de la mesa

    public Reserva() {
        // Incrementar el contador y asignar como ID
        contadorReservas++;
        this.id = contadorReservas;
    }

    // Constructor sin mesasSeleccionadas
    public Reserva(String nombreCliente) {
        this.nombreCliente = nombreCliente;
        this.mesasSeleccionadas = new ArrayList<>();
        // Incrementar el contador y asignar como ID
        contadorReservas++;
        this.id = contadorReservas;
    }

    // Método para actualizar las mesas seleccionadas y su estado
    public void setMesasSeleccionadas(ArrayList<Integer> mesasSeleccionadas, String estadoMesa) {
        this.mesasSeleccionadas = mesasSeleccionadas;
        this.estadoMesa = estadoMesa;
    }

    public int getId() {
        return id;
    }

    public String getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(String fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public String getHoraReserva() {
        return horaReserva;
    }

    public void setHoraReserva(String horaReserva) {
        this.horaReserva = horaReserva;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public ArrayList<Integer> getMesasSeleccionadas() {
        return mesasSeleccionadas;
    }

    public void setMesasSeleccionadas(ArrayList<Integer> mesasSeleccionadas) {
        this.mesasSeleccionadas = mesasSeleccionadas;
    }

    public String getEstadoMesa() {
        return estadoMesa;
    }

    public void setEstadoMesa(String estadoMesa) {
        this.estadoMesa = estadoMesa;
    }

    public String getNombreRestaurante() {
        return NombreRestaurante;
    }

    public void setNombreRestaurante(String nombreRestaurante) {
        NombreRestaurante = nombreRestaurante;
    }
}
