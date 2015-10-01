package com.izor066.android.blocspot.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.izor066.android.blocspot.BlocSpotApplication;
import com.izor066.android.blocspot.R;
import com.izor066.android.blocspot.api.DataSource;
import com.izor066.android.blocspot.api.model.PointOfInterest;

/**
 * Created by igor on 28/9/15.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemAdapterViewHolder> {

    @Override
    public ItemAdapter.ItemAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.point_of_interest, viewGroup, false);
        return new ItemAdapterViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ItemAdapter.ItemAdapterViewHolder itemAdapterViewHolder, int i) {
        DataSource sharedDataSource = BlocSpotApplication.getSharedDataSource();
        itemAdapterViewHolder.update(sharedDataSource.getAllPointsOfInterest().get(i));

    }

    @Override
    public int getItemCount() {
        return BlocSpotApplication.getSharedDataSource().getAllPointsOfInterest().size();
    }



    class ItemAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

        TextView title;
        TextView address;
        CheckBox visited;
        PointOfInterest pointOfInterest;

        public ItemAdapterViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_point_of_interest_title);
            address = (TextView) itemView.findViewById(R.id.tv_point_of_interest_address);
            visited = (CheckBox) itemView.findViewById(R.id.cb_visited);

            itemView.setOnClickListener(this);
            visited.setOnCheckedChangeListener(this);
        }

        void update(PointOfInterest pointOfInterest) {
            this.pointOfInterest = pointOfInterest;
            title.setText(pointOfInterest.getTitle());
            address.setText(pointOfInterest.getAddress());
        }

        // OnClickListener


        @Override
        public void onClick(View v) {
            Toast.makeText(itemView.getContext(), pointOfInterest.getTitle(), Toast.LENGTH_SHORT).show();
        }

        // OnCheckedChangedListener

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Log.v("Visited", "Checked changed to: " + isChecked);
        }
    }


}
