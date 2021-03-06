package com.obm.hy.export.event;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.obm.hy.export.R;
import com.obm.hy.export.widget.PagerSlidingTabStrip;


public class TabEvent {
    public static void setTab(Context context, PagerSlidingTabStrip indicator, DisplayMetrics dm){

        indicator.setIndicatorleftrightpadsize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 0, dm));
        // 设置Tab是自动填充满屏幕的
        indicator.setShouldExpand(true);
        // 设置Tab的分割线是透明的
        indicator.setDividerColor(Color.TRANSPARENT);
        // 设置Tab底部线的高度
        indicator.setUnderlineHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 0, dm));
        // 设置Tab Indicator的高度
        indicator.setIndicatorHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 1, dm));
        // 设置Tab标题文字的大小
        indicator.setTextSize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 13, dm));
        indicator.setTabPaddingLeftRight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 0, dm));
        indicator.setTabPadding((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 24, dm))  ;
        // 设置Tab Indicator的颜色
        indicator.setIndicatorColor(ContextCompat.getColor(context,R.color.indicator_color));
        // 设置选中Tab文字的颜色 (这是我自定义的一个方法)
        indicator.setSelectedTextColor(ContextCompat.getColor(context,R.color.indicator_color));
        // 取消点击Tab时的背景色
        indicator.setTabBackground(0);

    }




}
