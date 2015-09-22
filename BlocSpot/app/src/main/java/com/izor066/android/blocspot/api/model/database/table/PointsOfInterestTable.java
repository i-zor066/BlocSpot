package com.izor066.android.blocspot.api.model.database.table;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by igor on 22/9/15.
 */
public class PointsOfInterestTable extends Table {

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

        @Override
        public long insert(SQLiteDatabase writableDB) {
            return writableDB.insert(PointsOfInterestTable.NAME, null, values);
        }
    }

    private static final String NAME ="points_of_interest";

    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_LONGITUDE = "longitude";
    private static final String COLUMN_LATITUDE = "latitude";

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
                + COLUMN_LONGITUDE + " REAL,"
                + COLUMN_LATITUDE + " REAL)";
    }
}
