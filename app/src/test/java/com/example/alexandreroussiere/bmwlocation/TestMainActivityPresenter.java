package com.example.alexandreroussiere.bmwlocation;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.example.alexandreroussiere.bmwlocation.BuildConfig;
import com.example.alexandreroussiere.bmwlocation.R;
import com.example.alexandreroussiere.bmwlocation.ui.MainActivityContract;
import com.example.alexandreroussiere.bmwlocation.ui.MainActivityPresenter;
import com.example.alexandreroussiere.bmwlocation.ui.locationDetails.LocationDetailsFragment;
import com.example.alexandreroussiere.bmwlocation.ui.locationList.LocationListFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;
import static org.mockito.Mockito.mock;

/**
 * Created by alexandreroussiere on 6/29/17.
 * Unit tests to verify the behavior of the presenter of Main Activity
 * To do so, this class behaves as its view, and we just check that we get the right value by calling
 * the presenter's method
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class TestMainActivityPresenter implements MainActivityContract.View {


    private MainActivityPresenter mPresenter;

    @Before
    public void setup(){
        mPresenter = new MainActivityPresenter(RuntimeEnvironment.application);
    }

    @Test
    public void shouldPresenterNotBeNull(){
        assertNotNull("Presenter not created", mPresenter);
    }

    @Test(expected = NullPointerException.class)
    public void testSetView(){
        //The presenter has to be set to a view to work fine.
        //So This test should throw a null pointer exception because we have not set one
        mPresenter.setupFirstFragment();
    }

    @Test
    public void testSetUpFirstFragment(){
        //We parse our class as a view
        mPresenter.setView(this);
        //this call gonna use the drawFragment() method from our class so we can check the data over there
        mPresenter.setupFirstFragment();
    }

    @Override
    public void drawFragment(Fragment fragment) {
        //We go here from "testSetupFirstFragment"
        assertEquals("Wrong Fragment", fragment.getClass(), LocationListFragment.class);
    }
}
