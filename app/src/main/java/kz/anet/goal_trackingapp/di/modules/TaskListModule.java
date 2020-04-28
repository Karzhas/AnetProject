package kz.anet.goal_trackingapp.di.modules;

import android.content.Context;
import android.graphics.Canvas;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import kz.anet.goal_trackingapp.MvpContract.TaskListContract;
import kz.anet.goal_trackingapp.SwipeController;
import kz.anet.goal_trackingapp.SwipeControllerActions;
import kz.anet.goal_trackingapp.TaskDto;
import kz.anet.goal_trackingapp.TaskMapper;
import kz.anet.goal_trackingapp.TaskRoomRepository;
import kz.anet.goal_trackingapp.adapter.TaskListAdapter;
import kz.anet.goal_trackingapp.di.scopes.ActivityScope;
import kz.anet.goal_trackingapp.listener.OnDoneClickListener;
import kz.anet.goal_trackingapp.listener.OnTaskClickListener;
import kz.anet.goal_trackingapp.model.TaskListModel;
import kz.anet.goal_trackingapp.presenter.TaskListPresenter;

@ActivityScope
@Module
public class TaskListModule {

    private TaskListContract.View mView;
    private OnDoneClickListener mOnDoneClickListener;
    private OnTaskClickListener mOnTaskClickListener;

    public TaskListModule(TaskListContract.View view, OnDoneClickListener onDoneClickListener,
                          OnTaskClickListener onTaskClickListener) {
        mView = view;
        mOnDoneClickListener = onDoneClickListener;
        mOnTaskClickListener = onTaskClickListener;
    }

    @ActivityScope
    @Provides
    public TaskListContract.View provideTaskListView(){
        return mView;
    }

    @ActivityScope
    @Provides
    TaskListContract.Presenter provideTasklistPresenter(
                                                        TaskListContract.Model model,
                                                        CompositeDisposable mSubscriptions,
                                                        TaskMapper mapper){
        return new TaskListPresenter(model, mSubscriptions, mapper);
    }


    @ActivityScope
    @Provides
    TaskMapper provideMapper(){
        return new TaskMapper();
    }

    @ActivityScope
    @Provides
    TaskListContract.Model provideModel(TaskRoomRepository taskRoomRepository){
        return new TaskListModel(taskRoomRepository);
    }

    @ActivityScope
    @Provides
    TaskRoomRepository provideTaskRoomRepository(Context context){
        return new TaskRoomRepository(context);
    }

    @ActivityScope
    @Provides
    List<TaskDto> provideListDto(){
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    TaskListAdapter provideTaskListAdapter(Context context, OnDoneClickListener doneClickListener,
                                           OnTaskClickListener onTaskClickListener){
        return new TaskListAdapter(context, doneClickListener, onTaskClickListener);
    }

    @ActivityScope
    @Provides
    OnDoneClickListener provideOnDoneClickListenerImpl(){
        return mOnDoneClickListener;
    }

    @ActivityScope
    @Provides
    OnTaskClickListener provideOnTaskClickListener(){
        return  mOnTaskClickListener;
    }

    @ActivityScope
    @Provides
    RecyclerView.LayoutManager provideLayoutManager(Context context){
        return new LinearLayoutManager(context);
    }


    @ActivityScope
    @Provides
    SwipeController SwipeController(SwipeControllerActions swipeControllerActions){
        return new SwipeController(swipeControllerActions);
    }

    @ActivityScope
    @Provides
    SwipeControllerActions provideSwipeControllerActions(TaskListAdapter adapter,
                                                         TaskListContract.Presenter presenter){
        return position -> presenter.deleteTask(adapter.getTasks().get(position));
    }


    @ActivityScope
    @Provides
    ItemTouchHelper provideItemTouchHelper(SwipeController swipeController){
        return new ItemTouchHelper(swipeController);
    }

    @ActivityScope
    @Provides
    RecyclerView.ItemDecoration provideItemDecor(SwipeController swipeController){
        return new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        };
    }


}
