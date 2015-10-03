package com.izor066.android.blocspot.ui;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.izor066.android.blocspot.R;
import com.izor066.android.blocspot.api.model.PointOfInterest;

/**
 * Created by igor on 1/10/15.
 */
public class MapPane extends Activity implements OnMapReadyCallback {

    PointOfInterest pointOfInterest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pointOfInterest = (PointOfInterest) getIntent().getParcelableExtra("poi");

        setContentView(R.layout.map_activity);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {

        map.setMyLocationEnabled(true);


        String title = pointOfInterest.getTitle();
        float lat = pointOfInterest.getLatitude();
        float lon = pointOfInterest.getLongitude();
        String address = pointOfInterest.getAddress();

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 13));

        map.addMarker(new MarkerOptions().position(
                new LatLng(lat, lon)).title(title).snippet(address)).showInfoWindow();


    }

}
