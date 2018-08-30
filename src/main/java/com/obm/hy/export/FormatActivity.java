package com.obm.hy.export;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import com.obm.hy.export.base.BaseActivity;
import com.obm.hy.export.event.TabEvent;
import com.obm.hy.export.fragment.FormatInFragment;
import com.obm.hy.export.fragment.FormatOutFragment;
import com.obm.hy.export.utils.ZoomOutPageTransformer;
import com.obm.hy.export.widget.PagerSlidingTabStrip;

import java.util.ArrayList;



/**
 * 文件格式设置
 */
public class FormatActivity extends BaseActivity {

    private FormatInFragment inFragment= new FormatInFragment();;
    private FormatOutFragment outFragment=new FormatOutFragment();

    private ViewPager pager = null;
    private int index=0;
    private String[] CONTENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager_export);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().
                detectDiskWrites().detectNetwork().penaltyLog().build());
        setTitleText(getString(R.string.setting_file_format));
        setSubmit(getString(R.string.reset));
        initView();
    }

    private void initView() {

        CONTENT = new String[]{getString(R.string.import_setting), getString(R.string.export_setting)};

        FragmentPagerAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager());

        pager = (ViewPager) findViewById(R.id.viewpager);
        setListener();
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(2);
        PagerSlidingTabStrip indicator = (PagerSlidingTabStrip) findViewById(R.id.indicator);
        indicator.setViewPager(pager);
        pager.setPageTransformer(true, new ZoomOutPageTransformer());
        // 设置tab样式
        DisplayMetrics dm = getResources().getDisplayMetrics();
        TabEvent.setTab(FormatActivity.this, indicator, dm);
        pager.setCurrentItem(0);
    }

    private void setListener() {
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                index=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class MyFragmentAdapter extends FragmentPagerAdapter {

        ArrayList<Fragment> mList = new ArrayList<Fragment>();

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
            mList.add(inFragment);
            mList.add(outFragment);
        }

        @Override
        public Fragment getItem(int position) {
            return mList.get(position);
        }

        @Override
        public int getCount() {
            return CONTENT.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position % CONTENT.length].toUpperCase();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
    }


    public int getIndex() {
        return index;
    }

    @Override
    public void onSubmit(View view) {
//        daoSession.getFileFormatDao().addDatas2();
        //判断是导入还是导出
        //TODO
        if(index==0){
            inFragment.initData();
        }else if(index==1){
            outFragment.initData();
        }
        showShortToast(getString(R.string.reset_success));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == 2002) {
        }
    }
}