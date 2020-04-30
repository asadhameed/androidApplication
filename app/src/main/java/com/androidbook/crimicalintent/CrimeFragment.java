package com.androidbook.crimicalintent;


import android.os.BadParcelableException;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.UUID;

public class CrimeFragment extends Fragment {
    private Crime mCrime;
    private EditText mTitleField;
    private Button btnDate;
    private CheckBox cBoxSolved;
    private static final String ARG_CRIME_ID="crime_id";

    public  static  CrimeFragment newInstance(UUID crimeid){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeid);
        CrimeFragment fragment= new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID crimeID= (UUID) getArguments().getSerializable(ARG_CRIME_ID);
                mCrime= CrimeLab.get(getActivity()).getCrime(crimeID);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_crime, container, false);
        mTitleField= v.findViewById(R.id.crime_title);
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setmTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnDate= v.findViewById(R.id.crime_date);
        btnDate.setText(mCrime.getmDate().toString());
        btnDate.setEnabled(false);
        mTitleField.setText(mCrime.getmTitle());

        cBoxSolved=v.findViewById(R.id.crime_solved);
        cBoxSolved.setChecked(mCrime.ismSolved());
        cBoxSolved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setmSolved(isChecked);
            }
        });
        return v;
    }
}
