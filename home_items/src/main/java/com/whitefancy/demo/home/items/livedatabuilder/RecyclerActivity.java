package com.whitefancy.demo.home.items.livedatabuilder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.whitefancy.demo.home.items.AddItem;
import com.whitefancy.demo.home.items.R;
import com.whitefancy.demo.home.items.roomDB.AppDatabase;
import com.whitefancy.demo.home.items.roomDB.Item;
import com.whitefancy.demo.home.items.roomDB.ItemDao;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.GZIPOutputStream;

//todo 应用开启页面下载数据，应用退出界面数据库同步到服务器 图片压缩存储，退出应用时清理未使用图片
public class RecyclerActivity extends AppCompatActivity {
    private ItemViewModel model;
    private Spinner itemTypes;
    public static ItemDao db;

    private RecyclerView mContactsRecyclerView;

    private static Spinner itemPlaces;
    public static DbAdapter adapter;
    public static List<String> dbdata;

    //架构组件的目的 是提供有关应用程序架构的指南，以及用于生命周期管理和数据持久性等常见任务的库。
// 架构组件可帮助您以健壮、可测试和可维护的方式构建应用程序，并使用更少的样板代码。架构组件库是Android Jetpack 的一部分 。
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "local")
                .allowMainThreadQueries().build().itemDao();
        itemTypes = findViewById(R.id.item_type);
        itemPlaces = findViewById(R.id.item_place);
        adapter = new DbAdapter(this, new ArrayList<Item>());
        mContactsRecyclerView = findViewById(R.id.contactsRecyclerView);
        mContactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mContactsRecyclerView.setAdapter(adapter);
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
        itemsType = RecyclerActivity.db.getTypes();
        Spinner spinner = findViewById(R.id.item_type);

        typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsType);
        spinner.setAdapter(typeAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String data = adapterView.getSelectedItem().toString();
                String[] datas = {data};
                adapter.updateData(db.loadAllByTypes(datas));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        itemsPlace = RecyclerActivity.db.getPlaces();
        placeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsPlace);
        spinner = findViewById(R.id.item_place);
        spinner.setAdapter(placeAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String data = adapterView.getSelectedItem().toString();
                String[] datas = {data};
                adapter.updateData(db.loadAllByPlaces(datas));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        String[] itemsTime = {"1周", "1个月", "1年"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsTime);
        spinner = findViewById(R.id.item_time);
        spinner.setAdapter(adapter3);

        exitDialog = new AlertDialog.Builder(this).setTitle("退出并同步数据到服务器")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
//                                http请求这个不能在主线程
                                uploadDB();
                                clearUselessImage();
                                System.exit(0);
                            }
                        }).start();
                    }

                })
                .setNegativeButton("取消", null).create();


        loadContacts();

    }

    //应用退出界面数据库同步到服务器 图片压缩存储，退出应用时清理未使用图片
    private void clearUselessImage() {
    }

    private void uploadDB() {

//你可以通过这个：
//
//通过选择查询获取您要发送的所有数据并将此数据存储在数组列表中。
//
//使用 GSON 库，您可以将该数组列表转换为 json 数据
//
//现在您必须创建一个 API 来接收该 json 数据并对其进行解析并将每条记录插入数据库。
//
//在应用程序端，您必须点击该 API 并将 json 数据传递给它。
        Gson gson = new Gson();
        String j = gson.toJson(db.getAll());
        //使用 GZIP 压缩的方法
        String u = null;
        try {
            u = compress(j);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i(TAG, u);
        String r = httpPost("http://172.16.70.19/PaymentSolutionPHP/gameServer/db_save.php", u);
        Log.i(TAG, r);

    }

    public static String httpPost(String urlStr, String paramsEncoded) {

        String result = null;
        URL url = null;
        HttpURLConnection connection = null;
        try {
            byte[] bytes = paramsEncoded.getBytes("UTF-8");
            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-encoding", "gzip");
            connection.setRequestProperty("Content-type", "application/octet-stream");
            connection.setRequestProperty("Content-Length", Integer.toString(bytes.length));
            OutputStream wr = connection.getOutputStream();
            wr.write(bytes);
            wr.flush();
            wr.close();


            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            result = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String compress(String str) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream(str.length());
        GZIPOutputStream gos = new GZIPOutputStream(os);
        gos.write(str.getBytes());
        os.close();
        gos.close();
        return Base64.encodeToString(os.toByteArray(), Base64.DEFAULT);
    }

    List<String> itemsType;
    List<String> itemsPlace;
    private ArrayAdapter<String> placeAdapter;
    private ArrayAdapter<String> typeAdapter;
    private AlertDialog exitDialog;

    @Override
    public void onBackPressed() {

        exitDialog.show();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {

        }
    }

    private void loadContacts() {
        itemsType.clear();
        itemsType.addAll(RecyclerActivity.db.getTypes());
        typeAdapter.notifyDataSetChanged();
        itemsPlace.clear();
        itemsPlace.addAll(RecyclerActivity.db.getPlaces());
        placeAdapter.notifyDataSetChanged();


        this.adapter.updateData(db.getAll());
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
            startActivityForResult(mIntent, RC_CREATE_CONTACT);
        } else if (requestCode == RC_CREATE_CONTACT && resultCode == RESULT_OK) {
            loadContacts();
        } else if (requestCode == RC_UPDATE_CONTACT && resultCode == RESULT_OK) {
            loadContacts();
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

    private static final int RC_UPDATE_CONTACT = 2;
    private static final int RC_CREATE_CONTACT = 1;

}