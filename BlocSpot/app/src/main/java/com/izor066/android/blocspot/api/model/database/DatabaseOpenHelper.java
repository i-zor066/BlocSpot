package com.izor066.android.blocspot.api.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.izor066.android.blocspot.api.model.database.table.Table;

/**
 * Created by igor on 22/9/15.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {

    public static final String NAME = "blocspot_db";

    private static final int VERSION = 1;

    private Table[] tables;

    public DatabaseOpenHelper(Context context, Table... tables) {

        super(context, NAME, null, VERSION);
        this.tables = tables;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        for (Table table : tables) {
            db.execSQL(table.getCreateStatement());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (Table table : tables) {
            table.onUpgrade(db, oldVersion, newVersion);
        }

    }
}
