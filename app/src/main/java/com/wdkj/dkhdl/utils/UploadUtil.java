package com.wdkj.dkhdl.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

import android.util.Log;

/**
 * 使用post方式上传文件到服务器的方法
 */
public class UploadUtil {
	
	private static final String TAG = "uploadFile";
	private static final int TIME_OUT = 50 * 1000; // 超时时间
	private static final String CHARSET = "utf-8"; // 设置编码
	/**
	   * Android上传文件到服务端
	   * @param params 普通的表单参数
	   * @param file 需要上传的文件
	   * @param RequestURL 请求的rul
	   * @return 返回响应的内容
	   */
	  public static String uploadFile(Map<String, String> params,File file, String RequestURL) {
		  String result = null;
		  String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
		  String PREFIX = "--", LINE_END = "\r\n";
		  String CONTENT_TYPE = "multipart/form-data"; // 内容类型
		  long totalLength = 0;
		  try {
		  	// 取得文件大小 这里一会显示上传进度条会用到
			  totalLength = getFileSize(file);
			  Log.d("lfq", "### size: " + totalLength);
		  } catch (Exception e) {
		  	e.printStackTrace();
		  }

		  try {
			  URL url = new URL(RequestURL);
		      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		      conn.setReadTimeout(TIME_OUT);//设置读取超时时长
		      conn.setConnectTimeout(TIME_OUT);// 设置连接超时时长
		      conn.setDoInput(true); // 允许输入流
		      conn.setDoOutput(true); // 允许输出流
		      conn.setUseCaches(false); // 不允许使用缓存
			  conn.setAllowUserInteraction(false); //是否允许用户交互
			  conn.setChunkedStreamingMode(0);//分块编码，我这里不需要，因为要计算上传进度
		      conn.setRequestMethod("POST"); // 请求方式
		      conn.setRequestProperty("Charset", CHARSET); // 设置编码
		      conn.setRequestProperty("connection", "keep-alive");//设置HttpURLConnection请求头里面的属性
		      conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);

              StringBuffer sb = new StringBuffer();
              /** 普通表单参数 */
			  if(params!=null){
				  for (Map.Entry<String, String> entry : params.entrySet()) {

					  //表单的属性写入
					  sb.append(PREFIX);
					  sb.append(BOUNDARY);
					  sb.append(LINE_END);
					  sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINE_END);
					  sb.append("Content-Type: text/plain; charset=" + CHARSET + LINE_END);
					  sb.append("Content-Transfer-Encoding: 8bit" + LINE_END);
					  sb.append(LINE_END); sb.append(entry.getValue());
					  sb.append(LINE_END);
				  }
			  }
			  DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
			  outStream.write(sb.toString().getBytes());
			  // 发送文件数据
		      if (file != null) {
		    	  /**
				   * 当文件不为空，把文件包装并且上传
				   */
		    	  StringBuilder sb1 = new StringBuilder();
		    	  sb1.append(PREFIX);
		    	  sb1.append(BOUNDARY);
				  sb1.append(LINE_END);
				  /**
				   * 这里重点注意： name里面的值为服务端需要key 只有这个key 才可以得到对应的文件
				   * filename是文件的名字，包含后缀名的 比如:abc.png
				   */
				  sb1.append("Content-Disposition: form-data; name=\"file\"; filename=\""+ file.getName() + "\"" + LINE_END);
//				  sb1.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINE_END);
				  sb1.append("Content-Type:image/pjpeg" + LINE_END);
				  sb1.append(LINE_END);
				  outStream.write(sb1.toString().getBytes());
				  InputStream is = new FileInputStream(file);
				  byte[] bytes = new byte[1024];
				  int hasUpLoadCount = 0;
				  int len = 0;
				  while ((len = is.read(bytes)) != -1) {
					  outStream.write(bytes, 0, len);
				  	hasUpLoadCount += len;
				  	int total = (int) (hasUpLoadCount * 100 / totalLength ); //计算上传的进度
					  //设置进度条

				  }
				  is.close();
				  outStream.write(LINE_END.getBytes());

		      }
			  // 请求结束标志
			  byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
			  outStream.write(end_data);
			  outStream.flush();
			  /**
			   * 获取响应码 200=成功 当响应成功，获取响应的流
			   */
			  int res = conn.getResponseCode();
			  Log.e(TAG, "request success");
			  StringBuffer sb2 = new StringBuffer();

//			  InputStream input = conn.getInputStream();
//			  if(res == 200){
//				  int ss;
//				  while ((ss = input.read()) != -1) {
//					  sb2.append((char) ss);
//				  }
//			  }

			  BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			  sb2.append(br.readLine());

			  outStream.close();
			  br.close();
			  conn.disconnect();
			  result = sb2.toString();
			  Log.e(TAG, "result : " + result);
		  } catch (MalformedURLException e) {
		      e.printStackTrace();
		  }catch (FileNotFoundException e){
			  e.printStackTrace();
		  } catch (IOException e) {
		      e.printStackTrace();
		  }
		  return result;
	  }

	  /**
	   * 获取文件大小
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
