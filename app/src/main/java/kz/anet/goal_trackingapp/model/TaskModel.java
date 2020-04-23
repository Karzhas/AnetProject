package kz.anet.goal_trackingapp.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import kz.anet.goal_trackingapp.MvpContract.TasksContract;
import kz.anet.goal_trackingapp.TaskDto;
import kz.anet.goal_trackingapp.TaskRoomRepository;

public class TaskModel implements TasksContract.Model {
    TaskRoomRepository mTaskRoomRepository;
    List<TaskDto> tasks;
    public TaskModel(Context context) {
        mTaskRoomRepository = new TaskRoomRepository(context);
        tasks = new ArrayList<>();
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
}
