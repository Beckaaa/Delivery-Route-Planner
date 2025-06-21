package com.example.deliveryrouteplanner.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.deliveryrouteplanner.Entities.Report;


import java.util.List;

@Dao
public interface ReportDao {
    //CRUD operations
    @Insert(onConflict= OnConflictStrategy.IGNORE)
    void insert(Report report);

    @Update
    void update(Report report);

    @Delete
    void delete(Report report);

    @Query("SELECT * FROM REPORTS WHERE userID = :userID ORDER BY reportID ASC")
    LiveData<List<Report>> getAllReports(String userID);
}
