package com.example.deliveryrouteplanner.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.deliveryrouteplanner.Entities.Route;
import com.example.deliveryrouteplanner.ViewModels.RouteViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.deliveryrouteplanner.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
//adding firebase authentication logout option
    private FirebaseAuth mAuth;
    private ImageView createnewroute;
    private ImageView editcurrentroute;
    private ImageView viewreports;
    private ImageView createnewreport;
    private ImageView viewstops;
    private ImageView viewroutes;
    private TextView currentStart;
    private TextView currentEnd;
    private TextView currentStops;


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
        mAuth = FirebaseAuth.getInstance();

        // making buttons functional navigation
        createnewroute = findViewById(R.id.imageViewAddRoute);
        editcurrentroute = findViewById(R.id.imageViewEditCurrentRoute);
        viewreports = findViewById(R.id.imageViewReports);
        createnewreport = findViewById(R.id.imageViewAddReport);
        viewstops = findViewById(R.id.imageViewStopList);
        viewroutes = findViewById(R.id.imageViewRouteList);



        //create new route button
        createnewroute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RouteDetails.class));
            }
        });
        RouteViewModel routeViewModel = new ViewModelProvider(this).get(RouteViewModel.class);


        //current route details dashboard
        currentStart = findViewById(R.id.textViewdashboardStartLocation);
        currentEnd = findViewById(R.id.textViewdashboardEndLocation);
        currentStops = findViewById(R.id.textViewdashboardStopCount);
        SharedPreferences sharedPref = MainActivity.this.getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String userID = sharedPref.getString("uid", null);


        //edit current route button
        editcurrentroute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = MainActivity.this.getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                String userID = sharedPref.getString("uid", null);
                routeViewModel.getActiveRoute(userID).observe(MainActivity.this, route -> {
                if(route != null) {
                    Intent intent = new Intent(MainActivity.this, RouteDetails.class);
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                    intent.putExtra("routeID", route.getRouteID());
                    intent.putExtra("startLocation", route.getStartLocation());
                    intent.putExtra("endLocation", route.getEndLocation());
                    intent.putExtra("date", sdf.format(route.getDate()));
                    intent.putExtra("totalDistance", route.getTotalDistance());
                    intent.putExtra("stopCount", route.getStopCount());
                    intent.putExtra("routeActive", route.isActive());
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MainActivity.this, "No active route found", Toast.LENGTH_SHORT).show();
                }
                });
            }
        });

        //create new report
        createnewreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ReportDetails.class));
            }
        });

        //view reports button
        viewreports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ReportList.class));
            }
        });

        //view routes button
        viewroutes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RouteList.class));
            }
        });

        //view stops button
        viewstops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, StopList.class));
            }
        });

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
        //menu item functionality
        if (item.getItemId() == R.id.dashmenuroutes) {
            startActivity(new Intent(MainActivity.this, RouteList.class));
        }

        if (item.getItemId() == R.id.dashmenustops) {
            startActivity(new Intent(MainActivity.this, StopList.class));
        }

        if (item.getItemId() == R.id.dashmenureports) {
            startActivity(new Intent(MainActivity.this, ReportList.class));
        }

        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }

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

    @Override
    public void onResume(){
        super.onResume();
        SharedPreferences sharedPref = MainActivity.this.getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String userID = sharedPref.getString("uid", null);
        RouteViewModel routeViewModel = new ViewModelProvider(this).get(RouteViewModel.class);
        routeViewModel.getActiveRoute(userID).observe(MainActivity.this, route -> {
            if (route != null && route.isActive()) {
                currentStart.setText(String.format("Start Location: %s", route.getStartLocation()));
                currentEnd.setText(String.format("End Location: %s", route.getEndLocation()));
                currentStops.setText(String.format("Stops: %s", route.getStopCount()));
            }
            else {
                currentStart.setText("Start Location: N/A");
                currentEnd.setText("End Location: N/A");
                currentStops.setText("Stops: 0");
            }
        });
    }

}



