package com.obm.hy.export.utils;

import com.obm.hy.export.ExportConstants;
import com.obm.hy.export.bean.FileFormatBean;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;




/**
 * Created by TEST on 2017-06-08.
 */

public class TxtUtils {
    //分隔符为,
    public static <T> boolean exportData(String strSeparateCharFromvalue, List<FileFormatBean> list, List<T> listInfo1, String hastitle, String path) throws Exception {

        boolean result=false;
        if(path.isEmpty()){
            return false;
        }else {
//            int pathLen=path.length();
//            path=path.substring(0,pathLen-3);
            path=path+".txt";
        }

        if (hastitle.equals("Y")) {
            //写入首行
            writeFirstLine(list, strSeparateCharFromvalue,path);
        }

        List<T> listInfo = listInfo1;
        if (listInfo != null) {
            for (T scaninfo : listInfo) {
                StringBuilder sb = new StringBuilder();
                for (FileFormatBean fileFormat : list) {
                    //定长长度
                    int length = (fileFormat.getNexportLen() == 0) ? ExportConstants.EXPORT_LENGTH : fileFormat.getNexportLen();

                    String field = fileFormat.getCfieldName();
                    String format = fileFormat.getCFormatStr().toString();

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

                    try {
                        if(fieldValue==null){
                            fieldValue="";
                        }
                        sb = writeContent(strSeparateCharFromvalue, sb, fieldValue, length);
                    } catch (Exception e) {
                        e.printStackTrace();
                     }

                }

                //分隔符不是Enter时和定长时,截取最后一个字符。
                if (!strSeparateCharFromvalue.equals("\r\n") && !strSeparateCharFromvalue.equals("Fixed")) {
                    sb = sb.deleteCharAt(sb.length() - 1);
                    sb = sb.append("\r\n");
                }
                if (strSeparateCharFromvalue.equals("Fixed")) {
                    sb = sb.append("\r\n");
                }
                result= FileUtil.writeFile(path,sb.toString(),true);
            }
        }
        return result;
    }

    //判断是否是定长,写入数据
    private static StringBuilder writeContent(String strSeparateCharFromvalue, StringBuilder sb, String content, int length) {
        StringBuilder stringBuilder = sb;
        if (strSeparateCharFromvalue == "Fixed") {
            try {
                stringBuilder = stringBuilder.append(cutStr1(content, length));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            stringBuilder = stringBuilder.append(content + strSeparateCharFromvalue);
        }
        return stringBuilder;
    }

    //写入首行
    private static void writeFirstLine(List<FileFormatBean> list, String strSeparateCharFromvalue, String path) {

        StringBuilder firstLine = new StringBuilder();
        for (FileFormatBean fileFormat : list) {

            String value = fileFormat.getCtitle();

            //定长长度
            int length = (fileFormat.getNexportLen() == 0) ? ExportConstants.EXPORT_LENGTH : fileFormat.getNexportLen();
            firstLine = writeContent(strSeparateCharFromvalue, firstLine, value, length);


        }
        if (!strSeparateCharFromvalue.equals("Fixed")) {
            firstLine = firstLine.deleteCharAt(firstLine.length() - 1);
        }
        firstLine = firstLine.append("\r\n");

        FileUtil.writeFile(path,firstLine.toString());

    }




    //先判断字符串长度，和给出的限定长度
    //若大于等于给出的限定长度，则截去
    public static String cutStr1(String strs, int length) throws UnsupportedEncodingException {
        String str = strs;
        String result;
        byte[] bytes = str.getBytes();
        int len = bytes.length;
        if (len >= length) {

            return result = new String(bytes, 0, length);

        } else {
            str = str + " ";
            return cutStr1(str, length);

        }

    }



}




