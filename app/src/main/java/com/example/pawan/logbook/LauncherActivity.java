package com.example.pawan.logbook;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;

import java.io.File;

public class LauncherActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    Button buttonNew;
    Button buttonShow;
    Button buttonUpload;
    Button buttonExit;

    private static final String TAG = "MyActivity";
    private static final int REQUEST_CODE_RESOLUTION = 1;
    GoogleApiClient googleApiClient;
    boolean refresh;

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
        buttonUpload = (Button) findViewById(R.id.buttonLauncherActivity_Upload);
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

    public void listnerUploadbutton(View view) {
        refresh = false;
        if (googleApiClient == null)
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(Drive.API)
                    .addScope(Drive.SCOPE_FILE)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        googleApiClient.connect();
    }



    @Override
    protected void onPause() {
        if (googleApiClient != null) {
            googleApiClient.disconnect();
            googleApiClient = null;
        }
        super.onPause();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Toast.makeText(LauncherActivity.this, "Connected", Toast.LENGTH_SHORT).show();

        final File baseDirectory = new File(RecordManager.baseDirectory);

        final Hierarchy hierarchy = new Hierarchy(googleApiClient);
        if (refresh)
            new Thread(new Runnable() {
                @Override
                public void run() {
                    hierarchy.refresh();
                    hierarchy.createHierarchy(baseDirectory, null);
                }
            }).start();
        else
            new Thread(new Runnable() {
                @Override
                public void run() {
                    hierarchy.createHierarchy(baseDirectory, null);
                }
            }).start();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        Log.i(TAG, "GoogleApiClient connection failed: " + result.toString());
        Toast.makeText(LauncherActivity.this, "Still Trying to Connect", Toast.LENGTH_SHORT).show();
        if (!result.hasResolution()) {

            // show the localized error dialog.
            GoogleApiAvailability.getInstance().getErrorDialog(this, result.getErrorCode(), 0).show();
            return;
        }

        /**
         *  The failure has a resolution. Resolve it.
         *  Called typically when the app is not yet authorized, and an  authorization
         *  dialog is displayed to the user.
         */

        try {

            result.startResolutionForResult(this, REQUEST_CODE_RESOLUTION);

        } catch (IntentSender.SendIntentException e) {

            Log.e(TAG, "Exception while starting resolution activity", e);
        }
    }


    public void buttonCreateFolder(View view) {
        refresh = false;
        if (googleApiClient == null)
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(Drive.API)
                    .addScope(Drive.SCOPE_FILE)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        googleApiClient.connect();
    }


    public void buttonRefresh(View view) {
        refresh = true;
        if (googleApiClient == null)
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(Drive.API)
                    .addScope(Drive.SCOPE_FILE)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        googleApiClient.connect();
    }

}

