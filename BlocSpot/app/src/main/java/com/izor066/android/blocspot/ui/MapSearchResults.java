package com.izor066.android.blocspot.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.izor066.android.blocspot.R;
import com.izor066.android.blocspot.api.model.PointOfInterest;
import com.izor066.android.blocspot.ui.fragment.SavePointOfInterestYelpFragment;

import java.util.ArrayList;

/**
 * Created by igor on 17/10/15.
 */
public class MapSearchResults extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    PointOfInterest pointOfInterest;
    PointOfInterest pointOfInterestClicked;
    int color;

    ArrayList<PointOfInterest> pointsOfInterest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pointsOfInterest = getIntent().getParcelableArrayListExtra("points_of_interest");


        setContentView(R.layout.map_activity);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {

        map.setMyLocationEnabled(true);
        map.setOnMarkerClickListener(this);

        float latCam = pointsOfInterest.get(0).getLatitude();
        float longCam = pointsOfInterest.get(0).getLongitude();

        for (PointOfInterest pointOfInterestBundle : pointsOfInterest){

            String title = pointOfInterestBundle.getTitle();
            float lat = pointOfInterestBundle.getLatitude();
            float lon = pointOfInterestBundle.getLongitude();
            String address = pointOfInterestBundle.getAddress();



            map.addMarker(new MarkerOptions()
                    .position(new LatLng(lat, lon))
                    .title(title)
                    .snippet(address)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)))
                    .showInfoWindow();
        }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latCam, longCam), 13));

    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        Toast.makeText(this, marker.getTitle(), Toast.LENGTH_SHORT).show();
        Log.v("Marker clicked", marker.getTitle() + ", " + marker.getSnippet() + ", " + marker.getPosition().latitude + ", " + marker.getPosition().longitude);
        pointOfInterestClicked = new PointOfInterest(-1, marker.getTitle(), marker.getSnippet(),(float) marker.getPosition().latitude, (float) marker.getPosition().longitude, "Unsorted");
        showSavePointOfInterestYelpFragment(pointOfInterestClicked);
        return true;
    }


    private void showSavePointOfInterestYelpFragment(PointOfInterest pointOfInterest) {
        Bundle args = new Bundle();
        args.putParcelable("point_of_interest_from_click", pointOfInterest);
        FragmentManager fragmentManager = getSupportFragmentManager();
        SavePointOfInterestYelpFragment savePointOfInterestYelpFragment = new SavePointOfInterestYelpFragment();
        savePointOfInterestYelpFragment.setArguments(args);
        savePointOfInterestYelpFragment.show(fragmentManager, "Save POI Yelp Fragment");
    }
}
