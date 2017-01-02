package com.example.pawan.logbook;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

        check_permissions();
        init_buttons();
    }


    private void init_buttons() {
        buttonNew = (Button) findViewById(R.id.buttonLauncherActivity_New);
        buttonNew.setOnClickListener(listnerNewbutton);
        buttonShow = (Button) findViewById(R.id.buttonLauncherActivity_Show);
        buttonShow.setOnClickListener(listnerShowbutton);
        buttonUpload = (Button) findViewById(R.id.buttonLauncherActivity_Upload);
        buttonUpload.setOnClickListener(listnerUploadbutton);
        buttonExit = (Button) findViewById(R.id.buttonLauncherActivity_Exit);
        buttonExit.setOnClickListener(listnerExitbutton);
    }


    private void check_permissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //     if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
            //       ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v("Permission Grant", "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
        }
    }


    private View.OnClickListener listnerShowbutton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(LauncherActivity.this,RecordListDisplay.class);
            startActivity(intent);




        }};
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
    private View.OnClickListener listnerExitbutton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };


}

