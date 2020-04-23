package kz.anet.goal_trackingapp.presenter;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import kz.anet.goal_trackingapp.DoneTasksComparator;
import kz.anet.goal_trackingapp.MvpContract.TasksContract;
import kz.anet.goal_trackingapp.Task;
import kz.anet.goal_trackingapp.TaskDto;
import kz.anet.goal_trackingapp.TaskMapper;
import kz.anet.goal_trackingapp.model.TaskModel;

public class TasksPresenter implements TasksContract.Presenter {

    private TasksContract.View view;
    private TasksContract.Model model;
    private Context context;
    private CompositeDisposable mSubscriptions;
    private TaskMapper mapper;

    public TasksPresenter(Context context) {
        this.context = context;
        model = new TaskModel(context);
        mSubscriptions = new CompositeDisposable();
        mapper = new TaskMapper();
    }

    @Override
    public void showTasks() {
        Disposable disposable = model.getTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map((unsortedList) -> {
                    Collections.sort(unsortedList, new DoneTasksComparator());
                    return unsortedList;
                })
                .map((sortedListDto)->{
                    List<Task> sortedList = new ArrayList<>();
                    for(TaskDto dto : sortedListDto){
                        sortedList.add(mapper.toEntity(dto));
                    }
                    return sortedList;
                })
                .subscribe(
                        (tasks) -> view.showTasks(tasks),
                        (error) ->  Log.d("test", "error on get TaskDto presenter")
                );
        mSubscriptions.add(disposable);
    }

    @Override
    public void insertTask(Task task) {
        Disposable disposable = model.insertTask(mapper.toDto(task))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> view.dataChanged()
                );
        mSubscriptions.add(disposable);
    }

    @Override
    public void deleteTask(Task task) {
        Disposable disposable = model.deleteTask(mapper.toDto(task))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> view.dataChanged()
                );
        mSubscriptions.add(disposable);
    }

    @Override
    public void updateTask(Task task) {
        Boolean newMode = !task.getDone();
        task.setDone(newMode);
        Disposable disposable = model.updateTask(mapper.toDto(task))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ()-> view.dataChanged()
                );
        mSubscriptions.add(disposable);
    }

    @Override
    public void onAttach(TasksContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        view = null;
        mSubscriptions.dispose();
    }


}
