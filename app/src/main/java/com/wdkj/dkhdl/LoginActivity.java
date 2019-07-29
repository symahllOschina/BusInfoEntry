package com.wdkj.dkhdl;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.wdkj.dkhdl.bean.UserBean;
import com.wdkj.dkhdl.httputil.HttpURLConnectionUtil;
import com.wdkj.dkhdl.httputil.NetworkUtils;
import com.wdkj.dkhdl.utils.GsonUtils;
import com.wdkj.dkhdl.utils.MD5;
import com.wdkj.dkhdl.utils.MySerialize;
import com.wdkj.dkhdl.utils.NitConfig;
import com.wdkj.dkhdl.utils.SharedPreferencesUtil;
import com.wdkj.dkhdl.utils.ToastUtil;
import com.wdkj.dkhdl.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity implements OnClickListener{

    private static final String TAG = "LoginActivity";
    private Context context;

    private AutoCompleteTextView etAccount;
    private EditText etPasswd;
    private Button btLogin;


    String accountStr = "";
    String passwdStr = "";
    private SharedPreferencesUtil sharedPreferencesUtil;

    private ProgressDialog loginDialog;//登录提示框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = LoginActivity.this;
        sharedPreferencesUtil = new SharedPreferencesUtil(LoginActivity.this,"userInfo");
        mToolbar = null;
        initData();
        checkNetwork();

    }

    /**
     * 初始化应用信息
     */
    private void initData(){
        if(sharedPreferencesUtil.contain("account")){
            Log.e("登录保存的状态key：", "Key存在");
            accountStr = (String) sharedPreferencesUtil.getSharedPreference("account","");
            passwdStr = (String) sharedPreferencesUtil.getSharedPreference("passwd","");
            doLogin(accountStr,passwdStr);
        }else{
            Log.e("登录保存的状态key：", "Key不存在");
            setContentView(R.layout.activity_login);
            initView();
            initListener();
        }
    }

    /** 初始化View,控件 */
    private void initView(){
        etAccount = (AutoCompleteTextView) findViewById(R.id.login_activity_etAccount);
        etPasswd = (EditText) findViewById(R.id.login_activity_etPasswd);
        btLogin = (Button) findViewById(R.id.login_activity_btSubmit);

    }

    /** 注册监听  */
    private void initListener(){
        btLogin.setOnClickListener(this);
    }

    //尝试登录
    private void attemptLogin() {
        //记录焦点
        boolean cancel = false;
        View focusView = null;

        accountStr = etAccount.getText().toString();
        passwdStr = etPasswd.getText().toString();
        if(Utils.isEmpty(accountStr)){
            ToastUtil.showText(context,"用户名不能为空！",1);
            return;
        }
        if(Utils.isEmpty(passwdStr)){
            ToastUtil.showText(context,"密码不能为空！",1);
            return;
        }
        //都不为空的情况下判断用户名密码是否正确（格式是否正确，比如用户名为手机号时手机号是否为11位等）
        //这里直接提交服务器验证
        doLogin(accountStr,passwdStr);


        if (!TextUtils.isEmpty(passwdStr) && !isPasswordValid(passwdStr)) {
//            etPasswd.setError(getString(R.string.error_invalid_password));
            focusView = etPasswd;
            cancel = true;
        }
        if (TextUtils.isEmpty(accountStr)) {
//            etAccount.setError(getString(R.string.error_field_required));
            focusView = etAccount;
            cancel = true;
        } else if (!isEmailValid(accountStr)) {
//            etAccount.setError(getString(R.string.error_invalid_email));
            focusView = etAccount;
            cancel = true;
        }
        if (cancel) {

            focusView.requestFocus();
        } else {


        }
    }

    private boolean isEmailValid(String email) {

        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }


    /** 登录（提交到服务器验证）
     *  account和password参数
     *  登录账号1110007密码123456
     *  登录账号2810042密码123456
     */
    private void doLogin(final String account,String passwd){
        if(!NetworkUtils.isNetworkAvailable(this)){
            ToastUtil.showText(LoginActivity.this,"请检查网络是否连接...",1);
            return;
        }

        loginDialog = new ProgressDialog(this);// 新建了一个进度条
        loginDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条风格，风格为圆形，旋转的
        loginDialog.setCancelable(true);// 按返回键取消
        loginDialog = ProgressDialog.show(this, null, "正在登录中,请稍后.....");
        final String url = NitConfig.doLoginUrl;
        Log.e("doLogin请求地址：",url);
        //对参数密码值经过MD5加密再传值（加密方式：MD5.MD5Encode(密码+账号)）
//        final String accountStr = "1000145101";
//        final String passwdStr = "123456";
        final String MD5PasswdStr = MD5.MD5Encode(passwd+account);
        new Thread(){
            @Override
            public void run() {
                try {
                    // 拼装JSON数据，向服务端发起请求
                    JSONObject userJSON = new JSONObject();
                    userJSON.put("account",account);
                    userJSON.put("password",MD5PasswdStr);
                    String content = String.valueOf(userJSON);
                    Log.e("发起请求参数：", content);
                    String jsonStr = HttpURLConnectionUtil.doPos(url,content);

                    Log.e("返回字符串结果：", jsonStr);
                    int msg = 1;
                    String text = jsonStr;
                    sendMessage(msg,text);
                }catch (SocketTimeoutException e){

                }catch (IOException e){
                    e.printStackTrace();
                    sendMessage(NetworkUtils.REQUEST_IO_CODE,NetworkUtils.REQUEST_IO_TEXT);
                }catch (Exception e) {
                    e.printStackTrace();
                    sendMessage(NetworkUtils.REQUEST_CODE,NetworkUtils.REQUEST_TEXT);
                }
            }
        }.start();

    }



    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String errorJsonText = "";
            switch (msg.what){
                case 1:
                    String loginJson = (String) msg.obj;
                    loginResultJSON(loginJson);
                    if(loginDialog!=null){
                        loginDialog.dismiss();
                    }
                    break;
                case 201:
                    errorJsonText = (String) msg.obj;
                    ToastUtil.showText(context,errorJsonText,1);
                    if(loginDialog!=null){
                        loginDialog.dismiss();
                    }
                    break;
                case 202:
                    errorJsonText = (String) msg.obj;
                    ToastUtil.showText(context,errorJsonText,1);
                    if(loginDialog!=null){
                        loginDialog.dismiss();
                    }
                    break;
                case 301:
                    errorJsonText = (String) msg.obj;
                    ToastUtil.showText(context,errorJsonText,1);
                    if(loginDialog!=null){
                        loginDialog.dismiss();
                    }
                    break;
                case 400:
                    errorJsonText = (String) msg.obj;
                    ToastUtil.showText(context,errorJsonText,1);
                    if(loginDialog!=null){
                        loginDialog.dismiss();
                    }
                    break;
            }
        }
    };

    private void loginResultJSON(String json){
        //{"code":"000000","msg":"登陆成功","data":{"name":"业务员测","agent_id":"11","id":"11","role":"shop","account":"1110007"},
        // "timestamp":"1539939271572"}
        try {
            JSONObject job = new JSONObject(json);
            String code = job.getString("code");
            String msg = job.getString("msg");
            if(code.equals("000000")){
                String dataJson = job.getString("data");
                //保存用户名和密码
                sharedPreferencesUtil.put("account",accountStr);
                sharedPreferencesUtil.put("passwd",passwdStr);
                Gson gson = GsonUtils.getGson();
                UserBean userBean = gson.fromJson(dataJson,UserBean.class);
                try {
                    MySerialize.saveObject("user",this,MySerialize.serialize(userBean));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    sendMessage(NetworkUtils.REQUEST_IO_CODE,NetworkUtils.REQUEST_IO_TEXT);
                }
                intoMainActivity();
            }else{
                if(Utils.isNotEmpty(msg)){
                    ToastUtil.showText(context,msg,1);
                }else{
                    ToastUtil.showText(context,"服务异常！",1);
                }
                //清除本地账号信息：
                sharedPreferencesUtil.clear();
                Intent intent=new Intent();
                intent.setClass(this, WelComeActivity.class);
                startActivity(intent);
                BaseApplication.getInstance().exit();

            }
        } catch (JSONException e) {
            e.printStackTrace();
            snackError("登录异常！");
        }
    }




    private void sendMessage(int what,String text){
        Message msg = new Message();
        msg.what = what;
        msg.obj = text;
        handler.sendMessage(msg);
    }

    private void intoMainActivity(){
        Intent in = new Intent();
        in.setClass(LoginActivity.this,MainActivity.class);
        startActivity(in);
        //跳转动画效果
        overridePendingTransition(R.anim.in_from, R.anim.to_out);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_activity_btSubmit://提交、登录
//                intoMainActivity();
                attemptLogin();
                break;
        }
    }
}

