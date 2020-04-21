package kz.anet.goal_trackingapp.model;

import android.content.Context;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import kz.anet.goal_trackingapp.MvpContract.TasksContract;
import kz.anet.goal_trackingapp.Task;
import kz.anet.goal_trackingapp.TaskRoomRepository;

public class TaskModel implements TasksContract.Model {
    TaskRoomRepository mTaskRoomRepository;
    public TaskModel(Context context) {
        mTaskRoomRepository = new TaskRoomRepository(context);
    }

    @Override
    public Single<List<Task>> getTasks() {
        return mTaskRoomRepository.getAll();
    }

    @Override
    public Completable insertTask(Task task) {
        return mTaskRoomRepository.insert(task);
    }

    @Override
    public Completable deleteTask(Task task) {
        return mTaskRoomRepository.delete(task);
    }
}
