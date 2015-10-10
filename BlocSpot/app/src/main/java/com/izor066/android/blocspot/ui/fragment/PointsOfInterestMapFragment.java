package com.izor066.android.blocspot.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.izor066.android.blocspot.BlocSpotApplication;
import com.izor066.android.blocspot.R;
import com.izor066.android.blocspot.api.model.PointOfInterest;
import com.izor066.android.blocspot.ui.UIUtils;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

public class PointsOfInterestMapFragment extends Fragment {


    MapView mapView;

    List<PointOfInterest> pointsOfInterest = BlocSpotApplication.getSharedDataSource().getAllPointsOfInterest();
    private GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v("MapFragment", "OnCreateView");

        View view = inflater.inflate(R.layout.fragment_points_of_interest_map, container, false);
        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        Log.v("MapFragment", "mapView.OnCreate");

        mapView.onResume();
        Log.v("MapFragment", "mapView.onResume");

        MapsInitializer.initialize(getActivity().getApplicationContext());

        GoogleMap googleMap1 = mapView.getMap();

        int size = BlocSpotApplication.getSharedDataSource().getAllPointsOfInterest().size();

        float lat = pointsOfInterest.get(0).getLatitude();
        float lon = pointsOfInterest.get(0).getLongitude();

        for (int i = 0; i < size; i++) {
            String title = pointsOfInterest.get(i).getTitle();
            lat = pointsOfInterest.get(i).getLatitude();
            lon = pointsOfInterest.get(i).getLongitude();
            String address = pointsOfInterest.get(i).getAddress();
            String categoryName = pointsOfInterest.get(i).getPoiCategory();
            int color = BlocSpotApplication.getSharedDataSource().getCategoryFromDBWithCategoryName(categoryName).getColor();
            float colorH = UIUtils.getHueFromRGB(color);

            MarkerOptions marker = new MarkerOptions().position(
                    new LatLng(lat, lon)).title(title).snippet(address);

            marker.icon(BitmapDescriptorFactory
                    .defaultMarker(colorH));


            googleMap1.addMarker(marker);

        }
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(lat, lon)).zoom(12).build();
        googleMap1.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));


        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        Log.v("MapFragment", "onResume");

    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        Log.v("MapFragment", "onPause");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        Log.v("MapFragment", "onDestroy");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


}
