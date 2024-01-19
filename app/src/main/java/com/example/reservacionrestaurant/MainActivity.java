package com.example.reservacionrestaurant;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


public class MainActivity extends AppCompatActivity {

    public interface NombreUsuarioCallback {
        void onNombreUsuarioObtenido(String nombreUsuario);
    }


    Button btn_add, btn_login;
    EditText Usuario, Password;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();


        btn_add = findViewById(R.id.btn_add);

        btn_login =findViewById(R.id.btn_login);
        Usuario = findViewById(R.id.Usuario);
        Password = findViewById(R.id.Password);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegistroActivity.class));
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = Usuario.getText().toString().trim();
                String password = Password.getText().toString().trim();

                if(usuario.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Llene todos los datos", Toast.LENGTH_SHORT).show();
                }else {
                    userValidacion(usuario,password);
                }
            }
        });



    }

    private void userValidacion(String usuario, String password) {
        mAuth.signInWithEmailAndPassword(usuario, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this, ListaRestaurantActivity.class));
                    Toast.makeText(MainActivity.this, "** Bienvenido **", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(MainActivity.this, "** Error **", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Llene datos validos", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            obtenerNombreUsuarioActual();

            startActivity(new Intent(MainActivity.this, ListaRestaurantActivity.class));
            finish();
        } else {
        }
    }

    @SuppressLint("RestrictedApi")
    private void obtenerNombreUsuarioActual() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();
            FirebaseFirestore.getInstance().collection("Usuarios").document(userId)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String nombreUsuario = task.getResult().getString("Nombre");
                        } else {
                            Log.e(TAG, "Error al obtener el nombre de usuario", task.getException());
                        }
                    });
        }
    }
}