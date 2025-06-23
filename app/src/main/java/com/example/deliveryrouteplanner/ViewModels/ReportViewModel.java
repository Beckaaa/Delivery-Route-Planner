package com.example.deliveryrouteplanner.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.deliveryrouteplanner.Database.Repository;
import com.example.deliveryrouteplanner.Entities.Report;
import com.example.deliveryrouteplanner.Entities.Stop;

import java.util.List;

public class ReportViewModel extends AndroidViewModel {
    private final Repository repository;
    public ReportViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public LiveData<List<Report>> getAllReports(String userID) {
        return repository.getmAllReports(userID);
    }
    public void insert(Report report) {
        repository.insert(report);
    }
    public void update(Report report) {
        repository.update(report);
    }
    public void delete(Report report) {
        repository.delete(report);
    }
}
