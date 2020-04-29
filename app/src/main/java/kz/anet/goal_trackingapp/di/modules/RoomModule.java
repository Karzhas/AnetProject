package kz.anet.goal_trackingapp.di.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import kz.anet.goal_trackingapp.data.db.TaskRoomRepository;
import kz.anet.goal_trackingapp.di.scopes.AppScope;

@AppScope
@Module
public class RoomModule {

    @AppScope
    @Provides
    TaskRoomRepository provideTaskRoomRepository(Context context){
        return new TaskRoomRepository(context);
    }

}
