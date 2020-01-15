package com.gmail.myrunapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MenuActivity extends AppCompatActivity {

    ImageButton nextButton, previousButton;
    Button set, confirm, add;
    TextView currentDate, goal, distance;
    GridView gridView;

    DbHelper dbHelper;
    GridAdapter adapter;

    public Calendar calendar = Calendar.getInstance(Locale.ENGLISH);

    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);

    private List<Date> dates = new ArrayList<>();
    private List<Event> events = new ArrayList<>();

    private static final int MAX_DAYS = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        dbHelper = new DbHelper(this);

        currentDate = findViewById(R.id.textViewDateM);
        nextButton = findViewById(R.id.monthRightM);
        previousButton = findViewById(R.id.monthLeftM);
        gridView = findViewById(R.id.gridViewM);
        goal = findViewById(R.id.textViewCountdownM);

        currentDate.setText(dateFormat.format(calendar.getTime()));

        initializeDates();
        adapter = new GridAdapter(this, dates, calendar);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Date date = (Date) adapter.getItem(position);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
                String text = dateFormat.format(date);
                goal.setText(text);
            }
        });
    }

    public void nextMonth(View view){
        calendar.add(Calendar.MONTH, 1);
        currentDate.setText(dateFormat.format(calendar.getTime()));
        initializeDates();
        adapter = new GridAdapter(this, dates, calendar);
        gridView.setAdapter(adapter);
    }

    public void previousMonth(View view){
        calendar.add(Calendar.MONTH, -1);
        currentDate.setText(dateFormat.format(calendar.getTime()));
        initializeDates();
        adapter = new GridAdapter(this, dates, calendar);
        gridView.setAdapter(adapter);
    }

    public void initializeDates() {
        dates.clear();
        Calendar datesChangeCalendar = (Calendar) calendar.clone();
        datesChangeCalendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfMonth = datesChangeCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        datesChangeCalendar.add(Calendar.DAY_OF_MONTH, - (firstDayOfMonth - 1));
        if ((datesChangeCalendar.get(Calendar.DAY_OF_MONTH) > 1) && (datesChangeCalendar.get(Calendar.DAY_OF_MONTH) < 7)) {
            datesChangeCalendar.add(Calendar.DAY_OF_MONTH, -7);
        }

        while (dates.size() < MAX_DAYS) {
            dates.add(datesChangeCalendar.getTime());
            datesChangeCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }
}
