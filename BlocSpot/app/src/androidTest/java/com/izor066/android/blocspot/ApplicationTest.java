package com.izor066.android.blocspot;

import android.test.ApplicationTestCase;
import android.test.RenamingDelegatingContext;

import com.izor066.android.blocspot.api.model.PointOfInterest;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<BlocSpotApplication> {
    public ApplicationTest() {
        super(BlocSpotApplication.class);
    }

    public void testApplicationHasDataSource() {
        setContext(new RenamingDelegatingContext(getContext(), "test_"));

        createApplication();

        BlocSpotApplication application = getApplication();

        application.onCreate();

        assertNotNull(application.getDataSource());

    }

    public void testDataBaseInsert () {
        setContext(new RenamingDelegatingContext(getContext(), "test_"));

        createApplication();

        BlocSpotApplication application = getApplication();

        application.onCreate();

        assertNotNull(application.getDataSource());

        PointOfInterest testPointOfInterest = new PointOfInterest("Google", "10 Brock St\n" + "Kings Cross, London NW1 3FG\n" + "United Kingdom", 51.5261296f, -0.1394121f);
        application.getDataSource().insertPointToDatabase(testPointOfInterest);

        PointOfInterest testPointOfInterestDB = application.getDataSource().getPOIfromDBwithTitle(testPointOfInterest.getTitle());

        assertEquals("Title NOT the same!", testPointOfInterest.getTitle(), testPointOfInterestDB.getTitle());
        assertEquals("Address NOT the same!", testPointOfInterest.getAddress(), testPointOfInterestDB.getAddress());
        assertEquals("Latitude NOT the same!", testPointOfInterest.getLatitude(), testPointOfInterestDB.getLatitude());
        assertEquals("Longitude NOT the same!", testPointOfInterest.getLongitude(), testPointOfInterestDB.getLongitude());
    }
}
