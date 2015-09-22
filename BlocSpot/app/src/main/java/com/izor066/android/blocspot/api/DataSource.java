package com.izor066.android.blocspot.api;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.izor066.android.blocspot.BuildConfig;
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
                    .setTitle("Google")
                    .setAddress("1-13 St Giles High St\n" +
                            "London WC2H 8LG\n" +
                            "United Kingdom")
                    .setLatitude(51.5160563f)
                    .setLongitude(-0.1271485f)
                    .insert(writableDatabase);
            new PointsOfInterestTable.Builder()
                    .setTitle("Facebook London")
                    .setAddress("10 Brock St\n" +
                            "Kings Cross, London NW1 3FG\n" +
                            "United Kingdom")
                    .setLatitude(51.5261296f)
                    .setLongitude(-0.1394121f)
                    .insert(writableDatabase);
            new PointsOfInterestTable.Builder()
                    .setTitle("Twitter HQ")
                    .setAddress("Air St\n" +
                            "Soho, London W1B 5AG\n" +
                            "United Kingdom")
                    .setLatitude(51.5104794f)
                    .setLongitude(-0.1366545f)
                    .insert(writableDatabase);
            new PointsOfInterestTable.Builder()
                    .setTitle("")
                    .insert(writableDatabase);
        }
    }

    public void DatabaseTest(long rowId) {
        Cursor cursor = PointsOfInterestTable.getRowTest(databaseOpenHelper.getReadableDatabase(), rowId);
        cursor.moveToFirst();
        String title = PointsOfInterestTable.getTitle(cursor);
        String address = PointsOfInterestTable.getAddress(cursor);
        float latitude = PointsOfInterestTable.getLatitude(cursor);
        float longitude = PointsOfInterestTable.getLongitude(cursor);

        Log.v("Entry for: " + rowId,  " " + title + "; " + " " + address + "; " + " " + " " + latitude + "; " + " " + longitude + " "  );
    }
}
