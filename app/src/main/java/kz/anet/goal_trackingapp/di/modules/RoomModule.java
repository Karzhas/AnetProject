package kz.anet.goal_trackingapp.di.modules;

import dagger.Module;
import dagger.Provides;
import kz.anet.goal_trackingapp.db.TaskDao;
import kz.anet.goal_trackingapp.db.TaskDatabase;
import kz.anet.goal_trackingapp.di.scopes.AppScope;

@AppScope
@Module
public class RoomModule {

//    @AppScope
//    @Provides
//    TaskDatabase provideTaskDB(Context context){
//        return TaskDatabase.getDatabase(context);
//    }

    @AppScope
    @Provides
    TaskDao provideTaskDao(TaskDatabase db){
        return db.taskDao();
    }

}
