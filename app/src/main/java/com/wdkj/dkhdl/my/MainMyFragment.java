package com.wdkj.dkhdl.my;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wdkj.dkhdl.BaseApplication;
import com.wdkj.dkhdl.BaseFragment;
import com.wdkj.dkhdl.LoginActivity;
import com.wdkj.dkhdl.MainActivity;
import com.wdkj.dkhdl.R;
import com.wdkj.dkhdl.activity.AboutUsActivity;
import com.wdkj.dkhdl.bean.UserBean;
import com.wdkj.dkhdl.utils.MySerialize;
import com.wdkj.dkhdl.utils.SharedPreferencesUtil;
import com.wdkj.dkhdl.utils.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import de.hdodenhof.circleimageview.CircleImageView;

@SuppressLint("ValidFragment")
@ContentView(R.layout.fragment_my)
public class MainMyFragment extends BaseFragment implements View.OnClickListener{

    @ViewInject(R.id.main_fragment_my_circleImageView)
    CircleImageView headImg;
    @ViewInject(R.id.main_fragment_my_signOutLayout)
    RelativeLayout layoutSignOut;
    @ViewInject(R.id.main_fragment_my_aboutUsLayout)
    RelativeLayout layoutAboutUs;
    @ViewInject(R.id.main_fragment_my_myName)
    TextView tvName;

    private SharedPreferencesUtil sharedPreferencesUtil;

    private UserBean userBean;

    @SuppressLint("ValidFragment")
    public MainMyFragment(UserBean userBean) {
        this.userBean = userBean;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    private void initView(){

        if(userBean!=null){
            if(Utils.isNotEmpty(userBean.getName())){
                tvName.setText(userBean.getName());
            }
        }
    }

    private void initListener(){
        headImg.setOnClickListener(this);
        layoutSignOut.setOnClickListener(this);
        layoutAboutUs.setOnClickListener(this);
    }


    /**
     *  显示确认提示框
     **/
    private void showConfirmDialog(){
        View view = LayoutInflater.from(activity).inflate(R.layout.confirm_hint_dialog, null);
        TextView btok = (TextView) view.findViewById(R.id.confirm_hint_dialog_tvOk);
        TextView btCancel = (TextView) view.findViewById(R.id.confirm_hint_dialog_tvCancel);
        final Dialog myDialog = new Dialog(activity,R.style.dialog);
        Window dialogWindow = myDialog.getWindow();
        WindowManager.LayoutParams params = myDialog.getWindow().getAttributes(); // 获取对话框当前的参数值
        dialogWindow.setAttributes(params);
        myDialog.setContentView(view);
        btok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //清除保存的用户信息
                sharedPreferencesUtil = new SharedPreferencesUtil(activity,"userInfo");
                sharedPreferencesUtil.clear();


                Intent intent=new Intent();
                intent.setClass(getContext(), LoginActivity.class);
                startActivity(intent);
                //关闭应用
                BaseApplication.getInstance().exit();
                myDialog.dismiss();

            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_fragment_my_circleImageView:

                break;
            case R.id.main_fragment_my_signOutLayout:
                showConfirmDialog();
                break;
            case R.id.main_fragment_my_aboutUsLayout:
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
                break;
        }
    }
}
