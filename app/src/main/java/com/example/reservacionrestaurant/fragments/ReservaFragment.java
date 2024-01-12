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
import com.example.reservacionrestaurant.MainActivity;
import com.example.reservacionrestaurant.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ReservaFragment extends Fragment {

    private EditText etNombreCliente;
    private DatabaseReference databaseReference;
    private Spinner spinnerMesas;
    private Button btnGuardarReserva;

    private static final String ARG_MESAS_SELECCIONADAS = "mesas_seleccionadas";

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
        databaseReference = FirebaseDatabase.getInstance().getReference();

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
                // Obtener la lista de mesas seleccionadas
                ArrayList<Integer> mesasSeleccionadas = getArguments().getIntegerArrayList(ARG_MESAS_SELECCIONADAS);

                // Aquí puedes realizar la lógica para guardar la reserva y actualizar el estado en Firebase
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

        // Crear un nodo "reservas" en la base de datos
        DatabaseReference reservasRef = databaseReference.child("mesas");

        // Iterar sobre las mesas seleccionadas y agregar la información de la reserva
        for (int numeroMesa : mesasSeleccionadas) {
            // Crear una referencia para la reserva en la mesa específica
            DatabaseReference reservaMesaRef = reservasRef.child("mesa_" + numeroMesa);

            // Crear un objeto para almacenar la información de la reserva
            Reserva reserva = new Reserva(nombreCliente, "Ocupado"); // Puedes crear una clase Reserva según tus necesidades

            // Agregar la información de la reserva al nodo correspondiente
            reservaMesaRef.setValue(reserva);
        }

        // Después de guardar la reserva, iniciar ActivityListaRestaurant
        startActivity(new Intent(getActivity(), ListaRestaurantActivity.class));
        Toast.makeText(requireContext(), "Reserva guardada con éxito", Toast.LENGTH_SHORT).show();
        // Finalmente, cierra el fragmento actual
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }
}