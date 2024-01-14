package com.example.reservacionrestaurant.fragments;

import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.reservacionrestaurant.R;

public class ReservaViewHolder extends RecyclerView.ViewHolder {

    public TextView NombreRestaurante;
    public TextView NombreCliente;
    public TextView FechaReserva;
    public TextView MesasReservadas;
    public TextView HoraReserva;

    public ReservaViewHolder(@NonNull View itemView) {
        super(itemView);
        NombreRestaurante = itemView.findViewById(R.id.NombreRestaurante);
        NombreCliente = itemView.findViewById(R.id.NombreCliente);
        FechaReserva = itemView.findViewById(R.id.FechaReserva);
        MesasReservadas = itemView.findViewById(R.id.MesasReservadas);
        HoraReserva = itemView.findViewById(R.id.HoraReserva);

        Log.d("ReservaViewHolder", "Constructor llamado");
    }
}

