package com.example.deliveryrouteplanner.ViewModels;

import com.example.deliveryrouteplanner.Entities.Route;

public class RouteValidator {
    //checks string value is not empty or null
    public static boolean isNonEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    //checks if route is null or get userID is null or empty
    public static boolean isValidRoute(Route route) {
        if (route == null) return false;
        if (route.getUserID() == null || route.getUserID().trim().isEmpty()) return false;
        return true;
    }
}
