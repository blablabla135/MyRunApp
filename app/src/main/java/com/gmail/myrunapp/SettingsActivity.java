package com.gmail.myrunapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    private String profile;
    private DbHelper dbHelper;
    private UsersManager usersManager;
    private Button firstRan, mainEvent;
    private EditText mainEventName;
    private String firstRanDate, mainEventDate;

    private SimpleDateFormat dateFormatDay = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);

    private Calendar calendarForDB = Calendar.getInstance();
    private Date dateForDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Intent intent = getIntent();
        profile = intent.getStringExtra("eMail");

        dbHelper = new DbHelper(this);
        usersManager = new UsersManager(dbHelper);

        firstRan = findViewById(R.id.buttonFirstRanS);
        mainEvent = findViewById(R.id.buttonMainEventS);
        mainEventName = findViewById(R.id.editTextMainEventS);
    }

    public void setFirstRan(View view) {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePicker = new DatePickerDialog(SettingsActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendarForDB.set(year, month, dayOfMonth);
                dateForDB = calendarForDB.getTime();
                firstRanDate = dateFormatDay.format(dateForDB);
                firstRan.setText(firstRanDate);
            }
        }, year, month, day);
        datePicker.show();
    }

    public void setMainEvent(View view) {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePicker = new DatePickerDialog(SettingsActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendarForDB.set(year, month, dayOfMonth);
                dateForDB = calendarForDB.getTime();
                mainEventDate = dateFormatDay.format(dateForDB);
                mainEvent.setText(mainEventDate);
            }
        }, year, month, day);
        datePicker.show();
    }

    public void confirm(View view) {
        if (firstRanDate != null) {
            usersManager.updateFirstRanDate(firstRanDate, profile);
        }
        if (mainEventDate != null) {
            usersManager.updateMainEventDate(mainEventDate, profile);
        }
        if (!mainEventName.getText().toString().trim().equals("")) {
            usersManager.updateMainEventName(mainEventName.getText().toString().trim(), profile);
        }
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

}
