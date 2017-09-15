package com.example.alexandreroussiere.bmwlocation.ui.locationList;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.example.alexandreroussiere.bmwlocation.MyApplication;
import com.example.alexandreroussiere.bmwlocation.webService.WebServiceInterface;
import com.example.alexandreroussiere.bmwlocation.webService.models.LocationInfo;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alexandreroussiere on 6/29/17.
 */

public class LocationListPresenter implements LocationListContract.Presenter {

    private Context mContext;
    private LocationListContract.View mView;
    private List<LocationInfo> data;

    @Inject
    WebServiceInterface mRetrofitInterface;

    public LocationListPresenter(Context context) {
        mContext = context;
        ((MyApplication) mContext).getAppComponent().inject(this);
    }

    @Override
    public void sortDataByName() {

        Collections.sort(data, new Comparator<LocationInfo>() {
            @Override
            public int compare(LocationInfo locationInfo1, LocationInfo locationInfo2) {
                return locationInfo1.getName().compareTo(locationInfo2.getName());
            }
        });
        mView.updateDataRecyclerView();
    }

    @Override
    public void sortDataByDistance(final Location userLocation) {

        Collections.sort(data, new Comparator<LocationInfo>() {
            @Override
            public int compare(LocationInfo locationInfo1, LocationInfo locationInfo2) {
                //We get the location from the first LocationInfo
                Location locationA = new Location("LocationA");
                locationA.setLatitude(locationInfo1.getLatitude());
                locationA.setLongitude(locationInfo1.getLongitude());

                //We get the location from the second locationInfo
                Location locationB = new Location("LocationB");
                locationB.setLatitude(locationInfo2.getLatitude());
                locationB.setLongitude(locationInfo2.getLongitude());

                float distanceBetweenAandUser = locationA.distanceTo(userLocation);
                float distanceBetweenBandUser = locationB.distanceTo(userLocation);

                return Float.compare(distanceBetweenAandUser,distanceBetweenBandUser);
            }
        });
        mView.updateDataRecyclerView();

    }

    @Override
    public void sortDataByTime() {

        Collections.sort(data, new Comparator<LocationInfo>() {
            @Override
            public int compare(LocationInfo locationInfo1, LocationInfo locationInfo2) {
                return locationInfo1.getArrivalTime().compareTo(locationInfo2.getArrivalTime());
            }
        });
        mView.updateDataRecyclerView();
    }

    @Override
    public void fetchDataForRecyclerView() {

        Call<List<LocationInfo>> locationCall = mRetrofitInterface.getLocationData();
        locationCall.enqueue(new Callback<List<LocationInfo>>() {
            @Override
            public void onResponse(Call<List<LocationInfo>> call, Response<List<LocationInfo>> response) {
                if (response.isSuccessful()) {
                    data = response.body();
                    //By default the list is sorted by Name
                    Collections.sort(data, new Comparator<LocationInfo>() {
                        @Override
                        public int compare(LocationInfo lhs, LocationInfo rhs) {
                            return lhs.getName().compareTo(rhs.getName());
                        }
                    });
                    mView.setupRecyclerView(data);
                }
            }

            @Override
            public void onFailure(Call<List<LocationInfo>> call, Throwable t) {
                Log.e("MainActivityPresenter", "Error when fetching data: " + t.getMessage());
            }
        });

    }

    @Override
    public void setView(LocationListContract.View view) {
        mView = view;
    }

    @Override
    public void unsubscribe() {
        mView = null;
    }
}
