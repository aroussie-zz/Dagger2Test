package com.example.alexandreroussiere.bmwlocation.dagger;

import com.example.alexandreroussiere.bmwlocation.webService.WebServiceInterface;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alexandreroussiere on 6/30/17.
 * TestModule that we can use to give us a Mock object of the retrofit instance for testing
 */
@Module
public class TestNetworkModule {

    @Singleton
    @Provides
    public WebServiceInterface provideRetrofitInterface(){
        return Mockito.mock(WebServiceInterface.class);
    }

}
