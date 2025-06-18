package com.example.deliveryrouteplanner.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.deliveryrouteplanner.R;

public class MainActivity extends AppCompatActivity {
//adding firebase authentication logout option
    private FirebaseAuth mAuth;
    private Button logoutbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //set toolbar as a support action bar to show menu
        Toolbar toolbar = findViewById(R.id.dashtoolbar);
        setSupportActionBar(toolbar);
    }

    //menu inflater
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logoutaction) {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(MainActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, LoginPage.class));
            finish();
            return true;
        }
        //TODO: add functionality for menu items for menu_dashboard (only setup logout so far)

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser== null) {
            startActivity(new Intent(MainActivity.this, LoginPage.class));
        }
    }
    //log out method
    public void logout(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity.this, LoginPage.class));
    }

}



