package kz.anet.goal_trackingapp.presenter;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import kz.anet.goal_trackingapp.MvpContract.GraphContract;

public class GraphPresenter implements GraphContract.Presenter{
    private GraphContract.View mView;
    private GraphContract.Model mModel;
    private CompositeDisposable mSubscriptions;

    @Inject
    public GraphPresenter(GraphContract.Model model, CompositeDisposable subscriptions) {
        mModel = model;
        mSubscriptions = subscriptions;
    }

    @Override
    public void getGraphData() {
        Disposable disposable = mModel.getGraphData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (DataPoint[] dataPoints) -> {
                            mView.showGraph(dataPoints);
                        }
                );
        mSubscriptions.add(disposable);
    }

    @Override
    public void onAttach(GraphContract.View v) {
        mView = v;
    }

    @Override
    public void onDetach() {
        mView = null;
        mSubscriptions.dispose();
    }
}
