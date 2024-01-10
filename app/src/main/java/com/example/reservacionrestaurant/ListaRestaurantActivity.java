package com.example.reservacionrestaurant;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;

public class ListaRestaurantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_restaurant);

        ViewPager viewPager = findViewById(R.id.viewPager);
        RestaurantPagerAdapter adapter = new RestaurantPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }
}