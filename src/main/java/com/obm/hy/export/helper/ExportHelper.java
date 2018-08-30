package com.obm.hy.export.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.obm.hy.export.bean.FileFormatBean;
import com.obm.hy.export.utils.CSVUtils;
import com.obm.hy.export.utils.ExcelUtils;
import com.obm.hy.export.utils.TxtUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Huangyu on 2017-07-20.
 * 导出工具类
 */

public  class ExportHelper {


    private static String strFileTypevalue,strfileformatFrom,strHastitle;//文件类型,文件格式,包含标题
    private static String strSeparateCharFromvalue;//分隔符
    private static List<FileFormatBean> mList = new ArrayList<FileFormatBean>();
    FileFormatHelper formatHelper = new FileFormatHelper();
    private static Context mContext;
    private static String mTableName;
    private static String dbname;
    DBHelper helper;
    SQLiteDatabase db;

    /**
     *
     * @param context 上下文
     * @param dbname    数据库名称
     * @param version   数据库版本
     * @param tableName 文件格式设置表
     * @param fileType  导入或导出
     * @param path  导出路径
     * @param list1 导出的数据
     * @param <E>
     * @return
     * @throws java.io.IOException
     */
    public  <E> boolean OutputFile(Context context,String dbname,int version,String tableName ,String fileType,String path,List<E> list1) throws java.io.IOException{

        mContext=context;
        mTableName=tableName;
        dbname=dbname;
        helper.DB_NAME=dbname;
        helper.DB_VERSION=version;
        helper = new DBHelper(mContext);
        db=helper.getWritableDatabase();

        getOutData(fileType);


        if (strfileformatFrom.equals(FileFormatHelper.FileFormats.FILETYPETXT))
        {
            OutputFileText(mList,list1,strHastitle,path);
        }
        if(strfileformatFrom.equals(FileFormatHelper.FileFormats.FILETYPEEXCEL)){
            OutputFileExcel(mList,list1,strHastitle,path);
        }
        if(strfileformatFrom.equals(FileFormatHelper.FileFormats.FILETYPECSV)){
            OutputFileCSV(mList,list1,strHastitle,path);
        }
        return true;
    }

    /**
     *
     * @return
     * @throws java.io.IOException
     */
    public static <E> void OutputFileText(List<FileFormatBean> list,List<E> list1,String strHastitle,String path)  throws java.io.IOException {
        try {
            TxtUtils.exportData(strSeparateCharFromvalue,list,list1,strHastitle,path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <E> void OutputFileExcel(List<FileFormatBean> list, List<E> list1, String strHastitle, String path)  throws java.io.IOException {
        ExcelUtils.exportExcel(list,list1,strHastitle,path);
    }


    public static <E> void OutputFileCSV(List<FileFormatBean> list, List<E> list1, String hastitle, String path)  throws java.io.IOException {
        CSVUtils.exportCSV(list,list1,strHastitle,path);
    }


    public  void getOutData(String strFileType){

        String field,value;
        strSeparateCharFromvalue =",";
        strfileformatFrom ="";
        strHastitle="";


        Cursor cursor = db.rawQuery("select * from " + mTableName, new String[]{});
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            System.out.println(i + "--" + cursor.getColumnName(i));
        }

        formatHelper.setColummName(cursor);

        cursor = db.rawQuery("select * from " + mTableName + " where " + formatHelper.getCtype() + " =? and " + formatHelper.getBdisplayflag() + " =?",
                new String[]{strFileType + "Out", "Y"});


        if (cursor.moveToFirst()) {
            do {
                field=cursor.getString(cursor.getColumnIndex(formatHelper.getCfield_name()));
                value=cursor.getString(cursor.getColumnIndex(formatHelper.getCdisplay_name()));
                if(field.equals(FileFormatHelper.FileProperties.FILETYPE))//文件格式
                {
                    strfileformatFrom=value;
                }
                else if(field.equals(FileFormatHelper.FileProperties.SEPARATER))//分隔符
                {
                    strSeparateCharFromvalue = String.valueOf(getSeparator(value));
                }
                else if(field.equals(FileFormatHelper.FileProperties.HASTITLE))//是否有标题
                {
                    strHastitle=value;
                }
            }while (cursor.moveToNext());
        }

        cursor=null;

        cursor=db.query(mTableName,null,formatHelper.getCtype()+" =? and "+formatHelper.getBexportflag()+" =? ",
                new String[]{strFileType,"Y"},null,null,formatHelper.getNexportno()+" asc");



        mList.clear();
       formatHelper.CursorToList(cursor,mList);

        for(FileFormatBean f:mList){
            System.out.println(f.getCfieldName());
        }

        cursor.close();
        if(db.isOpen()){
            db.close();
        }

    }





    public static String getSeparator(String strtemp)
    {
        if (strtemp.length()<1)
            return ",";
        if (strtemp.equals(FileFormatHelper.FilePropertieValues.SEPARATERCOMMA))
            return ",";
        if (strtemp.equals(FileFormatHelper.FilePropertieValues.SEPARATERAffiliate))
            return ";";
        if (strtemp.equals(FileFormatHelper.FilePropertieValues.SEPARATERTAB))
            return "\t";
        if (strtemp.equals(FileFormatHelper.FilePropertieValues.SEPARATERFASTENLEN))
            return FileFormatHelper.FilePropertieValues.SEPARATERFASTENLEN;
        if (strtemp.equals(FileFormatHelper.FilePropertieValues.SEPARATERRETURN))
            return "\r\n" ;


        return strtemp.substring(0,1);
    }


}
