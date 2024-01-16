package com.example.reservacionrestaurant;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reservacionrestaurant.fragments.Reserva;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CuentaActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Reserva> listaReservas = new ArrayList<>();
    private int indiceActual = 0;
    private String selectedCollection = "Kfc_reservas"; // Colección predeterminada

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta);

        Button btnSiguienteReserva = findViewById(R.id.btnSiguienteReserva);
        btnSiguienteReserva.setOnClickListener(v -> mostrarSiguienteReserva());

        Button btnAnteriorReserva = findViewById(R.id.btnAnteriorReserva);
        btnAnteriorReserva.setOnClickListener(v -> mostrarReservaAnterior());

        Button btnSeleccionarColeccion = findViewById(R.id.btnSeleccionarColeccion);
        btnSeleccionarColeccion.setOnClickListener(v -> seleccionarColeccion());

        // Obtener referencia a los TextView en tu diseño XML
        TextView nombreRestauranteTextView = findViewById(R.id.nombreRestaurante);
        TextView nombreClienteTextView = findViewById(R.id.nombreCliente);
        TextView mesasSeleccionadasTextView = findViewById(R.id.mesasSeleccionadas);
        TextView fechaReservaTextView = findViewById(R.id.fechaReserva);
        TextView horaReservaTextView = findViewById(R.id.horaReserva);

        // Realizar la consulta a Firestore para obtener datos del documento específico
        cargarReservas();
    }

    private void cargarReservas() {
        CollectionReference reservasCollection = db.collection(selectedCollection);
        reservasCollection.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    listaReservas.clear(); // Limpiar la lista antes de cargar nuevas reservas
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Reserva reserva = documentSnapshot.toObject(Reserva.class);
                        if (reserva != null) {
                            listaReservas.add(reserva);
                        }
                    }
                    mostrarReservaActual();
                })
                .addOnFailureListener(e -> {
                    // Manejar errores de la consulta
                    Toast.makeText(CuentaActivity.this, "Error al obtener datos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void mostrarSiguienteReserva() {
        if (indiceActual < listaReservas.size() - 1) {
            indiceActual++;
            mostrarReservaActual();
        } else {
            // Ya no hay más reservas, puedes manejar este caso según tus necesidades
            Toast.makeText(CuentaActivity.this, "No hay más reservas", Toast.LENGTH_SHORT).show();
        }
    }

    private void mostrarReservaActual() {
        if (!listaReservas.isEmpty()) {
            Reserva reservaActual = listaReservas.get(indiceActual);
            // Actualiza tus TextView con los datos de la reservaActual
            TextView nombreRestauranteTextView = findViewById(R.id.nombreRestaurante);
            TextView nombreClienteTextView = findViewById(R.id.nombreCliente);
            TextView horaReservaTextView = findViewById(R.id.horaReserva);
            TextView fechaReservaTextView = findViewById(R.id.fechaReserva);
            TextView mesasSeleccionadasTextView = findViewById(R.id.mesasSeleccionadas);
            // Actualiza los TextView con los datos de la reservaActual
            nombreRestauranteTextView.setText("Nombre del Restaurante: " + reservaActual.getNombreRestaurante());
            nombreClienteTextView.setText("Nombre del Cliente: " + reservaActual.getNombreCliente());
            mesasSeleccionadasTextView.setText("Mesas Seleccionadas:" + reservaActual.getMesasSeleccionadas());
            fechaReservaTextView.setText("Fecha de Reserva: " + reservaActual.getFechaReserva());
            horaReservaTextView.setText("Hora de Reserva: " + reservaActual.getHoraReserva());
        } else {
            Toast.makeText(CuentaActivity.this, "No hay reservas disponibles", Toast.LENGTH_SHORT).show();
        }
    }

    private void mostrarReservaAnterior() {
        if (indiceActual > 0) {
            indiceActual--;
            mostrarReservaActual();
        } else {
            // Ya estás en la primera reserva, puedes manejar este caso según tus necesidades
            Toast.makeText(CuentaActivity.this, "Ya estás en la primera reserva", Toast.LENGTH_SHORT).show();
        }
    }

    private void seleccionarColeccion() {
        // Implementa aquí la lógica para que el usuario seleccione la colección
        // Puedes usar un cuadro de diálogo, una lista desplegable u otras opciones según tu preferencia
        // Por ahora, simplemente alternaremos entre dos colecciones como ejemplo
        if ("Kfc_reservas".equals(selectedCollection)) {
            selectedCollection = "McDonald_reservas";
        } else if ("McDonald_reservas".equals(selectedCollection)) {
            selectedCollection = "Mexican_food_reservas";
        } else {
            selectedCollection = "Kfc_reservas";
        }

        // Cargar las reservas de la nueva colección seleccionada
        cargarReservas();
    }
}
