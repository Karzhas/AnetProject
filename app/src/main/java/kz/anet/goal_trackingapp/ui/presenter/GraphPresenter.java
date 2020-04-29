package kz.anet.goal_trackingapp.ui.presenter;

import com.github.mikephil.charting.data.BarData;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import kz.anet.goal_trackingapp.ui.contracts.GraphContract;

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
                        (BarData data) -> {
                            mView.showGraph(data);
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
