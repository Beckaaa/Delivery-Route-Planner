package com.example.deliveryrouteplanner.DAO;

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

    @Query("SELECT * FROM STOPS ORDER BY stopID ASC")
    List<Stop> getAllStops();

    //query for stops on selected route
    @Query("SELECT * FROM STOPS WHERE routeID = :rouID ORDER BY stopID ASC")
    List <Stop> getAssociatedStops(String rouID);
}
