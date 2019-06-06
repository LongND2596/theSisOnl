package longnd.thesis.di.module;

import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import longnd.thesis.di.ViewModelFactory;

@Module
public abstract class ViewModelFactoryModule {
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory viewModelFactory);
}
