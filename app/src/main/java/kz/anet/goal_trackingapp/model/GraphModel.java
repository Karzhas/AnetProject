package kz.anet.goal_trackingapp.model;

import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import kz.anet.goal_trackingapp.MvpContract.GraphContract;
import kz.anet.goal_trackingapp.Task;
import kz.anet.goal_trackingapp.TaskDto;
import kz.anet.goal_trackingapp.TaskMapper;
import kz.anet.goal_trackingapp.TaskRoomRepository;
import kz.anet.goal_trackingapp.utils.TimeUtils;

public class GraphModel implements GraphContract.Model {
    private TaskRoomRepository mTaskRoomRepository;
    TaskMapper mTaskMapper;
    @Inject
    public GraphModel(TaskRoomRepository taskRoomRepository, TaskMapper mapper) {
        mTaskRoomRepository = taskRoomRepository;
        mTaskMapper = mapper;
    }

    @Override
    public Single<DataPoint[]> getGraphData() {
        return mTaskRoomRepository.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map((listDto)->{
                    List<Task> modelTask = new ArrayList<>();
                    for(TaskDto dto : listDto){
                        modelTask.add(mTaskMapper.toEntity(dto));
                    }
                    return populateDataPoints(modelTask);
                });

    }

    private DataPoint[] populateDataPoints(List<Task> modelTask) {
        int[] counterByMonth = new int[13];
        for(Task task : modelTask){
            if(!task.getDone())
                continue;
            int currentMonth = TimeUtils.getIntMonthFromDate(task.getFinishedAtDate());
            counterByMonth[currentMonth]++;

        }
        DataPoint[] dataPoints = new DataPoint[3];
        List<Date> dates = TimeUtils.getListOfMonthAsDate();
        for(int i = 1; i <= 3; i++){
            dataPoints[i-1] = new DataPoint(dates.get(i-1), counterByMonth[i]);
        }
        return dataPoints;
    }


}
