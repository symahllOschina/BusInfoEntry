package com.wdkj.dkhdl.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wdkj.dkhdl.R;

import java.util.List;

/**
 * 自定义PopWindow, 显示下拉框
 */
public class SpinerPopWindow<T> extends PopupWindow{


    private LayoutInflater inflater;
    private ListView mListView;
    private BaseAdapter adapter;

    private int popupWidth;
    private int popupHeight;

    View view;

    public SpinerPopWindow(Context context, BaseAdapter adapter,AdapterView.OnItemClickListener clickListener) {
        super(context);
        inflater=LayoutInflater.from(context);
        this.adapter = adapter;
        init(clickListener);
    }

    private void init(AdapterView.OnItemClickListener clickListener){
        view = inflater.inflate(R.layout.spinner_window_layout, null);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置可以获得焦点
        setFocusable(true);
        // 设置弹窗内可点击
        setTouchable(true);
        // 设置弹窗外可点击
        setOutsideTouchable(true);

        ColorDrawable dw = new ColorDrawable(0x00);
        setBackgroundDrawable(dw);
        mListView = (ListView) view.findViewById(R.id.spinner_window_listView);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(clickListener);



    }

    /**
     * 让PopWindow显示在指定控件上方时调用
     */
    public void showUp(View v) {
        setHeight(500);
        //获取自身的长宽高
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupWidth = view.getMeasuredWidth();
        popupHeight = view.getMeasuredHeight();
        //获取需要在其上方显示的控件的位置信息
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        //在控件上方显示
//        showAtLocation(v, Gravity.CENTER, (location[0] + v.getWidth() / 2) - popupWidth/2, 150);
        showAtLocation(v, Gravity.NO_GRAVITY, location[0], location[1] - popupHeight-300);

    }

    /**
     * 让PopWindow显示在指定控件上方时调用
     */
    public void showDown(View v) {
        showAsDropDown(v);
    }



}

