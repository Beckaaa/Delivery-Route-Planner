package com.example.deliveryrouteplanner.UI;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.deliveryrouteplanner.R;
import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class StopDetails extends AppCompatActivity {
    ImageButton btnSuccess;
    ImageButton btnPending;
    ImageButton btnFailed;
    String status;
    String address;
    EditText editAddress;
    String estArrival;
    EditText editETA;
    EditText editBarcode;
    String barcode;
    ImageButton clearSignature;
    EditText editTimeComplete;
    String timeComplete;


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
        editBarcode = findViewById(R.id.editTextBarcode);
        editETA = findViewById(R.id.editTextStopETA);
        status = getIntent().getStringExtra("status");
        address = getIntent().getStringExtra("address");
        barcode = getIntent().getStringExtra("barcode");
        estArrival = getIntent().getStringExtra("estArrival");
        timeComplete = getIntent().getStringExtra("timeComplete");

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


//TODO: make barcode button functional to scan a barcode option

//TODO: make camera button functional for photo confirm

//TODO: set up save and delete buttons functionality

//TODO: make timestamp fill when selecting status successful or failed when saving

// TODO: both insert and update for save button needs this included to save the signature:
//        if(!signaturePad.isEmpty()) {
//            Bitmap signatureBitmap = signaturePad.getSignatureBitmap();
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            signatureBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
//            byte[] signatureBytes = outputStream.toByteArray();
//            stop.setSignature(signatureBytes);
//        }




    }

    //reset status buttons when no status selected
    private void resetStatusButtonTint(){
        btnPending.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.yellow));
        btnSuccess.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.bluegray));
        btnFailed.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.bluegray));
    }
}