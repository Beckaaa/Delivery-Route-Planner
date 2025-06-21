package com.example.deliveryrouteplanner.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.deliveryrouteplanner.Entities.Stop;

import java.util.List;

@Dao
public interface StopDao {
    //CRUD operations
    @Insert(onConflict= OnConflictStrategy.IGNORE)
    void insert(Stop stop);

    @Update
    void update(Stop stop);

    @Delete
    void delete(Stop stop);

    @Query("SELECT * FROM STOPS WHERE userID = :userID ORDER BY stopID ASC")
    LiveData<List<Stop>> getAllStops(String userID);

    //query for stops on selected route
    @Query("SELECT * FROM STOPS WHERE routeID = :routeID AND userID = :userID ORDER BY stopID ASC")
    LiveData<List<Stop>> getAssociatedStops(int routeID, String userID);

    @Query("SELECT * FROM STOPS WHERE stopID = :stopID AND userID = :userID LIMIT 1")
    LiveData<Stop> getStopById(int stopID, String userID);
}
