package com.wdkj.dkhdl.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wdkj.dkhdl.BaseFragment;
import com.wdkj.dkhdl.R;
import com.wdkj.dkhdl.activity.AccountInfoActivity;
import com.wdkj.dkhdl.activity.AptitudeRateActivity;
import com.wdkj.dkhdl.activity.BasicInfoActivity;
import com.wdkj.dkhdl.activity.BusListActivity;
import com.wdkj.dkhdl.utils.ToastUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.fragment_home)
public class MainHomeFragment extends BaseFragment implements View.OnClickListener{

    @ViewInject(R.id.home_lyoutBusAdd)
    LinearLayout layoutBusAdd;
    @ViewInject(R.id.home_lyoutBusList)
    LinearLayout layoutBusList;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListener();
    }



    private void initListener(){
        layoutBusAdd.setOnClickListener(this);
        layoutBusList.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.home_lyoutBusAdd:
                intent = new Intent();
                intent.setClass(getActivity(), BasicInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.home_lyoutBusList:
                intent = new Intent();
                intent.setClass(getActivity(), BusListActivity.class);
                startActivity(intent);
                break;
        }
    }
}
