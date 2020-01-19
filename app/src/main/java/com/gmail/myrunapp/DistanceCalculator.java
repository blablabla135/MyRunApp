package com.gmail.myrunapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DistanceCalculator {

    private List<EventData> events;
    private String firstRanString, mainEventString;
    private Date firstRanDate, mainEventDate;
    private Calendar firstRanCalendar = Calendar.getInstance();
    private Calendar mainEventCalendar = Calendar.getInstance();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);

    public DistanceCalculator(List<EventData> events, String firstRanString, String mainEventString) {
        this.events = events;
        this.firstRanString = firstRanString;
        this.mainEventString = mainEventString;
    }

    public void initDates() {
        try {
                firstRanDate = dateFormat.parse(firstRanString);
                mainEventDate = dateFormat.parse(mainEventString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        firstRanCalendar.setTime(firstRanDate);
        mainEventCalendar.setTime(mainEventDate);
    }

    public String getDistanceByMonth() {
        String distanceString = "";

        initDates();

        





        return distanceString;
    }









}
