package kz.anet.goal_trackingapp.model;

import java.util.Calendar;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import kz.anet.goal_trackingapp.MvpContract.TaskListContract;
import kz.anet.goal_trackingapp.TaskDto;
import kz.anet.goal_trackingapp.TaskRoomRepository;
import kz.anet.goal_trackingapp.utils.TimeUtils;

public class TaskListModel implements TaskListContract.Model {
    private TaskRoomRepository mTaskRoomRepository;
    public TaskListModel(TaskRoomRepository taskRoomRepository) {
        mTaskRoomRepository = taskRoomRepository;
    }

    @Override
    public Single<List<TaskDto>> getTasks() {
        return mTaskRoomRepository.getAll();
    }

    @Override
    public Completable insertTask(TaskDto task) {
        return mTaskRoomRepository.insert(task);
    }

    @Override
    public Completable deleteTask(TaskDto task) {
        return mTaskRoomRepository.delete(task);
    }

    @Override
    public Completable updateTask(TaskDto task) {
        return mTaskRoomRepository.update(task);
    }

    @Override
    public Completable updateStatus(TaskDto task) {
        if(task.getDone()){
            task.setFinishedAtDate("");
            task.setFinishedAtTime("");
            task.setDone(false);
        }else{
            task.setDone(true);
            Calendar date = Calendar.getInstance();
            int hour = date.get(Calendar.HOUR_OF_DAY);
            int minute = date.get(Calendar.MINUTE);
            int year = date.get(Calendar.YEAR);
            int month = date.get(Calendar.MONTH);
            int day = date.get(Calendar.DAY_OF_MONTH);
            task.setFinishedAtTime(TimeUtils.getFormattedTime(minute,hour));
            task.setFinishedAtDate(TimeUtils.getFormattedDate(day,month+1,year));
        }

        return mTaskRoomRepository.update(task);
    }
}
