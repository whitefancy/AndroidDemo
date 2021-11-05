package com.whitefancy.layoutdemo.ui.textview;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.whitefancy.layoutdemo.R;

public class TextViewActivity extends AppCompatActivity {
    TextView tv = null;
    EditText ed = null;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edittextlayout);
        tv = (TextView) findViewById(R.id.textView);
        ed = (EditText) findViewById(R.id.editText);
        ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //用户输入前被调用
                // 参数：输入前文本框中的已有内容，光标位置，已有字符改变（删除）了几个，新添加字符长度
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//用户正在输入时调用
                // 参数：输入前文本框中的已有内容，光标位置，已有字符改变（删除）了几个，新添加字符长度
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
