package com.example.alexandreroussiere.bmwlocation.dagger;

import com.example.alexandreroussiere.bmwlocation.Constants;
import com.example.alexandreroussiere.bmwlocation.webService.WebServiceInterface;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alexandreroussiere on 6/29/17.
 * Module used to provide the Retrofit API
 */

@Module
public class NetworkModule {

    public static final String API_URL = "API_URL";

    @Provides
    @Named(API_URL)
    String providesBaseApiUrl(){
        return Constants.API_URL;
    }

    @Provides
    Converter.Factory providesGsonConverterFactory(){
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    Retrofit providesRetrofit(@Named(API_URL) String apiUrl, Converter.Factory converterFactory){
        return new Retrofit.Builder()
                .baseUrl(apiUrl)
                .addConverterFactory(converterFactory)
                .build();
    }

    @Provides
    @Singleton
    WebServiceInterface providesWebServiceInterface(Retrofit retrofit){
        return retrofit.create(WebServiceInterface.class);
    }

}
