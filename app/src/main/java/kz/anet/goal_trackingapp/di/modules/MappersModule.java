package kz.anet.goal_trackingapp.di.modules;

import dagger.Module;
import dagger.Provides;
import kz.anet.goal_trackingapp.data.TaskMapper;
import kz.anet.goal_trackingapp.di.scopes.AppScope;

@AppScope
@Module
public class MappersModule {

    @AppScope
    @Provides
    TaskMapper provideMapper(){
        return new TaskMapper();
    }
}
