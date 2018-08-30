package com.obm.hy.export.fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.obm.hy.export.ExportConstants;
import com.obm.hy.export.R;
import com.obm.hy.export.adapter.FormatInListAdapter;
import com.obm.hy.export.base.BaseFragment;
import com.obm.hy.export.bean.FileFormatBean;
import com.obm.hy.export.helper.DBHelper;
import com.obm.hy.export.helper.FileFormatHelper;
import com.obm.hy.export.widget.CustomAlertDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class FormatInFragment extends BaseFragment implements View.OnClickListener  {


    private EditText et_SeparateCharTo,et_sheet_no;
    private TextView tv_filetype, tv_fileformatTo,tv_sheet_no;
    private CheckBox ckb_hasTitleto;

    private ListView lv_data;
    private String strFileTypevalue, strSeparateCharTovalue;
    private Button btnSave;

    private List<FileFormatBean> mList = new ArrayList<FileFormatBean>();
    private List<FileFormatBean> mPropertiesList = new ArrayList<FileFormatBean>();
    FileFormatHelper formatHelper = new FileFormatHelper();

    private FormatInListAdapter mAdapter;
    private String dbname;
    private String tablename;
    private int dbversion;
    DBHelper helper;
    SQLiteDatabase db;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1001:
                    break;
                case 1002:
                    mAdapter.notifyDataSetChanged();
                    break;
                case 1008:
                    break;
                case 1009:
                    showShortToast((String) msg.obj);
                    break;

            }
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_format_in, null);
        dbname = getActivity().getIntent().getStringExtra(ExportConstants.EXPORT_DB_NAME);
        tablename = getActivity().getIntent().getStringExtra(ExportConstants.EXPORT_TABLE_NAME);
        dbversion = getActivity().getIntent().getIntExtra(ExportConstants.EXPORT_DB_VERSION, 1);
        DBHelper.DB_NAME = dbname;
        DBHelper.DB_VERSION = dbversion;
        helper = new DBHelper(getActivity());
        initView(view);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        initData();
                    }
                });
            }
        }, 300);


        setListener(view);
        return view;
    }

    private void initView(View view) {
        et_SeparateCharTo = (EditText)view.findViewById(R.id.et_SeparateCharTo);//分隔符
        tv_filetype = (TextView)view.findViewById(R.id.tv_filetype);//文件类型
        tv_fileformatTo = (TextView)view. findViewById(R.id.tv_fileformatTo);//文件格式
        lv_data = (ListView) view.findViewById(R.id.lv_data);
        btnSave = (Button)view. findViewById(R.id.btn_save);
        ckb_hasTitleto = (CheckBox)view.findViewById(R.id.ckb_hasTitleto);
        tv_sheet_no=(TextView)view.findViewById(R.id.tv_sheetno);
        et_sheet_no=(EditText)view.findViewById(R.id.et_sheetno);

    }

    public void initData() {
        //文件类型
        final String[] items = getResources().getStringArray(R.array.file_type_in);
        final String[] itemvalues = getResources().getStringArray(R.array.file_type_value_in);
        tv_filetype.setText(items[0]);
        strFileTypevalue = itemvalues[0];
        showData(strFileTypevalue);
    }

    private void setListener(View view) {
        view.findViewById(R.id.ll_type).setOnClickListener(this);
        view.findViewById(R.id.ll_fileformatTo).setOnClickListener(this);
        view.findViewById(R.id.ll_SeparateCharTo).setOnClickListener(this);
        btnSave.setOnClickListener(this);
        ckb_hasTitleto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        lv_data.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_FLING || scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                    clearFocus();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.ll_type) {//文件类型
            final String[] items = getResources().getStringArray(R.array.file_type_in);
            CustomAlertDialog.showListDialog(getActivity(), getString(R.string.dialog_file_type), items, new CustomAlertDialog.IAlertListDialogItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    final String[] itemvalues = getResources().getStringArray(R.array.file_type_value_in);
                    tv_filetype.setText(items[position]);
                    strFileTypevalue = itemvalues[position];
                    Message msg = new Message();
                    msg.what = 1001;
                    msg.obj = strFileTypevalue;
                    mHandler.sendMessage(msg);
                    showData(strFileTypevalue);
                }
            });
        } else if (id == R.id.ll_fileformatTo) {//文件格式


            final String[] items3 = getResources().getStringArray(R.array.file_format_in);

            CustomAlertDialog.showListDialog(getActivity(), getString(R.string.dialog_file_format), items3, new CustomAlertDialog.IAlertListDialogItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    tv_fileformatTo.setText(items3[position]);

                    boolean showlength2 = tv_fileformatTo.getText().toString().trim().equals(FileFormatHelper.FileFormats.FILETYPETXT)
                            && (strSeparateCharTovalue.equals(FileFormatHelper.FilePropertieValues.SEPARATERFASTENLEN));
                    mAdapter.setShowLength(showlength2);
                    mHandler.sendEmptyMessage(1002);

                }
            });
        } else if (id == R.id.ll_SeparateCharTo) {//分隔符


            final String[] items4 = getResources().getStringArray(R.array.file_SeparateChar);

            // final String[] item =  R.array.file_type mList.toArray(new String[mList.size()]);
            CustomAlertDialog.showListDialog(getActivity(), getString(R.string.dialog_select_sperator), items4, new CustomAlertDialog.IAlertListDialogItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    final String[] itemvalues4 = getResources().getStringArray(R.array.file_SeparateChar_value);
                    et_SeparateCharTo.setText(items4[position]);
                    strSeparateCharTovalue = itemvalues4[position];

                    boolean showlength2 = tv_fileformatTo.getText().toString().trim().equals(FileFormatHelper.FileFormats.FILETYPETXT)
                            && (strSeparateCharTovalue.equals(FileFormatHelper.FilePropertieValues.SEPARATERFASTENLEN));
                    mAdapter.setShowLength(showlength2);
                    mHandler.sendEmptyMessage(1002);
                }
            });
        } else if (id == R.id.btn_save) {//保存
            clearFocus();
            if (!checkInput())
                return;

            String field, value;
            db.beginTransaction();
            try {

                for (FileFormatBean fileFormat : mPropertiesList) {
                    field = fileFormat.getCfieldName();
                    if (field.equals(FileFormatHelper.FileProperties.FILETYPE)) {//文件類型
                        fileFormat.setCdisplayName(tv_fileformatTo.getText().toString());
//                        fileFormatDao.update(fileFormat);
                        db.execSQL("update " + tablename + " set " + formatHelper.getCdisplay_name() + "=?"+" where "+formatHelper.getId()+"=?",new String[]{fileFormat.getCdisplayName(),fileFormat.getId()+""});

                    } else if (field.equals(FileFormatHelper.FileProperties.SEPARATER)) {
//                           //判断是不是自己输入的
                        final String[] itemvalues = getResources().getStringArray(R.array.file_SeparateChar);
                        int pos = -1;
                        for (int i = 0; i < itemvalues.length; i++) {
                            if (itemvalues[i].equals(et_SeparateCharTo.getText().toString().trim())) {
                                pos = i;
                                break;
                            }
                        }
                        if (pos < 0) {
                            strSeparateCharTovalue = et_SeparateCharTo.getText().toString();
                        }

                        fileFormat.setCdisplayName(strSeparateCharTovalue);
                        db.execSQL("update " + tablename + " set " + formatHelper.getCdisplay_name() + "=?"+" where "+formatHelper.getId()+"=?",new String[]{fileFormat.getCdisplayName(),fileFormat.getId()+""});

                    } else if (field.equals(FileFormatHelper.FileProperties.HASTITLE)) {
                        fileFormat.setCdisplayName(ckb_hasTitleto.isChecked() ? "Y" : "N");
                        db.execSQL("update " + tablename + " set " + formatHelper.getCdisplay_name() + "=?"+" where "+formatHelper.getId()+"=?",new String[]{fileFormat.getCdisplayName(),fileFormat.getId()+""});

                    } else if (field.equals(FileFormatHelper.FileProperties.NUMBEREXCEPT)) {
//                            fileFormat.setCdisplayName(ckb_numberExcept.isChecked() ? "Y" : "N");
//                            fileFormatDao.update(fileFormat);
                    } else if (field.equals(FileFormatHelper.FileProperties.QUOTE)) {
//                            fileFormat.setCdisplayName(et_Quote.getText().toString().trim());
//                            fileFormatDao.update(fileFormat);
                    }else if(field.equals(FileFormatHelper.FileProperties.SHEET_NO)){//工作表序号
                        String sheet_no=et_sheet_no.getText().toString().trim();
                        if(!TextUtils.isEmpty(sheet_no)){
                            fileFormat.setCdisplayName(sheet_no);
                            db.execSQL("update " + tablename + " set " + formatHelper.getCdisplay_name() + "=?"+" where "+formatHelper.getId()+"=?",new String[]{fileFormat.getCdisplayName(),fileFormat.getId()+""});
                        }
                    }
                }

                for(FileFormatBean fileFormat:mList){
                    ContentValues values = new ContentValues();

                    values.put(formatHelper.getCtype(),fileFormat.getCtype());
                    values.put(formatHelper.getNseqno(),fileFormat.getNseqno());
                    values.put(formatHelper.getCfield_name(),fileFormat.getCfieldName());
                    values.put(formatHelper.getCdisplay_name(),fileFormat.getCdisplayName());
                    values.put(formatHelper.getBdisplayflag(),fileFormat.getBdisplayflag());
                    values.put(formatHelper.getB_data_flag(),fileFormat.getBDataFlag());
                    values.put(formatHelper.getCtitle(),fileFormat.getCtitle());
                    values.put(formatHelper.getBimportflag(),fileFormat.getBimportflag());
                    values.put(formatHelper.getNimportno(),fileFormat.getNimportno());
                    values.put(formatHelper.getNimport_len(),fileFormat.getNimportLen());
                    values.put(formatHelper.getBexportflag(),fileFormat.getBexportflag());
                    values.put(formatHelper.getNexportno(),fileFormat.getNexportno());
                    values.put(formatHelper.getNexport_len(),fileFormat.getNexportLen());
                    values.put(formatHelper.getBis_number(),fileFormat.getBisNumber());
                    values.put(formatHelper.getC_cust_field(),fileFormat.getCCustField());
                    values.put(formatHelper.getC_format_str(),fileFormat.getCFormatStr());
                    values.put(formatHelper.getC_operator(),fileFormat.getCOperator());
                    values.put(formatHelper.getCreduced(),fileFormat.getCreduced());
                    db.update(tablename, values,formatHelper.getId()+"=?", new String[]{fileFormat.getId()+""});

                }
                db.setTransactionSuccessful();
                showShortToast(getString(R.string.save_successfully));
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            } finally {
                db.endTransaction();
            }
        }


    }

    private void showData(String strFileType) {
        FileFormatBean fileFormat2;
        db = helper.getWritableDatabase();
        db.beginTransaction();

        Cursor cursor = db.rawQuery("select * from " + tablename, new String[]{});
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            System.out.println(i + "--" + cursor.getColumnName(i));
        }

        formatHelper.setColummName(cursor);

        //查找Out
        cursor = db.rawQuery("select * from " + tablename + " where " + formatHelper.getCtype() + " =? and " + formatHelper.getBdisplayflag() + " =?",
                new String[]{strFileType + "In", "Y"});


        String field, value;
        tv_fileformatTo.setText("");//文件格式
        et_SeparateCharTo.setText("");//分隔符
        strSeparateCharTovalue = "";
        mPropertiesList.clear();//List<FileFormat>
        formatHelper.CursorToList(cursor, mPropertiesList);
        if (cursor.moveToFirst()) {

            do {
                field = cursor.getString(cursor.getColumnIndex(formatHelper.getCfield_name()));
                value = cursor.getString(cursor.getColumnIndex(formatHelper.getCdisplay_name()));

                if (field.equals(FileFormatHelper.FileProperties.FILETYPE)) {
                    tv_fileformatTo.setText(value);//文件格式
                } else if (field.equals(FileFormatHelper.FileProperties.SEPARATER))//分隔符
                {
                    final String[] items = getResources().getStringArray(R.array.file_SeparateChar);
                    final String[] itemvalues = getResources().getStringArray(R.array.file_SeparateChar_value);
                    int pos = -1;
                    for (int i = 0; i < itemvalues.length; i++) {
                        if (itemvalues[i].equals(value)) {
                            pos = i;
                            break;
                        }
                    }
                    if (pos >= 0) {
                        et_SeparateCharTo.setText(items[pos]);//设置分隔符文本框的值
                        strSeparateCharTovalue = value;
                    } else {
                        et_SeparateCharTo.setText(value);
                        strSeparateCharTovalue = value;
                    }

                } else if (field.equals(FileFormatHelper.FileProperties.HASTITLE))//包含标题
                {
                    ckb_hasTitleto.setChecked(value.equals("Y"));
                } else if (field.equals(FileFormatHelper.FileProperties.NUMBEREXCEPT))//数量除外
                {
//                    ckb_numberExcept.setChecked(value.equals("Y"));
                } else if (field.equals(FileFormatHelper.FileProperties.QUOTE)) {
//                    et_Quote.setText(value);
                }else if(field.equals(FileFormatHelper.FileProperties.SHEET_NO)){//工作表序号
                    et_sheet_no.setText(value);
                }

            } while (cursor.moveToNext());
        }

        cursor = db.rawQuery("select * from " + tablename + " where " + formatHelper.getCtype() + " =? and " + formatHelper.getBdisplayflag() + " =?",

                new String[]{strFileType, "Y"});

        boolean showlength = tv_fileformatTo.getText().toString().trim().equals(FileFormatHelper.FileFormats.FILETYPETXT) &&
                (strSeparateCharTovalue.equals(FileFormatHelper.FilePropertieValues.SEPARATERFASTENLEN));

        mList.clear();

        formatHelper.CursorToList(cursor, mList);



        cursor.close();

        db.setTransactionSuccessful();
        db.endTransaction();
        mAdapter = new FormatInListAdapter(getActivity(), mList, showlength);

        lv_data.setAdapter(mAdapter);
        mHandler.sendEmptyMessage(1008);
    }


    private boolean checkInput() {
        String msg = "";
        boolean flag = true;
        if (TextUtils.isEmpty(strFileTypevalue)) {
            msg = "文件类型";
            flag = false;
        }

        if (TextUtils.isEmpty(strSeparateCharTovalue)) {
            String format = tv_fileformatTo.getText().toString().trim();
            if (TextUtils.isEmpty(format)) {
                msg = "文件格式";
                flag = false;
            } else {
                strSeparateCharTovalue = format;
            }
        }
        if (!flag) {
            Message msg2 = new Message();
            msg2.what = 1009;
            msg2.obj = msg;
            mHandler.sendMessage(msg2);
        }
        return flag;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
    }

    private void clearFocus() {
        for(int i=0;i<lv_data.getChildCount();i++){
            lv_data.getChildAt(i).clearFocus();
        }
    }
}