package com.example.reservacionrestaurant;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reservacionrestaurant.adapter.ReservaAdapter;
import com.example.reservacionrestaurant.fragments.Reserva;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class CuentaActivity extends AppCompatActivity {

    private RecyclerView recyclerViewReservas;
    private ReservaAdapter reservaAdapter;
    private List<Reserva> reservasList;

    private static final String TAG = "CuentaActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta);

        recyclerViewReservas = findViewById(R.id.recyclerViewReservas);
        reservasList = new ArrayList<>();
        reservaAdapter = new ReservaAdapter(reservasList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewReservas.setLayoutManager(layoutManager);
        recyclerViewReservas.setAdapter(reservaAdapter);

        // Obtener las reservas del usuario actual
        obtenerReservasUsuario();
    }
    private void obtenerReservasUsuario() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Obtener una referencia a la base de datos
        CollectionReference restaurantesRef = db.collection("restaurantes");

        // Realizar la consulta a Firebase Firestore para obtener las reservas del usuario en todos los restaurantes
        restaurantesRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot restauranteDoc : task.getResult()) {
                    // Obtener el nombre del restaurante
                    String nombreRestaurante = restauranteDoc.getId();

                    // Obtener la referencia a la colección de reservas específica del restaurante
                    CollectionReference reservasRef = db.collection(nombreRestaurante + "_reservas");

                    // Realizar la consulta a Firebase Firestore para obtener las reservas del usuario actual en el restaurante actual
                    reservasRef.whereEqualTo("userId", userId).get().addOnCompleteListener(reservasTask -> {
                        if (reservasTask.isSuccessful()) {
                            for (QueryDocumentSnapshot document : reservasTask.getResult()) {
                                // Convierte cada documento a un objeto Reserva y agrégalo a la lista
                                Reserva reserva = document.toObject(Reserva.class);
                                reservasList.add(reserva);
                            }

                            // Notifica al adaptador que los datos han cambiado
                            reservaAdapter.notifyDataSetChanged();
                        } else {
                            // Maneja el error de alguna manera (puedes mostrar un mensaje, registrar el error, etc.)
                            Log.e(TAG, "Error al obtener las reservas del usuario en " + nombreRestaurante, reservasTask.getException());
                            Toast.makeText(this, "Error al obtener las reservas del usuario en " + nombreRestaurante, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                // Maneja el error de alguna manera (puedes mostrar un mensaje, registrar el error, etc.)
                Log.e(TAG, "Error al obtener la lista de restaurantes", task.getException());
                Toast.makeText(this, "Error al obtener la lista de restaurantes", Toast.LENGTH_SHORT).show();
            }
        });
    }

}