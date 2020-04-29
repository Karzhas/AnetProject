package kz.anet.goal_trackingapp.model;

import java.util.ArrayList;
import java.util.List;

import kz.anet.goal_trackingapp.MvpContract.TaskAddContract;
import kz.anet.goal_trackingapp.Task;

public class TaskAddModel implements TaskAddContract.Model {
    private List<String> images;
    public TaskAddModel(){
        images = new ArrayList<>();
    }

    @Override
    public void addNewImage(String imagePath) {
        images.add(imagePath);
    }

    @Override
    public Task constructNewTask(String title, String desc, String date, String time) {
        Task newTask = new Task();
        newTask.setTitle(title);
        newTask.setDescription(desc);
        newTask.setPhotos(images);
        newTask.setCreatedAtDate(date);
        newTask.setCreatedAtTime(time);
        newTask.setDone(false);
        newTask.setFinishedAtDate("");
        newTask.setFinishedAtTime("");

        return newTask;
    }

    @Override
    public List<String> getAllImages() {
        return images;
    }

    @Override
    public List<String> deleteImage(String imagePath) {
        List<String> newImages = new ArrayList<>();
        for(String img : images){
            if(!img.equals(imagePath))
                newImages.add(img);
        }
        images = newImages;
        return images;
    }
}
