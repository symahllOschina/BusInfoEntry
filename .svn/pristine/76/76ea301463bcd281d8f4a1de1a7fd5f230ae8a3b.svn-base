package com.wdkj.dkhdl.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wdkj.dkhdl.BaseActivity;
import com.wdkj.dkhdl.Constant;
import com.wdkj.dkhdl.MainActivity;
import com.wdkj.dkhdl.R;
import com.wdkj.dkhdl.adapter.SpinnerBankAdapter;
import com.wdkj.dkhdl.adapter.SpinnerMerchantIdTypeAdapter;
import com.wdkj.dkhdl.adapter.SpinnerProvinceAdapter;
import com.wdkj.dkhdl.bean.BackListData;
import com.wdkj.dkhdl.bean.BusDetailData;
import com.wdkj.dkhdl.bean.MerchanIdTypeData;
import com.wdkj.dkhdl.bean.RegionData;
import com.wdkj.dkhdl.bean.UserBean;
import com.wdkj.dkhdl.date.picker.TimeSelector;
import com.wdkj.dkhdl.date.util.DateTimeUtil;
import com.wdkj.dkhdl.date.util.DateToTimeStamp;
import com.wdkj.dkhdl.httputil.HttpURLConnectionUtil;
import com.wdkj.dkhdl.httputil.NetworkUtils;
import com.wdkj.dkhdl.utils.GsonUtils;
import com.wdkj.dkhdl.utils.IdcardUtils;
import com.wdkj.dkhdl.utils.NitConfig;
import com.wdkj.dkhdl.utils.ToastUtil;
import com.wdkj.dkhdl.utils.Utils;
import com.wdkj.dkhdl.view.SpinerPopWindow;

import org.angmarch.views.NiceSpinner;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 商户结算账户信息
 */

@ContentView(R.layout.activity_account_info)
public class AccountInfoActivity extends BaseActivity implements View.OnClickListener,
                                                                CompoundButton.OnCheckedChangeListener,
                                                                RadioGroup.OnCheckedChangeListener
{
    private Context context;
    @ViewInject(R.id.info_failure_hintText)
    TextView tvErrorMsg;
    @ViewInject(R.id.account_info_tvNetType)
    NiceSpinner tvNetType;
    @ViewInject(R.id.account_info_licenseNum)
    LinearLayout layoutLicenseNum;
    @ViewInject(R.id.account_info_tvLicenseNum)
    EditText tvLicenseNum;
    @ViewInject(R.id.account_info_licenseValidity)
    LinearLayout layoutLicenseValidity;
    @ViewInject(R.id.account_info_tvLicenseValidity)
    Button tvLicenseValidity;//执照证件有效期
    @ViewInject(R.id.account_info_cbLicenseValidity)
    CheckBox cbLicenseValidity;
    @ViewInject(R.id.account_info_rgPublicAndPrivate)
    RadioGroup rgPublicAndPrivate;
    @ViewInject(R.id.account_info_rbPublic)
    RadioButton rbPublic;
    @ViewInject(R.id.account_info_rbPrivate)
    RadioButton rbPrivate;
    @ViewInject(R.id.account_info_licenseType)
    LinearLayout layoutLicenseType;
    @ViewInject(R.id.account_info_tvLicenseType)
    NiceSpinner tvLicenseType;
    @ViewInject(R.id.account_info_legalPerson)
    LinearLayout layoutLegalPerson;
    @ViewInject(R.id.account_info_legalPersonAccount)
    LinearLayout layoutLegalPersonAccount;
    @ViewInject(R.id.account_info_rgLegalPersonAccount)
    RadioGroup rgLegalPersonAccount;
    @ViewInject(R.id.account_info_rbIsLegalPersonAccount)
    RadioButton rbIsLegalPersonAccount;
    @ViewInject(R.id.account_info_rbNoLegalPersonAccount)
    RadioButton rbNoLegalPersonAccount;
    @ViewInject(R.id.account_info_etLegalPersonName)
    EditText etLegalPersonName;//法人姓名
    @ViewInject(R.id.account_info_etLegalPersonPhone)
    EditText etLegalPersonPhone;//法人手机号
    @ViewInject(R.id.account_info_tvMerchantIdType)
    TextView tvMerchantIdType;//证件类型
    @ViewInject(R.id.account_info_etLegalPersonIdNum)
    EditText etLegalPersonIdNum;//法人身份证号
    @ViewInject(R.id.account_info_tvLegalPersonIdNumValidity)
    Button tvLegalPersonIdNumValidity;//法人身份证号有效期
    @ViewInject(R.id.account_info_cbLegalPersonIdNumValidity)
    CheckBox cbLegalPersonIdNumValidity;
    @ViewInject(R.id.account_info_etContro_name)
    EditText etControName;//控制人姓名
    @ViewInject(R.id.account_info_tvContro_id_type)
    TextView tvControIdType;//控制人证件类型
    @ViewInject(R.id.account_info_etContro_id_no)
    EditText etControIdNo;//控制人证件号
    @ViewInject(R.id.account_info_tvContro_id_expire)
    Button tvControIdExpire;//证件有效期
    @ViewInject(R.id.account_info_cbContro_id_expire_long)
    CheckBox cbControIdExpireLong;//是否长期有效
    @ViewInject(R.id.account_info_settPerson)
    LinearLayout settPerson;
    @ViewInject(R.id.account_info_tvSettPersonIdNum)
    EditText etSettPersonIdNum;//结算人身份证号
    @ViewInject(R.id.account_info_tvSettPersonIdNumValidity)
    Button tvSettPersonIdNumValidity;//结算人身份证号有效期
    @ViewInject(R.id.account_info_cbSettPersonIdNumValidity)
    CheckBox cbSettPersonIdNumValidity;
    @ViewInject(R.id.account_info_etSettPersonAccountName)
    EditText etSettPersonAccountName;//结算人账户名
    @ViewInject(R.id.account_info_etSettPersonAccountNum)
    EditText etSettPersonAccountNum;//结算人账户开户号
    @ViewInject(R.id.account_info_etBankCardCell)
    EditText etBankCardCell;//银行卡预留手机号

    @ViewInject(R.id.account_info_tvOpeningBank)
    TextView tvOpeningBank;//银行总行
    @ViewInject(R.id.account_info_tvOpeningProvince)
    TextView tvOpeningProvince;
    @ViewInject(R.id.account_info_tvOpeningCity)
    TextView tvOpeningCity;
    @ViewInject(R.id.account_info_tvOpeningArea)
    TextView tvOpeningArea;
    @ViewInject(R.id.account_info_layoutOrgcod)
    LinearLayout layoutOrgcod;
    @ViewInject(R.id.account_info_etOrgcod)
    EditText etOrgcod;//组织机构代码号
    @ViewInject(R.id.account_info_layoutTaxcod)
    LinearLayout layoutTaxcod;
    @ViewInject(R.id.account_info_etTaxcod)
    EditText etTaxcod;//税务登记号

    @ViewInject(R.id.account_info_btNext)
    Button btNext;



    private UserBean userBean = MainActivity.userBean;
    private String MerName;//商户名称
    private int id;//商户id
    /**
     * 商户类型："1" 一级商户 "2" 二级商户
     */
    private String merchantType;
    private String netType;//入网类型：默认为企业
    private String cb_licenseValidity = "N";//执照证件到期日期，默认不选中值为"N",长期有效时为"Y"
    private String accountType = "1";//结算账户类型：对公 = 1，对私 = 2；
    private String licenseType = "SZHY";//入网证件类型,默认为三证合一
    private String isLegalPersonAccount = "1";//是否法人入账，法人入账 = 1，非法人入账 = 2；
    private String cb_legalPersonIdNumValidity = "N";//法人身份证有效日期，默认不选中值为"N",长期有效时为"Y"
    private String cb_ControIdExpireLong = "N";//控制人证件有效日期，默认不选中值为"N",长期有效时为"Y"
    private String cb_settPersonIdNumValidity = "N";//结算人身份证有效日期，默认不选中值为"N",长期有效时为"Y"

    private static final String format = "yyyy-MM-dd HH:mm";
    private String pickerStartDateTime,pickerEndDateTime;//日期选择控件的选择范围，起始日期和结束日期


    private List<MerchanIdTypeData> lsMerchanIdType = new ArrayList<>();
    MerchanIdTypeData merchanIdTypeData;//法人证件类型
    MerchanIdTypeData controIdTypeData;//控制人人证件类型
    private List<BackListData> lsBank = new ArrayList<>();
    BackListData bank;
    private List<RegionData> lsProvince = new ArrayList<RegionData>();
    RegionData province;
    private List<RegionData> lsCity = new ArrayList<RegionData>();
    RegionData city;
    private List<BackListData> lsBranch = new ArrayList<>();
    BackListData branch;

    private SpinerPopWindow mSpinerPopWindow;


    private int REQUEST_CODE = 1;
    public static final int RESULT_CODE = 1;

    BusDetailData bus;//商户信息对象

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = AccountInfoActivity.this;
        MerName = getIntent().getStringExtra("name");
        merchantType = getIntent().getStringExtra("merchantType");
        id = getIntent().getIntExtra("id",0);
        setTitle("商户结算信息");
        map = new HashMap<>();
        map.put("id",String.valueOf(id));
        //设置日期起始时间和结束日期
        //初始化起始日期时间（三月后日期）
        pickerStartDateTime = DateTimeUtil.getAMonthDateStr(3,format);
        pickerEndDateTime = DateTimeUtil.getYearDateStr(20,format);
        Log.e("起始日期：",pickerStartDateTime);
        Log.e("结束日期：",pickerEndDateTime);
        initView();

        initListener();

        //获取商户基础信息
        if(id!=0){
            getBusInfo();
        }

        //获取证件类型
        getMerchantIdTypeList();
    }

    private void initView(){
        //merchant_status状态为2即审核驳回时返回驳回原因
        if(bus!=null){
            if("2".equals(bus.getMerchant_status())){
                if(Utils.isNotEmpty(bus.getError_msg())){
                    tvErrorMsg.setVisibility(View.VISIBLE);
                    tvErrorMsg.setText(String.format(getResources().getString(R.string.failure_hints),bus.getError_msg()));
                }
            }
        }
        //结算入网类型
        setNetTypeData();
        //执照证件号码
        if(bus!=null){
            if(Utils.isNotEmpty(bus.getLicenseno())){
                tvLicenseNum.setText(bus.getLicenseno());
            }
        }
        //证件到期日期（默认显示为系统日期）
        if(bus!=null){
            String licensen_expireStr = bus.getLicensen_expire();
            if(Utils.isNotEmpty(licensen_expireStr)){
                Long licensen_expireLong = Long.parseLong(licensen_expireStr);
                String licensen_expire = DateTimeUtil.stampToFormatDate(licensen_expireLong,"yyyy-MM-dd");
                tvLicenseValidity.setText(licensen_expire);
            }else{
                tvLicenseValidity.setText(pickerStartDateTime.split(" ")[0]);
            }
        }else{
            tvLicenseValidity.setText(pickerStartDateTime.split(" ")[0]);
        }
        //是否长期有效
        if(bus!=null){
            if(Utils.isNotEmpty(bus.getLicensen_expire_long())){
                cb_licenseValidity = bus.getLicensen_expire_long();
            }
        }
        if("N".equals(cb_licenseValidity)){
            cbLicenseValidity.setChecked(false);
        }else{
            cbLicenseValidity.setChecked(true);
        }
        //结算账户类型（值默认为1对公选中状态）
        if(bus!=null){
            if(Utils.isNotEmpty(bus.getAccount_type())){
                accountType = bus.getAccount_type();
            }
        }
        if("1".equals(accountType)){
            rbPublic.setChecked(true);
            rbPrivate.setChecked(false);
        }else if("2".equals(accountType)){
            rbPublic.setChecked(true);
            rbPrivate.setChecked(false);
        }
        //入网证件类型
        setLicenseType();
        //是否法人入账（值默认为1法人入账为选中状态）
        if(bus!=null&&Utils.isNotEmpty(bus.getIs_liable_account())){
            isLegalPersonAccount = bus.getIs_liable_account();
        }
        if("1".equals(isLegalPersonAccount)){
            rbIsLegalPersonAccount.setChecked(true);
            rbNoLegalPersonAccount.setChecked(false);
        }else if("2".equals(isLegalPersonAccount)){
            rbIsLegalPersonAccount.setChecked(false);
            rbNoLegalPersonAccount.setChecked(true);
        }

        if(bus!=null){
            //法人姓名
            String legal_nameStr = bus.getLegal_name();
            if(Utils.isNotEmpty(legal_nameStr)){
                etLegalPersonName.setText(legal_nameStr);
            }
            //法人手机号
            String legal_phoneStr = bus.getLegal_phone();
            if(Utils.isNotEmpty(legal_phoneStr)){
                etLegalPersonPhone.setText(legal_phoneStr);
            }
            //证件类型
            String merchant_id_type_nameStr = bus.getMerchant_id_type_name();
            if(Utils.isNotEmpty(merchant_id_type_nameStr)){
                merchanIdTypeData = new MerchanIdTypeData();
                merchanIdTypeData.setCode(bus.getMerchant_id_type());
                merchanIdTypeData.setType(merchant_id_type_nameStr);
                tvMerchantIdType.setText(merchant_id_type_nameStr);
            }
            //证件号
            String merchant_id_noStr = bus.getMerchant_id_no();
            if(Utils.isNotEmpty(merchant_id_noStr)){
                etLegalPersonIdNum.setText(merchant_id_noStr);
            }
            //证件号有效日期
            String merchant_id_expireStr = bus.getMerchant_id_expire();
            if(Utils.isNotEmpty(merchant_id_expireStr)){
                Long merchant_id_expireLong = Long.parseLong(merchant_id_expireStr);
                String merchant_id_expire = DateTimeUtil.stampToFormatDate(merchant_id_expireLong,"yyyy-MM-dd");
                tvLegalPersonIdNumValidity.setText(merchant_id_expire);
            }else{
                tvLegalPersonIdNumValidity.setText(pickerStartDateTime.split(" ")[0]);
            }
            //是否长期有效
            if(Utils.isNotEmpty(bus.getMerchant_id_expire_long())){
                cb_legalPersonIdNumValidity = bus.getMerchant_id_expire_long();
            }
            if("N".equals(cb_legalPersonIdNumValidity)){
                cbLegalPersonIdNumValidity.setChecked(false);
            }else{
                cbLegalPersonIdNumValidity.setChecked(true);
            }
            //控制人姓名
            String contro_nameStr = bus.getContro_name();
            if(Utils.isNotEmpty(contro_nameStr)){
                etControName.setText(contro_nameStr);
            }
            //控制人证件类型
            String contro_id_type_nameStr = bus.getContro_id_type_name();
            if(Utils.isNotEmpty(contro_id_type_nameStr)){
                controIdTypeData = new MerchanIdTypeData();
                controIdTypeData.setCode(bus.getMerchant_id_type());
                controIdTypeData.setType(contro_id_type_nameStr);
                tvMerchantIdType.setText(contro_id_type_nameStr);
            }
            //证件号
            String contro_id_noStr = bus.getContro_id_no();
            if(Utils.isNotEmpty(contro_id_noStr)){
                etControIdNo.setText(contro_id_noStr);
            }
            //证件号有效期
            String contro_id_expireStr = bus.getContro_id_expire();
            if(Utils.isNotEmpty(contro_id_expireStr)){
                Long contro_id_expireLong = Long.parseLong(contro_id_expireStr);
                String contro_id_expire = DateTimeUtil.stampToFormatDate(contro_id_expireLong,"yyyy-MM-dd");
                tvControIdExpire.setText(contro_id_expire);
            }else{
                tvControIdExpire.setText(pickerStartDateTime.split(" ")[0]);
            }
            //是否长期有效
            if(Utils.isNotEmpty(bus.getContro_id_expire_long())){
                cb_ControIdExpireLong = bus.getContro_id_expire_long();
            }
            if("N".equals(cb_ControIdExpireLong)){
                cbControIdExpireLong.setChecked(false);
            }else{
                cbControIdExpireLong.setChecked(true);
            }
            //结算人身份证号
            String settle_id_noStr = bus.getSettle_id_no();
            if(Utils.isNotEmpty(settle_id_noStr)){
                etSettPersonIdNum.setText(settle_id_noStr);
            }
            //结算人身份证有效日期
            String settle_id_expireStr = bus.getSettle_id_expire();
            if(Utils.isNotEmpty(settle_id_expireStr)){
                Long settle_id_expireLong = Long.parseLong(settle_id_expireStr);
                String settle_id_expire = DateTimeUtil.stampToFormatDate(settle_id_expireLong,"yyyy-MM-dd");
                tvSettPersonIdNumValidity.setText(settle_id_expire);
            }else{
                tvSettPersonIdNumValidity.setText(pickerStartDateTime.split(" ")[0]);
            }
            //是否长期有效
            if(Utils.isNotEmpty(bus.getSettle_id_expire_long())){
                cb_settPersonIdNumValidity = bus.getSettle_id_expire_long();
            }
            if("N".equals(cb_settPersonIdNumValidity)){
                cbSettPersonIdNumValidity.setChecked(false);
            }else{
                cbSettPersonIdNumValidity.setChecked(true);
            }
            //结算人账户开户名
            String account_nameStr = bus.getAccount_name();
            if(Utils.isNotEmpty(account_nameStr)){
                etSettPersonAccountName.setText(account_nameStr);
            }
            //结算人账户开户号
            String account_noStr = bus.getAccount_no();
            if(Utils.isNotEmpty(account_noStr)){
                etSettPersonAccountNum.setText(account_noStr);
            }
            //银行预留手机号
            String account_phoneStr = bus.getAccount_phone();
            if(Utils.isNotEmpty(account_phoneStr)){
                etBankCardCell.setText(account_phoneStr);
            }
            //结算账户开户总行
            String bank_znameStr = bus.getBank_zname();
            if(Utils.isNotEmpty(bank_znameStr)){
                bank = new BackListData();
                bank.setId(Integer.parseInt(bus.getBank_zname_no()));
                bank.setBank_name(bank_znameStr);
                tvOpeningBank.setText(bank_znameStr);
            }
            //结算账户开户省份
            String bank_addres_pro_nameStr = bus.getBank_addres_pro_name();
            if(Utils.isNotEmpty(bank_addres_pro_nameStr)){
                province = new RegionData();
                province.setSid(Integer.parseInt(bus.getBank_addres_pro_no()));
                province.setFullname(bus.getBank_addres_pro_name());
                tvOpeningProvince.setText(bank_addres_pro_nameStr);
            }
            //结算账户开户城市
            String bank_addres_city_nameStr = bus.getBank_addres_city_name();
            if(Utils.isNotEmpty(bank_addres_city_nameStr)){
                city = new RegionData();
                city.setSid(Integer.parseInt(bus.getBank_addres_city_no()));
                city.setFullname(bus.getBank_addres_city_name());
                tvOpeningCity.setText(bank_addres_city_nameStr);
            }
            //结算账户开户支行
            String bank_nameStr = bus.getBank_name();
            if(Utils.isNotEmpty(bank_nameStr)){
                branch = new BackListData();
                branch.setBank_no(bus.getBank_no());
                branch.setBank_name(bus.getBank_name());
                tvOpeningArea.setText(bank_nameStr);
            }
            //组织机构代码号
            //入网证件类型：三证合一；营业执照
            if("YYZZ".equals(licenseType)){
                String orgcodStr = bus.getOrgcod();
                if(Utils.isNotEmpty(orgcodStr)){
                    etOrgcod.setText(orgcodStr);
                }
                //税务登记号
                String taxcodStr = bus.getTaxcod();
                if(Utils.isNotEmpty(taxcodStr)){
                    etTaxcod.setText(taxcodStr);
                }
            }


        }else{
            //法人身份证有效期（默认显示为系统日期）
            tvLegalPersonIdNumValidity.setText(pickerStartDateTime.split(" ")[0]);
            //结算人身份证有效期（默认显示为系统日期）
            tvSettPersonIdNumValidity.setText(pickerStartDateTime.split(" ")[0]);
            //实际控制人证件有效期（默认显示为系统日期）
            tvControIdExpire.setText(pickerStartDateTime.split(" ")[0]);
        }

        //以上控件值更改刷新View
        updateView();

    }



    private void initListener(){
        tvLicenseValidity.setOnClickListener(this);
        cbLicenseValidity.setOnCheckedChangeListener(this);
        rgPublicAndPrivate.setOnCheckedChangeListener(this);
        tvMerchantIdType.setOnClickListener(this);
        rgLegalPersonAccount.setOnCheckedChangeListener(this);
        tvLegalPersonIdNumValidity.setOnClickListener(this);
        tvSettPersonIdNumValidity.setOnClickListener(this);
        tvControIdType.setOnClickListener(this);//控制人证件类型
        tvControIdExpire.setOnClickListener(this);//控制人证件有效期
        cbLegalPersonIdNumValidity.setOnCheckedChangeListener(this);
        cbLegalPersonIdNumValidity.setOnCheckedChangeListener(this);
        cbControIdExpireLong.setOnCheckedChangeListener(this);//控制人证件是否长期有效
        tvOpeningBank.setOnClickListener(this);
        tvOpeningProvince.setOnClickListener(this);
        tvOpeningCity.setOnClickListener(this);
        tvOpeningArea.setOnClickListener(this);
        btNext.setOnClickListener(this);
    }

    private void updateView(){
        //入网类型为企业时
        if("QY".equals(netType)){
            layoutLicenseNum.setVisibility(View.VISIBLE);
            layoutLicenseValidity.setVisibility(View.VISIBLE);
            layoutLicenseType.setVisibility(View.VISIBLE);
            //结算账户为对公时
            if("1".equals(accountType)){
                //入网证件类型可选
                tvLicenseType.setClickable(true);
                //法人信息必填
                layoutLegalPerson.setVisibility(View.VISIBLE);
                //是否法人入账
                layoutLegalPersonAccount.setVisibility(View.GONE);
                //结算人信息隐藏
                settPerson.setVisibility(View.GONE);
            }else{
                //入网证件类型可选
                tvLicenseType.setClickable(true);
                //是否法人入账必选
                layoutLegalPersonAccount.setVisibility(View.VISIBLE);
                if("1".equals(isLegalPersonAccount)){
                    //法人信息不填
                    layoutLegalPerson.setVisibility(View.GONE);
                    //结算人信息必填
                    settPerson.setVisibility(View.VISIBLE);
                }else{
                    //法人信息必填
                    layoutLegalPerson.setVisibility(View.VISIBLE);
                    //结算人信息必填
                    settPerson.setVisibility(View.VISIBLE);
                }

            }

            rbPublic.setClickable(true);
            rbPrivate.setClickable(true);
            if("1".equals(accountType)){
                rbPublic.setChecked(true);
                rbPrivate.setChecked(false);
            }else if("2".equals(accountType)){
                rbPublic.setChecked(false);
                rbPrivate.setChecked(true);
            }

            //入网证件类型：三证合一；营业执照
            if("YYZZ".equals(licenseType)){
                //显示机构代码号和税务登记号
                layoutOrgcod.setVisibility(View.VISIBLE);
                layoutTaxcod.setVisibility(View.VISIBLE);
            }else{
                layoutOrgcod.setVisibility(View.GONE);
                layoutTaxcod.setVisibility(View.GONE);
            }

        }
        //入网类型为个体工商户时
        else if("GT".equals(netType)){

            layoutLicenseNum.setVisibility(View.VISIBLE);
            layoutLicenseValidity.setVisibility(View.VISIBLE);
            layoutLicenseType.setVisibility(View.VISIBLE);
            //结算账户为对公时
            if("1".equals(accountType)){
                //入网证件类型可选
                tvLicenseType.setClickable(true);
                //法人信息必填
                layoutLegalPerson.setVisibility(View.VISIBLE);
                //是否法人入账
                layoutLegalPersonAccount.setVisibility(View.GONE);
                //结算人信息隐藏
                settPerson.setVisibility(View.GONE);
            }else{
                //入网证件类型不可选
                tvLicenseType.setClickable(false);
                //是否法人入账必选
                layoutLegalPersonAccount.setVisibility(View.VISIBLE);
                if("1".equals(isLegalPersonAccount)){
                    //法人信息不填
                    layoutLegalPerson.setVisibility(View.GONE);
                    //结算人信息必填
                    settPerson.setVisibility(View.VISIBLE);
                }else{
                    //法人信息必填
                    layoutLegalPerson.setVisibility(View.VISIBLE);
                    //结算人信息必填
                    settPerson.setVisibility(View.VISIBLE);
                }

            }

            rbPublic.setClickable(true);
            rbPrivate.setClickable(true);
            if("1".equals(accountType)){
                rbPublic.setChecked(true);
                rbPrivate.setChecked(false);
            }else if("2".equals(accountType)){
                rbPublic.setChecked(false);
                rbPrivate.setChecked(true);
            }

            //入网证件类型：三证合一；营业执照
            if("YYZZ".equals(licenseType)){
                //显示机构代码号和税务登记号
                layoutOrgcod.setVisibility(View.VISIBLE);
                layoutTaxcod.setVisibility(View.VISIBLE);
            }else{
                layoutOrgcod.setVisibility(View.GONE);
                layoutTaxcod.setVisibility(View.GONE);
            }
        }
        //入网类型为小微商户时
        else {
            layoutLicenseNum.setVisibility(View.GONE);
            layoutLicenseValidity.setVisibility(View.GONE);
            rgPublicAndPrivate.setClickable(false);
            //结算账户类型（值默认为2对私选中状态，并且状态不可更改）
            accountType = "2";
            rbPublic.setClickable(false);
            rbPrivate.setClickable(false);
            if("1".equals(accountType)){
                rbPublic.setChecked(true);
                rbPrivate.setChecked(false);
            }else if("2".equals(accountType)){
                rbPublic.setChecked(false);
                rbPrivate.setChecked(true);
            }
            layoutLicenseType.setVisibility(View.GONE);
            layoutLegalPersonAccount.setVisibility(View.GONE);
            //法人信息不填
            layoutLegalPerson.setVisibility(View.GONE);
            //结算人信息必填
            settPerson.setVisibility(View.VISIBLE);
            //入网证件类型：三证合一；营业执照
            if("YYZZ".equals(licenseType)){
                //显示机构代码号和税务登记号
                layoutOrgcod.setVisibility(View.VISIBLE);
                layoutTaxcod.setVisibility(View.VISIBLE);
            }else{
                layoutOrgcod.setVisibility(View.GONE);
                layoutTaxcod.setVisibility(View.GONE);
            }

        }
    }

    private void setNetTypeData(){
        final List<String> list = new ArrayList<>();
        list.add(Constant.netTypeList[0]);
        list.add(Constant.netTypeList[1]);
        tvNetType.attachDataSource(list);
        if(bus!=null&&Utils.isNotEmpty(bus.getSettlement_mer_type())){

            netType = bus.getSettlement_mer_type();
            tvNetType.setSelectedIndex(Constant.getNetTypeIndex(netType));
//            tvNetType.setText(Constant.getNetTypeStr(netType));


        }else{
            //默认值为企业
            netType = Constant.getNetType(list.get(0));
        }


        tvNetType.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        netType = Constant.getNetType(list.get(position));
                        //以上控件值更改刷新View
                        updateView();
                        break;
                    case 1:
                        netType = Constant.getNetType(list.get(position));
                        //以上控件值更改刷新View
                        updateView();
                        break;
                    default:
                        break;
                }

            }
        });

    }

    private void setLicenseValidity(final int type){
        TimeSelector timeSelector = new TimeSelector(this, new TimeSelector.ResultHandler() {
            @Override
            public void handle(String time) {
//                        Toast.makeText(getApplicationContext(), time, Toast.LENGTH_LONG).show();
                if(type == 1){
                    tvLicenseValidity.setText(time.split(" ")[0]);
                }else if(type == 2){
                    tvLegalPersonIdNumValidity.setText(time.split(" ")[0]);
                }else if(type == 3){
                    tvSettPersonIdNumValidity.setText(time.split(" ")[0]);
                }else if(type == 4){
                    tvControIdExpire.setText(time.split(" ")[0]);
                }

            }
        },pickerStartDateTime,pickerEndDateTime);
        timeSelector.setMode(TimeSelector.MODE.YMD);//显示 年月日时分（默认）；
//                timeSelector.setMode(TimeSelector.MODE.YMD);//只显示 年月日
        timeSelector.setIsLoop(false);//不设置时为true，即循环显示
        timeSelector.show();
    }

    private void setLicenseType(){
        final List<String> list = new ArrayList<>();
        list.add(Constant.netLicenseTypeList[0]);
        list.add(Constant.netLicenseTypeList[1]);
        tvLicenseType.attachDataSource(list);
        if(bus!=null&&Utils.isNotEmpty(bus.getDocument_type())){
            licenseType = bus.getDocument_type();
            tvLicenseType.setSelectedIndex(Constant.getNetLicenseTypeIndex(licenseType));
        }else{
            //默认值为三证合一
            licenseType = Constant.getNetLicenseType(list.get(0));
        }
        tvLicenseType.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        licenseType = Constant.getNetLicenseType(list.get(position));
                        //以上控件值更改刷新View
                        updateView();
                        break;
                    case 1:
                        licenseType = Constant.getNetLicenseType(list.get(position));
                        //以上控件值更改刷新View
                        updateView();
                        break;
                    default:
                        break;
                }

            }
        });

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
                    userJSON.put("page", "2");
                    userJSON.put("id", String.valueOf(id));
                    String content = String.valueOf(userJSON);
                    Log.e("查询商户信息请求参数：", content);
                    String jsonStr = HttpURLConnectionUtil.doPos(url,content);
                    Log.e("查询商户信息返回字符串结果：", jsonStr);
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

    /** 获取证件类型 */
    private void getMerchantIdTypeList(){
        showWaitDialog();
        final String url = NitConfig.getMerchantIdTypeList;
        new Thread(){
            @Override
            public void run() {
                try {
                    // 拼装JSON数据，向服务端发起请求
                    JSONObject userJSON = new JSONObject();
                    String content = String.valueOf(userJSON);
                    Log.e("查询证件类型请求参数：", content);
                    String jsonStr = HttpURLConnectionUtil.doPos(url,content);
                    Log.e("查询证件类型返回字符串结果：", jsonStr);
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

    /**
     *  获取银行总行信息
     */
    private void  getBankList(){

        final String url = NitConfig.getBankList;
        new Thread(){
            @Override
            public void run() {
                try {
                    // 拼装JSON数据，向服务端发起请求
                    JSONObject userJSON = new JSONObject();
                    String content = String.valueOf(userJSON);
                    Log.e("查询银行总行请求参数：", content);
                    String jsonStr = HttpURLConnectionUtil.doPos(url,content);
                    Log.e("查询银行总行返回字符串结果：", jsonStr);
                    int msg = 2;
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

    /** 获取省份 */
    private void getProvince(){
        final String url = NitConfig.getProvince;
        new Thread(){
            @Override
            public void run() {
                try {
                    // 拼装JSON数据，向服务端发起请求
                    JSONObject userJSON = new JSONObject();
                    userJSON.put("province_code", "3229");
                    String content = String.valueOf(userJSON);
                    Log.e("查询省份请求参数：", content);
                    String jsonStr = HttpURLConnectionUtil.doPos(url,content);
                    Log.e("查询省份返回字符串结果：", jsonStr);
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



    /** 获取城市 */
    private void getCityList(final int sid){
        showWaitDialog();
        final String url = NitConfig.getCityList;
        new Thread(){
            @Override
            public void run() {
                try {
                    // 拼装JSON数据，向服务端发起请求
                    JSONObject userJSON = new JSONObject();
                    userJSON.put("id", String.valueOf(sid));
                    String content = String.valueOf(userJSON);
                    Log.e("查询城市请求参数：", content);
                    String jsonStr = HttpURLConnectionUtil.doPos(url,content);
                    Log.e("查询城市返回字符串结果：", jsonStr);
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

    /** 获取支行 */
    private void getBranchList(final int id,final int city_id){
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
                    String content = String.valueOf(userJSON);
                    Log.e("查询支行请求参数：", content);
                    String jsonStr = HttpURLConnectionUtil.doPos(url,content);
                    Log.e("查询支行返回字符串结果：", jsonStr);
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
     * 提交资料
     */
    private void subAccountInfo(final String licenseNum, final String legalPersonName, final String legalPhone,final String legalPersonIdNum,
                                final String controName,final String controIdNo,
                                final String settPersonIdNum,final String settPersonAccountName, final String settPersonAccountNum,
                                final String bankCardCell,final String orgcod,final String taxcod){

        showWaitDialog();
        //执照证件到期日期
        final String licenseValidity = tvLicenseValidity.getText().toString();
        //法人身份证号有效期
        final String legalPersonIdNumValidity = tvLegalPersonIdNumValidity.getText().toString();
        //控制人证件号有效期
        final String controIdExpire = tvControIdExpire.getText().toString();
        //结算人身份证号有效期
        final String settPersonIdNumValidity = tvSettPersonIdNumValidity.getText().toString();

        final String url = NitConfig.subBusAccountInfo;
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
                    userJSON.put("settlement_mer_type", netType);//入网类型
                    if("QY".equals(netType) || "GT".equals(netType)){
                        userJSON.put("licenseno", licenseNum);//执照号码
                        String licensen_expire = DateToTimeStamp.getStartTimeStampTo(licenseValidity);
                        userJSON.put("licensen_expire", licensen_expire);//执照到期日期（时间戳）
                        userJSON.put("licensen_expire_long", cb_licenseValidity);//执照日期是否长期有效
                    }
                    userJSON.put("account_type", accountType);//结算账户类型：对公，对私

                    if("QY".equals(netType) || "GT".equals(netType)){
                        userJSON.put("document_type", licenseType);//入网证件类型
                    }
                    //结算类型对公
                    if("1".equals(accountType)){
                        userJSON.put("legal_name", legalPersonName);//法人名称
                        userJSON.put("legal_phone", legalPhone);//法人手机号
                        userJSON.put("merchant_id_type", merchanIdTypeData.getCode());//证件类型
                        userJSON.put("merchant_id_no", legalPersonIdNum);//证件号
                        String merchant_id_expire = DateToTimeStamp.getStartTimeStampTo(legalPersonIdNumValidity);
                        userJSON.put("merchant_id_expire", merchant_id_expire);//证件号有效日期（时间戳）
                        userJSON.put("merchant_id_expire_long", cb_legalPersonIdNumValidity);//法人身份证号是否长期有效

                        userJSON.put("contro_name", controName);//控制人名称
                        userJSON.put("contro_id_type", controIdTypeData.getCode());//控制人证件类型
                        userJSON.put("contro_id_no", controIdNo);//控制人证件号
                        String contro_id_expire = DateToTimeStamp.getStartTimeStampTo(controIdExpire);
                        userJSON.put("contro_id_expire", contro_id_expire);//控制人证件号有效日期（时间戳）
                        userJSON.put("contro_id_expire_long", cb_ControIdExpireLong);//控制人身份证号是否长期有效

                    }else{
                        userJSON.put("is_liable_account", isLegalPersonAccount);//是否法人入账
                        //法人入账
                        if("1".equals(isLegalPersonAccount)){

                            userJSON.put("contro_name", controName);//控制人名称
                            userJSON.put("contro_id_type", controIdTypeData.getCode());//控制人证件类型
                            userJSON.put("contro_id_no", controIdNo);//控制人证件号
                            String contro_id_expire = DateToTimeStamp.getStartTimeStampTo(controIdExpire);
                            userJSON.put("contro_id_expire", contro_id_expire);//控制人证件号有效日期（时间戳）
                            userJSON.put("contro_id_expire_long", cb_ControIdExpireLong);//控制人身份证号是否长期有效

                            userJSON.put("settle_id_no", settPersonIdNum);//结算人身份证号
                            String settle_id_expire = DateToTimeStamp.getStartTimeStampTo(settPersonIdNumValidity);
                            userJSON.put("settle_id_expire", settle_id_expire);//结算人身份证号有效日期（时间戳）
                            userJSON.put("settle_id_expire_long", cb_settPersonIdNumValidity);//结算人身份证号是否长期有效
                        }else{
                            //非法人入账
                            userJSON.put("legal_name", legalPersonName);//法人名称
                            userJSON.put("legal_phone", legalPhone);//法人手机号
                            userJSON.put("merchant_id_type", merchanIdTypeData.getCode());//证件类型
                            userJSON.put("merchant_id_no", legalPersonIdNum);//证件号
                            String merchant_id_expire = DateToTimeStamp.getStartTimeStampTo(legalPersonIdNumValidity);
                            userJSON.put("merchant_id_expire", merchant_id_expire);//证件号有效日期（时间戳）
                            userJSON.put("merchant_id_expire_long", cb_legalPersonIdNumValidity);//法人身份证号是否长期有效

                            userJSON.put("contro_name", controName);//控制人名称
                            userJSON.put("contro_id_type", controIdTypeData.getCode());//控制人证件类型
                            userJSON.put("contro_id_no", controIdNo);//控制人证件号
                            String contro_id_expire = DateToTimeStamp.getStartTimeStampTo(controIdExpire);
                            userJSON.put("contro_id_expire", contro_id_expire);//控制人证件号有效日期（时间戳）
                            userJSON.put("contro_id_expire_long", cb_ControIdExpireLong);//控制人身份证号是否长期有效

                            userJSON.put("settle_id_no", settPersonIdNum);//结算人身份证号
                            String settle_id_expire = DateToTimeStamp.getStartTimeStampTo(settPersonIdNumValidity);
                            userJSON.put("settle_id_expire", settle_id_expire);//结算人身份证号有效日期（时间戳）
                            userJSON.put("settle_id_expire_long", cb_settPersonIdNumValidity);//结算人身份证号是否长期有效
                        }

                    }

                    userJSON.put("account_name", settPersonAccountName);//结算人账户开户名
                    userJSON.put("account_no", settPersonAccountNum);//结算人账户开户号
                    userJSON.put("account_phone", bankCardCell);//
                    userJSON.put("bank_zname_no", bank.getId());//
                    userJSON.put("bank_addres_pro_no", province.getSid());//
                    userJSON.put("bank_addres_city_no", city.getSid());//
                    if(branch!=null){
                        userJSON.put("bank_no", branch.getBank_no());
                    }

                    //入网证件类型：三证合一；营业执照
                    if("YYZZ".equals(licenseType)){
                        //显示机构代码号和税务登记号
                        userJSON.put("orgcod", orgcod);//组织机构代码号
                        userJSON.put("taxcod", taxcod);//税务登记号
                    }



                    String content = String.valueOf(userJSON);
                    Log.e("提交第二页请求参数：", content);
                    String jsonStr = HttpURLConnectionUtil.doPos(url,content);
                    Log.e("提交第二页返回字符串结果：", jsonStr);
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
                    String merchanIdTypeJson = (String) msg.obj;
                    merchanIdTypeJson(merchanIdTypeJson);
                    //默认获取银行总行集合
                    getBankList();
                    break;
                case 2:
                    String bankListJson = (String) msg.obj;
                    bankListJson(bankListJson);
                    //默认获取银行开户省份数据
                    getProvince();
                    break;
                case 3:
                    String provinceJson = (String) msg.obj;
                    provinceJson(provinceJson);
                    hideWaitDialog();
                    break;
                case 4:
                    String cityJson = (String) msg.obj;
                    cityJson(cityJson);
                    hideWaitDialog();
                    break;
                case 5:
                    String branchJson = (String) msg.obj;
                    branchJson(branchJson);
                    hideWaitDialog();
                    break;
                case 6:
                    String subAccountInfoResultJson = (String) msg.obj;
                    subAccountInfoResultJson(subAccountInfoResultJson);
                    hideWaitDialog();
                    break;
                case 8:
                    String busInfo = (String) msg.obj;
                    busInfo(busInfo);
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
                default:
                    break;
            }
        }
    };

    private void merchanIdTypeJson(String json){
        try {
            JSONObject job = new JSONObject(json);
            String code = job.getString("code");
            String msg = job.getString("msg");
            if(NetworkUtils.RESULT_CODE.equals(code)){
                String subCode = job.getString("subCode");
                String subMsg = job.getString("subMsg");
                if(NetworkUtils.RESULT_SUBCODE.equals(subCode)){
                    String data = job.getString("data");
                    lsMerchanIdType.clear();
                    Gson gjson1  =  GsonUtils.getGson();
                    lsMerchanIdType = gjson1.fromJson(data, new TypeToken<List<MerchanIdTypeData>>() {  }.getType());
                    Log.e("证件类型数据",lsMerchanIdType.size()+"");
                }else{
                    if(Utils.isNotEmpty(msg)){
                        ToastUtil.showText(context,subMsg,1);
                    }else{
                        ToastUtil.showText(context,"证件类型数据读取失败！",1);
                    }
                }
            }else{
                if(Utils.isNotEmpty(msg)){
                    ToastUtil.showText(context,msg,1);
                }else{
                    ToastUtil.showText(context,"服务异常！",1);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void bankListJson(String json){
        try {
            JSONObject job = new JSONObject(json);
            int status = job.getInt("status");
            String message = job.getString("message");
            if(status == 200){
                String dataJson = job.getString("data");
                JSONObject obj = new JSONObject(dataJson);
                String bankList = obj.getString("bankList");
                lsBank.clear();
                Gson gjson1  =  GsonUtils.getGson();
                lsBank = gjson1.fromJson(bankList, new TypeToken<List<BackListData>>() {  }.getType());
                Log.e("银行总行数据",lsBank.size()+"");
            }else{
                ToastUtil.showText(context,message,1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void provinceJson(String json){
        try {
            JSONObject job = new JSONObject(json);
            int status = job.getInt("status");
            String message = job.getString("message");
            if(status == 200){
                String dataJson = job.getString("data");
                JSONObject obj = new JSONObject(dataJson);
                String provinceList = obj.getString("provinceList");
                lsProvince.clear();
                Gson gjson1  =  GsonUtils.getGson();
                lsProvince=gjson1.fromJson(provinceList, new TypeToken<List<RegionData>>() {  }.getType());
                Log.e("省份数据",lsProvince.size()+"");
            }else{
                ToastUtil.showText(context,message,1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void cityJson(String json){

        try {
            JSONObject job = new JSONObject(json);
            int status = job.getInt("status");
            String message = job.getString("message");
            if(status == 200){
                String dataJson = job.getString("data");
                JSONObject obj = new JSONObject(dataJson);
                String provinceList = obj.getString("provinceList");
                lsCity.clear();
                Gson gjson1  =  GsonUtils.getGson();
                lsCity=gjson1.fromJson(provinceList, new TypeToken<List<RegionData>>() {  }.getType());

                Log.e("市数据",lsCity.size()+"");
                SpinnerProvinceAdapter adapter = new SpinnerProvinceAdapter(this,lsCity);
                mSpinerPopWindow = new SpinerPopWindow(this,adapter,cityItemClickListener);
                mSpinerPopWindow.setOnDismissListener(dismissListener);
                mSpinerPopWindow.setWidth(tvOpeningCity.getWidth());
                mSpinerPopWindow.showAsDropDown(tvOpeningCity);
            }else{
                ToastUtil.showText(context,message,1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void branchJson(String json){

        try {
            JSONObject job = new JSONObject(json);
            int status = job.getInt("status");
            String message = job.getString("message");
            if(status == 200){
                String dataJson = job.getString("data");
                JSONObject obj = new JSONObject(dataJson);
                String bankList = obj.getString("bankList");
                lsBranch.clear();
                Gson gjson1  =  GsonUtils.getGson();
                lsBranch = gjson1.fromJson(bankList, new TypeToken<List<BackListData>>() {  }.getType());
                Log.e("银行支行数据",lsBranch.size()+"");

                SpinnerBankAdapter adapter = new SpinnerBankAdapter(this,lsBranch);
                mSpinerPopWindow = new SpinerPopWindow(this,adapter,branchItemClickListener);
                mSpinerPopWindow.setOnDismissListener(dismissListener);

                mSpinerPopWindow.setWidth(tvOpeningArea.getWidth());
//                mSpinerPopWindow.showDown(tvOpeningArea);
                mSpinerPopWindow.showUp(tvOpeningCity);

            }else{
                ToastUtil.showText(context,message,1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void subAccountInfoResultJson(String json){
    //{"code":"000000","data":{"id":288,"message":"第二页添加成功","status":200},"timestamp":"1539743365325"}
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
                    int id = dataJob.getInt("id");
                    Intent intent = new Intent();
                    intent.putExtra("name",MerName);
                    intent.putExtra("merchantType",merchantType);
                    intent.putExtra("id",id);
                    intent.putExtra("netType",netType);//入网类型
                    intent.putExtra("accountType",accountType);//结算账户类型：对公对私
                    intent.putExtra("licenseType",licenseType);//入网证件类型：三证合一，营业执照
                    intent.putExtra("isLegalPersonAccount",isLegalPersonAccount);//是否法人入账
                    intent.setClass(this,AptitudeRateActivity.class);
                    startActivity(intent);
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

    private void busInfo(String json){

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
                        Gson gjson  =  GsonUtils.getGson();
                        bus = gjson.fromJson(agentMap, BusDetailData.class);
                        initView();
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

    /**
     * 监听popupwindow取消
     */
    private PopupWindow.OnDismissListener dismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {

        }
    };

    /**
     * popupwindow显示的ListView的item点击事件
     */
    private AdapterView.OnItemClickListener merTypeItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mSpinerPopWindow.dismiss();
            merchanIdTypeData = lsMerchanIdType.get(position);
            tvMerchantIdType.setText(merchanIdTypeData.getType());
        }
    };

    /**
     * popupwindow显示的ListView的item点击事件
     */
    private AdapterView.OnItemClickListener controIdTypeItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mSpinerPopWindow.dismiss();
            controIdTypeData = lsMerchanIdType.get(position);
            tvControIdType.setText(controIdTypeData.getType());
        }
    };

    /**
     * popupwindow显示的ListView的item点击事件
     */
    private AdapterView.OnItemClickListener bankItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mSpinerPopWindow.dismiss();
            bank = lsBank.get(position);
            tvOpeningBank.setText(bank.getBank_name());
        }
    };
    /**
     * popupwindow显示的ListView的item点击事件
     */
    private AdapterView.OnItemClickListener provinceItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mSpinerPopWindow.dismiss();
            province = lsProvince.get(position);
            tvOpeningProvince.setText(province.getFullname());
            //清空城市和支行信息
            city = null;
            branch = null;
            tvOpeningCity.setText("");
            tvOpeningArea.setText("");
        }
    };
    /**
     * popupwindow显示的ListView的item点击事件
     */
    private AdapterView.OnItemClickListener cityItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mSpinerPopWindow.dismiss();
            city = lsCity.get(position);
            tvOpeningCity.setText(city.getFullname());
            //清空支行信息
            branch = null;
            tvOpeningArea.setText("");
        }
    };

    /**
     * popupwindow显示的ListView的item点击事件
     */
    private AdapterView.OnItemClickListener branchItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mSpinerPopWindow.dismiss();
            branch = lsBranch.get(position);
            tvOpeningArea.setText(branch.getBank_name());
        }
    };



    /**
     * 本地验证
     */
    private void subTextVerification(){
        String licenseNum = tvLicenseNum.getText().toString().trim();
        String legalPersonName = etLegalPersonName.getText().toString().trim();
        String legalPhone = etLegalPersonPhone.getText().toString().trim();
        String legalPersonIdNum = etLegalPersonIdNum.getText().toString().trim();
        String controName = etControName.getText().toString().trim();
        String controIdNo = etControIdNo.getText().toString().trim();
        String settPersonIdNum = etSettPersonIdNum.getText().toString().trim();
        String settPersonAccountName = etSettPersonAccountName.getText().toString().trim();
        String settPersonAccountNum = etSettPersonAccountNum.getText().toString().trim();
        String bankCardCell = etBankCardCell.getText().toString().trim();
        String orgcod = etOrgcod.getText().toString().trim();
        String taxcod = etTaxcod.getText().toString().trim();

        //入网类型为企业时或者个体工商户时
        if("QY".equals(netType) || "GT".equals(netType)){
            if(Utils.isEmpty(licenseNum)){
                ToastUtil.showText(context,"执照证件号码不能为空！",1);
                return;
            }
            //92422801MA4BW3A356
            if(!Utils.isLicenseNum(licenseNum)){
                ToastUtil.showText(context,"请输入正确的执照证件号码！",1);
                return;
            }



            //结算账户为对公时
            if("1".equals(accountType)){

                if(Utils.isEmpty(legalPersonName)){
                    ToastUtil.showText(context,"法人姓名不能为空！",1);
                    return;
                }
                if(legalPersonName.length()<=1){
                    ToastUtil.showText(context,"请输入正确的法人姓名！",1);
                    return;
                }

                if(Utils.isEmpty(legalPhone)){
                    ToastUtil.showText(context,"法人手机号不能为空！",1);
                    return;
                }
                if(!Utils.isPhoneNumAndTel(legalPhone)){
                    ToastUtil.showText(context,"请填写正确的法人手机号！",1);
                    return;
                }

                if(merchanIdTypeData == null){
                    ToastUtil.showText(context,"请选择法人证件类型！",1);
                    return;
                }


                if(Utils.isEmpty(legalPersonIdNum)){
                    ToastUtil.showText(context,"法人证件号不能为空！",1);
                    return;
                }

                if("01".equals(merchanIdTypeData.getCode())){
                    if(!IdcardUtils.validateCard(legalPersonIdNum)){
                        ToastUtil.showText(context,"请检查身份证号是否正确！",1);
                        return;
                    }
                }

                if(Utils.isEmpty(controName)){
                    ToastUtil.showText(context,"控制人姓名不能为空！",1);
                    return;
                }
                if(controName.length()<=1){
                    ToastUtil.showText(context,"请输入正确的控制人姓名！",1);
                    return;
                }
                if(controIdTypeData == null){
                    ToastUtil.showText(context,"请选择控制人证件类型！",1);
                    return;
                }
                if(Utils.isEmpty(controIdNo)){
                    ToastUtil.showText(context,"控制人证件号不能为空！",1);
                    return;
                }

                if("01".equals(controIdTypeData.getCode())){
                    if(!IdcardUtils.validateCard(controIdNo)){
                        ToastUtil.showText(context,"请检查身份证号是否正确！",1);
                        return;
                    }
                }

                if(Utils.isEmpty(settPersonAccountName)){
                    ToastUtil.showText(context,"结算人账户开户名不能为空！",1);
                    return;
                }

                if(Utils.isEmpty(settPersonAccountNum)){
                    ToastUtil.showText(context,"结算人账户开户号不能为空！",1);
                    return;
                }
//                if(Utils.isEmpty(bankCardCell)){
//                    ToastUtil.showText(context,"银行卡预留手机号不能为空！",1);
//                    return;
//                }
//                if(!Utils.isPhoneNumAndTel(bankCardCell)){
//                    ToastUtil.showText(context,"请填写正确的手机号！",1);
//                    return;
//                }
                if(bank == null){
                    ToastUtil.showText(context,"请选择账户开户总行！",1);
                    return;
                }
                if(province == null){
                    ToastUtil.showText(context,"请选择账户开户省！",1);
                    return;
                }

                if(city == null){
                    ToastUtil.showText(context,"请选择账户开户城市！",1);
                    return;
                }

                if(branch == null){
                    ToastUtil.showText(context,"请选择账户开户支行！",1);
                    return;
                }


            }else{
                //结算账户对私，法人入账时
                if("1".equals(isLegalPersonAccount)){

                    if(Utils.isEmpty(controName)){
                        ToastUtil.showText(context,"控制人姓名不能为空！",1);
                        return;
                    }
                    if(controName.length()<=1){
                        ToastUtil.showText(context,"请输入正确的控制人姓名！",1);
                        return;
                    }
                    if(controIdTypeData == null){
                        ToastUtil.showText(context,"请选择控制人证件类型！",1);
                        return;
                    }
                    if(Utils.isEmpty(controIdNo)){
                        ToastUtil.showText(context,"控制人证件号不能为空！",1);
                        return;
                    }

                    if("01".equals(controIdTypeData.getCode())){
                        if(!IdcardUtils.validateCard(controIdNo)){
                            ToastUtil.showText(context,"请检查身份证号是否正确！",1);
                            return;
                        }
                    }

                    if(Utils.isEmpty(settPersonIdNum)){
                        ToastUtil.showText(context,"结算人身份证号不能为空！",1);
                        return;
                    }
                    if(!IdcardUtils.validateCard(settPersonIdNum)){
                        ToastUtil.showText(context,"请检查身份证号是否正确！",1);
                        return;
                    }

                    if(Utils.isEmpty(settPersonAccountName)){
                        ToastUtil.showText(context,"结算人账户开户名不能为空！",1);
                        return;
                    }

                    if(Utils.isEmpty(settPersonAccountNum)){
                        ToastUtil.showText(context,"结算人账户开户号不能为空！",1);
                        return;
                    }
//                    if(Utils.isEmpty(bankCardCell)){
//                        ToastUtil.showText(context,"银行卡预留手机号不能为空！",1);
//                        return;
//                    }
//                    if(!Utils.isPhoneNumAndTel(bankCardCell)){
//                        ToastUtil.showText(context,"请填写正确的手机号！",1);
//                        return;
//                    }
                    if(bank == null){
                        ToastUtil.showText(context,"请选择账户开户总行！",1);
                        return;
                    }
                    if(province == null){
                        ToastUtil.showText(context,"请选择账户开户省！",1);
                        return;
                    }

                    if(city == null){
                        ToastUtil.showText(context,"请选择账户开户城市！",1);
                        return;
                    }

                    if(branch == null){
                        ToastUtil.showText(context,"请选择账户开户支行！",1);
                        return;
                    }


                }else {
                    //结算账户对私，非法人入账时
                    if(Utils.isEmpty(legalPersonName)){
                        ToastUtil.showText(context,"法人姓名不能为空！",1);
                        return;
                    }

                    if(Utils.isEmpty(legalPhone)){
                        ToastUtil.showText(context,"法人手机号不能为空！",1);
                        return;
                    }
                    if(!Utils.isPhoneNumAndTel(legalPhone)){
                        ToastUtil.showText(context,"请填写正确的法人手机号！",1);
                        return;
                    }

                    if(legalPersonName.length()<=1){
                        ToastUtil.showText(context,"请输入正确的法人姓名！",1);
                        return;
                    }

                    if(merchanIdTypeData == null){
                        ToastUtil.showText(context,"请选择法人证件类型！",1);
                        return;
                    }


                    if(Utils.isEmpty(legalPersonIdNum)){
                        ToastUtil.showText(context,"法人证件号不能为空！",1);
                        return;
                    }

                    if("01".equals(merchanIdTypeData.getCode())){
                        if(!IdcardUtils.validateCard(legalPersonIdNum)){
                            ToastUtil.showText(context,"请检查身份证号是否正确！",1);
                            return;
                        }
                    }

                    if(Utils.isEmpty(controName)){
                        ToastUtil.showText(context,"控制人姓名不能为空！",1);
                        return;
                    }
                    if(controName.length()<=1){
                        ToastUtil.showText(context,"请输入正确的控制人姓名！",1);
                        return;
                    }
                    if(controIdTypeData == null){
                        ToastUtil.showText(context,"请选择控制人证件类型！",1);
                        return;
                    }
                    if(Utils.isEmpty(controIdNo)){
                        ToastUtil.showText(context,"控制人证件号不能为空！",1);
                        return;
                    }

                    if("01".equals(controIdTypeData.getCode())){
                        if(!IdcardUtils.validateCard(controIdNo)){
                            ToastUtil.showText(context,"请检查身份证号是否正确！",1);
                            return;
                        }
                    }

                    if(Utils.isEmpty(settPersonIdNum)){
                        ToastUtil.showText(context,"结算人身份证号不能为空！",1);
                        return;
                    }
                    if(!IdcardUtils.validateCard(settPersonIdNum)){
                        ToastUtil.showText(context,"请检查身份证号是否正确！",1);
                        return;
                    }

                    if(Utils.isEmpty(settPersonAccountName)){
                        ToastUtil.showText(context,"结算人账户开户名不能为空！",1);
                        return;
                    }

                    if(Utils.isEmpty(settPersonAccountNum)){
                        ToastUtil.showText(context,"结算人账户开户号不能为空！",1);
                        return;
                    }
//                    if(Utils.isEmpty(bankCardCell)){
//                        ToastUtil.showText(context,"银行卡预留手机号不能为空！",1);
//                        return;
//                    }
//                    if(!Utils.isPhoneNumAndTel(bankCardCell)){
//                        ToastUtil.showText(context,"请填写正确的手机号！",1);
//                        return;
//                    }
                    if(bank == null){
                        ToastUtil.showText(context,"请选择账户开户总行！",1);
                        return;
                    }
                    if(province == null){
                        ToastUtil.showText(context,"请选择账户开户省！",1);
                        return;
                    }

                    if(city == null){
                        ToastUtil.showText(context,"请选择账户开户城市！",1);
                        return;
                    }

                    if(branch == null){
                        ToastUtil.showText(context,"请选择账户开户支行！",1);
                        return;
                    }

                }
            }

        }
        //入网类型为小微商
        else{

            if(Utils.isEmpty(settPersonIdNum)){
                ToastUtil.showText(context,"结算人身份证号不能为空！",1);
                return;
            }
            if(!IdcardUtils.validateCard(settPersonIdNum)){
                ToastUtil.showText(context,"请检查身份证号是否正确！",1);
                return;
            }
            if(Utils.isEmpty(settPersonAccountName)){
                ToastUtil.showText(context,"结算人账户开户名不能为空！",1);
                return;
            }

            if(Utils.isEmpty(settPersonAccountNum)){
                ToastUtil.showText(context,"结算人账户开户号不能为空！",1);
                return;
            }
//            if(Utils.isEmpty(bankCardCell)){
//                ToastUtil.showText(context,"银行卡预留手机号不能为空！",1);
//                return;
//            }
//            if(!Utils.isPhoneNumAndTel(bankCardCell)){
//                ToastUtil.showText(context,"请填写正确的手机号！",1);
//                return;
//            }
            if(bank == null){
                ToastUtil.showText(context,"请选择账户开户总行！",1);
                return;
            }
            if(province == null){
                ToastUtil.showText(context,"请选择账户开户省！",1);
                return;
            }

            if(city == null){
                ToastUtil.showText(context,"请选择账户开户城市！",1);
                return;
            }

            if(branch == null){
                ToastUtil.showText(context,"请选择账户开户支行！",1);
                return;
            }
        }

        //入网证件类型：三证合一；营业执照
        if("YYZZ".equals(licenseType)){
            //显示机构代码号和税务登记号
            if(Utils.isEmpty(orgcod)){
                ToastUtil.showText(context,"组织机构代码号不能为空！",1);
                return;
            }
            if(Utils.isEmpty(taxcod)){
                ToastUtil.showText(context,"税务登记号不能为空！",1);
                return;
            }
        }




        //提交资料
        subAccountInfo(licenseNum,legalPersonName,legalPhone,legalPersonIdNum,
                controName,controIdNo,
                settPersonIdNum,settPersonAccountName,settPersonAccountNum,bankCardCell,orgcod,taxcod);
    }


    @Override
    public void onClick(View v) {
        int type = 0;
        BaseAdapter adapter;
        switch (v.getId()){
            case R.id.account_info_tvMerchantIdType://请选择法人证件类型
                adapter = new SpinnerMerchantIdTypeAdapter(this,lsMerchanIdType);
                mSpinerPopWindow = new SpinerPopWindow(this,adapter,merTypeItemClickListener);
                mSpinerPopWindow.setOnDismissListener(dismissListener);
                mSpinerPopWindow.setWidth(tvMerchantIdType.getWidth());
                mSpinerPopWindow.showAsDropDown(tvMerchantIdType);
                break;
            case R.id.account_info_tvContro_id_type://控制人证件类型
                adapter = new SpinnerMerchantIdTypeAdapter(this,lsMerchanIdType);
                mSpinerPopWindow = new SpinerPopWindow(this,adapter,controIdTypeItemClickListener);
                mSpinerPopWindow.setOnDismissListener(dismissListener);
                mSpinerPopWindow.setWidth(tvControIdType.getWidth());
                mSpinerPopWindow.showAsDropDown(tvControIdType);
                break;
            case R.id.account_info_tvLicenseValidity://执照证件到期日期
                type = 1;
                setLicenseValidity(type);
                break;
            case R.id.account_info_tvLegalPersonIdNumValidity://法人身份证有效期
                type = 2;
                setLicenseValidity(type);
                break;
            case R.id.account_info_tvSettPersonIdNumValidity://结算人身份证有效期
                type = 3;
                setLicenseValidity(type);
                break;
            case R.id.account_info_tvContro_id_expire://控制人证件有效期
                type = 4;
                setLicenseValidity(type);
                break;
            case R.id.account_info_tvOpeningBank://请选择银行
                adapter = new SpinnerBankAdapter(this,lsBank);
                mSpinerPopWindow = new SpinerPopWindow(this,adapter,bankItemClickListener);
                mSpinerPopWindow.setOnDismissListener(dismissListener);
                mSpinerPopWindow.setWidth(tvOpeningBank.getWidth());
                mSpinerPopWindow.showAsDropDown(tvOpeningBank);
                break;
            case R.id.account_info_tvOpeningProvince://请选择省份
                adapter = new SpinnerProvinceAdapter(this,lsProvince);
                mSpinerPopWindow = new SpinerPopWindow(this,adapter,provinceItemClickListener);
                mSpinerPopWindow.setOnDismissListener(dismissListener);
                mSpinerPopWindow.setWidth(tvOpeningProvince.getWidth());
                mSpinerPopWindow.showAsDropDown(tvOpeningProvince);
                break;
            case R.id.account_info_tvOpeningCity://请选择城市
                if(province!=null){
                    int sid = province.getSid();
                    getCityList(sid);
                }else{
                    ToastUtil.showText(context,"请先选择省份！",1);
                }
                break;
            case R.id.account_info_tvOpeningArea://请选择支行
                if(bank == null){
                    ToastUtil.showText(context,"请先选择总行！",1);
                    return;
                }
                if(city == null){
                    ToastUtil.showText(context,"请先选择城市！",1);
                    return;
                }

                Intent intent = new Intent();
                intent.setClass(this,SearchBankActivity.class);
                intent.putExtra("id",bank.getId());
                intent.putExtra("provice_id",province.getSid());
                intent.putExtra("city_id",city.getSid());
                startActivityForResult(intent,REQUEST_CODE);


//                int id = bank.getId();
//                int city_id = city.getSid();
//                getBranchList(id,city_id);
                break;
            case R.id.account_info_btNext://下一步/提交
                if(Utils.isFastClick(Constant.INTERVAL1000)){
                    return;
                }
                subTextVerification();
                break;
            default:
                break;

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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView.getId() == R.id.account_info_cbLicenseValidity){
            if(isChecked){
                Log.e("执照证件有效期","选中");
                cb_licenseValidity = "Y";
                tvLicenseValidity.setClickable(false);
                tvLicenseValidity.setText("");
            }else{
                cb_licenseValidity = "N";
                Log.e("执照证件有效期","未选中");
                tvLicenseValidity.setClickable(true);
                if(bus!=null){
                    String licensen_expireStr = bus.getLicensen_expire();
                    if(Utils.isNotEmpty(licensen_expireStr)){
                        Long licensen_expireLong = Long.parseLong(licensen_expireStr);
                        String licensen_expire = DateTimeUtil.stampToFormatDate(licensen_expireLong,"yyyy-MM-dd");
                        tvLicenseValidity.setText(licensen_expire);
                    }else{
                        tvLicenseValidity.setText(pickerStartDateTime.split(" ")[0]);
                    }
                }else{
                    tvLicenseValidity.setText(pickerStartDateTime.split(" ")[0]);
                }
            }
        }else if(buttonView.getId() == R.id.account_info_cbLegalPersonIdNumValidity){
            if(isChecked){
                Log.e("法人身份证有效期","选中");
                cb_legalPersonIdNumValidity = "Y";
                tvLegalPersonIdNumValidity.setClickable(false);
                tvLegalPersonIdNumValidity.setText("");
            }else{
                cb_legalPersonIdNumValidity = "N";
                tvLegalPersonIdNumValidity.setClickable(true);
                if(bus != null){
                    String merchant_id_expireStr = bus.getMerchant_id_expire();
                    if(Utils.isNotEmpty(merchant_id_expireStr)){
                        Long merchant_id_expireLong = Long.parseLong(merchant_id_expireStr);
                        String merchant_id_expire = DateTimeUtil.stampToFormatDate(merchant_id_expireLong,"yyyy-MM-dd");
                        tvLegalPersonIdNumValidity.setText(merchant_id_expire);
                    }else{
                        tvLegalPersonIdNumValidity.setText(pickerStartDateTime.split(" ")[0]);
                    }
                }else{
                    tvLegalPersonIdNumValidity.setText(pickerStartDateTime.split(" ")[0]);
                }

                Log.e("法人身份证有效期","未选中");
            }
        }else if(buttonView.getId() == R.id.account_info_cbContro_id_expire_long){
            if(isChecked){
                Log.e("控制人身份证有效期","选中");
                cb_ControIdExpireLong = "Y";
                tvControIdExpire.setClickable(false);
                tvControIdExpire.setText("");
            }else{
                cb_ControIdExpireLong = "N";
                tvControIdExpire.setClickable(true);
                if(bus != null){
                    String contro_id_expireStr = bus.getContro_id_expire();
                    if(Utils.isNotEmpty(contro_id_expireStr)){
                        Long contro_id_expireLong = Long.parseLong(contro_id_expireStr);
                        String contro_id_expire = DateTimeUtil.stampToFormatDate(contro_id_expireLong,"yyyy-MM-dd");
                        tvControIdExpire.setText(contro_id_expire);
                    }else{
                        tvControIdExpire.setText(pickerStartDateTime.split(" ")[0]);
                    }
                }else{
                    tvControIdExpire.setText(pickerStartDateTime.split(" ")[0]);
                }
                Log.e("控制人身份证有效期","未选中");
            }
        }else if(buttonView.getId() == R.id.account_info_cbSettPersonIdNumValidity){
            if(isChecked){
                Log.e("结算人身份证有效期","选中");
                cb_settPersonIdNumValidity = "Y";
                tvSettPersonIdNumValidity.setClickable(false);
                tvSettPersonIdNumValidity.setText("");
            }else{
                cb_settPersonIdNumValidity = "N";
                tvSettPersonIdNumValidity.setClickable(true);
                if(bus != null){
                    String settle_id_expireStr = bus.getSettle_id_expire();
                    if(Utils.isNotEmpty(settle_id_expireStr)){
                        Long settle_id_expireLong = Long.parseLong(settle_id_expireStr);
                        String settle_id_expire = DateTimeUtil.stampToFormatDate(settle_id_expireLong,"yyyy-MM-dd");
                        tvSettPersonIdNumValidity.setText(settle_id_expire);
                    }else{
                        tvSettPersonIdNumValidity.setText(pickerStartDateTime.split(" ")[0]);
                    }
                }else{
                    tvSettPersonIdNumValidity.setText(pickerStartDateTime.split(" ")[0]);
                }
                Log.e("结算人身份证有效期","未选中");
            }
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.account_info_rbPublic://对公
                accountType = "1";
                Log.e("选中状态","对公");
                //以上控件值更改刷新View
                updateView();
                break;
            case R.id.account_info_rbPrivate://对私
                accountType = "2";
                Log.e("选中状态","对私");
                //以上控件值更改刷新View
                updateView();
                break;
            case R.id.account_info_rbIsLegalPersonAccount://法人入账
                isLegalPersonAccount = "1";
                Log.e("选中状态","法人入账");
                //以上控件值更改刷新View
                updateView();
                break;
            case R.id.account_info_rbNoLegalPersonAccount://非法人入账
                isLegalPersonAccount = "2";
                Log.e("选中状态","非法人入账");
                //以上控件值更改刷新View
                updateView();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE){
            if(resultCode == SearchBankActivity.RESULT_CODE){
                Bundle bundle = data.getExtras();
                branch = (BackListData) bundle.getSerializable("branch");
                if(branch!=null){
                    tvOpeningArea.setText(branch.getBank_name());
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
