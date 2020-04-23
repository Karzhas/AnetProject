package kz.anet.goal_trackingapp;

import java.util.Comparator;

public class DoneTasksComparator implements Comparator<TaskDto> {
    @Override
    public int compare(TaskDto t1, TaskDto t2) {
        if(t1.getDone() && t2.getDone())
            return 0;
        else if(t1.getDone())
            return 1;
        else
            return -1;
    }
}
