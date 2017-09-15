package com.example.alexandreroussiere.bmwlocation.ui.locationDetails;


import com.example.alexandreroussiere.bmwlocation.webService.models.LocationInfo;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by alexandreroussiere on 6/29/17.
 * Contract that binds each other the view and its presenter for Location Details
 */
public class LocationDetailsContract {
    public interface Presenter {
        void setView(LocationDetailsContract.View view);
        void setLocationInfo(LocationInfo locationInfo);
        void fetchDataToDisplay();
        void unsubscribe();
        void setupMapCamera();
    }
    public interface View {
        void setAddressTextView(String address);
        void setLocationTextView(String location);
        void setArrivalTimeTextView(String timeInMinute);
        void setLatitudeTextView(String latitude);
        void setLongitudeTextView(String longitude);
        void setCameraToPosition(CameraUpdate cameraUpdate);
        void drawLocationMarker(LatLng position);
        void setToolbarTitle(String title);
    }
}

