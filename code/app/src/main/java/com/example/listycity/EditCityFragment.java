package com.example.listycity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EditCityFragment extends DialogFragment {
    interface EditCityDialogListener {
        void editCity();
    }
    private EditCityDialogListener listener;

    // idea from
    // Source - https://stackoverflow.com/a/9932244
    // Posted by Macarse, modified by community. See post 'Timeline' for change history
    // Retrieved 2026-01-20, License - CC BY-SA 3.0
    public static EditCityFragment newInstance(City city){
        EditCityFragment fragment = new EditCityFragment();
        Bundle args = new Bundle();
        args.putSerializable("city", city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditCityDialogListener) {
            listener = (EditCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement EditCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        assert getArguments() != null;
        City city_editable = (City) getArguments().getSerializable("city");

        View view =
                LayoutInflater.from(getContext()).inflate(R.layout.fragment_edit_city, null);

        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        assert city_editable != null;
        editCityName.setText(city_editable.getName());
        editProvinceName.setText(city_editable.getProvince());
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle("Edit city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Add", (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();
                    city_editable.setName(cityName);
                    city_editable.setProvince(provinceName);
                    listener.editCity();
                })
                .create();
    }
}