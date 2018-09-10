package com.josjos.aseloe.kamus;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.josjos.aseloe.kamus.Database.KamusHelper;
import com.josjos.aseloe.kamus.Model.KamusModel;
import com.josjos.aseloe.kamus.Prefs.ApplicationPref;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new LoadData().execute();
    }


    private class LoadData extends AsyncTask<Void, Integer, Void> {
        final String TAG = LoadData.class.getSimpleName();
        KamusHelper kamus_helper;
        ApplicationPref appPreference;

        @Override
        protected void onPreExecute() {
            kamus_helper = new KamusHelper(SplashScreen.this);
            appPreference = new ApplicationPref(SplashScreen.this);
        }

        @SuppressWarnings("Wrong Thread")
        @Override
        protected Void doInBackground(Void... params) {

            Boolean firstRun = appPreference.getFirstRun();

            if (firstRun) {
                ArrayList<KamusModel> kamusModels = preLoadRaw(R.raw.english_indonesia);
                ArrayList<KamusModel> kamusModels1 = preLoadRaw(R.raw.indonesia_english);
                kamus_helper.insertTransaction(kamusModels, true);
                kamus_helper.insertTransaction(kamusModels1, false);
                appPreference.setFirstRun(false);

            } else {
                try {
                    synchronized (this) {
                        this.wait(2000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }

        @Override
        protected void onPostExecute(Void result) {
            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    public ArrayList<KamusModel> preLoadRaw(int source) {
        ArrayList<KamusModel> kamusModels = new ArrayList<>();
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(source);
            String line = null;
            reader = new BufferedReader(new InputStreamReader(raw_dict));
            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t", 2);
                KamusModel kamusModel;
                kamusModel = new KamusModel(splitstr[0], splitstr[1]);
                kamusModels.add(kamusModel);

            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kamusModels;
    }
}
