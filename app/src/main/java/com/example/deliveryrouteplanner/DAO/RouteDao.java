package com.example.deliveryrouteplanner.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.deliveryrouteplanner.Entities.Route;

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

    @Query("SELECT * FROM ROUTES ORDER BY routeID ASC")
    LiveData<List<Route>> getAllRoutes();
}
