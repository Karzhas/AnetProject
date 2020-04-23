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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import kz.anet.goal_trackingapp.MvpContract.TasksContract;
import kz.anet.goal_trackingapp.R;
import kz.anet.goal_trackingapp.SwipeController;
import kz.anet.goal_trackingapp.SwipeControllerActions;
import kz.anet.goal_trackingapp.Task;
import kz.anet.goal_trackingapp.TaskDto;
import kz.anet.goal_trackingapp.TasksAdapter;
import kz.anet.goal_trackingapp.listener.OnDoneClickListener;
import kz.anet.goal_trackingapp.listener.OnTaskClickListener;
import kz.anet.goal_trackingapp.presenter.TasksPresenter;

public class TasksActivity extends AppCompatActivity implements TasksContract.View,
                                                                OnDoneClickListener,
                                                                OnTaskClickListener {
    private RecyclerView tasksRecycler;
    private TasksAdapter tasksAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<TaskDto> tasks;
    private TasksContract.Presenter presenter;
    private FloatingActionButton fab;
    private ImageButton graphBtn;
    private ImageButton tasksBtn;
    private OnDoneClickListener mOnDoneClickListener;
    private SwipeController mSwipeController;
    private ItemTouchHelper mItemTouchHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        tasksRecycler = findViewById(R.id.tasks_rv);
        fab = findViewById(R.id.fab);
        graphBtn = findViewById(R.id.graphbtn);
        tasksBtn = findViewById(R.id.taskbtn);


        tasks = new ArrayList<>();
        mOnDoneClickListener = this;
        presenter = new TasksPresenter(this);
        presenter.onAttach(this);
        tasksRecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        tasksRecycler.setLayoutManager(layoutManager);
        tasksAdapter = new TasksAdapter(this, mOnDoneClickListener, this);
        tasksRecycler.setAdapter(tasksAdapter);

        mSwipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                presenter.deleteTask(tasksAdapter.getTasks().get(position));
            }
        });
        mSwipeController.setContext(this);

        mItemTouchHelper = new ItemTouchHelper(mSwipeController);
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
                Toast.makeText(TasksActivity.this, "TASK", Toast.LENGTH_SHORT).show();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Task t1 = new Task();
//                Log.d("insssrt", "!!");
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
                startActivityForResult(new Intent(TasksActivity.this, TaskAddActivity.class), 1);

            }
        });
        graphBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TasksActivity.this, "GRAPH", Toast.LENGTH_SHORT).show();
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
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
