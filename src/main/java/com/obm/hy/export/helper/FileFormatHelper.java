package com.obm.hy.export.helper;

import android.database.Cursor;

import com.obm.hy.export.bean.FileFormatBean;

import java.util.ArrayList;
import java.util.List;


// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "FILE_FORMAT".
 */
public class FileFormatHelper {


    String id;
    String ctype;
    String nseqno;
    String cfield_name;
    String cdisplay_name;
    String bdisplayflag;
    String b_data_flag;
    String ctitle;
    String bimportflag;
    String nimportno;
    String nimport_len;
    String bexportflag;
    String nexportno;
    String nexport_len;
    String bis_number;
    String c_cust_field;
    String c_format_str;
    String c_operator;
    String creduced;


    public static final String TABLENAME = "FILE_FORMAT";


    public static class FileProperties {
        public static final String FILETYPE = "FILETYPE";
        public static final String SEPARATER = "SEPARATER";
        public static final String HASTITLE = "HASTITLE";
        public static final String SHEET_NO="SHEET_NO";
        public static final String QUOTE = "QUOTE";
        public static final String NUMBEREXCEPT = "NUMBEREXCEPT";
        public static final String ALLOWNULL = "ALLOWNULL";
        public static final String FIXEDSEPARATER = "FIXEDSEPARATER";



        public static final String CONDITION = "CONDITION";
        public static final String NUMBERDIGIT = "NUMBERDIGIT";
        public static final String SELECTCONSEPARATE = "CONSEPARATE";
    }

    public static class FilePropertieValues {
        public static final String SEPARATERCOMMA = ",";
        public static final String SEPARATERAffiliate = ";";
        public static final String SEPARATERTAB = "Tab";
        public static final String SEPARATERFASTENLEN = "Fixed";
        public static final String SEPARATERRETURN = "CR";


        public static final char SEPARATERFASTENChar = 'F';
    }

    public static class FileFormats {
        public static final String FILETYPEEXCEL = "XLS";
        public static final String FILETYPECSV = "CSV";
        public static final String FILETYPETXT = "TXT";
        public static final String FILETYPEXML = "XML";
        public static final String FILETYPEWPSET = "WPS ET";
        public static final String FILETYPEDBF = "DBF";
    }


    public void addDatas2() {

        ArrayList<FileFormatBean> entityList = new ArrayList<FileFormatBean>();
        FileFormatBean lobj = null;
        long id = 1;
        int seqno;


        seqno = 1;
        lobj = new FileFormatBean(id++, "EXPORT", seqno++, "type", "类型", "Y", "Y", "类型", "N", 0, 0, "Y", 1, 0, "N", "", "", "", "");
        entityList.add(lobj);

        lobj = new FileFormatBean(id++, "EXPORT", seqno++, "barcode", "条码", "Y", "Y", "条码", "N", 0, 0, "Y", 2, 0, "N", "", "", "", "");
        entityList.add(lobj);


        lobj = new FileFormatBean(id++, "EXPORT", seqno++, "qty", "数量", "Y", "Y", "数量", "N", 0, 0, "Y", 3, 0, "N", "", "", "", "");
        entityList.add(lobj);


        lobj = new FileFormatBean(id++, "EXPORT", seqno++, "optime", "操作时间", "Y", "Y", "操作时间", "N", 0, 0, "Y", 4, 0, "N", "", "", "", "");
        entityList.add(lobj);

        lobj = new FileFormatBean(id++, "EXPORT", seqno++, "bill_no", "单号", "Y", "Y", "单号", "N", 0, 0, "Y", 5, 0, "N", "", "", "", "");
        entityList.add(lobj);

        lobj = new FileFormatBean(id++, "EXPORT", seqno++, "price", "价格", "Y", "Y", "价格", "N", 0, 0, "Y", 6, 0, "N", "", "", "", "");
        entityList.add(lobj);
        lobj = new FileFormatBean(id++, "EXPORT", seqno++, "remark1", "备注一", "Y", "Y", "备注一", "N", 0, 0, "N", 0, 0, "N", "", "", "", "");
        entityList.add(lobj);
        lobj = new FileFormatBean(id++, "EXPORT", seqno++, "remark2", "备注二", "Y", "Y", "备注二", "N", 0, 0, "N", 0, 0, "N", "", "", "", "");
        entityList.add(lobj);
        lobj = new FileFormatBean(id++, "EXPORT", seqno++, "remark3", "备注三", "Y", "Y", "备注三", "N", 0, 0, "N", 0, 0, "N", "", "", "", "");
        entityList.add(lobj);
        lobj = new FileFormatBean(id++, "EXPORT", seqno++, "remark4", "备注四", "Y", "Y", "备注四", "N", 0, 0, "N", 0, 0, "N", "", "", "", "");
        entityList.add(lobj);

        lobj = new FileFormatBean(id++, "EXPORT", seqno++, "remark5", "备注五", "Y", "Y", "备注五", "N", 0, 0, "N", 0, 0, "N", "", "", "", "");
        entityList.add(lobj);

        seqno = 1;
        lobj = new FileFormatBean(id++, "EXPORTOut", seqno++, "FILETYPE", "TXT", "Y", "Y", "TXT", "N", 0, 0, "N", 0, 0, "N", "", "", "", "");
        entityList.add(lobj);
        lobj = new FileFormatBean(id++, "EXPORTOut", seqno++, "SEPARATER", ",", "Y", "Y", ",", "Y", 0, 0, "N", 0, 0, "N", "", "", "", "");
        entityList.add(lobj);
        lobj = new FileFormatBean(id++, "EXPORTOut", seqno++, "HASTITLE", "Y", "Y", "Y", "N", "Y", 0, 0, "N", 0, 0, "N", "", "", "", "");
        entityList.add(lobj);
        lobj = new FileFormatBean(id++, "EXPORTOut", seqno++, "QUOTE", "", "Y", "Y", "", "Y", 0, 0, "N", 0, 0, "N", "", "", "", "");
        entityList.add(lobj);
        lobj = new FileFormatBean(id++, "EXPORTOut", seqno++, "NUMBEREXCEPT", "N", "Y", "Y", "N", "Y", 0, 0, "N", 0, 0, "N", "", "", "", "");
        entityList.add(lobj);
        lobj = new FileFormatBean(id++, "EXPORTOut", seqno++, "ALLOWNULL", "N", "Y", "Y", "N", "Y", 0, 0, "N", 0, 0, "N", "", "", "", "");
        entityList.add(lobj);
        lobj = new FileFormatBean(id++, "EXPORTOut", seqno++, "FIXEDSEPARATER", "", "Y", "Y", "", "Y", 0, 0, "N", 0, 0, "N", "", "", "", "");
        entityList.add(lobj);
        lobj = new FileFormatBean(id++, "EXPORTOut", seqno++, "NUMBERDIGIT", "2", "Y", "Y", "2", "Y", 0, 0, "N", 0, 0, "N", "", "", "", "");
        entityList.add(lobj);


        seqno = 1;
        lobj = new FileFormatBean(id++, "EXPORT2", seqno++, "type", "类型", "Y", "Y", "类型", "N", 0, 0, "Y", 1, 0, "N", "", "", "", "");
        entityList.add(lobj);
        lobj = new FileFormatBean(id++, "EXPORT2", seqno++, "sheet_no", "单号", "Y", "Y", "单号", "N", 0, 0, "Y", 2, 0, "N", "", "", "", "");
        entityList.add(lobj);
        lobj = new FileFormatBean(id++, "EXPORT2", seqno++, "item_id", "货号", "Y", "Y", "货号", "N", 0, 0, "Y", 3, 0, "N", "", "", "", "");
        entityList.add(lobj);
        lobj = new FileFormatBean(id++, "EXPORT2", seqno++, "qty", "数量", "Y", "Y", "数量", "N", 0, 0, "Y", 4, 0, "N", "", "", "", "");
        entityList.add(lobj);
        lobj = new FileFormatBean(id++, "EXPORT2", seqno++, "price", "价格", "Y", "Y", "价格", "N", 0, 0, "Y", 5, 0, "N", "", "", "", "");
        entityList.add(lobj);
        lobj = new FileFormatBean(id++, "EXPORT2", seqno++, "branch_no_in", "仓库一", "Y", "Y", "仓库", "N", 0, 0, "N", 0, 0, "N", "", "", "", "");
        entityList.add(lobj);
        lobj = new FileFormatBean(id++, "EXPORT2", seqno++, "branch_name_in", "仓库一", "Y", "Y", "仓库", "N", 0, 0, "N", 0, 0, "N", "", "", "", "");
        entityList.add(lobj);
        lobj = new FileFormatBean(id++, "EXPORT2", seqno++, "branch_no_out", "仓库二", "Y", "Y", "仓库", "N", 0, 0, "N", 0, 0, "N", "", "", "", "");
        entityList.add(lobj);
        lobj = new FileFormatBean(id++, "EXPORT2", seqno++, "branch_name_out", "仓库二", "Y", "Y", "仓库", "N", 0, 0, "N", 0, 0, "N", "", "", "", "");
        entityList.add(lobj);


        seqno = 1;
        lobj = new FileFormatBean(id++, "EXPORT2Out", seqno++, "FILETYPE", "TXT", "Y", "Y", "TXT", "N", 0, 0, "N", 0, 0, "N", "", "", "", "");
        entityList.add(lobj);
        lobj = new FileFormatBean(id++, "EXPORT2Out", seqno++, "SEPARATER", ",", "Y", "Y", ",", "Y", 0, 0, "N", 0, 0, "N", "", "", "", "");
        entityList.add(lobj);
        lobj = new FileFormatBean(id++, "EXPORT2Out", seqno++, "HASTITLE", "N", "Y", "Y", "N", "Y", 0, 0, "N", 0, 0, "N", "", "", "", "");
        entityList.add(lobj);
        lobj = new FileFormatBean(id++, "EXPORT2Out", seqno++, "QUOTE", "", "Y", "Y", "", "Y", 0, 0, "N", 0, 0, "N", "", "", "", "");
        entityList.add(lobj);
        lobj = new FileFormatBean(id++, "EXPORT2Out", seqno++, "NUMBEREXCEPT", "N", "Y", "Y", "N", "Y", 0, 0, "N", 0, 0, "N", "", "", "", "");
        entityList.add(lobj);
        lobj = new FileFormatBean(id++, "EXPORT2Out", seqno++, "ALLOWNULL", "N", "Y", "Y", "N", "Y", 0, 0, "N", 0, 0, "N", "", "", "", "");
        entityList.add(lobj);
        lobj = new FileFormatBean(id++, "EXPORT2Out", seqno++, "FIXEDSEPARATER", "", "Y", "Y", "", "Y", 0, 0, "N", 0, 0, "N", "", "", "", "");
        entityList.add(lobj);
        lobj = new FileFormatBean(id++, "EXPORT2Out", seqno++, "NUMBERDIGIT", "2", "Y", "Y", "2", "Y", 0, 0, "N", 0, 0, "N", "", "", "", "");
        entityList.add(lobj);

    }

    public void CursorToList(Cursor cursor,List<FileFormatBean> mList) {
        setColummName(cursor);
        if (cursor.moveToFirst()) {
            FileFormatBean fileFormat;
            do {
                fileFormat = new FileFormatBean();
                fileFormat.setId(cursor.getLong(cursor.getColumnIndex(id)));
                fileFormat.setCtype(cursor.getString(cursor.getColumnIndex(ctype)));
                fileFormat.setNseqno(cursor.getInt(cursor.getColumnIndex(nseqno)));
                fileFormat.setCfieldName(cursor.getString(cursor.getColumnIndex(cfield_name)));
                fileFormat.setCdisplayName(cursor.getString(cursor.getColumnIndex(cdisplay_name)));
                fileFormat.setBdisplayflag(cursor.getString(cursor.getColumnIndex(bdisplayflag)));
                fileFormat.setBDataFlag(cursor.getString(cursor.getColumnIndex(b_data_flag)));
                fileFormat.setCtitle(cursor.getString(cursor.getColumnIndex(ctitle)));
                fileFormat.setBimportflag(cursor.getString(cursor.getColumnIndex(bimportflag)));
                fileFormat.setNimportno(cursor.getInt(cursor.getColumnIndex(nimportno)));
                fileFormat.setNimportLen(cursor.getInt(cursor.getColumnIndex(nimport_len)));
                fileFormat.setBexportflag(cursor.getString(cursor.getColumnIndex(bexportflag)));
                fileFormat.setNexportno(cursor.getInt(cursor.getColumnIndex(nexportno)));
                fileFormat.setNexportLen(cursor.getInt(cursor.getColumnIndex(nexport_len)));
                fileFormat.setBisNumber(cursor.getString(cursor.getColumnIndex(bis_number)));
                fileFormat.setCCustField(cursor.getString(cursor.getColumnIndex(c_cust_field)));
                fileFormat.setCFormatStr(cursor.getString(cursor.getColumnIndex(c_format_str)));
                fileFormat.setCOperator(cursor.getString(cursor.getColumnIndex(c_operator)));
                fileFormat.setCreduced(cursor.getString(cursor.getColumnIndex(creduced)));
                mList.add(fileFormat);

            } while (cursor.moveToNext());
        }
    }

    public String getId() {
        return id;
    }

    public String getCtype() {
        return ctype;
    }

    public String getNseqno() {
        return nseqno;
    }

    public String getCfield_name() {
        return cfield_name;
    }

    public String getCdisplay_name() {
        return cdisplay_name;
    }

    public String getBdisplayflag() {
        return bdisplayflag;
    }

    public String getB_data_flag() {
        return b_data_flag;
    }

    public String getCtitle() {
        return ctitle;
    }

    public String getBimportflag() {
        return bimportflag;
    }

    public String getNimportno() {
        return nimportno;
    }

    public String getNimport_len() {
        return nimport_len;
    }

    public String getBexportflag() {
        return bexportflag;
    }

    public String getNexportno() {
        return nexportno;
    }

    public String getNexport_len() {
        return nexport_len;
    }

    public String getBis_number() {
        return bis_number;
    }

    public String getC_cust_field() {
        return c_cust_field;
    }

    public String getC_format_str() {
        return c_format_str;
    }

    public String getC_operator() {
        return c_operator;
    }

    public String getCreduced() {
        return creduced;
    }

    //获取表的列名称
    public void setColummName(Cursor cursor) {
        id=cursor.getColumnName(0);
        ctype = cursor.getColumnName(1);
        nseqno = cursor.getColumnName(2);
        cfield_name = cursor.getColumnName(3);
        cdisplay_name = cursor.getColumnName(4);
        bdisplayflag = cursor.getColumnName(5);
        b_data_flag = cursor.getColumnName(6);
        ctitle = cursor.getColumnName(7);
        bimportflag = cursor.getColumnName(8);
        nimportno = cursor.getColumnName(9);
        nimport_len = cursor.getColumnName(10);
        bexportflag = cursor.getColumnName(11);
        nexportno = cursor.getColumnName(12);
        nexport_len = cursor.getColumnName(13);
        bis_number = cursor.getColumnName(14);

        c_cust_field = cursor.getColumnName(15);
        c_format_str = cursor.getColumnName(16);
        c_operator = cursor.getColumnName(17);
        creduced = cursor.getColumnName(18);
    }


}
