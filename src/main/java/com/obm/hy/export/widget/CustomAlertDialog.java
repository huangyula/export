package com.obm.hy.export.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.obm.hy.export.R;
import com.obm.hy.export.adapter.ListViewDialogAdapter;


/**
 * 信息提示框
 */
public class CustomAlertDialog {

    public static void showListDialog(Context context, String title, String[] items, final IAlertListDialogItemClickListener listener) {
        final Dialog dialog = new Dialog(context, R.style.MyDialog);
        dialog.setContentView(R.layout.dialog_listview);
        TextView tv_message = (TextView) dialog.findViewById(R.id.tv_title);
        tv_message.setText(title);
        if (TextUtils.isEmpty(title)) {
            tv_message.setVisibility(View.GONE);
        }
        ListView lv = (ListView) dialog.findViewById(R.id.lv_content);
        lv.setAdapter(new ListViewDialogAdapter(context, items));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (listener != null)
                    listener.onItemClick(position);
                dialog.dismiss();
            }
        });
        TextView tv_cancel = (TextView) dialog.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public interface IAlertListDialogItemClickListener {
        void onItemClick(int position);
    }




}