package com.whitefancy.layoutdemo.ui.textview;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.whitefancy.layoutdemo.R;

public class TextViewActivity extends AppCompatActivity {
    TextView tv = null;
    EditText ed = null;
    AutoCompleteTextView au = null;
    MultiAutoCompleteTextView mau = null;

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
                tv.append("beforeTextChanged:" + charSequence.toString());
                tv.append(" i:" + i);
                tv.append(" i1:" + i1);
                tv.append(" i2:" + i2);
                tv.append("\n");

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//用户正在输入时调用
                // 参数：输入前文本框中的已有内容，光标位置，已有字符改变（删除）了几个，新添加字符长度
                tv.append("onTextChanged:" + charSequence.toString());
                tv.append(" i:" + i);
                tv.append(" i1:" + i1);
                tv.append(" i2:" + i2);
                tv.append("\n");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                tv.append("afterTextChanged:" + editable.toString());
                tv.append("\n");

            }
        });

        au = findViewById(R.id.autoCompleteTextView);
        String[] names = {"五十九只野天鹅", "春天的樱桃树", "缓慢旋转的圆锥体"};
        ArrayAdapter<String> nameArray = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, names);
        au.setAdapter(nameArray);

        mau = findViewById(R.id.multiAutoCompleteTextView1);
        String[] address = {"英格兰", "土耳其", "拜占庭"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, address);
        mau.setAdapter(adapter);
        mau.setThreshold(1);//设置输入几个字符后提示，默认为2字符
        mau.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());//设置分隔符为逗号分隔符
    }
}
