package kz.anet.goal_trackingapp.di.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import kz.anet.goal_trackingapp.di.scopes.AppScope;

@AppScope
@Module
public class ContextModule {
    private Context mContext;

    public ContextModule(Context context) {
        mContext = context;
    }

    @Provides
    @AppScope
    Context provideContext(){
        return mContext;
    }
}
