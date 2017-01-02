package com.example.pawan.logbook;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by pawan on 1/1/17.
 */
public class RecordView extends LinearLayout {
    private LinearLayout directoriesLayout;
    private LinearLayout textFileLayout;
    private LinearLayout imageLayout;

    public RecordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        setPadding(5, 5, 5, 5);
        setBackgroundColor(Color.parseColor("#eeeeee"));
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        init();
    }

    public void addImage(TextView imageView) {
        imageLayout.addView(imageView);
    }
    public void addTextFile(TextView textView) {
        textFileLayout.addView(textView);
    }
    public void addDirectory(TextView textView) {
        directoriesLayout.addView(textView);

    }

    private void init() {
        directoriesLayout = new LinearLayout(getContext());
        textFileLayout = new LinearLayout(getContext());
        imageLayout = new LinearLayout(getContext());

        //   directoriesLayout.setBackgroundColor(Color.parseColor("#aaaaaa"));

        LinearLayout.LayoutParams layoutParams = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(5, 5, 5, 5);

        directoriesLayout.setOrientation(VERTICAL);
        textFileLayout.setOrientation(VERTICAL);
        imageLayout.setOrientation(VERTICAL);

        directoriesLayout.setLayoutParams(layoutParams);
        textFileLayout.setLayoutParams(layoutParams);
        imageLayout.setLayoutParams(layoutParams);

        addView(directoriesLayout);
        addView(textFileLayout);
        addView(imageLayout);
    }

    public void reset() {
        directoriesLayout.removeAllViews();
        textFileLayout.removeAllViews();
        imageLayout.removeAllViews();
        ;
    }
}
