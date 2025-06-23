package com.example.deliveryrouteplanner.UI;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryrouteplanner.Entities.Report;
import com.example.deliveryrouteplanner.R;
import com.example.deliveryrouteplanner.ViewModels.ReportViewModel;
import com.example.deliveryrouteplanner.ViewModels.RouteViewModel;
import com.example.deliveryrouteplanner.ViewModels.StopViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

public class ReportDetails extends AppCompatActivity {
    
    private ReportViewModel reportViewModel;
    private StopViewModel stopViewModel;
    private RouteViewModel routeViewModel;
    private RouteSelectionAdapter routeSelectionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_report_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //set toolbar as a support action bar to show menu
        Toolbar toolbar = findViewById(R.id.reportdetailstoolbar);
        setSupportActionBar(toolbar);
        
        //home button to go back do main activity dashboard
        ImageButton home = findViewById(R.id.homebutton);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReportDetails.this, MainActivity.class));
            }
        });

        //generate report button
        Button generateButton = findViewById(R.id.GenerateReportButton);
        generateButton.setOnClickListener( v -> generateReport());

        //TODO: set up share button
        

        SharedPreferences sharedPref = ReportDetails.this.getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String userID = sharedPref.getString("uid", null);

        //recyclerview
        RecyclerView reportRecyclerView = findViewById(R.id.reportdetailsrecyclerview);
        routeSelectionAdapter = new RouteSelectionAdapter(this);
        reportRecyclerView.setAdapter(routeSelectionAdapter);
        reportRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        reportViewModel = new ViewModelProvider(this).get(ReportViewModel.class);
        stopViewModel = new ViewModelProvider(this).get(StopViewModel.class);
        routeViewModel = new ViewModelProvider(this).get(RouteViewModel.class);
        
        //display available routes
        routeViewModel.getAllRoutes(userID).observe(this, routes -> {
            if (routes != null) {
                routeSelectionAdapter.setRoutes(routes);
            }
        });





    }

    private void generateReport() {
        Set<Integer> selectedRouteIds = routeSelectionAdapter.getSelectedRouteIds();
        if (selectedRouteIds.isEmpty()) {
            Toast.makeText(this, "Please select at least one route.", Toast.LENGTH_SHORT).show();
        }
        //TODO: need to put in logic for generate PDF- toast is for testing button and selection count
        Toast.makeText(this, "Generating PDF for " + selectedRouteIds.size() + " routes", Toast.LENGTH_SHORT).show();
    }
}