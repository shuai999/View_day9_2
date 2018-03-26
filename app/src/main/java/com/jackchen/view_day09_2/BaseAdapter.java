package com.jackchen.view_day09_2;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;

/**
 * Email: 2185134304@qq.com
 * Created by JackChen 2018/3/25 7:12
 * Version 1.0
 * Params:
 * Description:   流式布局的adapter
*/

public abstract class BaseAdapter {
    // 1. 有多少个条目
    public abstract int getCount() ;
    // 2. getView通过position
    public abstract View getView(int position , ViewGroup parent) ;

    // 3. 观察者模式及时通知更新
    public void registerDataSetObserver(DataSetObserver observer){

    }
    public void unRegisterDataSetObserver(DataSetObserver observer){

    }
}
