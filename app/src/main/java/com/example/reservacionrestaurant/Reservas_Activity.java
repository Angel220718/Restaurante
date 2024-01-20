package com.example.reservacionrestaurant;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.reservacionrestaurant.fragments.MesaFragment;
import com.example.reservacionrestaurant.fragments.ReservaFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Reservas_Activity extends AppCompatActivity {

    ImageView imagenIdentificadora;
    BottomNavigationView menu;
    String restauranteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas);

        menu = findViewById(R.id.menu);
        imagenIdentificadora = findViewById(R.id.imagen_identificadora);
        restauranteId = getIntent().getStringExtra("restaurante_id");
        Log.d("Reservas_Activity", "ID del restaurante: " + restauranteId);

        Intent intent = getIntent();
        if (intent != null) {

            restauranteId = intent.getStringExtra("restaurante_id");

            if ("Buffet Kingdom".equals(restauranteId)) {
                imagenIdentificadora.setImageResource(R.drawable.buffetkindom);
            } else if ("Rukito".equals(restauranteId)) {
                imagenIdentificadora.setImageResource(R.drawable.rukito);
            } else if ("Fritamoro".equals(restauranteId)) {
                imagenIdentificadora.setImageResource(R.drawable.fritamoro);
            }

            // Pasa la información del restaurante al fragmento MesaFragment
            // Por ejemplo, al seleccionar el restaurante McDonald
            MesaFragment mesaFragment = MesaFragment.newInstance("param1", "param2", restauranteId);
            mostrarFragment(mesaFragment);
        }

        menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.Reserva) {
                    mostrarFragment(ReservaFragment.newInstance(new ArrayList<>(), restauranteId));
                }
                if (menuItem.getItemId() == R.id.Mesa) {
                    // Pasa la información del restaurante al fragmento MesaFragment
                    // Por ejemplo, al seleccionar el restaurante McDonald
                    MesaFragment mesaFragment = MesaFragment.newInstance("param1", "param2", restauranteId);
                    mostrarFragment(mesaFragment);
                }
                return true;
            }
        });
    }

    private void mostrarFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}