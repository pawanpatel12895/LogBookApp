package com.example.pawan.logbook;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pawan on 31/12/16.
 */
public class RecordManager {
    static public String baseDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()+File.separator+ "LogBook";


    private String Comment;
    private String[] attributes;
    private List<Bitmap> bitmaps;
    private String Location;
    private String Panel;
    private String Device;
    String TAG = "RecordManager";

    public RecordManager() {
        Comment = new String("");
        attributes = new String[]{};
        bitmaps = new ArrayList<>();
        Location = null;
        Panel = null;
        Device = null;
    }
    public RecordManager(String Location, String Panel, String Device) {
        this.Location = Location;
        this.Panel = Panel;
        this.Device = Device;
        Comment = new String("");
        attributes = new String[]{};
        bitmaps = new ArrayList<>();
    }

    public String getDevice() {
        return Device;
    }
    public String getLocation() {
        return Location;
    }
    public String getPanel() {
        return Panel;
    }
    public String getComment() {
        return Comment;
    }
    public List<Bitmap> getBitmaps() {
        return bitmaps;
    }
    public String[] getAttributes() {
        return attributes;
    }
    public void setComment(String comment) {
        Comment = comment;
    }
    public void setAttributes(String[] attributes) {
        this.attributes = attributes;
    }
    public void setAttributes(List<String> attributeList) {
        attributes = (String[]) attributeList.toArray();
    }
    public void setLocation(String location) {
        this.Location = location;
    }
    public void setPanel(String panel) {
        this.Panel = panel;
    }
    public void setDevice(String device) {
        this.Device = device;
    }
    public void setBitmaps(List<Bitmap> bitmaps) {
        this.bitmaps = bitmaps;
    }

    public boolean save(Activity parent) {
        if (Comment == null && bitmaps == null)
            return false;
        if (Location == null || Panel == null || Device == null)
            return false;

        File AppDirectory = new File(baseDirectory);
        File LocationDirectory = new File(AppDirectory, Location);
        File PanelDirectory = new File(LocationDirectory, Panel);
        File DeviceDirectory = new File(PanelDirectory, Device);
        File RecordDirectory = new File(DeviceDirectory, getTime());

        Log.d(TAG, "Trying to save to :" + RecordDirectory.getAbsolutePath());


        if (RecordDirectory.mkdirs()) {
            Log.d(TAG," directory made : " + RecordDirectory);
        } else {
            Log.d(TAG, "error in making directory");
            check_permissions(parent);
            return false;
        }

        if(saveComment(RecordDirectory)&& savePhotos(RecordDirectory)&& saveAttributes(RecordDirectory))
            return true;
        else
            return false;
    }


    private boolean saveComment(File recordDirectory) {
        try {
            File fileComment= new File(recordDirectory,"Comment.txt");
            FileWriter fileWriter = new FileWriter(fileComment);
            fileWriter.write(Comment);
            fileWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    private boolean savePhotos(File recordDirectory) {
        try {   File file;
                if(bitmaps!=null)
                for(int i=0;i<bitmaps.size();++i)
                {   file = new File(recordDirectory,"image"+(i+1)+".jpg");
                    FileOutputStream fos = new FileOutputStream(file);
                    bitmaps.get(i).compress(Bitmap.CompressFormat.JPEG,100,fos);
                    fos.flush();
                    fos.close();
                }
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    private boolean saveAttributes(File recordDirectory) {
        try {
            File fileComment= new File(recordDirectory,"Attributes.txt");
            FileWriter fileWriter = new FileWriter(fileComment);
            for(String s : attributes)
            fileWriter.write(s+"\n");
            fileWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    static File[] retriveList(String path)
    {   File file = new File(path);
        return file.listFiles();
    }


    public void check_permissions(Activity activity) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //     if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                //       ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                if (ContextCompat.checkSelfPermission(activity,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, 1);
                }
            }
        }

    private String getTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd hhmm");
        Date date = new Date();
        return dateFormat.format(date);
    }
}