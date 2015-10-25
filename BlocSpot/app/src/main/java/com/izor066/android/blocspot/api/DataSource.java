package com.izor066.android.blocspot.api;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.izor066.android.blocspot.BuildConfig;
import com.izor066.android.blocspot.api.model.Category;
import com.izor066.android.blocspot.api.model.PointOfInterest;
import com.izor066.android.blocspot.api.model.database.DatabaseOpenHelper;
import com.izor066.android.blocspot.api.model.database.table.CategoriesTable;
import com.izor066.android.blocspot.api.model.database.table.PointsOfInterestTable;
import com.izor066.android.blocspot.api.model.database.table.Table;
import com.izor066.android.blocspot.ui.fragment.CategoryDialogFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by igor on 22/9/15.
 */
public class DataSource {

    private DatabaseOpenHelper databaseOpenHelper;
    private PointsOfInterestTable pointsOfInterestTable;
    private CategoriesTable categoriesTable;


    public DataSource(Context context) {
        pointsOfInterestTable = new PointsOfInterestTable();
        categoriesTable = new CategoriesTable();
        databaseOpenHelper = new DatabaseOpenHelper(context, pointsOfInterestTable, categoriesTable);

        if (BuildConfig.DEBUG) {
            context.deleteDatabase(DatabaseOpenHelper.NAME);
            SQLiteDatabase writableDatabase = databaseOpenHelper.getWritableDatabase();
            new PointsOfInterestTable.Builder()
                    .setTitle("Google")
                    .setAddress("1-13 St Giles High St, London WC2H 8LG, United Kingdom")
                    .setLatitude(51.5160563f)
                    .setLongitude(-0.1271485f)
                    .insert(writableDatabase);
            new PointsOfInterestTable.Builder()
                    .setTitle("Facebook London")
                    .setAddress("10 Brock St, Kings Cross, London NW1 3FG, United Kingdom")
                    .setLatitude(51.5261296f)
                    .setLongitude(-0.1394121f)
                    .insert(writableDatabase);
            new PointsOfInterestTable.Builder()
                    .setTitle("Twitter HQ")
                    .setAddress("Air St, Soho, London W1B 5AG, United Kingdom")
                    .setLatitude(51.5104794f)
                    .setLongitude(-0.1366545f)
                    .insert(writableDatabase);
            new PointsOfInterestTable.Builder()
                    .setTitle("SCE London")
                    .setAddress("10-15 Great Marlborough St, London W1F 7HR, United Kingdom")
                    .setLatitude(51.5146954f)
                    .setLongitude(-0.1391727f)
                    .insert(writableDatabase);
            new PointsOfInterestTable.Builder()
                    .setTitle("Apple European HQ")
                    .setAddress("1 Hanover Street, London W1S 1YZ, United Kingdom")
                    .setLatitude(51.513833f)
                    .setLongitude(-0.1420289f)
                    .insert(writableDatabase);
            new PointsOfInterestTable.Builder()
                    .setTitle("Rockstar International")
                    .setAddress("555 King's Rd, London SW6 2EB, United Kingdom")
                    .setLatitude(51.479068f)
                    .setLongitude(-0.1874554f)
                    .insert(writableDatabase);
            new CategoriesTable.Builder()
                    .setCategoryName("Unsorted")
                    .insert(writableDatabase);


        }
    }

    // Insert

    public void insertPointToDatabase(PointOfInterest pointOfInterest) {
        SQLiteDatabase writableDatabase = databaseOpenHelper.getWritableDatabase();
        new PointsOfInterestTable.Builder()
                .setTitle(pointOfInterest.getTitle())
                .setAddress(pointOfInterest.getAddress())
                .setLatitude(pointOfInterest.getLatitude())
                .setLongitude(pointOfInterest.getLongitude())
                .setCategory(pointOfInterest.getPoiCategory())
                .setNote(pointOfInterest.getPoiNote())
                .insert(writableDatabase);
    }

    public void insertCategoryToDatabase(Category category) {
        SQLiteDatabase writableDatabase = databaseOpenHelper.getWritableDatabase();
        new CategoriesTable.Builder()
                .setCategoryName(category.getCategoryName())
                .setColor(category.getColor())
                .insert(writableDatabase);
    }

    public void updatePointOfInterestCategory(PointOfInterest pointOfInterest, String categoryName) {
        SQLiteDatabase writableDatabase = databaseOpenHelper.getWritableDatabase();
        String pointOfInterestTitle = pointOfInterest.getTitle();
        new PointsOfInterestTable.Builder()
                .setCategory(categoryName)
                .updateForTitle(writableDatabase, pointOfInterestTitle);
    }


    // Retrieve PointsOfInterest


    public PointOfInterest getPOIfromDBwithTitle(String title) {
        Cursor cursor = PointsOfInterestTable.getRowFromTitle(databaseOpenHelper.getReadableDatabase(), title);
        cursor.moveToFirst();
        long rowId = Table.getRowId(cursor);
        String address = PointsOfInterestTable.getAddress(cursor);
        float latitude = PointsOfInterestTable.getLatitude(cursor);
        float longitude = PointsOfInterestTable.getLongitude(cursor);
        String poiCategory = PointsOfInterestTable.getPoiCategory(cursor);
        String poiNote = PointsOfInterestTable.getPoiNote(cursor);
        PointOfInterest pointOfInterest = new PointOfInterest(rowId, title, address, latitude, longitude, poiCategory, poiNote);
        cursor.close();
        return pointOfInterest;
    }

    public PointOfInterest getPOIfromDBwithRowId(long rowId) {
        Cursor cursor = PointsOfInterestTable.getRowWithId(databaseOpenHelper.getReadableDatabase(), rowId);
        cursor.moveToFirst();
        String title = PointsOfInterestTable.getTitle(cursor);
        String address = PointsOfInterestTable.getAddress(cursor);
        float latitude = PointsOfInterestTable.getLatitude(cursor);
        float longitude = PointsOfInterestTable.getLongitude(cursor);
        String poiCategory = PointsOfInterestTable.getPoiCategory(cursor);
        String poiNote = PointsOfInterestTable.getPoiNote(cursor);
        PointOfInterest pointOfInterest = new PointOfInterest(rowId, title, address, latitude, longitude, poiCategory, poiNote);
        cursor.close();
        return pointOfInterest;
    }

    public List<PointOfInterest> getAllPointsOfInterest() {
        Cursor cursor = PointsOfInterestTable.fetchAllPointsOfInterest(databaseOpenHelper.getReadableDatabase());
        List<PointOfInterest> allPoints = new ArrayList<PointOfInterest>();
        if (cursor.moveToFirst()) {
            do {
                allPoints.add(pointOfInterestFromCursor(cursor));
            } while (cursor.moveToNext());
            cursor.close();
        }

        return allPoints;

    }

    public List<PointOfInterest> getPointsOfInterestForCategory(String categoryName) {
        if (categoryName.equals(CategoryDialogFragment.ALL_CATEGORIES)) {
            return getAllPointsOfInterest();
        }
        Cursor cursor = PointsOfInterestTable.getAllForCategory(databaseOpenHelper.getReadableDatabase(), categoryName);
        List<PointOfInterest> allPointsForCat = new ArrayList<PointOfInterest>();
        if (cursor.moveToFirst()) {
            do {
                allPointsForCat.add(pointOfInterestFromCursor(cursor));
            } while (cursor.moveToNext());
            cursor.close();
        }

        return allPointsForCat;

    }

    private static PointOfInterest pointOfInterestFromCursor(Cursor cursor) {
        //cursor.moveToFirst();
        long rowId = Table.getRowId(cursor);
        String title = PointsOfInterestTable.getTitle(cursor);
        String address = PointsOfInterestTable.getAddress(cursor);
        float latitude = PointsOfInterestTable.getLatitude(cursor);
        float longitude = PointsOfInterestTable.getLongitude(cursor);
        String poiCategory = PointsOfInterestTable.getPoiCategory(cursor);
        String poiNote = PointsOfInterestTable.getPoiNote(cursor);
        PointOfInterest pointOfInterest = new PointOfInterest(rowId, title, address, latitude, longitude, poiCategory, poiNote);
        //cursor.close();
        return pointOfInterest;
    }

    // Retrieve Categories

    public Category getCategoryFromDBWithCategoryName(String categoryName) {
        Cursor cursor = CategoriesTable.getRowFromCategoryName(databaseOpenHelper.getReadableDatabase(), categoryName);
        cursor.moveToFirst();
        Category category = categoryFromCursor(cursor);
        cursor.close();
        return category;
    }

    public List<Category> getAllCategories() {
        Cursor cursor = CategoriesTable.getAllCategories(databaseOpenHelper.getReadableDatabase());
        List<Category> allCategories = new ArrayList<Category>();
        if (cursor.moveToFirst()) {
            do {
                allCategories.add(categoryFromCursor(cursor));
            } while (cursor.moveToNext());
            cursor.close();
        }

        return allCategories;
    }

    public List<String> getAllCategoryNames() {
        Cursor cursor = CategoriesTable.getAllCategories(databaseOpenHelper.getReadableDatabase());
        List<String> allCategoryNames = new ArrayList<String>();
        if (cursor.moveToFirst()) {
            do {
                allCategoryNames.add(categoryFromCursor(cursor).getCategoryName());
            } while (cursor.moveToNext());
            cursor.close();
        }

        return allCategoryNames;
    }


    private static Category categoryFromCursor(Cursor cursor) {
        String categoryName = CategoriesTable.getCategoryName(cursor);
        int color = CategoriesTable.getColor(cursor);
        Category category = new Category(categoryName, color);
        return category;
    }

    public boolean categoryExists(String categoryName) {
        Cursor cursor = CategoriesTable.getRowFromCategoryName(databaseOpenHelper.getReadableDatabase(), categoryName);

        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }


}
