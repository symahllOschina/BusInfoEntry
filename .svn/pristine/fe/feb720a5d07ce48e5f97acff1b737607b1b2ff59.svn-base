package com.wdkj.dkhdl.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.DecimalFormat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

/**
 *文件操作工具类
 */
public class FileUtils {

	/**
	 * 保存文件到SD卡 
	 */
	public static void saveToSDCard(String filename,String content) throws Exception{  
        File file=new File(Environment.getExternalStorageDirectory(), filename);  
        OutputStream out=new FileOutputStream(file);  
        out.write(content.getBytes());  
        out.close();  
    }  
	
	/**
	 * 保存bitmap到本地图库（指定路径SDCard/abcd/android.jpg，指定保存文件的名称android.jpg）
	 *  context：上下文
	 *  bmp：保存的bitmap对象
	 */
	
	private final static String ALBUM_PATH = Environment.getExternalStorageDirectory() + "/yilone/";
	
	private static String fileName="AIntervalLength.txt";
	
	public static void saveFile(Context context,String content){
	    try {
			File dirFile = new File(ALBUM_PATH);
			if (!dirFile.exists()) {
			    dirFile.mkdir();
			}
			File myCaptureFile = new File(ALBUM_PATH + fileName);
			OutputStream out=new FileOutputStream(myCaptureFile);  
	        out.write(content.getBytes());  
	        out.close();  
	        Log.e("保存时长：", "成功！");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("保存时长：", "失败111！");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("保存时长：", "失败222");
		}
	}
	
	/** 
     * 获取目录名称 
     * @param url 
     * @return FileName 
     */  
    public static String getFileName(String url)  
    {  
        int lastIndexStart = url.lastIndexOf("/");  
        if(lastIndexStart!=-1)  
        {  
            return url.substring(lastIndexStart+1, url.length());  
        }else{  
            return new Timestamp(System.currentTimeMillis()).toString();  
        }  
    } 
    /** 
     * 判断SD卡是否存在 
     * @return boolean 
     */  
    public static boolean checkSDCard() {  
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;  
        } else {  
            return false;  
        }  
    }  
      
    /** 
     * 保存目录目录到目录 
     * @param context 
     * @return  目录保存的目录 
     */  
    public static String setMkdir(Context context)  
    {  
        String filePath = null;  
        if(checkSDCard())  
        {  
            filePath = Environment.getExternalStorageDirectory()+File.separator+"yishuabao"+File.separator+"downloads";  
        }else{  
            filePath = context.getCacheDir().getAbsolutePath()+File.separator+"yishuabao"+File.separator+"downloads";  
        }  
        File file = new File(filePath);  
        if(!file.exists())  
        {  
            file.mkdirs();  
            Log.e("file", "目录不存在   创建目录    ");  
        }else{  
            Log.e("file", "目录存在");  
        }  
        return filePath;  
    }  
      
    /** 
     * 获取路径 
     * @return 
     * @throws IOException 
     */  
    public static  String getPath(Context context,String url)  
    {  
        String path = null;  
        try {  
            path = FileUtils.setMkdir(context)+File.separator+url.substring(url.lastIndexOf("/")+1);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return path;  
    }

    /**
     * 判断文件是否存在（根据文件名称判断）
     * context : 上下文
     * fileName : 文件名称
     */
    public static boolean isFileExist(Context context,String fileName)throws Exception{
        boolean state = false;
        File file = new File(context.getFilesDir(), fileName);
        if(file.exists())
        {
            state = true;
        }
        return state;
    }

    /**
     * 保存文件
     * context : 上下文
     * fileName : 文件名称
     * str : 要保存的字符串
     */
    public static void saveStringFile(Context context,String fileName,String str) throws Exception{

        //Context.MODE_PRIVATE权限，只有自身程序才能访问，而且写入的内容会覆盖文本内原有内
        FileOutputStream outStream=context.openFileOutput(fileName,context.MODE_PRIVATE);

        outStream.write(str.getBytes());

        outStream.close();

    }

    /**
     * 读取文件
     * context : 上下文
     * fileName : 文件名称
     */
    public static String readStringFile(Context context,String fileName)throws Exception{
        String str = "";
        File file = new File(context.getFilesDir(), fileName);
        if(file.exists())
        {
            //打开文件输入流
            FileInputStream inStream = context.openFileInput(fileName);
            //定义1M的缓冲区
            byte[] buffer = new byte[1024];
            //定义字符串变量
            StringBuilder sb = new StringBuilder("");
            int len = 0;
            //读取文件内容，当文件内容长度大于0时，
            while ((len = inStream.read(buffer)) > 0) {
                //把字条串连接到尾部
                sb.append(new String(buffer, 0, len));
            }
            //关闭输入流
            inStream.close();
            //返回字符串
            str = sb.toString();
        }
        return str;
    }


    /** 
     * 获取文件的大小 
     *  
     * @param fileSize 
     *            文件的大小
     * @return 
     */  
    public static String FormetFileSize(long fileSize) {// 转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");  
        String fileSizeString = "";  
        if (fileSize < 1024) {  
            fileSizeString = df.format((double) fileSize) + "B";  
        } else if (fileSize < 1048576) {  
            fileSizeString = df.format((double) fileSize / 1024) + "K";  
        } else if (fileSize < 1073741824) {  
            fileSizeString = df.format((double) fileSize / 1048576) + "M";  
        } else {  
            fileSizeString = df.format((double) fileSize / 1073741824) + "G";  
        }  
        return fileSizeString;  
    }

    /**
     * 获取文件的大小
     *
     * @param fileSize
     *            文件的大小
     * @return
     */
    public static double getFileSize(long fileSize) {// 转换文件大小
        double fileSizeDou = 0;
        if (fileSize < 1073741824) {
            fileSizeDou = (double) fileSize / 1048576;
        }
        return fileSizeDou;
    }



    /**
     * 获取文件大小字节
     */
    public static long getFileSize(File file) throws Exception {

        if (file == null) {
            return 0;
        }
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        }
        return size;
    }
    
}
