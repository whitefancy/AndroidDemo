package com.whitefancy.layoutdemo.ui.interaction;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.whitefancy.layoutdemo.R;

public class InteractionActivity extends Activity {
    TextView tv = null;
    TextView tv2 = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //实例化TextView对象要放在setContentView之后，因为tv是一个视图对象，该对象在整个界面加载之后才会出现
        //加载活动界面的方法是setContentView
        setContentView(R.layout.interactionlayout);
        tv = (TextView) findViewById(R.id.textView);
        listenerUsage();
    }

    private void listenerUsage() {
        tv2 = (TextView) findViewById(R.id.textView2);
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv2.setText("单击加关注");
            }
        });
        tv2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //学习ContextMenu，可以长按创建弹出菜单
                tv2.setText("长按有附件动作");
                return false;
            }
        });
    }

    //使用系统回调方法相应用户的关键点如下：
    //1 找准用户动作的对应回调方法
    //2 在回调方法内编写自己的相应内容
    // 系统与用户交互的方式很多，只要善于使用回调方法中的实参及实参对象中的方法，获取有用信息并处理即可。
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        String x = String.valueOf(event.getX());
        String y = String.valueOf(event.getY());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                tv.setText(String.format("你单击的位置是（%s,%s)", x, y));
                break;
            case MotionEvent.ACTION_MOVE:
                tv.setText("你滑动屏幕想干什么呢");
                break;
        }
        //重写监听方法的内容要放在supper之前
        return super.onTouchEvent(event);
    }
}
