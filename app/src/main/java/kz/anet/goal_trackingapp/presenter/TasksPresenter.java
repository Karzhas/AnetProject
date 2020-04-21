package kz.anet.goal_trackingapp.presenter;

import android.content.Context;
import android.util.Log;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import kz.anet.goal_trackingapp.MvpContract.TasksContract;
import kz.anet.goal_trackingapp.Task;
import kz.anet.goal_trackingapp.model.TaskModel;

public class TasksPresenter implements TasksContract.Presenter {

    private TasksContract.View view;
    private TasksContract.Model model;
    private Context context;
    private CompositeDisposable mSubscriptions;

    public TasksPresenter(Context context) {
        this.context = context;
        model = new TaskModel(context);
        mSubscriptions = new CompositeDisposable();
    }

    @Override
    public void getTasks() {
        Disposable disposable = model.getTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (tasks) -> view.showTasks(tasks),
                        (error) ->  Log.d("test", "error on get Task presenter")
                );
        mSubscriptions.add(disposable);
    }

    @Override
    public void insertTask(Task task) {
        Disposable disposable = model.insertTask(task)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ()-> Log.d("test", "succ insert")
                );
        mSubscriptions.add(disposable);
    }

    @Override
    public void deleteTask(Task task) {
        Disposable disposable = model.deleteTask(task)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ()-> Log.d("test", "succ insert")
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
