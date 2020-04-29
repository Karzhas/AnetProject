package kz.anet.goal_trackingapp.di.modules;


import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import kz.anet.goal_trackingapp.MvpContract.GraphContract;
import kz.anet.goal_trackingapp.TaskMapper;
import kz.anet.goal_trackingapp.TaskRoomRepository;
import kz.anet.goal_trackingapp.di.scopes.ActivityScope;
import kz.anet.goal_trackingapp.model.GraphModel;
import kz.anet.goal_trackingapp.presenter.GraphPresenter;

@ActivityScope
@Module
public class GraphModule {
    @ActivityScope
    @Provides
    GraphContract.Presenter providePresenter(GraphContract.Model model, CompositeDisposable compositeDisposable){
        return new GraphPresenter(model, compositeDisposable);
    }
    @ActivityScope
    @Provides
    GraphContract.Model provideModel(TaskRoomRepository taskRoomRepository, TaskMapper mapper){
        return new GraphModel(taskRoomRepository, mapper);
    }

}
