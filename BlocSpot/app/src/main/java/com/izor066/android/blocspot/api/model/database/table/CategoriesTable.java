package com.izor066.android.blocspot.api.model.database.table;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by igor on 4/10/15.
 */
public class CategoriesTable extends Table {

    private static final String NAME ="categories";

    private static final String COLUMN_CATEGORY_NAME = "category_name";
    private static final String COLUMN_COLOR = "color";

    public static class Builder implements Table.Builder {

        ContentValues values = new ContentValues();

        public Builder setCategoryName(String categoryName) {
            values.put(COLUMN_CATEGORY_NAME, categoryName);
            return this;
        }

        public Builder setColor(int color) {
            values.put(COLUMN_COLOR, color);
            return this;
        }


        @Override
        public long insert(SQLiteDatabase writableDB) {
            return writableDB.insert(CategoriesTable.NAME, null, values);
        }
    }

    public static String getCategoryName(Cursor cursor) {
        return getString(cursor, COLUMN_CATEGORY_NAME);
    }

    public static int getColor(Cursor cursor) {
        return getInt(cursor, COLUMN_COLOR);
    }

    public static Cursor getRowFromCategoryName(SQLiteDatabase readonlyDatabase, String categoryName) {
        return readonlyDatabase.query(true, NAME, null, COLUMN_CATEGORY_NAME + " = ?",
                new String[]{categoryName}, null, null, null, null);
    }

    public static Cursor getAllCategories(SQLiteDatabase readonlyDatabase) {
        return readonlyDatabase.rawQuery("SELECT * FROM " + NAME + " ORDER BY ?", new String[]{COLUMN_CATEGORY_NAME});
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getCreateStatement() {
        return "CREATE TABLE " + getName() + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_CATEGORY_NAME + " TEXT,"
                + COLUMN_COLOR + " INTEGER)";
    }
}
