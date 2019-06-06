package longnd.thesis.di;

import android.app.Application;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import longnd.thesis.MainApplication;
import longnd.thesis.di.module.ActivityBuildersModule;
import longnd.thesis.di.module.AppModule;
import longnd.thesis.di.module.PsychologicalDBModule;
import longnd.thesis.di.module.ViewModelModule;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        ActivityBuildersModule.class,
        PsychologicalDBModule.class,
        ViewModelModule.class
})
public interface AppComponent {

    void inject(MainApplication tvMazeApplication);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
