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

import com.example.deliveryrouteplanner.Entities.Stop;
import com.example.deliveryrouteplanner.R;
import com.example.deliveryrouteplanner.ViewModels.StopViewModel;

import java.util.ArrayList;
import java.util.List;

public class StopList extends AppCompatActivity {

    private StopAdapter stopAdapter;
    private StopViewModel stopViewModel;
    private List<Stop> allStops = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_stop_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //set toolbar as a support action bar to show menu
        Toolbar toolbar = findViewById(R.id.stoplisttoolbar);
        setSupportActionBar(toolbar);

        //home button to navigate to main activity (dashboard)
        ImageButton home = findViewById(R.id.homebutton);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StopList.this, MainActivity.class));
            }
        });

        //recyclerView for stop list
        RecyclerView recyclerView = findViewById(R.id.stoplistrecyclerview);
        stopAdapter = new StopAdapter(this);
        recyclerView.setAdapter(stopAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SharedPreferences sharedPref = StopList.this.getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String userID = sharedPref.getString("uid", null);
        stopViewModel = new ViewModelProvider(this).get(StopViewModel.class);
        stopViewModel.getAllStops(userID).observe(this, stops -> {
            allStops = stops;
            stopAdapter.setStops(stops);
        });

        //searchView- search address, delivery id, status
        SearchView searchView = findViewById(R.id.stoplistsearch);
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            filterStops(query);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            filterStops(newText);
            return true;
        }
        private void filterStops(String text) {
            List<Stop> filteredList = new ArrayList<>();
            for (Stop stop : allStops) {
                if (stop.getAddress().toLowerCase().contains(text.toLowerCase())
                    || (stop.getDeliveryID() != null && stop.getDeliveryID().toLowerCase().contains(text.toLowerCase()))
                    || (stop.getStatus() != null && stop.getStatus().toLowerCase().contains(text.toLowerCase()))
                    || (stop.getReason()!= null && stop.getReason().toLowerCase().contains(text.toLowerCase()))
                ){
                    filteredList.add(stop);
                }
            }
            stopAdapter.setStops(filteredList);
        }
    });


    }
}