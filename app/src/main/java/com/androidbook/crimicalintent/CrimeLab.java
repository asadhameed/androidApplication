package com.androidbook.crimicalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;

import com.androidbook.crimicalintent.CrimeDbSchema.CrimeTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab crimeLab;
   // private List<Crime> mCrime ;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CrimeLab get(Context context){
        if(crimeLab==null)
            crimeLab= new CrimeLab(context);
        return crimeLab;

    }

    public List<Crime> getCrimes(){
        List<Crime> crimes= new ArrayList<>();
        CrimeCursorWrapper cursorWrapper= queryCrime(null,null);
        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()){
                crimes.add(cursorWrapper.getCrime());
                cursorWrapper.moveToNext();

            }
        }finally {
            cursorWrapper.close();
        }
        return crimes;
    }
    public void addCrime(Crime crime){
        ContentValues values=getContentValues(crime);
        mDatabase.insert(CrimeTable.NAME,null,values);
        //mCrime.add(crime);
    }

    public void updateCrime(Crime crime){
        String uuid= crime.getmId().toString();

        ContentValues values= getContentValues(crime);
        mDatabase.update(CrimeTable.NAME, values,CrimeTable.Cols.UUID +" = ?" ,new String[] {uuid} );

    }
    public Crime getCrime(UUID id){
        CrimeCursorWrapper crimeCursorWrapper=queryCrime(CrimeTable.Cols.UUID+ " =  ?" , new String[]{id.toString()});
        try{
            if(crimeCursorWrapper.getCount()==0)
                    return  null;
            crimeCursorWrapper.moveToFirst();
            return  crimeCursorWrapper.getCrime();
        }finally {
            crimeCursorWrapper.close();
        }
//
    }


    private CrimeLab(Context context){
        mContext=context.getApplicationContext();
        mDatabase=new CrimeBaseHelper(mContext).getWritableDatabase();
       // mCrime= new ArrayList<>();
    }

    private static ContentValues getContentValues(Crime crime){
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getmId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getmTitle());
        values.put(CrimeTable.Cols.DATE, crime.getmDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.ismSolved()?1:0);
        return values;
    }

    private CrimeCursorWrapper queryCrime(String whereClause, String[] whereArgs){
        Cursor cursor= mDatabase.query(CrimeTable.NAME,null, whereClause, whereArgs, null ,null,null);
        return new CrimeCursorWrapper(cursor) ;
    }
}
