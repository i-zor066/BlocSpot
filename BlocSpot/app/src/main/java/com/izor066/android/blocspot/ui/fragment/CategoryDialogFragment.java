package com.izor066.android.blocspot.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.izor066.android.blocspot.BlocSpotApplication;
import com.izor066.android.blocspot.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by igor on 4/10/15.
 */
public class CategoryDialogFragment extends DialogFragment implements TextView.OnEditorActionListener, AdapterView.OnItemSelectedListener {

    EditText categoryNameInput;
    Spinner selectCategoryViewSpinner;
    ArrayAdapter<String> dataAdapter;
    static public final String ALL_CATEGORIES = "All Categories";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.category_dialog_fragment, container);
        categoryNameInput = (EditText) view.findViewById(R.id.et_category_name);
        categoryNameInput.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        categoryNameInput.setOnEditorActionListener(this);
        String currentCatFromBundle = getArguments().getString("currentCategoryView");
        List<String> spinnerSource = new ArrayList<String>();
        spinnerSource.add(ALL_CATEGORIES);
        List<String> categories = BlocSpotApplication.getSharedDataSource().getAllCategoryNames(); //ToDo: Do a list, with first entry "Show All Categories", then iterate this into that list
        for (String category:categories) {
            spinnerSource.add(category);
        }
        selectCategoryViewSpinner = (Spinner) view.findViewById(R.id.sp_select_category_view);
        setupSpinner(spinnerSource);
        selectCategoryViewSpinner.setOnItemSelectedListener(this);
        selectCategoryViewSpinner.setSelection(spinnerSource.indexOf(currentCatFromBundle));
        return view;
    }

    private void setupSpinner(List<String> spinnerNames) {
        dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spinnerNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.setNotifyOnChange(true);
        selectCategoryViewSpinner.setAdapter(dataAdapter);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            CategoryDialogListener categoryDialogListener = (CategoryDialogListener) getActivity();
            categoryDialogListener.onCategoryAdded(categoryNameInput.getText().toString());
            dismiss();
            return true;
        }
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        CategoryDialogListener categoryDialogListener = (CategoryDialogListener) getActivity();
        categoryDialogListener.onCategoryViewSelected(parent.getItemAtPosition(position).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface CategoryDialogListener {
        void onCategoryAdded(String categoryNameInput);
        void onCategoryViewSelected(String categoryViewSelected);
    }
}
