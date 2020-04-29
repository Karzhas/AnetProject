package kz.anet.goal_trackingapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import javax.inject.Inject;

import kz.anet.goal_trackingapp.GoalTrackingApp;
import kz.anet.goal_trackingapp.MvpContract.TaskListContract;
import kz.anet.goal_trackingapp.R;
import kz.anet.goal_trackingapp.SwipeController;
import kz.anet.goal_trackingapp.Task;
import kz.anet.goal_trackingapp.adapter.TaskListAdapter;
import kz.anet.goal_trackingapp.constants.Request;
import kz.anet.goal_trackingapp.constants.Result;
import kz.anet.goal_trackingapp.di.components.DaggerTaskListComponent;
import kz.anet.goal_trackingapp.di.modules.TaskListModule;
import kz.anet.goal_trackingapp.listener.OnDoneClickListener;
import kz.anet.goal_trackingapp.listener.OnTaskClickListener;

public class TaskListActivity extends AppCompatActivity implements TaskListContract.View,
                                                                OnDoneClickListener,
                                                                OnTaskClickListener {

    private RecyclerView tasksRecycler;
    @Inject
    TaskListAdapter tasksAdapter;
    @Inject
    RecyclerView.LayoutManager layoutManager;
    @Inject
    RecyclerView.ItemDecoration mItemDecoration;
    @Inject
    TaskListContract.Presenter presenter;

    private FloatingActionButton fab;
    private ImageButton graphBtn;
    private ImageButton tasksBtn;
    @Inject
    SwipeController mSwipeController;
    @Inject
    ItemTouchHelper mItemTouchHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        DaggerTaskListComponent.builder()
                .appComponent(((GoalTrackingApp)getApplicationContext()).getAppComponent())
                .taskListModule(new TaskListModule(this, this, this))
                .build().inject(this);

        tasksRecycler   = findViewById(R.id.tasks_rv);
        fab             = findViewById(R.id.fab);
        graphBtn        = findViewById(R.id.graphbtn);
        tasksBtn        = findViewById(R.id.taskbtn);

        tasksBtn.setOnClickListener(this::onTasksClicked);
        fab.setOnClickListener(this::onFabClicked);
        graphBtn.setOnClickListener(this::onGraphClicked);

        presenter.onAttach(this);
        setupRecycler();
        presenter.showTasks();


    }

    private void onTasksClicked(View view) {
    }

    private void onGraphClicked(View view) {
        startActivity(new Intent(this, GraphActivity.class));
    }

    private void onFabClicked(View v){
        startActivityForResult(new Intent(TaskListActivity.this,
                TaskAddActivity.class), Request.REQUEST_NEW_TASK);
    }

    private void setupRecycler() {
        tasksRecycler.setHasFixedSize(true);
        tasksRecycler.setLayoutManager(layoutManager);
        tasksRecycler.setAdapter(tasksAdapter);
        mSwipeController.setContext(this);
        mItemTouchHelper.attachToRecyclerView(tasksRecycler);
        tasksRecycler.addItemDecoration(mItemDecoration);
    }


    @Override
    public void showTasks(List<Task> tasks) {
        for(Task task : tasks){
            Log.d("testing", task.getFinishedAtDate() + " , " + task.getFinishedAtTime());
        }
        tasksAdapter.setTasks(tasks);
        tasksAdapter.notifyDataSetChanged();
    }

    @Override
    public void dataChanged() {
        presenter.showTasks();
    }

    @Override
    public void onDoneClick(Task task) {
        presenter.updateStatus(task);
    }

    @Override
    public void onTaskClick(Task task) {
        Intent intent = new Intent(this, TaskInformationActivity.class);
        intent.putExtra("task", task);
        startActivityForResult(intent, Request.REQUEST_UPDATE_TASK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Request.REQUEST_NEW_TASK) {
            if (resultCode == Result.OK) {// RESULT_OK
                    Task task = (Task) data.getParcelableExtra("newTask");
                    presenter.insertTask(task);
            }else if(resultCode == Result.ERROR){
                Log.d("debug", "error");
            }
        }
        if(requestCode == Request.REQUEST_UPDATE_TASK){
            if(resultCode == Result.OK){
                Task task = (Task) data.getParcelableExtra("updatedTask");
                presenter.updateTask(task);
            }else if(resultCode == Result.ERROR){
                Log.d("debug", "error");
            }
        }
    }


}
