package kz.anet.goal_trackingapp;

import android.content.Context;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import kz.anet.goal_trackingapp.db.TaskDao;
import kz.anet.goal_trackingapp.db.TaskDatabase;

public class TaskRoomRepository {
    Context context;
    TaskDatabase db;
    TaskDao taskDao;

    public TaskRoomRepository(Context context) {
        this.context = context;
        db = TaskDatabase.getDatabase(context);
        taskDao = db.taskDao();
    }

    public Single<List<Task>> getAll() {
        return taskDao.getAll();
    }

    public Completable insert(Task task) {
        return taskDao.insertTask(task);
    }

    public Completable delete(Task task) {
        return taskDao.delete(task);
    }

    public Completable update(Task task) {
        return taskDao.update(task);
    }
}
