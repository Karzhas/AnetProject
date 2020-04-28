package kz.anet.goal_trackingapp;

import java.util.Comparator;
import java.util.Date;

import kz.anet.goal_trackingapp.utils.TimeUtils;

public class DoneTasksComparator implements Comparator<TaskDto> {
    @Override
    public int compare(TaskDto t1, TaskDto t2) {
        if(!t1.getDone() && !t2.getDone()){
            // if both done, compare by date
            Date d1 = TimeUtils.getDateFromString(t1.getCreatedAtTime(), t1.getCreatedAtDate());
            Date d2 = TimeUtils.getDateFromString(t2.getCreatedAtTime(), t2.getCreatedAtDate());
            return compareByDate(d1,d2);
        }
        else if(t1.getDone())
            return 1;
        else
            return -1;
    }

    private int compareByDate(Date d1, Date d2) {
        if(d1.compareTo(d2) > 0) {
            return 1;
        } else if(d1.compareTo(d2) < 0) {
            return -1;
        } else {
            return 0;
        }
    }
}
