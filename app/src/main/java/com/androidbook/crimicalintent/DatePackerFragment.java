package com.androidbook.crimicalintent;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DatePackerFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog alertDialog= new AlertDialog
                .Builder(getActivity())
                .setTitle(R.string.date_packer_title)
                .setPositiveButton(android.R.string.ok,null)
                .create();
        return alertDialog;
    }
}

