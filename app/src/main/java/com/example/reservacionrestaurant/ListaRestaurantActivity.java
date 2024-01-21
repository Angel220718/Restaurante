package com.example.reservacionrestaurant;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.reservacionrestaurant.fragments.NombreObtenido;
import com.example.reservacionrestaurant.fragments.ReservaUtil;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class ListaRestaurantActivity extends AppCompatActivity {

    Button exit, cuenta;

    CardView McDonald, Mexican_food, Kfc;

    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_restaurant);
        FirebaseApp.initializeApp(this);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        String nombreUsuario = getIntent().getStringExtra("nombreUsuario");

        exit = findViewById(R.id.exit);
        McDonald = findViewById(R.id.McDonald);
        Kfc = findViewById(R.id.Kfc);
        Mexican_food = findViewById(R.id.Mexican_food);
        cuenta = findViewById(R.id.cuenta);


        McDonald.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaRestaurantActivity.this, Reservas_Activity.class);
                intent.putExtra("restaurante_id", "Buffet Kingdom");
                NombreObtenido.setNombreo(nombreUsuario);
                ReservaUtil.setHoraDisponible("10:00 - 23:00");
                startActivity(intent);
            }
        });

        Mexican_food.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaRestaurantActivity.this, Reservas_Activity.class);
                intent.putExtra("restaurante_id", "Fritamoro");
                NombreObtenido.setNombreo(nombreUsuario);
                Log.e(TAG, "Listarestaurant nombre usuario" + nombreUsuario);
                ReservaUtil.setHoraDisponible("12:30 - 23:00");
                startActivity(intent);
            }
        });

        Kfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaRestaurantActivity.this, Reservas_Activity.class);
                intent.putExtra("restaurante_id", "Rukito");
                NombreObtenido.setNombreo(nombreUsuario);
                ReservaUtil.setHoraDisponible("12:30 - 23:00");
                startActivity(intent);
            }
        });

        cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListaRestaurantActivity.this, SeleccionarRestaurantActivity.class));
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                finish();
                startActivity(new Intent(ListaRestaurantActivity.this, MainActivity.class));
            }
        });

    }

}