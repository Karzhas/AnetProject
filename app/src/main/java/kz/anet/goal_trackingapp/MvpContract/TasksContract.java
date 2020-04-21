package kz.anet.goal_trackingapp.MvpContract;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import kz.anet.goal_trackingapp.Task;

public interface TasksContract {
    interface View{
        void showTasks(List<Task> tasks);
    }
    interface Presenter{
        void getTasks();
        void insertTask(Task task);
        void deleteTask(Task task);
        void onAttach(TasksContract.View view);
        void onDetach();
    }
    interface Model{
        Single<List<Task>> getTasks();
        Completable insertTask(Task task);
        Completable deleteTask(Task task);
    }
}
