package com.androidbook.crimicalintent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;
import java.util.UUID;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class CrimePagerActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private List<Crime> mCRimes;
    private static final String ARG_CRIME_ID="crime_id";

    public static Intent newIntent(Context context, UUID crimeID){
        Intent intent = new Intent(context, CrimePagerActivity.class);
        intent.putExtra(ARG_CRIME_ID,crimeID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID crimeID= (UUID) getIntent().getSerializableExtra(ARG_CRIME_ID);
        mViewPager= findViewById(R.id.crime_view_pager);
        mCRimes=CrimeLab.get(this).getCrimes();
        FragmentManager fm= getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter (fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                Crime crime=mCRimes.get(position);

                return CrimeFragment.newInstance(crime.getmId());
            }

            @Override
            public int getCount() {
                return mCRimes.size();
            }
        });

        for(int i=0 ;i <mCRimes.size();i++){
            if(mCRimes.get(i).getmId().equals(crimeID)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
