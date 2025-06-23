package com.example.deliveryrouteplanner.UI;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryrouteplanner.Database.Repository;
import com.example.deliveryrouteplanner.Entities.Report;
import com.example.deliveryrouteplanner.Entities.Route;
import com.example.deliveryrouteplanner.Entities.Stop;
import com.example.deliveryrouteplanner.R;
import com.example.deliveryrouteplanner.ViewModels.ReportViewModel;
import com.example.deliveryrouteplanner.ViewModels.RouteViewModel;
import com.example.deliveryrouteplanner.ViewModels.StopViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class ReportDetails extends AppCompatActivity {
    
    private ReportViewModel reportViewModel;
    private StopViewModel stopViewModel;
    private RouteViewModel routeViewModel;
    private RouteSelectionAdapter routeSelectionAdapter;
    private Repository repository;
    String userID;

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
        

        SharedPreferences sharedPref = ReportDetails.this.getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        userID = sharedPref.getString("uid", null);

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

        repository = new Repository(getApplication());

    }

    private List<Route> getSelectedRoutes() {
        List<Route> allRoutes = routeSelectionAdapter.getRouteList();
        Set<Integer> selectedIds = routeSelectionAdapter.getSelectedRouteIds();
        List<Route> selectedRoutes = new ArrayList<>();
        for (Route route : allRoutes){
            if(selectedIds.contains(route.getRouteID())) {
                selectedRoutes.add(route);
            }
        }
        return selectedRoutes;
    }

    private void generateReport() {
        List<Route> selectedRoutes = getSelectedRoutes();
        Set<Integer> selectedRouteIds = routeSelectionAdapter.getSelectedRouteIds();
        if (selectedRouteIds.isEmpty()) {
            Toast.makeText(this, "Please select at least one route.", Toast.LENGTH_SHORT).show();
            return;
        }

        //TODO: need to put in logic for generate PDF- toast is for testing button and selection count
        new Thread(()-> {
           Map<Integer, List<Stop>> routeStopsMap = new HashMap<>();
           for (Route route : selectedRoutes) {
               List<Stop> stops = repository.getAssociatedStopsSync(route.getRouteID(), userID);
               routeStopsMap.put(route.getRouteID(), stops);
           }
           runOnUiThread(()-> {
               generatePdfReport(selectedRoutes, routeStopsMap, userID);
           });
        }).start();
    }

    private void generatePdfReport (List<Route> routes, Map<Integer, List<Stop>> routeStopsMap, String userID) {
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        int pageNumber = 1;
        //Standard A4 page size
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, pageNumber).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        //top margin
        int y = 50;
        //bold and larger font title
        paint.setTextSize(20);
        paint.setFakeBoldText(true);
        canvas.drawText("Delivery Report", 200, y, paint);

        y+= 30;
        // smaller text, not bold
        paint.setTextSize(16);
        paint.setFakeBoldText(false);

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.US);
        canvas.drawText("Generated on: " + sdf.format(new Date()), 50, y, paint);
        y+=30;

        //route details adding
        for (Route route : routes) {
            //start a new page if y is nearing height limit of 842 to prevent running out of space
            if (y > 800) {
                pdfDocument.finishPage(page);
                pageNumber ++;
                pageInfo = new PdfDocument.PageInfo.Builder(595, 842, pageNumber).create();
                page = pdfDocument.startPage(pageInfo);
                canvas = page.getCanvas();
                y=50;
            }
            paint.setTextSize(16);
            //add route details
            canvas.drawText("Route #" + route.getRouteID(), 50, y, paint);
            y +=20;
            canvas.drawText("From: " + route.getStartLocation(), 50, y, paint);
            y +=20;
            canvas.drawText("To: " + route.getEndLocation(), 50, y, paint);
            y +=20;
            canvas.drawText("Number of Stops: " + route.getStopCount()
                    + "| Distance: " + route.getTotalDistance() + " miles"
                    ,50, y, paint);
            y +=20;

            //find the stop details for stops on the route to add to pdf
            List<Stop> stops = routeStopsMap.get(route.getRouteID());

            if (stops != null) {
                for (Stop stop : stops) {
                    if (y > 800) {
                        pdfDocument.finishPage(page);
                        pageNumber++;
                        pageInfo = new PdfDocument.PageInfo.Builder(595, 842, pageNumber).create();
                        page = pdfDocument.startPage(pageInfo);
                        canvas = page.getCanvas();
                        y = 50;
                    }
                    paint.setTextSize(12);
                    // shift text a bit for stops to separate from routes easily
                    canvas.drawText("- Stop ID: " + stop.getStopID()
                            + "| Address: " + stop.getAddress(), 70, y, paint);
                    y +=20;
                    canvas.drawText("  Delivery ID: " + stop.getDeliveryID()
                            + "| Notes: " + stop.getReason(), 70, y, paint);
                    y +=20;

                    canvas.drawText("  Signature: " , 70, y, paint);
                    y +=20;
                    byte[] signatureBytes = stop.getSignature();
                    if (signatureBytes != null && signatureBytes.length > 0) {
                        Bitmap signatureBitmap = BitmapFactory.decodeByteArray(signatureBytes, 0, signatureBytes.length);
                        if (signatureBitmap != null) {
                            Bitmap scaledSignature = Bitmap.createScaledBitmap(signatureBitmap, 200, 80, false);
                            canvas.drawBitmap(scaledSignature, 70, y, paint);
                            y +=120;
                        }
                    }
                }
            }
            //space between route/stop details
            y+= 20;
        }
        pdfDocument.finishPage(page);

        //save pdf to local storage
        String fileName = "delivery_report_" + System.currentTimeMillis() + ".pdf";
        File filePath = new File(getExternalFilesDir(null), fileName);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            pdfDocument.writeTo(fileOutputStream);
            pdfDocument.close();

            //save report to database
            Report report = new Report(
                    0, new Date(), -1, filePath.getAbsolutePath(), userID
            );

            new ViewModelProvider(this).get(ReportViewModel.class).insert(report);

            Toast.makeText(this, "Report saved to: " + filePath.getAbsolutePath(), Toast.LENGTH_LONG).show();
            //share method called
            sharePdf(filePath);
        }
        catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save pdf: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void sharePdf(File pdfFile) {
        Uri pdfUri = FileProvider.getUriForFile(
                this, getPackageName() + ".provider", pdfFile);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_STREAM, pdfUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(intent, "Share PDF"));
    }


}