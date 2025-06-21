package com.example.deliveryrouteplanner.Entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity (tableName = "stops")
public class Stop implements Serializable {
    @PrimaryKey (autoGenerate = true)
    private int stopID;
    private String address;
    private String status;
    private Date timestamp;
    private Date estArrival;
    private String deliveryID;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] signature;
    private int routeID;
    private String reason;

    @ColumnInfo(name = "userID")
    private String userID;


    //constructor

    public Stop (int stopID, String address, String status, Date timestamp,Date estArrival, String deliveryID, String reason, byte[] signature, int routeID, String userID) {
        this.stopID = stopID;
        this.address = address;
        this.status = status;
        this.timestamp = timestamp;
        this.estArrival = estArrival;
        this.reason = reason;
        this.deliveryID = deliveryID;
        this.signature = signature;
        this.routeID = routeID;
        this.userID = userID;
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


    public String getDeliveryID() {
        return deliveryID;
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

    public void setDeliveryID(String deliveryID) {
        this.deliveryID = deliveryID;
    }

    public String getReason() {
        return reason;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
