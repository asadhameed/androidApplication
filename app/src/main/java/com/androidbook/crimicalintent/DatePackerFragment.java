package com.androidbook.crimicalintent;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.Date;

public class DatePackerFragment extends DialogFragment {
    public final static String ARG_DATE="com.androidbook.crimicalintent.date";
    private DatePicker crimeDatePicker;
    public static DatePackerFragment newIntent(Date date){
        Bundle arg= new Bundle();
        arg.putSerializable(ARG_DATE,date);
        DatePackerFragment datePackerFragment= new DatePackerFragment();
        datePackerFragment.setArguments(arg);
        return datePackerFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View v= LayoutInflater.from(getActivity()).inflate(R.layout.crime_date_picker,null);
        crimeDatePicker= v.findViewById(R.id.crime_date_picker);
        Date crimeDate = (Date) getArguments().getSerializable(ARG_DATE);
        final Calendar calendar= Calendar.getInstance();
        calendar.setTime(crimeDate);
        crimeDatePicker.init(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),null);
        AlertDialog alertDialog= new AlertDialog
                .Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_packer_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        calendar.set(crimeDatePicker.getYear(),crimeDatePicker.getMonth(),crimeDatePicker.getDayOfMonth());
                        sendResult(calendar.getTime(), Activity.RESULT_OK);
                    }
                })
                .create();
        return alertDialog;
    }

    private void sendResult(Date date, int resultCode){
        Intent intent = new Intent();
        intent.putExtra(ARG_DATE,date);
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);

    }
}

