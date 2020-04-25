package kz.anet.goal_trackingapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kz.anet.goal_trackingapp.R;

public class SlidingImagesAdapter extends PagerAdapter {

    private List<String> mImagesUrl;
    private LayoutInflater mInflater;
    private Context mContext;

    public SlidingImagesAdapter(Context context) {
        this.mContext = context;
        mImagesUrl = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
    }


    public void setImagesUrl(List<String> imagesUrl) {
        mImagesUrl = imagesUrl;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mImagesUrl.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup view, int position) {
        View imageLayout = mInflater.inflate(R.layout.sliding_image, view, false);
        ImageView imageView = imageLayout.findViewById(R.id.branchImage);
        TextView indicator = imageLayout.findViewById(R.id.text_indicator);

        String imagePath = mImagesUrl.get(position);
        Uri uri = Uri.parse(imagePath);
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uri);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String imagesCounter = (position + 1) + "/" + mImagesUrl.size();
        indicator.setText(imagesCounter);
        view.addView(imageLayout);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}

