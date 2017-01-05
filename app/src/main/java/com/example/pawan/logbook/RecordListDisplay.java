package com.example.pawan.logbook;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class RecordListDisplay extends AppCompatActivity {
    String pwd = RecordManager.baseDirectory;
    RecordView recordView;
    TextView header;
    private File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list_display);
        init_actionBar();
        init_views();
    }

    @Override
    protected void onResume() {
        super.onResume();
        placeContentsOfPWD();
    }

    private void placeContentsOfPWD() {
        if(RecordManager.isDirectory()==false)
        {
            Toast.makeText(RecordListDisplay.this, "Nothing to Display", Toast.LENGTH_SHORT).show();
            return ;
        }
        File[] contentList = RecordManager.retriveList(pwd);
        recordView.reset();
        for (File file : contentList) {
            if (file.isDirectory()) {

                recordView.addDirectory(getDirectoryTextView(file));

            } else {
                if (file.getName().endsWith(".txt"))
                    recordView.addTextFile(getTextFileView(file));

                else if (file.getName().endsWith(".jpg"))
                    recordView.addImage(getImageView(file));

            }

        }
    }

    private TextView getImageView(final File file) {
        TextView textView = new TextView(RecordListDisplay.this);
        textView.setText(file.getName());
        LinearLayout.LayoutParams  layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(5,5,5,5);
        textView.setLayoutParams(layoutParams);
        textView.setBackgroundColor(Color.parseColor("#aaaaaa"));
        textView.setPadding(20,20,20,20);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.fromFile(file);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "image/*");
                startActivity(intent);
            }
        });
        return textView;
    }

    private TextView getTextFileView(final File file) {TextView textView = new TextView(RecordListDisplay.this);
        textView.setText(file.getName());
        LinearLayout.LayoutParams  layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(5,5,5,5);
        textView.setLayoutParams(layoutParams);
        textView.setBackgroundColor(Color.parseColor("#aaaaaa"));
        textView.setPadding(20,20,20,20);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.fromFile(file);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "text/*");
                startActivity(intent);
            }
        });
        return textView;
    }

    private TextView getDirectoryTextView(final File file) {

        TextView textView = new TextView(RecordListDisplay.this);
        textView.setText(file.getName());
        LinearLayout.LayoutParams  layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(5,5,5,5);
        textView.setLayoutParams(layoutParams);
        textView.setBackgroundColor(Color.parseColor("#aaaaaa"));
        textView.setPadding(20,20,20,20);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordView.reset();
                pwd = file.getAbsolutePath();
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
}
