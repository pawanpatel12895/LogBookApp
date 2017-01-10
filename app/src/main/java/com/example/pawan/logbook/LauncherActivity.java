package com.example.pawan.logbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class LauncherActivity extends AppCompatActivity {
    Button buttonNew;
    Button buttonShow;
    Button buttonUpload;
    Button buttonExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        init_buttons();
    }

    private void init_buttons() {
        buttonNew = (Button) findViewById(R.id.buttonLauncherActivity_New);
        buttonNew.setOnClickListener(listnerNewbutton);
        buttonShow = (Button) findViewById(R.id.buttonLauncherActivity_Show);
        buttonShow.setOnClickListener(listnerShowbutton);
      //  buttonUpload = (Button) findViewById(R.id.buttonLauncherActivity_Upload);
        buttonUpload.setOnClickListener(listnerUploadbutton);
    }





    private View.OnClickListener listnerShowbutton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(LauncherActivity.this, ActivityRecordListDisplay.class);
            startActivity(intent);
        }
    };
    private View.OnClickListener listnerNewbutton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(LauncherActivity.this, ActivityAddChooseOptions.class);
            startActivity(intent);
        }
    };
    private View.OnClickListener listnerUploadbutton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        }
    };

}

