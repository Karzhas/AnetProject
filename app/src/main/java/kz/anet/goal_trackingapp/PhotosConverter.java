package kz.anet.goal_trackingapp;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PhotosConverter {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @TypeConverter
    public String fromPhotos(List<String> photos) {
        return photos.stream().collect(Collectors.joining(","));
    }

    @TypeConverter
    public List<String> toPhotos(String data) {
        return Arrays.asList(data.split(","));
    }
}
