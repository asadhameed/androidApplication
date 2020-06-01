package com.androidbook.crimicalintent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.androidbook.crimicalintent.CrimeDbSchema.CrimeTable;

public class CrimeBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION=1;
    public static final String DATABASE_NAME="crimeBase1.db";
    public CrimeBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ CrimeTable.NAME +"( "+ " _id integer primary key autoincrement ," +
                CrimeTable.Cols.UUID + " , "+
                CrimeTable.Cols.TITLE + " , "+
                CrimeTable.Cols.DATE  + " , "+
                CrimeTable.Cols.SUSPECT +" ,"+
                CrimeTable.Cols.SOLVED + " ) ") ;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
