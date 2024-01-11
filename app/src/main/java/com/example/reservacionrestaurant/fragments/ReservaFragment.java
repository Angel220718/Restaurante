package com.example.reservacionrestaurant.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.reservacionrestaurant.R;

import java.util.ArrayList;
import java.util.List;

public class ReservaFragment extends Fragment {

    private EditText etNombreCliente;
    private Spinner spinnerMesas;
    private Button btnGuardarReserva;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReservaFragment() {
        // Required empty public constructor
    }
    public static ReservaFragment newInstance(List<Integer> mesasSeleccionadas) {
        ReservaFragment fragment = new ReservaFragment();
        Bundle args = new Bundle();
        args.putIntegerArrayList("mesasSeleccionadas", (ArrayList<Integer>) mesasSeleccionadas);
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_reserva, container, false);

        etNombreCliente = view.findViewById(R.id.etNombreCliente);
        spinnerMesas = view.findViewById(R.id.spinnerMesas);
        btnGuardarReserva = view.findViewById(R.id.btnGuardarReserva);

        // Configurar el adaptador para el Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(), R.array.mesas_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMesas.setAdapter(adapter);

        // Agregar un escucha al Spinner para manejar la selección
        spinnerMesas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String mesaSeleccionada = parentView.getItemAtPosition(position).toString();

                // Llenar automáticamente el formulario con datos de la mesa seleccionada
                etNombreCliente.setText("Nombre del Cliente para " + mesaSeleccionada);
                // También podrías llenar otros campos según sea necesario
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Aquí puedes manejar el caso en que no se selecciona nada
            }
        });

        btnGuardarReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes realizar la lógica para guardar la reserva
            }
        });

        // Verificar si hay argumentos y llenar automáticamente el formulario
        if (getArguments() != null) {
            List<Integer> mesasSeleccionadas = getArguments().getIntegerArrayList("mesasSeleccionadas");

            // Utiliza las mesas seleccionadas según sea necesario
            // Por ejemplo, puedes mostrarlas en un TextView o realizar otras acciones
        }

        return view;
    }
}