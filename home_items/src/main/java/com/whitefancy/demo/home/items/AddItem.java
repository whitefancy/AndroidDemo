package com.whitefancy.demo.home.items;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AddItem extends AppCompatActivity {
    private ImageView imageView;
    String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.activity_add_item, COUNTRIES);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.item_type1);
        textView.setAdapter(adapter);
        Intent mIntent = getIntent();
        currentPhotoPath = mIntent.getStringExtra("imageURL");
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(currentPhotoPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Bitmap bitmap = BitmapFactory.decodeStream(fs);
        imageView = (ImageView) findViewById(R.id.item_image);
    }

    private static final String[] COUNTRIES = new String[]{
            "冰箱", "柜子", "储藏室"
    };

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            //2
            //
            //您需要等待 ImageView 膨胀。来试试OnLayoutChangeListener。
            //为何我们调用view.getWidth和view.getHeight的时候，返回值都为0呢？？？？？
            //因为在 onCreate方法执行完了,我们定义的控件才会被度量(measure),
            // 所以我们在onCreate方法里面通过view.getHeight()获取控件的高度或者宽度肯定是0,因为它自己还没有被度量,
            // 也就是说他自己都不知道自己有多高,而你这时候去获取它的尺寸,肯定是不行的.
            setPic();
        }
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        imageView.setImageBitmap(bitmap);
    }

    public void Add(View view) {
    }
}