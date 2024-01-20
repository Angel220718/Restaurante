package com.example.reservacionrestaurant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class SeleccionarRestaurantActivity extends AppCompatActivity {

    CardView McDonald, Mexican_food, Kfc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_restaurant);

        McDonald = findViewById(R.id.McDonald);
        Kfc = findViewById(R.id.Kfc);
        Mexican_food = findViewById(R.id.Mexican_food);


        McDonald.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeleccionarRestaurantActivity.this, CuentaActivity.class);
                intent.putExtra("restaurante_id", "Buffet Kingdom");
                startActivity(intent);
            }
        });

        Mexican_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeleccionarRestaurantActivity.this, CuentaActivity.class);
                intent.putExtra("restaurante_id", "Fritamoro");
                startActivity(intent);
            }
        });

        Kfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeleccionarRestaurantActivity.this, CuentaActivity.class);
                intent.putExtra("restaurante_id", "Rukito");
                startActivity(intent);
            }
        });


    }
}