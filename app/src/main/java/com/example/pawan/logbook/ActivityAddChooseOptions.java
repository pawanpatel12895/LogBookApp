package com.example.pawan.logbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class ActivityAddChooseOptions extends AppCompatActivity {
    Spinner spinnerLocation;
    Spinner spinnerPanel;
    Spinner spinnerDevice;
    Button buttonNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_add_choose_options);
        init_actionBar();
        init_views();
    }

    private void init_views() {
        buttonNext = (Button) findViewById(R.id.buttonActivityAddChooser_Next);
        spinnerLocation = (Spinner) findViewById(R.id.spinnerActivityAddChooser_Location);
        spinnerDevice = (Spinner) findViewById(R.id.spinnerActivityAddChooser_Device);
        spinnerPanel = (Spinner) findViewById(R.id.spinnerActivityAddChooser_Panel);

        final ArrayAdapter<CharSequence> arrayAdapterSpinnerLocation = ArrayAdapter.createFromResource(this, R.array.ArrayLocationContent, R.layout.support_simple_spinner_dropdown_item);
        final ArrayAdapter<CharSequence> arrayAdapterSpinnerPanel = ArrayAdapter.createFromResource(this, R.array.ArrayPanelContent, R.layout.support_simple_spinner_dropdown_item);

        spinnerLocation.setAdapter(arrayAdapterSpinnerLocation);
        spinnerPanel.setAdapter(arrayAdapterSpinnerPanel);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int spinnerLocationSelectedPosition = spinnerLocation.getSelectedItemPosition();
                int spinnerPanelSelectedPosition = spinnerPanel.getSelectedItemPosition();
                int spinnerDeviceSelectedPosition = spinnerDevice.getSelectedItemPosition();
                if(spinnerLocationSelectedPosition==0) {
                    Toast.makeText(ActivityAddChooseOptions.this, "Choose Location", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(spinnerPanelSelectedPosition==0) {
                    Toast.makeText(ActivityAddChooseOptions.this, "Choose Panel", Toast.LENGTH_SHORT).show();
                    return;
                }
                String spinnerLocationSelectedString = arrayAdapterSpinnerLocation.getItem(spinnerLocationSelectedPosition).toString();
                String spinnerPanelSelectedString = arrayAdapterSpinnerPanel.getItem(spinnerPanelSelectedPosition).toString();
                String spinnerDeviceSelectedString = spinnerDevice.getSelectedItem().toString();


                Intent intent = new Intent(ActivityAddChooseOptions.this, ActivityAddSnaps.class);
                intent.putExtra("intentLocation",spinnerLocationSelectedString);
                intent.putExtra("intentPanel",spinnerPanelSelectedString);
                intent.putExtra("intentDevice",spinnerDeviceSelectedString);
                startActivity(intent);
            }
        });
        spinnerPanel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch(position)
                {   case 1:
                        spinnerDevice.setAdapter(ArrayAdapter.createFromResource(getBaseContext(), R.array.ArrayDeviceInHVPanelContent, R.layout.support_simple_spinner_dropdown_item));
                    break;
                    case 2:
                        spinnerDevice.setAdapter( ArrayAdapter.createFromResource(getBaseContext(), R.array.ArrayDeviceInMVPanelContent, R.layout.support_simple_spinner_dropdown_item));
                        break;
                    case 3:
                        spinnerDevice.setAdapter(ArrayAdapter.createFromResource(getBaseContext(), R.array.ArrayDeviceInLVPanelContent, R.layout.support_simple_spinner_dropdown_item));
                        break;
                    default:String[] stringsDevice={"Choose Panel..."};
                        spinnerDevice.setAdapter(new ArrayAdapter(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,stringsDevice));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
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
