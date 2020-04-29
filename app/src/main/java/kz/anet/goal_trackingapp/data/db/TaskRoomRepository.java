package kz.anet.goal_trackingapp.data.db;

import android.content.Context;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import kz.anet.goal_trackingapp.data.model.TaskDto;

public class TaskRoomRepository {

    TaskDatabase mTaskDatabase;
    TaskDao mTaskDao;


    public TaskRoomRepository(Context context) {
        this.mTaskDatabase = TaskDatabase.getDatabase(context);
        this.mTaskDao = mTaskDatabase.taskDao();
    }

    public Single<List<TaskDto>> getAll() {
        return mTaskDao.getAll();
    }

    public Completable insert(TaskDto task) {
        return mTaskDao.insertTask(task);
    }

    public Completable delete(TaskDto task) {
        return mTaskDao.delete(task);
    }

    public Completable update(TaskDto task) {
        return mTaskDao.update(task);
    }
}
