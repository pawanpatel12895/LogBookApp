package com.example.pawan.logbook;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pawan on 30/12/16.
 */
public class CheckBoxGroupView extends LinearLayout{
    List<CheckBox> checkBoxList = new ArrayList<>();
    public CheckBoxGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        ViewGroup.LayoutParams layoutParams= new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(layoutParams);
    }
    void refresh()
    {   invalidate();
        requestLayout();
    }
    void put(CheckBox checkBox)
    {
        addView(checkBox);
        checkBoxList.add(checkBox);
    }


}