package com.izor066.android.blocspot.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.izor066.android.blocspot.R;
import com.izor066.android.blocspot.ui.adapter.TabAdapter;
import com.izor066.android.blocspot.ui.widget.tabs.SlidingTabLayout;

public class MainActivity extends ActionBarActivity /*implements PointsOfInterestFragmentList.OnPointOfInterestClickListener*/ {


    Toolbar toolbar;
    ViewPager pager;
    TabAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"Points Of Interest", "Map"};
    int Numboftabs = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);


        adapter = new TabAdapter(getSupportFragmentManager(), Titles, Numboftabs);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);


        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);


        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });


        tabs.setViewPager(pager);



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*@Override
    onAttach(Activity activity) {
        this.listener = (OnPointOfInterestClickListener) activity;
    }*/



    /* PointsOfInterestFragmentList.OnPointOfInterestClickListener

    @Override
    public void OnPointOfInterestClick(PointOfInterest pointOfInterest) {
        Intent intent = new Intent(this, MapPane.class);
        intent.putExtra("poi", pointOfInterest);
        startActivity(intent);
    }*/


}
