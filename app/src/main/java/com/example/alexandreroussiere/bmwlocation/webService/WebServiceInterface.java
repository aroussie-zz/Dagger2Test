package com.example.alexandreroussiere.bmwlocation.webService;

import com.example.alexandreroussiere.bmwlocation.webService.models.LocationInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by alexandreroussiere on 6/28/17.
 * Interface used by Retrofit
 */

public interface WebServiceInterface {

    @GET("Locations")
    Call<List<LocationInfo>> getLocationData();


}
