package com.example.deliveryrouteplanner.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity (tableName = "stops")
public class Stop {
    @PrimaryKey
    private String stopID;
    private String address;
    private String status;
    private Date timestamp;
    private String photoPath;
    private String barcode;
    private String signaturePath;
    private String routeID;

    //constructor

    public Stop(String stopID, String address, String status, Date timestamp, String photoPath, String barcode, String signaturePath, String routeID) {
        this.stopID = stopID;
        this.address = address;
        this.status = status;
        this.timestamp = timestamp;
        this.photoPath = photoPath;
        this.barcode = barcode;
        this.signaturePath = signaturePath;
        this.routeID = routeID;
    }


    //getters and setters


    public String getStopID() {
        return stopID;
    }

    public void setStopID(String stopID) {
        this.stopID = stopID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getSignaturePath() {
        return signaturePath;
    }

    public void setSignaturePath(String signaturePath) {
        this.signaturePath = signaturePath;
    }

    public String getRouteID() {
        return routeID;
    }

    public void setRouteID(String routeID) {
        this.routeID = routeID;
    }
}
