package com.example.alexandreroussiere.bmwlocation.dagger;

import com.example.alexandreroussiere.bmwlocation.ui.locationList.TestLocationListPresenter;

import javax.inject.Singleton;
import dagger.Component;

/**
 * Created by alexandreroussiere on 6/30/17.
 * Component that injects the webInterface into TestLocationListPresenter
 */
@Singleton
@Component(modules = {NetworkModule.class})
public interface TestApplicationComponent extends ApplicationComponent {
     void inject(TestLocationListPresenter locationListPresenter);
}
