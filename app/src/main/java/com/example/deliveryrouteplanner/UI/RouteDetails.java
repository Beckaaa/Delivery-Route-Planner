package com.example.deliveryrouteplanner.UI;


import android.app.AlertDialog;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

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


import com.example.deliveryrouteplanner.Database.Repository;
import com.example.deliveryrouteplanner.Entities.Route;
import com.example.deliveryrouteplanner.Entities.Stop;
import com.example.deliveryrouteplanner.R;
import com.example.deliveryrouteplanner.ViewModels.RouteViewModel;
import com.example.deliveryrouteplanner.ViewModels.StopViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    private RouteViewModel routeViewModel;
    private List<Route> cachedRoutes = new ArrayList<>();
    CheckBox editRouteActive;
    boolean routeActive;


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
        editRouteActive = findViewById(R.id.checkBoxActive);
        routeID = getIntent().getIntExtra("routeID", -1);
        //fab invisible until the route is saved
        if (routeID == -1) {
            fab.setVisibility(View.GONE);
        }
        else {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RouteDetails.this, StopDetails.class);
                    intent.putExtra("routeID",routeID);
                    startActivity(intent);
                }
            });
        }

        date = getIntent().getStringExtra("date");
        startLocation = getIntent().getStringExtra("startLocation");
        endLocation = getIntent().getStringExtra("endLocation");
        stopCount = getIntent().getIntExtra("stopCount", 0);
        totalDistance = getIntent().getIntExtra("totalDistance", 0);
        routeActive = getIntent().getBooleanExtra("routeActive", false);


        if (date != null){
            editDate.setText(date);
        }
        DatePickerController datePicker = new DatePickerController(this, editDate);

        editStartLocation.setText(startLocation);
        editEndLocation.setText(endLocation);
        editStopCount.setText(String.valueOf(stopCount));
        editTotalDistance.setText(String.valueOf(totalDistance));
        editRouteActive.setChecked(routeActive);
        editDate.setOnClickListener(v -> datePicker.showDate(editDate));




        //TODO: create the StopAdapter and add the recyclerview for associated stops list
        RecyclerView stopRecyclerView = findViewById(R.id.routedetailsrecyclerview);
        final StopAdapter stopAdapter = new StopAdapter(this);
        stopRecyclerView.setAdapter(stopAdapter);
        stopRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        SharedPreferences sharedPref = RouteDetails.this.getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String userID = sharedPref.getString("uid", null);
        StopViewModel stopViewModel = new ViewModelProvider(this).get(StopViewModel.class);
        stopViewModel.getAssociatedStops(routeID, userID).observe(this, new Observer<List<Stop>>() {
                    @Override
                    public void onChanged(List<Stop> stops) {
                        stopAdapter.setStops(stops);
                    }
        });

        //save button functionality
        routeViewModel = new ViewModelProvider(this).get(RouteViewModel.class);
        routeViewModel.getAllRoutes(userID).observe(this, routes -> {
            if (routes != null) {
                cachedRoutes = routes;
            }
        });

        ImageButton saveRouteButton = findViewById(R.id.routedetailsavebutton);
        saveRouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (routeID == -1) {
                    //increment route id if null  id = 1, else it increases by 1
                    int newRouteID = (cachedRoutes == null || cachedRoutes.isEmpty())
                            ? 1
                            : cachedRoutes.get(cachedRoutes.size()-1).getRouteID()+1;
                    //parse stop counts
                    int editStopCountValue;
                    try {
                        editStopCountValue = Integer.parseInt(editStopCount.getText().toString().trim());
                    } catch (NumberFormatException e) {
                        editStopCount.setError("Invalid stop count");
                        return;
                    }
                    //parse distance
                    int editTotalDistanceValue;
                    try {
                        editTotalDistanceValue = Integer.parseInt(editTotalDistance.getText().toString().trim());
                    }
                    catch (NumberFormatException e) {
                        editTotalDistance.setError("Invalid total distance");
                        return;
                    }
                    //parse date
                    Date dateValue;
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                    try {
                        dateValue = sdf.parse(editDate.getText().toString().trim());
                    } catch (ParseException e) {
                        editDate.setError("Invalid date format (MM/dd/yy)");
                        return;
                    }

                    boolean isActive = editRouteActive.isChecked();
                    if (isActive) {
                        routeViewModel.deactivateAllRoutes(userID);
                    }
                    String currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Route route = new Route(
                            newRouteID,
                            dateValue,
                            editStartLocation.getText().toString(),
                            editEndLocation.getText().toString(),
                            editStopCountValue,
                            editTotalDistanceValue,
                            isActive,
                            currentUserID
                    );
                    routeViewModel.insert(route);
                    Toast.makeText(RouteDetails.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                    Toast.makeText(RouteDetails.this, "Previous active routes were deactivated.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RouteDetails.this, RouteList.class));
                    // make fab visible after save when opening route again
                    fab.setVisibility(View.VISIBLE);
                }
                else {
                    int editStopCountValue;
                    try {
                        editStopCountValue = Integer.parseInt(editStopCount.getText().toString().trim());
                    } catch (NumberFormatException e) {
                        editStopCount.setError("Invalid stop count");
                        return;
                    }
                    //parse distance
                    int editTotalDistanceValue;
                    try {
                        editTotalDistanceValue = Integer.parseInt(editTotalDistance.getText().toString().trim());
                    }
                    catch (NumberFormatException e) {
                        editTotalDistance.setError("Invalid total distance");
                        return;
                    }
                    //parse date
                    Date dateValue;
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                    try {
                        dateValue = sdf.parse(editDate.getText().toString().trim());
                    } catch (ParseException e) {
                        editDate.setError("Invalid date format");
                        return;
                    }

                    boolean isActive = editRouteActive.isChecked();
                    if (isActive) {
                        routeViewModel.deactivateAllRoutes(userID);
                    }
                    SharedPreferences sharedPref = RouteDetails.this.getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                    String uid = sharedPref.getString("uid", null);
                    Route route = new Route(
                            routeID,
                            dateValue,
                            editStartLocation.getText().toString(),
                            editEndLocation.getText().toString(),
                            editStopCountValue,
                            editTotalDistanceValue,
                            isActive,
                            uid
                    );
                    routeViewModel.update(route);
                    Toast.makeText(RouteDetails.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //delete button functionality
        ImageButton deleteRouteButton = findViewById(R.id.routedetailsdeletebutton);
        deleteRouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (routeID != -1) {
                    new AlertDialog.Builder(RouteDetails.this).setTitle("Delete Route")
                            .setMessage("Are you sure you want to delete this route?")
                            .setPositiveButton("Delete", (dialog, which) -> {
                                Date dateValue;
                                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                                try {
                                    dateValue = sdf.parse(editDate.getText().toString().trim());
                                } catch (ParseException e) {
                                    editDate.setError("Invalid date format");
                                    return;
                                }
                                SharedPreferences sharedPref = RouteDetails.this.getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                                String uid = sharedPref.getString("uid", null);
                                Route routeToDelete = new Route(
                                        routeID, dateValue, startLocation, endLocation, stopCount, totalDistance,routeActive, uid);
                                routeViewModel.delete(routeToDelete);
                                Toast.makeText(RouteDetails.this, "Route deleted", Toast.LENGTH_SHORT).show();
                                //returns to route list after deleting
                                startActivity(new Intent(RouteDetails.this, RouteList.class));
                                finish();
                            })
                            .setNegativeButton("Cancel", null)
                            .show();
                }
            }
        });

    }
}