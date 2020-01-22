package com.gmail.myrunapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class UsersManager {

    private DbHelper dbHelper;

    public UsersManager(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public List<UserData> getUsers(){

        List<UserData> userList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] columns = {
                DbHelper.COLUMN_USER_ID,
                DbHelper.COLUMN_USER_EMAIL,
                DbHelper.COLUMN_USER_NAME,
                DbHelper.COLUMN_USER_PASSWORD,
                DbHelper.COLUMN_USER_FIRST_RAN,
                DbHelper.COLUMN_USER_MAIN_EVENT,
                DbHelper.COLUMN_USER_MAIN_EVENT_NAME
        };

        Cursor cursor = db.query(DbHelper.TABLE_USER_DATA, columns, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                UserData user = new UserData();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_USER_PASSWORD)));
                user.setFirstRan(cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_USER_FIRST_RAN)));
                user.setMainEvent(cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_USER_MAIN_EVENT)));
                user.setMainEventName(cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_USER_MAIN_EVENT_NAME)));
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return userList;
    }

    public void addUser(UserData user) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_USER_NAME, user.getName());
        values.put(DbHelper.COLUMN_USER_EMAIL, user.getEmail());
        values.put(DbHelper.COLUMN_USER_PASSWORD, user.getPassword());
        values.put(DbHelper.COLUMN_USER_FIRST_RAN, user.getFirstRan());
        values.put(DbHelper.COLUMN_USER_MAIN_EVENT, user.getMainEvent());
        values.put(DbHelper.COLUMN_USER_MAIN_EVENT_NAME, user.getMainEventName());

        db.insert(DbHelper.TABLE_USER_DATA, null, values);
        db.close();
    }

    public UserData getUser(String userName){

        String[] selectionArg = {userName};

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = DbHelper.COLUMN_USER_EMAIL + " = ?";

        String[] columns = {
                DbHelper.COLUMN_USER_ID,
                DbHelper.COLUMN_USER_EMAIL,
                DbHelper.COLUMN_USER_NAME,
                DbHelper.COLUMN_USER_PASSWORD,
                DbHelper.COLUMN_USER_FIRST_RAN,
                DbHelper.COLUMN_USER_MAIN_EVENT,
                DbHelper.COLUMN_USER_MAIN_EVENT_NAME
        };

        Cursor cursor = db.query(DbHelper.TABLE_USER_DATA, columns, selection, selectionArg, null, null, null, null);

        UserData user = new UserData();

        if (cursor.moveToFirst()) {
            do {
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_USER_PASSWORD)));
                user.setFirstRan(cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_USER_FIRST_RAN)));
                user.setMainEvent(cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_USER_MAIN_EVENT)));
                user.setMainEventName(cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_USER_MAIN_EVENT_NAME)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return user;
    }



}
