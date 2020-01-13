package com.gmail.myrunapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
                DbHelper.COLUMN_USER_PASSWORD
        };

        Cursor cursor = db.query(DbHelper.TABLE_USER_DATA, columns, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                UserData user = new UserData();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_USER_PASSWORD)));
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

        db.insert(DbHelper.TABLE_USER_DATA, null, values);
        db.close();
    }
}
