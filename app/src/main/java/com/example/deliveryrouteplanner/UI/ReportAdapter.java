package com.example.deliveryrouteplanner.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryrouteplanner.Entities.Report;
import com.example.deliveryrouteplanner.R;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

    private List<Report> reports = new ArrayList<>();
    private final LayoutInflater mInflater;

    public ReportAdapter (Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public static class ReportViewHolder extends RecyclerView.ViewHolder {
        TextView reportId, reportDate, pdfPath;
        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            reportId= itemView.findViewById(R.id.textViewReportListId);
            reportDate = itemView.findViewById(R.id.textViewReportListDate);
            pdfPath = itemView.findViewById(R.id.textViewReportListPdfPath);
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

    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
        notifyDataSetChanged();
    }



}
