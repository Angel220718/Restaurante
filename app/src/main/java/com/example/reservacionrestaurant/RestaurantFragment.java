package com.example.reservacionrestaurant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

public class RestaurantFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    public RestaurantFragment() {

    }

    public static RestaurantFragment newInstance(int position) {
        RestaurantFragment fragment = new RestaurantFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_restaurant, container, false);

        return rootView;
    }
}
