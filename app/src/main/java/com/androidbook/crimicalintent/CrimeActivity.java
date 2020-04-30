package com.androidbook.crimicalintent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {
    public static final  String CRIMEID= "com.androidbook.crimicalIntent.crimeID";

    @Override
    protected Fragment CreateFragment() {
        UUID crimeID= (UUID) getIntent().getSerializableExtra(CrimeActivity.CRIMEID);
        return  CrimeFragment.newInstance(crimeID);
    }

    public static Intent newIntent(Context con, UUID crimeId){
        Intent intent = new Intent(con , CrimeActivity.class);
        intent.putExtra(CRIMEID, crimeId);
        return intent;
    }


}
