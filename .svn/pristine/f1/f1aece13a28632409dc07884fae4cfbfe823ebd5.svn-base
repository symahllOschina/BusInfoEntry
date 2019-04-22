package com.wdkj.dkhdl.photo.util;

import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileStorage {

    private File cropIconDir;
    private File iconDor;

    public FileStorage(){
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            File external = Environment.getExternalStorageDirectory();
            //avater_path:要和res/xml/provider_paths.xml文件中定义的path值一样
            String rootDir = "/" + "avater_path";
            cropIconDir = new File(external,rootDir + "/crop");
            if(!cropIconDir.exists()){
                cropIconDir.mkdirs();
            }
            iconDor = new File(external,rootDir + "/icon");
            if(!iconDor.exists()){
                iconDor.mkdirs();
            }
        }
    }

    /**
     *
     */
    public File createCropFile(){
        String fileName = "";
        if(cropIconDir != null){
            fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".jpg";
        }
        return new File(iconDor,fileName);
    }


    public File createIconFile(){
        String fileName = "";
        if(iconDor != null){
            fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".jpg";
        }
        return new File(iconDor,fileName);
    }

}
