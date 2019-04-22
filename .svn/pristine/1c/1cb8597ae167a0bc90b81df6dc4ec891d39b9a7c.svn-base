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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wdkj.dkhdl.BaseActivity;
import com.wdkj.dkhdl.MainActivity;
import com.wdkj.dkhdl.R;
import com.wdkj.dkhdl.adapter.IndustryTypeAdapter;
import com.wdkj.dkhdl.adapter.RegionAdapter;
import com.wdkj.dkhdl.bean.IndustryTypeData;
import com.wdkj.dkhdl.bean.RegionData;
import com.wdkj.dkhdl.bean.UserBean;
import com.wdkj.dkhdl.httputil.HttpURLConnectionUtil;
import com.wdkj.dkhdl.httputil.NetworkUtils;
import com.wdkj.dkhdl.utils.EditTextUtils;
import com.wdkj.dkhdl.utils.GsonUtils;
import com.wdkj.dkhdl.utils.NitConfig;
import com.wdkj.dkhdl.utils.ToastUtil;
import com.wdkj.dkhdl.utils.Utils;

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
public class ModifyBasicInfoActivity extends BaseActivity implements View.OnClickListener{
    public static Context context;
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
    @ViewInject(R.id.basic_info_contactsTel)
    EditText etContactsTel;
    @ViewInject(R.id.basic_info_contactsEmil)
    EditText etContactsEmil;
    @ViewInject(R.id.basic_info_industryType)
    TextView tvIndustryType;
    @ViewInject(R.id.basic_info_service_phone)
    EditText etServicePhone;

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

    private UserBean userBean = MainActivity.userBean;

    private int REQUEST_CODE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = ModifyBasicInfoActivity.this;
        initView();
        initListener();

        setTitle("商户基本资料");

        //获取商户基础信息
        getBusInfo();


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
    }

    private void initListener(){
        tvRegion.setOnClickListener(this);
        tvIndustryType.setOnClickListener(this);
        btNext.setOnClickListener(this);
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
        int width = Utils.getDisplayWidth(ModifyBasicInfoActivity.this);
        int hight = Utils.getDisplayHeight(ModifyBasicInfoActivity.this);
        lp.width = width;
        lp.height = hight/2;
        window.setAttributes(lp);
        provinceDialog.show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ToastUtil.showText(context,lsProvince.get(position).getFullname(),1);
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
        final String url = NitConfig.getIndustryType;
        new Thread(){
            @Override
            public void run() {
                try {
                    // 拼装JSON数据，向服务端发起请求
                    JSONObject userJSON = new JSONObject();
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
     * 获取行业分类二级菜单
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
                            final String contactsStr, final String contactsTelStr, final String contactsEmilStr, final String servicePhoneStr)
    {

        showWaitDialog();
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
                    userJSON.put("merchant_phone", contactsTelStr);
                    userJSON.put("merchant_email", contactsEmilStr);
                    userJSON.put("business_type1_id", oneIndustryType.getId());
                    userJSON.put("business_type2_id", twoIndustryType.getId());
                    userJSON.put("business_type3_id", threeIndustryType.getId());
                    userJSON.put("merchant_service_phone", servicePhoneStr);
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

    private Handler handler = new Handler(){
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
                String oneIndustryList = obj.getString("TypeList");
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
            if(code.equals("000000")){
                String dataJson = job.getString("data");
                JSONObject dataJob = new JSONObject(dataJson);
                int status = dataJob.getInt("status");
                String message = dataJob.getString("message");
                if(status == 200){
                    int id = dataJob.getInt("id");
                    Intent intent = new Intent();
                    intent.putExtra("id",id);
                    intent.setClass(this,AccountInfoActivity.class);
                    startActivityForResult(intent,REQUEST_CODE);

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

        Log.e("结果","省="+province.getFullname()+"市="+city.getFullname()+"区="+area.getFullname());

        subBusInfo(busNameStr,busAbbreviationStr,registNameStr,addressStr,contactsStr,contactsTelStr,contactsEmilStr,servicePhoneStr);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE){
            if(resultCode == AccountInfoActivity.RESULT_OK){
                Bundle bundle=data.getExtras();
                id =bundle.getString("id");
                Log.e("onActivityResult：",id);
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.basic_info_region:
                if(Utils.isFastClick()){
                    return;
                }
                if(lsProvince!=null&&lsProvince.size()>=1){
                    provinceDialog(lsProvince);
                }else{
                    getProvince();
                }
                break;
            case R.id.basic_info_industryType:
                if(Utils.isFastClick()){
                    return;
                }
                if(lsOneIndustry!=null&&lsOneIndustry.size()>=1){
                   oneIndustryDialog(lsOneIndustry);
                }else{
                    getIndustryType();
                }
                break;
            case R.id.basic_info_btNext:
                if(Utils.isFastClick()){
                    return;
                }
                subTextVerification();

                break;
        }
    }



}
