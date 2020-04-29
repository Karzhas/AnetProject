package kz.anet.goal_trackingapp.ui.contracts;

import java.util.List;

import kz.anet.goal_trackingapp.models.Task;

public interface TaskInformationContract {

    interface View{
        void setStatus(Boolean isDone);
        void setCreatedAtDate(String createdAtDate);
        void setCreatedAtTime(String createdAtTime);
        void setTitle(String title);
        void setPhotos(List<String>photos);
        void setDescription(String description);
    }
    interface Presenter{
        void getStatus(Task task);
        void getCreatedAtDate(Task task);
        void getCreatedAtTime(Task task);
        void getTitle(Task task);
        void getPhotos(Task task);
        void getDescription(Task task);

    }

}
