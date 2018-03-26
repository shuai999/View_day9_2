package com.jackchen.view_day09_2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Email: 2185134304@qq.com
 * Created by JackChen 2018/3/24 18:42
 * Version 1.0
 * Params:
 * Description:   流式布局
*/

public class TagLayout extends ViewGroup {

    private List<List<View>> mChildViews = new ArrayList<>() ;

    private BaseAdapter mAdapter ;


    public TagLayout(Context context) {
        super(context);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    // 2.1 onMeasure()指定宽高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 清空mChildViews集合
        mChildViews.clear();

        // 获取所有子孩子
        int childCount = getChildCount();

        // 获取到的宽度
        int width = MeasureSpec.getSize(widthMeasureSpec);

        int height = getPaddingTop() + getPaddingBottom() ;

        // 一行的宽度
        int lineWidth = getPaddingLeft() ;

        // 把不换行的时候的 childView 添加到 childViews这个集合中
        ArrayList<View> childViews = new ArrayList<>() ;
        mChildViews.add(childViews) ;

        // 子View 高度不一致的情况下
        int maxHeight = 0 ;

        for (int i = 0; i < childCount; i++) {

            // 2.1.1 for循环测量子View
            View childView = getChildAt(i) ;

            if (childView.getVisibility() == View.GONE){
                // 结束本次循环
                continue;
            }
            

            // 这段话执行完毕之后就可以获取子View的宽高，因为会调用子View的 onMeasure()
            measureChild(childView,widthMeasureSpec,heightMeasureSpec);

            // margin值  ViewGroup.LayoutParams没有margin值 ，
            // 这个时候就需要想一下LinearLayout为什么有margin值？
            // 因为LinearLayout有自己的 LayoutParams
            ViewGroup.MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();



            // 什么时候需要换行 ，肯定是一行不够的情况下需要换行 ，还需要考虑margin
            if (lineWidth + (childView.getMeasuredWidth() + params.leftMargin + params.rightMargin) > width){
                // 换行，累加高度
                height += childView.getMeasuredHeight() + params.topMargin + params.bottomMargin;
                lineWidth = childView.getMeasuredWidth() + params.leftMargin + params.rightMargin ;
                // 需要换行的时候 ， 就把childViews 添加到最外层总的集合中
                childViews = new ArrayList<>() ;
                mChildViews.add(childViews) ;
            }else{
                // 不换行，累加宽度
                lineWidth += childView.getMeasuredWidth() + params.leftMargin + params.rightMargin ;
                // 不需要换行的时候，就把子View添加到 集合childViews中
                childViews.add(childView) ;
                maxHeight = Math.max(childView.getMeasuredHeight() + params.topMargin + params.bottomMargin ,maxHeight) ;
            }

            childViews.add(childView) ;
        }

        height += maxHeight ;

        // 2.1.2 根据子View 计算和指定自己的高度
        setMeasuredDimension(width , height);
    }


    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return super.generateLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext() , attrs);
    }

    /**
     * 用于摆放 所有 子View
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 获取所有子孩子
        int childCount = getChildCount();

        int left , top = getPaddingTop() , right , bottom ;

        for (List<View> childViews : mChildViews) {
            left = getPaddingLeft() ;
            int maxHeight = 0 ;
            for (View childView : childViews) {

                if (childView.getVisibility() == View.GONE){
                    // 结束本次循环
                    continue;
                }
                ViewGroup.MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();

                left += params.leftMargin ;
                int childTop = top + params.topMargin ;

                right = left + childView.getMeasuredWidth() ;
                bottom = childTop + childView.getMeasuredHeight() ;

                // 摆放子View
                childView.layout(left , childTop , right , bottom);

                // left叠加
                left += childView.getMeasuredWidth() + params.rightMargin;
            }

            // 不断的叠加top值
            ViewGroup.MarginLayoutParams params = (MarginLayoutParams) childViews.get(0).getLayoutParams();
            top += childViews.get(0).getMeasuredHeight() + params.topMargin + params.bottomMargin;
        }
    }



    public void setAdapter(BaseAdapter adapter){
        if (adapter == null){
            // 抛空指针异常
            throw new NullPointerException("空指针异常") ;
        }

        // 清空里边所有的子View ，防止多次 setAdapter
        removeAllViews();

        mAdapter = null ;
        mAdapter = adapter ;

        // 获取数量
        int childCount = mAdapter.getCount() ;
        for (int i = 0; i < childCount; i++) {
            // 通过位置获取View
            // this表示当前ViewGroup是谁
            View childView = mAdapter.getView(i, this);
            addView(childView);
        }

    }
}
