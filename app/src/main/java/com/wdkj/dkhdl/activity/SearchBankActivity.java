package com.wdkj.dkhdl.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wdkj.dkhdl.BaseActivity;
import com.wdkj.dkhdl.R;
import com.wdkj.dkhdl.adapter.SearchBankAdapter;
import com.wdkj.dkhdl.adapter.SpinnerBankAdapter;
import com.wdkj.dkhdl.bean.BackListData;
import com.wdkj.dkhdl.httputil.HttpURLConnectionUtil;
import com.wdkj.dkhdl.httputil.NetworkUtils;
import com.wdkj.dkhdl.utils.GsonUtils;
import com.wdkj.dkhdl.utils.NitConfig;
import com.wdkj.dkhdl.utils.ToastUtil;
import com.wdkj.dkhdl.utils.Utils;
import com.wdkj.dkhdl.view.SpinerPopWindow;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 模糊查询支行信息
 */
@ContentView(R.layout.activity_choice_bank)
public class SearchBankActivity extends BaseActivity implements OnClickListener,AdapterView.OnItemClickListener{

    private Context context;
    public static final int RESULT_CODE = 1;

    @ViewInject(R.id.search_header_imgBack)
    ImageView imgBack;
    @ViewInject(R.id.search_header_tvSearch)
    TextView tvSearch;
    @ViewInject(R.id.search_header_etSearch)
    EditText etSearch;
    @ViewInject(R.id.choice_bank_listview)
    ListView mListView;

    @ViewInject(R.id.emptyView)
    View emptyView;


    private int id;//总行Id
    private int city_id;//城市ID

    private List<BackListData> lsBranch = new ArrayList<>();
    BackListData branch;
    SearchBankAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = SearchBankActivity.this;
        id = getIntent().getIntExtra("id",0);
        city_id = getIntent().getIntExtra("city_id",0);

        initListener();
        mAdapter = new SearchBankAdapter(context,lsBranch);
        mListView.setAdapter(mAdapter);

        String bankName = etSearch.getText().toString().trim();
        getBranchList(bankName);
    }

    private void initListener(){
        imgBack.setOnClickListener(this);
        tvSearch.setOnClickListener(this);

        mListView.setOnItemClickListener(this);
    }

    /** 获取支行 */
    private void getBranchList(final String bankName){
        showWaitDialog();
        final String url = NitConfig.getBranchList;
        new Thread(){
            @Override
            public void run() {
                try {
                    // 拼装JSON数据，向服务端发起请求
                    JSONObject userJSON = new JSONObject();
                    userJSON.put("id", String.valueOf(id));
                    userJSON.put("bank_city_code", String.valueOf(city_id));
                    userJSON.put("bank_name", bankName);
                    String content = String.valueOf(userJSON);
                    Log.e("查询支行请求参数：", content);
                    String jsonStr = HttpURLConnectionUtil.doPos(url,content);
                    Log.e("查询支行返回字符串结果：", jsonStr);
                    int msg = 1;
                    String text = jsonStr;
                    sendMessage(msg,text);

                } catch (JSONException e) {
                    e.printStackTrace();
                    sendMessage(NetworkUtils.REQUEST_JSON_CODE,NetworkUtils.REQUEST_JSON_TEXT);
                }catch (IOException e){
                    e.printStackTrace();
                    sendMessage(NetworkUtils.REQUEST_IO_CODE,NetworkUtils.REQUEST_IO_TEXT);
                } catch (Exception e) {
                    e.printStackTrace();
                    sendMessage(NetworkUtils.REQUEST_CODE,NetworkUtils.REQUEST_TEXT);
                }
            }
        }.start();
    }

    private void sendMessage(int what,String text){
        Message msg = new Message();
        msg.what = what;
        msg.obj = text;
        handler.sendMessage(msg);
    }


    private Handler handler = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            String errorJsonText = "";
            String url = "";
            switch (msg.what){
                case 1:
                    String branchJson = (String) msg.obj;
                    branchJson(branchJson);
                    hideWaitDialog();
                    break;
                case 201:
                    errorJsonText = (String) msg.obj;
                    ToastUtil.showText(context,errorJsonText,1);
                    hideWaitDialog();
                    break;
                case 202:
                    errorJsonText = (String) msg.obj;
                    ToastUtil.showText(context,errorJsonText,1);
                    hideWaitDialog();
                    break;
                case 301:
                    errorJsonText = (String) msg.obj;
                    ToastUtil.showText(context,errorJsonText,1);
                    hideWaitDialog();
                    break;
                case 400:
                    errorJsonText = (String) msg.obj;
                    ToastUtil.showText(context,errorJsonText,1);
                    hideWaitDialog();
                    break;
            }
        }
    };

    private void branchJson(String json){
        try {
            JSONObject job = new JSONObject(json);
            int status = job.getInt("status");
            String message = job.getString("message");
            if(status == 200){
                String dataJson = job.getString("data");
                JSONObject obj = new JSONObject(dataJson);
                String bankList = obj.getString("bankList");
                List<BackListData> list = new ArrayList<>();
                list.clear();
                Gson gjson1  =  GsonUtils.getGson();
                list = gjson1.fromJson(bankList, new TypeToken<List<BackListData>>() {  }.getType());


                Log.e("银行支行数据",list.size()+"");
                lsBranch.clear();
                if (list == null || list.isEmpty()) {
                    mListView.setEmptyView(emptyView);
                } else {

                    lsBranch.addAll(list);
                }

//                mAdapter = new SearchBankAdapter(this,lsBranch);
//
//                mListView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

            }else{
                ToastUtil.showText(context,message,1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_header_imgBack:
                branch = null;
                Intent in = new Intent();
                in.putExtra("branch",branch);
                setResult(RESULT_CODE,in);
                finish();
                break;
            case R.id.search_header_tvSearch:
                if(Utils.isFastClick()){
                    return;
                }
                String bankName = etSearch.getText().toString().trim();
                getBranchList(bankName);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        branch = lsBranch.get(position);
        Intent in = new Intent();
        in.putExtra("branch",branch);
        setResult(RESULT_CODE,in);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                branch = null;
                Intent in = new Intent();
                in.putExtra("branch",branch);
                setResult(RESULT_CODE,in);
                finish();

                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
