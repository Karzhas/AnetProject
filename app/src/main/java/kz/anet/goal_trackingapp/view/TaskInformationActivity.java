package kz.anet.goal_trackingapp.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

import kz.anet.goal_trackingapp.MvpContract.TaskInformationContract;
import kz.anet.goal_trackingapp.R;
import kz.anet.goal_trackingapp.SlidingImagesAdapter;
import kz.anet.goal_trackingapp.Task;
import kz.anet.goal_trackingapp.presenter.TaskInformationPresenter;

public class TaskInformationActivity extends AppCompatActivity
            implements TaskInformationContract.View {

    private ViewPager mViewPagerImages;
    private SlidingImagesAdapter mSlidingImagesAdapter;
    private Intent data;
    private TaskInformationContract.Presenter mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_information);
        data = getIntent();
        mViewPagerImages = findViewById(R.id.view_pager_images);
        mSlidingImagesAdapter = new SlidingImagesAdapter(this);
        mViewPagerImages.setAdapter(mSlidingImagesAdapter);
        mPresenter = new TaskInformationPresenter();
        setup();
    }

    public void setup(){
        Task currentTask = data.getParcelableExtra("task");
        mPresenter.getCreatedAtDate(currentTask);
        mPresenter.getCreatedAtTime(currentTask);
        mPresenter.getDescription(currentTask);
        mPresenter.getFinishedAt(currentTask);
        mPresenter.getPhotos(currentTask);
        mPresenter.getTitle(currentTask);
        mPresenter.getStatus(currentTask);
    }

    @Override
    public void setPhotos(List<String> photos) {
        mSlidingImagesAdapter.setImagesUrl(photos);
        mSlidingImagesAdapter.notifyDataSetChanged();
    }

    @Override
    public void setStatus(Boolean isDone) {

    }

    @Override
    public void setCreatedAtDate(String createdAtDate) {

    }

    @Override
    public void setCreatedAtTime(String createdAtTime) {

    }

    @Override
    public void setFinishedAt(String finishedAt) {

    }

    @Override
    public void setTitle(String title) {

    }



    @Override
    public void setDescription(String description) {

    }
}
