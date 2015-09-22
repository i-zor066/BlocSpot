package com.izor066.android.blocspot.api;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.izor066.android.blocspot.BuildConfig;
import com.izor066.android.blocspot.api.model.PointsOfInterest;
import com.izor066.android.blocspot.api.model.database.DatabaseOpenHelper;
import com.izor066.android.blocspot.api.model.database.table.PointsOfInterestTable;

/**
 * Created by igor on 22/9/15.
 */
public class DataSource {

    private DatabaseOpenHelper databaseOpenHelper;
    private PointsOfInterestTable pointsOfInterestTable;



    public DataSource (Context context) {
        pointsOfInterestTable = new PointsOfInterestTable();
        databaseOpenHelper = new DatabaseOpenHelper(context, pointsOfInterestTable);

        if (BuildConfig.DEBUG && true) {
            context.deleteDatabase("blocspot_db");
            SQLiteDatabase writableDatabase = databaseOpenHelper.getWritableDatabase();
            new PointsOfInterestTable.Builder()
                    .setTitle("")
                    .insert(writableDatabase);
        }
    }

    public void insertPointToDatabase(PointsOfInterest pointsOfInterest) {
        SQLiteDatabase writableDatabase = databaseOpenHelper.getWritableDatabase();
        new PointsOfInterestTable.Builder()
                .setTitle(pointsOfInterest.getTitle())
                .setAddress(pointsOfInterest.getAddress())
                .setLatitude(pointsOfInterest.getLatitude())
                .setLongitude(pointsOfInterest.getLongitude())
                .insert(writableDatabase);
    }

    public PointsOfInterest getPOIfromDBwithTitle (String title) {
        Cursor cursor = PointsOfInterestTable.getRowFromTitle(databaseOpenHelper.getReadableDatabase(), title);
        cursor.moveToFirst();
        PointsOfInterest pointsOfInterest = new PointsOfInterest(title, PointsOfInterestTable.getAddress(cursor), PointsOfInterestTable.getLatitude(cursor), PointsOfInterestTable.getLongitude(cursor));
        return pointsOfInterest;
    }

}
