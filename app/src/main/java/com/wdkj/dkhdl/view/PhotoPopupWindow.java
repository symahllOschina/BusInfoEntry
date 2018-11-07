package com.wdkj.dkhdl.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wdkj.dkhdl.R;


/**
 * 拍照，图库选择PopupWindow
 *
 */
public class PhotoPopupWindow extends PopupWindow{

    private Context context;

    public PhotoPopupWindow(Context context) {
        super(context);
        this.context = context;
    }

    private OnSelectClickListener onSelectClickListener;

    public void setOnSelectClickListener(OnSelectClickListener onSelectClickListener){
        this.onSelectClickListener = onSelectClickListener;
    }

    public interface OnSelectClickListener{
        public void selectListener(int type);
    }

    public void showPhotoWindow() {
        View view = LayoutInflater.from(context).inflate(R.layout.photo_setting_popwindow,null);
        TextView tvGallery = view.findViewById(R.id.photo_setting_popwindow_tvGallery);
        TextView tvCamera = view.findViewById(R.id.photo_setting_popwindow_tvCamera);
        TextView tvCancel = view.findViewById(R.id.photo_setting_popwindow_tvCaccel);
        // 相册
        tvGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int type = 1;
                onSelectClickListener.selectListener(type);
                // 销毁弹出框
                dismiss();
            }
        });
        // 拍照
        tvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int type = 2;
                onSelectClickListener.selectListener(type);
                // 销毁弹出框
                dismiss();
            }
        });

        // 取消
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 销毁弹出框
                dismiss();
            }
        });

        // 设置SelectPicPopupWindow的View
        this.setContentView(view);
        // 设置SelectPicPopupWindow弹出窗口的宽
        this.setWidth(LinearLayout.LayoutParams.FILL_PARENT);
        // 设置SelectPicPopupWindow弹出窗口的高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗口可点击  点击空白处时，隐藏掉pop窗口
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗口动画效果
        this.setAnimationStyle(R.style.AnimBottom);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗口的背景
        this.setBackgroundDrawable(dw);

    }


}