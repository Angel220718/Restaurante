package com.example.reservacionrestaurant;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {

    Button add_cliente;
    EditText InNombre,InApellido,InTelefono ,InCorreo ,InContraseña;
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
        InCorreo = findViewById(R.id.InCorreo);
        InContraseña = findViewById(R.id.InContraseña);
        add_cliente = findViewById(R.id.add_cliente);

        add_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = InNombre.getText().toString().trim();
                String apellido = InApellido.getText().toString().trim();
                String telefono = InTelefono.getText().toString().trim();
                String correo = InCorreo.getText().toString().trim();
                String contraseña = InContraseña.getText().toString().trim();

                if (nombre.isEmpty() && apellido.isEmpty() && telefono.isEmpty() && correo.isEmpty() && contraseña.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "LLene los Datos", Toast.LENGTH_SHORT).show();
                } else {
                    post(nombre, apellido, telefono, correo, contraseña);
                }
            }
        });



    }

    private void post(String nombre, String apellido, String telefono, String correo, String contraseña) {
        Map<String,Object> map = new HashMap<>();
        map.put("Nombre", nombre);
        map.put("Apellido", apellido);
        map.put("Telefono", telefono);
        map.put("Correo", correo);
        map.put("Contraseña", contraseña);

        mfirestore.collection("Cliente").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(), "Datos ingresados Correctamente", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al Ingresar los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}
