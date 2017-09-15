package com.example.alexandreroussiere.bmwlocation;


import com.example.alexandreroussiere.bmwlocation.dagger.TestApplicationComponent;
import com.example.alexandreroussiere.bmwlocation.dagger.TestNetworkModule;

/**
 * Created by alexandreroussiere on 6/30/17.
 */

public class TestMyApplication extends MyApplication {

    private TestApplicationComponent testApplicationComponent;

    @Override
    public TestApplicationComponent getAppComponent(){

        //For a reason that I don't know, Dagger does not want to generate the component Class from
        //TestApplicationComponent so I can't override the getter...
//        if (testApplicationComponent == null) {
//            return DaggerTestApplicationComponent.builder()
//                    .testNetworkModule(new TestNetworkModule())
//                    .build();
//        }
        return testApplicationComponent;
    }
}
