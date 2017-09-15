package com.example.alexandreroussiere.bmwlocation.dagger;

import android.content.Context;

import com.example.alexandreroussiere.bmwlocation.ui.locationDetails.LocationDetailsPresenter;
import com.example.alexandreroussiere.bmwlocation.ui.locationList.LocationListPresenter;
import com.example.alexandreroussiere.bmwlocation.ui.MainActivityPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alexandreroussiere on 6/28/17.
 * Dagger module which provides the implementation of every presenter
 */

@Module
public class PresenterModule {

    @Singleton
    @Provides
    MainActivityPresenter provideMainActivityPresenter(Context context){
        return new MainActivityPresenter(context);
    }

    @Singleton
    @Provides
    LocationListPresenter provideLocationListPresenter(Context context){
        return new LocationListPresenter(context);
    }

    @Singleton
    @Provides
    LocationDetailsPresenter provideLocationDetailsPresenter(Context context){
        return new LocationDetailsPresenter(context);
    }



}
