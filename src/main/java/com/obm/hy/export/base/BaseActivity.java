package com.obm.hy.export.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.obm.hy.export.R;


public class BaseActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.layout_top_bar);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

    }


    protected void hideTopBar() {
        getSupportActionBar().hide();
    }

    protected void hideBack(){
        Button btn = (Button) (getSupportActionBar().getCustomView().findViewById(R.id.btn_back));
        btn.setVisibility(View.GONE);
    }

    public void setTitleText(String title) {
        ((TextView) (getSupportActionBar().getCustomView().findViewById(R.id.tv_title))).setText(title);
    }

    public void setSubmit(String text) {
        Button btn = (Button) (getSupportActionBar().getCustomView().findViewById(R.id.btn_submit));
        if(TextUtils.isEmpty(text)){
            btn.setVisibility(View.GONE);
        }else {
            btn.setVisibility(View.VISIBLE);
            btn.setText(text);
        }
    }




    /**
     * 获取当前activity实例
     */
    protected Activity getActivity() {
        return this;
    }

    public void defaultFinish() {
        finish();
//        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    /**
     * 返回
     */
    public void goBack(View view) {
        back();
    }

    public void onSubmit(View view) {
        submit();
    }

    protected void submit() {

    }

    public void showShortToast(int id) {
        Toast.makeText(this, getString(id), Toast.LENGTH_SHORT).show();
    }

    public void showShortToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    public void showShortToast(String content, int i) {
        Toast toast = Toast.makeText(this, content, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 重新计算ListView的高度，解决ScrollView和ListView两个View都有滚动的效果，在嵌套使用时起冲突的问题
     */
    public void setListViewHeight(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        
    }

    public void back() {
        defaultFinish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK)
            back();
        return super.onKeyDown(keyCode, event);
    }


}