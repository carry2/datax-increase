package com.alibaba.datax.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class PropertiesUtil {
    public static Properties constantPro;
    private static Logger log = LoggerFactory.getLogger(PropertiesUtil.class);
     private static String TIMEPATH="/data/datax/plugin/reader/mysqlreader/time.properties";
    // private static String TIMEPATH="D://time.properties";

    static {
        File F=new File(TIMEPATH);
        try {
        InputStream isConstant = new FileInputStream(F);
        constantPro = new Properties();
            constantPro.load(isConstant);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getConstantValue(String key) {
        return (String) constantPro.get(key);
    }

    public static void setProperty(Map<String, String> data) {
        try {
//接下来就可以随便往配置文件里面添加内容了
//          props.setProperty(key, value);
            if (data != null) {
                Iterator<Map.Entry<String, String>> iter = data.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = iter.next();
                    constantPro.setProperty(entry.getKey().toString(), entry.getValue().toString());
                }
            }
//在保存配置文件之前还需要取得该配置文件的输出流，<span style="color: #ff0000; font-size: medium;">切记，</span>如果该项目是需要导出的且是一个非WEB项目，则该配置文件应当放在根目录下，否则会提示找不到配置文件
            OutputStream out = new FileOutputStream(TIMEPATH);
//最后就是利用Properties对象保存配置文件的输出流到文件中;
            constantPro.store(out, null);
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //获取并更新时间
    public static String getLastTime(String dataName ,String tableName) {
        //获取最后时间
        String LASTTIME=getConstantValue(dataName+"/"+tableName+"/"+"LASTTIME");
        //如果为空说明是第一次 默认全量加载 时间默认值为0
        if(LASTTIME==null){
            LASTTIME="0";
        }
        return LASTTIME;
    }


    public static void upTime(String dataName ,String tableName,String formatDate) {
        //获取最后时间
        String LASTTIME=getConstantValue(dataName+"/"+tableName+"/"+"LASTTIME");
        //如果为空说明是第一次 默认全量加载 时间默认值为0
        if(LASTTIME==null){
            LASTTIME="0";
        }

        Map<String ,String>  data=new HashMap<String, String>();
        //把当前时间作为最后时间 把上次最后时间作为上次时间
        data.put(dataName+"/"+tableName+"/"+"LASTTIME",formatDate);
        data.put(dataName+"/"+tableName+"/"+"SECONDTIME",LASTTIME);
        setProperty(data);
    }

//    public static void main (String [] args){
//      String S=  "jdbc:mysql://192.168.0.68:3306/datacenter_online?yearIsDateType=false&zeroDateTimeBehavior=convertToNull&tinyInt1isBit=false&rewriteBatchedStatements=true";
//        String[] split = S.split("\\?");
//        String S2=  "jdbc:mysql://192.168.0.68:3306/datacenter_online";
//        String[] split2 = S2.split("\\?");
//
//        String dataNameFromJdbcUrl = DataBaseType.parseDataNameFromJdbcUrl(split[0]);
//        String dataNameFromJdbcUrl2 = DataBaseType.parseDataNameFromJdbcUrl(split2[0]);
//            System.out.print(dataNameFromJdbcUrl);
//        System.out.print(dataNameFromJdbcUrl2);
//    }
}
