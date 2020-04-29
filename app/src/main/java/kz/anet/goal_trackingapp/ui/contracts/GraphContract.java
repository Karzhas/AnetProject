package kz.anet.goal_trackingapp.ui.contracts;

import com.github.mikephil.charting.data.BarData;

import io.reactivex.Single;

public interface GraphContract {

    interface View{
        void showGraph(BarData data);
    }
    interface Presenter{
        void getGraphData();
        void onAttach(GraphContract.View v);
        void onDetach();
    }
    interface Model{
        Single<BarData> getGraphData();
    }



}
