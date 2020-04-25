package kz.anet.goal_trackingapp.presenter;

import java.util.List;

import kz.anet.goal_trackingapp.MvpContract.TaskAddContract;
import kz.anet.goal_trackingapp.Task;
import kz.anet.goal_trackingapp.model.TaskAddModel;

public class TaskAddPresenter implements TaskAddContract.Presenter {

    TaskAddContract.Model mModel;
    TaskAddContract.View mView;
    public TaskAddPresenter(TaskAddContract.View view){
        mView = view;
        mModel = new TaskAddModel();
    }


    @Override
    public void addNewImage(String imagePath) {
        mModel.addNewImage(imagePath);
        mView.showImages(mModel.getAllImages());
    }

    @Override
    public void deleteImage(String imagePath) {
        List<String> newImages = mModel.deleteImage(imagePath);
        mView.showImages(newImages);
    }

    @Override
    public void constructNewTask(String title, String desc, String date, String time) {
        Task task = mModel.constructNewTask(title, desc, date,time);
        mView.returnConstructedTask(task);
    }
}
