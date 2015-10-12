package com.izor066.android.blocspot.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.izor066.android.blocspot.ui.fragment.PointsOfInterestFragmentList;
import com.izor066.android.blocspot.ui.fragment.PointsOfInterestMapFragment;

/**
 * Created by igor on 27/9/15.
 */
public class TabAdapter extends FragmentStatePagerAdapter {


    CharSequence mTitles[];
    int mNumbOfTabs;
    private PointsOfInterestFragmentList pointsOfInterestFragmentList;
    private PointsOfInterestMapFragment pointsOfInterestMapFragment;


    public TabAdapter(FragmentManager fm, CharSequence Titles[], int NumbOfTabsumb) {
        super(fm);

        this.mTitles = Titles;
        this.mNumbOfTabs = NumbOfTabsumb;

    }


    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            pointsOfInterestFragmentList = new PointsOfInterestFragmentList();
            return pointsOfInterestFragmentList;
        } else {
            pointsOfInterestMapFragment = new PointsOfInterestMapFragment();
            return pointsOfInterestMapFragment;
        }

    }

    public void updateCategory(String category) {
        try {
            pointsOfInterestFragmentList.updateCategory(category);
            pointsOfInterestMapFragment.updateCategory(category);
        } catch (NullPointerException e) {
            Log.v("NullPointerException TA", String.valueOf(e));

        }

        try {
            pointsOfInterestMapFragment.updateMap();
            Log.v("updateMap CAT", "ran");
        } catch (NullPointerException e) {
            Log.v("NPE MP CAT", String.valueOf(e));

        }
      //  notifyDataSetChanged();

    }

    public void updateMap() {
        try {
            pointsOfInterestMapFragment.updateMap();
            Log.v("updateMap", "ran");
        } catch (NullPointerException e) {
            Log.v("NullPointerException MP", String.valueOf(e));

        }
      //  notifyDataSetChanged();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }


    @Override
    public int getCount() {
        return mNumbOfTabs;
    }


}
