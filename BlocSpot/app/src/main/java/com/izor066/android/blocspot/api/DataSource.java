package com.izor066.android.blocspot.api;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.izor066.android.blocspot.BuildConfig;
import com.izor066.android.blocspot.api.model.PointOfInterest;
import com.izor066.android.blocspot.api.model.database.DatabaseOpenHelper;
import com.izor066.android.blocspot.api.model.database.table.PointsOfInterestTable;

/**
 * Created by igor on 22/9/15.
 */
public class DataSource {

    private DatabaseOpenHelper databaseOpenHelper;
    private PointsOfInterestTable pointsOfInterestTable;


    public DataSource(Context context) {
        pointsOfInterestTable = new PointsOfInterestTable();
        databaseOpenHelper = new DatabaseOpenHelper(context, pointsOfInterestTable);

        if (BuildConfig.DEBUG) {
            context.deleteDatabase(DatabaseOpenHelper.NAME);
            SQLiteDatabase writableDatabase = databaseOpenHelper.getWritableDatabase();

        }
    }

    public void insertPointToDatabase(PointOfInterest pointOfInterest) {
        SQLiteDatabase writableDatabase = databaseOpenHelper.getWritableDatabase();
        new PointsOfInterestTable.Builder()
                .setTitle(pointOfInterest.getTitle())
                .setAddress(pointOfInterest.getAddress())
                .setLatitude(pointOfInterest.getLatitude())
                .setLongitude(pointOfInterest.getLongitude())
                .insert(writableDatabase);
    }

    public PointOfInterest getPOIfromDBwithTitle(String title) {
        Cursor cursor = PointsOfInterestTable.getRowFromTitle(databaseOpenHelper.getReadableDatabase(), title);
        cursor.moveToFirst();
        String address = PointsOfInterestTable.getAddress(cursor);
        float latitude = PointsOfInterestTable.getLatitude(cursor);
        float longitude = PointsOfInterestTable.getLongitude(cursor);
        PointOfInterest pointOfInterest = new PointOfInterest(title, address, latitude, longitude);
        cursor.close();
        return pointOfInterest;
    }

}
