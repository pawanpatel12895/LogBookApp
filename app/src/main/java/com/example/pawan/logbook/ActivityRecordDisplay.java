package com.example.pawan.logbook;

import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ActivityRecordDisplay extends AppCompatActivity {
    String pwd ;
    String Location ;
    String Panel ;
    String Device ;
    String _Date ;
    TextView textViewAttribute;
    TextView textViewInfo;
    TextView textViewComment;
    GridLayout gridLayoutImages;
    ImageView fullscreenImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_display);
        init_actionBar();
        init();
        init_views();
    }

    private void init_views() {
        textViewInfo.setText(Location+"\n"+Panel+"\n"+Device+"\n"+_Date);
        File file  =  new File(pwd);
        if(!file.exists())
            finish();
        File[] files = file.listFiles();
        int i=0;
        for(File file1:files)
        {
            if(file1.getName().endsWith(".jpg"))
            {
                addimage(file1);
                i++;
            }
        }
        if(i==0)
        {
            TextView textView = new TextView(ActivityRecordDisplay.this);
            textView.setText("None");
            gridLayoutImages.addView(textView);
        }
        File file2 = new File(file,"Comment.txt");
        File file3 = new File(file,"Attributes.txt");

        String content = null;
        Scanner sc;
        try {
            sc = new Scanner(file2);
            if(sc.hasNext())
                content =sc.useDelimiter("\\Z").next();
            else content = "None";
            textViewComment.setText(content);

            sc = new Scanner(file3);
            if(sc.hasNext())
            content =sc.useDelimiter("\\Z").next();
            else content = "None";
            textViewAttribute.setText(content);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void addimage(File file1) {
        Uri uri = Uri.fromFile(file1);
        ImageView imageView = new ImageView(ActivityRecordDisplay.this);
      imageView.setPadding(5,5,5,5);
        imageView.setImageURI(uri);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView view1 = (ImageView) view;

                fullscreenImage.setImageBitmap(((BitmapDrawable) view1.getDrawable()).getBitmap());
                fullscreenImage.setVisibility(View.VISIBLE);
            }
        });
        gridLayoutImages.addView(imageView);
    }

    private void init() {
        pwd = getIntent().getStringExtra("pwd");
        Location = getIntent().getStringExtra("Location");
        Panel = getIntent().getStringExtra("Panel");
        Device = getIntent().getStringExtra("Device");
        _Date = getIntent().getStringExtra("Date");

        textViewAttribute = (TextView) findViewById(R.id.activityShowRecord_textViewAttributes);
        textViewComment = (TextView) findViewById(R.id.activityShowRecord_textViewComment);
        textViewInfo = (TextView) findViewById(R.id.activityShowRecord_textViewInfo);
        gridLayoutImages = (GridLayout) findViewById(R.id.activityShowRecord_gridLayoutImages);
        fullscreenImage = (ImageView) findViewById(R.id.activityShowRecord_fullScreenImage);
    }


    private void init_actionBar() {
        ActionBar supportActionBar = getSupportActionBar();
        assert supportActionBar != null;
        supportActionBar.setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(fullscreenImage.getVisibility()==View.VISIBLE)
        {   fullscreenImage.setImageDrawable(null);
            fullscreenImage.setVisibility(View.GONE);
        }
        else
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return super.onOptionsItemSelected(item);
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if(fullscreenImage.getVisibility()==View.VISIBLE)
        {   fullscreenImage.setImageDrawable(null);
            fullscreenImage.setVisibility(View.GONE);
        }
        else
            super.onBackPressed();
    }
}
