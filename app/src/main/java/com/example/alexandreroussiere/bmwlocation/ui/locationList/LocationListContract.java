package com.example.alexandreroussiere.bmwlocation.ui.locationList;

import android.location.Location;

import com.example.alexandreroussiere.bmwlocation.webService.models.LocationInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexandreroussiere on 6/29/17.
 * Contract that binds together the view and its presenter for LocationList
 */

public class LocationListContract {
    public interface Presenter {
        void sortDataByName();
        void sortDataByDistance(Location userLocation);
        void sortDataByTime();
        void fetchDataForRecyclerView();
        void setView(LocationListContract.View view);
        void unsubscribe();
    }

    public interface View {
        void setupRecyclerView(List<LocationInfo> locationInfoList);
        void updateDataRecyclerView();
        void requestPermission(ArrayList<String> permissions);
    }
}
