package com.example.alexandreroussiere.bmwlocation.ui;

import android.content.Context;

import com.example.alexandreroussiere.bmwlocation.ui.locationList.LocationListFragment;

/**
 * Created by alexandreroussiere on 6/28/17.
 */

public class MainActivityPresenter implements MainActivityContract.Presenter {

    private Context mContext;
    private MainActivityContract.View mView;

    public MainActivityPresenter(Context context) {
        mContext = context;
    }

    @Override
    public void setupFirstFragment() {
        mView.drawFragment(new LocationListFragment());
    }

    @Override
    public void setView(MainActivityContract.View view) {
        mView = view;
    }


}
