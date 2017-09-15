package com.example.alexandreroussiere.bmwlocation.ui.locationList;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.alexandreroussiere.bmwlocation.MyApplication;
import com.example.alexandreroussiere.bmwlocation.R;
import com.example.alexandreroussiere.bmwlocation.adapter.RecyclerViewAdapter;
import com.example.alexandreroussiere.bmwlocation.ui.locationDetails.LocationDetailsFragment;
import com.example.alexandreroussiere.bmwlocation.webService.models.LocationInfo;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexandreroussiere on 6/29/17.
 */

public class LocationListFragment extends Fragment implements LocationListContract.View, RecyclerViewAdapter.onItemClickedListener {

    private final String TAG = "LocationListFragment";

    @Inject
    public Context mContext;
    @Inject
    public LocationListPresenter mPresenter;

    private FusedLocationProviderClient mFusedLocationClient;

    @BindView(R.id.locationRecyclerView)
    public RecyclerView mRecyclerView;

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.location_recyclerview, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);
        ((MyApplication) getActivity().getApplication()).getAppComponent().inject(this);
        mPresenter.setView(this);
        ((AppCompatActivity) getActivity()).getSupportActionBar()
                .setTitle(getResources().getString(R.string.toolbar_title_mainActivity));
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.fetchDataForRecyclerView();
    }

    @Override
    public void setupRecyclerView(List<LocationInfo> locationInfoList) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(locationInfoList, this, mContext);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void updateDataRecyclerView() {
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onItemClicked(LocationInfo info) {
        //We go to the second fragment and we set the clicked LocationInfo object in it
        Fragment newFragment = LocationDetailsFragment.newInstance(info);
        String backStateName = newFragment.getClass().getName();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.fragment_container, newFragment, backStateName);
        ft.addToBackStack(backStateName);
        ft.commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_distance:
                //We only check for location when the user clicks on a feature that uses it.
                prepareForCheckPermission();
                return true;

            case R.id.action_sort_name:
                mPresenter.sortDataByName();
                return true;

            case R.id.action_sort_time:
                mPresenter.sortDataByTime();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void prepareForCheckPermission() {
        ArrayList<String> permissions = new ArrayList<>();

        //Check if we can use the location
        if (ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
            permissions.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
            requestPermission(permissions);
        } else {
            //This would mean that the permission is granted so we can calculate the distance
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);
            mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    //Once we get the last known location, we can estimate the distance
                    mPresenter.sortDataByDistance(location);
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (grantResults.length > 0
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            //We go there if the user accepted the demand of permission
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);
            mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    //Once we get the last known location, we can estimate the distance
                    mPresenter.sortDataByDistance(location);
                }
            });
        } else {
            //If the user says no, then we display a message
            Toast.makeText(mContext, getString(R.string.location_permission_denied), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void requestPermission(ArrayList<String> permissions) {

        //Will ask all the permissions from the list in the signature
        String[] arrayPermissions = new String[permissions.size()];
        arrayPermissions = permissions.toArray(arrayPermissions);
        requestPermissions(arrayPermissions, 0);
    }

    @Override
    public void onDestroyView() {
        mPresenter.unsubscribe();
        super.onDestroyView();
    }
}
