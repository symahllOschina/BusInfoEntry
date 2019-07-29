package com.wdkj.dkhdl.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.wdkj.dkhdl.BaseActivity;
import com.wdkj.dkhdl.BaseApplication;
import com.wdkj.dkhdl.MainActivity;
import com.wdkj.dkhdl.R;
import com.wdkj.dkhdl.bean.BusDetailData;
import com.wdkj.dkhdl.bean.UserBean;
import com.wdkj.dkhdl.httputil.HttpURLConnectionUtil;
import com.wdkj.dkhdl.httputil.NetworkUtils;
import com.wdkj.dkhdl.photo.util.FileUtilcll;
import com.wdkj.dkhdl.photo.util.PhotoModule;
import com.wdkj.dkhdl.utils.DecimalUtil;
import com.wdkj.dkhdl.utils.FileUtils;
import com.wdkj.dkhdl.utils.GsonUtils;
import com.wdkj.dkhdl.utils.NitConfig;
import com.wdkj.dkhdl.utils.ToastUtil;
import com.wdkj.dkhdl.utils.UploadUtil;
import com.wdkj.dkhdl.utils.Utils;
import com.wdkj.dkhdl.view.PhotoPopupWindow;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.KeyValue;
import org.xutils.common.util.LogUtil;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.http.body.MultipartBody;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资质费率信息
 * https://blog.csdn.net/guolin_blog/article/details/78357251
 */
@ContentView(R.layout.activity_rate_info)
public class AptitudeRateActivity extends BaseActivity implements View.OnClickListener,PhotoPopupWindow.OnSelectClickListener{

    @ViewInject(R.id.info_failure_hintText)
    TextView tvErrorMsg;
    @ViewInject(R.id.parent_myLayout)
    LinearLayout myLayout;

    @ViewInject(R.id.rate_info_wxRateReduce)
    ImageView wxRateReduce;
    @ViewInject(R.id.rate_info_wxRateAdd)
    ImageView wxRateAdd;
    @ViewInject(R.id.rate_info_wxRate)
    EditText etWxRate;
    @ViewInject(R.id.rate_info_aliRateReduce)
    ImageView aliRateReduce;
    @ViewInject(R.id.rate_info_aliRateAdd)
    ImageView aliRateAdd;
    @ViewInject(R.id.rate_info_aliRate)
    EditText etAliRate;
    @ViewInject(R.id.rate_info_yzfRateReduce)
    ImageView yzfRateReduce;
    @ViewInject(R.id.rate_info_yzfRateAdd)
    ImageView yzfRateAdd;
    @ViewInject(R.id.rate_info_yzfRate)
    EditText etYzfRate;
    @ViewInject(R.id.rate_info_jjkRateReduce)
    ImageView jjkRateReduce;
    @ViewInject(R.id.rate_info_jjkRateAdd)
    ImageView jjkRateAdd;
    @ViewInject(R.id.rate_info_jjkRate)
    EditText etJjkRate;
    @ViewInject(R.id.rate_info_djkRateReduce)
    ImageView djkRateReduce;
    @ViewInject(R.id.rate_info_djkRateAdd)
    ImageView djkRateAdd;
    @ViewInject(R.id.rate_info_djkRate)
    EditText etDjkRate;
    @ViewInject(R.id.rate_info_ylRateReduce)
    ImageView ylRateReduce;
    @ViewInject(R.id.rate_info_ylRateAdd)
    ImageView ylRateAdd;
    @ViewInject(R.id.rate_info_ylRate)
    EditText etYlRate;

    @ViewInject(R.id.rate_info_bankReserveReduce)
    ImageView bankReserveReduce;
    @ViewInject(R.id.rate_info_bankReserveAdd)
    ImageView bankReserveAdd;
    @ViewInject(R.id.rate_info_bankReserve)
    EditText etBankReserve;

    @ViewInject(R.id.rate_info_imgLegalPersonLayout)
    LinearLayout imgLegalPersonLayout;//法人身份证
    @ViewInject(R.id.rate_info_imgBankCardLayout)
    LinearLayout imgBankCardLayout;//银行卡正反面
    @ViewInject(R.id.rate_info_imgOpeningPermitLayout)
    LinearLayout imgOpeningPermitLayout;//开户许可证
    @ViewInject(R.id.rate_info_imgMerRelationLayout)
    LinearLayout imgMerRelationLayout;//商户关系证明
    @ViewInject(R.id.rate_info_imgSettPersonLayout)
    LinearLayout imgSettPersonLayout;//结算人身份证
    @ViewInject(R.id.rate_info_imgJGDMZandSWDJZLayout)
    LinearLayout imgJGDMZandSWDJZLayout;//机构代码证和税务登记证



    @ViewInject(R.id.rate_info_imgLicense)
    ImageView imgLicense;//营业执照
    @ViewInject(R.id.rate_info_imgLegalPersonJust)
    ImageView imgLegalPersonJust;//法人正面
    @ViewInject(R.id.rate_info_imgLegalPersonBack)
    ImageView imgLegalPersonBack;
    @ViewInject(R.id.rate_info_imgBankCardJust)
    ImageView imgBankCardJust;//银行卡正面
    @ViewInject(R.id.rate_info_imgBankCardBack)
    ImageView imgBankCardBack;
    @ViewInject(R.id.rate_info_imgMerDoorhead)
    ImageView imgMerDoorhead;//商户门头照
    @ViewInject(R.id.rate_info_imgMerReception)
    ImageView imgMerReception;//门店前台照
    @ViewInject(R.id.rate_info_imgOpeningPermit)
    ImageView imgOpeningPermit;//开户许可证
    @ViewInject(R.id.rate_info_imgMerRelation)
    ImageView imgMerRelation;//商户关系证明照
    @ViewInject(R.id.rate_info_imgSettPersonJust)
    ImageView imgSettPersonJust;//结算人身份证正面
    @ViewInject(R.id.rate_info_imgSettPersonBack)
    ImageView imgSettPersonBack;
    @ViewInject(R.id.rate_info_imgMerZFDRelation)
    ImageView imgMerZFDRelation;//总分店关系照
    @ViewInject(R.id.rate_info_imgMerXY)
    ImageView imgMerXY;//商户增值协议
    @ViewInject(R.id.rate_info_imgJGDMZ)
    ImageView imgJGDMZ;//机构代码证
    @ViewInject(R.id.rate_info_imgSWDJZ)
    ImageView imgSWDJZ;//税务登记证
    @ViewInject(R.id.account_rate_btSubmit)
    Button btSubmit;


    private Context context;
    private UserBean userBean = MainActivity.userBean;
    private String MerName;//商户名称
    private int id = 350;//商户id
    private String netType = "GT";//入网类型：企业；个体，小微
    private String accountType = "2";//结算账户类型：对公 = 1，对私 = 2；
    private String licenseType = "SZHY";//入网证件类型：三证合一；营业执照
    private String isLegalPersonAccount = "1";//是否法人入账，法人入账 = 1，非法人入账 = 2；

    /** 费率初始默认值 */
    private double wxRate = 6.0;//最低3.0，最高50
    private double aliRate = 6.0;//最低3.0，最高50
    private double yzfRate = 3.0;//最低3.0，最高50
    private double jjkRate = 5.0;//最低4.2，最高500
    private double djkRate = 6.0;//最低5.4，最高500
    private double ylRate = 3.8;//最低3.8，最高6.0
    private int bankReserve = 18;//最低18，最高25

    private static final double wxMinRate = 3.0;
    private static final double aliMinRate = 3.0;
    private static final double yzfMinRate = 3.0;
    private static final double jjkMinRate = 4.2;
    private static final double djkMinRate = 5.4;
    private static final double ylMinRate = 3.8;
    private static final int bankMinReserve = 18;

    private static final double wxMaxRate = 50;
    private static final double aliMaxRate = 50;
    private static final double yzfMaxRate = 50;
    private static final double jjkMaxRate = 500;
    private static final double djkMaxRate = 500;
    private static final double ylMaxRate = 6.0;
    private static final int bankMaxReserve = 25;

    private static final double CHANGE = 0.1;//费率变化起伏值
    private static final int RESERVE = 1;//封顶值变化起伏值

    public static final int REQUEST_PERMISSIONS = 0x101;//动态权限注册请求码
    private float alpha = 1;
    private PhotoModule mPhotoModule;

    private String selectImg = "0";
    /** 照片上传成功后服务器路径 */
    //缩略图地址
    private String imgLicensePath = null;
    private String imgLegalPersonJustPath = null;
    private String imgLegalPersonBackPath = null;
    private String imgBankCardJustPath = null;
    private String imgBankCardBackPath = null;
    private String imgMerDoorheadPath = null;
    private String imgMerReceptionPath = null;
    private String imgOpeningPermitPath = null;
    private String imgMerRelationPath = null;
    private String imgSettPersonJustPath = null;
    private String imgSettPersonBackPath = null;
    private String imgMerZFDRelationPath = "";
    private String imgMerXYPath = "";
    private String imgJGDMZPath = null;
    private String imgSWDJZPath = null;
    //原图地址
    private String yt_imgLicensePath = null;
    private String yt_imgLegalPersonJustPath = null;
    private String yt_imgLegalPersonBackPath = null;
    private String yt_imgBankCardJustPath = null;
    private String yt_imgBankCardBackPath = null;
    private String yt_imgMerDoorheadPath = null;
    private String yt_imgMerReceptionPath = null;
    private String yt_imgOpeningPermitPath = null;
    private String yt_imgMerRelationPath = null;
    private String yt_imgSettPersonJustPath = null;
    private String yt_imgSettPersonBackPath = null;
    private String yt_imgMerZFDRelationPath = "";
    private String yt_imgMerXYPath = "";
    private String yt_imgJGDMZPath = null;
    private String yt_imgSWDJZPath = null;

    BusDetailData bus;//商户信息对象

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = AptitudeRateActivity.this;
        MerName = getIntent().getStringExtra("name");
        id = getIntent().getIntExtra("id",0);
        netType = getIntent().getStringExtra("netType");
        accountType = getIntent().getStringExtra("accountType");
        licenseType = getIntent().getStringExtra("licenseType");
        isLegalPersonAccount = getIntent().getStringExtra("isLegalPersonAccount");
        Log.e(TAG,id+"");
        Log.e(TAG,netType);
        Log.e(TAG,accountType);
        Log.e(TAG,licenseType);
        Log.e(TAG,isLegalPersonAccount);

        setTitle("商户资质费率");
        initPermission();
        initView();

        initListener();

        //获取商户基础信息
        if(id!=0){
            getBusInfo();
        }
    }


    private void initView(){

        //照片工具处理类实例化
        mPhotoModule=new PhotoModule(this);

        updateView();
    }

    private void initListener(){
        wxRateReduce.setOnClickListener(this);
        wxRateAdd.setOnClickListener(this);
        aliRateReduce.setOnClickListener(this);
        aliRateAdd.setOnClickListener(this);
        yzfRateReduce.setOnClickListener(this);
        yzfRateAdd.setOnClickListener(this);
        jjkRateReduce.setOnClickListener(this);
        jjkRateAdd.setOnClickListener(this);
        djkRateReduce.setOnClickListener(this);
        djkRateAdd.setOnClickListener(this);
        ylRateReduce.setOnClickListener(this);
        ylRateAdd.setOnClickListener(this);

        bankReserveReduce.setOnClickListener(this);
        bankReserveAdd.setOnClickListener(this);

        imgLicense.setOnClickListener(this);
        imgLegalPersonJust.setOnClickListener(this);
        imgLegalPersonBack.setOnClickListener(this);
        imgBankCardJust.setOnClickListener(this);
        imgBankCardBack.setOnClickListener(this);
        imgMerDoorhead.setOnClickListener(this);
        imgMerReception.setOnClickListener(this);
        imgOpeningPermit.setOnClickListener(this);
        imgOpeningPermit.setOnClickListener(this);
        imgMerRelation.setOnClickListener(this);
        imgSettPersonJust.setOnClickListener(this);
        imgSettPersonBack.setOnClickListener(this);
        imgMerZFDRelation.setOnClickListener(this);
        imgMerXY.setOnClickListener(this);
        imgJGDMZ.setOnClickListener(this);
        imgSWDJZ.setOnClickListener(this);
        btSubmit.setOnClickListener(this);
    }

    /**
     * 6.0动态权限
     */
    private void initPermission(){
        String[] permissions = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
        //检查权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
            // 之前拒绝了权限，但没有点击 不再询问 这个时候让它继续请求权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
                //Toast.makeText(this, "用户曾拒绝打开相机权限", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS);
            } else {
                //注册相机权限
                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS);
            }
        }
    }

    private void updateView(){
        //初始化
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.option_icon);

        //merchant_status状态为2即审核驳回时返回驳回原因
        if(bus!=null){
            if(bus.getMerchant_status().equals("2")){
                if(Utils.isNotEmpty(bus.getError_msg())){
                    tvErrorMsg.setVisibility(View.VISIBLE);
                    tvErrorMsg.setText(String.format(getResources().getString(R.string.failure_hints),bus.getError_msg()));
                }
            }
        }
        etWxRate.setText(DecimalUtil.doubletoString(wxRate,1));
        etAliRate.setText(DecimalUtil.doubletoString(aliRate,1));
        etYzfRate.setText(DecimalUtil.doubletoString(yzfRate,1));
        etJjkRate.setText(DecimalUtil.doubletoString(jjkRate,1));
        etDjkRate.setText(DecimalUtil.doubletoString(djkRate,1));
        etYlRate.setText(DecimalUtil.doubletoString(ylRate,1));
        etBankReserve.setText(String.valueOf(bankReserve));
        if(bus!=null){
            //微信费率
            String wxRateStr = bus.getWx_rate();
            if(Utils.isNotEmpty(wxRateStr)){
                etWxRate.setText(wxRateStr);
                wxRate = DecimalUtil.StringToDouble(wxRateStr,1);
            }
            //支付宝费率
            String aliRateStr = bus.getAli_rate();
            if(Utils.isNotEmpty(aliRateStr)){
                etAliRate.setText(aliRateStr);
                aliRate = DecimalUtil.StringToDouble(aliRateStr,1);
            }
            //翼支付费率
            String bestRateStr = bus.getBest_rate();
            if(Utils.isNotEmpty(bestRateStr)){
                etYzfRate.setText(bestRateStr);
                yzfRate = DecimalUtil.StringToDouble(bestRateStr,1);
            }
            //贷记卡费率
            String crebitRateStr = bus.getCrebit_rate();
            if(Utils.isNotEmpty(crebitRateStr)){
                etDjkRate.setText(crebitRateStr);
                djkRate = DecimalUtil.StringToDouble(crebitRateStr,1);
            }
            //借记卡费率
            if(Utils.isNotEmpty(bus.getDebit_rate())){
                etJjkRate.setText(bus.getDebit_rate());
            }
            //银联费率
            String unionpayRateStr = bus.getUnionpay_rate();
            if(Utils.isNotEmpty(unionpayRateStr)){
                etYlRate.setText(unionpayRateStr);
                ylRate = DecimalUtil.StringToDouble(unionpayRateStr,1);
            }
            //借记卡封顶值
            String reserve1Str = bus.getReserve1();
            if(Utils.isNotEmpty(reserve1Str)){
                etBankReserve.setText(reserve1Str);
                bankReserve = Integer.parseInt(reserve1Str);
            }


            //营业执照
            String thum_img_business_license = bus.getThum_img_business_license();
            String img_business_license = bus.getImg_business_license();
            if(Utils.isNotEmpty(thum_img_business_license)){
                imgLicensePath = thum_img_business_license;
                yt_imgLicensePath = img_business_license;
                Glide.with(this)
                        .load(thum_img_business_license)
                        .apply(options)
                        .into(imgLicense);
            }
            //法人正面
            String thum_img_idcard_a = bus.getThum_img_idcard_a();
            String img_idcard_a = bus.getImg_idcard_a();
            if(Utils.isNotEmpty(thum_img_idcard_a)){
                imgLegalPersonJustPath = thum_img_idcard_a;
                yt_imgLegalPersonJustPath = img_idcard_a;
                Glide.with(this)
                        .load(thum_img_idcard_a)
                        .apply(options)
                        .into(imgLegalPersonJust);
            }
            //法人反面
            String thum_img_idcard_b = bus.getThum_img_idcard_b();
            String img_idcard_b = bus.getImg_idcard_b();
            if(Utils.isNotEmpty(thum_img_idcard_b)){
                imgLegalPersonBackPath = thum_img_idcard_b;
                yt_imgLegalPersonBackPath = img_idcard_b;
                Glide.with(this)
                        .load(thum_img_idcard_b)
                        .apply(options)
                        .into(imgLegalPersonBack);
            }
            //银行卡正面
            String thum_img_bankcard_a = bus.getThum_img_bankcard_a();
            String img_bankcard_a = bus.getImg_bankcard_a();
            if(Utils.isNotEmpty(thum_img_bankcard_a)){
                imgBankCardJustPath = thum_img_bankcard_a;
                yt_imgBankCardJustPath = img_bankcard_a;
                Glide.with(this)
                        .load(thum_img_bankcard_a)
                        .apply(options)
                        .into(imgBankCardJust);
            }
            //银行卡反面
            String thum_img_bankcard_b = bus.getThum_img_bankcard_b();
            String img_bankcard_b = bus.getImg_bankcard_b();
            if(Utils.isNotEmpty(thum_img_bankcard_b)){
                imgBankCardBackPath = thum_img_bankcard_b;
                yt_imgBankCardBackPath = img_bankcard_b;
                Glide.with(this)
                        .load(thum_img_bankcard_b)
                        .apply(options)
                        .into(imgBankCardBack);
            }
            //商户门头照
            String thum_img_logo = bus.getThum_img_logo();
            String img_logo = bus.getImg_logo();
            if(Utils.isNotEmpty(thum_img_logo)){
                imgMerDoorheadPath = thum_img_logo;
                yt_imgMerDoorheadPath = img_logo;
                Glide.with(this)
                        .load(thum_img_logo)
                        .apply(options)
                        .into(imgMerDoorhead);
            }
            //内部前台照
            String thum_img_indoor = bus.getThum_img_indoor();
            String img_indoor = bus.getImg_indoor();
            if(Utils.isNotEmpty(thum_img_indoor)){
                imgMerReceptionPath = thum_img_indoor;
                yt_imgMerReceptionPath = img_indoor;
                Glide.with(this)
                        .load(thum_img_indoor)
                        .apply(options)
                        .into(imgMerReception);
            }
            //开户许可证
            String thum_img_open_permits = bus.getThum_img_open_permits();
            String img_open_permits = bus.getImg_open_permits();
            if(Utils.isNotEmpty(thum_img_open_permits)){
                imgOpeningPermitPath = thum_img_open_permits;
                yt_imgOpeningPermitPath = img_open_permits;
                Glide.with(this)
                        .load(thum_img_open_permits)
                        .apply(options)
                        .into(imgOpeningPermit);
            }
            //结算人正面
            String thum_img_private_idcard_a = bus.getThum_img_private_idcard_a();
            String img_private_idcard_a = bus.getImg_private_idcard_a();
            if(Utils.isNotEmpty(thum_img_private_idcard_a)){
                imgSettPersonJustPath = thum_img_private_idcard_a;
                yt_imgSettPersonJustPath = img_private_idcard_a;
                Glide.with(this)
                        .load(thum_img_private_idcard_a)
                        .apply(options)
                        .into(imgSettPersonJust);
            }
            //结算人反面
            String thum_img_private_idcard_b = bus.getThum_img_private_idcard_b();
            String img_private_idcard_b = bus.getImg_private_idcard_b();
            if(Utils.isNotEmpty(thum_img_private_idcard_b)){
                imgSettPersonBackPath = thum_img_private_idcard_b;
                yt_imgSettPersonBackPath = img_private_idcard_b;
                Glide.with(this)
                        .load(thum_img_private_idcard_b)
                        .apply(options)
                        .into(imgSettPersonBack);
            }
            //商户关系证明
            String thum_img_relation = bus.getThum_img_relation();
            String img_relation = bus.getImg_relation();
            if(Utils.isNotEmpty(thum_img_relation)){
                imgMerRelationPath = thum_img_relation;
                yt_imgMerRelationPath = img_relation;
                Glide.with(this)
                        .load(thum_img_relation)
                        .apply(options)
                        .into(imgMerRelation);
            }
            //商户总分店关系证明
            String thum_img_mer_relation = bus.getThum_img_mer_relation();
            String img_mer_relation = bus.getImg_mer_relation();
            if(Utils.isNotEmpty(thum_img_mer_relation)){
                imgMerZFDRelationPath = thum_img_mer_relation;
                yt_imgMerZFDRelationPath = img_mer_relation;
                Glide.with(this)
                        .load(thum_img_mer_relation)
                        .apply(options)
                        .into(imgMerZFDRelation);
            }
            //商户增值协议
            String thum_img_mer_increment = bus.getThum_img_mer_increment();
            String img_mer_increment = bus.getImg_mer_increment();
            if(Utils.isNotEmpty(thum_img_mer_increment)){
                imgMerXYPath = thum_img_mer_increment;
                yt_imgMerXYPath = img_mer_increment;
                Glide.with(this)
                        .load(thum_img_mer_increment)
                        .apply(options)
                        .into(imgMerXY);
            }
            //税务登记证
            String thum_img_tax_reg = bus.getThum_img_tax_reg();
            String img_tax_reg = bus.getImg_tax_reg();
            if(Utils.isNotEmpty(thum_img_tax_reg)){
                imgSWDJZPath = thum_img_tax_reg;
                yt_imgSWDJZPath = img_tax_reg;
                Glide.with(this)
                        .load(thum_img_tax_reg)
                        .apply(options)
                        .into(imgSWDJZ);
            }
            //机构代码证
            String thum_img_org_code = bus.getThum_img_org_code();
            String img_org_code = bus.getImg_org_code();
            if(Utils.isNotEmpty(thum_img_org_code)){
                imgJGDMZPath = thum_img_org_code;
                yt_imgJGDMZPath = img_org_code;
                Glide.with(this)
                        .load(thum_img_org_code)
                        .apply(options)
                        .into(imgJGDMZ);
            }
        }



        //入网类型：企业；个体，小微
        if(netType.equals("QY")){
            //结算账户类型：对公 = 1，对私 = 2；
            if(accountType.equals("1")){
                //入网证件类型：三证合一；营业执照
                if(licenseType.equals("SZHY")){
                    imgBankCardLayout.setVisibility(View.GONE);
                    imgMerRelationLayout.setVisibility(View.GONE);
                    imgSettPersonLayout.setVisibility(View.GONE);
                    imgJGDMZandSWDJZLayout.setVisibility(View.GONE);
                }else{
                    imgBankCardLayout.setVisibility(View.GONE);
                    imgMerRelationLayout.setVisibility(View.GONE);
                    imgSettPersonLayout.setVisibility(View.GONE);
                }
            }else{
                //对私
                //入网证件类型：三证合一；营业执照
                if(licenseType.equals("SZHY")){
                    //是否法人入账，法人入账 = 1，非法人入账 = 2；
                    if(isLegalPersonAccount.equals("1")){
                        imgLegalPersonLayout.setVisibility(View.GONE);
                        imgMerRelationLayout.setVisibility(View.GONE);
                        imgJGDMZandSWDJZLayout.setVisibility(View.GONE);
                    }else{
                        imgJGDMZandSWDJZLayout.setVisibility(View.GONE);
                    }


                }else{
                    //是否法人入账，法人入账 = 1，非法人入账 = 2；
                    if(isLegalPersonAccount.equals("1")){
                        imgLegalPersonLayout.setVisibility(View.GONE);
                        imgMerRelationLayout.setVisibility(View.GONE);
                    }
                }
            }
        }
        //个体
        else if(netType.equals("GT")){
            //结算账户类型：对公 = 1，对私 = 2；
            if(accountType.equals("1")){
                //入网证件类型：三证合一；营业执照
                if(licenseType.equals("SZHY")){
                    imgBankCardLayout.setVisibility(View.GONE);
                    imgMerRelationLayout.setVisibility(View.GONE);
                    imgSettPersonLayout.setVisibility(View.GONE);
                    imgJGDMZandSWDJZLayout.setVisibility(View.GONE);
                }else{
                    imgBankCardLayout.setVisibility(View.GONE);
                    imgMerRelationLayout.setVisibility(View.GONE);
                    imgSettPersonLayout.setVisibility(View.GONE);
                }

            }else{
                //对私
                //入网证件类型：三证合一；营业执照
                if(licenseType.equals("SZHY")){
                    //是否法人入账，法人入账 = 1，非法人入账 = 2；
                    if(isLegalPersonAccount.equals("1")){
                        imgLegalPersonLayout.setVisibility(View.GONE);
                        imgOpeningPermitLayout.setVisibility(View.GONE);
                        imgMerRelationLayout.setVisibility(View.GONE);
                        imgJGDMZandSWDJZLayout.setVisibility(View.GONE);
                    }else{
                        imgOpeningPermitLayout.setVisibility(View.GONE);
                        imgJGDMZandSWDJZLayout.setVisibility(View.GONE);

                    }
                }else{
                    //是否法人入账，法人入账 = 1，非法人入账 = 2；
                    if(isLegalPersonAccount.equals("1")){
                        imgLegalPersonLayout.setVisibility(View.GONE);
                        imgOpeningPermitLayout.setVisibility(View.GONE);
                        imgMerRelationLayout.setVisibility(View.GONE);
                    }else{
                        imgLegalPersonLayout.setVisibility(View.GONE);
                        imgOpeningPermitLayout.setVisibility(View.GONE);
                        imgJGDMZandSWDJZLayout.setVisibility(View.GONE);
                    }
                }
            }
        }

    }

    /**
     * 提交本地验证
     */
    private void subTextVerification(){
        if(Utils.isEmpty(imgLicensePath)){
            ToastUtil.showText(context,"请上传营业执照照片!",1);
            return;
        }
        //入网类型：企业；个体，小微
        if(netType.equals("QY")){
            //结算账户类型：对公 = 1，对私 = 2；
            if(accountType.equals("1")){
                //入网证件类型：三证合一；营业执照
                if(licenseType.equals("SZHY")){
                    if(Utils.isEmpty(imgLegalPersonJustPath)){
                        ToastUtil.showText(context,"请上传法人身份证正面照片!",1);
                        return;
                    }
                    if(Utils.isEmpty(imgLegalPersonBackPath)){
                        ToastUtil.showText(context,"请上传法人身份证反面照片!",1);
                        return;
                    }
                    if(Utils.isEmpty(imgMerDoorheadPath)){
                        ToastUtil.showText(context,"请上传商户门头照片!",1);
                        return;
                    }
                    if(Utils.isEmpty(imgMerReceptionPath)){
                        ToastUtil.showText(context,"请上传内部前台照片!",1);
                        return;
                    }
                    if(Utils.isEmpty(imgOpeningPermitPath)){
                        ToastUtil.showText(context,"请上传开户许可证照片!",1);
                        return;
                    }
                }else{
                    if(Utils.isEmpty(imgLegalPersonJustPath)){
                        ToastUtil.showText(context,"请上传法人身份证正面照片!",1);
                        return;
                    }
                    if(Utils.isEmpty(imgLegalPersonBackPath)){
                        ToastUtil.showText(context,"请上传法人身份证反面照片!",1);
                        return;
                    }
                    if(Utils.isEmpty(imgMerDoorheadPath)){
                        ToastUtil.showText(context,"请上传商户门头照片!",1);
                        return;
                    }
                    if(Utils.isEmpty(imgMerReceptionPath)){
                        ToastUtil.showText(context,"请上传内部前台照片!",1);
                        return;
                    }
                    if(Utils.isEmpty(imgOpeningPermitPath)){
                        ToastUtil.showText(context,"请上传开户许可证照片!",1);
                        return;
                    }
                    if(Utils.isEmpty(imgJGDMZPath)){
                        ToastUtil.showText(context,"请上传机构代码证!",1);
                        return;
                    }
                    if(Utils.isEmpty(imgSWDJZPath)){
                        ToastUtil.showText(context,"请上传税务登记证!",1);
                        return;
                    }
                }
            }else{
                //对私
                //入网证件类型：三证合一；营业执照
                if(licenseType.equals("SZHY")){
                    //是否法人入账，法人入账 = 1，非法人入账 = 2；
                    if(isLegalPersonAccount.equals("1")){
                        if(Utils.isEmpty(imgBankCardJustPath)){
                            ToastUtil.showText(context,"请上传银行卡正面照!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgBankCardBackPath)){
                            ToastUtil.showText(context,"请上传银行卡反面照!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgMerDoorheadPath)){
                            ToastUtil.showText(context,"请上传商户门头照片!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgMerReceptionPath)){
                            ToastUtil.showText(context,"请上传内部前台照片!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgOpeningPermitPath)){
                            ToastUtil.showText(context,"请上传开户许可证照片!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgSettPersonJustPath)){
                            ToastUtil.showText(context,"请上传结算人身份证正面照片!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgSettPersonBackPath)){
                            ToastUtil.showText(context,"请上传结算人身份证反面照片!",1);
                            return;
                        }
                    }else{
                        if(Utils.isEmpty(imgLegalPersonJustPath)){
                            ToastUtil.showText(context,"请上传法人身份证正面照片!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgLegalPersonBackPath)){
                            ToastUtil.showText(context,"请上传法人身份证反面照片!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgBankCardJustPath)){
                            ToastUtil.showText(context,"请上传银行卡正面照!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgBankCardBackPath)){
                            ToastUtil.showText(context,"请上传银行卡反面照!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgMerDoorheadPath)){
                            ToastUtil.showText(context,"请上传商户门头照片!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgMerReceptionPath)){
                            ToastUtil.showText(context,"请上传内部前台照片!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgOpeningPermitPath)){
                            ToastUtil.showText(context,"请上传开户许可证照片!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgMerRelationPath)){
                            ToastUtil.showText(context,"请上传商户关系证明!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgSettPersonJustPath)){
                            ToastUtil.showText(context,"请上传结算人身份证正面照片!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgSettPersonBackPath)){
                            ToastUtil.showText(context,"请上传结算人身份证反面照片!",1);
                            return;
                        }
                    }

                }else{
                    //是否法人入账，法人入账 = 1，非法人入账 = 2；
                    if(isLegalPersonAccount.equals("1")){
                        if(Utils.isEmpty(imgBankCardJustPath)){
                            ToastUtil.showText(context,"请上传银行卡正面照!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgBankCardBackPath)){
                            ToastUtil.showText(context,"请上传银行卡反面照!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgMerDoorheadPath)){
                            ToastUtil.showText(context,"请上传商户门头照片!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgMerReceptionPath)){
                            ToastUtil.showText(context,"请上传内部前台照片!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgOpeningPermitPath)){
                            ToastUtil.showText(context,"请上传开户许可证照片!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgSettPersonJustPath)){
                            ToastUtil.showText(context,"请上传结算人身份证正面照片!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgSettPersonBackPath)){
                            ToastUtil.showText(context,"请上传结算人身份证反面照片!",1);
                            return;
                        }

                        if(Utils.isEmpty(imgJGDMZPath)){
                            ToastUtil.showText(context,"请上传机构代码证!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgSWDJZPath)){
                            ToastUtil.showText(context,"请上传税务登记证!",1);
                            return;
                        }
                    }else{
                        if(Utils.isEmpty(imgLegalPersonJustPath)){
                            ToastUtil.showText(context,"请上传法人身份证正面照片!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgLegalPersonBackPath)){
                            ToastUtil.showText(context,"请上传法人身份证反面照片!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgBankCardJustPath)){
                            ToastUtil.showText(context,"请上传银行卡正面照!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgBankCardBackPath)){
                            ToastUtil.showText(context,"请上传银行卡反面照!",1);
                            return;
                        }

                        if(Utils.isEmpty(imgMerDoorheadPath)){
                            ToastUtil.showText(context,"请上传商户门头照片!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgMerReceptionPath)){
                            ToastUtil.showText(context,"请上传内部前台照片!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgOpeningPermitPath)){
                            ToastUtil.showText(context,"请上传开户许可证照片!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgMerRelationPath)){
                            ToastUtil.showText(context,"请上传商户关系证明!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgSettPersonJustPath)){
                            ToastUtil.showText(context,"请上传结算人身份证正面照片!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgSettPersonBackPath)){
                            ToastUtil.showText(context,"请上传结算人身份证反面照片!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgJGDMZPath)){
                            ToastUtil.showText(context,"请上传机构代码证!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgSWDJZPath)){
                            ToastUtil.showText(context,"请上传税务登记证!",1);
                            return;
                        }
                    }
                }
            }
        }
        //个体
        else if(netType.equals("GT")){
            //结算账户类型：对公 = 1，对私 = 2；
            if(accountType.equals("1")){
                //入网证件类型：三证合一；营业执照
                if(licenseType.equals("SZHY")){
                    if(Utils.isEmpty(imgLegalPersonJustPath)){
                        ToastUtil.showText(context,"请上传法人身份证正面照片!",1);
                        return;
                    }
                    if(Utils.isEmpty(imgLegalPersonBackPath)){
                        ToastUtil.showText(context,"请上传法人身份证反面照片!",1);
                        return;
                    }
                    if(Utils.isEmpty(imgMerDoorheadPath)){
                        ToastUtil.showText(context,"请上传商户门头照片!",1);
                        return;
                    }
                    if(Utils.isEmpty(imgMerReceptionPath)){
                        ToastUtil.showText(context,"请上传内部前台照片!",1);
                        return;
                    }
                    if(Utils.isEmpty(imgOpeningPermitPath)){
                        ToastUtil.showText(context,"请上传开户许可证照片!",1);
                        return;
                    }
                }else{
                    if(Utils.isEmpty(imgLegalPersonJustPath)){
                        ToastUtil.showText(context,"请上传法人身份证正面照片!",1);
                        return;
                    }
                    if(Utils.isEmpty(imgLegalPersonBackPath)){
                        ToastUtil.showText(context,"请上传法人身份证反面照片!",1);
                        return;
                    }
                    if(Utils.isEmpty(imgMerDoorheadPath)){
                        ToastUtil.showText(context,"请上传商户门头照片!",1);
                        return;
                    }
                    if(Utils.isEmpty(imgMerReceptionPath)){
                        ToastUtil.showText(context,"请上传内部前台照片!",1);
                        return;
                    }
                    if(Utils.isEmpty(imgOpeningPermitPath)){
                        ToastUtil.showText(context,"请上传开户许可证照片!",1);
                        return;
                    }

                    if(Utils.isEmpty(imgJGDMZPath)){
                        ToastUtil.showText(context,"请上传机构代码证!",1);
                        return;
                    }
                    if(Utils.isEmpty(imgSWDJZPath)){
                        ToastUtil.showText(context,"请上传税务登记证!",1);
                        return;
                    }
                }

            }else{
                //对私
                //入网证件类型：三证合一；营业执照
                if(licenseType.equals("SZHY")){
                    //是否法人入账，法人入账 = 1，非法人入账 = 2；
                    if(isLegalPersonAccount.equals("1")){
                        if(Utils.isEmpty(imgBankCardJustPath)){
                            ToastUtil.showText(context,"请上传银行卡正面照!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgBankCardBackPath)){
                            ToastUtil.showText(context,"请上传银行卡反面照!",1);
                            return;
                        }

                        if(Utils.isEmpty(imgMerDoorheadPath)){
                            ToastUtil.showText(context,"请上传商户门头照片!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgMerReceptionPath)){
                            ToastUtil.showText(context,"请上传内部前台照片!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgSettPersonJustPath)){
                            ToastUtil.showText(context,"请上传结算人身份证正面照片!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgSettPersonBackPath)){
                            ToastUtil.showText(context,"请上传结算人身份证反面照片!",1);
                            return;
                        }


                    }else{

                        if(Utils.isEmpty(imgLegalPersonJustPath)){
                            ToastUtil.showText(context,"请上传法人身份证正面照片!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgLegalPersonBackPath)){
                            ToastUtil.showText(context,"请上传法人身份证反面照片!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgBankCardJustPath)){
                            ToastUtil.showText(context,"请上传银行卡正面照!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgBankCardBackPath)){
                            ToastUtil.showText(context,"请上传银行卡反面照!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgMerDoorheadPath)){
                            ToastUtil.showText(context,"请上传商户门头照片!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgMerReceptionPath)){
                            ToastUtil.showText(context,"请上传内部前台照片!",1);
                            return;
                        }

                        if(Utils.isEmpty(imgMerRelationPath)){
                            ToastUtil.showText(context,"请上传商户关系证明!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgSettPersonJustPath)){
                            ToastUtil.showText(context,"请上传结算人身份证正面照片!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgSettPersonBackPath)){
                            ToastUtil.showText(context,"请上传结算人身份证反面照片!",1);
                            return;
                        }
                    }
                }else{
                    //是否法人入账，法人入账 = 1，非法人入账 = 2；
                    if(isLegalPersonAccount.equals("1")){
                        if(Utils.isEmpty(imgBankCardJustPath)){
                            ToastUtil.showText(context,"请上传银行卡正面照!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgBankCardBackPath)){
                            ToastUtil.showText(context,"请上传银行卡反面照!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgMerDoorheadPath)){
                            ToastUtil.showText(context,"请上传商户门头照片!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgMerReceptionPath)){
                            ToastUtil.showText(context,"请上传内部前台照片!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgSettPersonJustPath)){
                            ToastUtil.showText(context,"请上传结算人身份证正面照片!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgSettPersonBackPath)){
                            ToastUtil.showText(context,"请上传结算人身份证反面照片!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgJGDMZPath)){
                            ToastUtil.showText(context,"请上传机构代码证!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgSWDJZPath)){
                            ToastUtil.showText(context,"请上传税务登记证!",1);
                            return;
                        }
                    }else{
                        if(Utils.isEmpty(imgBankCardJustPath)){
                            ToastUtil.showText(context,"请上传银行卡正面照!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgBankCardBackPath)){
                            ToastUtil.showText(context,"请上传银行卡反面照!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgMerDoorheadPath)){
                            ToastUtil.showText(context,"请上传商户门头照片!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgMerReceptionPath)){
                            ToastUtil.showText(context,"请上传内部前台照片!",1);
                            return;
                        }

                        if(Utils.isEmpty(imgMerRelationPath)){
                            ToastUtil.showText(context,"请上传商户关系证明!",1);
                            return;
                        }

                        if(Utils.isEmpty(imgSettPersonJustPath)){
                            ToastUtil.showText(context,"请上传结算人身份证正面照片!",1);
                            return;
                        }
                        if(Utils.isEmpty(imgSettPersonBackPath)){
                            ToastUtil.showText(context,"请上传结算人身份证反面照片!",1);
                            return;
                        }
                    }
                }
            }
        }
        subRateInfo();
    }

    /**
     * 获取详细信息
     */
    private void getBusInfo(){
        showWaitDialog();
        final String url = NitConfig.getBusInfo;
        new Thread(){
            @Override
            public void run() {
                try {
                    // 拼装JSON数据，向服务端发起请求
                    JSONObject userJSON = new JSONObject();
                    userJSON.put("page", "3");
                    userJSON.put("id", String.valueOf(id));
                    String content = String.valueOf(userJSON);
                    Log.e("查询商户信息请求参数：", content);
                    String jsonStr = HttpURLConnectionUtil.doPos(url,content);
                    Log.e("查询商户信息请求参数返回字符串结果：", jsonStr);
                    int msg = 8;
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

    /**
     *  上传图片
     */
    private void UploadPhoto(final File file){
//        showWaitDialog("正在上传...");
        //上传图片path
        final String url= NitConfig.uploadImg;
        final Map<String, String> map = new HashMap<String, String>();
        map.put("id", String.valueOf(id));
        map.put("agent_id", userBean.getAgent_id());
        new Thread(){
            public void run() {
                Log.e(TAG,"文件路径"+file.getPath());
                String jsonStr=UploadUtil.uploadFile(map,file, url);
                Log.e(TAG,"上传照片返回的信息为："+jsonStr);

                int msg = 2;
                String text = jsonStr;
                sendMessage(msg,text);

            };
        }.start();

    }

    private void subRateInfo(){
        showWaitDialog();
        final String url = NitConfig.subRateInfo;
        new Thread(){
            @Override
            public void run() {
                try {
                    // 拼装JSON数据，向服务端发起请求
                    JSONObject userJSON = new JSONObject();
                    userJSON.put("salesman_id", userBean.getSalesman_id());
                    userJSON.put("agent_id", userBean.getAgent_id());
                    userJSON.put("id", String.valueOf(id));
                    userJSON.put("wx_rate", String.valueOf(wxRate));
                    userJSON.put("ali_rate", String.valueOf(aliRate));
                    userJSON.put("best_rate", String.valueOf(yzfRate));
                    userJSON.put("debit_rate", String.valueOf(jjkRate));
                    userJSON.put("crebit_rate", String.valueOf(djkRate));
                    userJSON.put("unionpay_rate", String.valueOf(ylRate));
                    userJSON.put("reserve1", String.valueOf(bankReserve));

                    Log.e("wx_rate",String.valueOf(wxRate));
                    Log.e("ali_rate",String.valueOf(aliRate));
                    Log.e("best_rate",String.valueOf(yzfRate));
                    Log.e("crebit_rate",String.valueOf(djkRate));
                    Log.e("debit_rate",String.valueOf(jjkRate));
                    Log.e("unionpay_rate",String.valueOf(ylRate));

                    userJSON.put("img_business_license", yt_imgLicensePath);
                    userJSON.put("img_idcard_a", yt_imgLegalPersonJustPath);//法人正面照
                    userJSON.put("img_idcard_b", yt_imgLegalPersonBackPath);
                    userJSON.put("img_bankcard_a", yt_imgBankCardJustPath);//银行卡
                    userJSON.put("img_bankcard_b", yt_imgBankCardBackPath);
                    userJSON.put("img_logo", yt_imgMerDoorheadPath);//商户门头照
                    userJSON.put("img_indoor", yt_imgMerReceptionPath);//内部前台照
                    userJSON.put("img_open_permits", yt_imgOpeningPermitPath);//开户许可证
                    userJSON.put("img_private_idcard_a", yt_imgSettPersonJustPath);//结算人身份证
                    userJSON.put("img_private_idcard_b", yt_imgSettPersonBackPath);
                    userJSON.put("img_relation", yt_imgMerRelationPath);//商户关系证明
                    userJSON.put("img_mer_relation", yt_imgMerZFDRelationPath);//商户总分店关系
                    userJSON.put("img_mer_increment", yt_imgMerXYPath);//商户增值协议
                    userJSON.put("img_org_code", yt_imgJGDMZPath);//机构代码证
                    userJSON.put("img_tax_reg", yt_imgSWDJZPath);//税务登记证


                    userJSON.put("thum_img_business_license", imgLicensePath);
                    userJSON.put("thum_img_idcard_a", imgLegalPersonJustPath);//法人正面照
                    userJSON.put("thum_img_idcard_b", imgLegalPersonBackPath);
                    userJSON.put("thum_img_bankcard_a", imgBankCardJustPath);//银行卡
                    userJSON.put("thum_img_bankcard_b", imgBankCardBackPath);
                    userJSON.put("thum_img_logo", imgMerDoorheadPath);//商户门头照
                    userJSON.put("thum_img_indoor", imgMerReceptionPath);//内部前台照

                    userJSON.put("thum_img_open_permits", imgOpeningPermitPath);//开户许可证

                    userJSON.put("thum_img_private_idcard_a", imgSettPersonJustPath);//结算人身份证
                    userJSON.put("thum_img_private_idcard_b", imgSettPersonBackPath);
                    userJSON.put("thum_img_relation", imgMerRelationPath);//商户关系证明
                    userJSON.put("thum_img_mer_relation", imgMerZFDRelationPath);//商户总分店关系
                    userJSON.put("thum_img_mer_increment", imgMerXYPath);//商户增值协议
                    userJSON.put("thum_img_org_code", imgJGDMZPath);//机构代码证
                    userJSON.put("thum_img_tax_reg", imgSWDJZPath);//税务登记证
                    String content = String.valueOf(userJSON);
                    Log.e("提交第三页请求参数：", content);
                    String jsonStr = HttpURLConnectionUtil.doPos(url,content);
                    Log.e("提交第三页返回字符串结果：", jsonStr);
                    int msg = 3;
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
                    backgroundAlpha((float)msg.obj);
                    break;
                case 2:
                    String upLoadImgJson = (String) msg.obj;
                    upLoadImgJson(upLoadImgJson);
                    break;
                case 3:
                    String subRateInfoJson = (String) msg.obj;
                    subRateInfoJson(subRateInfoJson);
                    hideWaitDialog();
                    break;
                case 8:
                    String busInfo = (String) msg.obj;
                    busInfo(busInfo);
                    hideWaitDialog();
                    break;
                case 201:
                    hideWaitDialog();
                    errorJsonText = (String) msg.obj;
                    ToastUtil.showText(context,errorJsonText,1);
                    break;
                case 202:
                    hideWaitDialog();
                    errorJsonText = (String) msg.obj;
                    ToastUtil.showText(context,errorJsonText,1);
                    break;
                case 301:
                    hideWaitDialog();
                    errorJsonText = (String) msg.obj;
                    ToastUtil.showText(context,errorJsonText,1);
                    break;
                case 400:
                    hideWaitDialog();
                    errorJsonText = (String) msg.obj;
                    ToastUtil.showText(context,errorJsonText,1);
                    break;
            }
        }
    };

    private void upLoadImgJson(String json){
        if(Utils.isNotEmpty(json)){
            try {

                JSONObject job  = new JSONObject(json);
                String code = job.getString("code");
                if(code.equals("000000")){
                    String dataJson = job.getString("data");
                    JSONObject dataJob = new JSONObject(dataJson);
                    String thumbnailImage = dataJob.getString("thumbnailImage");
                    String locationPath = dataJob.getString("locationPath");
                    //赋值对应证件
                    setImgBitmap(thumbnailImage,locationPath);
                }else{
                    hideWaitDialog();
                    ToastUtil.showText(context,"上传失败！",1);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                ToastUtil.showText(context,"上传失败！",1);
            }
        }else{
            hideWaitDialog();
            ToastUtil.showText(context,"上传失败！",1);
        }

    }

    private void subRateInfoJson(String json){
        //{"code":"000000","data":{"id":"288","message":"提交成功","status":200},"timestamp":"1539934159224"}
        try {
            JSONObject job = new JSONObject(json);
            String code = job.getString("code");
            if(code.equals("000000")){
                String dataJson = job.getString("data");
                JSONObject dataJob = new JSONObject(dataJson);
                int status = dataJob.getInt("status");
                String message = dataJob.getString("message");
                if(status == 200){
                    int id = dataJob.getInt("id");
                    Intent in = new Intent();
                    in.setClass(this,SubmitSuccessActivity.class);
                    in.putExtra("name",MerName);
                    startActivity(in);
                    //关闭除MainActivity外其余的Activity
                    BaseApplication.getInstance().noMain_exit();
                    finish();

                }else{
                    ToastUtil.showText(context,message,1);
                }
            }else{
                ToastUtil.showText(context,"服务异常",1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void busInfo(String json){
        //{"code":"000000","data":{"timely_sign":"0","agentMap":{"timely_sign":"0","merchant_status":"0","id":"350"},"isEmpty":"0"},"timestamp":"1540449834047"}
        try {
            JSONObject job = new JSONObject(json);
            String code = job.getString("code");
            if(code.equals("000000")){
                String dataJson = job.getString("data");
                JSONObject dataJob = new JSONObject(dataJson);
                //isEmpty："1"值为1表示有数据，为0无数据
                String isEmpty = dataJob.getString("isEmpty");
                if(isEmpty.equals("1")){
                    String agentMap = dataJob.getString("agentMap");
                    Gson gjson  =  GsonUtils.getGson();
                    bus = gjson.fromJson(agentMap, BusDetailData.class);
                    updateView();
                }
            }else{
                ToastUtil.showText(context,"数据请求失败！",1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 背景渐变暗
     */
    private void setWindowBackground(){
        PhotoPopupWindow popupWindow = new PhotoPopupWindow(this);
        popupWindow.showPhotoWindow();
        popupWindow.setOnSelectClickListener(this);
        popupWindow.showAtLocation(myLayout, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
        //添加pop窗口关闭事件，主要是实现关闭时改变背景的透明度
        popupWindow.setOnDismissListener(new poponDismissListener());
        backgroundAlpha(1f);
        new Thread(new Runnable(){
            @Override
            public void run() {
                while(alpha > 0.5f){
                    try {
                        //4是根据弹出动画时间和减少的透明度计算
                        Thread.sleep(4);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message msg = handler.obtainMessage();
                    msg.what = 1;
                    //每次减少0.01，精度越高，变暗的效果越流畅
                    alpha-=0.01f;
                    msg.obj = alpha ;
                    handler.sendMessage(msg);
                }
            }

        }).start();
    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }


    /**
     * 返回或者点击空白位置的时候将背景透明度改回来
     */
    class poponDismissListener implements PopupWindow.OnDismissListener{

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            new Thread(new Runnable(){
                @Override
                public void run() {
                    //此处while的条件alpha不能<= 否则会出现黑屏
                    while(alpha<1f){
                        try {
                            Thread.sleep(4);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.d("HeadPortrait","alpha:"+alpha);

                        Message msg = handler.obtainMessage();
                        msg.what = 1;
                        alpha+=0.01f;
                        msg.obj =alpha ;
                        handler.sendMessage(msg);
                    }
                }

            }).start();
        }

    }

    /**
     * 启动相册
     */
    private void intentGallery(){

        mPhotoModule.takePhoto(PhotoModule.REQUEST_CODE_CROP_PHOTO);
    }

    /**
     * 启动拍照（相机）
     */
    private void intentCamera(){
        mPhotoModule.takePhoto(PhotoModule.REQUEST_CODE_CAMERA);
    }

    private void setImgBitmap(String imgPath,String ytImgPath){
        if(selectImg.equals("1")){
            imgLicensePath = imgPath;
            yt_imgLicensePath = ytImgPath;
            //占位图
            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.option_icon);
            Glide.with(this)
                    .load(imgPath)
                    .apply(options)
                    .into(imgLicense);
        }
        if(selectImg.equals("2")){
            //法人正面
            imgLegalPersonJustPath = imgPath;
            yt_imgLegalPersonJustPath = ytImgPath;
            Glide.with(this)
                    .load(imgPath)
                    .into(imgLegalPersonJust);
        }
        if(selectImg.equals("3")){
            //法人反面
            imgLegalPersonBackPath = imgPath;
            yt_imgLegalPersonBackPath = ytImgPath;
            Glide.with(this)
                    .load(imgPath)
                    .into(imgLegalPersonBack);

        }
        if(selectImg.equals("4")){
            //银行卡正面
            imgBankCardJustPath = imgPath;
            yt_imgBankCardJustPath = ytImgPath;
            Glide.with(this)
                    .load(imgPath)
                    .into(imgBankCardJust);

        }
        if(selectImg.equals("5")){
            //银行卡反面
            imgBankCardBackPath = imgPath;
            yt_imgBankCardBackPath = ytImgPath;
            Glide.with(this)
                    .load(imgPath)
                    .into(imgBankCardBack);

        }
        if(selectImg.equals("6")){
            //商户门头照
            imgMerDoorheadPath = imgPath;
            yt_imgMerDoorheadPath = ytImgPath;
            Glide.with(this)
                    .load(imgPath)
                    .into(imgMerDoorhead);

        }
        if(selectImg.equals("7")){
            //门店前台照
            imgMerReceptionPath = imgPath;
            yt_imgMerReceptionPath = ytImgPath;
            Glide.with(this)
                    .load(imgPath)
                    .into(imgMerReception);
        }
        if(selectImg.equals("8")){
            //开户许可证
            imgOpeningPermitPath = imgPath;
            yt_imgOpeningPermitPath = ytImgPath;
            Glide.with(this)
                    .load(imgPath)
                    .into(imgOpeningPermit);
        }
        if(selectImg.equals("9")){
            //商户关系证明照
            imgMerRelationPath = imgPath;
            yt_imgMerRelationPath = ytImgPath;
            Glide.with(this)
                    .load(imgPath)
                    .into(imgMerRelation);
        }
        if(selectImg.equals("10")){
            //结算人身份证正面
            imgSettPersonJustPath = imgPath;
            yt_imgSettPersonJustPath = ytImgPath;
            Glide.with(this)
                    .load(imgPath)
                    .into(imgSettPersonJust);
        }
        if(selectImg.equals("11")){
            imgSettPersonBackPath = imgPath;
            yt_imgSettPersonBackPath = ytImgPath;
            Glide.with(this)
                    .load(imgPath)
                    .into(imgSettPersonBack);
        }
        if(selectImg.equals("12")){
            //总分店关系照
            imgMerZFDRelationPath = imgPath;
            yt_imgMerZFDRelationPath = ytImgPath;
            Glide.with(this)
                    .load(imgPath)
                    .into(imgMerZFDRelation);

        }
        if(selectImg.equals("13")){
            //商户增值协议
            imgMerXYPath = imgPath;
            yt_imgMerXYPath = ytImgPath;
            Glide.with(this)
                    .load(imgPath)
                    .into(imgMerXY);
        }
        if(selectImg.equals("14")){
            //机构代码证
            imgJGDMZPath = imgPath;
            yt_imgJGDMZPath = ytImgPath;
            Glide.with(this)
                    .load(imgPath)
                    .into(imgJGDMZ);
        }
        if(selectImg.equals("15")){
            //税务登记证
            imgSWDJZPath = imgPath;
            yt_imgSWDJZPath = ytImgPath;
            Glide.with(this)
                    .load(imgPath)
                    .into(imgSWDJZ);
        }
        hideWaitDialog();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rate_info_wxRateReduce:
                if(wxRate<=wxMinRate){
                    return;
                }
                wxRate = DecimalUtil.sub(wxRate,CHANGE,1);
                etWxRate.setText(String.valueOf(wxRate));
                break;
            case R.id.rate_info_wxRateAdd:
                if(wxRate>=wxMaxRate){
                    return;
                }
                wxRate = DecimalUtil.add(wxRate,CHANGE,1);
                etWxRate.setText(String.valueOf(wxRate));
                break;
            case R.id.rate_info_aliRateReduce:
                if(aliRate<=aliMinRate){
                    return;
                }
                aliRate = DecimalUtil.sub(aliRate,CHANGE,1);
                etAliRate.setText(String.valueOf(aliRate));
                break;
            case R.id.rate_info_aliRateAdd:
                if(aliRate>=aliMaxRate){
                    return;
                }
                aliRate = DecimalUtil.add(aliRate,CHANGE,1);
                etAliRate.setText(String.valueOf(aliRate));
                break;
            case R.id.rate_info_yzfRateReduce:
                if(yzfRate<=yzfMinRate){
                    return;
                }
                yzfRate = DecimalUtil.sub(yzfRate,CHANGE,1);
                etYzfRate.setText(String.valueOf(yzfRate));
                break;
            case R.id.rate_info_yzfRateAdd:
                if(yzfRate>=yzfMaxRate){
                    return;
                }
                yzfRate = DecimalUtil.add(yzfRate,CHANGE,1);
                etYzfRate.setText(String.valueOf(yzfRate));
                break;
            case R.id.rate_info_jjkRateReduce:
                if(jjkRate<=jjkMinRate){
                    return;
                }
                jjkRate = DecimalUtil.sub(jjkRate,CHANGE,1);
                etJjkRate.setText(String.valueOf(jjkRate));
                break;
            case R.id.rate_info_jjkRateAdd:
                if(jjkRate>=jjkMaxRate){
                    return;
                }
                jjkRate = DecimalUtil.add(jjkRate,CHANGE,1);
                etJjkRate.setText(String.valueOf(jjkRate));
                break;
            case R.id.rate_info_djkRateReduce:
                if(djkRate<=djkMinRate){
                    return;
                }
                djkRate = DecimalUtil.sub(djkRate,CHANGE,1);
                etDjkRate.setText(String.valueOf(djkRate));
                break;
            case R.id.rate_info_djkRateAdd:
                if(djkRate>=djkMaxRate){
                    return;
                }
                djkRate = DecimalUtil.add(djkRate,CHANGE,1);
                etDjkRate.setText(String.valueOf(djkRate));
                break;
            case R.id.rate_info_ylRateReduce:
                if(ylRate<=ylMinRate){
                    return;
                }
                ylRate = DecimalUtil.sub(ylRate,CHANGE,1);
                etYlRate.setText(String.valueOf(ylRate));
                break;
            case R.id.rate_info_ylRateAdd:
                if(ylRate>=ylMaxRate){
                    return;
                }
                ylRate = DecimalUtil.add(ylRate,CHANGE,1);
                etYlRate.setText(String.valueOf(ylRate));
                break;
            case R.id.rate_info_bankReserveReduce:
                if(bankReserve<=bankMinReserve){
                    return;
                }
                bankReserve = bankReserve-RESERVE;
                etBankReserve.setText(String.valueOf(bankReserve));
                break;
            case R.id.rate_info_bankReserveAdd:
                if(bankReserve>=bankMaxReserve){
                    return;
                }
                bankReserve = bankReserve+RESERVE;
                etBankReserve.setText(String.valueOf(bankReserve));
                break;
            case R.id.rate_info_imgLicense://营业执照
                selectImg = "1";
                //背景渐变暗
                setWindowBackground();
                break;
            case R.id.rate_info_imgLegalPersonJust://法人正面
                selectImg = "2";
                //背景渐变暗
                setWindowBackground();
                break;
            case R.id.rate_info_imgLegalPersonBack://法人反面
                selectImg = "3";
                //背景渐变暗
                setWindowBackground();
                break;
            case R.id.rate_info_imgBankCardJust://银行卡正面
                selectImg = "4";
                //背景渐变暗
                setWindowBackground();
                break;
            case R.id.rate_info_imgBankCardBack://银行卡反面
                selectImg = "5";
                //背景渐变暗
                setWindowBackground();
                break;
            case R.id.rate_info_imgMerDoorhead://商户门头照
                selectImg = "6";
                //背景渐变暗
                setWindowBackground();
                break;
            case R.id.rate_info_imgMerReception://门店前台照
                selectImg = "7";
                //背景渐变暗
                setWindowBackground();
                break;
            case R.id.rate_info_imgOpeningPermit://开户许可照
                selectImg = "8";
                //背景渐变暗
                setWindowBackground();
                break;
            case R.id.rate_info_imgMerRelation://商户关系照
                selectImg = "9";
                //背景渐变暗
                setWindowBackground();
                break;
            case R.id.rate_info_imgSettPersonJust://结算人正面
                selectImg = "10";
                //背景渐变暗
                setWindowBackground();
                break;
            case R.id.rate_info_imgSettPersonBack://结算人反面
                selectImg = "11";
                //背景渐变暗
                setWindowBackground();
                break;
            case R.id.rate_info_imgMerZFDRelation://商户总分店关系
                selectImg = "12";
                //背景渐变暗
                setWindowBackground();
                break;
            case R.id.rate_info_imgMerXY://商户增值协议
                selectImg = "13";
                //背景渐变暗
                setWindowBackground();
                break;
            case R.id.rate_info_imgJGDMZ://机构代码证
                selectImg = "14";
                //背景渐变暗
                setWindowBackground();
                break;
            case R.id.rate_info_imgSWDJZ://税务登记证
                selectImg = "15";
                //背景渐变暗
                setWindowBackground();
                break;
            case R.id.account_rate_btSubmit:
                if(Utils.isFastClick()){
                    return;
                }
                //本地验证
                subTextVerification();
                break;
        }
    }

    @Override
    public void selectListener(int type) {
        if(type == 1){
            //访问相册
            intentGallery();
        }else{
            //拍照
            intentCamera();
        }
    }

    /**
     * 所有事件（进入相册，拍照，裁剪返回）回调处理
     *
     **/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        File file = null;//图片文件
        int degree = 0;//原图片角度
        Bitmap bitmap = null;//原图片
        Bitmap newBitmap = null;//新图片图片
        if (resultCode == this.RESULT_OK) {
            switch (requestCode){
                case PhotoModule.REQUEST_CODE_CROP_PHOTO://相册返回
                    if(resultCode == RESULT_CANCELED){
                        //没有选择图片
                        Log.e("选择图片结果：","没有选择图片");
                        return;
                    }
                    if(Build.VERSION.SDK_INT >= 19){//机型版本适配
                        mPhotoModule.handleImageOnKitKat(data);//7.0
                    }else{
                        mPhotoModule.handleImageBeforeKitKat(data);
                    }
                    try {
                        /** 以下代码是屏蔽调裁剪后上传原图的代码 */
                        file = new File(mPhotoModule.imagePath);
                        degree = FileUtilcll.readPictureDegree(file.getAbsolutePath());
                        bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        newBitmap = FileUtilcll.rotaingImageView(degree,bitmap);
                        file = FileUtilcll.saveBitmapFile(newBitmap,file.getAbsolutePath());
                        if(file.exists()){
                            long totalLength = FileUtils.getFileSize(file);
                            double filaSize = FileUtils.getFileSize(totalLength);
                            Log.e("文件字节：",totalLength+"");
                            Log.e("文件大小：",filaSize+"");
                            if(filaSize<8){
//                                ToastUtil.showText(context,"图片文件"+FileUtils.FormetFileSize(totalLength),1);
                                Log.e(TAG,"文件存在！");
                                showWaitDialog("正在上传...");
                                //上传文件
                                UploadPhoto(file);
                            }else{
                                ToastUtil.showText(context,"选择的图片文件过大，无法上传！",1);
//                                ToastUtil.showText(context,"图片文件"+FileUtils.FormetFileSize(totalLength),1);
                            }
                        }else{
                            Log.e(TAG,"文件不存在！");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case PhotoModule.REQUEST_CODE_CAMERA://拍照返回
                    if(requestCode == PhotoModule.REQUEST_CODE_CAMERA){
                        if(resultCode == RESULT_OK){
                            //选择裁剪（但不清晰）
//                            mPhotoModule.cropPhoto();
                            //不选择裁剪直接返回File
                            try {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    file = mPhotoModule.getCameraFile();
                                }else{
                                    file = FileUtilcll.uriTurnFile(mPhotoModule.imageUri);
                                }
                                degree = FileUtilcll.readPictureDegree(file.getAbsolutePath());
                                bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                                newBitmap = FileUtilcll.rotaingImageView(degree,bitmap);
                                file = FileUtilcll.saveBitmapFile(newBitmap,file.getAbsolutePath());
                                if(file.exists()){
                                    long totalLength = FileUtils.getFileSize(file);
                                    double filaSize = FileUtils.getFileSize(totalLength);
                                    Log.e("文件字节：",totalLength+"");
                                    Log.e("文件大小：",filaSize+"");
                                    if(filaSize<8){
//                                        ToastUtil.showText(context,"图片文件"+FileUtils.FormetFileSize(totalLength),1);
                                        Log.e(TAG,"文件存在！");
                                        showWaitDialog("正在上传...");
                                        //上传文件
                                        UploadPhoto(file);
                                    }else{
                                        ToastUtil.showText(context,"选择的图片文件过大，无法上传！",1);
//                                        ToastUtil.showText(context,"图片文件"+FileUtils.FormetFileSize(totalLength),1);
                                    }
                                }else{
                                    Log.e(TAG,"文件不存在！");
                                    ToastUtil.showText(context,"拍照图片丢失！",1);
                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                case PhotoModule.CROP_RESULT_CODE://裁剪
                    bitmap = null;
                    /** 裁剪时,裁减属性设置 cropIntent.putExtra("return-data", true); 处理方案如下
                    if (data != null) {
                        Bundle bundle = data.getExtras();
                        if (bundle != null) {
                            bitmap = bundle.getParcelable("data");
                            // 把裁剪后的图片保存至本地 返回路径
                            String urlpath = FileUtilcll.saveFile(this, "crop.jpg", bitmap);
                        }
                    }

                    // 裁剪时,裁减属性设置 cropIntent.putExtra("return-data", false); 处理方案如下
                    try {
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(mPhotoModule.outputUri));
                        // 把裁剪后的图片保存至本地 返回路径
                        String urlpath = FileUtilcll.saveFile(this, "crop.jpg", bitmap);
                        Log.e(TAG,urlpath);
                        //获取文件
                        File file = FileUtilcll.saveBitmapFile(bitmap,urlpath);
                        if(file.exists()){
                            Log.e(TAG,"文件存在！");
                            showWaitDialog("正在上传...");
                            //上传文件
                            UploadPhoto(file);
                        }else{
                            Log.e(TAG,"文件不存在！");
                        }

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    */
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    //com.yanzhenjie:permission:2.0.0-rc11
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //成功
                    Toast.makeText(this, "授权相机权限", Toast.LENGTH_SHORT).show();
                } else {
                    // 勾选了不再询问
                    Toast.makeText(this, "拒绝相机权限", Toast.LENGTH_SHORT).show();
                    /**
                     * 跳转到 APP 详情的权限设置页
                     * 可根据自己的需求定制对话框，点击某个按钮在执行下面的代码
                     */
                }
                break;
        }
    }
}
