package kz.anet.goal_trackingapp.ui.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kz.anet.goal_trackingapp.R;
import kz.anet.goal_trackingapp.adapter.TaskImagesAdapter;
import kz.anet.goal_trackingapp.constants.Request;
import kz.anet.goal_trackingapp.constants.Result;
import kz.anet.goal_trackingapp.listener.OnImageDeleteClickListener;
import kz.anet.goal_trackingapp.models.Task;
import kz.anet.goal_trackingapp.ui.contracts.TaskAddContract;
import kz.anet.goal_trackingapp.ui.presenter.TaskAddPresenter;
import kz.anet.goal_trackingapp.utils.TimeUtils;

public class TaskAddActivity extends AppCompatActivity
                            implements TaskAddContract.View, OnImageDeleteClickListener {


    private Button btnAddPhoto;
    private RecyclerView rvPhotos;
    private TaskImagesAdapter mTaskImagesAdapter;
    private EditText edTitle;
    private EditText edDescription;
    private EditText txtTime;
    private EditText txtDate;
    private Button btnAddTask;
    private ImageView imgDeletePhoto;
    private TaskAddContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        btnAddPhoto = findViewById(R.id.btn_add_photo);
        rvPhotos = findViewById(R.id.rv_photos);
        txtTime = findViewById(R.id.ed_time);
        edTitle = findViewById(R.id.ed_title);
        edDescription = findViewById(R.id.ed_description);
        txtDate = findViewById(R.id.ed_date);
        btnAddTask = findViewById(R.id.btn_add_task);
        imgDeletePhoto = findViewById(R.id.img_delete_photo);


        mTaskImagesAdapter = new TaskImagesAdapter(this, this);
        rvPhotos.setHasFixedSize(true);
        rvPhotos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvPhotos.setAdapter(mTaskImagesAdapter);

        mPresenter = new TaskAddPresenter(this);
        btnAddPhoto.setOnClickListener(this::onAddPhotoClicked);
        btnAddTask.setOnClickListener(this::onAddTaskClicked);
        txtDate.setOnClickListener(this::onSelectDateClicked);
        txtTime.setOnClickListener(this::onSelectTimeClicked);

        if(savedInstanceState != null){
            ArrayList<String> savedImages = savedInstanceState.getStringArrayList("images");
            mPresenter.addNewImages(savedImages);
        }

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        ArrayList<String> images = (ArrayList) mTaskImagesAdapter.getImages();
        outState.putStringArrayList("images", images);
        super.onSaveInstanceState(outState);
    }

    private void onSelectTimeClicked(View view) {
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(TaskAddActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String formattedTime = TimeUtils.getFormattedTime(selectedMinute, selectedHour);
                txtTime.setText(formattedTime);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private void onSelectDateClicked(View view) {
        Calendar currentDate = Calendar.getInstance();
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH);
        int day = currentDate.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog mDatePicker;
        mDatePicker = new DatePickerDialog(TaskAddActivity.this, (datepicker, selectedYear, selectedMonth, selectedDay) -> {
            String formattedDate = TimeUtils.getFormattedDate(selectedDay, selectedMonth+1, selectedYear);
            txtDate.setText(formattedDate);
        }, year, month, day);
        mDatePicker.setTitle("Select Date");
        mDatePicker.show();
    }

    private void onAddTaskClicked(View view) {
        String title = edTitle.getText().toString();
        String desc = edDescription.getText().toString();
        String date = txtDate.getText().toString();
        String time = txtTime.getText().toString();
        mPresenter.constructNewTask(title,desc,date,time);
    }

    private void onAddPhotoClicked(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), Request.REQUEST_IMAGE_PICK);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Request.REQUEST_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Request.REQUEST_IMAGE_PICK){
            if(resultCode == RESULT_OK && data != null && data.getData() != null){
                mPresenter.addNewImage(data.getData().toString());
            }
        }

    }


    @Override
    public void showImages(List<String> images) {
        mTaskImagesAdapter.setImages(images);
        rvPhotos.setVisibility(View.VISIBLE);
        mTaskImagesAdapter.notifyDataSetChanged();
    }

    @Override
    public void returnConstructedTask(Task task) {
        int resultCode = Result.OK;
        Intent resultIntent = new Intent();
        resultIntent.putExtra("newTask", task);
        setResult(resultCode, resultIntent);
        finish();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDeleteImageClick(String imagePath, Boolean isLastImage) {
        mPresenter.deleteImage(imagePath);
        if(isLastImage)
            rvPhotos.setVisibility(View.GONE);
    }
}
