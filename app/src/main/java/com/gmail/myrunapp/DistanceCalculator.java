package com.gmail.myrunapp;

import android.content.Intent;

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
        double distance = 0.00;

        initDates();

        for (EventData event: events) {

            try {
                Date eventDate = dateFormat.parse(event.getDate());

                Calendar currentCalendar = Calendar.getInstance();
                Date currentDate = currentCalendar.getTime();

                Calendar dateEvent = Calendar.getInstance();
                dateEvent.setTime(eventDate);
                Calendar dateCurrent = Calendar.getInstance();
                dateCurrent.setTime(currentDate);

                if ((dateEvent.get(Calendar.MONTH) == dateCurrent.get(Calendar.MONTH)) &&
                        (dateEvent.get(Calendar.YEAR) == dateCurrent.get(Calendar.YEAR)) && !event.getDistance().equals("")) {
                    distance = distance + Double.parseDouble(event.getDistance());
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        distanceString = distance + "km";

        return distanceString;
    }

    public String getDistanceByWeek() {
        String distanceString = "";
        double distance = 0.00;

        initDates();

        for (EventData event: events) {

            try {
                Date eventDate = dateFormat.parse(event.getDate());

                Calendar currentCalendar = Calendar.getInstance();
                Date currentDate = currentCalendar.getTime();

                Calendar dateEvent = Calendar.getInstance();
                dateEvent.setFirstDayOfWeek(Calendar.MONDAY);
                dateEvent.setTime(eventDate);
                Calendar dateCurrent = Calendar.getInstance();
                dateCurrent.setFirstDayOfWeek(Calendar.MONDAY);
                dateCurrent.setTime(currentDate);

                if ((dateEvent.get(Calendar.WEEK_OF_YEAR) == dateCurrent.get(Calendar.WEEK_OF_YEAR)) &&
                        (dateEvent.get(Calendar.MONTH) == dateCurrent.get(Calendar.MONTH)) &&
                        (dateEvent.get(Calendar.YEAR) == dateCurrent.get(Calendar.YEAR)) && !event.getDistance().equals("")) {
                    distance = distance + Double.parseDouble(event.getDistance());
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        distanceString = distance + "km";

        return distanceString;
    }









}
