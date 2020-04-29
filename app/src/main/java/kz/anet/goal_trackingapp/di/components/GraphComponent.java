package kz.anet.goal_trackingapp.di.components;

import dagger.Component;
import kz.anet.goal_trackingapp.di.modules.GraphModule;
import kz.anet.goal_trackingapp.di.scopes.ActivityScope;
import kz.anet.goal_trackingapp.ui.view.GraphActivity;

@ActivityScope
@Component(dependencies = {AppComponent.class}, modules = {GraphModule.class})
public interface GraphComponent {

    void inject(GraphActivity activity);
//    @Component.Builder
//    interface Builder{
//        TaskListComponent build();
//        TaskListComponent.Builder appComponent(AppComponent appComponent);
//    }

}
