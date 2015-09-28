package com.izor066.android.blocspot.ui.fragment;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.izor066.android.blocspot.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PointsOfInterestFragmentList extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_points_of_interest_fragment_list, container, false);
        return view;
    }


}
