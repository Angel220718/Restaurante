package com.example.reservacionrestaurant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {

    Button add_cliente;
    EditText InNombre, InApellido, InTelefono, InUsuario, InContraseña;
    private FirebaseFirestore mfirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        this.setTitle("Crear Cliente");
        mfirestore = FirebaseFirestore.getInstance();

        InNombre = findViewById(R.id.InNombre);
        InApellido = findViewById(R.id.InApellido);
        InTelefono = findViewById(R.id.InTelefono);
        InUsuario = findViewById(R.id.InUsuario);
        InContraseña = findViewById(R.id.InContraseña);
        add_cliente = findViewById(R.id.add_cliente);

        add_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = InNombre.getText().toString().trim();
                String apellido = InApellido.getText().toString().trim();
                String telefono = InTelefono.getText().toString().trim();
                String usuario = InUsuario.getText().toString().trim();
                String contraseña = InContraseña.getText().toString().trim();

                if (nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || usuario.isEmpty() || contraseña.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Llene todos los datos", Toast.LENGTH_SHORT).show();
                } else {
                    post(nombre, apellido, telefono, usuario, contraseña);
                }
            }
        });
    }

    private void post(String nombre, String apellido, String telefono, String usuario, String contraseña) {
        Map<String, Object> map = new HashMap<>();
        map.put("Nombre", nombre);
        map.put("Apellido", apellido);
        map.put("Telefono", telefono);
        map.put("Usuario", usuario);
        map.put("Contraseña", contraseña);

        mfirestore.collection("Cliente").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(), "Datos ingresados correctamente", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegistroActivity.this, ListaRestaurantActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al ingresar los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}
