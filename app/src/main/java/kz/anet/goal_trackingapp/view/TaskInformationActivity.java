package kz.anet.goal_trackingapp.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.Calendar;
import java.util.List;

import kz.anet.goal_trackingapp.MvpContract.TaskInformationContract;
import kz.anet.goal_trackingapp.R;
import kz.anet.goal_trackingapp.Task;
import kz.anet.goal_trackingapp.adapter.SlidingImagesAdapter;
import kz.anet.goal_trackingapp.constants.Result;
import kz.anet.goal_trackingapp.presenter.TaskInformationPresenter;
import kz.anet.goal_trackingapp.utils.TimeUtils;

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
        setupTask();

    }

    public void onDateClicked(View view){
        Calendar currentDate = Calendar.getInstance();
        int mYear = currentDate.get(Calendar.YEAR);
        int mMonth = currentDate.get(Calendar.MONTH);
        int mDay = currentDate.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog mDatePicker;
        mDatePicker = new DatePickerDialog(TaskInformationActivity.this, (datepicker, selectedYear, selectedMonth, selectedDay) -> {
            String formattedDate = TimeUtils.getFormattedDate(selectedDay, selectedMonth+1, selectedYear);
            edDate.setText(formattedDate);
        }, mYear, mMonth, mDay);
        mDatePicker.setTitle("Select Date");
        mDatePicker.show();
    }

    public void onTimeClicked(View view){
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(TaskInformationActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String formattedTime = TimeUtils.getFormattedTime(selectedMinute, selectedHour);
                edTime.setText(formattedTime);
            }
        }, hour, minute, true);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }


    public void setupTask(){
        Task currentTask = data.getParcelableExtra("task");
        mPresenter.getCreatedAtDate(currentTask);
        mPresenter.getCreatedAtTime(currentTask);
        mPresenter.getDescription(currentTask);
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
        int resultCode = Result.OK;
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
        edTitle.setFocusableInTouchMode(true);
        edDesc.setFocusableInTouchMode(true);
        menuEdit.setVisible(false);
        menuSave.setVisible(true);
        edDate.setOnClickListener(this::onDateClicked);
        edTime.setOnClickListener(this::onTimeClicked);
    }
}
