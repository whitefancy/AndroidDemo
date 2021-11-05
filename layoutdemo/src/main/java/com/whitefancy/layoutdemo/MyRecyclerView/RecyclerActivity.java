package com.whitefancy.layoutdemo.MyRecyclerView;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.whitefancy.layoutdemo.R;

public class RecyclerActivity extends Activity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    //布局管理器用于定位屏幕上每一个独立数据项所在的视图，并决定什么时候重用哪些隐藏的数据项视图。
    //这种视图回收的方式，避免了创建不必要的视图或执行昂贵的findViewById查找方法，提高了性能
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        //Android支持库包括三个标准的布局管理器：LinearLayoutManager将所有的数据项排列在一维列表中
        //使用时，先定义LayoutManager对象。
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //GridLayoutManager 网格布局管理器，将所有数据项排列成二维网格
        layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        //瀑布流布局管理器 排列成二维网格，每列大小随机偏离前一个。或者通过扩展RecyclerView.LayoutManager抽象类来创建自己的布局管理器
        layoutManager = new StaggeredGridLayoutManager(3, 1);
        recyclerView.setLayoutManager(layoutManager);
        //RecyclerView 还可以实现对数据项对修饰（如增加分割线，重新绘制各个数据项视图，绘制间隔样式，为item添加动画等。
        String[] mydataset = {"item1", "item2", "item3", "item4", "item5", "item6", "item7", "item8", "item9",};
        mAdapter = new MyAdapter(mydataset);
        recyclerView.setAdapter(mAdapter);
    }
}
