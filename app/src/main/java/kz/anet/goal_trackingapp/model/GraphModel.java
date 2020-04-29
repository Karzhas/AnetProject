package kz.anet.goal_trackingapp.model;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
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
    public Single<BarData> getGraphData() {
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

    private BarData populateDataPoints(List<Task> modelTask) {
        int[] counterByMonth = new int[13];
        for(Task task : modelTask){
            if(!task.getDone())
                continue;
            int currentMonth = TimeUtils.getIntMonthFromDate(task.getFinishedAtDate());
            counterByMonth[currentMonth]++;

        }
        List<BarEntry> entries = new ArrayList<>();
        for(int i = 0; i <= 11; i++){
            entries.add(new BarEntry(i, counterByMonth[i+1]));
        }
        BarDataSet set = new BarDataSet(entries, "");
        return new BarData(set);
    }


}
