package kz.anet.goal_trackingapp.di.modules;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import kz.anet.goal_trackingapp.di.scopes.AppScope;

@Module
@AppScope
public class RxModule {
    @Provides
    @AppScope
    static CompositeDisposable provideCompositeDisposable(){
        return new CompositeDisposable();
    }

//    @Provides
//    @ActivityScope
//    static SchedulerProvider provideSchedulerProvider(){
//        return new SchedulerProviderImpl();
//    }
}
