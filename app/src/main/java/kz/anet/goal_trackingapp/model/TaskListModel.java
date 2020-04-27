package kz.anet.goal_trackingapp.model;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import kz.anet.goal_trackingapp.MvpContract.TaskListContract;
import kz.anet.goal_trackingapp.TaskDto;
import kz.anet.goal_trackingapp.TaskRoomRepository;

public class TaskListModel implements TaskListContract.Model {
    @Inject
    TaskRoomRepository mTaskRoomRepository;
    @Inject
    List<TaskDto> tasks;
//    public TaskListModel(Context context) {
//        mTaskRoomRepository = new TaskRoomRepository(context);
//        tasks = new ArrayList<>();
//    }

    public TaskListModel(TaskRoomRepository taskRoomRepository, List<TaskDto> tasks) {
        mTaskRoomRepository = taskRoomRepository;
        this.tasks = tasks;
    }

    @Override
    public Single<List<TaskDto>> getTasks() {
        return mTaskRoomRepository.getAll();
    }

    @Override
    public Completable insertTask(TaskDto task) {
        return mTaskRoomRepository.insert(task);
    }

    @Override
    public Completable deleteTask(TaskDto task) {
        return mTaskRoomRepository.delete(task);
    }

    @Override
    public Completable updateTask(TaskDto task) {
        return mTaskRoomRepository.update(task);
    }
}
