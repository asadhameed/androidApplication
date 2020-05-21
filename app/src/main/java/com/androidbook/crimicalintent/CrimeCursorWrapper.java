package com.androidbook.crimicalintent;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.androidbook.crimicalintent.CrimeDbSchema.CrimeTable;

import java.util.Date;
import java.util.UUID;

public class CrimeCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Crime getCrime(){
        String uuid=getString(getColumnIndex(CrimeTable.Cols.UUID));
        String Title =getString(getColumnIndex(CrimeTable.Cols.TITLE));
        long date=getLong(getColumnIndex(CrimeTable.Cols.DATE));
        int isSolved=getInt(getColumnIndex(CrimeTable.Cols.SOLVED));

        Crime crime= new Crime(UUID.fromString(uuid));
        crime.setmTitle(Title);
        crime.setmDate(new Date(date));
        crime.setmSolved(isSolved!=0);
        return  crime;
    }
}
