package kz.anet.goal_trackingapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "Tasks")
public class TaskDto {
    @PrimaryKey(autoGenerate = true)
    private int uid;
    @ColumnInfo(name = "done")
    private Boolean isDone;
    private String createdAtDate;
    private String createdAtTime;
    private String finishedAtDate;
    private String finishedAtTime;
    private String title;
    @TypeConverters({PhotosConverter.class})
    private List<String> photos = new ArrayList<>();
    private String description;

    public TaskDto(){}



    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Boolean getDone() {
        return isDone;
    }

    public String getCreatedAtTime() {
        return createdAtTime;
    }

    public void setCreatedAtTime(String createdAtTime) {
        this.createdAtTime = createdAtTime;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public String getCreatedAtDate() {
        return createdAtDate;
    }

    public void setCreatedAtDate(String createdAtDate) {
        this.createdAtDate = createdAtDate;
    }

    public String getFinishedAtDate() {
        return finishedAtDate;
    }

    public void setFinishedAtDate(String finishedAtDate) {
        this.finishedAtDate = finishedAtDate;
    }

    public String getFinishedAtTime() {
        return finishedAtTime;
    }

    public void setFinishedAtTime(String finishedAtTime) {
        this.finishedAtTime = finishedAtTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
