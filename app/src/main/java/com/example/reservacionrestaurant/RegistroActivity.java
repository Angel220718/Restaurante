package com.example.reservacionrestaurant;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {

    Button add_cliente;
    EditText InNombre, InApellido, InTelefono, InUsuario, InContraseña;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

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

                if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(apellido) || TextUtils.isEmpty(telefono) ||
                        TextUtils.isEmpty(usuario) || TextUtils.isEmpty(contraseña)) {
                    Toast.makeText(getApplicationContext(), "Llene todos los datos", Toast.LENGTH_SHORT).show();
                } else {
                    registerUserAndSaveData(nombre, apellido, telefono, usuario, contraseña);
                }
            }
        });
    }

    private void registerUserAndSaveData(String nombre, String apellido, String telefono, String usuario, String contraseña) {
        mAuth.createUserWithEmailAndPassword(usuario, contraseña)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String id = mAuth.getCurrentUser().getUid();
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", id);
                        map.put("Nombre", nombre);
                        map.put("Apellido", apellido);
                        map.put("Telefono", telefono);
                        map.put("Usuario", usuario);

                        mFirestore.collection("Usuarios").document(id)
                                .set(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        finish();
                                        startActivity(new Intent(RegistroActivity.this, MainActivity.class));
                                        Toast.makeText(getApplicationContext(), "Datos ingresados correctamente", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), "Error al ingresar los datos", Toast.LENGTH_SHORT).show();
                                    }
                                });


                    }
                });
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}
