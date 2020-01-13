package com.gmail.myrunapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GridAdapter extends BaseAdapter {

    private List<Date> dates;
    private Context context;
    private Calendar calendar;

    public GridAdapter(Context context, List<Date> dates, Calendar calendar) {
        this.dates = dates;
        this.context = context;
        this.calendar = calendar;
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

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_grid, parent, false);
        }

        if (displayMonth != currentMonth || displayYear != currentYear) {
            convertView.setBackgroundResource(R.drawable.shape_grid_cell_dark);
        }

        TextView dayNo = convertView.findViewById(R.id.calendarDayG);
        dayNo.setText(String.valueOf(dayNumber));

        return convertView;
    }
}
