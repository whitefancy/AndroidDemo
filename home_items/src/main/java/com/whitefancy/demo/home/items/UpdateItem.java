package com.whitefancy.demo.home.items;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.whitefancy.demo.home.items.livedatabuilder.RecyclerActivity;
import com.whitefancy.demo.home.items.roomDB.Item;

import java.io.File;
import java.util.List;

public class UpdateItem extends AppCompatActivity {
    private static final String TAG = "AddItem";
    private ImageView imageView;
    String currentPhotoPath;
    AutoCompleteTextView au = null;
    AutoCompleteTextView place = null;
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);


//public void setThreshold (int threshold)
//自：API 级别 1
//指定在显示下拉列表之前用户必须在编辑框中键入的最少字符数。
//当阈值小于或等于 0 时，应用阈值 1

        Intent mIntent = getIntent();
        String iteminfo = mIntent.getStringExtra("itemInfo");
        Gson gson = new GsonBuilder().setDateFormat("MMM dd,yyyy HH:mm:ss").create();
        Item item1 = gson.fromJson(iteminfo, Item.class);
        item = RecyclerActivity.db.getById(item1.id);
        currentPhotoPath = item.imgUrl;
        imageView = (ImageView) findViewById(R.id.item_image);
        place = findViewById(R.id.item_place);
        place.setText(item.place);
        EditText time = (EditText) findViewById(R.id.item_time);
        int date = (int) (item.createTs + item.lifetime - System.currentTimeMillis() / 1000) / (24 * 60 * 60) + 1;
        time.setText("" + date);
        au = findViewById(R.id.item_type);
        au.setText(item.type);
        ((EditText) findViewById(R.id.item_name)).setText(item.name);
    }

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
            loadDB();
        }
    }

    private void loadDB() {
        List<String> names = RecyclerActivity.db.getTypes();
        ArrayAdapter<String> nameArray = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, names);
        au.setAdapter(nameArray);
        au.setThreshold(1);
        //要使自动完成显示在焦点上，请添加焦点侦听器并在字段获得焦点时显示下拉菜单，如下所示：
        au.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    au.showDropDown();
                }
            }
        });
        List<String> places = RecyclerActivity.db.getPlaces();
        place.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, places));
        place.setThreshold(1);
        //要使自动完成显示在焦点上，请添加焦点侦听器并在字段获得焦点时显示下拉菜单，如下所示：
        place.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    place.showDropDown();
                }
            }
        });
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
        String type = au.getText().toString();
        String name = ((EditText) findViewById(R.id.item_name)).getText().toString();
        Integer shelf_time = Integer.parseInt(((EditText) findViewById(R.id.item_time)).getText().toString());
        item.createTs = (int) (System.currentTimeMillis() / 1000);
        item.lifetime = shelf_time * 24 * 60 * 60;
        item.imgUrl = currentPhotoPath;
        item.name = name;
        item.type = type;
        item.place = place.getText().toString();
        try {
            RecyclerActivity.db.updateAll(item);
            setResult(RESULT_OK);
            this.finish();
        } catch (SQLiteConstraintException e) {
            Log.e(TAG, "A cotnact with same phone number already exists.");
        }
    }

    private void renameFile(String name) {

        String path = "/storage/emulated/0/Android/data/com.whitefancy.demo.home.items/files/Pictures/valid";
        String newPhotoPath = path + name + ".jpg";
        File file = new File(currentPhotoPath);
        boolean success = file.renameTo(new File(newPhotoPath));
    }
}