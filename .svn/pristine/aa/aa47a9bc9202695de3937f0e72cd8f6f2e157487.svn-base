package com.wdkj.dkhdl.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wdkj.dkhdl.BaseActivity;
import com.wdkj.dkhdl.BaseApplication;
import com.wdkj.dkhdl.MainActivity;
import com.wdkj.dkhdl.R;
import com.wdkj.dkhdl.bean.UserBean;
import com.wdkj.dkhdl.utils.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * 商户列表
 */
@ContentView(R.layout.activity_submit_success)
public class SubmitSuccessActivity extends BaseActivity{

    private Context context;

    @ViewInject(R.id.submit_success_layoutInfo)
    LinearLayout layoutInfo;
    @ViewInject(R.id.submit_success_busName)
    TextView tvMerName;

    private UserBean userBean = MainActivity.userBean;
    private String MerName;//商户名称

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = SubmitSuccessActivity.this;
        MerName = getIntent().getStringExtra("name");
        if(Utils.isNotEmpty(MerName)){
            layoutInfo.setVisibility(View.VISIBLE);
            tvMerName.setText(String.format(getResources().getString(R.string.busName),MerName));
        }else {
            layoutInfo.setVisibility(View.GONE);
        }

        setTitle("资料提交成功");
    }
}
