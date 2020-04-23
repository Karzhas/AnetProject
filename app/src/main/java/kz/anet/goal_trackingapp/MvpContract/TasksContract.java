package kz.anet.goal_trackingapp.MvpContract;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import kz.anet.goal_trackingapp.Task;
import kz.anet.goal_trackingapp.TaskDto;

public interface TasksContract {
    interface View{
        void showTasks(List<Task> tasks);
        void dataChanged();
    }
    interface Presenter{
        void showTasks();
        void insertTask(Task task);
        void deleteTask(Task task);
        void updateTask(Task task);
        void onAttach(TasksContract.View view);
        void onDetach();
    }
    interface Model{
        Single<List<TaskDto>> getTasks();
        Completable insertTask(TaskDto task);
        Completable deleteTask(TaskDto task);
        Completable updateTask(TaskDto task);
    }
}
