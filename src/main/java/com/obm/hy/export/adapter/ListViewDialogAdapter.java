package com.obm.hy.export.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.obm.hy.export.R;


/**
 * 显示数组
 */
public class ListViewDialogAdapter extends BaseAdapter {

    private Context mContext;
    private String[] mItem ;

    public ListViewDialogAdapter(Context context, String[] mItem) {
        this.mContext = context;
        this.mItem = mItem.clone();
    }

    @Override
    public int getCount() {
        return mItem.length;
    }

    @Override
    public Object getItem(int position) {
        return mItem[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final HoldClass hold;
        if (convertView == null) {
            hold = new HoldClass();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_listview_dialog, null);
            hold.tv_item= (TextView) convertView.findViewById(R.id.tv_item);

            convertView.setTag(hold);
        } else {
            hold = (HoldClass) convertView.getTag();
        }
        hold.tv_item.setText(mItem[position]);

        return convertView;
    }

    static class HoldClass {
        private TextView tv_item;
    }
}