package kz.anet.goal_trackingapp.presenter;

import kz.anet.goal_trackingapp.MvpContract.TaskInformationContract;
import kz.anet.goal_trackingapp.Task;

public class TaskInformationPresenter implements TaskInformationContract.Presenter {
    private TaskInformationContract.View view;

    public TaskInformationPresenter(TaskInformationContract.View view) {
        this.view = view;
    }

    @Override
    public void getStatus(Task task) {
        view.setStatus(task.getDone());
    }

    @Override
    public void getCreatedAtDate(Task task) {
        view.setCreatedAtDate(task.getCreatedAtDate());
    }

    @Override
    public void getCreatedAtTime(Task task) {
        view.setCreatedAtTime(task.getCreatedAtTime());
    }



    @Override
    public void getTitle(Task task) {
        view.setTitle(task.getTitle());
    }

    @Override
    public void getPhotos(Task task) {
        view.setPhotos(task.getPhotos());
    }

    @Override
    public void getDescription(Task task) {
        view.setDescription(task.getDescription());
    }
}
