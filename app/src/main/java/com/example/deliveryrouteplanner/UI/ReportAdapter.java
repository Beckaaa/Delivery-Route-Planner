package com.example.deliveryrouteplanner.UI;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryrouteplanner.Entities.Report;
import com.example.deliveryrouteplanner.R;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {
    private List<Report> reports = new ArrayList<>();
    private List<Report> allReports = new ArrayList<>();
    private final LayoutInflater mInflater;
    private final Context context;
    public ReportAdapter (Context context) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public static class ReportViewHolder extends RecyclerView.ViewHolder {
        TextView reportId, reportDate, pdfPath;
        Button shareReportButton;
        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            reportId= itemView.findViewById(R.id.textViewReportListId);
            reportDate = itemView.findViewById(R.id.textViewReportListDate);
            pdfPath = itemView.findViewById(R.id.textViewReportListPdfPath);
            shareReportButton = itemView.findViewById(R.id.shareReportButton);
        }
    }

    @NonNull
    @Override
    public ReportAdapter.ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.report_list_item, parent, false);
        return new ReportViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        Report report = reports.get(position);
        holder.reportId.setText("Report ID: " + report.getReportID());

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.US);
        holder.reportDate.setText("Generated: " + sdf.format(report.getDateGenerated()));
        holder.pdfPath.setText("Storage Path: " + report.getPdfPath());

        holder.shareReportButton.setOnClickListener(v -> {
            File pdfFile = new File(report.getPdfPath());
            if (pdfFile.exists()) {
                Uri pdfUri = FileProvider.getUriForFile(
                        context, context.getPackageName() + ".provider", pdfFile);

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("application/pdf");
                shareIntent.putExtra(Intent.EXTRA_STREAM, pdfUri);
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                context.startActivity(Intent.createChooser(shareIntent, "Share Report PDF"));
            }
            else {
                Toast.makeText(context, "PDF file not found", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
        this.allReports = new ArrayList<>(reports);
        notifyDataSetChanged();
    }

}
