package com.example.dbapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class Database {
    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "persons";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "NAME";
    private static final String COLUMN_SURNAME = "SURNAME";
    private static final String COLUMN_AGE = "AGE";
    ;

    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_NAME = 1;
    private static final int NUM_COLUMN_SURNAME = 2;
    private static final int NUM_COLUMN_AGE = 3;


    private final SQLiteDatabase dataBase;

    public Database(Context context) {
        OpenHelper helper = new OpenHelper(context);
        dataBase = helper.getWritableDatabase();
    }

    public void insert(String name, String surname, int age) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_SURNAME, surname);
        cv.put(COLUMN_AGE, age);
        long newRowId = dataBase.insert(TABLE_NAME, null, cv);
    }

    public Person select(long id) {
        String[] projection = { COLUMN_ID, COLUMN_NAME, COLUMN_SURNAME, COLUMN_AGE};
        Cursor cursor = dataBase.query(
                TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
        if(cursor.getCount() > 0 && cursor.moveToFirst()) {
            String name = cursor.getString(NUM_COLUMN_NAME);
            String surname = cursor.getString(NUM_COLUMN_SURNAME);
            int age = cursor.getInt(NUM_COLUMN_AGE);
            cursor.close();
            return new Person(name, surname, age);
        }else{
            return null;
        }
    }

    public ArrayList<Person> selectAll() {
        Cursor c = dataBase.query(TABLE_NAME, null, null, null, null, null, null);
        ArrayList<Person> arr = new ArrayList<>();
        c.moveToFirst();
        if (!c.isAfterLast()) {
            do {
                String name = c.getString(NUM_COLUMN_NAME);
                String surname = c.getString(NUM_COLUMN_SURNAME);
                int age = c.getInt(NUM_COLUMN_AGE);
                arr.add(new Person(name, surname, age));
            } while (c.moveToNext());
        }
        return arr;
    }

    public long length(){
        long taskCount = DatabaseUtils.queryNumEntries(dataBase, TABLE_NAME);
        return taskCount;
    }

    public void deleteAll() {
        dataBase.delete(TABLE_NAME, null, null);
    }


    public static class OpenHelper extends SQLiteOpenHelper {
        public OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String query = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_SURNAME + " TEXT," +
                    COLUMN_AGE + " INT);";
            sqLiteDatabase.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(sqLiteDatabase);
        }
    }
}