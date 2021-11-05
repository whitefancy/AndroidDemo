package com.whitefancy.layoutdemo.ui.interaction;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.whitefancy.layoutdemo.R;

public class InteractionActivity1 extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
    TextView tv = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interactionlayout);
        tv = (TextView) findViewById(R.id.textView);
        tv.setOnClickListener(this);
        tv.setOnLongClickListener(this);
    }


    @Override
    public void onClick(View view) {

        tv.setText("单击加关注");
    }

    @Override
    public boolean onLongClick(View view) {
        tv.setText("长按有附件动作");
        return false;
    }
}
