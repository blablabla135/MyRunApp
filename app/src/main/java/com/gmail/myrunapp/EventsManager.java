package com.gmail.myrunapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class EventsManager {

    private DbHelper dbHelper;

    private String userName;

    public EventsManager(DbHelper dbHelper, String userName) {
        this.dbHelper = dbHelper;
        this.userName = userName;
    }

    public List<EventData> getEvents(){

        String[] selectionArg = {userName};

        List<EventData> eventList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] columns = {
                DbHelper.COLUMN_EVENT_ID,
                DbHelper.COLUMN_EVENT_DATE,
                DbHelper.COLUMN_EVENT_DISTANCE,
                DbHelper.COLUMN_EVENT_PROFILE
        };

        String selection = DbHelper.COLUMN_EVENT_PROFILE + " = ?";

        Cursor cursor = db.query(DbHelper.TABLE_EVENT_DATA, columns, selection, selectionArg, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                EventData event = new EventData();
                event.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_EVENT_ID))));
                event.setDate(cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_EVENT_DATE)));
                event.setDistance(cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_EVENT_DISTANCE)));
                eventList.add(event);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return eventList;
    }

    public void addEvent(EventData event) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_EVENT_DATE, event.getDate());
        values.put(DbHelper.COLUMN_EVENT_DISTANCE, event.getDistance());
        values.put(DbHelper.COLUMN_EVENT_PROFILE, userName);

        db.insert(DbHelper.TABLE_EVENT_DATA, null, values);
        db.close();
    }

    public void updateEvent(EventData event) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = DbHelper.COLUMN_EVENT_PROFILE + " = ? AND " + DbHelper.COLUMN_EVENT_DATE + " = ?";
        String[] selectionArg = {userName, event.getDate()};

        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_EVENT_DATE, event.getDate());
        values.put(DbHelper.COLUMN_EVENT_DISTANCE, event.getDistance());
        values.put(DbHelper.COLUMN_EVENT_PROFILE, userName);

        db.update(DbHelper.TABLE_EVENT_DATA, values, selection, selectionArg);
        db.close();
    }

    public void deleteEvent(EventData event) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = DbHelper.COLUMN_EVENT_PROFILE + " = ? AND " + DbHelper.COLUMN_EVENT_DATE + " = ?";
        String[] selectionArg = {userName, event.getDate()};

        db.delete(DbHelper.TABLE_EVENT_DATA, selection, selectionArg);
        db.close();
    }

}
