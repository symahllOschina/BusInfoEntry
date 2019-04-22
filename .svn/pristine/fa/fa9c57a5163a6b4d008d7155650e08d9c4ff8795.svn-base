package com.wdkj.dkhdl.utils;


/**
 * 服务地址管理类
 * 所有接口地址管理
 *
 * 
 */
public class NitConfig {
	
	/**  打包前必看：
	 * 1，替换正式域名前缀(包括更新版本地址前缀)
	 */
	public static final boolean isFormal = true;//true:正式环境,false:测试环境


	//测试服务器地址前缀
	public static final String queryBasePath1 =  "https://dev.weupay.com/admin";


	//正式服务器地址前缀
	public static final String queryBasePath = "https://weixin.weupay.com/admin";


	/**
	 * 登录
	 * 参数：account:1110007,password:1234567
	 */
	public static final String doLoginUrl = queryBasePath + "/wp/ag/1/loginApp";


	/**
	 * 上传图片
	 * 参数
	 * file
	 * id
	 */
	public static final String uploadImg = queryBasePath + "/wp/ag/uploadAgentImages";


	/**
	 * 查询省份
	 * 入参：
	 * province_code : 传0死值,银行省份传3229
	 */
	public static final String getProvince = queryBasePath + "/wp/comm/getProvince";

	/**
	 * 查询市，区
	 * 入参：
	 * id : 省份id值对应省sid，市sid
	 */
	public static final String getCityList = queryBasePath + "/wp/comm/queryCity";


	/**
	 * 查询行业一级分类
	 * 入参：不传
	 */
	public static final String getIndustryType = queryBasePath + "/wp/comm/addLcShopView";

	/**
	 * 查询行业二三级分类
	 * 入参：
	 * id : 父级分类id
	 */
	public static final String getSubIndustryType = queryBasePath + "/wp/comm/showBusinessType";

	/**
	 *商户基本信息提交
	 */
	public static final String subBusInfo = queryBasePath + "/wp/ag/addAppAgentMerone";

	/***
	 * 银行总行
	 * http://dev.weupay.com/admin/wp/comm/selectBank
	 * 入参：不传
	 */
	public static final String getBankList = queryBasePath + "/wp/comm/selectBank";
	/***
	 * 银行支行
	 * http://dev.weupay.com/admin/wp/comm/selectbranch
	 * 入参：{bank_city_code: 2460, id: 30}
	 * bank_city_code：城市code,
	 * id :总行id
     * bank_name:支行名称（模糊查询）
	 */
	public static final String getBranchList = queryBasePath + "/wp/comm/selectbranch";

	/**
	 *商户结算信息提交
	 */
	public static final String subBusAccountInfo = queryBasePath + "/wp/ag/addAppAgentMertwo";

	/**
	 *资质费率信息提交
	 */
	public static final String subRateInfo = queryBasePath + "/wp/ag/addAppAgentMerthree";



	/**
	 * 查询商户列表
	 * 参数：分别表示：页数  业务员id  代理id
	 * pageNum numPerPage  salesman_id   agent_id
	 */
	public static final String getBusList = queryBasePath + "/wp/ag/salesmanMerEnter";


	/**
	 * 商户资料信息回现
	 * 	参数：
	 * 	page(页数 = 1,2,3)
	 	id(商户资料表id)
	 */
	public static final String getBusInfo = queryBasePath + "/wp/ag/agentShopPages";
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
}
