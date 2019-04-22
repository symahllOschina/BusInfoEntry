package com.wdkj.dkhdl;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * 欢迎界面
 */
public class WelComeActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mToolbar = null;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelComeActivity.this, LoginActivity.class));
                finish();
            }
        }, 1000);
    }
}
