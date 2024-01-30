package com.example.reservacionrestaurant.fragments;

import java.util.ArrayList;

// aki cree la clase o moelo de como se guardaria las reservas en firebase
public class Reserva {
    private static int contadorReservas = 0; // cree una variable estática para el conteo de reservas

    private String id; // el id lo recibo en entero pero lo cambie a tipo String


    private String NombreRestaurante;
    private String nombreCliente;
    private ArrayList<Integer> mesasSeleccionadas;
    private String fechaReserva;
    private String horaReserva;
    private String fechadeReserva;
    private String horadeReserva;
    private String estadoMesa;

    public Reserva() {
        contadorReservas++;
        this.id = String.valueOf(contadorReservas);
    }

    public Reserva(String id, String nombreCliente) {
        this.id = id;
        this.nombreCliente = nombreCliente;
    }

    // Constructor sin mesasSeleccionadas
    public Reserva(String nombreCliente) {
        this.nombreCliente = nombreCliente;
        this.mesasSeleccionadas = new ArrayList<>();
        // Incrementar el contador y asignar como ID
        contadorReservas++;
        this.id = String.valueOf(contadorReservas);
    }

    // Método para actualizar las mesas seleccionadas y su estado
    public void setMesasSeleccionadas(ArrayList<Integer> mesasSeleccionadas, String estadoMesa) {
        this.mesasSeleccionadas = mesasSeleccionadas;
        this.estadoMesa = estadoMesa;
    }

    public String getId() {
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

    public String getFechadeReserva() {
        return fechadeReserva;
    }

    public void setFechadeReserva(String fechadeReserva) {
        this.fechadeReserva = fechadeReserva;
    }

    public String getHoradeReserva() {
        return horadeReserva;
    }

    public void setHoradeReserva(String horadeReserva) {
        this.horadeReserva = horadeReserva;
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

    public void setId(String id) {
        this.id = id;
    }

}
