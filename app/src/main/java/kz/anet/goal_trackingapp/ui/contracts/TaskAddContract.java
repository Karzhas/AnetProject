package kz.anet.goal_trackingapp.ui.contracts;

import java.util.ArrayList;
import java.util.List;

import kz.anet.goal_trackingapp.models.Task;

public interface TaskAddContract {

    interface View{
        void showImages(List<String> images);
        void returnConstructedTask(Task task);
        void showMessage(String message);
    }
    interface Presenter{
        void addNewImage(String imagePath);
        void deleteImage(String imagePath);
        void constructNewTask(String title, String desc, String date, String time);
        void addNewImages(ArrayList<String> images);
    }
    interface Model{
        void addNewImage(String imagePath);
        Task constructNewTask(String title, String desc, String date, String time);
        List<String> getAllImages();
        List<String> deleteImage(String imagePath);
    }

}
