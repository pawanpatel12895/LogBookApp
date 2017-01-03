package com.example.pawan.logbook;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ActivityAddSnaps extends AppCompatActivity {
    private String SelecetdLocation;
    private String SelectdPanel;
    private String SelectdDevice;
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
        //       crap();
    }

    private void crap() {

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(3, 3, 3, 3);
        for (int i = 0; i < 4; ++i) {
            ImageView imageView = new ImageView(ActivityAddSnaps.this);
            imageView.setImageResource(R.drawable.bharatpetlogo);
            imageView.setLayoutParams(layoutParams);
            imageGroupView.putImageView(imageView);
        }
        imageGroupView.refresh();
    }

    private void init_views() {
        button_Save = (Button) findViewById(R.id.activitySnaps_buttonSave);
        checkBoxGroupView = (CheckBoxGroupView) findViewById(R.id.activitySnaps_checkBoxGroupview);
        editText_Comment = (EditText) findViewById(R.id.activitySnaps_editTextComment);
        imageGroupView = (ImageGroupView) findViewById(R.id.imageBox);
        imageView_TakeNewSnap = (ImageView) findViewById(R.id.activitySnaps_ImageView_TakeNewImage);

        imageView_TakeNewSnap.setOnClickListener(onClickListener_TakeSnaps);
        button_Save.setOnClickListener(onClickListener_SaveData);
        init_checkBoxGroupView();
    }

    private void init_checkBoxGroupView() {
        String AllPanels[] = getResources().getStringArray(R.array.ArrayPanelContent);
        String CheckBoxList[] = new String[0];
        if (SelectdPanel.equals(AllPanels[1])) {
            String AllDevices[] = getResources().getStringArray(R.array.ArrayDeviceInHVPanelContent);
            if (SelectdDevice.equals(AllDevices[0]) || SelectdDevice.equals(AllDevices[7]))
                CheckBoxList = getResources().getStringArray(R.array.HVPanel1_8Content);
            else if (SelectdDevice.equals(AllDevices[1]) || SelectdDevice.equals(AllDevices[6]))
                CheckBoxList = getResources().getStringArray(R.array.HVPanel2_7Content);
            else if (SelectdDevice.equals(AllDevices[2]) || SelectdDevice.equals(AllDevices[5]))
                CheckBoxList = getResources().getStringArray(R.array.HVPanel3_6Content);
            else if (SelectdDevice.equals(AllDevices[3]))
                CheckBoxList = getResources().getStringArray(R.array.HVPanel4Content);
            else if (SelectdDevice.equals(AllDevices[4]))
                CheckBoxList = getResources().getStringArray(R.array.HVPanel5Content);
        } else if (SelectdPanel.equals(AllPanels[2])) {
            String AllDevices[] = getResources().getStringArray(R.array.ArrayDeviceInMVPanelContent);
            if (SelectdDevice.equals(AllDevices[0]) || SelectdDevice.equals(AllDevices[14]))
                CheckBoxList = getResources().getStringArray(R.array.MVPanel1_15Content);
            else if (SelectdDevice.equals(AllDevices[1]) || SelectdDevice.equals(AllDevices[13]) || SelectdDevice.equals(AllDevices[3]) || SelectdDevice.equals(AllDevices[12]))
                CheckBoxList = getResources().getStringArray(R.array.MVPanel2_4_13_14Content);
            else if (SelectdDevice.equals(AllDevices[2]) || SelectdDevice.equals(AllDevices[11]))
                CheckBoxList = getResources().getStringArray(R.array.MVPanel3_12Content);
            else if (SelectdDevice.equals(AllDevices[4]) || SelectdDevice.equals(AllDevices[10]))
                CheckBoxList = getResources().getStringArray(R.array.MVPanel5_11Content);
            else if (SelectdDevice.equals(AllDevices[5]) || SelectdDevice.equals(AllDevices[9]))
                CheckBoxList = getResources().getStringArray(R.array.MVPanel6_10Content);
            else if (SelectdDevice.equals(AllDevices[6]) || SelectdDevice.equals(AllDevices[8]))
                CheckBoxList = getResources().getStringArray(R.array.MVPanel7_9Content);
        } else if (SelectdPanel.equals(AllPanels[3])) {
            String AllDevices[] = getResources().getStringArray(R.array.ArrayDeviceInLVPanelContent);
            if (SelectdDevice.equals(AllDevices[0]) || SelectdDevice.equals(AllDevices[2]))
                CheckBoxList = getResources().getStringArray(R.array.LVPanel1_3Content);
            else if (SelectdDevice.equals(AllDevices[1]))
                CheckBoxList = getResources().getStringArray(R.array.LVPanel2Content);
        }
        for (String s : CheckBoxList) {
            CheckBox cb = new CheckBox(this);
            cb.setTag(1);
            cb.setText(s);
            checkBoxGroupView.put(cb);
        }
        checkBoxGroupView.refresh();
    }


    private void retrive_intent() {
        SelecetdLocation = getIntent().getStringExtra("intentLocation");
        SelectdPanel = getIntent().getStringExtra("intentPanel");
        SelectdDevice = getIntent().getStringExtra("intentDevice");

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

    private View.OnClickListener onClickListener_TakeSnaps = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, 1888);
        }
    };
    private View.OnClickListener onClickListener_SaveData = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecordManager recordManager = new RecordManager(SelecetdLocation, SelectdPanel, SelectdDevice);

            recordManager.setComment(editText_Comment.getText().toString());
            recordManager.setAttributes(checkBoxGroupView.getCheckBoxCheckedStrings());
            recordManager.setBitmaps(imageGroupView.getImageGroupBitmapList());


            if (recordManager.save(getParent())) {
                Log.d(getApplication().getPackageName(), "Saving of Record done");
                Toast.makeText(ActivityAddSnaps.this, "Record Saved", Toast.LENGTH_SHORT).show();
            } else {
                Log.d(getApplication().getPackageName(), "Saving Failed");
                Toast.makeText(ActivityAddSnaps.this, "Saving Failed", Toast.LENGTH_SHORT).show();
            }

            restartApplication();
        }
    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1888 && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            assert photo != null;
            photo = Bitmap.createScaledBitmap(photo, photo.getWidth()*imageView_TakeNewSnap.getHeight()/photo.getHeight(), imageView_TakeNewSnap.getHeight(), false);
            ImageView imageView = new ImageView(ActivityAddSnaps.this);
            imageView.setImageBitmap(photo);
            imageGroupView.putImageView(imageView);


        }
    }


    private void restartApplication() {
        Toast.makeText(ActivityAddSnaps.this, "Restating ..", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ActivityAddSnaps.this, LauncherActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
