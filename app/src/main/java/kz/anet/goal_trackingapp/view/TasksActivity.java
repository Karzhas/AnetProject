package kz.anet.goal_trackingapp.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import kz.anet.goal_trackingapp.MvpContract.TasksContract;
import kz.anet.goal_trackingapp.R;
import kz.anet.goal_trackingapp.Task;
import kz.anet.goal_trackingapp.TasksAdapter;
import kz.anet.goal_trackingapp.presenter.TasksPresenter;

public class TasksActivity extends AppCompatActivity implements TasksContract.View {
    private RecyclerView tasksRecycler;
    private TasksAdapter tasksAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Task> tasks;
    private TasksContract.Presenter presenter;
    private FloatingActionButton fab;
    private ImageButton graphBtn;
    private ImageButton tasksBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        tasksRecycler = findViewById(R.id.tasks_rv);
        fab = findViewById(R.id.fab);
        graphBtn = findViewById(R.id.graphbtn);
        tasksBtn = findViewById(R.id.taskbtn);
        tasks = new ArrayList<>();

        presenter = new TasksPresenter(this);
        presenter.onAttach(this);
        Task t1 = new Task();
        t1.setTitle("Learn programming");
        t1.setCreatedAtDate("Wed. 13 Jul");
        t1.setCreatedAtTime("03:59pm");
        t1.setDone(false);
        Task t2 = new Task();
        t2.setTitle("Cooking");
        t2.setDone(true);
        t2.setCreatedAtDate("Sat. 01 Jan");
        t2.setCreatedAtTime("11:02pm");

        presenter.insertTask(t1);
        presenter.insertTask(t2);

        presenter.getTasks();

        tasksRecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        tasksRecycler.setLayoutManager(layoutManager);
        tasksAdapter = new TasksAdapter(this);
        tasksRecycler.setAdapter(tasksAdapter);


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
                Toast.makeText(TasksActivity.this, "FAB", Toast.LENGTH_SHORT).show();
            }
        });
        graphBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TasksActivity.this, "GRAPH", Toast.LENGTH_SHORT).show();
            }
        });



    }


    @Override
    public void showTasks(List<Task> tasks) {
        tasksAdapter.setTasks(tasks);
        tasksAdapter.notifyDataSetChanged();
        for(Task task : tasks){
            Log.d("test", task.getTitle() + " " + task.getDone());
        }
    }

}
