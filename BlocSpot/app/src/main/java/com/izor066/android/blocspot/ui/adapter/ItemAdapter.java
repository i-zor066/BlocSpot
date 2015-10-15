package com.izor066.android.blocspot.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.izor066.android.blocspot.BlocSpotApplication;
import com.izor066.android.blocspot.R;
import com.izor066.android.blocspot.api.DataSource;
import com.izor066.android.blocspot.api.model.PointOfInterest;
import com.izor066.android.blocspot.ui.fragment.CategoryDialogFragment;

/**
 * Created by igor on 28/9/15.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemAdapterViewHolder>  {

    private String currentCategoryView = CategoryDialogFragment.ALL_CATEGORIES;


    public static interface OnPointOfInterestClickListener {
        public void onPointOfInterestClick(PointOfInterest pointOfInterest);
        public void onPointOfInterestLongClick(PointOfInterest pointOfInterest);
    }

    private final OnPointOfInterestClickListener listener;

    public ItemAdapter(OnPointOfInterestClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ItemAdapter.ItemAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.point_of_interest, viewGroup, false);
        return new ItemAdapterViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ItemAdapter.ItemAdapterViewHolder itemAdapterViewHolder, int i) {
        DataSource sharedDataSource = BlocSpotApplication.getSharedDataSource();
        if (currentCategoryView == CategoryDialogFragment.ALL_CATEGORIES) {
            itemAdapterViewHolder.update(sharedDataSource.getAllPointsOfInterest().get(i), listener);
        } else {
            itemAdapterViewHolder.update(sharedDataSource.getPointsOfInterestForCategory(currentCategoryView).get(i), listener);
        }
    }

    public void updateCategory(String category) {
        this.currentCategoryView = category;
    }

    @Override
    public int getItemCount() {
        if (currentCategoryView == CategoryDialogFragment.ALL_CATEGORIES) {
            return BlocSpotApplication.getSharedDataSource().getAllPointsOfInterest().size();
        } else {
            return BlocSpotApplication.getSharedDataSource().getPointsOfInterestForCategory(currentCategoryView).size();
        }

    }



    class ItemAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, View.OnLongClickListener, CategoryDialogFragment.CategoryDialogListener {

        TextView title;
        TextView address;
        CheckBox visited;
        View wrapper;
        PointOfInterest pointOfInterest;
        OnPointOfInterestClickListener listener;

        public ItemAdapterViewHolder(View itemView) {
            super(itemView);
            wrapper = itemView.findViewById(R.id.rl_poi_wrapper);
            title = (TextView) itemView.findViewById(R.id.tv_point_of_interest_title);
            address = (TextView) itemView.findViewById(R.id.tv_point_of_interest_address);
            visited = (CheckBox) itemView.findViewById(R.id.cb_visited);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            visited.setOnCheckedChangeListener(this);
        }

        void update(PointOfInterest pointOfInterest, OnPointOfInterestClickListener listener) {
            this.pointOfInterest = pointOfInterest;
            title.setText(pointOfInterest.getTitle());
            address.setText(pointOfInterest.getAddress());
            this.listener = listener;

            String categoryName = BlocSpotApplication.getSharedDataSource().getPOIfromDBwithTitle(pointOfInterest.getTitle()).getPoiCategory();
            int color = BlocSpotApplication.getSharedDataSource().getCategoryFromDBWithCategoryName(categoryName).getColor();

            wrapper.setBackgroundColor(color);
        }

        // OnClickListener


        @Override
        public void onClick(View v) {
            listener.onPointOfInterestClick(pointOfInterest);
           // Toast.makeText(itemView.getContext(), pointOfInterest.getTitle(), Toast.LENGTH_SHORT).show();
        }

        // OnCheckedChangedListener

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Log.v("Visited", "Checked changed to: " + isChecked);
        }

        @Override
        public boolean onLongClick(View v) {
            listener.onPointOfInterestLongClick(pointOfInterest);
            return true;
        }


        @Override
        public void onCategoryAdded(String categoryNameInput) {

        }

        @Override
        public void onCategoryViewSelected(String categoryViewSelected) {
            currentCategoryView = categoryViewSelected;
            Log.v("ItemAdapter", "categoryViewSelected");

        }
    }

}
