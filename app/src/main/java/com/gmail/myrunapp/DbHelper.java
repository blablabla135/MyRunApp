package com.gmail.myrunapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MyRunningApp.db";

    public static final String TABLE_USER_DATA = "user";

    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_USER_EMAIL = "user_email";
    public static final String COLUMN_USER_PASSWORD = "user_password";


    public static final String TABLE_EVENT_DATA = "event";

    public static final String COLUMN_EVENT_ID = "event_id";
    public static final String COLUMN_EVENT_NAME = "event_name";
    public static final String COLUMN_EVENT_DATE = "event_date";
    public static final String COLUMN_EVENT_MONTH = "event_month";
    public static final String COLUMN_EVENT_YEAR = "event_year";
    public static final String COLUMN_EVENT_IS_DONE = "event_is_done";
    public static final String COLUMN_EVENT_GOAL = "event_goal";

    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER_DATA + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";

    private String CREATE_EVENT_TABLE = "CREATE TABLE " + TABLE_EVENT_DATA + "("
            + COLUMN_EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_EVENT_NAME + " TEXT,"
            + COLUMN_EVENT_DATE + " TEXT," + COLUMN_EVENT_MONTH + " TEXT," + COLUMN_EVENT_YEAR +
            " TEXT," + COLUMN_EVENT_IS_DONE + " INTEGER," + COLUMN_EVENT_GOAL + " INTEGER" + ")";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_EVENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
