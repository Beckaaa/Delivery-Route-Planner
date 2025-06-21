package com.example.deliveryrouteplanner.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.example.deliveryrouteplanner.R;
import com.google.firebase.auth.FirebaseUser;


public class CreateAccountPage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email, password;
    private Button createaccountbutton;
    private TextView existingUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_account_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // initialize variables for login
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editTextAddEmailAddress);
        password = findViewById(R.id.editTextAddPassword);
        createaccountbutton = findViewById(R.id.createaccountbutton);
        existingUser = findViewById(R.id.existingaccount);

        // register new account
        createaccountbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAccount();
            }
        });

    // back to login page
        existingUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateAccountPage.this, LoginPage.class));
            }
        });
    }

    private void registerAccount() {

        String user = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        if (user.isEmpty()) {
            email.setError("Email cannot be empty");
        }
        if (pass.isEmpty()) {
            password.setError("Password cannot be empty");
        }
        else {
            mAuth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        String uid = user.getUid();
                        //shared preferences to allow multiple users on same device if necessary
                        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                        sharedPreferences.edit().putString("uid", uid).apply();

                        Toast.makeText(CreateAccountPage.this, "Account Creation Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CreateAccountPage.this, MainActivity.class));
                    }
                    else {
                        Toast.makeText(CreateAccountPage.this, "Account Creation Failed"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}