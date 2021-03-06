package com.wdkj.dkhdl;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class BaseApplication extends Application{

    private static final String TAG = "N2MApplication";
    public static BaseApplication myApp;
    List<Activity> activityList = new ArrayList<>();

    //为了实现每次使用该类时不创建新的对象而创建的静态对象
    private static BaseApplication instance;


    //实例化
    public synchronized static BaseApplication getInstance(){
        if(instance==null){
            instance=new BaseApplication();
        }
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;
        x.Ext.init(this);
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Log.d(TAG, "onActivityCreated:" + activity.hashCode());
//                if (activity instanceof SplashActivity) {
//                    return;
//                }
                activityList.add(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.d(TAG, "onActivityDestroyed:" + activity.hashCode());
                activityList.remove(activity);
            }
        });
    }

    // 保存打开的Actviity到集合中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void exit() {
        for (Activity activity: activityList) {
            activity.finish();
        }
        //结束应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    //关闭除MianActivity外list内的其余的activity
    public void noMain_exit() {
        for (int i = 0; i < activityList.size(); i++) {
            Activity activity = activityList.get(i);
            //当前Activity的名称为：com.mobile.android.yiloneshop.activity.MainLayoutActivity@f59a736
            Log.e("当前Activity的名称为：", activity+"");
            String activityStr = String.valueOf(activity);
            String containStr = "MainActivity";
            if( !activityStr.contains(containStr) )
            {
                if (activity != null)
                {
                    activity.finish();
                }
            }
        }
    }

    /**
     * 如果要像微信一样，所有字体都不允许随系统调节而发生大小变化
     * 在工程的Application或BaseActivity中添加下面的代码
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }




}
