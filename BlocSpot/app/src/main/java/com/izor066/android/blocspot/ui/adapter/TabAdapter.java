package com.izor066.android.blocspot.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

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
            pointsOfInterestFragmentList.updateCategory(category);
            pointsOfInterestMapFragment.updateCategory(category);

            pointsOfInterestMapFragment.updateMap();



    }

    public void updateMap() {
            pointsOfInterestMapFragment.updateMap();
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
