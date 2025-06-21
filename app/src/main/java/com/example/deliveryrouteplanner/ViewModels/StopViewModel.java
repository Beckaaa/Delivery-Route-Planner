package com.example.deliveryrouteplanner.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.deliveryrouteplanner.Database.Repository;
import com.example.deliveryrouteplanner.Entities.Stop;

import java.util.List;

public class StopViewModel extends AndroidViewModel {
    private final Repository repository;
    public StopViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public LiveData<List<Stop>> getAllStops(String userID) {
        return repository.getmAllStops(userID);
    }
    public LiveData<List<Stop>> getAssociatedStops(int routeID, String userID) {
        return repository.getmAssociatedStops(routeID, userID);
    }
    public void insert(Stop stop) {
        repository.insert(stop);
    }
    public void update(Stop stop) {
        repository.update(stop);
    }
    public void delete(Stop stop) {
        repository.delete(stop);
    }
}
