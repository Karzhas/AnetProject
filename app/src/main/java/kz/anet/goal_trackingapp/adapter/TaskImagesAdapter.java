package kz.anet.goal_trackingapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kz.anet.goal_trackingapp.R;
import kz.anet.goal_trackingapp.listener.OnImageDeleteClickListener;

public class TaskImagesAdapter extends RecyclerView.Adapter<TaskImagesAdapter.ImageViewHolder> {

    private List<String> mImages;
    private Context mContext;
    private OnImageDeleteClickListener mListener;

    public TaskImagesAdapter(Context context, OnImageDeleteClickListener onImageDeleteClickListener) {
        this.mContext = context;
        mImages = new ArrayList<>();
        mListener = onImageDeleteClickListener;
    }

    public void setImages(List<String> images) {
        this.mImages = images;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_new_photo, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        String imagePath = mImages.get(position);
        Uri uri = Uri.parse(imagePath);
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uri);
            holder.image.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getImages() {
        return mImages;
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }


    class ImageViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        ImageView deleteImage;
        ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img_photo);
            deleteImage = itemView.findViewById(R.id.img_delete_photo);

            deleteImage.setOnClickListener((view)->{
                Boolean isLastImage = mImages.size() == 1;
                mListener.onDeleteImageClick(mImages.get(getAdapterPosition()), isLastImage);
            });

        }
    }

}
