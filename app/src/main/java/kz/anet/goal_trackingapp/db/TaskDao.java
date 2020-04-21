package kz.anet.goal_trackingapp.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import kz.anet.goal_trackingapp.Task;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Tasks")
    Single<List<Task>> getAll();

    @Insert
    Completable insertTask(Task task);

    @Update
    Completable update(Task task);

    @Delete
    Completable delete(Task task);

}
