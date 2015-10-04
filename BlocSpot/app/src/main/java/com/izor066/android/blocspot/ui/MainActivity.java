package com.izor066.android.blocspot.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.izor066.android.blocspot.BlocSpotApplication;
import com.izor066.android.blocspot.GeoFences.GeofenceStore;
import com.izor066.android.blocspot.R;
import com.izor066.android.blocspot.api.model.Category;
import com.izor066.android.blocspot.api.model.PointOfInterest;
import com.izor066.android.blocspot.ui.adapter.TabAdapter;
import com.izor066.android.blocspot.ui.fragment.CategoryDialogFragment;
import com.izor066.android.blocspot.ui.fragment.PointsOfInterestFragmentList;
import com.izor066.android.blocspot.ui.widget.tabs.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PointsOfInterestFragmentList.OnPointOfInterestClickListener, CategoryDialogFragment.CategoryDialogListener {


    Toolbar toolbar;
    ViewPager pager;
    TabAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"Points Of Interest", "Map"};
    int Numboftabs = 2;

    // GeoFence Data

    ArrayList<Geofence> mGeofences;
    private static final int GEOFENCE_RADIUS = 150;
//    private static final int LOITERING_DELAY = 45000;
    private GeofenceStore mGeofenceStore;


    List<PointOfInterest> pointsOfInterest = BlocSpotApplication.getSharedDataSource().getAllPointsOfInterest();


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

        // GeoFences

        mGeofences = new ArrayList<Geofence>();

        int size = BlocSpotApplication.getSharedDataSource().getAllPointsOfInterest().size();

        for (int i = 0; i < size; i++) {
            String title = pointsOfInterest.get(i).getTitle();
            float lat = pointsOfInterest.get(i).getLatitude();
            float lon = pointsOfInterest.get(i).getLongitude();

            mGeofences.add(new Geofence.Builder()
                    .setRequestId(title)
                    .setCircularRegion(lat, lon, GEOFENCE_RADIUS)
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
//                    .setLoiteringDelay(LOITERING_DELAY)
                    .setTransitionTypes(
                            Geofence.GEOFENCE_TRANSITION_ENTER
//                                    | Geofence.GEOFENCE_TRANSITION_DWELL
                                    | Geofence.GEOFENCE_TRANSITION_EXIT).build());
        }

        mGeofenceStore = new GeofenceStore(this, mGeofences);
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

        if (id == R.id.action_category) {
            showEditDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onPointOfInterestClick(PointOfInterest pointOfInterest) {
        Intent intent = new Intent(this, MapPane.class);
        intent.putExtra("poi", pointOfInterest);
        startActivity(intent);
    }

    private void showEditDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        CategoryDialogFragment categoryDialogFragment = new CategoryDialogFragment();
        categoryDialogFragment.show(fragmentManager, "insert_category_name");
    }


    @Override
    public void onCategoryAdded(String categoryNameInput) {

        String categoryName = categoryNameInput;
        Boolean categoryExists = BlocSpotApplication.getSharedDataSource().categoryExists(categoryName);
        int color = UIUtils.generateRandomColor(toolbar.getResources().getColor(android.R.color.white));
        Category category = new Category(categoryName, color);

        if (categoryExists) {
            Toast.makeText(this, getString(R.string.category_exists), Toast.LENGTH_SHORT).show();
        } else {
            BlocSpotApplication.getSharedDataSource().insertCategoryToDatabase(category);
            Toast.makeText(this, getString(R.string.category_added) + " " + categoryNameInput, Toast.LENGTH_SHORT).show();
            Category categoryFromDB = BlocSpotApplication.getSharedDataSource().getCategoryFromDBWithCategoryName(categoryName);
            Log.v ("Category inserted: ", " " + categoryFromDB.getCategoryName() + ", " + categoryFromDB.getColor());
        }

    }
}
