package com.jackchen.view_day09_2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TagLayout tag_layout;
    private List<String> mItems ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tag_layout = (TagLayout) findViewById(R.id.tag_layout);

        // 真正开发中，所有数据肯定都是从后台获取的
        mItems = new ArrayList<>() ;
        mItems.add("1111111");
        mItems.add("11");
        mItems.add("1111");
        mItems.add("1111");
        mItems.add("11");
        mItems.add("1111");
        mItems.add("1111111");
        mItems.add("1111111");
        mItems.add("11");
        mItems.add("1111");
        mItems.add("1111");
        mItems.add("11");
        mItems.add("1111");
        mItems.add("1111111");


        tag_layout.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return mItems.size();
            }

            @Override
            public View getView(final int position, ViewGroup parent) {
                TextView textView = (TextView) LayoutInflater.from(MainActivity.this).inflate(R.layout.item_tag, parent, false);
                textView.setText(mItems.get(position));

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this , "点击了标签了"+position , Toast.LENGTH_SHORT).show();
                    }
                });
                return textView;
            }
        });
    }
}
