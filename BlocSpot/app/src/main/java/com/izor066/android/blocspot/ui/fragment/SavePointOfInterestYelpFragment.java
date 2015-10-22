package com.izor066.android.blocspot.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.izor066.android.blocspot.BlocSpotApplication;
import com.izor066.android.blocspot.R;
import com.izor066.android.blocspot.api.model.PointOfInterest;

import java.util.List;

/**
 * Created by igor on 18/10/15.
 */
public class SavePointOfInterestYelpFragment extends DialogFragment implements AdapterView.OnItemSelectedListener, Button.OnClickListener {

    Spinner selectCategorySpinner;
    ArrayAdapter<String> dataAdapter;
    PointOfInterest pointOfInterestFromMarker;
    PointOfInterest pointOfInterestToSave;
    TextView poiName;
    TextView poiAddress;
    Button submit;
    Button cancel;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.save_poi_yelp_fragment, container);
        poiName = (TextView) view.findViewById(R.id.tv_add_yelp_name);
        poiAddress = (TextView) view.findViewById(R.id.tv_add_yelp_address);
        submit = (Button) view.findViewById(R.id.bt_add_yelp_submit);
        cancel = (Button) view.findViewById(R.id.bt_add_yelp_cancel);

        pointOfInterestFromMarker = getArguments().getParcelable("point_of_interest_from_click");

        poiName.setText(pointOfInterestFromMarker.getTitle());
        poiAddress.setText(pointOfInterestFromMarker.getAddress());

        submit.setOnClickListener(this);
        cancel.setOnClickListener(this);

        selectCategorySpinner = (Spinner) view.findViewById(R.id.sp_select_category_yelp);

        List<String> categories = BlocSpotApplication.getSharedDataSource().getAllCategoryNames();
        setupSpinner(categories);
        selectCategorySpinner.setOnItemSelectedListener(this);


        return view;
    }


    private void setupSpinner(List<String> spinnerNames) {
        dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spinnerNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectCategorySpinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String category = parent.getItemAtPosition(position).toString();
        pointOfInterestToSave = new PointOfInterest(-1, pointOfInterestFromMarker.getTitle(), pointOfInterestFromMarker.getAddress(),pointOfInterestFromMarker.getLatitude(), pointOfInterestFromMarker.getLongitude(), category);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onClick(View v) {

        if (v == v.findViewById(R.id.bt_add_yelp_submit)) {
            if(pointOfInterestToSave == null) {
                Toast.makeText(getActivity(), "Please Select a Category.", Toast.LENGTH_SHORT).show();
            } else {
                BlocSpotApplication.getSharedDataSource().insertPointToDatabase(pointOfInterestToSave);
                dismiss();
                Toast.makeText(getActivity(), pointOfInterestToSave.getTitle() + " saved." + ": " + pointOfInterestToSave.getPoiCategory(), Toast.LENGTH_SHORT).show();

            }

        } else {
            dismiss();
        }

    }


}
