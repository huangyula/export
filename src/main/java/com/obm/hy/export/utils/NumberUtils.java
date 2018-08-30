package com.obm.hy.export.utils;

import java.text.DecimalFormat;

/**
 * Created by and on 2017-08-24.
 */

public class NumberUtils {

    public static String getNumber(String str, Double number) {
        //先判断是哪种形式
        if (str.startsWith("f")) {//保留小数位数
            return setF(str, number);
        } else if (str.startsWith("n")) {//使用千分符并保留小数位数
            return setN(str, number);
        } else if (str.equals("p") || str.equals("P")) {//百分比
            return setP(str,number);
        }
        return "";
    }












    public static String setF(String str,Double number){
        String pattern="#.";
        //先判断是是否是f1至f4
        int len=str.length();
        if(2==len){
            if(str.startsWith("f")){
                switch (str.charAt(1)){
                    case 49:
                        pattern=pattern+"0";
                        break;
                    case 50:
                        pattern=pattern+"00";
                        break;
                    case 51:
                        pattern=pattern+"000";
                        break;
                    case 52:
                        pattern=pattern+"0000";
                        break;

                }
            }
        }else if(str.equals("f")){//长度为1
            pattern="#.00";
        }

        if(pattern.equals("#.")){
            return "";
        }else{
            DecimalFormat df=new DecimalFormat(pattern);
            return  df.format(number);
        }
    }


    public static String setN(String str,Double number) {
        //先判断是是否是n1至n4
        String pattern = "";
        int len = str.length();
        if (2 == len) {
            if (str.startsWith("n")) {
                switch (str.charAt(1)) {
                    case 49:
                        pattern = "###,##0.0";
                        break;
                    case 50:
                        pattern = "###,##0.00";
                        break;
                    case 51:
                        pattern = "###,##0.000";
                        break;
                    case 52:
                        pattern = "###,##0.0000";
                        break;

                }
            }
        } else if (str.equals("n")) {//长度为1
            pattern = "###,##0.00";
        }

        if(pattern.equals("")){
            return "";
        }else{
            DecimalFormat df=new DecimalFormat(pattern);
            return  df.format(number);
        }

    }

    public static String setP(String str,Double number) {

        DecimalFormat df = new DecimalFormat("#.00%");

        return df.format(number);



    }

}
