package com.whitefancy.layoutdemo.ui.menu;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.whitefancy.layoutdemo.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_layout, menu);//加载menu菜单
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //当用户选择menu菜单的某个项时，此方法被调用
        TextView tv1 = findViewById(R.id.tv1);
        switch (item.getItemId()) {
            case R.id.item1:
                tv1.setTextColor(Color.RED);
                break;
            case R.id.item2:
                tv1.setTextSize(26);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}