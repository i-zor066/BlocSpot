package com.izor066.android.blocspot.api;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.izor066.android.blocspot.BuildConfig;
import com.izor066.android.blocspot.api.model.PointOfInterest;
import com.izor066.android.blocspot.api.model.database.DatabaseOpenHelper;
import com.izor066.android.blocspot.api.model.database.table.PointsOfInterestTable;

import java.util.ArrayList;
import java.util.List;

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
                    .setTitle("Google 2")
                    .setAddress("1-13 St Giles High St, London WC2H 8LG, United Kingdom")
                    .setLatitude(51.5169573f)
                    .setLongitude(-0.1279495f)
                    .insert(writableDatabase);
            new PointsOfInterestTable.Builder()
                    .setTitle("Facebook London 2")
                    .setAddress("10 Brock St, Kings Cross, London NW1 3FG, United Kingdom")
                    .setLatitude(51.5269276f)
                    .setLongitude(-0.1399191f)
                    .insert(writableDatabase);
            new PointsOfInterestTable.Builder()
                    .setTitle("Twitter HQ 2")
                    .setAddress("Air St, Soho, London W1B 5AG, United Kingdom")
                    .setLatitude(51.5104224f)
                    .setLongitude(-0.1362595f)
                    .insert(writableDatabase);

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

    public List<PointOfInterest> getAllPointsOfInterest() {
        Cursor cursor = PointsOfInterestTable.fetchAllPointsOfInterest(databaseOpenHelper.getReadableDatabase());
        List<PointOfInterest> allPoints = new ArrayList<PointOfInterest>();
        if (cursor.moveToFirst()) {
            do {
                allPoints.add(PointOfInterestFromCursor(cursor));
            } while (cursor.moveToNext());
            cursor.close();
        }

        return allPoints;

    }

    static PointOfInterest PointOfInterestFromCursor(Cursor cursor) {
        //cursor.moveToFirst();
        String title = PointsOfInterestTable.getTitle(cursor);
        String address = PointsOfInterestTable.getAddress(cursor);
        float latitude = PointsOfInterestTable.getLatitude(cursor);
        float longitude = PointsOfInterestTable.getLongitude(cursor);
        PointOfInterest pointOfInterest = new PointOfInterest(title, address, latitude, longitude);
        //cursor.close();
        return pointOfInterest;
    }



}
