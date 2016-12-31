package com.example.pawan.logbook;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ActivityAddSnaps extends AppCompatActivity {
    private String SelecetdLocation;
    private String SelecetdPanel;
    private String SelecetdDevice;
    private CheckBoxGroupView checkBoxGroupView;
    private EditText editText_Comment;
    private Button button_Save;
    private ImageGroupView imageGroupView;
    private ImageView imageView_TakeNewSnap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_snaps);
        init_actionBar();
        retrive_intent();
        init_views();
        crap();
    }

    private void crap() {
        for(int i=0;i<5;++i)
        {
            CheckBox checkBox = new CheckBox(ActivityAddSnaps.this);
            checkBox.setText("checkbox "+i);
            checkBoxGroupView.put(checkBox);
        }
        checkBoxGroupView.refresh();
   //     FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
  //      layoutParams.setMargins(5,5,5,5);
        for(int i=0;i<4;++i)
        {
            ImageView imageView = new ImageView(ActivityAddSnaps.this);
            imageView.setImageResource(R.drawable.bharatpetlogo);
    //        imageView.setLayoutParams(layoutParams);
            imageGroupView.putImageView(imageView);
        }

   //     imageGroupView.setLayoutParams(layoutParams);
        imageGroupView.refresh();
    }

    private void init_views() {
        button_Save = (Button) findViewById(R.id.activitySnaps_buttonSave);
        checkBoxGroupView = (CheckBoxGroupView) findViewById(R.id.activitySnaps_checkBoxGroupview);
        editText_Comment = (EditText) findViewById(R.id.activitySnaps_editTextComment);
        imageGroupView = (ImageGroupView) findViewById(R.id.imageBox);
        imageView_TakeNewSnap = (ImageView) findViewById(R.id.activitySnaps_ImageView_TakeNewImage);

        imageView_TakeNewSnap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityAddSnaps.this, "To Do : take Snap", Toast.LENGTH_SHORT).show();
            }
        });
        button_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityAddSnaps.this, "To Do : Save All", Toast.LENGTH_SHORT).show();
            }
        });
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
