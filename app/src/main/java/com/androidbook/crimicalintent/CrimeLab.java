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
    public void addCrime(Crime crime){
        mCrime.add(crime);
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
    }
}
