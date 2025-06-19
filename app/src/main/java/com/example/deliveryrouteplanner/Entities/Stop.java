package com.example.deliveryrouteplanner.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity (tableName = "stops")
public class Stop {
    @PrimaryKey (autoGenerate = true)
    private int stopID;
    private String address;
    private String status;
    private Date timestamp;
    private Date estArrival;
    private String photoPath;
    private String barcode;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] signature;
    private int routeID;

    //constructor

    public Stop(int stopID, String address, String status, Date timestamp,Date estArrival, String photoPath, String barcode, byte[] signature, int routeID) {
        this.stopID = stopID;
        this.address = address;
        this.status = status;
        this.timestamp = timestamp;
        this.estArrival = estArrival;
        this.photoPath = photoPath;
        this.barcode = barcode;
        this.signature = signature;
        this.routeID = routeID;
    }


    //getters and setters


    public int getStopID() {
        return stopID;
    }

    public void setStopID(int stopID) {
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

    public Date getEstArrival() {
        return estArrival;
    }

    public void setEstArrival(Date estArrival) {
        this.estArrival = estArrival;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public int getRouteID() {
        return routeID;
    }

    public void setRouteID(int routeID) {
        this.routeID = routeID;
    }
}
