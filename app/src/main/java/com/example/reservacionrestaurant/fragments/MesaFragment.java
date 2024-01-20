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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.reservacionrestaurant.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MesaFragment extends Fragment {
    private GridView gridViewMesas;
    private static List<Integer> mesasSeleccionadas = new ArrayList<>();

    private static final String TAG = MesaFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private CollectionReference mesasCollection;
    private String mParam1;
    private String mParam2;
    private String restauranteId;

    public static MesaFragment newInstance(String param1, String param2, String restauranteId) {
        MesaFragment fragment = new MesaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString("restauranteId", restauranteId);
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

        restauranteId = getArguments().getString("restauranteId");
        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        mesasCollection = mFirestore.collection(restauranteId + "_mesas");

        gridViewMesas = view.findViewById(R.id.gridViewMesas);
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

        MesaAdapter adapter = new MesaAdapter(listaIconosMesas, new ArrayList<>());
        gridViewMesas.setAdapter(adapter);

        gridViewMesas.setOnItemClickListener((parent, itemview, position, id) -> {
            int numeroMesa = position + 1;

            if (mesasSeleccionadas.contains(numeroMesa)) {
                mesasSeleccionadas.remove(Integer.valueOf(numeroMesa));
            } else {
                mesasSeleccionadas.add(numeroMesa);
            }

            adapter.notifyDataSetChanged();
        });

        Button btnIrAReserva = view.findViewById(R.id.btnIrAReserva);
        btnIrAReserva.setOnClickListener(v -> {
            ReservaFragment reservaFragment = ReservaFragment.newInstance(new ArrayList<>(mesasSeleccionadas), restauranteId);
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, reservaFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

//        Button btnCrearColeccion = view.findViewById(R.id.btnCrearColeccion);
//        btnCrearColeccion.setOnClickListener(v -> {
//            // Código para crear la colección restaurantId_mesas
//            crearColeccionMesas();
//        });

        // Obtener y actualizar los estados de las mesas desde Firebase
        obtenerEstadosMesasDesdeFirebase(adapter);

        return view;
    }

//    private void crearColeccionMesas() {
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//        // Verificar si la colección ya existe antes de intentar crearla nuevamente
//        db.collection(restauranteId + "_mesas")
//                .get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful() && task.getResult().isEmpty()) {
//                        // La colección no existe, así que procedemos a crearla
//                        for (int i = 1; i <= 32; i++) {
//                            String numeroMesaFormateado = String.format(Locale.getDefault(), "%03d", i);
//                            Map<String, Object> mesaData = new HashMap<>();
//                            mesaData.put("estadoMesa", "Libre");
//                            mesaData.put("informacionAdicional", "");
//                            mesaData.put("numeroMesa", i);
//
//                            db.collection(restauranteId + "_mesas").document("mesa_" + numeroMesaFormateado)
//                                    .set(mesaData)
//                                    .addOnSuccessListener(aVoid -> {
//                                        Toast.makeText(requireContext(), "Documento mesa_" + numeroMesaFormateado + " creado con éxito.", Toast.LENGTH_SHORT).show();
//                                    })
//                                    .addOnFailureListener(e -> {
//                                        Toast.makeText(requireContext(), "Error al crear el documento mesa_" + numeroMesaFormateado, Toast.LENGTH_SHORT).show();
//                                        Log.e(TAG, "Error al crear el documento mesa_" + numeroMesaFormateado, e);
//                                    });
//                        }
//                    } else {
//                        // La colección ya existe
//                        Toast.makeText(requireContext(), "La colección restaurantId_mesas ya existe.", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//
//}

    private void obtenerEstadosMesasDesdeFirebase(MesaAdapter adapter) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(restauranteId + "_mesas")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<String> estadosMesas = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Mesa mesa = document.toObject(Mesa.class);
                            String estadoMesa = mesa != null ? mesa.getEstadoMesa() : "Desconocido";
                            estadosMesas.add(estadoMesa);
                        }

                        // Ordenar las mesas de manera numérica
                        Collections.sort(estadosMesas, (mesa1, mesa2) -> {
                            String[] partesMesa1 = mesa1.split("_");
                            String[] partesMesa2 = mesa2.split("_");

                            // Verificar si hay al menos dos partes en ambos nombres de mesa
                            if (partesMesa1.length > 1 && partesMesa2.length > 1) {
                                try {
                                    int numeroMesa1 = Integer.parseInt(partesMesa1[1]);
                                    int numeroMesa2 = Integer.parseInt(partesMesa2[1]);
                                    return Integer.compare(numeroMesa1, numeroMesa2);
                                } catch (NumberFormatException e) {
                                    // Manejar la excepción si no se puede convertir a un número
                                    e.printStackTrace();
                                    return 0; // o devuelve un valor predeterminado según tus necesidades
                                }
                            } else {
                                // Manejar el caso donde el formato no es el esperado
                                return 0; // o devuelve un valor predeterminado según tus necesidades
                            }
                        });

                        adapter.setEstadosMesas(estadosMesas);
                    } else {
                        Exception exception = task.getException();
                        if (exception != null) {
                            // Manejar la excepción
                        }
                    }
                });
    }

    private static class MesaAdapter extends BaseAdapter {
        private final int[] listaIconosMesas;
        private List<String> estadosMesas;

        MesaAdapter(int[] listaIconosMesas, List<String> estadosMesas) {
            this.listaIconosMesas = listaIconosMesas;
            this.estadosMesas = estadosMesas;
        }

        void setEstadosMesas(List<String> estadosMesas) {
            this.estadosMesas = estadosMesas;
            notifyDataSetChanged();
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
            String estadoMesa = obtenerEstadoMesa(numeroMesa);

            actualizarColorDeFondo(estadoMesa, imageView, numeroMesa);

            return imageView;
        }

        private String obtenerEstadoMesa(int numeroMesa) {
            if (estadosMesas == null || estadosMesas.isEmpty() || numeroMesa <= 0 || numeroMesa > estadosMesas.size()) {
                return "Ocupado";
            }

            return estadosMesas.get(numeroMesa - 1);
        }

        private void actualizarColorDeFondo(String estadoMesa, ImageView imageView, int numeroMesa) {
            int iconoResId;

            switch (estadoMesa) {
                case "Libre":
                    iconoResId = R.mipmap.ic_mesa_verde;
                    break;
                case "Ocupado":
                    iconoResId = R.mipmap.ic_mesa_rojo;
                    break;
                default:
                    iconoResId = R.mipmap.ic_mesa_rojo;
                    break;
            }

            Glide.with(imageView.getContext())
                    .load(iconoResId)
                    .transform(new CircleCrop())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView);

            if (mesasSeleccionadas.contains(numeroMesa)) {
                imageView.setAlpha(0.5f);
            } else {
                imageView.setAlpha(1.0f);
            }
        }
    }
}
