package kz.anet.goal_trackingapp.MvpContract;

import com.jjoe64.graphview.series.DataPoint;

import io.reactivex.Single;

public interface GraphContract {

    interface View{
        void showGraph(DataPoint[] dataPoints);
    }
    interface Presenter{
        void getGraphData();
        void onAttach(GraphContract.View v);
        void onDetach();
    }
    interface Model{
        Single<DataPoint[]> getGraphData();
    }



}
