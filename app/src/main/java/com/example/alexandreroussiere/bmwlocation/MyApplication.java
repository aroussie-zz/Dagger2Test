package com.example.alexandreroussiere.bmwlocation;

import android.app.Application;

import com.example.alexandreroussiere.bmwlocation.dagger.ApplicationComponent;
import com.example.alexandreroussiere.bmwlocation.dagger.ApplicationModule;
import com.example.alexandreroussiere.bmwlocation.dagger.DaggerApplicationComponent;

/**
 * Created by alexandreroussiere on 6/28/17.
 */

public class MyApplication extends Application {

    private ApplicationComponent mAppComponent;

    @Override
    public void onCreate(){
        super.onCreate();
        mAppComponent = initDagger(this);
    }

    public ApplicationComponent getAppComponent() {
        return mAppComponent;
    }

    protected ApplicationComponent initDagger(MyApplication application){
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(application))
                .build();
    }
}
