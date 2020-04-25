package kz.anet.goal_trackingapp.MvpContract;

import java.util.List;

import kz.anet.goal_trackingapp.Task;

public interface TaskAddContract {

    interface View{
        void showImages(List<String> images);
        void returnConstructedTask(Task task);
    }
    interface Presenter{
        void addNewImage(String imagePath);
        void deleteImage(String imagePath);
        void constructNewTask(String title, String desc, String date, String time);
    }
    interface Model{
        void addNewImage(String imagePath);
        Task constructNewTask(String title, String desc, String date, String time);
        List<String> getAllImages();
        List<String> deleteImage(String imagePath);
    }

}
