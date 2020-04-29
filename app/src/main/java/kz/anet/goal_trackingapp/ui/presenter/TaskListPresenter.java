package kz.anet.goal_trackingapp.ui.presenter;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import kz.anet.goal_trackingapp.utils.DoneTasksComparator;
import kz.anet.goal_trackingapp.ui.contracts.TaskListContract;
import kz.anet.goal_trackingapp.models.Task;
import kz.anet.goal_trackingapp.data.model.TaskDto;
import kz.anet.goal_trackingapp.data.TaskMapper;

public class TaskListPresenter implements TaskListContract.Presenter {

    private TaskListContract.View mView;
    private TaskListContract.Model mModel;
    private CompositeDisposable mSubscriptions;
    private TaskMapper mMapper;



    public TaskListPresenter(TaskListContract.Model model,
                             CompositeDisposable subscriptions,
                             TaskMapper mapper
                             ) {
        this.mModel = model;
        mSubscriptions = subscriptions;
        this.mMapper = mapper;
    }

    @Override
    public void showTasks() {
        Disposable disposable = mModel.getTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map((unsortedList) -> {
                    Collections.sort(unsortedList, new DoneTasksComparator());
                    return unsortedList;
                })
                .map((sortedListDto)->{
                    List<Task> sortedList = new ArrayList<>();
                    for(TaskDto dto : sortedListDto){
                        sortedList.add(mMapper.toEntity(dto));
                    }
                    return sortedList;
                })
                .subscribe(
                        (tasks) -> mView.showTasks(tasks),
                        (error) ->  Log.d("test", "error on get TaskDto mPresenter")
                );
        mSubscriptions.add(disposable);
    }

    @Override
    public void insertTask(Task task) {
        Disposable disposable = mModel.insertTask(mMapper.toDto(task))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> mView.dataChanged()
                );
        mSubscriptions.add(disposable);
    }

    @Override
    public void deleteTask(Task task) {
        Disposable disposable = mModel.deleteTask(mMapper.toDto(task))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> mView.dataChanged()
                );
        mSubscriptions.add(disposable);
    }

    @Override
    public void updateTask(Task task) {
        Disposable disposable = mModel.updateTask(mMapper.toDto(task))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ()-> mView.dataChanged()
                );
        mSubscriptions.add(disposable);
    }

    @Override
    public void updateStatus(Task task) {
        Disposable disposable = mModel.updateStatus(mMapper.toDto(task))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ()-> mView.dataChanged()
                );
        mSubscriptions.add(disposable);
    }

    @Override
    public void onAttach(TaskListContract.View view) {
        this.mView = view;
    }

    @Override
    public void onDetach() {
        mView = null;
        mSubscriptions.dispose();
    }


}
