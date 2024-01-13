package com.example.reservacionrestaurant.fragments;

import android.content.Intent;
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
import android.widget.Toast;

import com.example.reservacionrestaurant.ListaRestaurantActivity;
import com.example.reservacionrestaurant.R;

import java.util.ArrayList;

public class ReservaFragment extends Fragment {

    private EditText etNombreCliente;
    private Spinner spinnerMesas;
    private Button btnGuardarReserva;

    private static final String ARG_MESAS_SELECCIONADAS = "mesas_seleccionadas";

    private ArrayList<Integer> mesasSeleccionadas;

    public ReservaFragment() {
        // Required empty public constructor
    }

    public static ReservaFragment newInstance(ArrayList<Integer> mesasSeleccionadas, String restauranteId) {
        ReservaFragment fragment = new ReservaFragment();
        Bundle args = new Bundle();
        args.putIntegerArrayList(ARG_MESAS_SELECCIONADAS, mesasSeleccionadas);
        args.putString("restauranteId", restauranteId);
        fragment.setArguments(args);
        return fragment;
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
                int numeroMesa = position + 1;  // Suponiendo que la posición en el Spinner representa el número de la mesa
                etNombreCliente.setText("Nombre del Cliente para mesa_" + numeroMesa);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Aquí puedes manejar el caso en que no se selecciona nada
            }
        });

        btnGuardarReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener la lista de mesas seleccionadas
                ArrayList<Integer> mesasSeleccionadas = getArguments().getIntegerArrayList(ARG_MESAS_SELECCIONADAS);

                // Aquí puedes realizar la lógica para guardar la reserva
                guardarReserva(mesasSeleccionadas);
            }
        });

        return view;
    }

    private void guardarReserva(ArrayList<Integer> mesasSeleccionadas) {
        // Obtener el nombre del cliente desde el EditText
        String nombreCliente = etNombreCliente.getText().toString().trim();

        // Verificar si el nombre del cliente está vacío
        if (nombreCliente.isEmpty()) {
            Toast.makeText(requireContext(), "Ingresa el nombre del cliente", Toast.LENGTH_SHORT).show();
            return;
        }

        // Puedes simular la lógica de guardar la reserva aquí

        // Después de guardar la reserva, iniciar ActivityListaRestaurant
        startActivity(new Intent(getActivity(), ListaRestaurantActivity.class));
        Toast.makeText(requireContext(), "Reserva guardada con éxito", Toast.LENGTH_SHORT).show();
        // Finalmente, cierra el fragmento actual
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }
}
