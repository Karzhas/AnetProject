package kz.anet.goal_trackingapp.ui.presenter;

import kz.anet.goal_trackingapp.ui.contracts.TaskInformationContract;
import kz.anet.goal_trackingapp.models.Task;

public class TaskInformationPresenter implements TaskInformationContract.Presenter {
    private TaskInformationContract.View mView;

    public TaskInformationPresenter(TaskInformationContract.View view) {
        this.mView = view;
    }

    @Override
    public void getStatus(Task task) {
        mView.setStatus(task.getDone());
    }

    @Override
    public void getCreatedAtDate(Task task) {
        mView.setCreatedAtDate(task.getCreatedAtDate());
    }

    @Override
    public void getCreatedAtTime(Task task) {
        mView.setCreatedAtTime(task.getCreatedAtTime());
    }



    @Override
    public void getTitle(Task task) {
        mView.setTitle(task.getTitle());
    }

    @Override
    public void getPhotos(Task task) {
        mView.setPhotos(task.getPhotos());
    }

    @Override
    public void getDescription(Task task) {
        mView.setDescription(task.getDescription());
    }
}
