package com.izor066.android.blocspot;

import android.test.ApplicationTestCase;
import android.test.RenamingDelegatingContext;

import com.izor066.android.blocspot.api.model.Category;
import com.izor066.android.blocspot.api.model.PointOfInterest;

import java.util.List;

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

   public void testPOIDataBaseInsert () {
        setContext(new RenamingDelegatingContext(getContext(), "test_"));

        createApplication();

        BlocSpotApplication application = getApplication();

        application.onCreate();

        assertNotNull(application.getDataSource());

        PointOfInterest testPointOfInterest = new PointOfInterest(0l, "TestEntry", "10 Brock St, Kings Cross, London NW1 3FG, United Kingdom", 51.5261296f, -0.1394121f, "Unsorted", "notSetYet");
        application.getDataSource().insertPointToDatabase(testPointOfInterest);


        PointOfInterest testPointOfInterestDB = application.getDataSource().getPOIfromDBwithTitle(testPointOfInterest.getTitle());

        assertEquals("Title NOT the same!", testPointOfInterest.getTitle(), testPointOfInterestDB.getTitle());
        assertEquals("Address NOT the same!", testPointOfInterest.getAddress(), testPointOfInterestDB.getAddress());
        assertEquals("Latitude NOT the same!", testPointOfInterest.getLatitude(), testPointOfInterestDB.getLatitude());
        assertEquals("Longitude NOT the same!", testPointOfInterest.getLongitude(), testPointOfInterestDB.getLongitude());
        assertEquals("Note not the same", testPointOfInterest.getPoiNote(), testPointOfInterestDB.getPoiNote());

    }

//    public void testPOInoteDefaultValue() {
//        setContext(new RenamingDelegatingContext(getContext(), "test_"));
//
//        createApplication();
//
//        BlocSpotApplication application = getApplication();
//
//        application.onCreate();
//
//        assertNotNull(application.getDataSource());
//
//        PointOfInterest testPointOfInterest = new PointOfInterest(0l, "TestEntry", "10 Brock St, Kings Cross, London NW1 3FG, United Kingdom", 51.5261296f, -0.1394121f, "Unsorted", null);
//        application.getDataSource().insertPointToDatabase(testPointOfInterest);
//
//
//        PointOfInterest testPointOfInterestDB = application.getDataSource().getPOIfromDBwithTitle(testPointOfInterest.getTitle());
//
//        assertEquals("Default value", "No note yet", testPointOfInterestDB.getPoiNote());
//
//    }

    public void testCategoryDataBaseInsert () {
        setContext(new RenamingDelegatingContext(getContext(), "test_"));

        createApplication();

        BlocSpotApplication application = getApplication();

        application.onCreate();

        assertNotNull(application.getDataSource());

        assertNotNull("Table not found!", application.getDataSource().getAllCategories());

        Category testCategory = new Category("DemoTest", 000000);
        application.getDataSource().insertCategoryToDatabase(testCategory);

        Category testCategoryDB = application.getDataSource().getCategoryFromDBWithCategoryName(testCategory.getCategoryName());

        assertEquals("Category Name NOT the same!", testCategory.getCategoryName(), testCategoryDB.getCategoryName());
        assertEquals("Color NOT the same!", testCategory.getColor(), testCategoryDB.getColor());

        List<Category> allCategories = application.getDataSource().getAllCategories();
        Category emptyRow = allCategories.get(0);

        assertEquals("Category name default value is wrong!", "Unsorted", emptyRow.getCategoryName());
        assertEquals("Colour default value is wrong!", 0, emptyRow.getColor());


    }
}
