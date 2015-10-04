package com.izor066.android.blocspot.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.izor066.android.blocspot.R;

/**
 * Created by igor on 4/10/15.
 */
public class CategoryDialogFragment extends DialogFragment implements TextView.OnEditorActionListener {

    EditText categoryNameInput;

//    public CategoryDialogFragment() {
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.category_dialog_fragment, container);
        categoryNameInput = (EditText) view.findViewById(R.id.et_category_name);
        categoryNameInput.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        categoryNameInput.setOnEditorActionListener(this);
        return view;
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

    public interface CategoryDialogListener {
        void onCategoryAdded(String categoryNameInput);
    }
}
