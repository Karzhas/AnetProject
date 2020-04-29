package kz.anet.goal_trackingapp.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kz.anet.goal_trackingapp.GoalTrackingApp;
import kz.anet.goal_trackingapp.GraphXAxisFormatter;
import kz.anet.goal_trackingapp.MvpContract.GraphContract;
import kz.anet.goal_trackingapp.R;
import kz.anet.goal_trackingapp.di.components.DaggerGraphComponent;

public class GraphActivity extends AppCompatActivity implements GraphContract.View {
    private BarChart graph;
    @Inject
    GraphContract.Presenter mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        graph = findViewById(R.id.graph);
        DaggerGraphComponent.builder()
                .appComponent(((GoalTrackingApp)getApplicationContext()).getAppComponent())
                .build().inject(this);
        setupGraph();
        mPresenter.onAttach(this);
       // mPresenter.getGraphData();
        s();
    }

    private void s() {
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 30f));
        entries.add(new BarEntry(1f, 80f));
        entries.add(new BarEntry(2f, 60f));
        entries.add(new BarEntry(3f, 50f));
        entries.add(new BarEntry(4f, 50f));
        entries.add(new BarEntry(5f, 70f));
        entries.add(new BarEntry(6f, 60f));
        entries.add(new BarEntry(7f, 60f));
        entries.add(new BarEntry(8f, 60f));
        entries.add(new BarEntry(9f, 60f));
        entries.add(new BarEntry(10f, 60f));
        entries.add(new BarEntry(11f, 60f));

        BarDataSet set = new BarDataSet(entries, "BarDataSet");
        BarData data = new BarData(set);
        data.setBarWidth(0.3f); // set custom bar width
        graph.setData(data);
        graph.setFitBars(true); // make the x-axis fit exactly all bars
        graph.getXAxis().setValueFormatter(new GraphXAxisFormatter());

        graph.setDragEnabled(true); // on by default
        graph.setVisibleXRange(0,8); // sets the viewport to show 3 bars
        graph.invalidate();

    }

    private void setupGraph() {



    }

    @Override
    public void showGraph(DataPoint[] dataPoints) {

    }
}
