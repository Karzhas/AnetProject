package kz.anet.goal_trackingapp.ui.model;

import java.util.ArrayList;
import java.util.List;

import kz.anet.goal_trackingapp.ui.contracts.TaskAddContract;
import kz.anet.goal_trackingapp.models.Task;

public class TaskAddModel implements TaskAddContract.Model {
    private List<String> mImages;
    public TaskAddModel(){
        mImages = new ArrayList<>();
    }

    @Override
    public void addNewImage(String imagePath) {
        mImages.add(imagePath);
    }

    @Override
    public Task constructNewTask(String title, String desc, String date, String time) {
        Task newTask = new Task();
        newTask.setTitle(title);
        newTask.setDescription(desc);
        newTask.setPhotos(mImages);
        newTask.setCreatedAtDate(date);
        newTask.setCreatedAtTime(time);
        newTask.setDone(false);
        newTask.setFinishedAtDate("");
        newTask.setFinishedAtTime("");

        return newTask;
    }

    @Override
    public List<String> getAllImages() {
        return mImages;
    }

    @Override
    public List<String> deleteImage(String imagePath) {
        List<String> newImages = new ArrayList<>();
        for(String img : mImages){
            if(!img.equals(imagePath))
                newImages.add(img);
        }
        mImages = newImages;
        return mImages;
    }
}
