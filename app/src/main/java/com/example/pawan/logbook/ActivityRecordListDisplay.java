package com.example.pawan.logbook;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class ActivityRecordListDisplay extends AppCompatActivity {
    String pwd = RecordManager.baseDirectory;
    RecordView recordView;
    TextView header;
    Intent intent ;

    int iter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list_display);
        intent = new Intent(ActivityRecordListDisplay.this,ActivityRecordDisplay.class);
        if (!checkReadPermission())
            if (!giveReadPermission()) {
                Toast.makeText(ActivityRecordListDisplay.this, "Storage Permission Not Granted", Toast.LENGTH_SHORT).show();
                finish();
            }
        if (!checkWritePermission())
            if (!giveWritePermission()) {
                Toast.makeText(ActivityRecordListDisplay.this, "Storage Permission Not Granted", Toast.LENGTH_SHORT).show();
                finish();
            }
        init_actionBar();
        init_views();

    }

    @Override
    protected void onResume() {
        super.onResume();
        pwd = RecordManager.baseDirectory;
        iter = 0;
        placeContentsOfPWD();
    }

    private void placeContentsOfPWD() {
        if (!RecordManager.isDirectory()) {
            Toast.makeText(ActivityRecordListDisplay.this, "Nothing to Display", Toast.LENGTH_SHORT).show();
            return;
        }
        if(iter==4)
        {   intent.putExtra("pwd",pwd);
            startActivity(intent);
            return;
        }

        File[] contentList = RecordManager.retriveList(pwd);
        recordView.reset();
        iter++;
        for (File file : contentList) {
            if (file.isDirectory()) {
                recordView.addDirectory(getDirectoryTextView(file));
            }
        }
    }

    private TextView getDirectoryTextView(final File file) {

        TextView textView = new TextView(ActivityRecordListDisplay.this);
        textView.setText(file.getName());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(5, 5, 5, 5);
        textView.setLayoutParams(layoutParams);
        textView.setBackgroundColor(Color.parseColor("#aaaaaa"));
        textView.setPadding(20, 20, 20, 20);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordView.reset();
                pwd = file.getAbsolutePath();
                if(iter==1)
                    intent.putExtra("Location",file.getName());
                else if (iter==2)
                    intent.putExtra("Panel",file.getName());
                else if (iter==3)
                    intent.putExtra("Device",file.getName());
                else if (iter==4)
                    intent.putExtra("Date",file.getName());
                placeContentsOfPWD();
            }
        });
        return textView;
    }

    private void init_views() {
        recordView = (RecordView) findViewById(R.id.activityShowRecords_RecordView);
        header = (TextView) findViewById(R.id.activityShowRecords_textViewPath);
    }

    private void init_actionBar() {
        ActionBar supportActionBar = getSupportActionBar();
        assert supportActionBar != null;
        supportActionBar.setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();

                return super.onOptionsItemSelected(item);
        }
        return false;
    }


    boolean checkWritePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            return false;
        return true;
    }

    boolean giveWritePermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            return false;
        return true;
    }

    boolean checkReadPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            return false;
        return true;
    }

    boolean giveReadPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            return false;
        return true;
    }
}
