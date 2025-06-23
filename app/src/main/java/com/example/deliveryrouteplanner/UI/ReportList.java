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

import com.example.deliveryrouteplanner.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ReportList extends AppCompatActivity {

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





    }

}