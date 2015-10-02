package com.izor066.android.blocspot.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.izor066.android.blocspot.R;
import com.izor066.android.blocspot.api.model.PointOfInterest;
import com.izor066.android.blocspot.ui.MapPane;
import com.izor066.android.blocspot.ui.adapter.ItemAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class PointsOfInterestFragmentList extends Fragment implements ItemAdapter.OnPointOfInterestClickListener {


    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;

   /* public interface OnPointOfInterestClickListener {
        void onPointOfInterestClick(PointOfInterest pointOfInterest);
    }

    private final OnPointOfInterestClickListener listener;

    public PointsOfInterestFragmentList(OnPointOfInterestClickListener listener) {
        this.listener = listener;
    }*/



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_points_of_interest_fragment_list, container, false);
        recyclerView = (RecyclerView) inflate.findViewById(R.id.rv_points_of_interest_fragment_list);
        return inflate;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        itemAdapter = new ItemAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(itemAdapter);
        Log.v("POIFragmentList", "OnActivityCreated");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v("POIFragmentList", "OnResume");

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v("POIFragmentList", "OnPause");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("POIFragmentList", "OnDestroy");

    }


    // ItemAdapter.OnPointOfInterestClickListener


    @Override
    public void OnPointOfInterestClick(PointOfInterest pointOfInterest) {
       Intent intent = new Intent(getActivity(), MapPane.class);
        intent.putExtra("poi", pointOfInterest);
        startActivity(intent);
        /* listener.onPointOfInterestClick(pointOfInterest);*/
    }
}
