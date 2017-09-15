package com.example.alexandreroussiere.bmwlocation.ui;

import android.Manifest;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.alexandreroussiere.bmwlocation.MyApplication;
import com.example.alexandreroussiere.bmwlocation.adapter.RecyclerViewAdapter;
import com.example.alexandreroussiere.bmwlocation.R;
import com.example.alexandreroussiere.bmwlocation.webService.models.LocationInfo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The MainActivity is responsible for hosting 2 fragments. They replace each other depending on
 * the user actions and go into a FrameLayout inside the view
 */
public class MainActivity extends AppCompatActivity implements MainActivityContract.View{

    @Inject
    MainActivityPresenter mPresenter;

    @Inject
    Context mContext;

    private Fragment mCurrentFragment;

    @BindView(R.id.mainActivityToolbar)
    public Toolbar mToolbar;

    @BindView(R.id.fragment_container)
    public FrameLayout mFragmentContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ((MyApplication)getApplication()).getAppComponent().inject(this);
        mPresenter.setView(this);

        if (mToolbar != null){
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }
        //By default when rotated, the OS maintains the current fragment. So this allows us to decide
        //which fragment we want when the app launches
        if (savedInstanceState == null) {
            mPresenter.setupFirstFragment();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    //This will allow the user to come back to the list of location from the location detail view
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void drawFragment(Fragment fragment) {
        if (mCurrentFragment == null){
            mCurrentFragment = fragment;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            String backStateName = mCurrentFragment.getClass().getName();
            transaction.replace(R.id.fragment_container, mCurrentFragment, backStateName);
            transaction.commit();
        }
    }
}
