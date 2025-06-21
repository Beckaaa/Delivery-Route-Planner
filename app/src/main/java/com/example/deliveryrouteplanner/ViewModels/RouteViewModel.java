package com.example.deliveryrouteplanner.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.deliveryrouteplanner.Database.Repository;
import com.example.deliveryrouteplanner.Entities.Route;
import com.example.deliveryrouteplanner.R;

import java.util.List;

public class RouteViewModel extends AndroidViewModel {
    private final Repository repository;
    public RouteViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public LiveData<List<Route>> getAllRoutes(String userID) {
        return repository.getmAllRoutes(userID);
    }
    public void insert(Route route) {
        repository.insert(route);
    }
    public void delete(Route route) {
        repository.delete(route);
    }
    public void update(Route route){
        repository.update(route);
    }
    public void deactivateAllRoutes(String userID){
        repository.deactivateAllRoutes(userID);
    }

    public LiveData<Route> getActiveRoute(String userID) {
        return repository.getActiveRoute(userID);
    }

}
