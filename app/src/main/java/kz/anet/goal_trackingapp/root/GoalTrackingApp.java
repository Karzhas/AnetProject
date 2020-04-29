package kz.anet.goal_trackingapp.root;

import android.app.Application;

import kz.anet.goal_trackingapp.di.components.AppComponent;
import kz.anet.goal_trackingapp.di.components.DaggerAppComponent;
import kz.anet.goal_trackingapp.di.modules.ContextModule;

public class GoalTrackingApp extends Application {
    private AppComponent mAppComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder()
                .contextModule(new ContextModule(getApplicationContext()))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
