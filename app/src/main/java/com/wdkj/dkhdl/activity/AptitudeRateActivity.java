package com.wdkj.dkhdl.activity;

import android.Manifest;
import android.app.Dialog;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wdkj.dkhdl.BaseActivity;
import com.wdkj.dkhdl.BaseApplication;
import com.wdkj.dkhdl.Constant;
import com.wdkj.dkhdl.LoginActivity;
import com.wdkj.dkhdl.MainActivity;
import com.wdkj.dkhdl.R;
import com.wdkj.dkhdl.adapter.IndustryTypeAdapter;
import com.wdkj.dkhdl.bean.BusDetailData;
import com.wdkj.dkhdl.bean.BusWxAndAliDetailData;
import com.wdkj.dkhdl.bean.IndustryTypeData;
import com.wdkj.dkhdl.bean.UserBean;
import com.wdkj.dkhdl.httputil.HttpURLConnectionUtil;
import com.wdkj.dkhdl.httputil.NetworkUtils;
import com.wdkj.dkhdl.photo.util.FileUtilcll;
import com.wdkj.dkhdl.photo.util.PhotoModule;
import com.wdkj.dkhdl.utils.DecimalUtil;
import com.wdkj.dkhdl.utils.FileUtils;
import com.wdkj.dkhdl.utils.GsonUtils;
import com.wdkj.dkhdl.utils.NitConfig;
import com.wdkj.dkhdl.utils.SharedPreferencesUtil;
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
public class AptitudeRateActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener,View.OnClickListener,PhotoPopupWindow.OnSelectClickListener{

    @ViewInject(R.id.info_failure_hintText)
    TextView tvErrorMsg;
    @ViewInject(R.id.parent_myLayout)
    LinearLayout myLayout;

    @ViewInject(R.id.rate_info_wxSwitch)
    Switch wxSwitch;
    @ViewInject(R.id.rate_info_wxLayout)
    LinearLayout wxLayout;
    @ViewInject(R.id.rate_info_wxContidText)
    EditText wxContidText;
    @ViewInject(R.id.rate_info_aliSwitch)
    Switch aliSwitch;
    @ViewInject(R.id.rate_info_aliLayout)
    LinearLayout aliLayout;
    @ViewInject(R.id.rate_info_aliSourceText)
    EditText aliSourceText;
    @ViewInject(R.id.rate_info_aliCtgyidText)
    TextView aliCtgyidText;

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
    @ViewInject(R.id.rate_info_img_person_a)
    ImageView imgPersonA;//联系人正面
    @ViewInject(R.id.rate_info_img_person_b)
    ImageView imgPersonB;//联系人反面
    @ViewInject(R.id.account_rate_btSubmit)
    Button btSubmit;


    private Context context;
    private UserBean userBean = MainActivity.userBean;
    private String MerName;//商户名称
    private int id;//商户id
    /**
     * 商户类型："1" 一级商户 "2" 二级商户
     */
    private String merchantType;
    private String netType = "GT";//入网类型：企业；个体，小微
    private String accountType = "2";//结算账户类型：对公 = 1，对私 = 2；
    private String licenseType = "SZHY";//入网证件类型：三证合一；营业执照
    private String isLegalPersonAccount = "1";//是否法人入账，法人入账 = 1，非法人入账 = 2；

    /** 微信支付宝信息设置 */
    private boolean wxChecked,aliChecked;
    private List<IndustryTypeData> lsOneIndustry = new ArrayList<IndustryTypeData>();
    private List<IndustryTypeData> lsTwoIndustry = new ArrayList<IndustryTypeData>();
    private List<IndustryTypeData> lsThreeIndustry = new ArrayList<IndustryTypeData>();

    private IndustryTypeData oneIndustryType;
    private IndustryTypeData twoIndustryType;
    private IndustryTypeData threeIndustryType;


    private int oneIndustryIndex,twoIndustryIndex,threeIndustryIndex;
    private String oneIndustryName,twoIndustryName,threeIndustryName;

    private Dialog oneIndustryDialog;
    private Dialog twoIndustryDialog;
    private Dialog threeIndustryDialog;
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
    private String imgPersonAPath = "";
    private String imgPersonBPath = "";
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
    private String yt_imgPersonAPath = "";
    private String yt_imgPersonBPath = "";

    BusDetailData bus;//商户信息对象
    BusWxAndAliDetailData busWxAndAli;//商户信息(微信，支付宝信息)对象

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = AptitudeRateActivity.this;
        MerName = getIntent().getStringExtra("name");
        merchantType = getIntent().getStringExtra("merchantType");
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

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG,"onResume()方法执行.....");
        wxContidText.setFocusable(true);
        wxContidText.setFocusableInTouchMode(true);
        wxContidText.clearFocus();//失去焦点
        aliSourceText.setFocusable(true);
        aliSourceText.setFocusableInTouchMode(true);
        aliSourceText.clearFocus();//失去焦点
//        aliSourceText.requestFocus();//获取焦点
    }

    private void initView(){

        //照片工具处理类实例化
        mPhotoModule=new PhotoModule(this);

        updateView();
    }

    private void initListener(){
        wxSwitch.setOnCheckedChangeListener(this);
        aliSwitch.setOnCheckedChangeListener(this);
        aliCtgyidText.setOnClickListener(this);
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
        imgPersonA.setOnClickListener(this);
        imgPersonB.setOnClickListener(this);
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
            if("2".equals(bus.getMerchant_status())){
                if(Utils.isNotEmpty(bus.getError_msg())){
                    tvErrorMsg.setVisibility(View.VISIBLE);
                    tvErrorMsg.setText(String.format(getResources().getString(R.string.failure_hints),bus.getError_msg()));
                }
            }
        }
        //微信，支付宝信息设置
        wxChecked = true;
        wxSwitch.setChecked(true);
        wxLayout.setVisibility(View.VISIBLE);
        aliChecked = true;
        aliSwitch.setChecked(true);
        aliLayout.setVisibility(View.VISIBLE);
        etWxRate.setText(DecimalUtil.doubletoString(wxRate,1));
        etAliRate.setText(DecimalUtil.doubletoString(aliRate,1));
        etYzfRate.setText(DecimalUtil.doubletoString(yzfRate,1));
        etJjkRate.setText(DecimalUtil.doubletoString(jjkRate,1));
        etDjkRate.setText(DecimalUtil.doubletoString(djkRate,1));
        etYlRate.setText(DecimalUtil.doubletoString(ylRate,1));
        etBankReserve.setText(String.valueOf(bankReserve));
        if(bus != null){
            //微信支付宝开关
            String wx_openStr = bus.getWx_open();
            String ali_openStr = bus.getAli_open();
            if(Utils.isNotEmpty(wx_openStr)){
                if("Y".equals(wx_openStr)){
                    wxChecked = true;
                    wxSwitch.setChecked(true);
                    wxLayout.setVisibility(View.VISIBLE);

                    if(busWxAndAli != null){
                        //联系人微信账号
                        String wx_contidStr = busWxAndAli.getWx_contid();
                        if(Utils.isNotEmpty(wx_contidStr)){
                            wxContidText.setText(wx_contidStr);
                        }
                    }else{
                        wxContidText.setText("");
                    }
                }else{
                    wxChecked = false;
                    wxSwitch.setChecked(false);
                    wxLayout.setVisibility(View.GONE);
                }
            }
            if(Utils.isNotEmpty(ali_openStr)){
                if("Y".equals(ali_openStr)){
                    aliChecked = true;
                    aliSwitch.setChecked(true);
                    aliLayout.setVisibility(View.VISIBLE);
                    if(busWxAndAli != null){

                        //支付宝PID
                        String ali_sourceStr = busWxAndAli.getAli_source();
                        if(Utils.isNotEmpty(ali_sourceStr)){
                            aliSourceText.setText(ali_sourceStr);
                        }else{
                            aliSourceText.setText("");
                        }
                        //经营类目  "ali_ctgyid": "[1669,1686,1689]",
                        String ali_ctgyidStr = busWxAndAli.getAli_ctgyid();
                        Log.e(TAG,ali_ctgyidStr);
                        String reserve1Str = busWxAndAli.getReserve1();
                        //去掉"[ ]"字符
                        if(Utils.isNotEmpty(ali_ctgyidStr)){
                            if(ali_ctgyidStr.contains("[")&&ali_ctgyidStr.contains("]")){
                                String ali_ctgyidStrOne = ali_ctgyidStr.substring(ali_ctgyidStr.indexOf("[")+1,ali_ctgyidStr.length());
                                Log.e(TAG,ali_ctgyidStrOne);
                                String ali_ctgyidStrTwo = ali_ctgyidStrOne.substring(0,ali_ctgyidStrOne.indexOf("]"));
                                Log.e(TAG,ali_ctgyidStrTwo);
                                String ali_ctgyid[] = ali_ctgyidStrTwo.split(",");
                                String ali_ctgyName[] = reserve1Str.split(",");
                                oneIndustryType = new IndustryTypeData();
                                oneIndustryType.setId(Integer.parseInt(ali_ctgyid[0]));
                                oneIndustryType.setName(ali_ctgyName[0]);
                                twoIndustryType = new IndustryTypeData();
                                twoIndustryType.setId(Integer.parseInt(ali_ctgyid[1]));
                                twoIndustryType.setName(ali_ctgyName[1]);
                                threeIndustryType = new IndustryTypeData();
                                threeIndustryType.setId(Integer.parseInt(ali_ctgyid[2]));
                                threeIndustryType.setName(ali_ctgyName[2]);
                                aliCtgyidText.setText(oneIndustryType.getName()+"/"+twoIndustryType.getName()+"/"+threeIndustryType.getName());

                            }else{
                                aliCtgyidText.setText("");
//                                showErrorHintDialog("数据异常");
                            }
                        }else{
                            aliCtgyidText.setText("");
//                            showErrorHintDialog("数据异常");
                        }


                    }
                }else{
                    aliChecked = false;
                    aliSwitch.setChecked(false);
                    aliLayout.setVisibility(View.GONE);
                }
            }


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
            //联系人正面照
            String thum_img_person_a = bus.getThum_img_person_a();
            String img_person_a = bus.getImg_person_a();
            if(Utils.isNotEmpty(thum_img_person_a)){
                imgSWDJZPath = thum_img_person_a;
                yt_imgSWDJZPath = img_person_a;
                Glide.with(this)
                        .load(thum_img_person_a)
                        .apply(options)
                        .into(imgPersonA);
            }
            //联系人反面
            String thum_img_person_b = bus.getThum_img_person_b();
            String img_person_b = bus.getImg_person_b();
            if(Utils.isNotEmpty(thum_img_person_b)){
                imgJGDMZPath = thum_img_person_b;
                yt_imgJGDMZPath = img_person_b;
                Glide.with(this)
                        .load(thum_img_person_b)
                        .apply(options)
                        .into(imgJGDMZ);
            }
        }



        //入网类型：企业；个体，小微
        if("QY".equals(netType)){
            //结算账户类型：对公 = 1，对私 = 2；
            if("1".equals(accountType)){
                //入网证件类型：三证合一；营业执照
                if("SZHY".equals(licenseType)){
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
                if("SZHY".equals(licenseType)){
                    //是否法人入账，法人入账 = 1，非法人入账 = 2；
                    if("1".equals(isLegalPersonAccount)){
                        imgLegalPersonLayout.setVisibility(View.GONE);
                        imgMerRelationLayout.setVisibility(View.GONE);
                        imgJGDMZandSWDJZLayout.setVisibility(View.GONE);
                    }else{
                        imgJGDMZandSWDJZLayout.setVisibility(View.GONE);
                    }


                }else{
                    //是否法人入账，法人入账 = 1，非法人入账 = 2；
                    if("1".equals(isLegalPersonAccount)){
                        imgLegalPersonLayout.setVisibility(View.GONE);
                        imgMerRelationLayout.setVisibility(View.GONE);
                    }
                }
            }
        }
        //个体
        else if("GT".equals(netType)){
            //结算账户类型：对公 = 1，对私 = 2；
            if("1".equals(accountType)){
                //入网证件类型：三证合一；营业执照
                if("SZHY".equals(licenseType)){
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
                if("SZHY".equals(licenseType)){
                    //是否法人入账，法人入账 = 1，非法人入账 = 2；
                    if("1".equals(isLegalPersonAccount)){
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
                    if("1".equals(isLegalPersonAccount)){
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
        String wxContidStr = "",aliSourceStr = "",aliCtgyidStr = "";
    /*
        //微信，支付宝信息设置
        if(wxChecked){
            wxContidStr = wxContidText.getText().toString().trim();
            if(Utils.isEmpty(wxContidStr)){
                ToastUtil.showText(context,"请填写联系人微信账号!",1);
                return;
            }
        }
        if(aliChecked){
            aliSourceStr = aliSourceText.getText().toString().trim();
            if(Utils.isEmpty(aliSourceStr)){
                ToastUtil.showText(context,"请填写合作伙伴在支付宝的PID!",1);
                return;
            }

            if(oneIndustryType == null || twoIndustryType == null || threeIndustryType ==null){
                ToastUtil.showText(context,"请选择经营类目!",1);
                return;
            }else{
                aliCtgyidStr = "["+String.valueOf(oneIndustryType.getId())
                        +","
                        + String.valueOf(twoIndustryType.getId())
                        +","
                        + String.valueOf(threeIndustryType.getId())+"]";
            }
        }
*/

        if(Utils.isEmpty(imgLicensePath)){
            ToastUtil.showText(context,"请上传营业执照照片!",1);
            return;
        }
        //入网类型：企业；个体，小微
        if("QY".equals(netType)){
            //结算账户类型：对公 = 1，对私 = 2；
            if("1".equals(accountType)){
                //入网证件类型：三证合一；营业执照
                if("SZHY".equals(licenseType)){
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
                if("SZHY".equals(licenseType)){
                    //是否法人入账，法人入账 = 1，非法人入账 = 2；
                    if("1".equals(isLegalPersonAccount)){
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
                    if("1".equals(isLegalPersonAccount)){
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
        else if("GT".equals(netType)){
            //结算账户类型：对公 = 1，对私 = 2；
            if("1".equals(accountType)){
                //入网证件类型：三证合一；营业执照
                if("SZHY".equals(licenseType)){
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
                if("SZHY".equals(licenseType)){
                    //是否法人入账，法人入账 = 1，非法人入账 = 2；
                    if("1".equals(isLegalPersonAccount)){
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
                    if("1".equals(isLegalPersonAccount)){
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
        subRateInfo(wxContidStr,aliSourceStr,aliCtgyidStr);
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
            @Override
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

    private void subRateInfo(final String wxContidStr, final String aliSourceStr,final String aliCtgyidStr){
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
                    userJSON.put("merchant_type", merchantType);
                    if(wxChecked){
                        userJSON.put("wx_open", "Y");
                        userJSON.put("wx_contid", wxContidStr);
                    }else{
                        userJSON.put("wx_open", "N");
                        userJSON.put("wx_contid", "");//""
                    }
                    if(aliChecked){
                        userJSON.put("ali_open", "Y");
                        userJSON.put("ali_source", aliSourceStr);
                        userJSON.put("ali_ctgyid", aliCtgyidStr);
                    }else{
                        userJSON.put("ali_open", "N");
                        userJSON.put("ali_source", "");
                        userJSON.put("ali_ctgyid", "");
                    }
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
                    userJSON.put("img_person_a", yt_imgPersonAPath);//联系人正面
                    userJSON.put("img_person_a", yt_imgPersonBPath);//联系人反面


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
                    userJSON.put("thum_img_person_a", imgPersonAPath);//联系人正面
                    userJSON.put("thum_img_person_a", imgPersonBPath);//联系人反面
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

    /**
     * 获取行业分类一级菜单
     */
    private void getIndustryType(){
        showWaitDialog();
        final String url = NitConfig.getSubIndustryType;
        new Thread(){
            @Override
            public void run() {
                try {
                    // 拼装JSON数据，向服务端发起请求
                    JSONObject userJSON = new JSONObject();
                    userJSON.put("id","0");
                    userJSON.put("type","ALI");
                    String content = String.valueOf(userJSON);
                    Log.e("查询行业一级分类请求参数：", content);
                    String jsonStr = HttpURLConnectionUtil.doPos(url,content);
                    Log.e("查询行业一级分类返回字符串结果：", jsonStr);
                    int msg = 4;
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
     * 获取行业分类二级菜单
     */
    private void getTwoIndustryType(final int id){
        showWaitDialog();
        final String url = NitConfig.getSubIndustryType;
        new Thread(){
            @Override
            public void run() {
                try {
                    // 拼装JSON数据，向服务端发起请求
                    JSONObject userJSON = new JSONObject();
                    userJSON.put("id", String.valueOf(id));
                    userJSON.put("type","ALI");
                    String content = String.valueOf(userJSON);
                    Log.e("查询行业二级分类请求参数：", content);
                    String jsonStr = HttpURLConnectionUtil.doPos(url,content);
                    Log.e("查询行业二级分类返回字符串结果：", jsonStr);
                    int msg = 5;
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
     * 获取行业分类三级菜单
     */
    private void getThreeIndustryType(final int id){
        showWaitDialog();
        final String url = NitConfig.getSubIndustryType;
        new Thread(){
            @Override
            public void run() {
                try {
                    // 拼装JSON数据，向服务端发起请求
                    JSONObject userJSON = new JSONObject();
                    userJSON.put("id", String.valueOf(id));
                    userJSON.put("type","ALI");
                    String content = String.valueOf(userJSON);
                    Log.e("查询行业三级分类请求参数：", content);
                    String jsonStr = HttpURLConnectionUtil.doPos(url,content);
                    Log.e("查询行业三级分类返回字符串结果：", jsonStr);
                    int msg = 6;
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
                case 4:
                    String oneIndustryTypeJson = (String) msg.obj;
                    oneIndustryTypeJson(oneIndustryTypeJson);
                    hideWaitDialog();
                    break;
                case 5:
                    String twoIndustryTypeJson = (String) msg.obj;
                    twoIndustryTypeJson(twoIndustryTypeJson);
                    hideWaitDialog();
                    break;
                case 6:
                    String threeIndustryTypeJson = (String) msg.obj;
                    threeIndustryTypeJson(threeIndustryTypeJson);
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
                default:
                    break;
            }
        }
    };

    private void upLoadImgJson(String json){
        if(Utils.isNotEmpty(json)){
            try {

                JSONObject job  = new JSONObject(json);
                String code = job.getString("code");
                if("000000".equals(code)){
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
            String msg = job.getString("msg");
            if(NetworkUtils.RESULT_CODE.equals(code)){
                String subCode = job.getString("subCode");
                String subMsg = job.getString("subMsg");
                if(NetworkUtils.RESULT_SUBCODE.equals(subCode)){
                    Intent in = new Intent();
                    in.setClass(this,SubmitSuccessActivity.class);
                    in.putExtra("name",MerName);
                    startActivity(in);
                    //关闭除MainActivity外其余的Activity
                    BaseApplication.getInstance().noMain_exit();
                    finish();
                }else{
                    if(Utils.isNotEmpty(subMsg)){
                        ToastUtil.showText(context,subMsg,1);
                    }else{
                        ToastUtil.showText(context,"获取数据失败！",1);
                    }
                }
            }else{
                if(Utils.isNotEmpty(msg)){
                    ToastUtil.showText(context,msg,1);
                }else{

                    ToastUtil.showText(context,"提交失败！",1);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();

            ToastUtil.showText(context,"提交失败！",1);
        }
    }

    private void oneIndustryTypeJson(String json){

        try {
            JSONObject job = new JSONObject(json);
            int status = job.getInt("status");
            String message = job.getString("message");
            if(status == 200){
                String dataJson = job.getString("data");
                JSONObject obj = new JSONObject(dataJson);
                String oneIndustryList = obj.getString("BusinessList");
                lsOneIndustry.clear();
                Gson gjson1  =  GsonUtils.getGson();
                lsOneIndustry=gjson1.fromJson(oneIndustryList, new TypeToken<List<IndustryTypeData>>() {  }.getType());

                Log.e("行业分类一级数据",lsOneIndustry.size()+"");
                oneIndustryDialog(lsOneIndustry);

            }else{
                ToastUtil.showText(context,message,1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void twoIndustryTypeJson(String json){

        try {
            JSONObject job = new JSONObject(json);
            int status = job.getInt("status");
            String message = job.getString("message");
            if(status == 200){
                String dataJson = job.getString("data");
                JSONObject obj = new JSONObject(dataJson);
                String oneIndustryList = obj.getString("BusinessList");
                lsTwoIndustry.clear();
                Gson gjson1  =  GsonUtils.getGson();
                lsTwoIndustry=gjson1.fromJson(oneIndustryList, new TypeToken<List<IndustryTypeData>>() {  }.getType());

                Log.e("行业分类二级数据",lsTwoIndustry.size()+"");
                twoIndustryDialog(lsTwoIndustry);

            }else{
                ToastUtil.showText(context,message,1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void threeIndustryTypeJson(String json){

        try {
            JSONObject job = new JSONObject(json);
            int status = job.getInt("status");
            String message = job.getString("message");
            if(status == 200){
                String dataJson = job.getString("data");
                JSONObject obj = new JSONObject(dataJson);
                String oneIndustryList = obj.getString("BusinessList");
                lsThreeIndustry.clear();
                Gson gjson1  =  GsonUtils.getGson();
                lsThreeIndustry=gjson1.fromJson(oneIndustryList, new TypeToken<List<IndustryTypeData>>() {  }.getType());

                Log.e("行业分类三级数据",lsThreeIndustry.size()+"");
                threeIndustryDialog(lsThreeIndustry);

            }else{
                ToastUtil.showText(context,message,1);
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
            String msg = job.getString("msg");
            if(NetworkUtils.RESULT_CODE.equals(code)){
                String subCode = job.getString("subCode");
                String subMsg = job.getString("subMsg");
                if(NetworkUtils.RESULT_SUBCODE.equals(subCode)){

                    String dataJson = job.getString("data");
                    JSONObject dataJob = new JSONObject(dataJson);
                    //isEmpty："1"值为1表示有数据，为0无数据
                    String isEmpty = dataJob.getString("isEmpty");
                    if("1".equals(isEmpty)){
                        String agentMap = dataJob.getString("agentMap");
                        String bsbPay = dataJob.getString("bsbPay");
                        Gson gjson  =  GsonUtils.getGson();
                        bus = gjson.fromJson(agentMap, BusDetailData.class);
                        busWxAndAli = gjson.fromJson(bsbPay, BusWxAndAliDetailData.class);

                        updateView();

                    }
                }else{
                    if(Utils.isNotEmpty(subMsg)){
                        showErrorHintDialog(subMsg);
                    }else{
                        showErrorHintDialog("获取数据失败");
                    }
                }

            }else{
                if(Utils.isNotEmpty(msg)){
                    showErrorHintDialog(msg);
                }else{
                    showErrorHintDialog("服务异常");
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
            showErrorHintDialog("获取数据失败");
        }
    }

    private void oneIndustryDialog(final List<IndustryTypeData> list){
        oneIndustryDialog = new Dialog(context, R.style.PickerDialog);
        //点击屏幕不消失
        oneIndustryDialog.setCancelable(true);
        oneIndustryDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //1：先创建子布局选择器对象
        LayoutInflater inflater=LayoutInflater.from(context);
        //获取dialog布局
        View view=inflater.inflate(R.layout.city_picker_view, null);
        RadioGroup radioGroup = view.findViewById(R.id.city_picker_radioGroup);
        final RadioButton rbProvince = view.findViewById(R.id.city_picker_province);
        final RadioButton rbCity = view.findViewById(R.id.city_picker_city);
        final RadioButton rbArea = view.findViewById(R.id.city_picker_area);
        ListView listView = view.findViewById(R.id.city_picker_listView);
        IndustryTypeAdapter adapter = new IndustryTypeAdapter(this,list);
        listView.setAdapter(adapter);
        oneIndustryDialog.setContentView(view);
        Window window = oneIndustryDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = window.getAttributes();
//            int width = ScreenUtil.getInstance(context).getScreenWidth();
        int width = Utils.getDisplayWidth(this);
        int hight = Utils.getDisplayHeight(this);
        lp.width = width;
        lp.height = hight/2;
        window.setAttributes(lp);


        oneIndustryDialog.show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ToastUtil.showText(context,list.get(position).getName(),1);
                if(Utils.isFastClick(Constant.INTERVAL500)){
                    return;
                }
                oneIndustryIndex = position;
                oneIndustryName = list.get(position).getName();
                rbProvince.setText(oneIndustryName);
                int typeid = list.get(position).getId();

                getTwoIndustryType(typeid);

            }
        });
    }

    private void twoIndustryDialog(final List<IndustryTypeData> list){
        twoIndustryDialog = new Dialog(context, R.style.PickerDialog);
        //点击屏幕不消失
        twoIndustryDialog.setCancelable(true);
        twoIndustryDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //1：先创建子布局选择器对象
        LayoutInflater inflater=LayoutInflater.from(context);
        //获取dialog布局
        View view=inflater.inflate(R.layout.city_picker_view, null);
        RadioGroup radioGroup = view.findViewById(R.id.city_picker_radioGroup);
        final RadioButton rbProvince = view.findViewById(R.id.city_picker_province);
        rbProvince.setText(oneIndustryName);
        final RadioButton rbCity = view.findViewById(R.id.city_picker_city);
        rbCity.setVisibility(View.VISIBLE);
        RadioButton rbArea = view.findViewById(R.id.city_picker_area);
        ListView listView = view.findViewById(R.id.city_picker_listView);
        IndustryTypeAdapter adapter = new IndustryTypeAdapter(this,list);
        listView.setAdapter(adapter);
        twoIndustryDialog.setContentView(view);
        Window window = twoIndustryDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = window.getAttributes();
//            int width = ScreenUtil.getInstance(context).getScreenWidth();
        int width = Utils.getDisplayWidth(this);
        int hight = Utils.getDisplayHeight(this);
        lp.width = width;
        lp.height = hight/2;
        window.setAttributes(lp);

        twoIndustryDialog.show();
        oneIndustryDialog.dismiss();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ToastUtil.showText(context,list.get(position).getName(),1);
                if(Utils.isFastClick(Constant.INTERVAL500)){
                    return;
                }
                twoIndustryIndex = position;
                twoIndustryName = list.get(position).getName();
                rbCity.setText(twoIndustryName);
                int typeid = list.get(position).getId();

                getThreeIndustryType(typeid);



            }
        });
    }

    private void threeIndustryDialog(final List<IndustryTypeData> list){
        threeIndustryDialog = new Dialog(context, R.style.PickerDialog);
        //点击屏幕不消失
        threeIndustryDialog.setCancelable(true);
        threeIndustryDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //1：先创建子布局选择器对象
        LayoutInflater inflater=LayoutInflater.from(context);
        //获取dialog布局
        View view=inflater.inflate(R.layout.city_picker_view, null);
        RadioGroup radioGroup = view.findViewById(R.id.city_picker_radioGroup);
        final RadioButton rbProvince = view.findViewById(R.id.city_picker_province);
        final RadioButton rbCity = view.findViewById(R.id.city_picker_city);
        final RadioButton rbArea = view.findViewById(R.id.city_picker_area);
        rbProvince.setText(oneIndustryName);
        rbCity.setVisibility(View.VISIBLE);
        rbCity.setText(twoIndustryName);
        rbArea.setVisibility(View.VISIBLE);
        ListView listView = view.findViewById(R.id.city_picker_listView);
        IndustryTypeAdapter adapter = new IndustryTypeAdapter(this,list);
        listView.setAdapter(adapter);
        threeIndustryDialog.setContentView(view);
        Window window = threeIndustryDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = window.getAttributes();
//            int width = ScreenUtil.getInstance(context).getScreenWidth();
        int width = Utils.getDisplayWidth(this);
        int hight = Utils.getDisplayHeight(this);
        lp.width = width;
        lp.height = hight/2;
        window.setAttributes(lp);


        threeIndustryDialog.show();
        twoIndustryDialog.dismiss();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ToastUtil.showText(context,list.get(position).getName(),1);
                if(Utils.isFastClick(Constant.INTERVAL500)){
                    return;
                }
                threeIndustryIndex = position;
                threeIndustryName = list.get(position).getName();

                threeIndustryType = list.get(position);
                rbArea.setText(threeIndustryName);
                oneIndustryType = lsOneIndustry.get(oneIndustryIndex);
                twoIndustryType = lsTwoIndustry.get(twoIndustryIndex);
                aliCtgyidText.setText(oneIndustryType.getName()+"/"+twoIndustryType.getName()+"/"+threeIndustryType.getName());
                threeIndustryDialog.dismiss();


            }
        });
    }


    /**
     * 背景渐变暗
     */
    private void setWindowBackground(boolean isUpload){
        PhotoPopupWindow popupWindow = new PhotoPopupWindow(this,isUpload);
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
     *  显示错误提示框
     **/
    private void showErrorHintDialog(String msgText){
        View view = LayoutInflater.from(activity).inflate(R.layout.error_hint_dialog, null);
        TextView tvHintTitle = (TextView) view.findViewById(R.id.error_hint_dialog_tvHintTitle);
        TextView tvHintText = (TextView) view.findViewById(R.id.error_hint_dialog_tvHintText);
        TextView btok = (TextView) view.findViewById(R.id.error_hint_dialog_tvOk);
        final Dialog myDialog = new Dialog(activity,R.style.dialog);
        Window dialogWindow = myDialog.getWindow();
        WindowManager.LayoutParams params = myDialog.getWindow().getAttributes(); // 获取对话框当前的参数值
        dialogWindow.setAttributes(params);
        myDialog.setContentView(view);
        tvHintText.setText(msgText);
        btok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
                myDialog.dismiss();

            }
        });

        myDialog.show();
        myDialog.setCancelable(false);
    }

    /**
     *  显示确认提示框
     **/
    private void showDelateHintDialog(){
        View view = LayoutInflater.from(activity).inflate(R.layout.operation_hint_dialog, null);
        TextView btok = (TextView) view.findViewById(R.id.operation_hint_tvOk);
        TextView btCancel = (TextView) view.findViewById(R.id.operation_hint_tvCancel);
        final Dialog myDialog = new Dialog(activity,R.style.dialog);
        Window dialogWindow = myDialog.getWindow();
        WindowManager.LayoutParams params = myDialog.getWindow().getAttributes(); // 获取对话框当前的参数值
        dialogWindow.setAttributes(params);
        myDialog.setContentView(view);
        btok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                deleteImg();
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

    /**
     * 删除
     */
    private void deleteImg(){
        String imgPath = null;
        if("1".equals(selectImg)){
            imgLicensePath = null;
            yt_imgLicensePath = null;
            setImgView(imgLicense,imgPath);
        }
        if("2".equals(selectImg)){
            //法人正面
            imgLegalPersonJustPath = null;
            yt_imgLegalPersonJustPath = null;
            setImgView(imgLegalPersonJust,imgPath);
        }
        if("3".equals(selectImg)){
            //法人反面
            imgLegalPersonBackPath = null;
            yt_imgLegalPersonBackPath = null;
            setImgView(imgLegalPersonBack,imgPath);

        }
        if("4".equals(selectImg)){
            //银行卡正面
            imgBankCardJustPath = null;
            yt_imgBankCardJustPath = null;
            setImgView(imgBankCardJust,imgPath);

        }
        if("5".equals(selectImg)){
            //银行卡反面
            imgBankCardBackPath = null;
            yt_imgBankCardBackPath = null;
            setImgView(imgBankCardBack,imgPath);

        }
        if("6".equals(selectImg)){
            //商户门头照
            imgMerDoorheadPath = null;
            yt_imgMerDoorheadPath = null;
            setImgView(imgMerDoorhead,imgPath);

        }
        if("7".equals(selectImg)){
            //门店前台照
            imgMerReceptionPath = null;
            yt_imgMerReceptionPath = null;
            setImgView(imgMerReception,imgPath);
        }
        if("8".equals(selectImg)){
            //开户许可证
            imgOpeningPermitPath = null;
            yt_imgOpeningPermitPath = null;
            setImgView(imgOpeningPermit,imgPath);
        }
        if("9".equals(selectImg)){
            //商户关系证明照
            imgMerRelationPath = null;
            yt_imgMerRelationPath = null;
            setImgView(imgMerRelation,imgPath);
        }
        if("10".equals(selectImg)){
            //结算人身份证正面
            imgSettPersonJustPath = null;
            yt_imgSettPersonJustPath = null;
            setImgView(imgSettPersonJust,imgPath);
        }
        if("11".equals(selectImg)){
            imgSettPersonBackPath = null;
            yt_imgSettPersonBackPath = null;
            setImgView(imgSettPersonBack,imgPath);
        }
        if("12".equals(selectImg)){
            //总分店关系照
            imgMerZFDRelationPath = "";
            yt_imgMerZFDRelationPath = "";
            setImgView(imgMerZFDRelation,imgPath);

        }
        if("13".equals(selectImg)){
            //商户增值协议
            imgMerXYPath = "";
            yt_imgMerXYPath = "";
            setImgView(imgMerXY,imgPath);
        }
        if("14".equals(selectImg)){
            //机构代码证
            imgJGDMZPath = null;
            yt_imgJGDMZPath = null;
            setImgView(imgJGDMZ,imgPath);
        }
        if("15".equals(selectImg)){
            //税务登记证
            imgSWDJZPath = null;
            yt_imgSWDJZPath = null;
            setImgView(imgSWDJZ,imgPath);
        }
        if("16".equals(selectImg)){
            //联系人正面
            imgPersonAPath = null;
            yt_imgPersonAPath = null;
            setImgView(imgPersonA,imgPath);
        }
        if("17".equals(selectImg)){
            //联系人反面
            imgPersonBPath = null;
            yt_imgPersonBPath = null;
            setImgView(imgPersonB,imgPath);
        }


    }

    /**
     * 查看大图
     */
    private void seeOriginalImg(){
        String imgPath = "";
        if("1".equals(selectImg)){
            imgPath = yt_imgLicensePath;
        }
        if("2".equals(selectImg)){
            //法人正面
            imgPath = yt_imgLegalPersonJustPath;
        }
        if("3".equals(selectImg)){
            //法人反面
            imgPath = yt_imgLegalPersonBackPath;

        }
        if("4".equals(selectImg)){
            //银行卡正面
            imgPath = yt_imgBankCardJustPath;

        }
        if("5".equals(selectImg)){
            //银行卡反面
            imgPath = yt_imgBankCardBackPath;

        }
        if("6".equals(selectImg)){
            //商户门头照
            imgPath = yt_imgMerDoorheadPath;

        }
        if("7".equals(selectImg)){
            //门店前台照
            imgPath = yt_imgMerReceptionPath;
        }
        if("8".equals(selectImg)){
            //开户许可证
            imgPath = yt_imgOpeningPermitPath;
        }
        if("9".equals(selectImg)){
            //商户关系证明照
            imgPath = yt_imgMerRelationPath;
        }
        if("10".equals(selectImg)){
            //结算人身份证正面
            imgPath = yt_imgSettPersonJustPath;
        }
        if("11".equals(selectImg)){
            imgPath = yt_imgSettPersonBackPath;
        }
        if("12".equals(selectImg)){
            //总分店关系照
            imgPath = yt_imgMerZFDRelationPath;

        }
        if("13".equals(selectImg)){
            //商户增值协议
            imgPath = yt_imgMerXYPath;
        }
        if("14".equals(selectImg)){
            //机构代码证
            imgPath = yt_imgJGDMZPath;
        }
        if("15".equals(selectImg)){
            //税务登记证
            imgPath = yt_imgSWDJZPath;
        }
        if("16".equals(selectImg)){
            //联系人正面
            imgPath = yt_imgPersonAPath;
        }
        if("17".equals(selectImg)){
            //联系人反面
            imgPath = yt_imgPersonBPath;
        }
        if(Utils.isNotEmpty(imgPath)){
            Intent intent = new Intent();
            intent.setClass(activity,SeeOriginalImgActivity.class);
            intent.putExtra("imgPath",imgPath);
            startActivity(intent);
        }else{
            ToastUtil.showText(activity,"找不到图片地址，请重新上传！",1);
        }

    }

    /**
     * ImageView赋值占位图
     */
    private void setImgView(ImageView imgView,String imgPath){
        //占位图
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.option_icon);

        if(Utils.isNotEmpty(imgPath)){
            Glide.with(this)
                    .load(imgPath)
                    .apply(options)
                    .into(imgView);
        }else{
            imgView.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.add_img_icon));
        }

    }

    /**
     * 加载图片
     */
    private void setImgBitmap(String imgPath,String ytImgPath){

        if("1".equals(selectImg)){
            imgLicensePath = imgPath;
            yt_imgLicensePath = ytImgPath;
            setImgView(imgLicense,imgPath);
        }
        if("2".equals(selectImg)){
            //法人正面
            imgLegalPersonJustPath = imgPath;
            yt_imgLegalPersonJustPath = ytImgPath;
            setImgView(imgLegalPersonJust,imgPath);
        }
        if("3".equals(selectImg)){
            //法人反面
            imgLegalPersonBackPath = imgPath;
            yt_imgLegalPersonBackPath = ytImgPath;
            setImgView(imgLegalPersonBack,imgPath);

        }
        if("4".equals(selectImg)){
            //银行卡正面
            imgBankCardJustPath = imgPath;
            yt_imgBankCardJustPath = ytImgPath;
            setImgView(imgBankCardJust,imgPath);

        }
        if("5".equals(selectImg)){
            //银行卡反面
            imgBankCardBackPath = imgPath;
            yt_imgBankCardBackPath = ytImgPath;
            setImgView(imgBankCardBack,imgPath);

        }
        if("6".equals(selectImg)){
            //商户门头照
            imgMerDoorheadPath = imgPath;
            yt_imgMerDoorheadPath = ytImgPath;
            setImgView(imgMerDoorhead,imgPath);

        }
        if("7".equals(selectImg)){
            //门店前台照
            imgMerReceptionPath = imgPath;
            yt_imgMerReceptionPath = ytImgPath;
            setImgView(imgMerReception,imgPath);
        }
        if("8".equals(selectImg)){
            //开户许可证
            imgOpeningPermitPath = imgPath;
            yt_imgOpeningPermitPath = ytImgPath;
            setImgView(imgOpeningPermit,imgPath);
        }
        if("9".equals(selectImg)){
            //商户关系证明照
            imgMerRelationPath = imgPath;
            yt_imgMerRelationPath = ytImgPath;
            setImgView(imgMerRelation,imgPath);
        }
        if("10".equals(selectImg)){
            //结算人身份证正面
            imgSettPersonJustPath = imgPath;
            yt_imgSettPersonJustPath = ytImgPath;
            setImgView(imgSettPersonJust,imgPath);
        }
        if("11".equals(selectImg)){
            imgSettPersonBackPath = imgPath;
            yt_imgSettPersonBackPath = ytImgPath;
            setImgView(imgSettPersonBack,imgPath);
        }
        if("12".equals(selectImg)){
            //总分店关系照
            imgMerZFDRelationPath = imgPath;
            yt_imgMerZFDRelationPath = ytImgPath;
            setImgView(imgMerZFDRelation,imgPath);

        }
        if("13".equals(selectImg)){
            //商户增值协议
            imgMerXYPath = imgPath;
            yt_imgMerXYPath = ytImgPath;
            setImgView(imgMerXY,imgPath);
        }
        if("14".equals(selectImg)){
            //机构代码证
            imgJGDMZPath = imgPath;
            yt_imgJGDMZPath = ytImgPath;
            setImgView(imgJGDMZ,imgPath);
        }
        if("15".equals(selectImg)){
            //税务登记证
            imgSWDJZPath = imgPath;
            yt_imgSWDJZPath = ytImgPath;
            setImgView(imgSWDJZ,imgPath);
        }
        if("16".equals(selectImg)){
            //税务登记证
            imgPersonAPath = imgPath;
            yt_imgPersonAPath = ytImgPath;
            setImgView(imgPersonA,imgPath);
        }
        if("17".equals(selectImg)){
            //税务登记证
            imgPersonBPath = imgPath;
            yt_imgPersonBPath = ytImgPath;
            setImgView(imgPersonB,imgPath);
        }
        hideWaitDialog();
    }


    @Override
    public void onClick(View v) {
        //判断当前是上传还是撤销操作
        boolean isUpload = true;
        switch (v.getId()){
            case R.id.rate_info_aliCtgyidText://经营类目选择
                if(Utils.isFastClick(Constant.INTERVAL500)){
                    return;
                }
                if(lsOneIndustry!=null&&lsOneIndustry.size()>=1){
                    oneIndustryDialog(lsOneIndustry);
                }else{
                    getIndustryType();
                }
                break;
            case R.id.rate_info_wxRateReduce:
                if(busWxAndAli != null){
                    ToastUtil.showText(context,"暂不支持修改费率！",1);
                    return;
                }
                if(wxRate<=wxMinRate){
                    return;
                }
                wxRate = DecimalUtil.sub(wxRate,CHANGE,1);
                etWxRate.setText(String.valueOf(wxRate));
                break;
            case R.id.rate_info_wxRateAdd:
                if(busWxAndAli != null){
                    ToastUtil.showText(context,"暂不支持修改费率！",1);
                    return;
                }
                if(wxRate>=wxMaxRate){
                    return;
                }
                wxRate = DecimalUtil.add(wxRate,CHANGE,1);
                etWxRate.setText(String.valueOf(wxRate));
                break;
            case R.id.rate_info_aliRateReduce:
                if(busWxAndAli != null){
                    ToastUtil.showText(context,"暂不支持修改费率！",1);
                    return;
                }
                if(aliRate<=aliMinRate){
                    return;
                }
                aliRate = DecimalUtil.sub(aliRate,CHANGE,1);
                etAliRate.setText(String.valueOf(aliRate));
                break;
            case R.id.rate_info_aliRateAdd:
                if(busWxAndAli != null){
                    ToastUtil.showText(context,"暂不支持修改费率！",1);
                    return;
                }
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
                if(Utils.isNotEmpty(imgLicensePath)||Utils.isNotEmpty(yt_imgLicensePath)){
                    isUpload = false;
                }
                //背景渐变暗
                setWindowBackground(isUpload);

                break;
            case R.id.rate_info_imgLegalPersonJust://法人正面
                selectImg = "2";
                if(Utils.isNotEmpty(imgLegalPersonJustPath)||Utils.isNotEmpty(yt_imgLegalPersonJustPath)){
                    isUpload = false;
                }
                //背景渐变暗
                setWindowBackground(isUpload);
                break;
            case R.id.rate_info_imgLegalPersonBack://法人反面
                selectImg = "3";
                if(Utils.isNotEmpty(imgLegalPersonBackPath)||Utils.isNotEmpty(yt_imgLegalPersonBackPath)){
                    isUpload = false;
                }
                //背景渐变暗
                setWindowBackground(isUpload);
                break;
            case R.id.rate_info_imgBankCardJust://银行卡正面
                selectImg = "4";
                if(Utils.isNotEmpty(imgBankCardJustPath)||Utils.isNotEmpty(yt_imgBankCardJustPath)){
                    isUpload = false;
                }
                //背景渐变暗
                setWindowBackground(isUpload);
                break;
            case R.id.rate_info_imgBankCardBack://银行卡反面
                selectImg = "5";
                if(Utils.isNotEmpty(imgBankCardBackPath)||Utils.isNotEmpty(yt_imgBankCardBackPath)){
                    isUpload = false;
                }
                //背景渐变暗
                setWindowBackground(isUpload);
                break;
            case R.id.rate_info_imgMerDoorhead://商户门头照
                selectImg = "6";
                if(Utils.isNotEmpty(imgMerDoorheadPath)||Utils.isNotEmpty(yt_imgMerDoorheadPath)){
                    isUpload = false;
                }
                //背景渐变暗
                setWindowBackground(isUpload);
                break;
            case R.id.rate_info_imgMerReception://门店前台照
                selectImg = "7";
                if(Utils.isNotEmpty(imgMerReceptionPath)||Utils.isNotEmpty(yt_imgMerReceptionPath)){
                    isUpload = false;
                }
                //背景渐变暗
                setWindowBackground(isUpload);
                break;
            case R.id.rate_info_imgOpeningPermit://开户许可照
                selectImg = "8";
                if(Utils.isNotEmpty(imgOpeningPermitPath)||Utils.isNotEmpty(yt_imgOpeningPermitPath)){
                    isUpload = false;
                }
                //背景渐变暗
                setWindowBackground(isUpload);
                break;
            case R.id.rate_info_imgMerRelation://商户关系照
                selectImg = "9";
                if(Utils.isNotEmpty(imgMerRelationPath)||Utils.isNotEmpty(yt_imgMerRelationPath)){
                    isUpload = false;
                }
                //背景渐变暗
                setWindowBackground(isUpload);
                break;
            case R.id.rate_info_imgSettPersonJust://结算人正面
                selectImg = "10";
                if(Utils.isNotEmpty(imgSettPersonJustPath)||Utils.isNotEmpty(yt_imgSettPersonJustPath)){
                    isUpload = false;
                }
                //背景渐变暗
                setWindowBackground(isUpload);
                break;
            case R.id.rate_info_imgSettPersonBack://结算人反面
                selectImg = "11";
                if(Utils.isNotEmpty(imgSettPersonBackPath)||Utils.isNotEmpty(yt_imgSettPersonBackPath)){
                    isUpload = false;
                }
                //背景渐变暗
                setWindowBackground(isUpload);
                break;
            case R.id.rate_info_imgMerZFDRelation://商户总分店关系
                selectImg = "12";
                if(Utils.isNotEmpty(imgMerZFDRelationPath)||Utils.isNotEmpty(yt_imgMerZFDRelationPath)){
                    isUpload = false;
                }
                //背景渐变暗
                setWindowBackground(isUpload);
                break;
            case R.id.rate_info_imgMerXY://商户增值协议
                selectImg = "13";
                if(Utils.isNotEmpty(imgMerXYPath)||Utils.isNotEmpty(yt_imgMerXYPath)){
                    isUpload = false;
                }
                //背景渐变暗
                setWindowBackground(isUpload);
                break;
            case R.id.rate_info_imgJGDMZ://机构代码证
                selectImg = "14";
                if(Utils.isNotEmpty(imgJGDMZPath)||Utils.isNotEmpty(yt_imgJGDMZPath)){
                    isUpload = false;
                }
                //背景渐变暗
                setWindowBackground(isUpload);
                break;
            case R.id.rate_info_imgSWDJZ://税务登记证
                selectImg = "15";
                if(Utils.isNotEmpty(imgSWDJZPath)||Utils.isNotEmpty(yt_imgSWDJZPath)){
                    isUpload = false;
                }
                //背景渐变暗
                setWindowBackground(isUpload);
                break;
            case R.id.rate_info_img_person_a://联系人正面
                selectImg = "16";
                if(Utils.isNotEmpty(imgPersonAPath)||Utils.isNotEmpty(yt_imgPersonAPath)){
                    isUpload = false;
                }
                //背景渐变暗
                setWindowBackground(isUpload);
                break;
            case R.id.rate_info_img_person_b://联系人反面
                selectImg = "17";
                if(Utils.isNotEmpty(imgPersonBPath)||Utils.isNotEmpty(yt_imgPersonBPath)){
                    isUpload = false;
                }
                //背景渐变暗
                setWindowBackground(isUpload);
                break;
            case R.id.account_rate_btSubmit:
                if(Utils.isFastClick(Constant.INTERVAL1000)){
                    return;
                }
                //本地验证
                subTextVerification();
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.rate_info_wxSwitch:
                if(isChecked){
                    wxChecked = true;
                    wxLayout.setVisibility(View.VISIBLE);
                }else{
                    wxChecked = false;
                    wxLayout.setVisibility(View.GONE);
                }
                break;
            case R.id.rate_info_aliSwitch:
                if(isChecked){
                    aliChecked = true;
                    aliLayout.setVisibility(View.VISIBLE);
                }else{
                    aliChecked = false;
                    aliLayout.setVisibility(View.GONE);
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void selectListener(int type) {
        if(type == 1){
            //访问相册
            intentGallery();
        }else if(type == 2){
            //拍照
            intentCamera();
        }else if(type == 3){
            //删除
            showDelateHintDialog();
        }else {
            //查看大图
            seeOriginalImg();
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
        if (resultCode == RESULT_OK) {
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
                default:
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
            default:
                break;
        }
    }
}
