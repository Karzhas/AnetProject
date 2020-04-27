package kz.anet.goal_trackingapp.view;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

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
    TaskListContract.Presenter presenter;

    private FloatingActionButton fab;
    private ImageButton graphBtn;
    private ImageButton tasksBtn;
    private OnDoneClickListener mOnDoneClickListener;
    @Inject
     SwipeController mSwipeController;
    @Inject
     ItemTouchHelper mItemTouchHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        tasksRecycler = findViewById(R.id.tasks_rv);
        fab = findViewById(R.id.fab);
        graphBtn = findViewById(R.id.graphbtn);
        tasksBtn = findViewById(R.id.taskbtn);

        DaggerTaskListComponent.builder()
                .appComponent(((GoalTrackingApp)getApplicationContext()).getAppComponent())
                .taskListModule(new TaskListModule(this, this, this))
                .build().inject(this);

        mOnDoneClickListener = this;
        presenter.onAttach(this);
        tasksRecycler.setHasFixedSize(true);
        //layoutManager = new LinearLayoutManager(this);
        tasksRecycler.setLayoutManager(layoutManager);
       // tasksAdapter = new TaskListAdapter(this, mOnDoneClickListener, this);
        tasksRecycler.setAdapter(tasksAdapter);

//        mSwipeController = new SwipeController(new SwipeControllerActions() {
//            @Override
//            public void onRightClicked(int position) {
//                presenter.deleteTask(tasksAdapter.getTasks().get(position));
//            }
//        });
        mSwipeController.setContext(this);

       //mItemTouchHelper = new ItemTouchHelper(mSwipeController);
        mItemTouchHelper.attachToRecyclerView(tasksRecycler);
        tasksRecycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                mSwipeController.onDraw(c);
            }
        });

        // clicks
        tasksBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TaskListActivity.this, "TASK", Toast.LENGTH_SHORT).show();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Task t1 = new Task();
//                t1.setTitle("Learn programming");
//                t1.setCreatedAtDate("Wed. 13 Jul");
//                t1.setCreatedAtTime("03:59pm");
//                t1.setDone(false);
//                Task t2 = new Task();
//                t2.setTitle("Cooking");
//                t2.setDone(true);
//                t2.setCreatedAtDate("Sat. 01 Jan");
//                t2.setCreatedAtTime("11:02pm");
//                presenter.insertTask(t1);
//                presenter.insertTask(t2);
                startActivityForResult(new Intent(TaskListActivity.this, TaskAddActivity.class), 1);

            }
        });
        graphBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TaskListActivity.this, "GRAPH", Toast.LENGTH_SHORT).show();
            }
        });

        presenter.showTasks();


    }


    @Override
    public void showTasks(List<Task> tasks) {
        tasksAdapter.setTasks(tasks);
        tasksAdapter.notifyDataSetChanged();
    }

    @Override
    public void dataChanged() {
        presenter.showTasks();
    }

    @Override
    public void onDoneClick(Task task) {
        presenter.updateTask(task);
    }

    @Override
    public void onTaskClick(Task task) {
        Intent intent = new Intent(this, TaskInformationActivity.class);
        intent.putExtra("task", task);
        startActivityForResult(intent, 9);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 10) {// RESULT_OK
                    Task task = (Task) data.getParcelableExtra("newTask");
                    presenter.insertTask(task);
            }
        }
        if(requestCode == 9){
            if(resultCode == 10){
                Task task = (Task) data.getParcelableExtra("updatedTask");
                presenter.updateTask(task);
            }
        }
    }
}
