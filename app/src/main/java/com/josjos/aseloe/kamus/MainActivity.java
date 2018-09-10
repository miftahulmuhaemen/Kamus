package com.josjos.aseloe.kamus;

import android.os.AsyncTask;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.josjos.aseloe.kamus.Adapter.rv_adapter;
import com.josjos.aseloe.kamus.Database.KamusHelper;
import com.josjos.aseloe.kamus.Model.KamusModel;
import com.josjos.aseloe.kamus.Prefs.ApplicationPref;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    ActionBarDrawerToggle toggle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.rv_main)
    RecyclerView recyclerView;
    @BindView(R.id.searchview)
    SearchView searchView;

    rv_adapter rv_adapter;
    KamusHelper kamus_helper;
    ApplicationPref applicationPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        applicationPref = new ApplicationPref(this);

        kamus_helper = new KamusHelper(this);
        rv_adapter = new rv_adapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);

                        int id = menuItem.getItemId();
                        if (id == R.id.nav_en_id) {
                            if(applicationPref.getLanguage() != true){
                                applicationPref.setLanguage(true);
                                rv_adapter.replace(kamus_helper.getAllData(applicationPref.getLanguage()));
                            }
                        } else if (id == R.id.nav_id_en && applicationPref.getLanguage() != false){
                            applicationPref.setLanguage(false);
                            rv_adapter.replace(kamus_helper.getAllData(applicationPref.getLanguage()));
                        }
                        drawer.closeDrawers();
                        return true;
                    }
                });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryCari) {
                ArrayList<KamusModel> kamusModel = kamus_helper.getDatabyKey(queryCari,applicationPref.getLanguage());
                rv_adapter.replace(kamusModel);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        if(savedInstanceState!=null){
            ArrayList<KamusModel> list;
            list = savedInstanceState.getParcelableArrayList("mainActivityState");
            rv_adapter.setMData(list);
        }
        recyclerView.setAdapter(rv_adapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("mainActivityState",new ArrayList<>(rv_adapter.getMData()));
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
