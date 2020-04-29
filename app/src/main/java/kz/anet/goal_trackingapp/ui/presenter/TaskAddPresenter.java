package kz.anet.goal_trackingapp.ui.presenter;

import java.util.ArrayList;
import java.util.List;

import kz.anet.goal_trackingapp.ui.contracts.TaskAddContract;
import kz.anet.goal_trackingapp.models.Task;
import kz.anet.goal_trackingapp.ui.model.TaskAddModel;

public class TaskAddPresenter implements TaskAddContract.Presenter {

    private TaskAddContract.Model mModel;
    private TaskAddContract.View mView;
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
        if(date.equals("") || time.equals("")){
            mView.showMessage("Select date and time");
        }else{
            Task task = mModel.constructNewTask(title, desc, date,time);
            mView.returnConstructedTask(task);
        }

    }

    @Override
    public void addNewImages(ArrayList<String> images) {
        for(String image : images){
            addNewImage(image);
        }
    }
}
