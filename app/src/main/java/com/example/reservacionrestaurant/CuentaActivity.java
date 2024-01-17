package com.example.reservacionrestaurant;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.reservacionrestaurant.fragments.Reserva;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CuentaActivity extends AppCompatActivity {

    private String restauranteId;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Reserva> listaReservas = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta);

        restauranteId = getIntent().getStringExtra("restaurante_id");

        // Obtener referencia a ListView en tu diseño XML
        listView = findViewById(R.id.listViewReservas);

        // Realizar la consulta a Firestore para obtener datos del documento específico
        cargarReservas();

    }

    private void cargarReservas() {
        // Utiliza la referencia a la colección basada en el restauranteId
        CollectionReference reservasCollection = db.collection(restauranteId + "_reservas");

        reservasCollection.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            listaReservas.clear(); // Limpiar la lista antes de cargar nuevas reservas

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Reserva reserva = document.toObject(Reserva.class);
                                listaReservas.add(reserva);
                            }

                            // Configurar el adaptador para el ListView
                            ReservaListAdapter adapter = new ReservaListAdapter(CuentaActivity.this, listaReservas);
                            listView.setAdapter(adapter);
                        } else {
                            // Manejar errores de la consulta
                            Toast.makeText(CuentaActivity.this, "Error al obtener datos: " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}
