package com.example.pawan.logbook;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class ActivityAddSnaps extends AppCompatActivity {
    private String SelecetdLocation;
    private String SelecetdPanel;
    private String SelecetdDevice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_snaps);
        init_actionBar();
        retrive_intent();
    }

    private void retrive_intent() {
        SelecetdLocation = getIntent().getStringExtra("intentLocation");
        SelecetdPanel = getIntent().getStringExtra("intentPanel");
        SelecetdDevice = getIntent().getStringExtra("intentDevice");

        Toast.makeText(ActivityAddSnaps.this, SelecetdLocation, Toast.LENGTH_SHORT).show();
        Toast.makeText(ActivityAddSnaps.this, SelecetdPanel, Toast.LENGTH_SHORT).show();
        Toast.makeText(ActivityAddSnaps.this, SelecetdDevice, Toast.LENGTH_SHORT).show();
    }

    private void init_actionBar() {
        ActionBar supportActionBar = getSupportActionBar();
        assert supportActionBar != null;
        supportActionBar.setDisplayHomeAsUpEnabled(true);
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return super.onOptionsItemSelected(item);
        }
        return false;
    }
}
