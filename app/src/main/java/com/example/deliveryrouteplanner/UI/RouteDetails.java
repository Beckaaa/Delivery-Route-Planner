package com.example.deliveryrouteplanner.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryrouteplanner.Database.Repository;
import com.example.deliveryrouteplanner.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Date;

import Controller.DatePickerController;

public class RouteDetails extends AppCompatActivity {
    private Repository repository;
    private RecyclerView recyclerView;

    EditText editDate;
    EditText editStartLocation;
    EditText editEndLocation;
    EditText editStopCount;
    EditText editTotalDistance;
    int routeID;
    String date;
    String startLocation;
    String endLocation;
    int stopCount;
    int totalDistance;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_route_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //set toolbar as a support action bar to show menu
        Toolbar toolbar = findViewById(R.id.routedetailstoolbar);
        setSupportActionBar(toolbar);

        //fab to add stop details
        FloatingActionButton fab=findViewById(R.id.floatingActionButtonRouteDetails);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RouteDetails.this, StopDetails.class));
            }
        });

        //home button to navigate to main activity (dashboard)
        ImageButton home = findViewById(R.id.homebutton);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RouteDetails.this, MainActivity.class));
            }
        });
        //get intent data from route list to fill fields if selecting a route from the list instead of creating new
        editDate = findViewById(R.id.editTextDate);
        editStartLocation = findViewById(R.id.editTextStartLocation);
        editEndLocation = findViewById(R.id.editTextEndLocation);
        editStopCount = findViewById(R.id.editTextStopCount);
        editTotalDistance = findViewById(R.id.editTextTotalDistance);
        routeID = getIntent().getIntExtra("routeID", -1);
        date = getIntent().getStringExtra("date");
        startLocation = getIntent().getStringExtra("startLocation");
        endLocation = getIntent().getStringExtra("endLocation");
        stopCount = getIntent().getIntExtra("stopCount", 0);
        totalDistance = getIntent().getIntExtra("totalDistance", 0);

        editDate.setText(date);
        DatePickerController datePicker = new DatePickerController(this, editDate);

        editStartLocation.setText(startLocation);
        editEndLocation.setText(endLocation);
        editStopCount.setText(String.valueOf(stopCount));
        editTotalDistance.setText(String.valueOf(totalDistance));
        editDate.setOnClickListener(v -> datePicker.showDate(editDate));

        //TODO: create the StopAdapter and add the recyclerview for associated stops list
        //TODO: add save and delete button functionality

    }
}