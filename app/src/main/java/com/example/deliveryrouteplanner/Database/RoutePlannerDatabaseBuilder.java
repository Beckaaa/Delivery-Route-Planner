package com.example.deliveryrouteplanner.Database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.deliveryrouteplanner.DAO.ReportDao;
import com.example.deliveryrouteplanner.DAO.RouteDao;
import com.example.deliveryrouteplanner.DAO.StopDao;
import com.example.deliveryrouteplanner.Entities.Report;
import com.example.deliveryrouteplanner.Entities.Route;
import com.example.deliveryrouteplanner.Entities.Stop;

@Database(entities = {Route.class, Stop.class, Report.class}, version= 1, exportSchema = false)
public abstract class RoutePlannerDatabaseBuilder extends RoomDatabase {
    public abstract RouteDao routeDao();
    public abstract StopDao stopDao();
    public abstract ReportDao reportDao();

    private static volatile RoutePlannerDatabaseBuilder INSTANCE;
    //start database
    static RoutePlannerDatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoutePlannerDatabaseBuilder.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), RoutePlannerDatabaseBuilder.class, "MyDeliveryRoutePlanner.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
