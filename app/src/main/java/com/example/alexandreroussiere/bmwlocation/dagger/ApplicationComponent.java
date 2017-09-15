package com.example.alexandreroussiere.bmwlocation.dagger;

import com.example.alexandreroussiere.bmwlocation.ui.locationDetails.LocationDetailsFragment;
import com.example.alexandreroussiere.bmwlocation.ui.locationList.LocationListFragment;
import com.example.alexandreroussiere.bmwlocation.ui.locationList.LocationListPresenter;
import com.example.alexandreroussiere.bmwlocation.ui.MainActivity;

import javax.inject.Singleton;
import dagger.Component;

/**
 * Created by alexandreroussiere on 6/28/17.
 * Dagger component which is responsible for injecting all the dependency where needed.
 * I'm sure there must be a nicer way to implement it without having all those modules in one
 * component, but I haven't found it...
 */
@Singleton
@Component(modules = {ApplicationModule.class, PresenterModule.class, NetworkModule.class})
public interface ApplicationComponent {
    void inject(MainActivity mainActivity);
    void inject(LocationListPresenter locationListPresenter);
    void inject(LocationListFragment locationListFragment);
    void inject(LocationDetailsFragment locationDetailsFragment);
}
