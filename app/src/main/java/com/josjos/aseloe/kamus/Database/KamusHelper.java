package com.josjos.aseloe.kamus.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.josjos.aseloe.kamus.Model.KamusModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.josjos.aseloe.kamus.Database.DatabaseContract.EN_ID;
import static com.josjos.aseloe.kamus.Database.DatabaseContract.ID_EN;
import static com.josjos.aseloe.kamus.Database.DatabaseContract.KolomKamus.KATA;
import static com.josjos.aseloe.kamus.Database.DatabaseContract.KolomKamus.KUNCI;

public class KamusHelper {

    private Context context;
    private DatabaseHelper dataBaseHelper;
    private SQLiteDatabase database;

    public KamusHelper(Context context) {
        this.context = context;
    }

    public KamusHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dataBaseHelper.close();
    }


    public ArrayList<KamusModel> getDatabyKey(String key, boolean isEnglish) {

        String TABLE_NAME = isEnglish ? EN_ID : ID_EN;
        ArrayList<KamusModel> arrayList = new ArrayList<>();
        try {
            open();

            Cursor cursor = database.query(TABLE_NAME, null, KUNCI + " LIKE ?", new String[]{key}, null, null, _ID + " ASC", null);
            cursor.moveToFirst();

            KamusModel kamusModel;
            if (cursor.getCount() > 0) {
                do {
                    kamusModel = new KamusModel();
                    kamusModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                    kamusModel.setKata(cursor.getString(cursor.getColumnIndexOrThrow(KATA)));
                    kamusModel.setKunci(cursor.getString(cursor.getColumnIndexOrThrow(KUNCI)));
                    arrayList.add(kamusModel);
                    cursor.moveToNext();

                } while (!cursor.isAfterLast());
            }
            cursor.close();
            close();
        } catch (android.database.SQLException e){
            e.printStackTrace();
        }
        return arrayList;
    }

    public ArrayList<KamusModel> getAllData(boolean isEnglish) {

        String TABLE_NAME = isEnglish ? EN_ID : ID_EN;
        ArrayList<KamusModel> arrayList = new ArrayList<>();
        try {
            open();
            Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
            cursor.moveToFirst();

            KamusModel kamusModel;
            if (cursor.getCount() > 0) {
                do {
                    kamusModel = new KamusModel();
                    kamusModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                    kamusModel.setKata(cursor.getString(cursor.getColumnIndexOrThrow(KATA)));
                    kamusModel.setKunci(cursor.getString(cursor.getColumnIndexOrThrow(KUNCI)));

                    arrayList.add(kamusModel);
                    cursor.moveToNext();

                } while (!cursor.isAfterLast());
            }
            cursor.close();
        } catch (android.database.SQLException e){
            e.printStackTrace();
        }
        return arrayList;
    }

    public void insertTransaction(ArrayList<KamusModel> kamusModel, boolean isEnglish) {

        try {
            open();
            String TABLE_NAME = isEnglish ? EN_ID : ID_EN;
            database.beginTransaction();

            String sql = "INSERT INTO " + TABLE_NAME + " (" + KUNCI + ", " + KATA
                    + ") VALUES (?, ?)";

            for (int i = 0; i < kamusModel.size(); i++){
                SQLiteStatement stmt = database.compileStatement(sql);
                stmt.bindString(1, kamusModel.get(i).getKunci());
                stmt.bindString(2, kamusModel.get(i).getKata());
                stmt.execute();
                stmt.clearBindings();
            }
            database.setTransactionSuccessful();
            database.endTransaction();

        } catch (android.database.SQLException e){
            e.printStackTrace();
        }

    }
}
