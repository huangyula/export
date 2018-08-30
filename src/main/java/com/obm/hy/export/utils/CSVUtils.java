package com.obm.hy.export.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;

import com.obm.hy.export.bean.FileFormatBean;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



/**
 * Created by and on 2017-07-24.
 */

public class CSVUtils {
    public static File file;
    private static String f1;
//        // 这里显式地配置一下CSV文件的Header，然后设置跳过Header（要不然读的时候会把头也当成一条记录）
//        CSVFormat format = CSVFormat.DEFAULT.withHeader(FILE_HEADER).withSkipHeaderRecord();
//    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @SuppressLint("NewApi")
    public  static<E> boolean exportCSV(List<FileFormatBean> list, List<E> listInfo1, String strHastitle, String path) {
        boolean result=false;

        if(path.isEmpty()){
            return false;
        }else {

            path=path+".csv";
        }

        String[] FILE_HEADER=new String[list.size()];
        int i=0;
        for (FileFormatBean fileFormat : list) {

            String value = fileFormat.getCtitle();
            FILE_HEADER[i]=value;
            i++;
        }
       //判断是否包含标题
        if(strHastitle.equals("N")){
            FILE_HEADER=null;
        }
        CSVFormat format = CSVFormat.DEFAULT.withHeader(FILE_HEADER);
        List<E> listInfo = listInfo1;

        FileUtil.makeDirs(path);
        try(Writer out = new FileWriter(path);
            CSVPrinter printer = new CSVPrinter(out, format)) {
            if (listInfo != null && listInfo.size() > 0) {
                for (E scaninfo : listInfo) {
                    List<String> records = new ArrayList<>();
                    for (FileFormatBean fileFormat : list) {
                        String field = fileFormat.getCfieldName();
                        String cellVal = "";
                        String formatstr=fileFormat.getCFormatStr().toString();
                        //获取类名
                        Class c = scaninfo.getClass();
                        //获取属性名
                        Field f = ReflectUtil.getFieldByName(field, c);
                        //获取属性值
                        String fieldValue = ReflectUtil.getFieldValueByName(f.getName(), scaninfo).toString();

                        //判断是否是日期,是的话根据格式导出日期
                        if(fileFormat.getBisNumber().equals("D")){
                            String formatStr=fileFormat.getCFormatStr();
                            if(!formatStr.isEmpty()&&!fieldValue.isEmpty()){

                                Date date= DateUtil.transForDate(fieldValue);

                                String str= DateUtil.getTime(date,formatStr);
                                fieldValue=(str==""?fieldValue:str);
                            }
                        }else if(fileFormat.getBisNumber().equals("Y")){//是数值
                            String formatStr=fileFormat.getCFormatStr();
                            if(!formatStr.isEmpty()&&!fieldValue.isEmpty()){
                                Double value=Double.parseDouble(fieldValue);
                                String str= NumberUtils.getNumber(formatStr,value);
                                fieldValue=(str==""?fieldValue:str);
                            }
                        }

                        cellVal=fieldValue;

                        records.add(cellVal);

                    }
                    printer.printRecord(records);
                }
            }
            result=true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return result;
        }
    }
}
