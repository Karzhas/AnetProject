package kz.anet.goal_trackingapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Task implements Parcelable {

    private int uid;
    private Boolean isDone;
    private String createdAtDate;
    private String createdAtTime;
    private String finishedAt;
    private String title;
    private List<String> photos = new ArrayList<>();
    private String description;

    public Task() {
    }


    protected Task(Parcel in) {
        uid = in.readInt();
        byte tmpIsDone = in.readByte();
        isDone = tmpIsDone == 0 ? null : tmpIsDone == 1;
        createdAtDate = in.readString();
        createdAtTime = in.readString();
        finishedAt = in.readString();
        title = in.readString();
        photos = in.createStringArrayList();
        description = in.readString();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

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

    public String getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(String finishedAt) {
        this.finishedAt = finishedAt;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(uid);
        dest.writeByte((byte) (isDone == null ? 0 : isDone ? 1 : 2));
        dest.writeString(createdAtDate);
        dest.writeString(createdAtTime);
        dest.writeString(finishedAt);
        dest.writeString(title);
        dest.writeStringList(photos);
        dest.writeString(description);
    }
}
