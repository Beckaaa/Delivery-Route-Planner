package com.example.deliveryrouteplanner.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.deliveryrouteplanner.Entities.Route;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface RouteDao {
    //CRUD operations
    @Insert(onConflict= OnConflictStrategy.IGNORE)
    void insert(Route route);

    @Update
    void update(Route route);

    @Delete
    void delete(Route route);

    @Query("SELECT * FROM ROUTES WHERE userID = :userID ORDER BY routeID ASC")
    LiveData<List<Route>> getAllRoutes(String userID);

    @Query("SELECT * FROM ROUTES WHERE isActive = 1 AND userID = :userID LIMIT 1")
    LiveData<Route> getActiveRoute(String userID);

    //List<Route> cachedRoutes = new ArrayList<>();

    @Query("UPDATE ROUTES SET isActive = 0 WHERE userID = :userID")
    void deactivateAllRoutes(String userID);

}
