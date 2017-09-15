package com.example.alexandreroussiere.bmwlocation.ui.locationDetails;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alexandreroussiere.bmwlocation.MyApplication;
import com.example.alexandreroussiere.bmwlocation.R;
import com.example.alexandreroussiere.bmwlocation.webService.models.LocationInfo;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by alexandreroussiere on 6/29/17.
 */
public class LocationDetailsFragment extends Fragment implements LocationDetailsContract.View, OnMapReadyCallback{

    public static final String ARG_LOCATION_INFO = "locationInfo";
    public static final String TAG = "LocationDetailsFragment";

    @Inject
    LocationDetailsPresenter mPresenter;

    private GoogleMap mGoogleMap;
    private LocationInfo mLocationInfo;

    @BindView(R.id.locationDetails_mapView)
    public MapView mMapView;

    @BindView(R.id.location_details_address_TextView)
    public TextView mAddressTextView;

    @BindView(R.id.location_details_arrivalTime_TextView)
    public TextView mArrivalTimeTextView;

    @BindView(R.id.location_details_latitude_TextView)
    public TextView mLatitudeTextView;

    @BindView(R.id.location_details_longitude_TextView)
    public TextView mLongitudeTextView;

    @BindView(R.id.location_details_location_TextView)
    public TextView mLocationTextView;

    public LocationDetailsFragment() {
    }

    //This allows us to create the fragment with a LocationInfo object into it
    public static LocationDetailsFragment newInstance(LocationInfo locationInfo) {
        LocationDetailsFragment fragment = new LocationDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_LOCATION_INFO, locationInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLocationInfo = getArguments().getParcelable(ARG_LOCATION_INFO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.location_details_layout, container, false);
        ButterKnife.bind(this, view);
        mMapView.onCreate(null);
        setHasOptionsMenu(true);
        initializeMapFragment();
        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);
        ((MyApplication) getActivity().getApplication()).getAppComponent().inject(this);
        mPresenter.setView(this);
        mPresenter.setLocationInfo(mLocationInfo);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.fetchDataToDisplay();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    public void initializeMapFragment() {
        if (mMapView != null) {
            mMapView.getMapAsync(LocationDetailsFragment.this);
        }
    }

    @Override
    public void setAddressTextView(String address) {
        mAddressTextView.setText(address);
    }

    @Override
    public void setLocationTextView(String location) {
        mLocationTextView.setText(location);
    }

    @Override
    public void setArrivalTimeTextView(String timeInMinute) {
        mArrivalTimeTextView.setText(timeInMinute);
    }

    @Override
    public void setLatitudeTextView(String latitude) {
        mLatitudeTextView.setText(latitude);
    }

    @Override
    public void setLongitudeTextView(String longitude) {
        mLongitudeTextView.setText(longitude);
    }

    @Override
    public void setToolbarTitle(String title) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(title);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Allows us to hide the menu of the Toolbar
     * @param menu
     */
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("TripDetails", "Map is ready!");
        mGoogleMap = googleMap;

        mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
        mGoogleMap.getUiSettings().setCompassEnabled(false);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
        mGoogleMap.getUiSettings().setMapToolbarEnabled(false);

        mPresenter.setupMapCamera();

    }

    @Override
    public void setCameraToPosition(CameraUpdate cameraUpdate) {
        mGoogleMap.moveCamera(cameraUpdate);
    }

    @Override
    public void drawLocationMarker(LatLng position) {
        mGoogleMap.addMarker(new MarkerOptions().position(position));
    }

    @Override
    public void onDestroyView() {
        mPresenter.unsubscribe();
        super.onDestroyView();
    }

}
