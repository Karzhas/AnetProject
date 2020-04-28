package kz.anet.goal_trackingapp;

import android.content.Context;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import kz.anet.goal_trackingapp.db.TaskDao;
import kz.anet.goal_trackingapp.db.TaskDatabase;

public class TaskRoomRepository {

    TaskDatabase db;
    TaskDao taskDao;


    public TaskRoomRepository(Context context) {
        this.db = TaskDatabase.getDatabase(context);
        this.taskDao = db.taskDao();
    }

    public Single<List<TaskDto>> getAll() {
        return taskDao.getAll();
    }

    public Completable insert(TaskDto task) {
        return taskDao.insertTask(task);
    }

    public Completable delete(TaskDto task) {
        return taskDao.delete(task);
    }

    public Completable update(TaskDto task) {
        return taskDao.update(task);
    }
}
