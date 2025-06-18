package com.example.deliveryrouteplanner.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity (tableName = "reports")
public class Report {
    @PrimaryKey (autoGenerate = true)
    private int reportID;
    private Date dateGenerated;
    private int routeID;
    private String pdfPath;

    //constructor

    public Report(int reportID, Date dateGenerated, int routeID, String pdfPath) {
        this.reportID = reportID;
        this.dateGenerated = dateGenerated;
        this.routeID = routeID;
        this.pdfPath = pdfPath;
    }


    //getters and setters

    public int getReportID() {
        return reportID;
    }

    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    public Date getDateGenerated() {
        return dateGenerated;
    }

    public void setDateGenerated(Date dateGenerated) {
        this.dateGenerated = dateGenerated;
    }

    public int getRouteID() {
        return routeID;
    }

    public void setRouteID(int routeID) {
        this.routeID = routeID;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }
}
