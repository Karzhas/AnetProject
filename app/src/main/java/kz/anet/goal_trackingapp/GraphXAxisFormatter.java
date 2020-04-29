package kz.anet.goal_trackingapp;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

import kz.anet.goal_trackingapp.utils.TimeUtils;

public class GraphXAxisFormatter extends ValueFormatter {
    private String[] month;

    public GraphXAxisFormatter() {
        month = TimeUtils.getMonthArray();
    }

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        return month[(int)value];
    }
}
