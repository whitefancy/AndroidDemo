package com.whitefancy.demo.home.items;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner spinner = findViewById(R.id.item_type);

        String[] itemsType = {"clothes", "food", "杂物"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsType);
        spinner.setAdapter(adapter);
        String[] itemsPlace = {"冰箱", "柜子", "储藏室"};
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsPlace);
        spinner = findViewById(R.id.item_status);
        spinner.setAdapter(adapter);
        String[] itemsTime = {"1周", "1个月", "1年"};
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsTime);
        spinner = findViewById(R.id.item_time);
        spinner.setAdapter(adapter);
//悬浮操作按钮 (FAB) 是一种圆形按钮，用于在应用界面中触发主要操作。本页将介绍如何将悬浮操作按钮添加到布局、自定义该按钮的一些外观，以及响应按钮点按操作。
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开摄像机
// 启动拍照功能，拍照完成后 启动新增页面
                dispatchTakePictureIntent();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            //知识点：问题为为什么拍摄相机的回调图片数据data为null
            //
            //查询发现：
            //
            //照相机有自己默认的存储路径，拍摄的照片将返回一个缩略图，即data里面保存的数据。
            //
            //但是如果自己代码指定了保存图片的uri，data里面就不会保存数据。也就是说，调用相机时指定了uri，data就没有数据，没有指定uri，data就有数据。
            //
            //但是这个规律也不是适用于所有的安卓手机，红米和三星部分型号在没有指定uri时，data依然没有数据

            Intent mIntent = new Intent();
            mIntent.putExtra("imageURL", currentPhotoPath);
            mIntent.setClass(this, AddItem.class);
            startActivity(mIntent);
        }
    }

    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    static final int REQUEST_TAKE_PHOTO = 4323;
    static final String TAG = "testlog";

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e(TAG, ex.toString());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        this.getPackageName() + ".fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }


}