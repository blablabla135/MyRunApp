package com.gmail.myrunapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;


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
    Toolbar actionBar;
    ActionMode mActionMode;

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
        actionBar = findViewById(R.id.actionBarM);

        setSupportActionBar(actionBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        currentDate.setText(dateFormat.format(calendar.getTime()));

        initializeDates();
        adapter = new GridAdapter(this, dates, calendar);
        gridView.setAdapter(adapter);

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (mActionMode != null) {
                    return false;
                }
                mActionMode = startSupportActionMode(mActionModeCallback);
                return true;
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

    ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_context_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.addRunM: {
                    Toast.makeText(MenuActivity.this, "add", Toast.LENGTH_SHORT).show();
                    mode.finish();
                    return true;
                }
                case R.id.deleteRunM: {
                    Toast.makeText(MenuActivity.this, "dell", Toast.LENGTH_SHORT).show();
                    mode.finish();
                    return true;
                }
                case R.id.editRunM: {
                    Toast.makeText(MenuActivity.this, "edit", Toast.LENGTH_SHORT).show();
                    mode.finish();
                    return true;
                }
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;

        }
    };
}
