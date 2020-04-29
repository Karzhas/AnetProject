package kz.anet.goal_trackingapp.di.components;

import android.content.Context;

import dagger.Component;
import io.reactivex.disposables.CompositeDisposable;
import kz.anet.goal_trackingapp.data.TaskMapper;
import kz.anet.goal_trackingapp.data.db.TaskRoomRepository;
import kz.anet.goal_trackingapp.di.modules.ContextModule;
import kz.anet.goal_trackingapp.di.modules.MappersModule;
import kz.anet.goal_trackingapp.di.modules.RoomModule;
import kz.anet.goal_trackingapp.di.modules.RxModule;
import kz.anet.goal_trackingapp.di.scopes.AppScope;

@AppScope
@Component(modules = {ContextModule.class, RxModule.class, MappersModule.class, RoomModule.class})
public interface AppComponent {
    Context getAppContext();
    CompositeDisposable getCompositeDisposable();
    TaskRoomRepository getTaskRoomRepository();
    TaskMapper getTaskMapper();
}
