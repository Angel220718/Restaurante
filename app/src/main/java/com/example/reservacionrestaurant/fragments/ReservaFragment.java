package com.example.reservacionrestaurant.fragments;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.reservacionrestaurant.CuentaActivity;
import com.example.reservacionrestaurant.ListaRestaurantActivity;
import com.example.reservacionrestaurant.R;
import com.example.reservacionrestaurant.ReservasDetalleActivity;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Locale;

public class ReservaFragment extends Fragment {

    private Reserva reserva;

    private EditText etNombreCliente;
    private Spinner spinnerMesas;
    private String restauranteId;
    private Button btnGuardarReserva;

    private static final String ARG_MESAS_SELECCIONADAS = "mesas_seleccionadas";

    private ArrayList<Integer> mesasSeleccionadas;
    private FirebaseFirestore db;

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
        restauranteId = getArguments().getString("restauranteId");
        reserva = new Reserva("NombreCliente");
        db = FirebaseFirestore.getInstance();


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
                // Obtener el nombre del cliente
                String nombreCliente = etNombreCliente.getText().toString().trim();

                // Obtener la lista de mesas seleccionadas
                ArrayList<Integer> mesasSeleccionadas = getArguments().getIntegerArrayList(ARG_MESAS_SELECCIONADAS);

                // Verificar si hay al menos una mesa seleccionada
                if (mesasSeleccionadas.isEmpty()) {
                    Toast.makeText(requireContext(), "Seleccione al menos una mesa.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verificar si alguna de las mesas seleccionadas está ocupada
                for (Integer numeroMesa : mesasSeleccionadas) {
                    String numeroMesaFormateado = String.format(Locale.getDefault(), "%03d", numeroMesa);
                    DocumentReference mesaDocument = db.collection(restauranteId + "_mesas").document("mesa_" + numeroMesaFormateado);

                    // Verificar si la mesa está ocupada
                    mesaDocument.get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Verificar el estado de la mesa
                            String estadoMesa = task.getResult().getString("estadoMesa");

                            if ("Ocupado".equals(estadoMesa)) {
                                // Mesa ocupada, mostrar mensaje y salir
                                Toast.makeText(requireContext(), "La mesa " + numeroMesa + " está ocupada. Seleccione otra mesa.", Toast.LENGTH_SHORT).show();
                            } else {
                                // Mesa disponible, proceder con la reserva
                                guardarReserva(restauranteId, nombreCliente, mesasSeleccionadas);
                            }
                        } else {
                            Log.e(TAG, "Error al obtener el estado de la mesa para mesa_" + numeroMesa, task.getException());
                        }
                    });
                }
            }
        });

        return view;
    }

    private void guardarReserva(String restauranteId, String nombreCliente, ArrayList<Integer> mesasSeleccionadas) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Verificar si hay al menos una mesa seleccionada
        if (mesasSeleccionadas.isEmpty()) {
            Toast.makeText(requireContext(), "Seleccione al menos una mesa.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Acumular las mesas ocupadas en un mensaje
        StringBuilder mesasOcupadasMensaje = new StringBuilder();

        // Verificar el estado de cada mesa seleccionada
        for (Integer numeroMesa : mesasSeleccionadas) {
            String numeroMesaFormateado = String.format(Locale.getDefault(), "%03d", numeroMesa);
            DocumentReference mesaDocument = db.collection(restauranteId + "_mesas").document("mesa_" + numeroMesaFormateado);

            // Verificar si la mesa está ocupada
            mesaDocument.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // Verificar el estado de la mesa
                    String estadoMesa = task.getResult().getString("estadoMesa");

                    if ("Ocupado".equals(estadoMesa)) {
                        // Mesa ocupada, acumular en el mensaje
                        mesasOcupadasMensaje.append("mesa ").append(numeroMesa).append(", ");
                    }

                    // Verificar si es la última mesa
                    if (mesasSeleccionadas.indexOf(numeroMesa) == mesasSeleccionadas.size() - 1) {
                        // Mostrar mensaje de mesas ocupadas si es necesario
                        if (mesasOcupadasMensaje.length() > 0) {
                            mesasOcupadasMensaje.deleteCharAt(mesasOcupadasMensaje.length() - 2); // Eliminar la última coma y el espacio
                            Toast.makeText(requireContext(), "La(s) " + mesasOcupadasMensaje + " están ocupadas. Seleccione otras mesas.", Toast.LENGTH_SHORT).show();
                        } else {
                            // Todas las mesas están disponibles, proceder con la reserva

                            // Crear una única reserva que incluya todas las mesas seleccionadas
                            StringBuilder nombreReserva = new StringBuilder("Reserva para:  ");

                            for (Integer mesa : mesasSeleccionadas) {
                                nombreReserva.append("mesa_").append(mesa).append(", ");
                            }

                            nombreReserva.deleteCharAt(nombreReserva.length() - 2); // Eliminar la última coma y el espacio

                            // Asegúrate de que la reserva tenga un identificador único, por ejemplo, la combinación de las mesas seleccionadas
                            String reservaId = nombreReserva.toString().replace(" ", "_");

                            Reserva reserva = new Reserva(nombreCliente);
                            reserva.setMesasSeleccionadas(mesasSeleccionadas, "Ocupado"); // Cambiar a "Ocupado"

                            // Guardar la reserva
                            db.collection(restauranteId + "_reservas").document(reservaId)
                                    .set(reserva)
                                    .addOnSuccessListener(documentReference -> {
                                        // Éxito al guardar la reserva

                                        // Actualizar el estado de todas las mesas a "Ocupado"
                                        actualizarEstadoMesas(restauranteId, mesasSeleccionadas, "Ocupado");

                                        Toast.makeText(requireContext(), nombreReserva + " guardada con éxito", Toast.LENGTH_SHORT).show();

                                        // Después de guardar la reserva, puedes iniciar ActivityListaRestaurant
                                        startActivity(new Intent(getActivity(), ListaRestaurantActivity.class));

                                        Intent intent = new Intent(getActivity(), ReservasDetalleActivity.class);
                                        intent.putExtra("nombreReserva", nombreReserva.toString());
                                        intent.putExtra("mesasSeleccionadas", mesasSeleccionadas);
                                        intent.putExtra("nombreCliente", nombreCliente);
                                        intent.putExtra("fechaReserva", reserva.getFechaReserva());
                                        intent.putExtra("horaReserva", reserva.getHoraReserva());
                                        startActivity(intent);

                                        // Finalmente, cierra el fragmento actual
                                        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
                                    })
                                    .addOnFailureListener(e -> {
                                        // Error al guardar la reserva
                                        Toast.makeText(requireContext(), "Error al guardar la reserva", Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();
                                    });
                        }
                    }
                } else {
                    Log.e(TAG, "Error al obtener el estado de la mesa para mesa_" + numeroMesa, task.getException());
                }
            });
        }
    }



    private void actualizarEstadoMesas(String restauranteId, ArrayList<Integer> mesasSeleccionadas, String estado) {
        if (mesasSeleccionadas == null || mesasSeleccionadas.isEmpty()) {
            Log.w(TAG, "Lista de mesas seleccionadas es nula o vacía");
            return;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        for (Integer numeroMesa : mesasSeleccionadas) {
            String numeroMesaFormateado = String.format(Locale.getDefault(), "%03d", numeroMesa);

            // Añadir logs para verificar valores
            Log.d(TAG, "restauranteId: " + restauranteId);
            Log.d(TAG, "numeroMesaFormateado: " + numeroMesaFormateado);

            DocumentReference mesaDocument = db.collection(restauranteId + "_mesas").document("mesa_" + numeroMesaFormateado);

            mesaDocument.update("estadoMesa", estado)
                    .addOnSuccessListener(aVoid -> {
                        Log.d(TAG, "Estado de la mesa actualizado correctamente para mesa_" + numeroMesa);
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "Error al actualizar el estado de la mesa para mesa_" + numeroMesa, e);
                    });
        }


    }

}