package com.example.deliveryrouteplanner.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.deliveryrouteplanner.DAO.ReportDao;
import com.example.deliveryrouteplanner.DAO.RouteDao;
import com.example.deliveryrouteplanner.DAO.StopDao;
import com.example.deliveryrouteplanner.Entities.Report;
import com.example.deliveryrouteplanner.Entities.Route;
import com.example.deliveryrouteplanner.Entities.Stop;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private RouteDao mRouteDao;
    private StopDao mStopDao;
    private ReportDao mReportDao;
    private LiveData<List<Route>> mAllRoutes;
    private LiveData<List <Stop>> mAllStops;
    private LiveData<List <Report>> mAllReports;
    private LiveData<List <Stop>> mAssociatedStops;


    private static int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        //get database instance if exists, else trigger to create new instance to build database
        RoutePlannerDatabaseBuilder db = RoutePlannerDatabaseBuilder.getDatabase(application);
        mRouteDao = db.routeDao();
        mStopDao = db.stopDao();
        mReportDao = db.reportDao();
    }

    //db query all routes
    public LiveData<List<Route>> getmAllRoutes() {
        databaseExecutor.execute(() -> {
            mAllRoutes = mRouteDao.getAllRoutes();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllRoutes;
    }
    //db query all stops
    public LiveData<List<Stop>> getmAllStops() {
        databaseExecutor.execute(()-> {
            mAllStops= mStopDao.getAllStops();
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllStops;
    }
    //db query all reports
    public LiveData<List<Report>> getmAllReports() {
        databaseExecutor.execute(()-> {
            mAllReports= mReportDao.getAllReports();
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllReports;
    }
    //db query associated stops
    public LiveData<List<Stop>> getmAssociatedStops(int routeID) {
        databaseExecutor.execute(()-> {
            mAssociatedStops= mStopDao.getAssociatedStops(routeID);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAssociatedStops;
    }
    //db executor for insert update delete routes
    public void insert(Route route){
        databaseExecutor.execute(()-> {
            mRouteDao.insert(route);
        });
    }
    public void update(Route route){
        databaseExecutor.execute(()-> {
            mRouteDao.update(route);
        });
    }
    public void delete(Route route){
        databaseExecutor.execute(()-> {
            mRouteDao.delete(route);
        });
    }
    //db executor for insert update delete stops
    public void insert(Stop stop){
        databaseExecutor.execute(()-> {
            mStopDao.insert(stop);
        });
    }
    public void update(Stop stop){
        databaseExecutor.execute(()-> {
            mStopDao.update(stop);
        });
    }
    public void delete(Stop stop){
        databaseExecutor.execute(()-> {
            mStopDao.delete(stop);
        });
    }
    //db executor for insert update delete reports
    public void insert(Report report){
        databaseExecutor.execute(()-> {
            mReportDao.insert(report);
        });
    }
    public void update(Report report){
        databaseExecutor.execute(()-> {
            mReportDao.update(report);
        });
    }
    public void delete(Report report){
        databaseExecutor.execute(()-> {
            mReportDao.delete(report);
        });
    }

    public void deactivateAllRoutes() {
        databaseExecutor.execute(()-> mRouteDao.deactivateAllRoutes());
    }
    public LiveData<Route> getActiveRoute(){
        return mRouteDao.getActiveRoute();
    }
}
