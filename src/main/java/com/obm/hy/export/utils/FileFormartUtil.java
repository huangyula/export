/**
 * Copyright (c) 2012-2013, Michael Yang 杨福海 (www.yangfuhai.com).
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.obm.hy.export.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.obm.hy.export.bean.FileFormatBean;
import com.obm.hy.export.helper.DBHelper;
import com.obm.hy.export.helper.FileFormatHelper;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 * 文件格式工具类
 */
public class FileFormartUtil {

    private static String strFileTypevalue, strfileformatFrom;
    private int sheet_no = 0;
    private static boolean hasTitleFrom;
    private static String strSeparateCharFromvalue;
    private static List<FileFormatBean> mList = new ArrayList<FileFormatBean>();
    FileFormatHelper formatHelper = new FileFormatHelper();


    private static Context mContext;
    private static String mTableName;
    private static String dbname;
    DBHelper helper;
    static SQLiteDatabase db;
    boolean result = false;
    Handler mHandler;

    /**
     * 导入文件
     * @param dbname    数据库名称
     * @param version  数据库版本
     * @param tableName 文件格式设置表
     * @param fileTypeName 文件类型名称
     * @param fileType 文件类型编码
     * @param filepath 文件完整路徑
     * @param filename     文件名称
     * @param delete       是否删除(覆盖)之前数据
     * @param mHandler     Handler
     * @return 是否成功
     */
    public void ImportFile(Context context, String dbname, int version, String tableName, String fileTypeName, String fileType,String filepath, String filename,boolean delete, Handler mHandler) throws IOException {
        mContext = context;
        mTableName = tableName;
        dbname = dbname;
        this.mHandler=mHandler;
        helper.DB_NAME = dbname;
        helper.DB_VERSION = version;
        helper = new DBHelper(mContext);
        db = helper.getWritableDatabase();

        getInData(fileType);

        if (strfileformatFrom.equals(FileFormatHelper.FileFormats.FILETYPETXT))//Txt文件读取
        {
            try {
                ImportFileText(fileType, filepath,filename, delete,mHandler);
            }catch (Exception e){
                e.printStackTrace();
            }

        } else if (strfileformatFrom.equals(FileFormatHelper.FileFormats.FILETYPEEXCEL)) {
            try {
                ImportFileXLS(fileType,filepath,filename,delete, mHandler);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(strfileformatFrom.equals(FileFormatHelper.FileFormats.FILETYPECSV)){
            try {
                strSeparateCharFromvalue=",";
                ImportFileCSV(fileType,filepath,filename, delete,mHandler);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void ImportFileXLS(final String fileType, final String filepath,final String filename,final boolean detele, Handler handler) {
        new Thread(new Runnable() {
            public void run() {
                int index = 0;
                try {
                    Thread.sleep(50);
                    long current = System.currentTimeMillis();
                    String path = filepath + ".xls";
                    File file = new File(path);
                    if (!file.exists()) {
                        Message message = new Message();
                        message.what = 500;
                        message.obj = filename+".xls"+"不存在";
                        mHandler.sendMessage(message);
                        return;

                    }
                    HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
                    System.out.println("总数:" + workbook.getNumberOfSheets());
                    //TODO
                    //获取文件总共的工作表
                    if (sheet_no > workbook.getNumberOfSheets()) {
                        Message message = new Message();
                        message.what = 500;
                        message.obj = filename+".xls"+"工作表" + sheet_no + " 不存在";
                        mHandler.sendMessage(message);
                        return;
                    }
                    HSSFSheet hssfSheet = workbook.getSheetAt(sheet_no);

                    db.beginTransaction();
                    //删除数据
                    if(detele){
                        if (db != null) {
                            db.execSQL("delete from "+fileType);
                        }
                    }
                    // 得到第一列第一行的单元格
                    int rownum = hssfSheet.getLastRowNum() + 1;// 得到行数
                    Log.e("FileUtils", "行数：" + rownum);
                    String title = "";

                    for (int i = 0; i < rownum; i++) {// 行

                        index = i;
                        Row row = hssfSheet.getRow(i);//第几行
                        if (i==251){
                            System.out.println("251");
                        }
                        if (i == 0 && hasTitleFrom) {

                        } else {
                            int colnum = row.getLastCellNum();//该行的列数
                            String values[] = new String[colnum];
                            for (int j = 0; j < colnum; j++) {
                                Cell cell = row.getCell(j);
                                String result;
                                if (cell == null) {
                                    result = "";
                                } else {
                                    result = cell.toString().trim();
                                }
                                values[j] = result;//单元格的值

                            }

                            insertDataInfo(fileType, values, values.length);
                        }

                    }
                    db.setTransactionSuccessful();
                    db.endTransaction();
                    db.close();
                    Message message = Message.obtain();
                    message.what = 400;
                    message.obj = filename+".xls"+"加载成功";
                    mHandler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    Message message = new Message();
                    message.what = 500;
                    message.obj = filename+".xls"+"第" + (index + 1) + "/行出错";
                    mHandler.sendEmptyMessage(500);
                } catch (InterruptedException e) {
                    result = false;
                    e.printStackTrace();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /**
     * 导入文本文件
     *
     * @param fileType 文件类型编码
     * @param filename 文件夹完整绝对路径
     * @param mHandler Handler
     * @return 是否成功
     */
    private void ImportFileText(final String fileType,final String filepath, final String filename,final boolean delete, final Handler mHandler) throws IOException {

        new Thread(new Runnable() {
            int index=0;
            @Override
            public void run() {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String path = filepath + ".txt";
                File file = new File(path);
                if (!file.exists()) {
                    Message message = new Message();
                    message.what = 500;
                    message.obj = filename+".txt"+"不存在";
                    mHandler.sendMessage(message);
                    result = false;
                    return;

                }
                InputStreamReader isr = null;
                try {
                    isr = new InputStreamReader(new FileInputStream(path), "GBK");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                BufferedReader br = new BufferedReader(isr);

                try {
                    db.beginTransaction();
                    //删除数据
                    if(delete){
                        if (db != null) {
                            db.execSQL("delete from "+fileType);
                        }
                    }
                    String strData;
                    int rowno = 0;
                    int insertcount = 0;
                    int errorcount = 0;
                    int readcount;
                    String[] values = {};


                    while ((strData = br.readLine()) != null) {//读取每一行数据
                        if (rowno % 100 == 0) {
                            //System.Windows.Forms.Application.DoEvents();
                        }
                        rowno++;
                        index=rowno;
                        if ((rowno == 1) && hasTitleFrom) {
                            //包含标题，则第一行不分析
                            continue;
                        }

                        //解析每行数据,获得该行数据数组长度
                        values = GetDataFormatDatas(strSeparateCharFromvalue, strData, values);
                        if (values != null) {
                            readcount = values.length;
                        } else {
                            readcount = 0;
                        }

                        if (readcount > 0)//解析成功
                        {

                            if (insertDataInfo(fileType, values, readcount) > 0)//将数据写入db
                                errorcount++;
                            else
                                insertcount++;

                        }

                    }
                    db.setTransactionSuccessful();
                    db.endTransaction();
                    db.close();
                    Message message = Message.obtain();
                    message.what = 400;
                    message.obj = filename+".txt"+"加载成功";
                    mHandler.sendMessage(message);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    Message message = new Message();
                    message.what = 500;
                    message.obj = filename+".txt"+"第" + (index) + "/行出错";
                    mHandler.sendEmptyMessage(500);

                }
                finally {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();

    }
    private void ImportFileCSV(final String fileType,final String filepath, final String filename,final boolean delete, final Handler mHandler) throws IOException {

        new Thread(new Runnable() {
            int index=0;
            @Override
            public void run() {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String path = filepath + ".csv";
                File file = new File(path);
                if (!file.exists()) {
                    Message message = new Message();
                    message.what = 500;
                    message.obj = filename+".csv"+"不存在";
                    mHandler.sendMessage(message);
                    result = false;
                    return;

                }
                InputStreamReader isr = null;
                try {
                    isr = new InputStreamReader(new FileInputStream(path), "GBK");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                BufferedReader br = new BufferedReader(isr);

                try {
                    db.beginTransaction();
                    //删除数据
                    if(delete){
                        if (db != null) {
                            db.execSQL("delete from "+fileType);
                        }
                    }
                    String strData;
                    int rowno = 0;
                    int insertcount = 0;
                    int errorcount = 0;
                    int readcount;
                    String[] values = {};


                    while ((strData = br.readLine()) != null) {//读取每一行数据
                        if (rowno % 100 == 0) {
                            //System.Windows.Forms.Application.DoEvents();
                        }
                        rowno++;
                        index=rowno;
                        if ((rowno == 1) && hasTitleFrom) {
                            //包含标题，则第一行不分析
                            continue;
                        }

                        //解析每行数据,获得该行数据数组长度
                        values = GetDataFormatDatas(strSeparateCharFromvalue, strData, values);
                        if (values != null) {
                            readcount = values.length;
                        } else {
                            readcount = 0;
                        }

                        if (readcount > 0)//解析成功
                        {

                            if (insertDataInfo(fileType, values, readcount) > 0)//将数据写入db
                                errorcount++;
                            else
                                insertcount++;

                        }

                    }
                    db.setTransactionSuccessful();
                    db.endTransaction();
                    db.close();
                    Message message = Message.obtain();
                    message.what = 400;
                    message.obj = filename+".csv"+"加载成功";
                    mHandler.sendMessage(message);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    Message message = new Message();
                    message.what = 500;
                    message.obj = filename+".csv"+"第" + (index) + "/行出错";
                    mHandler.sendEmptyMessage(500);

                }
                finally {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();

    }


    public static String GetSqlString(String Value) {
        if (Value == null)
            return "null";
        String str = Value.trim();
        str = str.replace("'", "''");
        return "'" + str + "'";
    }


    public static int insertDataInfo(String filetype, String[] values, int valueslen) {
        if (values.length == 0)
            return 1;

        String strSql = "", strhead = "", strbody = "";
        int col = 0;
        int colcount = 0;
        String strfield, strvalue, strisnumber;


        col = 0;
        colcount = 0;
        int index = 0;
        try {

            for (int i = 0; i < mList.size(); i++) {

                if (mList.get(i).getBDataFlag().toString().equals("Y"))//不是占位符
                {
                    strfield = mList.get(i).getCfieldName().toString();//获得字段名称,例如：item_id,item_subid,Barcode

                    //重要算法
                    //strvalue = values[col];//如何取值要改此处
                    try {
                        index = mList.get(i).getNimportno().intValue();//获取该字段的导入序号
                    } catch (Exception ex) {
                        index = 1;
                    }
                    if (index - 1 >= valueslen || index - 1 < 0)//导出序号与总共要导出的字段数有矛盾,则跳过
                        continue;

                    strvalue = values[index - 1];//如何取值要改此处,取得值(item_id 的值)


                    strvalue = strvalue.replace("\"", "");
                    strvalue = strvalue.trim();


                    strisnumber = mList.get(i).getBisNumber().toString();//判断该字段是否是数值

                    //是否数值
                    if (strisnumber.equals("Y"))//数值
                    {
                        strvalue = GetSqlString(strvalue);
                    } else {//不是数值

                        if (strisnumber.equals("D"))//日期
                        {
                            if (strvalue.length() > 0)
                                strvalue = strvalue.replace(" 00:00:00", "");
                        }

                        strvalue = GetSqlString(strvalue);
                    }

                    if (colcount > 0) {
                        strhead += "," + strfield;
                        strbody += "," + strvalue;
                    } else {
                        strhead = strfield;
                        strbody = strvalue;
                    }

                    colcount++;

                }
                col++;
            }
            if (colcount == 0)
                return 1;
            strSql = "Insert into " + filetype + " (" + strhead + ") ";
            strSql = strSql + "values ( " + strbody + ")";
            if (db != null) {
                db.execSQL(strSql);
            }
        } catch (Exception ee) {
            System.out.println(ee + "  " + ee.getMessage());
            ee.printStackTrace();
            return 2;
        }
        return 0;
    }


    public static String[] GetDataFormatDatas(String separator, String strData, String[] values) {

        if (strData == null || strData.length() == 0)
            return null;

        if (mList.size() == 0)
            return null;

        if (!separator.equals(String.valueOf(FileFormatHelper.FilePropertieValues.SEPARATERFASTENChar)))//非定长
        {
            values = strData.split(separator);//将每行数据分割成一个数组
            return values;
            //返回数组长度,即每行数据的字段数
        } else {
            //定长,以下为定长部分处理
            values = new String[mList.size()];//要导入的总共有多少字段

            int col = 0, len;
            int colcount = 0;
            String strtemp;

            col = 0;
            colcount = 0;
            byte[] bstrLength = strData.getBytes();//将每行字符串转化为字节数组
            for (int i = 0; i < mList.size(); i++) {

                len = mList.get(i).getNimportLen().intValue();//获取每个字段的导入长度
                if (bstrLength.length >= colcount + len)
                    strtemp = new String(bstrLength, colcount, len);// inputstr.Substring(0, len);
                else
                    strtemp = new String(bstrLength, colcount, len - colcount); //intlen);// inputstr.Substring(0, len);

                values[col] = strtemp;

                colcount += len;
                col++;
            }

            return values;
        }
    }

    public static char getSeparator(String strtemp) {
        if (strtemp.length() < 1)
            return ',';
        if (strtemp.equals(FileFormatHelper.FilePropertieValues.SEPARATERCOMMA))
            return ',';
        if (strtemp.equals(FileFormatHelper.FilePropertieValues.SEPARATERAffiliate))
            return ';';
        if (strtemp.equals(FileFormatHelper.FilePropertieValues.SEPARATERTAB))
            return (char) 9;
        if (strtemp.equals(FileFormatHelper.FilePropertieValues.SEPARATERFASTENLEN))
            return FileFormatHelper.FilePropertieValues.SEPARATERFASTENChar;
        if (strtemp.equals(FileFormatHelper.FilePropertieValues.SEPARATERRETURN))
            return '\r';


        return strtemp.charAt(0);
    }


    private void getInData(String strFileType) {
        FileFormatBean fileFormat2;

        Cursor cursor = db.rawQuery("select * from " + mTableName, new String[]{});
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            System.out.println(i + "--" + cursor.getColumnName(i));
        }

        formatHelper.setColummName(cursor);
        //查询导入格式,分隔符,是否包含标题
        cursor = db.rawQuery("select * from " + mTableName + " where " + formatHelper.getCtype() + " =? and " + formatHelper.getBdisplayflag() + " =?",
                new String[]{strFileType + "In", "Y"});

        String field, value;
        hasTitleFrom = false;
        strSeparateCharFromvalue = ",";
        strfileformatFrom = "";

        if (cursor.moveToFirst()) {
            do {
                field = cursor.getString(cursor.getColumnIndex(formatHelper.getCfield_name()));
                value = cursor.getString(cursor.getColumnIndex(formatHelper.getCdisplay_name()));
                if (field.equals(FileFormatHelper.FileProperties.FILETYPE))//文件格式
                {
                    strfileformatFrom = value;
                } else if (field.equals(FileFormatHelper.FileProperties.SEPARATER))//分隔符
                {
                    strSeparateCharFromvalue = String.valueOf(getSeparator(value));
                } else if (field.equals(FileFormatHelper.FileProperties.HASTITLE))//是否有标题
                {
                    hasTitleFrom = value.equals("Y");
                } else if (field.equals(FileFormatHelper.FileProperties.SHEET_NO))//工作表序号
                {
                    sheet_no = Integer.parseInt(value);
                }
            } while (cursor.moveToNext());
        }

        cursor = null;
        //查找哪些字段要导出
        cursor = db.query(mTableName, null, formatHelper.getCtype() + " =? and " + formatHelper.getBimportflag() + " =? ",
                new String[]{strFileType, "Y"}, null, null, formatHelper.getNimportno() + " asc");
        mList.clear();
        formatHelper.CursorToList(cursor, mList);

        for (FileFormatBean f : mList) {
            System.out.println(f.getCfieldName());
        }

        cursor.close();


    }

}