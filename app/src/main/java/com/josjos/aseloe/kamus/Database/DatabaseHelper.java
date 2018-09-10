package com.josjos.aseloe.kamus.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.josjos.aseloe.kamus.Database.DatabaseContract.EN_ID;
import static com.josjos.aseloe.kamus.Database.DatabaseContract.ID_EN;
import static com.josjos.aseloe.kamus.Database.DatabaseContract.KolomKamus.KATA;
import static com.josjos.aseloe.kamus.Database.DatabaseContract.KolomKamus.KUNCI;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static String DATABASE_NAME = "dbKamus";

    private static final int DATABASE_VERSION = 1;

    public static String CREATE_TABLE_EN_ID = "create table " + EN_ID +
            " (" + _ID + " integer primary key autoincrement, " +
            KUNCI + " text not null, " +
            KATA + " text not null);";

    public static String CREATE_TABLE_ID_EN = "create table " + ID_EN +
            " (" + _ID + " integer primary key autoincrement, " +
            KUNCI + " text not null, " +
            KATA + " text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_EN_ID);
        db.execSQL(CREATE_TABLE_ID_EN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + EN_ID);
        db.execSQL("DROP TABLE IF EXISTS " + ID_EN);
        onCreate(db);
    }
}
