package com.example.deliveryrouteplanner.UI;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.deliveryrouteplanner.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ReportDetails extends AppCompatActivity {

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

        //delete button functionality code sample
//        ImageButton deleteRouteButton = findViewById(R.id.routedetailsdeletebutton);
//        deleteRouteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (routeID != -1) {
//                    new AlertDialog.Builder(RouteDetails.this).setTitle("Delete Route")
//                            .setMessage("Are you sure you want to delete this route?")
//                            .setPositiveButton("Delete", (dialog, which) -> {
//                                Date dateValue;
//                                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
//                                try {
//                                    dateValue = sdf.parse(editDate.getText().toString().trim());
//                                } catch (ParseException e) {
//                                    editDate.setError("Invalid date format");
//                                    return;
//                                }
//                                SharedPreferences sharedPref = RouteDetails.this.getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
//                                String uid = sharedPref.getString("uid", null);
//                                Route routeToDelete = new Route(
//                                        routeID, dateValue, startLocation, endLocation, stopCount, totalDistance,routeActive, uid);
//                                routeViewModel.delete(routeToDelete);
//                                Toast.makeText(RouteDetails.this, "Route deleted", Toast.LENGTH_SHORT).show();
//                                //returns to route list after deleting
//                                startActivity(new Intent(RouteDetails.this, RouteList.class));
//                                finish();
//                            })
//                            .setNegativeButton("Cancel", null)
//                            .show();
//                }
//            }
//        });
    }
}