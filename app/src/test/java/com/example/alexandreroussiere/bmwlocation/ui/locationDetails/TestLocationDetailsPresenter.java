package com.example.alexandreroussiere.bmwlocation.ui.locationDetails;

import android.content.Context;

import com.example.alexandreroussiere.bmwlocation.BuildConfig;
import com.example.alexandreroussiere.bmwlocation.R;
import com.example.alexandreroussiere.bmwlocation.webService.models.LocationInfo;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.robolectric.annotation.Config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by alexandreroussiere on 6/29/17.
 * Unit tests to verify the behavior of the presenter of LocationDetails
 * To do so, this class behaves as its view, and we just check that we get the right value by calling
 * the presenter's method
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(CameraUpdateFactory.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class TestLocationDetailsPresenter implements LocationDetailsContract.View {


    private final String LOCATION_ARRIVALTIME = "2017-06-30T16:38:01.357";
    private final String LOCATION_ADDRESS = "11 N Canal St L30 Chicago, IL 60606";
    private final float LOCATION_LONGITUDE = -23;
    private final float LOCATION_LATITUDE = 32;
    private final String LOCATION_NAME = "MERCHANDISE MART";

    private boolean cameraUpdateSentToView;
    private LocationDetailsPresenter mPresenter;

    @Mock
    Context mContext;

    @Mock LocationInfo locationInfo;

    @Before
    public void setup(){
        //We use mockito to mock the behavior of the context and being able to get some resources
        when(mContext.getString(R.string.cardView_address)).thenReturn("Address: %s");
        when(mContext.getString(R.string.cardView_arrivalTime)).thenReturn("Arrival time: %d minutes");
        when(mContext.getString(R.string.cardView_location)).thenReturn("Location: %s");
        when(mContext.getString(R.string.cardView_longitude)).thenReturn("Longitude: %.2f");
        when(mContext.getString(R.string.cardView_latitude)).thenReturn("Latitude: %.2f");

        //We also use Mockito to mock the behavior of the LocationObject
        when(locationInfo.getAddress()).thenReturn(LOCATION_ADDRESS);
        when(locationInfo.getName()).thenReturn(LOCATION_NAME);
        when(locationInfo.getLongitude()).thenReturn(LOCATION_LONGITUDE);
        when(locationInfo.getLatitude()).thenReturn(LOCATION_LATITUDE);
        when(locationInfo.getArrivalTime()).thenReturn(LOCATION_ARRIVALTIME);

        cameraUpdateSentToView = false;

        mPresenter = new LocationDetailsPresenter(mContext);
    }

    @Test
    public void shouldPresenterNotBeNull(){
        assertNotNull("Presenter not created", mPresenter);
    }

    @Test(expected = NullPointerException.class)
    public void testSetView(){
        //The presenter has to be set to a view to work fine.
        //So This test should throw a null pointer exception because we have not set one
        //To be sure we have the exception because of the view, we first set a locationInfo object
        mPresenter.setLocationInfo(locationInfo);
        mPresenter.setupMapCamera();
    }

    @Test(expected = NullPointerException.class)
    public void testSetLocationInfo(){
        //The presenter gets its data from the locationInfo object set by its view.
        //So if don't sent one to it, it should throw a null pointer exception
        mPresenter.setView(this);
        mPresenter.fetchDataToDisplay();
    }

    @Test
    public void testFetchDataToDisplay(){
        mPresenter.setLocationInfo(locationInfo);
        mPresenter.setView(this);

        //This is gonna call all the "display" method of the view
        mPresenter.fetchDataToDisplay();

    }

    @Test
    public void testSetupMapCamera(){
        mPresenter.setView(this);
        mPresenter.setLocationInfo(locationInfo);

        //Unfortunately, I have an error from CameraUpdateFactory saying that it has not been initialized
        //So instead, I'm using PowerMockito to check the value given to the newLatLngZoom method and see
        // that the position given is actually the one we expect. Then I just check that the call to the view is done.
        PowerMockito.mockStatic(CameraUpdateFactory.class);
        // Check that we give the right argument to the CameraUpdateFactory method
        when(CameraUpdateFactory.newLatLngZoom(any(LatLng.class), anyFloat())).thenAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                LatLng firstArgument = (LatLng) invocation.getArguments()[0];
                LatLng expectedPoint = new LatLng(LOCATION_LATITUDE, LOCATION_LONGITUDE);
                assertEquals("The LatLng point is not correct", expectedPoint, firstArgument);
                doNothing(); // Need this to avoid the nullPointerException
                return null;
            }
        });

        //This is gonna send the CameraUpdate and the marker position to the view
        mPresenter.setupMapCamera();
        assertEquals("cameraUpdate not sent to the view", true, cameraUpdateSentToView);

    }

    @Test(expected = NullPointerException.class)
    public void testUnsubscribe(){
        mPresenter.setView(this);
        mPresenter.setLocationInfo(locationInfo);
        mPresenter.unsubscribe();

        //This function should throw a null pointer exception because the view should be null
        mPresenter.fetchDataToDisplay();
    }

    @Override
    public void setAddressTextView(String address) {
        assertEquals("wrong address", String.format(mContext.getString(R.string.cardView_address),
                LOCATION_ADDRESS), address);
    }

    @Override
    public void setLocationTextView(String location) {
        assertEquals("wrong location", String.format(mContext.getString(R.string.cardView_location),
                LOCATION_NAME), location);
    }

    @Override
    public void setArrivalTimeTextView(String timeInMinute) {

        //Here we have to do the math to get the minutes difference between now and our mock object
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S", Locale.US);
        Date mockArrivalTimeDate = new Date();
        try {
            mockArrivalTimeDate = format.parse(LOCATION_ARRIVALTIME);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long currentTime = System.currentTimeMillis();
        long arrivalTimeInMinute = (mockArrivalTimeDate.getTime() - currentTime) / 1000 / 60;

        //Now we can check that we get the same value from our present call
        assertEquals("Wrong arrival time", String.format(mContext.getString(R.string.cardView_arrivalTime),
                arrivalTimeInMinute), timeInMinute);

    }

    @Override
    public void setLatitudeTextView(String latitude) {
        assertEquals("wrong latitude", String.format(mContext.getString(R.string.cardView_latitude),
                LOCATION_LATITUDE), latitude);
    }

    @Override
    public void setLongitudeTextView(String longitude) {
        assertEquals("wrong longitude", String.format(mContext.getString(R.string.cardView_longitude),
                LOCATION_LONGITUDE), longitude);
    }

    @Override
    public void setCameraToPosition(CameraUpdate cameraUpdate) {
        //See testSetupMapCamera comments
        cameraUpdateSentToView = true;
    }

    @Override
    public void drawLocationMarker(LatLng position) {
        //The position should be the same as the locationInfo object
        assertEquals("Wrong position", new LatLng(LOCATION_LATITUDE, LOCATION_LONGITUDE), position);
    }

    @Override
    public void setToolbarTitle(String title) {
        assertEquals("wrong title", LOCATION_NAME, title);
    }
}
