package com.example.pawan.logbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LauncherActivity extends AppCompatActivity {
    Button buttonnew;
    Button buttonshow;
    Button buttonupload;
    Button buttonexit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        init_buttons();
    }

    private void init_buttons() {
        buttonnew = (Button) findViewById(R.id.buttonLauncherActivity_New);
        buttonnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LauncherActivity.this,ActivityAddChooseOptions.class);
                startActivity(intent);
            }
        });
        buttonshow = (Button) findViewById(R.id.buttonLauncherActivity_Show);
        buttonshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        buttonupload = (Button) findViewById(R.id.buttonLauncherActivity_Upload);
        buttonupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        buttonexit = (Button) findViewById(R.id.buttonLauncherActivity_Exit);
        buttonexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
