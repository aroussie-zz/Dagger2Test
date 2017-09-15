package com.example.alexandreroussiere.bmwlocation.ui.locationDetails;

import android.content.Context;

import com.example.alexandreroussiere.bmwlocation.R;
import com.example.alexandreroussiere.bmwlocation.webService.models.LocationInfo;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by alexandreroussiere on 6/29/17.
 */

public class LocationDetailsPresenter implements LocationDetailsContract.Presenter {

    private Context mContext;
    private LocationDetailsContract.View mView;
    private LocationInfo mLocationInfo;

    public LocationDetailsPresenter(Context context) {
        mContext = context;
    }

    @Override
    public void fetchDataToDisplay() {

        mView.setAddressTextView(String.format(mContext.getString(R.string.cardView_address),
                mLocationInfo.getAddress()));

        mView.setLatitudeTextView(String.format(mContext.getString(R.string.cardView_latitude),
                mLocationInfo.getLatitude()));

        mView.setLongitudeTextView(String.format(mContext.getString(R.string.cardView_longitude),
                mLocationInfo.getLongitude()));

        mView.setLocationTextView(String.format(mContext.getString(R.string.cardView_location),
                mLocationInfo.getName()));

        mView.setToolbarTitle(mLocationInfo.getName());

        long timeArrivalInMinute = getTimeArrivalInMinutes();
        //This would mean that the arrival time already happened
        if (timeArrivalInMinute < 0 ) {
            mView.setArrivalTimeTextView(mContext.getString(R.string.arrivalTime_happened));
        } else {
            mView.setArrivalTimeTextView(String.format(mContext.getString(R.string.cardView_arrivalTime),
                    timeArrivalInMinute));
        }

    }

    private long getTimeArrivalInMinutes(){

        //Get the date from the String of LocationInfo
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S", Locale.US);
        Date arrivalTimeDate = new Date();
        try {
            arrivalTimeDate = format.parse(mLocationInfo.getArrivalTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long currentTime = System.currentTimeMillis();
        long arrivalTimeInMilliseconds = arrivalTimeDate.getTime() - currentTime;
        return arrivalTimeInMilliseconds / 1000 / 60;
    }

    @Override
    public void setupMapCamera() {
        //Set the position based on its coordinates and zoom on it
        LatLng position = new LatLng(mLocationInfo.getLatitude(), mLocationInfo.getLongitude());
        CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(position, 16);
        mView.setCameraToPosition(cu);
        mView.drawLocationMarker(position);
    }

    @Override
    public void setView(LocationDetailsContract.View view) {
        mView = view;
    }

    @Override
    public void setLocationInfo(LocationInfo locationInfo) {
        mLocationInfo = locationInfo;
    }

    @Override
    public void unsubscribe() {
        mView = null;
    }
}
