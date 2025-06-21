package com.example.deliveryrouteplanner.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryrouteplanner.Entities.Route;
import com.example.deliveryrouteplanner.R;
import com.example.deliveryrouteplanner.ViewModels.RouteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class RouteList extends AppCompatActivity {

    private RouteViewModel routeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_route_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //set toolbar as a support action bar to show menu
        Toolbar toolbar = findViewById(R.id.routelisttoolbar);
        setSupportActionBar(toolbar);

       //fab functionality
        FloatingActionButton fab = findViewById(R.id.floatingActionButtonRouteList);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(RouteList.this, RouteDetails.class);
            startActivity(intent);
        });

        //home button to navigate to main activity (dashboard)
        ImageButton home = findViewById(R.id.homebutton);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RouteList.this, MainActivity.class));
            }
        });
        //display routes in recycler view
        RecyclerView recyclerView = findViewById(R.id.routelistrecyclerview);
        final RouteAdapter adapter = new RouteAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        routeViewModel = new ViewModelProvider(this).get(RouteViewModel.class);
        routeViewModel.getAllRoutes().observe(this, new Observer<List<Route>>() {
            @Override
            public void onChanged(List<Route> routes) {
                adapter.setRoutes(routes);
            }
        });
    }
}