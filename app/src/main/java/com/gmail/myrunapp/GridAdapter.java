package com.gmail.myrunapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GridAdapter extends BaseAdapter {

    private List<Date> dates;
    private List<EventData> events;
    private Context context;
    private Calendar calendar;

    SimpleDateFormat dateFormatDay = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);

    public GridAdapter(Context context, List<Date> dates, List<EventData> events, Calendar calendar) {
        this.dates = dates;
        this.context = context;
        this.calendar = calendar;
        this.events = events;
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public Object getItem(int position) {
        return dates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Date monthDate = dates.get(position);
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(monthDate);

        int dayNumber = dateCalendar.get(Calendar.DAY_OF_MONTH);

        int displayMonth = dateCalendar.get(Calendar.MONTH) + 1;
        int displayYear = dateCalendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentYear = calendar.get(Calendar.YEAR);

        Calendar highlightCalendar = Calendar.getInstance();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_grid, parent, false);
        }

        if (displayMonth != currentMonth || displayYear != currentYear) {
            convertView.setBackgroundResource(R.drawable.shape_grid_cell_dark);
        }

        if ((highlightCalendar.get(Calendar.DAY_OF_MONTH) == dateCalendar.get(Calendar.DAY_OF_MONTH)) && (highlightCalendar.get(Calendar.MONTH) == dateCalendar.get(Calendar.MONTH)) && (highlightCalendar.get(Calendar.DAY_OF_MONTH) == dateCalendar.get(Calendar.DAY_OF_MONTH))) {
            convertView.setBackgroundResource(R.drawable.shape_grid_cell_highlighted);
        }

        TextView dayNo = convertView.findViewById(R.id.calendarDayG);
        TextView distance = convertView.findViewById(R.id.eventG);
        dayNo.setText(String.valueOf(dayNumber));

        if (comparison(dates.get(position), events) != null) {
            if (comparison(dates.get(position), events).getDistance().equals("")) {
                convertView.setBackgroundResource(R.drawable.shape_grid_cell_yellow);
            } else {
                convertView.setBackgroundResource(R.drawable.shape_grid_cell_green);
                distance.setText(getDistance(dates.get(position), events));
            }
        }

        return convertView;
    }

    public EventData comparison(Date date, List<EventData> events) {

        for (EventData event: events) {
            try {
                Date eventDate = dateFormatDay.parse(event.getDate());

                Calendar dateEvent = Calendar.getInstance();
                dateEvent.setTime(eventDate);
                Calendar dateCurrent = Calendar.getInstance();
                dateCurrent.setTime(date);

                if ((dateEvent.get(Calendar.DAY_OF_MONTH) == dateCurrent.get(Calendar.DAY_OF_MONTH)) &&
                        (dateEvent.get(Calendar.MONTH) == dateCurrent.get(Calendar.MONTH)) &&
                        (dateEvent.get(Calendar.YEAR) == dateCurrent.get(Calendar.YEAR))) {
                    return event;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getDistance(Date date, List<EventData> events) {

        String distance = "";

        for (EventData event: events) {
            try {
                Date eventDate = dateFormatDay.parse(event.getDate());

                Calendar dateEvent = Calendar.getInstance();
                dateEvent.setTime(eventDate);
                Calendar dateCurrent = Calendar.getInstance();
                dateCurrent.setTime(date);

                if ((dateEvent.get(Calendar.DAY_OF_MONTH) == dateCurrent.get(Calendar.DAY_OF_MONTH)) &&
                        (dateEvent.get(Calendar.MONTH) == dateCurrent.get(Calendar.MONTH)) &&
                        (dateEvent.get(Calendar.YEAR) == dateCurrent.get(Calendar.YEAR)) && !event.getDistance().equals("")) {

                    distance = event.getDistance();
                    return distance + " km";
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return distance;


    }

}
