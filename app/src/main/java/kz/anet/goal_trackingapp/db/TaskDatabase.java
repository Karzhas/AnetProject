package kz.anet.goal_trackingapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import kz.anet.goal_trackingapp.Task;

@Database(entities = {Task.class}, version = 1, exportSchema = false)
public abstract class TaskDatabase extends RoomDatabase {
    private static TaskDatabase INSTANCE;
    private static final String DB_NAME = "tasks7.db";

    public static TaskDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TaskDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TaskDatabase.class, DB_NAME)
//                            .allowMainThreadQueries() // SHOULD NOT BE USED IN PRODUCTION !!!
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract TaskDao taskDao();

}