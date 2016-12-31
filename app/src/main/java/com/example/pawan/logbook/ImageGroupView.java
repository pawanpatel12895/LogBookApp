package com.example.pawan.logbook;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pawan on 30/12/16.
 */
public class ImageGroupView extends LinearLayout {
    List<ImageView> imageViewList = new ArrayList<>();
    public ImageGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
    }
    void refresh()
    {   invalidate();
        requestLayout();
    }
    void putImageView(ImageView imageView)
    {   addView(imageView);
        imageViewList.add(imageView);
    }
}
