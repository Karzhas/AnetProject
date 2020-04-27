package kz.anet.goal_trackingapp.presenter;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import kz.anet.goal_trackingapp.DoneTasksComparator;
import kz.anet.goal_trackingapp.MvpContract.TaskListContract;
import kz.anet.goal_trackingapp.Task;
import kz.anet.goal_trackingapp.TaskDto;
import kz.anet.goal_trackingapp.TaskMapper;

public class TaskListPresenter implements TaskListContract.Presenter {

    private TaskListContract.View view;
    @Inject
     TaskListContract.Model model;
    //private Context context;
    @Inject
     CompositeDisposable mSubscriptions;
    @Inject
     TaskMapper mapper;

//    public TaskListPresenter(Context context) {
//        this.context = context;
//        model = new TaskListModel(context);
//        mSubscriptions = new CompositeDisposable();
//        mapper = new TaskMapper();
//    }

    public TaskListPresenter(TaskListContract.Model model,
                             CompositeDisposable subscriptions,
                             TaskMapper mapper
                             ) {
        this.model = model;
        mSubscriptions = subscriptions;
        this.mapper = mapper;
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
    public void onAttach(TaskListContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        view = null;
        mSubscriptions.dispose();
    }


}
