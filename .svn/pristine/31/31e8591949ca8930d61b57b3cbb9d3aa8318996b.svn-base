package com.wdkj.dkhdl.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.InputType;
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
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wdkj.dkhdl.BaseActivity;
import com.wdkj.dkhdl.Constant;
import com.wdkj.dkhdl.MainActivity;
import com.wdkj.dkhdl.R;
import com.wdkj.dkhdl.adapter.IndustryTypeAdapter;
import com.wdkj.dkhdl.adapter.RegionAdapter;
import com.wdkj.dkhdl.bean.BusDetailData;
import com.wdkj.dkhdl.bean.IndustryTypeData;
import com.wdkj.dkhdl.bean.RegionData;
import com.wdkj.dkhdl.bean.UserBean;
import com.wdkj.dkhdl.date.picker.TimeSelector;
import com.wdkj.dkhdl.date.util.DateTimeUtil;
import com.wdkj.dkhdl.date.util.DateToTimeStamp;
import com.wdkj.dkhdl.httputil.HttpURLConnectionUtil;
import com.wdkj.dkhdl.httputil.NetworkUtils;
import com.wdkj.dkhdl.utils.EditTextUtils;
import com.wdkj.dkhdl.utils.GsonUtils;
import com.wdkj.dkhdl.utils.IdcardUtils;
import com.wdkj.dkhdl.utils.NitConfig;
import com.wdkj.dkhdl.utils.ToastUtil;
import com.wdkj.dkhdl.utils.Utils;

import org.angmarch.views.NiceSpinner;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 商户基本信息
 */
@ContentView(R.layout.activity_basic_info)
public class BasicInfoActivity extends BaseActivity implements View.OnClickListener,
                                                                CompoundButton.OnCheckedChangeListener
{
    public static Context context;
    @ViewInject(R.id.info_failure_hintText)
    TextView tvErrorMsg;
    @ViewInject(R.id.basic_info_busName)
    EditText etBusName;
    @ViewInject(R.id.basic_info_busAbbreviation)
    EditText etBusAbbreviation;
    @ViewInject(R.id.basic_info_registName)
    EditText etRegistName;
    @ViewInject(R.id.basic_info_region)
    TextView tvRegion;
    @ViewInject(R.id.basic_info_address)
    EditText etAddress;
    @ViewInject(R.id.basic_info_contacts)
    EditText etContacts;
    @ViewInject(R.id.basic_info_contactsIdCard)
    EditText etContactsIdCard;
    @ViewInject(R.id.basic_info_contacts_tvIdCardValidity)
    Button tvIdCardValidity;//联系人身份证号有效期
    @ViewInject(R.id.basic_info_contacts_cbIdCardValidity)
    CheckBox cbIdCardValidity;//是否长期有效
    @ViewInject(R.id.basic_info_contactsTel)
    EditText etContactsTel;
    @ViewInject(R.id.basic_info_contactsEmil)
    EditText etContactsEmil;
    @ViewInject(R.id.basic_info_industryType)
    TextView tvIndustryType;
    @ViewInject(R.id.basic_info_service_phone)
    EditText etServicePhone;
    @ViewInject(R.id.basic_info_merchant_type)
    NiceSpinner tvMerchantType;

    @ViewInject(R.id.basic_info_btNext)
    Button btNext;



    private String id = "";
    private List<RegionData> lsProvince = new ArrayList<RegionData>();
    private List<RegionData> lsCity = new ArrayList<RegionData>();
    private List<RegionData> lsArea = new ArrayList<RegionData>();

    private RegionData province;
    private RegionData city;
    private RegionData area;

    private int provinceIndex,cityIndex,areaIndex;
    private String provinceName,cityName,areaName;

    private Dialog provinceDialog;
    private Dialog cityDialog;
    private Dialog areaDialog;


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
    /**
     * 商户类型："1" 一级商户 "2" 二级商户
     * 默认为二级商户
     */
    private String merchantType = "2";

    private String cb_IdCardValidity = "N";//联系人身份证有效日期，默认不选中值为"N",长期有效时为"Y"

    private static final String format = "yyyy-MM-dd HH:mm";
    private String pickerStartDateTime,pickerEndDateTime;//日期选择控件的选择范围，起始日期和结束日期


    private UserBean userBean = MainActivity.userBean;

    private int REQUEST_CODE = 1;

    BusDetailData bus = null;//商户信息对象
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = BasicInfoActivity.this;
        Intent in = getIntent();
        int busId = in.getIntExtra("id",0);
        //设置日期起始时间和结束日期
        //初始化起始日期时间（三月后日期）
        pickerStartDateTime = DateTimeUtil.getAMonthDateStr(3,format);
        pickerEndDateTime = DateTimeUtil.getYearDateStr(20,format);
        Log.e("起始日期：",pickerStartDateTime);
        Log.e("结束日期：",pickerEndDateTime);

        initView();
        initListener();

        setTitle("商户基本资料");



        //获取商户基础信息
        if(busId!=0){
            id = String.valueOf(busId);
            getBusInfo();
        }



    }

    private void initView(){
        //以下输入框禁止输入空格和换行
        EditTextUtils.setEditTextInputSpace(etBusName);
        EditTextUtils.setEditTextInputSpace(etBusAbbreviation);
        EditTextUtils.setEditTextInputSpace(etRegistName);
        EditTextUtils.setEditTextInputSpace(etAddress);
        EditTextUtils.setEditTextInputSpace(etContacts);
        EditTextUtils.setEditTextInputSpace(etContactsTel);
        EditTextUtils.setEditTextInputSpace(etContactsEmil);
        EditTextUtils.setEditTextInputSpace(etServicePhone);

        //商户类型
        BusDetailData bus = null;
        setMerchantType(bus);
        /**
         * 联系人证件号及有效期
         */
        //证件到期日期（默认显示为系统日期）
        if(bus!=null){
            String person_id_expireStr = bus.getPerson_id_expire();
            if(Utils.isNotEmpty(person_id_expireStr)){
                Long person_id_expireLong = Long.parseLong(person_id_expireStr);
                String person_id_expire = DateTimeUtil.stampToFormatDate(person_id_expireLong,"yyyy-MM-dd");
                tvIdCardValidity.setText(person_id_expire);
            }else{
                tvIdCardValidity.setText(pickerStartDateTime.split(" ")[0]);
            }
        }else{
            tvIdCardValidity.setText(pickerStartDateTime.split(" ")[0]);
        }
        //是否长期有效
        if(bus!=null){
            if(Utils.isNotEmpty(bus.getPerson_id_expire_long())){
                cb_IdCardValidity = bus.getPerson_id_expire_long();
            }
        }
        if("N".equals(cb_IdCardValidity)){
            cbIdCardValidity.setChecked(false);
        }else{
            cbIdCardValidity.setChecked(true);
        }
    }

    private void initListener(){
        tvRegion.setOnClickListener(this);
        tvIdCardValidity.setOnClickListener(this);
        cbIdCardValidity.setOnCheckedChangeListener(this);
        tvIndustryType.setOnClickListener(this);
        btNext.setOnClickListener(this);
    }


    /**
     * 初始化界面数据（回显数据）
     */
    private void updateView(BusDetailData bus){
        //merchant_status状态为2即审核驳回时返回驳回原因
        if(bus!=null){
            if("2".equals(bus.getMerchant_status())){
                if(Utils.isNotEmpty(bus.getError_msg())){
                    tvErrorMsg.setVisibility(View.VISIBLE);
                    tvErrorMsg.setText(String.format(getResources().getString(R.string.failure_hints),bus.getError_msg()));
                }
            }

            //商户名称禁止修改
//            etBusName.setEnabled(false);
            etBusName.setText(bus.getMerchant_name());
            etBusAbbreviation.setText(bus.getMerchant_alias());
            etRegistName.setText(bus.getMerchant_company());
            //省
            province = new RegionData();
            province.setSid(Integer.parseInt(bus.getMerchant_province_code()));
            province.setFullname(bus.getMerchant_province_name());
            //市
            city = new RegionData();
            city.setSid(Integer.parseInt(bus.getMerchant_city_code()));
            city.setFullname(bus.getMerchant_city_name());
            //县区
            area = new RegionData();
            area.setSid(Integer.parseInt(bus.getMerchant_county_code()));
            area.setFullname(bus.getMerchant_county_name());
            tvRegion.setText(province.getFullname()+"/"+city.getFullname()+"/"+area.getFullname());
            etAddress.setText(bus.getMerchant_address());
            //联系人信息
            etContacts.setText(bus.getMerchant_person());
            etContactsIdCard.setText(bus.getPerson_id_no());
            String person_id_expireStr = bus.getPerson_id_expire();
            if(Utils.isNotEmpty(person_id_expireStr)){
                Long person_id_expireLong = Long.parseLong(person_id_expireStr);
                String person_id_expire = DateTimeUtil.stampToFormatDate(person_id_expireLong,"yyyy-MM-dd");
                tvIdCardValidity.setText(person_id_expire);
            }else{
                tvIdCardValidity.setText(pickerStartDateTime.split(" ")[0]);
            }
            //是否长期有效
            if(Utils.isNotEmpty(bus.getPerson_id_expire_long())){
                cb_IdCardValidity = bus.getPerson_id_expire_long();
            }
            if("N".equals(cb_IdCardValidity)){
                cbIdCardValidity.setChecked(false);
            }else{
                cbIdCardValidity.setChecked(true);
            }
            etContactsTel.setText(bus.getMerchant_phone());
            etContactsEmil.setText(bus.getMerchant_email());
            //行业类目
            oneIndustryType = new IndustryTypeData();
            oneIndustryType.setId(Integer.parseInt(bus.getBusiness_type1_code()));
            oneIndustryType.setName(bus.getBusiness_type1_name());
            twoIndustryType = new IndustryTypeData();
            twoIndustryType.setId(Integer.parseInt(bus.getBusiness_type2_code()));
            twoIndustryType.setName(bus.getBusiness_type2_name());
            threeIndustryType = new IndustryTypeData();
            threeIndustryType.setId(Integer.parseInt(bus.getBusiness_type3_code()));
            threeIndustryType.setName(bus.getBusiness_type3_name());
            tvIndustryType.setText(oneIndustryType.getName()+"/"+twoIndustryType.getName()+"/"+threeIndustryType.getName());
            //客服电话
            etServicePhone.setText(bus.getMerchant_service_phone());
            //商户类型
            setMerchantType(bus);

        }


    }

    /**
     * 商户类型
     */
    private void setMerchantType(BusDetailData bus){
        final List<String> list = new ArrayList<>();
        list.add(Constant.merchantTypeList[0]);
        list.add(Constant.merchantTypeList[1]);
        tvMerchantType.attachDataSource(list);
        if(bus!=null&&Utils.isNotEmpty(bus.getMerchant_type())){

            merchantType = bus.getMerchant_type();
            tvMerchantType.setSelectedIndex(Constant.getMerchantTypeIndex(merchantType));



        }else{
            //默认值为二级商户
            merchantType = Constant.getMerchantType(list.get(1));
        }


        tvMerchantType.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        merchantType = Constant.getMerchantType(list.get(position));
                        break;
                    case 1:
                        merchantType = Constant.getMerchantType(list.get(position));
                        break;
                        default:
                            break;
                }

            }
        });

    }

    /**
     * 选择联系人证件有效期
     */
    private void setIdCardValidity(){
        TimeSelector timeSelector = new TimeSelector(this, new TimeSelector.ResultHandler() {
            @Override
            public void handle(String time) {
//                        Toast.makeText(getApplicationContext(), time, Toast.LENGTH_LONG).show();

                tvIdCardValidity.setText(time.split(" ")[0]);


            }
        },pickerStartDateTime,pickerEndDateTime);
        timeSelector.setMode(TimeSelector.MODE.YMD);//显示 年月日时分（默认）；
//                timeSelector.setMode(TimeSelector.MODE.YMD);//只显示 年月日
        timeSelector.setIsLoop(false);//不设置时为true，即循环显示
        timeSelector.show();
    }




    private void provinceDialog(final List<RegionData> list){
        provinceDialog = new Dialog(context, R.style.PickerDialog);
        //点击屏幕不消失
        provinceDialog.setCancelable(true);
        provinceDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //1：先创建子布局选择器对象
        LayoutInflater inflater=LayoutInflater.from(context);
        //获取dialog布局
        View view=inflater.inflate(R.layout.city_picker_view, null);
        RadioGroup radioGroup = view.findViewById(R.id.city_picker_radioGroup);
        final RadioButton rbProvince = view.findViewById(R.id.city_picker_province);
        RadioButton rbCity = view.findViewById(R.id.city_picker_city);
        RadioButton rbArea = view.findViewById(R.id.city_picker_area);
        ListView listView = view.findViewById(R.id.city_picker_listView);
        RegionAdapter adapter = new RegionAdapter(this,list);
        listView.setAdapter(adapter);
        provinceDialog.setContentView(view);
        Window window = provinceDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = window.getAttributes();
//            int width = ScreenUtil.getInstance(context).getScreenWidth();
        int width = Utils.getDisplayWidth(BasicInfoActivity.this);
        int hight = Utils.getDisplayHeight(BasicInfoActivity.this);
        lp.width = width;
        lp.height = hight/2;
        window.setAttributes(lp);
        provinceDialog.show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ToastUtil.showText(context,lsProvince.get(position).getFullname(),1);
                if(Utils.isFastClick(Constant.INTERVAL500)){
                    return;
                }
                provinceIndex = position;
                provinceName = list.get(position).getFullname();
                rbProvince.setText(provinceName);
                int sid = list.get(position).getSid();

                getCityList(sid);

            }
        });
    }

    private void cityDialog(final List<RegionData> list){
        cityDialog = new Dialog(context, R.style.PickerDialog);
        //点击屏幕不消失
        cityDialog.setCancelable(true);
        cityDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //1：先创建子布局选择器对象
        LayoutInflater inflater=LayoutInflater.from(context);
        //获取dialog布局
        View view=inflater.inflate(R.layout.city_picker_view, null);
        RadioGroup radioGroup = view.findViewById(R.id.city_picker_radioGroup);
        final RadioButton rbProvince = view.findViewById(R.id.city_picker_province);
        rbProvince.setText(provinceName);
        final RadioButton rbCity = view.findViewById(R.id.city_picker_city);
        rbCity.setVisibility(View.VISIBLE);
        RadioButton rbArea = view.findViewById(R.id.city_picker_area);
        ListView listView = view.findViewById(R.id.city_picker_listView);
        RegionAdapter adapter = new RegionAdapter(this,list);
        listView.setAdapter(adapter);
        cityDialog.setContentView(view);
        Window window = cityDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = window.getAttributes();
//            int width = ScreenUtil.getInstance(context).getScreenWidth();
        int width = Utils.getDisplayWidth(this);
        int hight = Utils.getDisplayHeight(this);
        lp.width = width;
        lp.height = hight/2;
        window.setAttributes(lp);

        cityDialog.show();
        provinceDialog.dismiss();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ToastUtil.showText(context,list.get(position).getFullname(),1);
                if(Utils.isFastClick(Constant.INTERVAL500)){
                    return;
                }
                cityIndex = position;
                cityName = list.get(position).getFullname();
                rbCity.setText(cityName);
                int sid = list.get(position).getSid();

                getAreaList(sid);



            }
        });
    }

    private void areaDialog(final List<RegionData> list){
        areaDialog = new Dialog(context, R.style.PickerDialog);
        //点击屏幕不消失
        areaDialog.setCancelable(true);
        areaDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //1：先创建子布局选择器对象
        LayoutInflater inflater=LayoutInflater.from(context);
        //获取dialog布局
        View view=inflater.inflate(R.layout.city_picker_view, null);
        RadioGroup radioGroup = view.findViewById(R.id.city_picker_radioGroup);
        final RadioButton rbProvince = view.findViewById(R.id.city_picker_province);
        final RadioButton rbCity = view.findViewById(R.id.city_picker_city);
        final RadioButton rbArea = view.findViewById(R.id.city_picker_area);
        rbProvince.setText(provinceName);
        rbCity.setVisibility(View.VISIBLE);
        rbCity.setText(cityName);
        rbArea.setVisibility(View.VISIBLE);
        ListView listView = view.findViewById(R.id.city_picker_listView);
        RegionAdapter adapter = new RegionAdapter(this,list);
        listView.setAdapter(adapter);
        areaDialog.setContentView(view);
        Window window = areaDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = window.getAttributes();
//            int width = ScreenUtil.getInstance(context).getScreenWidth();
        int width = Utils.getDisplayWidth(this);
        int hight = Utils.getDisplayHeight(this);
        lp.width = width;
        lp.height = hight/2;
        window.setAttributes(lp);


        areaDialog.show();
        cityDialog.dismiss();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ToastUtil.showText(context,list.get(position).getFullname(),1);
                if(Utils.isFastClick(Constant.INTERVAL500)){
                    return;
                }
                areaIndex = position;
                areaName = list.get(position).getFullname();

                area = list.get(position);
                rbArea.setText(area.getFullname());
                province = lsProvince.get(provinceIndex);
                city = lsCity.get(cityIndex);
                tvRegion.setText(province.getFullname()+"/"+city.getFullname()+"/"+area.getFullname());
                areaDialog.dismiss();


            }
        });
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
                tvIndustryType.setText(oneIndustryType.getName()+"/"+twoIndustryType.getName()+"/"+threeIndustryType.getName());
                threeIndustryDialog.dismiss();


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
                  userJSON.put("page", "1");
                  userJSON.put("id", id);
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

    /** 获取省份 */
    private void getProvince(){
        showWaitDialog();
        final String url = NitConfig.getProvince;
        new Thread(){
            @Override
            public void run() {
                try {
                    // 拼装JSON数据，向服务端发起请求
                    JSONObject userJSON = new JSONObject();
                    userJSON.put("province_code", "0");
                    String content = String.valueOf(userJSON);
                    Log.e("查询省份请求参数：", content);
                    String jsonStr = HttpURLConnectionUtil.doPos(url,content);
                    Log.e("查询省份返回字符串结果：", jsonStr);
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

    /** 获取城市 */
    private void getAreaList(final int sid){
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
                    Log.e("查询区请求参数：", content);
                    String jsonStr = HttpURLConnectionUtil.doPos(url,content);
                    Log.e("查询区返回字符串结果：", jsonStr);
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
                    userJSON.put("type","");
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
                    userJSON.put("type","");
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
                    userJSON.put("type","");
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

    /**
     * 提交第一页商户基本信息
     */
    private void subBusInfo(final String busNameStr, final String busAbbreviationStr, final String registNameStr, final String addressStr,
                            final String contactsStr, final String contactsIdCardStr,final String contactsTelStr, final String contactsEmilStr, final String servicePhoneStr)
    {

        showWaitDialog();

        //联系人证件有效期
        final String idCardValidity = tvIdCardValidity.getText().toString();
        //有效期转时间戳
        final String person_id_expire = DateToTimeStamp.getStartTimeStampTo(idCardValidity);

        final String url = NitConfig.subBusInfo;
        new Thread(){
            @Override
            public void run() {
                try {
                    // 拼装JSON数据，向服务端发起请求
                    JSONObject userJSON = new JSONObject();
                    userJSON.put("salesman_id", userBean.getSalesman_id());
                    userJSON.put("agent_id", userBean.getAgent_id());
                    userJSON.put("id", id);
                    userJSON.put("merchant_name", busNameStr);
                    userJSON.put("merchant_alias", busAbbreviationStr);
                    userJSON.put("merchant_company", registNameStr);
                    userJSON.put("merchant_province_code", province.getSid());
                    userJSON.put("merchant_city_code", city.getSid());
                    userJSON.put("merchant_county_code", area.getSid());
                    userJSON.put("merchant_address", addressStr);
                    userJSON.put("merchant_person", contactsStr);
                    userJSON.put("person_id_no", contactsIdCardStr);
                    userJSON.put("person_id_expire", person_id_expire);
                    userJSON.put("person_id_expire_long", cb_IdCardValidity);
                    userJSON.put("merchant_phone", contactsTelStr);
                    userJSON.put("merchant_email", contactsEmilStr);
                    userJSON.put("business_type1_id", oneIndustryType.getId());
                    userJSON.put("business_type2_id", twoIndustryType.getId());
                    userJSON.put("business_type3_id", threeIndustryType.getId());
                    userJSON.put("merchant_service_phone", servicePhoneStr);
                    userJSON.put("merchant_type", merchantType);
                    String content = String.valueOf(userJSON);
                    Log.e("提交第一页请求参数：", content);
                    String jsonStr = HttpURLConnectionUtil.doPos(url,content);
                    Log.e("提交第一页返回字符串结果：", jsonStr);
                    int msg = 7;
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
                    String provinceJson = (String) msg.obj;
                    provinceJson(provinceJson);
                    hideWaitDialog();
                    break;
                case 2:
                    String cityJson = (String) msg.obj;
                    cityJson(cityJson);
                    hideWaitDialog();
                    break;
                case 3:
                    String areaJson = (String) msg.obj;
                    areaJson(areaJson);
                    hideWaitDialog();
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
                case 7:
                    String subBusInfoResultJson = (String) msg.obj;
                    subBusInfoResultJson(subBusInfoResultJson);
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
                //弹出选择Dialog
                provinceDialog(lsProvince);
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
                cityDialog(lsCity);
            }else{
                ToastUtil.showText(context,message,1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void areaJson(String json){

        try {
            JSONObject job = new JSONObject(json);
            int status = job.getInt("status");
            String message = job.getString("message");
            if(status == 200){
                String dataJson = job.getString("data");
                JSONObject obj = new JSONObject(dataJson);
                String provinceList = obj.getString("provinceList");
                lsArea.clear();
                Gson gjson1  =  GsonUtils.getGson();
                lsArea=gjson1.fromJson(provinceList, new TypeToken<List<RegionData>>() {  }.getType());
                Log.e("区数据",lsArea.size()+"");
                areaDialog(lsArea);

            }else{
                ToastUtil.showText(context,message,1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
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
    private void subBusInfoResultJson(String json){

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
                    String busNameStr = etBusName.getText().toString();
                    int id = dataJob.getInt("id");
                    Intent intent = new Intent();
                    intent.putExtra("id",id);
                    intent.putExtra("name",busNameStr);
                    intent.putExtra("merchantType",merchantType);
                    intent.setClass(this,AccountInfoActivity.class);
                    startActivityForResult(intent,REQUEST_CODE);
                }else{
                    if(Utils.isNotEmpty(subMsg)){
                        ToastUtil.showText(context,subMsg,1);
                    }else{
                        ToastUtil.showText(context,"提交失败！",1);
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
                        updateView(bus);
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
     * 本地验证
     */
    private void subTextVerification(){
        String busNameStr = etBusName.getText().toString();
        if(Utils.isEmpty(busNameStr)){
            ToastUtil.showText(context,"商户名称不能为空！",1);
            return;
        }
        String busAbbreviationStr = etBusAbbreviation.getText().toString();
        if(Utils.isEmpty(busAbbreviationStr)){
            ToastUtil.showText(context,"商户简称不能为空！",1);
            return;
        }
        String registNameStr = etRegistName.getText().toString();
        if(Utils.isEmpty(registNameStr)){
            ToastUtil.showText(context,"营业执照名称不能为空！",1);
            return;
        }
        if(province == null||city == null||area == null){
            ToastUtil.showText(context,"请选择商户所在省/市/区！",1);
            return;
        }
        String addressStr = etAddress.getText().toString();
        if(Utils.isEmpty(addressStr)){
            ToastUtil.showText(context,"详细地址不能为空！",1);
            return;
        }
        String contactsStr = etContacts.getText().toString();
        if(Utils.isEmpty(contactsStr)){
            ToastUtil.showText(context,"联系人不能为空！",1);
            return;
        }

        if(contactsStr.length()<=1){
            ToastUtil.showText(context,"请输入正确的商户联系人！",1);
            return;
        }
        String contactsIdCardStr = etContactsIdCard.getText().toString().trim();
        if(Utils.isEmpty(contactsIdCardStr)){
            ToastUtil.showText(context,"联系人身份证号不能为空！",1);
            return;
        }
        if(!IdcardUtils.validateCard(contactsIdCardStr)){
            ToastUtil.showText(context,"请检查联系人身份证号是否正确！",1);
            return;
        }
        String contactsTelStr = etContactsTel.getText().toString();
        if(Utils.isEmpty(contactsTelStr)){
            ToastUtil.showText(context,"联系人电话不能为空！",1);
            return;
        }
        if(!Utils.isPhoneNumAndTel(contactsTelStr)){
            ToastUtil.showText(context,"请填写正确的联系人电话！",1);
            return;
        }

        String contactsEmilStr = etContactsEmil.getText().toString();
        if(Utils.isEmpty(contactsEmilStr)){
            ToastUtil.showText(context,"联系人邮箱不能为空！",1);
            return;
        }
        if(!Utils.isEmail(contactsEmilStr)){
            ToastUtil.showText(context,"请填写正确的邮箱地址！",1);
            return;
        }

        if(oneIndustryType == null||twoIndustryType == null||threeIndustryType == null){
            ToastUtil.showText(context,"请选择行业类目！",1);
            return;
        }
        String servicePhoneStr = etServicePhone.getText().toString();
        if(Utils.isEmpty(servicePhoneStr)){
            ToastUtil.showText(context,"客服电话不能为空！",1);
            return;
        }
        if(Utils.isNotEmpty(servicePhoneStr)){
            if(!Utils.isPhoneNumAndTel(servicePhoneStr)){
                ToastUtil.showText(context,"请填写正确的客服电话！",1);
                return;
            }
        }


        Log.e("结果","省="+province.getFullname()+"市="+city.getFullname()+"区="+area.getFullname());

        subBusInfo(busNameStr,busAbbreviationStr,registNameStr,addressStr,contactsStr,contactsIdCardStr,contactsTelStr,contactsEmilStr,servicePhoneStr);

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE){
            if(resultCode == AccountInfoActivity.RESULT_OK){
                Bundle bundle=data.getExtras();
                id =bundle.getString("id");
                merchantType = bundle.getString("merchantType");
//                etBusName.setEnabled(false);
                Log.e("onActivityResult：",id);
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.basic_info_region:
                if(Utils.isFastClick(Constant.INTERVAL500)){
                    return;
                }
                if(lsProvince!=null&&lsProvince.size()>=1){
                    provinceDialog(lsProvince);
                }else{
                    getProvince();
                }
                break;
            case R.id.basic_info_contacts_tvIdCardValidity://联系人证件有效期
                setIdCardValidity();
                break;
            case R.id.basic_info_industryType:
                if(Utils.isFastClick(Constant.INTERVAL500)){
                    return;
                }
                if(lsOneIndustry!=null&&lsOneIndustry.size()>=1){
                   oneIndustryDialog(lsOneIndustry);
                }else{
                    getIndustryType();
                }
                break;
            case R.id.basic_info_btNext:
                if(Utils.isFastClick(Constant.INTERVAL500)){
                    return;
                }
                subTextVerification();
                break;
            default:
                break;
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView.getId() == R.id.basic_info_contacts_cbIdCardValidity){
            if(isChecked){
                Log.e("证件有效期","选中");
                cb_IdCardValidity = "Y";
                tvIdCardValidity.setClickable(false);
                tvIdCardValidity.setText("");
            }else{
                cb_IdCardValidity = "N";
                Log.e("证件有效期","未选中");
                tvIdCardValidity.setClickable(true);
                if(bus!=null){
                    String person_id_expireStr = bus.getPerson_id_expire();
                    if(Utils.isNotEmpty(person_id_expireStr)){
                        Long person_id_expireLong = Long.parseLong(person_id_expireStr);
                        String person_id_expire = DateTimeUtil.stampToFormatDate(person_id_expireLong,"yyyy-MM-dd");
                        tvIdCardValidity.setText(person_id_expire);
                    }else{
                        tvIdCardValidity.setText(pickerStartDateTime.split(" ")[0]);
                    }
                }else{
                    tvIdCardValidity.setText(pickerStartDateTime.split(" ")[0]);
                }
            }
        }
    }
}
