package com.whitefancy.layoutdemo.ui.interaction;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.whitefancy.layoutdemo.R;

public class InteractionActivity2 extends Activity {
    TextView tv = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interactionlayout1);
        tv = (TextView) findViewById(R.id.textView);
    }

    //方法三：针对Wideget部件的单击事件，可以在该组件的XML布局代码中添加属性，然后在活动中完成单击方法的定义
    //这种可以比较简洁地完成系统单击时的响应
    public void methodName(View view) {
        tv.setText("单击加关注");
    }
}
