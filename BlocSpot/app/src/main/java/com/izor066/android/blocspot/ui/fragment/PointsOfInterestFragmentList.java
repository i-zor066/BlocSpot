package com.izor066.android.blocspot.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.izor066.android.blocspot.R;
import com.izor066.android.blocspot.ui.adapter.ItemAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class PointsOfInterestFragmentList extends Fragment {

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_points_of_interest_fragment_list, container, false);
        recyclerView = (RecyclerView) inflate.findViewById(R.id.rv_points_of_interest_fragment_list);
        return inflate;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        itemAdapter = new ItemAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(itemAdapter);
    }


}
