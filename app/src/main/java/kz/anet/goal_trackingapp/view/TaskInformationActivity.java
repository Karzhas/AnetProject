package kz.anet.goal_trackingapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

import kz.anet.goal_trackingapp.MvpContract.TaskInformationContract;
import kz.anet.goal_trackingapp.R;
import kz.anet.goal_trackingapp.Task;
import kz.anet.goal_trackingapp.adapter.SlidingImagesAdapter;
import kz.anet.goal_trackingapp.presenter.TaskInformationPresenter;

public class TaskInformationActivity extends AppCompatActivity
            implements TaskInformationContract.View {

    private ViewPager mViewPagerImages;
    private SlidingImagesAdapter mSlidingImagesAdapter;
    private Intent data;
    private TaskInformationContract.Presenter mPresenter;
    private EditText edTitle;
    private EditText edDesc;
    private EditText edDate;
    private EditText edTime;
    private MenuItem menuEdit;
    private MenuItem menuSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_information);
        data = getIntent();
        mViewPagerImages = findViewById(R.id.view_pager_images);
        edTitle = findViewById(R.id.ed_title);
        edDesc = findViewById(R.id.ed_desc);
        edDate = findViewById(R.id.ed_date);
        edTime = findViewById(R.id.ed_time);

        mSlidingImagesAdapter = new SlidingImagesAdapter(this);
        mViewPagerImages.setAdapter(mSlidingImagesAdapter);
        mPresenter = new TaskInformationPresenter(this);
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
        edDate.setText(createdAtDate);
    }

    @Override
    public void setCreatedAtTime(String createdAtTime) {
        edTime.setText(createdAtTime);
    }

    @Override
    public void setFinishedAt(String finishedAt) {

    }

    @Override
    public void setTitle(String title) {
        edTitle.setText(title);
    }



    @Override
    public void setDescription(String description) {
        edDesc.setText(description);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        menuEdit = menu.findItem(R.id.edit);
        menuSave = menu.findItem(R.id.save);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.edit){
            onEditClicked();
            return true;
        }else if(id == R.id.save){
            onSaveClicked();
            return true;
        }else
            return super.onOptionsItemSelected(item);
    }

    private void onSaveClicked() {
        int resultCode = 10;
        Intent resultIntent = new Intent();
        Task updatedTask = data.getParcelableExtra("task");
        updatedTask.setTitle(edTitle.getText().toString());
        updatedTask.setCreatedAtTime(edTime.getText().toString());
        updatedTask.setCreatedAtDate(edDate.getText().toString());
        updatedTask.setDescription(edDesc.getText().toString());
        resultIntent.putExtra("updatedTask", updatedTask);
        setResult(resultCode, resultIntent);
        finish();
    }

    private void onEditClicked() {
        edDesc.setFocusableInTouchMode(true);
        edDate.setFocusableInTouchMode(true);
        edTitle.setFocusableInTouchMode(true);
        edTime.setFocusableInTouchMode(true);
        edDesc.setFocusableInTouchMode(true);
        menuEdit.setVisible(false);
        menuSave.setVisible(true);
    }
}
