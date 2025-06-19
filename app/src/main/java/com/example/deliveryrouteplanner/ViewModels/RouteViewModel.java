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
    private final LiveData<List<Route>> allRoutes;
    public RouteViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allRoutes = repository.getmAllRoutes();
    }

    public LiveData<List<Route>> getAllRoutes() {
        return allRoutes;
    }
    public void insert(Route route) {
        repository.insert(route);
    }
    public void delete(Route route) {
        repository.delete(route);
    }
}
