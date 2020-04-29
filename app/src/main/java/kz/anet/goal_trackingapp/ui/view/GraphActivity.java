package kz.anet.goal_trackingapp.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kz.anet.goal_trackingapp.root.GoalTrackingApp;
import kz.anet.goal_trackingapp.ui.GraphXAxisFormatter;
import kz.anet.goal_trackingapp.ui.contracts.GraphContract;
import kz.anet.goal_trackingapp.R;
import kz.anet.goal_trackingapp.di.components.DaggerGraphComponent;

public class GraphActivity extends AppCompatActivity implements GraphContract.View {
    private BarChart mGraph;
    @Inject
    public GraphContract.Presenter mPresenter;
    private ImageButton mImgBtnTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        mGraph = findViewById(R.id.graph);
        mImgBtnTask = findViewById(R.id.taskbtn);
        DaggerGraphComponent.builder()
                .appComponent(((GoalTrackingApp)getApplicationContext()).getAppComponent())
                .build().inject(this);
        setupGraph();
        mPresenter.onAttach(this);
        mPresenter.getGraphData();
        mImgBtnTask.setOnClickListener((view) -> startActivity(new Intent(this, TaskListActivity.class)));
        //s();
    }

    private void s() {
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 30f));
        entries.add(new BarEntry(11f, 60f));

        BarDataSet set = new BarDataSet(entries, "BarDataSet");
        BarData data = new BarData(set);


    }

    private void setupGraph() {
        mGraph.getDescription().setEnabled(false);
    }

    @Override
    public void showGraph(BarData data) {
        data.setBarWidth(0.3f); // set custom bar width
        mGraph.setData(data);
        mGraph.setFitBars(true); // make the x-axis fit exactly all bars
        mGraph.getXAxis().setValueFormatter(new GraphXAxisFormatter());
        mGraph.setDragEnabled(true); // on by default
        mGraph.setVisibleXRange(0,8); // sets the viewport to show 3 bars
        mGraph.invalidate();

    }
}
