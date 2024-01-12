package com.example.reservacionrestaurant.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.annotation.NonNull;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.reservacionrestaurant.R;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MesaFragment extends Fragment {
    private GridView gridViewMesas;
    private DatabaseReference databaseReference;
    private static List<Integer> mesasSeleccionadas = new ArrayList<>();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private String restauranteId;

    public static MesaFragment newInstance(String param1, String param2) {
        MesaFragment fragment = new MesaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MesaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mesa, container, false);

        gridViewMesas = view.findViewById(R.id.gridViewMesas);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mesasSeleccionadas = new ArrayList<>();

        int[] listaIconosMesas = {
                R.mipmap.ic_mesa, R.mipmap.ic_mesa, R.mipmap.ic_mesa, R.mipmap.ic_mesa, R.mipmap.ic_mesa,
                R.mipmap.ic_mesa, R.mipmap.ic_mesa, R.mipmap.ic_mesa, R.mipmap.ic_mesa, R.mipmap.ic_mesa,
                R.mipmap.ic_mesa, R.mipmap.ic_mesa, R.mipmap.ic_mesa, R.mipmap.ic_mesa, R.mipmap.ic_mesa,
                R.mipmap.ic_mesa, R.mipmap.ic_mesa, R.mipmap.ic_mesa, R.mipmap.ic_mesa, R.mipmap.ic_mesa,
                R.mipmap.ic_mesa, R.mipmap.ic_mesa, R.mipmap.ic_mesa, R.mipmap.ic_mesa, R.mipmap.ic_mesa,
                R.mipmap.ic_mesa, R.mipmap.ic_mesa, R.mipmap.ic_mesa, R.mipmap.ic_mesa, R.mipmap.ic_mesa,
                R.mipmap.ic_mesa, R.mipmap.ic_mesa
        };

        MesaAdapter adapter = new MesaAdapter(listaIconosMesas);
        gridViewMesas.setAdapter(adapter);

        gridViewMesas.setOnItemClickListener((parent, itemview, position, id) -> {
            int numeroMesa = position + 1;

            if (mesasSeleccionadas.contains(numeroMesa)) {
                mesasSeleccionadas.remove(Integer.valueOf(numeroMesa));
            } else {
                mesasSeleccionadas.add(numeroMesa);
            }

            adapter.notifyDataSetChanged();

            DatabaseReference mesaReference = databaseReference.child("estadoMesa").child(String.valueOf(numeroMesa));
            mesaReference.get().addOnCompleteListener(task -> {
                mesaReference.get().addOnCompleteListener(taskReloaded -> {
                    if (taskReloaded.isSuccessful() && taskReloaded.getResult().getValue() != null) {
                        String estadoMesa = String.valueOf(taskReloaded.getResult().getValue());
                        actualizarColorDeFondo(estadoMesa, (ImageView) itemview);
                    }
                });
            });
        });

        Button btnIrAReserva = view.findViewById(R.id.btnIrAReserva);
        btnIrAReserva.setOnClickListener(v -> {
            ReservaFragment reservaFragment = ReservaFragment.newInstance(new ArrayList<>(mesasSeleccionadas), restauranteId);
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, reservaFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return view;
    }

    private static void actualizarColorDeFondo(String estadoMesa, ImageView imageView) {
        int iconoResId;

        switch (estadoMesa) {
            case "Libre":
                // Cargar la imagen verde
                iconoResId = R.mipmap.ic_mesa_verde;
                break;
            default:
                // Cargar la imagen por defecto o neutral
                iconoResId = R.mipmap.ic_mesa;
                break;
        }

        // Cargar y aplicar la imagen con Glide
        Glide.with(imageView.getContext())
                .load(iconoResId)
                .transform(new CircleCrop()) // Otras transformaciones si son necesarias
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    private static class MesaAdapter extends BaseAdapter {
        private final int[] listaIconosMesas;

        MesaAdapter(int[] listaIconosMesas) {
            this.listaIconosMesas = listaIconosMesas;
        }


        @Override
        public int getCount() {
            return listaIconosMesas.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(parent.getContext());
                imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            int numeroMesa = position + 1;
            DatabaseReference mesaReference = FirebaseDatabase.getInstance().getReference().child("mesas").child("mesa_" + numeroMesa);

            mesaReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Asegúrate de que los datos existan antes de intentar acceder a ellos
                        String estadoMesa = String.valueOf(dataSnapshot.child("estadoMesa").getValue());
                        actualizarColorDeFondo(estadoMesa, imageView);

                        if (mesasSeleccionadas.contains(numeroMesa)) {
                            imageView.setAlpha(0.5f);
                        } else {
                            imageView.setAlpha(1.0f);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Manejar errores si es necesario
                }
            });

            return imageView;
        }

    }

    }
