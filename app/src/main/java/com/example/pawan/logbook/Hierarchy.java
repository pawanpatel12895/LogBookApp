package com.example.pawan.logbook;

import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataChangeSet;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by pawan on 10/1/17.
 */
public class Hierarchy {

    private static final String TAG = "Hierarchy";
    GoogleApiClient googleApiClient;


    Hierarchy(GoogleApiClient googleApiClient) {
        this.googleApiClient = googleApiClient;
    }

    void createHierarchy(File file, DriveId ParentdriveId) {
        if (!file.exists()) return;
        if (file.isDirectory()) {
            Log.d(TAG, file.getAbsolutePath());
            DriveId folderToDrive = createFolderToDrive(file, ParentdriveId);
            File[] files = file.listFiles();
            for (File f : files)
                createHierarchy(f, folderToDrive);

        } else {
            Log.d(TAG, file.getAbsolutePath());

            createFileToDrive(file, ParentdriveId);
        }
    }

    private void createFileToDrive(File file, DriveId parentdriveId) {
        DriveContents driveContents = file2Cont(null, file);
        DriveFolder appFolder = parentdriveId.asDriveFolder();
        DriveApi.MetadataBufferResult listChildren = appFolder.listChildren(googleApiClient).await();
        Metadata metadata = null;
        for (Metadata m : listChildren.getMetadataBuffer()) {
            if (m.getTitle().equals(file.getName()) && !m.isTrashed()) {
                Log.d(TAG, "File Already Exist : " + file.getName());
                metadata = m;
                break;
            }

        }
        listChildren.release();;
        MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                .setTitle(file.getName()).build();
        appFolder.createFile(googleApiClient,changeSet,driveContents);

    }

    private DriveId createFolderToDrive(File file, DriveId baseFolder) {
        DriveFolder appFolder;
        if (baseFolder == null) {
            appFolder = Drive.DriveApi.getRootFolder(googleApiClient);
        } else
            appFolder = baseFolder.asDriveFolder();

        DriveApi.MetadataBufferResult listChildren = appFolder.listChildren(googleApiClient).await();
       DriveId driveId = null;
        for (Metadata m : listChildren.getMetadataBuffer()) {
            if (m.getTitle().equals(file.getName()) && !m.isTrashed()) {
                Log.d(TAG, "Folder Already Exist : " + file.getName());
               driveId = m.getDriveId();
                break;
            }

        }
        listChildren.release();;
        if(driveId!=null)return driveId;
        MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                .setTitle(file.getName())
                .build();
        DriveFolder.DriveFolderResult folderResult = appFolder.createFolder(googleApiClient, changeSet).await();
        Log.d(TAG, "New Created : " + file.getName());

        listChildren.release();
        return folderResult.getDriveFolder().getDriveId();
    }


    private DriveContents file2Cont(DriveContents cont, File file) {
        if (file == null) return null;  //--------------------->>>
        if (cont == null) {
            DriveApi.DriveContentsResult r1 = Drive.DriveApi.newDriveContents(googleApiClient).await();
            cont = r1 != null && r1.getStatus().isSuccess() ? r1.getDriveContents() : null;
        }
        if (cont != null) try {
            OutputStream oos = cont.getOutputStream();
            if (oos != null) try {
                InputStream is = new FileInputStream(file);
                byte[] buf = new byte[4096];
                int c;
                while ((c = is.read(buf, 0, buf.length)) > 0) {
                    oos.write(buf, 0, c);
                    oos.flush();
                }
            } finally {
                oos.close();
            }
            return cont; //++++++++++++++++++++++++++++++>>>
        } catch (Exception ignore) {
        }
        return null;   //--------------------->>>
    }

    public void refresh() {
        DriveFolder rootFolder = Drive.DriveApi.getRootFolder(googleApiClient);
        DriveApi.MetadataBufferResult listChildren = rootFolder.listChildren(googleApiClient).await();
        File file = new File(RecordManager.baseDirectory);
        for(Metadata m:listChildren.getMetadataBuffer())
        {
            if(m.getTitle().equals(file.getName()))
            {
                DriveFolder driveFolder =m.getDriveId().asDriveFolder();
                driveFolder.delete(googleApiClient);
            }
        }
        Log.d(TAG,"Refreshed");
    }
}
