package kz.anet.goal_trackingapp.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
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

import java.util.Calendar;
import java.util.List;

import kz.anet.goal_trackingapp.MvpContract.TaskAddContract;
import kz.anet.goal_trackingapp.R;
import kz.anet.goal_trackingapp.Task;
import kz.anet.goal_trackingapp.adapter.TaskImagesAdapter;
import kz.anet.goal_trackingapp.listener.OnImageDeleteClickListener;
import kz.anet.goal_trackingapp.presenter.TaskAddPresenter;

public class TaskAddActivity extends AppCompatActivity
                            implements TaskAddContract.View, OnImageDeleteClickListener {


    private int PICK_IMAGE_REQUEST = 1;
    private final int REQUEST_CODE_ASK_PERMISSIONS = 2;
    Button btnAddPhoto;
    RecyclerView rvPhotos;
    TaskImagesAdapter mTaskImagesAdapter;
    EditText edTitle;
    EditText edDescription;
    EditText txtTime;
    EditText txtDate;
    Button btnAddTask;
    ImageView imgDeletePhoto;
    TaskAddContract.Presenter mPresenter;

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

        btnAddPhoto.setOnClickListener((view)->{
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        });

        btnAddTask.setOnClickListener((view)->{
            String title = edTitle.getText().toString();
            String desc = edDescription.getText().toString();
            String date = txtDate.getText().toString();
            String time = txtTime.getText().toString();
            mPresenter.constructNewTask(title,desc,date,time);
        });


        txtDate.setOnClickListener((view)->{
            Calendar mcurrentDate = Calendar.getInstance();
            int mYear = mcurrentDate.get(Calendar.YEAR);
            int mMonth = mcurrentDate.get(Calendar.MONTH);
            int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog mDatePicker;
            mDatePicker = new DatePickerDialog(TaskAddActivity.this, (datepicker, selectedyear, selectedmonth, selectedday) -> {
                selectedmonth = selectedmonth + 1;
                txtDate.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
            }, mYear, mMonth, mDay);
            mDatePicker.setTitle("Select Date");
            mDatePicker.show();
        });

        txtTime.setOnClickListener((view)->{
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(TaskAddActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    txtTime.setText( selectedHour + ":" + selectedMinute);
                }
            }, hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // do your stuff
                } else {
                    Toast.makeText(this, "GET_ACCOUNTS Denied",
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
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mPresenter.addNewImage(data.getData().toString());
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
        int resultCode = 10;
        Intent resultIntent = new Intent();
        resultIntent.putExtra("newTask", task);
        setResult(resultCode, resultIntent);
        finish();
    }

    @Override
    public void onDeleteImageClick(String imagePath, Boolean isLastImage) {
        mPresenter.deleteImage(imagePath);
        if(isLastImage)
            rvPhotos.setVisibility(View.GONE);
    }
}
