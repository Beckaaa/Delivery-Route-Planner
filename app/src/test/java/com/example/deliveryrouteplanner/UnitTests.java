package com.example.deliveryrouteplanner;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.deliveryrouteplanner.Entities.Route;
import com.example.deliveryrouteplanner.ViewModels.RouteValidator;

import java.util.Date;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTests {
    Route routeTest;
    // distance is a positive value
    @Test
    public void testValidDistance() {
        routeTest = new Route (
                1,
                new java.util.Date(),
                "start",
                "end",
                3,
                10,
                true,
                "user123"
        );
        int result = routeTest.getTotalDistance();
        assertEquals(10, result);
    }

    //empty string not allowed for location
    @Test
    public void testEmptyStartLocationFails() {
        routeTest = new Route (
                1,
                new java.util.Date(),
                "",
                "end",
                3,
                10,
                true,
                "user123"
        );
        assertFalse(RouteValidator.isNonEmpty(""));
    }
    //null value not allowed for location
    @Test
    public void testNullLocationFails() {
        assertFalse(RouteValidator.isNonEmpty(null));
    }
    // userID required to save a route
    @Test
    public void testRouteInvalidWithoutUserID () {
        routeTest = new Route(
                1,
                new java.util.Date(),
                "start",
                "end",
                3,
                10,
                true,
                null
        );
        assertFalse(RouteValidator.isValidRoute(routeTest));
    }

    @Test
    public void testRouteIsValidUserID () {

        routeTest = new Route(
                1,
                new java.util.Date(),
                "start",
                "end",
                3,
                10,
                true,
                "user123"
        );
        assertNotNull(routeTest.getUserID());

    }

}
