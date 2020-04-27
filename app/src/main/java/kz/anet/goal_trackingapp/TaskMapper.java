package kz.anet.goal_trackingapp;

public class TaskMapper {

    public Task toEntity(TaskDto dto){
        Task task = new Task();
        task.setUid(dto.getUid());
        task.setDone(dto.getDone());
        task.setCreatedAtTime(dto.getCreatedAtTime());
        task.setCreatedAtDate(dto.getCreatedAtDate());
        task.setFinishedAt(dto.getFinishedAt());
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
        task.setFinishedAt(item.getFinishedAt());
        task.setPhotos(item.getPhotos());
        task.setTitle(item.getTitle());
        task.setDescription(item.getDescription());
        return task;

    }
}
