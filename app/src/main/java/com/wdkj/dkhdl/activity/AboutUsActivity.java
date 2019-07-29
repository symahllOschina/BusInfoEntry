package com.wdkj.dkhdl.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wdkj.dkhdl.BaseActivity;
import com.wdkj.dkhdl.MainActivity;
import com.wdkj.dkhdl.R;
import com.wdkj.dkhdl.bean.UserBean;
import com.wdkj.dkhdl.utils.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * 关于我们
 */
@ContentView(R.layout.activity_about_us)
public class AboutUsActivity extends BaseActivity{

    private Context context;


    @ViewInject(R.id.about_us_tvVersionCode)
    TextView tvVersionCode;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = AboutUsActivity.this;
        setTitle("关于我们");
        try {
            tvVersionCode.setText(Utils.getVersionName(context));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
