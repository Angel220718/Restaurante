package com.example.reservacionrestaurant.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.reservacionrestaurant.R;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import android.widget.BaseAdapter;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


public class MesaFragment extends Fragment {
    private GridView gridViewMesas;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mesa, container, false);

        gridViewMesas = view.findViewById(R.id.gridViewMesas);

        int[] listaIconosMesas = {
                R.mipmap.ic_mesa,
                R.mipmap.ic_mesa,
                R.mipmap.ic_mesa,
                R.mipmap.ic_mesa,
                R.mipmap.ic_mesa,
                R.mipmap.ic_mesa,
                R.mipmap.ic_mesa,
                R.mipmap.ic_mesa,
                R.mipmap.ic_mesa,
                R.mipmap.ic_mesa,
                R.mipmap.ic_mesa,
                R.mipmap.ic_mesa,

                // Agrega más iconos según sea necesario
        };

        // Configurar el adaptador para el GridView
        MesaAdapter adapter = new MesaAdapter(listaIconosMesas);
        gridViewMesas.setAdapter(adapter);

        // Aquí puedes manejar la selección de la mesa en el gridViewMesas

        gridViewMesas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Cambiar el estado de selección de la mesa al hacer clic
                adapter.toggleMesaSelection(position);
            }
        });

        Button btnIrAReserva = view.findViewById(R.id.btnIrAReserva);
        btnIrAReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener las mesas seleccionadas
                List<Integer> mesasSeleccionadas = adapter.getMesasSeleccionadas();

                // Crear una instancia de ReservaFragment y pasar las mesas seleccionadas como argumento
                ReservaFragment reservaFragment = ReservaFragment.newInstance(mesasSeleccionadas);

                // Reemplazar el fragmento actual con ReservaFragment
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, reservaFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        return view;
    }

    public class MesaAdapter extends BaseAdapter {
        private final int[] listaIconosMesas;
        private boolean[] mesaSeleccionada;

        MesaAdapter(int[] listaIconosMesas) {
            this.listaIconosMesas = listaIconosMesas;
            this.mesaSeleccionada = new boolean[listaIconosMesas.length];
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
                // Si la vista no existe, inflarla
                imageView = new ImageView(parent.getContext());
                imageView.setLayoutParams(new GridView.LayoutParams(200, 200)); // Ajusta el tamaño según tus necesidades
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

// Asignar el icono de la mesa al ImageView
            imageView.setImageResource(listaIconosMesas[position]);

            if (mesaSeleccionada[position]) {
                // Cambiar el color de fondo o aplicar algún efecto para indicar la selección
                imageView.setBackgroundColor(ContextCompat.getColor(parent.getContext(), R.color.colorSelected));
            } else {
                // Restaurar el color de fondo predeterminado
                imageView.setBackgroundColor(Color.TRANSPARENT);
            }

            return imageView;

        }
        // Método para cambiar el estado de selección de una mesa
        public void toggleMesaSelection(int position) {
            mesaSeleccionada[position] = !mesaSeleccionada[position];
            notifyDataSetChanged(); // Notificar al adaptador sobre el cambio
        }

        // Método para obtener las mesas seleccionadas
        public List<Integer> getMesasSeleccionadas() {
            List<Integer> mesasSeleccionadas = new ArrayList<>();
            for (int i = 0; i < mesaSeleccionada.length; i++) {
                if (mesaSeleccionada[i]) {
                    mesasSeleccionadas.add(i + 1); // Agregar la mesa seleccionada a la lista
                }
            }
            return mesasSeleccionadas;
        }
    }
}