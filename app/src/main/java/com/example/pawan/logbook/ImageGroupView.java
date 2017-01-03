package com.example.pawan.logbook;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pawan on 30/12/16.
 */
public class ImageGroupView extends LinearLayout {
    List<ImageView> imageGroupViewList = new ArrayList<>();

    public ImageGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
    }

    void refresh() {
        invalidate();
        requestLayout();
    }

    void putImageView(ImageView imageView) {
        addView(imageView);
        imageGroupViewList.add(imageView);
    }

    public List<ImageView> getImageGroupViewList() {
        return imageGroupViewList;
    }

    public List<Bitmap> getImageGroupBitmapList() {
        List<Bitmap> bitmaps = new ArrayList<>();
        for (ImageView imageView : imageGroupViewList)
            bitmaps.add(((BitmapDrawable) imageView.getDrawable()).getBitmap());
        return bitmaps;
    }
}
