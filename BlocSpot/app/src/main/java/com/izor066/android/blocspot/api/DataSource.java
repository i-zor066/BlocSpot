package com.izor066.android.blocspot.api;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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
            float latitude = 51.5160563f;
            float longitude =  -0.1271485f;
            SQLiteDatabase writableDatabase = databaseOpenHelper.getWritableDatabase();
            new PointsOfInterestTable.Builder()
                    .setTitle("Google")
                    .setAddress("1-13 St Giles High St\n" +
                            "London WC2H 8LG\n" +
                            "United Kingdom")
                    .setLatitude(latitude)
                    .setLongitude(longitude)
                    .insert(writableDatabase);
        }
    }
}
