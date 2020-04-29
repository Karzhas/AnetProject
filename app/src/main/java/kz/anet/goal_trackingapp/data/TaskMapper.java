package kz.anet.goal_trackingapp.data;

import kz.anet.goal_trackingapp.data.model.TaskDto;
import kz.anet.goal_trackingapp.models.Task;

public class TaskMapper {

    public Task toEntity(TaskDto dto){
        Task task = new Task();
        task.setUid(dto.getUid());
        task.setDone(dto.getDone());
        task.setCreatedAtTime(dto.getCreatedAtTime());
        task.setCreatedAtDate(dto.getCreatedAtDate());
        task.setFinishedAtDate(dto.getFinishedAtDate());
        task.setFinishedAtTime(dto.getFinishedAtTime());
        task.setPhotos(dto.getPhotos());
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        return task;
    }
    public TaskDto toDto(Task item){
        TaskDto task = new TaskDto();
        task.setUid(item.getUid());
        task.setDone(item.getDone());
        task.setCreatedAtTime(item.getCreatedAtTime());
        task.setCreatedAtDate(item.getCreatedAtDate());
        task.setFinishedAtDate(item.getFinishedAtDate());
        task.setFinishedAtTime(item.getFinishedAtTime());
        task.setPhotos(item.getPhotos());
        task.setTitle(item.getTitle());
        task.setDescription(item.getDescription());
        return task;

    }
}
