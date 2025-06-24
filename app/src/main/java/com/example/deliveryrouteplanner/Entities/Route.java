package com.example.deliveryrouteplanner.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity (tableName = "routes")
public class Route {
    @PrimaryKey (autoGenerate = true)
    private int routeID;
    private Date date;
    private String startLocation;
    private String endLocation;
    private int totalDistance;
    private int stopCount;
    private boolean isActive;
    @ColumnInfo(name = "userID")
    private String userID;

    //constructor
    public Route(int routeID, Date date, String startLocation, String endLocation, int stopCount, int totalDistance, boolean isActive, String userID) {
        this.routeID = routeID;
        this.date = date;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.stopCount = stopCount;
        this.totalDistance = totalDistance;
        this.isActive = isActive;
        this.userID = userID;
    }
    //getters and setters

    public int getRouteID() {
        return routeID;
    }

    public void setRouteID(int routeID) {
        this.routeID = routeID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public int getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(int totalDistance) {
        this.totalDistance = totalDistance;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getStopCount() {
        return stopCount;
    }

    public void setStopCount(int stopCount) {
        this.stopCount = stopCount;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
