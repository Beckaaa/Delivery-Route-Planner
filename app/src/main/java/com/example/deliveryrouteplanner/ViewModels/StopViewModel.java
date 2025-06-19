package com.example.deliveryrouteplanner.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.deliveryrouteplanner.Database.Repository;
import com.example.deliveryrouteplanner.Entities.Stop;

import java.util.List;

public class StopViewModel {
    private final Repository repository;
    private final LiveData<List<Stop>> allStops;
    public StopViewModel(@NonNull Application application) {
        repository = new Repository(application);
        allStops = repository.getmAllStops();
    }

    public LiveData<List<Stop>> getAllStops() {
        return allStops;
    }
    public void insert(Stop stop) {
        repository.insert(stop);
    }
}
