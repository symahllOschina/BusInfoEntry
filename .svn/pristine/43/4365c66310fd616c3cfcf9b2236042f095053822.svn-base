package com.wdkj.dkhdl.utils;

import android.util.Log;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数字操作工具类
 */
public class DecimalUtil {
	
	public static String doubletoString(double d,int scale) {
		/**
		 * scale:代表几位小数
		 *  BigDecimal.setScale()方法用于格式化小数点
		 *	setScale(1)表示保留一位小数，默认用四舍五入方式
		 *	setScale(1,BigDecimal.ROUND_DOWN)直接删除多余的小数位，如2.35会变成2.3
		 *	setScale(1,BigDecimal.ROUND_UP)进位处理，2.35变成2.4
		 *	setScale(1,BigDecimal.ROUND_HALF_UP)四舍五入，2.35变成2.4
		 *	setScaler(1,BigDecimal.ROUND_HALF_DOWN)四舍五入，2.35变成2.3，如果是5则向下舍
		 * */
		BigDecimal b = new BigDecimal(d);
		BigDecimal f1 = b.setScale(scale, BigDecimal.ROUND_HALF_UP);
		return String.valueOf(f1);
		
	}

	public static double StringToDouble(String d,int scale) {
		BigDecimal b = new BigDecimal(d);
		BigDecimal f1 = b.setScale(scale, BigDecimal.ROUND_HALF_UP);
		return f1.doubleValue();

	}
	
	/**  
	 *  金额换算（元转分）
	 *  例如：1  转换结果：0.01
	 */
	public static String elementToBranch(String moneyStr){
//		Log.e("原字符串金额：", moneyStr);
		String total_fee = "";
    	BigDecimal sumAmount = new BigDecimal(moneyStr);
    	BigDecimal transAmt = sumAmount.multiply(new BigDecimal(100));//乘以100(单位：分) //String  转化为 BigDecimal ,乘以100，元转化为分进制是100
    	total_fee = transAmt.intValue()+""; 
//    	Log.e("换算的提交金额：", total_fee);
		return total_fee;
	}
	
	/**  
	 *  金额换算（分转元）
	 */
	public static String branchToElement(String moneyStr){
		Log.e("原字符串金额：",moneyStr);
		String total_fee = "";
		BigDecimal sumAmount = new BigDecimal(moneyStr);
		BigDecimal transAmt = sumAmount.multiply(new BigDecimal(0.01)).setScale(2, BigDecimal.ROUND_HALF_UP);
		total_fee = transAmt.toString();
		Log.e("换算后金额：",total_fee);
		return total_fee;
	}

	/**
	 * 主要作用为将String字符串转换为String类型的金额
	 * 例如将字符串.3或2.03，2.156的字符串
	 * 输出为0.3,2.03,2.156
	 * 
	 */
    public static String scaleNumber(String totalStr){
    	BigDecimal returnNum = new BigDecimal(totalStr);
    	return returnNum.doubleValue()+"";
    }  
    
    /**
     * 作用同上：都是将String字符串转换为金额，
     * 不同的是该方法保留小数点后面两位小数，保留采取四舍五入，位数不够使用0补替
     * 例如将字符串.3或2.03，2.156的字符串
	 * 输出为0.30,2.03,2.16
     */
    public static String StringToDoublePrice(String priceStr){
    	double cny = Double.parseDouble(priceStr);
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(cny);
    }
    
    /**
     * 判断字符串是否相等（用于金额比较，这里主要作用是判断输入金额是否为有效金额，比如0.00为无效金额）
     * 在数字上小于、等于或大于 b 时，返回 -1、0 或 1。
     * 例如：str = "0.01"; 则返回1
     */
    public static int isEqual(String str){
    	double b = 0.00;
    	BigDecimal data1 = new BigDecimal(str);
    	BigDecimal data2 = new BigDecimal(b);
    	return data1.compareTo(data2);
    }

    /**
	 * isRange（）判断是否在范围内，是返回true
	 */
    public static boolean isRange(double num,int minNum,int maxNum){
    	if(num >= minNum && num<=maxNum){
    		return true;
		}
		return false;
	}
	
	
	/**
	 *  用正则表达式，判断是否为整数
	 */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){  
            return false;   
        }   
        return true;   
     }


	/**
	 * 提供精确的加法运算。
	 * @param v1 被加数
	 * @param v2 加数
	 * @return 两个参数的和
	 */
	public static double add(double v1,double v2,int scale){
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的减法运算。
	 * @param v1 被减数
	 * @param v2 减数
	 * @return 两个参数的差
	 */
	public static double sub(double v1,double v2,int scale){
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).setScale(scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	/**
	 * 提供精确的乘法运算。
	 * @param v1 被乘数
	 * @param v2 乘数
	 * @return 两个参数的积
	 */
	public static double mul(double v1,double v2){
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
	 * 定精度，以后的数字四舍五入。
	 * @param v1 被除数
	 * @param v2 除数
	 * @param scale 表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static double div(double v1,double v2,int scale){
		if(scale<0){
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b2.divide(b1,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	/**
	 * 提供精确的小数位四舍五入处理。
	 * @param v 需要四舍五入的数字
	 * @param scale 小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double v,int scale){
		if(scale<0){
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	}

    
    
}
