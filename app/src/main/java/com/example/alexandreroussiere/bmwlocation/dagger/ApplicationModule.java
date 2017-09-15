package com.example.alexandreroussiere.bmwlocation.dagger;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alexandreroussiere on 6/28/17.
 * Dagger module that will be used to get the context of the App
 * This is the first time I'm using dagger, so I hope I'm doing it well enough :)
 */

@Module
public class ApplicationModule {

    private Application mApplication;

    public ApplicationModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    @Singleton
    public Context provideContext(){
        return mApplication;
    }

}
