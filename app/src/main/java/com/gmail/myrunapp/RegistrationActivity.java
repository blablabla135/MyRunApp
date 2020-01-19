package com.gmail.myrunapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RegistrationActivity extends AppCompatActivity {

    private EditText name, eMail, password, confirmPassword;
    private DbHelper dbHelper;
    private UsersManager usersManager;
    private List<UserData> userList;
    private Button firstRan, mainEvent;
    private String firstRanDate, mainEventDate;

    SimpleDateFormat dateFormatDay = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);

    Calendar calendarForDB = Calendar.getInstance();
    Date dateForDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name = findViewById(R.id.editTextUserNameR);
        eMail = findViewById(R.id.editTextEMailR);
        password = findViewById(R.id.editTextPasswordR);
        confirmPassword = findViewById(R.id.editTextPasswordConfirmR);
        firstRan = findViewById(R.id.buttonFirstRanR);
        mainEvent = findViewById(R.id.buttonMainEventR);

        dbHelper = new DbHelper(this);
        usersManager = new UsersManager(dbHelper);
    }

    public void signUp(View view) {

        userList = usersManager.getUsers();

        List<String> eMales = new ArrayList<String>();

        for (UserData x : userList) {
            eMales.add(x.getEmail());
        }

        if (!name.getText().toString().trim().equals("") && !eMail.getText().toString().trim().equals("") && !password.getText().toString().trim().equals("") && !confirmPassword.getText().toString().trim().equals("")) {
            if (eMales.contains(eMail.getText().toString().trim())) {
                Toast.makeText(this, "this eMale is already taken", Toast.LENGTH_SHORT).show();
            } else if (!password.getText().toString().trim().equals(confirmPassword.getText().toString().trim())) {
                Toast.makeText(this, "confirm password", Toast.LENGTH_SHORT).show();
            } else if (firstRanDate == null || mainEventDate == null) {
                Toast.makeText(this, "set dates", Toast.LENGTH_SHORT).show();
            } else {
                UserData user = new UserData();
                user.setEmail(eMail.getText().toString().trim());
                user.setName(name.getText().toString().trim());
                user.setPassword(password.getText().toString().trim());
                user.setFirstRan(firstRanDate);
                user.setMainEvent(mainEventDate);

                usersManager.addUser(user);

                Intent intent = new Intent(this, StartActivity.class);
                startActivity(intent);
            }
        } else {
            Toast.makeText(this, "fill all the fields", Toast.LENGTH_SHORT).show();
        }
    }

    public void setFirstRan(View view) {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePicker = new DatePickerDialog(RegistrationActivity.this, new DatePickerDialog.OnDateSetListener() {
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
        DatePickerDialog datePicker = new DatePickerDialog(RegistrationActivity.this, new DatePickerDialog.OnDateSetListener() {
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
}