package com.example.deliveryrouteplanner.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity (tableName = "reports")
public class Report {
    @PrimaryKey
    private String reportID;
    private Date dateGenerated;
    private String routeID;
    private String pdfPath;

    //constructor

    public Report(String reportID, Date dateGenerated, String routeID, String pdfPath) {
        this.reportID = reportID;
        this.dateGenerated = dateGenerated;
        this.routeID = routeID;
        this.pdfPath = pdfPath;
    }


    //getters and setters

    public String getReportID() {
        return reportID;
    }

    public void setReportID(String reportID) {
        this.reportID = reportID;
    }

    public Date getDateGenerated() {
        return dateGenerated;
    }

    public void setDateGenerated(Date dateGenerated) {
        this.dateGenerated = dateGenerated;
    }

    public String getRouteID() {
        return routeID;
    }

    public void setRouteID(String routeID) {
        this.routeID = routeID;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }
}
