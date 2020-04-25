package com.androidbook.crimicalintent;

import android.content.Context;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab crimeLab;
    private List<Crime> mCrime ;

    public static CrimeLab get(Context context){
        if(crimeLab==null)
            crimeLab= new CrimeLab(context);
        return crimeLab;

    }

    public List<Crime> getCrimes(){
        return mCrime;
    }

    public Crime getCrime(UUID id){
        for (Crime cr: mCrime){
            if(cr.getmId().equals(id))
                return cr;
        }
        return null;
    }


    private CrimeLab(Context context){
        mCrime= new ArrayList<>();

        for(int i=0; i<100;i++){
            Crime crime = new Crime();
            crime.setmTitle("Crime #"+i);
            crime.setmSolved(i%2==0);
            crime.setmRequiresPolice((i%3==0)||(i%5==0));
            mCrime.add(crime);
        }
    }
}
