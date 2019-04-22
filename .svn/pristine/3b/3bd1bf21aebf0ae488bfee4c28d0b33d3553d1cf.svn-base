package com.wdkj.dkhdl.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.bm.library.Info;
import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.wdkj.dkhdl.BaseActivity;
import com.wdkj.dkhdl.LoginActivity;
import com.wdkj.dkhdl.R;
import com.wdkj.dkhdl.WelComeActivity;
import com.wdkj.dkhdl.utils.ToastUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * 第三页资料查看原图界面
 */
@ContentView(R.layout.activity_see_img)
public class SeeOriginalImgActivity extends BaseActivity{

    @ViewInject(R.id.see_img_photoView)
    PhotoView photoView;

    String imgPath = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imgPath = getIntent().getStringExtra("imgPath");
        showWaitDialog();
        initView();

        //占位图
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.option_icon);
        Glide.with(this)
                .load(imgPath)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                        hideWaitDialog();
                        ToastUtil.showText(activity,"图片加载失败！",1);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        hideWaitDialog();
                        return false;
                    }
                })
                .apply(options)
                .into(photoView);

    }

    /**
     * 初始化控件
     */
    private void initView(){

        // 启用图片缩放功能
        photoView.enable();

    }
}
