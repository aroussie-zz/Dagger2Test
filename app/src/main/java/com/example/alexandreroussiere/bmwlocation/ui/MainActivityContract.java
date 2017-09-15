package com.example.alexandreroussiere.bmwlocation.ui;

import android.support.v4.app.Fragment;

import com.example.alexandreroussiere.bmwlocation.webService.models.LocationInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexandreroussiere on 6/28/17.
 * Contract that binds the presenter and the view together for Main Activity
 */
public class MainActivityContract {

    public interface Presenter{
        void setupFirstFragment();
        void setView(MainActivityContract.View view);
    }

    public interface View {
        void drawFragment(Fragment fragment);
    }

}
