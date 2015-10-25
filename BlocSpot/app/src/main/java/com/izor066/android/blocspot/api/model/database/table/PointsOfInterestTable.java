package com.izor066.android.blocspot.api.model.database.table;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by igor on 22/9/15.
 */
public class PointsOfInterestTable extends Table {

    private static final String NAME = "points_of_interest";

    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_LATITUDE = "latitude";
    private static final String COLUMN_LONGITUDE = "longitude";
    private static final String COLUMN_POI_CATEGORY = "poi_category";
    private static final String COLUMN_POI_NOTE = "poi_note";

    public static class Builder implements Table.Builder {

        ContentValues values = new ContentValues();

        public Builder setTitle(String title) {
            values.put(COLUMN_TITLE, title);
            return this;
        }

        public Builder setAddress(String address) {
            values.put(COLUMN_ADDRESS, address);
            return this;
        }

        public Builder setLongitude(float longitude) {
            values.put(COLUMN_LONGITUDE, longitude);
            return this;
        }

        public Builder setLatitude(float latitude) {
            values.put(COLUMN_LATITUDE, latitude);
            return this;
        }

        public Builder setCategory(String poiCategory) {
            values.put(COLUMN_POI_CATEGORY, poiCategory);
            return this;
        }

        public Builder setNote(String poiNote) {
            values.put(COLUMN_POI_NOTE, poiNote);
            return this;
        }

        public void updateForTitle(SQLiteDatabase writableDB, String title) {
            writableDB.update(PointsOfInterestTable.NAME, values, COLUMN_TITLE + " = ?", new String[]{title});
        }

        @Override
        public long insert(SQLiteDatabase writableDB) {
            return writableDB.insert(PointsOfInterestTable.NAME, null, values);
        }
    }

    public static String getTitle(Cursor cursor) {
        return getString(cursor, COLUMN_TITLE);
    }

    public static String getAddress(Cursor cursor) {
        return getString(cursor, COLUMN_ADDRESS);
    }


    public static float getLatitude(Cursor cursor) {
        return getFloat(cursor, COLUMN_LATITUDE);
    }

    public static float getLongitude(Cursor cursor) {
        return getFloat(cursor, COLUMN_LONGITUDE);
    }

    public static String getPoiCategory(Cursor cursor) {
        return getString(cursor, COLUMN_POI_CATEGORY);
    }

    public static String getPoiNote(Cursor cursor) {
        return getString(cursor, COLUMN_POI_NOTE);
    }

    public static Cursor getRowWithId(SQLiteDatabase readonlyDatabase, long rowId) {
        return readonlyDatabase.query(true, NAME, null, COLUMN_ID + " = ?",
                new String[]{String.valueOf(rowId)}, null, null, null, null);
    }

    public static Cursor getRowFromTitle(SQLiteDatabase readonlyDatabase, String title) {
        return readonlyDatabase.query(true, NAME, null, COLUMN_TITLE + " = ?",
                new String[]{title}, null, null, null, null);
    }

    public static Cursor fetchAllPointsOfInterest(SQLiteDatabase readonlyDatabase) {
        return readonlyDatabase.rawQuery("SELECT * FROM " + NAME + " ORDER BY ?", new String[]{COLUMN_TITLE});
    }

    public static Cursor getAllForCategory(SQLiteDatabase readonlyDatabase, String categoryName) {
        return readonlyDatabase.query(true, NAME, null, COLUMN_POI_CATEGORY + " = ?",
                new String[]{categoryName}, null, null, null, null);
    }

//    public static Cursor getAllForCatOrder(SQLiteDatabase readonlyDatabase, String categoryName) {
//        return readonlyDatabase.query(true, NAME, null, COLUMN_POI_CATEGORY + " = ?", new String[]{categoryName}, null, null, COLUMN_TITLE + " DESC", null);
//    }


    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getCreateStatement() {
        return "CREATE TABLE " + getName() + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_ADDRESS + " TEXT,"
                + COLUMN_LATITUDE + " REAL,"
                + COLUMN_LONGITUDE + " REAL,"
                + COLUMN_POI_CATEGORY + " TEXT DEFAULT 'Unsorted',"
                + COLUMN_POI_NOTE + " TEXT DEFAULT 'No note yet')";
    }
}
