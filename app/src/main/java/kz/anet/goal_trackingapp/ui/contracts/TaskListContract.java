package kz.anet.goal_trackingapp.ui.contracts;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import kz.anet.goal_trackingapp.models.Task;
import kz.anet.goal_trackingapp.data.model.TaskDto;

public interface TaskListContract {
    interface View{
        void showTasks(List<Task> tasks);
        void dataChanged();
    }
    interface Presenter{
        void showTasks();
        void insertTask(Task task);
        void deleteTask(Task task);
        void updateTask(Task task);
        void updateStatus(Task task);
        void onAttach(TaskListContract.View view);
        void onDetach();
    }
    interface Model{
        Single<List<TaskDto>> getTasks();
        Completable insertTask(TaskDto task);
        Completable deleteTask(TaskDto task);
        Completable updateTask(TaskDto task);
        Completable updateStatus(TaskDto task);

    }
}
