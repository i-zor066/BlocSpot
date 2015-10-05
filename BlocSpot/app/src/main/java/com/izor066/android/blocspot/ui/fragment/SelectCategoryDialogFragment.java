package com.izor066.android.blocspot.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.izor066.android.blocspot.BlocSpotApplication;
import com.izor066.android.blocspot.R;

import java.util.List;

/**
 * Created by igor on 5/10/15.
 */
public class SelectCategoryDialogFragment extends DialogFragment implements AdapterView.OnItemSelectedListener {

    Spinner selectCategorySpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.category_select_dialog_fragment, container);
        selectCategorySpinner = (Spinner) view.findViewById(R.id.sp_select_category);
        getSpinnerData();
        selectCategorySpinner.setOnItemSelectedListener(this);

        return view;
    }

    private void getSpinnerData () {
        List<String> spinnerNames = BlocSpotApplication.getSharedDataSource().getAllCategoryNames();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spinnerNames );
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectCategorySpinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        CategorySelectedListener categorySelectedListener = (CategorySelectedListener) getActivity();
        categorySelectedListener.onCategorySelected(parent.getItemAtPosition(position).toString());
        selectCategorySpinner.setSelection(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface CategorySelectedListener {
        void onCategorySelected(String selectedCategoryName);
    }
}
