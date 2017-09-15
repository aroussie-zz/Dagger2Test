package com.example.alexandreroussiere.bmwlocation.ui.locationList;

/**
 * Created by alexandreroussiere on 6/30/17.
 */

import android.content.Context;

import com.example.alexandreroussiere.bmwlocation.BuildConfig;
import com.example.alexandreroussiere.bmwlocation.Constants;
import com.example.alexandreroussiere.bmwlocation.TestMyApplication;
import com.example.alexandreroussiere.bmwlocation.webService.WebServiceInterface;
import com.example.alexandreroussiere.bmwlocation.webService.models.LocationInfo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by alexandreroussiere on 6/29/17.
 * Unit tests to verify the behavior of the presenter of LocationList View
 * To do so, this class behaves as its view, and we just check that we get the right value by calling
 * the presenter's method
 */
@RunWith(PowerMockRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, application = TestMyApplication.class)
public class TestLocationListPresenter implements LocationListContract.View {

    @Mock
    LocationInfo location1;

    @Mock
    LocationInfo location2;

    @Inject
    WebServiceInterface retrofitInterface;

    private LocationListPresenter mPresenter;
    private List<LocationInfo> mData;
    private String fakeJson;

    @Before
    public void setup(){

        //This would give us a mock object for the retrofit interface.
        // It's not working because I have an issue in TestMyApplication
        ((TestMyApplication)RuntimeEnvironment.application).getAppComponent().inject(this);
        mPresenter = new LocationListPresenter(RuntimeEnvironment.application);

        fakeJson = "[{\"ID\":1," +
                "\"Name\":\"Al's Beef\"," +
                "\"Latitude\":32," +
                "\"Longitude\":-23," +
                "\"Address\":\"11 N Canal St L30 Chicago, IL 60606\"," +
                "\"ArrivalTime\":\"2020-06-30T16:38:01.357\"}," +
                "{\"ID\":2," +
                "\"Name\":\"Walgreen\"," +
                "\"Latitude\":20," +
                "\"Longitude\":-23," +
                "\"Address\":\"205 W Lake St, Chicago, IL 60606\"," +
                "\"ArrivalTime\":\"2017-06-30T16:38:01.357\"}]";


        //We gonna need fake data to mock what we could have from the API
        when(location1.getName()).thenReturn("Al's Beef");
        when(location1.getLatitude()).thenReturn(32f);
        when(location1.getLongitude()).thenReturn(-23f);
        when(location1.getArrivalTime()).thenReturn("2020-06-30T16:38:01.357");

        when(location2.getName()).thenReturn("Walgreen");
        when(location2.getLatitude()).thenReturn(20f);
        when(location2.getLongitude()).thenReturn(-23f);
        when(location2.getArrivalTime()).thenReturn("2017-06-30T16:38:01.357");

        mData.add(location1);
        mData.add(location2);


    }

    @Test
    public void shouldPresenterNotBeNull(){
        assertNotNull("Presenter not created", mPresenter);
    }

    @Test
    public void testFetchDataForRecyclerView() throws IOException {

        //Here we are gonna mock the callback that we should get from retrofit to return our fake data
        MockWebServer mockWebServer = new MockWebServer();

        //I'm pretty sure I could use Dagger for that but I'm not expert enough yet
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mockWebServer.enqueue(new MockResponse().setBody(fakeJson));

        WebServiceInterface retrofitInterface = retrofit.create(WebServiceInterface.class);

        Call<List<LocationInfo>> call = retrofitInterface.getLocationData();
        assertTrue("call not done", call.execute() != null);


        //Finish web server
        mockWebServer.shutdown();

    }


    @Override
    public void setupRecyclerView(List<LocationInfo> locationInfoList) {

    }

    @Override
    public void updateDataRecyclerView() {

    }

    @Override
    public void requestPermission(ArrayList<String> permissions) {

    }
}
