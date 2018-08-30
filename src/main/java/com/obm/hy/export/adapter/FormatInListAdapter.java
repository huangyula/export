package com.obm.hy.export.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.obm.hy.export.R;
import com.obm.hy.export.bean.FileFormatBean;

import java.util.List;

public class FormatInListAdapter extends BaseAdapter {

    private Context mContext;
    private List<FileFormatBean> mList;
    private boolean mShowLength=false;

    public FormatInListAdapter(Context context, List<FileFormatBean> mList, boolean showlength) {
        this.mContext = context;
        this.mList = mList;
        this.mShowLength=showlength;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setShowLength(boolean showlength)
    {
        this.mShowLength=showlength;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final FileFormatBean fileFormat = mList.get(i);
        HoldClass hold;
        if (view == null) {
            hold = new HoldClass();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_list_format_in, null);
            hold.tv_pos = (TextView) view.findViewById(R.id.tv_pos);
            hold.tv_nseqno = (TextView) view.findViewById(R.id.tv_nseqno);
            hold.tv_cfieldName= (TextView) view.findViewById(R.id.tv_cfieldName);//字段名称
            hold.tv_cdisplayName = (TextView) view.findViewById(R.id.tv_cdisplayName);//显示原名称
            hold.et_ctitle = (EditText) view.findViewById(R.id.et_ctitle);//标题名
            hold.ckb_bexportflag = (CheckBox) view.findViewById(R.id.ckb_bexportflag);//是否导出
            hold.et_nexportLen = (EditText) view.findViewById(R.id.et_nexportLen);//导出长度
            hold.et_nexportno = (EditText) view.findViewById(R.id.et_nexportno);//序号
            hold.et_cFormatStr = (EditText) view.findViewById(R.id.et_cFormatStr);//格式
            hold.tv_Length = (TextView) view.findViewById(R.id.tv_Length);
            view.setTag(hold);
        } else {
            hold = (HoldClass) view.getTag();
        }

        hold.tv_nseqno.setText(fileFormat.getNseqno().toString());
        hold.tv_cfieldName.setText(fileFormat.getCfieldName());
        hold.tv_cdisplayName.setText(fileFormat.getCdisplayName());
        hold.et_ctitle.setText(fileFormat.getCtitle());//标题名
        hold.et_nexportno.setText(fileFormat.getNimportno().toString());
        hold.et_nexportLen.setText(fileFormat.getNimportLen().toString());
        hold.ckb_bexportflag.setChecked(fileFormat.getBimportflag().equals("Y"));
        hold.tv_Length.setVisibility(mShowLength? View.VISIBLE: View.GONE);
        hold.et_nexportLen.setVisibility(mShowLength? View.VISIBLE: View.GONE);

        hold.et_cFormatStr.setText(fileFormat.getCFormatStr());

        //格式
        hold.et_cFormatStr.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && (v != null)) {
                    EditText et_cFormatStr = (EditText) v;
                    if (et_cFormatStr.getText().toString().trim().length() >=0) {


                        if(mList.size()>0) {
                            FileFormatBean fileFormat = mList.get(i);
                            fileFormat.setCFormatStr(et_cFormatStr.getText().toString().trim());

                        }

                    }
                }
            }
        });
        //显示名称
        hold.et_ctitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && (v != null)) {
                    EditText et_ctitle = (EditText) v;
                    if (et_ctitle.getText().toString().trim().length() > 0) {
                        if(mList.size()>0){
                            FileFormatBean fileFormat = mList.get(i);
                            fileFormat.setCtitle(et_ctitle.getText().toString().trim());
                        }
                    }
                }
            }
        });
        //序号
        hold.et_nexportno.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus &&  (v != null) )
                {
                    EditText et_nexportno = (EditText)v;
                    if (et_nexportno.getText().toString().trim().length()>0) {
                        if(mList.size()>0){
                            FileFormatBean fileFormat = mList.get(i);

                            fileFormat.setNimportno(Integer.valueOf(et_nexportno.getText().toString().trim()));
                        }
                    }
                }
            }
        });
        //长度
        hold.et_nexportLen.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus &&  (v != null) )
                {
                    EditText et_nexportLen = (EditText)v;
                    if (et_nexportLen.getText().toString().trim().length()>0) {
                        if(mList.size()>0){
                            FileFormatBean fileFormat = mList.get(i);

                            fileFormat.setNimportLen(Integer.valueOf(et_nexportLen.getText().toString().trim()));
                        }
                    }

                }
            }
        });
        //是否导出
        hold.ckb_bexportflag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v != null)
                {
                    if(mList.size()>0){
                        FileFormatBean fileFormat = mList.get(i);
                        CheckBox ckb_bexportflag = (CheckBox)v;
                        fileFormat.setBimportflag(ckb_bexportflag.isChecked()?"Y":"N");
                    }
                }
            }
        });

        return view;
    }

    private static class HoldClass {
        private TextView tv_nseqno,tv_cfieldName,tv_cdisplayName,tv_pos,tv_Length;
        private EditText et_ctitle,et_nexportno,et_nexportLen,et_cFormatStr;
        private CheckBox ckb_bexportflag;
    }



}
