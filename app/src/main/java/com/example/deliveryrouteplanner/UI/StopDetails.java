package com.example.deliveryrouteplanner.UI;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.deliveryrouteplanner.Entities.Stop;
import com.example.deliveryrouteplanner.R;
import com.example.deliveryrouteplanner.ViewModels.StopViewModel;
import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StopDetails extends AppCompatActivity {
    ImageButton btnSuccess;
    ImageButton btnPending;
    ImageButton btnFailed;
    String status;
    String address;
    EditText editAddress;
    //String estArrival;
    //String timeComplete;
    EditText editETA;
    EditText editDeliveryID;
    String deliveryID;
    ImageButton clearSignature;
    EditText editTimeComplete;
    int stopID;
    int routeID;
    String reason;
    EditText editReason;
    private List<Stop> cachedStops = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_stop_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //home button to navigate to main activity (dashboard)
        ImageButton home = findViewById(R.id.homebutton);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StopDetails.this, MainActivity.class));
            }
        });
        // status buttons selection
        btnSuccess = findViewById(R.id.imageButtonSuccess);
        btnPending = findViewById(R.id.imageButtonPending);
        btnFailed = findViewById(R.id.imageButtonFailed);

        View.OnClickListener statusClickListener = v -> {
            resetStatusButtonTint();
          if (v.getId() == R.id.imageButtonPending) {
              btnPending.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.yellow));
              status = "Pending";
            }
          else if (v.getId() == R.id.imageButtonSuccess) {
              btnSuccess.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.yellow));
              status = "Success";
          }
          else if (v.getId() == R.id.imageButtonFailed) {
              btnFailed.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.yellow));
              status="Failed";
          }
        };
          btnPending.setOnClickListener(statusClickListener);
          btnSuccess.setOnClickListener(statusClickListener);
          btnFailed.setOnClickListener(statusClickListener);

        //allow and clear signature
        SignaturePad signaturePad = findViewById(R.id.signature_pad);
        clearSignature = findViewById(R.id.imageButtonClearSignature);
        clearSignature.setOnClickListener(v -> {
            signaturePad.clear();
        });

        //get fields and intent data from route list to fill fields if selecting a route from the list instead of creating new
        editAddress = findViewById(R.id.editTextStopLocation);
        editDeliveryID = findViewById(R.id.editTextDeliveryID);
        editETA = findViewById(R.id.editTextStopETA);
        editTimeComplete = findViewById(R.id.editTextTimeComplete);
        editReason = findViewById(R.id.editTextReason);


        //using serializable extra instead of passing data individually
        Stop stop = (Stop) getIntent().getSerializableExtra("stop");
        if (stop != null) {
            stopID = stop.getStopID();
            routeID =stop.getRouteID();
            editAddress.setText(stop.getAddress());
            editETA.setText(new SimpleDateFormat ("HH:mm", Locale.US).format(stop.getEstArrival()));
            editDeliveryID.setText(stop.getDeliveryID());
            editReason.setText(stop.getReason());
            editTimeComplete.setText(new SimpleDateFormat ("HH:mm", Locale.US).format(stop.getTimestamp()));

            status = stop.getStatus();
            if ("Success".equals(status)) {
                btnSuccess.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.yellow));
            }
            else if ("Pending".equals(status)) {
                btnPending.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.yellow));
            }
            else if ("Failed".equals(status)) {
                btnFailed.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.yellow));
            }
            if (stop.getSignature() != null) {
                Bitmap signatureBitmap = BitmapFactory.decodeByteArray(stop.getSignature(), 0, stop.getSignature().length);
                signaturePad.setSignatureBitmap(signatureBitmap);
            }
            if (stop.getTimestamp() != null) {
                editTimeComplete.setText(new SimpleDateFormat("HH:mm", Locale.US).format(stop.getTimestamp()));
            }
        }

        //time picker for eta
        editETA.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    StopDetails.this, (view, selectedHour, selectedMinute) -> {
                        String time = String.format(Locale.US, "%02d:%02d", selectedHour, selectedMinute);
                        editETA.setText(time);
            },
                    hour,
                    minute,
                    false
            );
            timePickerDialog.show();
        });

        //save button functionality
        StopViewModel stopViewModel = new ViewModelProvider(this).get(StopViewModel.class);
        stopViewModel.getAllStops().observe(this, stops -> {
            if (stops != null) {
                cachedStops = stops;
            }
        });
        ImageButton saveStopButton = findViewById(R.id.stopdetailsavebutton);
        saveStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //increment and assign stopID
                int newStopID = (stopID == -1)
                    ? (cachedStops == null || cachedStops.isEmpty())
                        ? 1
                        : cachedStops.get(cachedStops.size() - 1).getStopID() + 1
                        : stopID;
                String addressVal = editAddress.getText().toString().trim();
                String deliveryIDVal = editDeliveryID.getText().toString().trim();
                Date etaVal = null;
                Date timeCompletionVal = null;

                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.US);
                //set timecompleteval
                try {
                    if (!editETA.getText().toString().isEmpty()){
                        etaVal = timeFormat.parse(editETA.getText().toString().trim());
                    }
                    if("Success".equals(status) || "Failed".equals(status)) {
                        String now = timeFormat.format(new Date());
                        timeCompletionVal = timeFormat.parse(now);
                        editTimeComplete.setText(now);
                    }
                } catch (ParseException e) {
                    Toast.makeText(StopDetails.this, "Invalid time format", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    return;
                }

                //get signature
                byte[] signatureBytes = null;
                if(!signaturePad.isEmpty()) {
                    Bitmap signatureBitmap = signaturePad.getSignatureBitmap();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    signatureBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    signatureBytes = outputStream.toByteArray();
                }

                //get signature or photo if it exists in db already
                Stop existingStop = (Stop) getIntent().getSerializableExtra("stop");
                if(signatureBytes == null && existingStop != null) {
                    signatureBytes= existingStop.getSignature();
                }


                Stop stop = new Stop(
                        newStopID,
                        addressVal,
                        status,
                        timeCompletionVal,
                        etaVal,
                        reason,
                        deliveryIDVal,
                        signatureBytes,
                        routeID
                    );
                if (stopID == -1) {
                    stopViewModel.insert(stop);
                    Toast.makeText(StopDetails.this, "Stop Saved", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    stopViewModel.update(stop);
                    Toast.makeText(StopDetails.this, "Stop Updated", Toast.LENGTH_SHORT).show();
                    finish();
                }
                }
        });
        //delete button functionality
        ImageButton deleteStopButton = findViewById(R.id.stopdetailsdeletebutton);
        deleteStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stopID != -1) {
                    new AlertDialog.Builder(StopDetails.this)
                            .setTitle("Delete Stop")
                            .setMessage("Are you sure you want to delete this stop?")
                            .setPositiveButton("Delete", ((dialog, which) -> {
                                Date etaVal = null;
                                Date timeCompletionVal = null;

                                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.US);
                                try {
                                    etaVal = timeFormat.parse(editETA.getText().toString().trim());
                                }
                                catch (ParseException e) {
                                    editETA.setError("Invalid time format");
                                }
                                try {
                                    timeCompletionVal = timeFormat.parse(editTimeComplete.getText().toString().trim());
                                }
                                catch (ParseException e) {
                                    editETA.setError("Invalid time format");
                                }
                                Stop stopToDelete = new Stop(stopID,
                                        address,
                                        status,
                                        timeCompletionVal,
                                        etaVal,
                                        reason,
                                        deliveryID,
                                        null,
                                        routeID);
                                stopViewModel.delete(stopToDelete);
                                Toast.makeText(StopDetails.this, "Stop deleted", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(StopDetails.this, RouteDetails.class));
                                finish();

                            }))
                            .setNegativeButton("Cancel", null)
                            .show();
                }

            }
        });

    }

    //reset status buttons when no status selected
    private void resetStatusButtonTint(){
        btnPending.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.bluegray));
        btnSuccess.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.bluegray));
        btnFailed.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.bluegray));
    }
}