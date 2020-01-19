package com.gmail.myrunapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
    TextView currentDate;
    GridView gridView;
    Toolbar actionBar;
    ActionMode mActionMode;
    AlertDialog addDialog;

    DbHelper dbHelper;
    EventsManager eventsManager;
    GridAdapter adapter;

    String profile;
    String dateForDB;

    public Calendar calendar = Calendar.getInstance(Locale.ENGLISH);

    SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    SimpleDateFormat dateFormatDay = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);

    private List<Date> dates = new ArrayList<>();
    private List<EventData> events = new ArrayList<>();

    private static final int MAX_DAYS = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        profile = intent.getStringExtra("eMail");

        dbHelper = new DbHelper(this);
        eventsManager = new EventsManager(dbHelper, profile);

        currentDate = findViewById(R.id.textViewDateM);
        nextButton = findViewById(R.id.monthRightM);
        previousButton = findViewById(R.id.monthLeftM);
        gridView = findViewById(R.id.gridViewM);
        actionBar = findViewById(R.id.actionBarM);



        setSupportActionBar(actionBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        currentDate.setText(dateFormatMonth.format(calendar.getTime()));

        initializeDates();
        adapter = new GridAdapter(this, dates, eventsManager.getEvents(), calendar);
        gridView.setAdapter(adapter);

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                dateForDB = dateFormatDay.format(gridView.getItemAtPosition(position));
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
        currentDate.setText(dateFormatMonth.format(calendar.getTime()));
        initializeDates();
        adapter = new GridAdapter(this, dates, eventsManager.getEvents(), calendar);
        gridView.setAdapter(adapter);
    }

    public void previousMonth(View view){
        calendar.add(Calendar.MONTH, -1);
        currentDate.setText(dateFormatMonth.format(calendar.getTime()));
        initializeDates();
        adapter = new GridAdapter(this, dates, eventsManager.getEvents(), calendar);
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
                    showAddDialog();
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

    public void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final CharSequence[] events = {"Set run", "Confirm run"};
        builder.setTitle("Chose activity").setItems(events, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: {
                        showDistanceDialog();
                        break;
                    }
                    case 1: {
                        Toast.makeText(MenuActivity.this, "Confirm run", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
        });
        addDialog = builder.create();
        addDialog.show();
    }

    public void showDistanceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText dialogDistance = new EditText(MenuActivity.this);
        builder.setTitle("Do not specify a distance, if you just want to schedule run").
                setView(dialogDistance).
                setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String distance = dialogDistance.getText().toString().trim();
                        EventData event = new EventData();
                        event.setDate(dateForDB);
                        event.setDistance(distance);
                        eventsManager.addEvent(event);
                        adapter = new GridAdapter(MenuActivity.this, dates, eventsManager.getEvents(), calendar);
                        gridView.setAdapter(adapter);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        addDialog = builder.create();
        addDialog.show();
    }


}
