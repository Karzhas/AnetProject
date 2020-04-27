package kz.anet.goal_trackingapp.di.components;

import dagger.Component;
import kz.anet.goal_trackingapp.di.modules.TaskListModule;
import kz.anet.goal_trackingapp.di.scopes.ActivityScope;
import kz.anet.goal_trackingapp.view.TaskListActivity;

@ActivityScope
@Component(dependencies = {AppComponent.class}, modules = {TaskListModule.class})
public interface TaskListComponent {
    void inject(TaskListActivity activity);

    @Component.Builder
    interface Builder{
        TaskListComponent build();
        Builder appComponent(AppComponent appComponent);
        Builder taskListModule(TaskListModule taskListModule);
    }
}
