package com.example.deliveryrouteplanner.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ReportList extends AppCompatActivity {
    private ReportViewModel reportViewModel;
    private List<Report> allReports = new ArrayList<>();
    private List<Report> reports = new ArrayList<>();
    private ReportAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_report_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //set toolbar as a support action bar to show menu
        Toolbar toolbar = findViewById(R.id.reportlisttoolbar);
        setSupportActionBar(toolbar);
        //home button to navigate to main activity (dashboard)
        ImageButton home = findViewById(R.id.homebutton);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReportList.this, MainActivity.class));
            }
        });

        //fab to add report
        FloatingActionButton fab=findViewById(R.id.floatingActionButtonReportList);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent (ReportList.this, ReportDetails.class));
            }
        });

        //display reports in recycler view
        RecyclerView recyclerView = findViewById(R.id.reportlistrecyclerview);
        adapter= new ReportAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SharedPreferences sharedPref = ReportList.this.getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String userID = sharedPref.getString("uid", null);
        reportViewModel = new ViewModelProvider(this).get(ReportViewModel.class);
        reportViewModel.getAllReports(userID).observe(this, reports -> {
            this.allReports = new ArrayList<>(reports);
            adapter.setReports(reports);
        });

        //search reports
        SearchView searchView = findViewById(R.id.reportlistsearch);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterReports(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterReports(newText);
                return true;
            }
        });





    }
    public void filterReports(String text) {
        List<Report> filteredList = new ArrayList<>();
        for (Report report : allReports) {
            String id = String.valueOf(report.getReportID());
            String date = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.US).format(report.getDateGenerated());
            if (id.toLowerCase().contains(text.toLowerCase())
                || date.toLowerCase().contains(text.toLowerCase())
            ) {
                filteredList.add(report);
            }
        }
        adapter.setReports(filteredList);
    }

}