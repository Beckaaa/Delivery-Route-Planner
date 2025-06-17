package com.example.deliveryrouteplanner.Database;

import android.app.Application;

import com.example.deliveryrouteplanner.DAO.ReportDao;
import com.example.deliveryrouteplanner.DAO.RouteDao;
import com.example.deliveryrouteplanner.DAO.StopDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private RouteDao mRouteDao;
    private StopDao mStopDao;
    private ReportDao mReportDao;

    private static int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        //get database instance if exists, else trigger to create new instance to build database
        RoutePlannerDatabaseBuilder db = RoutePlannerDatabaseBuilder.getDatabase(application);
        mRouteDao = db.routeDao();
        mStopDao = db.stopDao();
        mReportDao = db.reportDao();
    }

    //TODO:db query all routes
    //TODO:db query all stops
    //TODO:db query all reports
    //TODO:db query associated stops
    //TODO:db executor for insert update delete routes
    //TODO:db executor for insert update delete stops
    //TODO:db executor for insert update delete reports

}
