package com.whitefancy.wallbreaker;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.Selection;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.whitefancy.sdk.service.bean.SDKDataBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {
    public TextView textView;
    public static Handler handler;
    MethodBean game;
    String device1;
    private MethodBean device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initTestMethods();
        initResultView();
    }

    private void initTestMethods() {
        game = new MethodBean("com.whitefancy.sdk.game.api.MyPlatform", findViewById(R.id.spinner1));
        initSpinner(game);
        device = new MethodBean("com.whitefancy.sdk.device.api.CabbageUtil", findViewById(R.id.spinner2));
        initSpinner(device);
    }

    private void initSpinner(MethodBean bean) {
        String[] items = bean.initDropdownMethods();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        bean.spinner.setAdapter(adapter);
    }

    private void initResultView() {
        textView = findViewById(R.id.content1);
        textView.setMovementMethod(new ScrollingMovementMethod());
        SpannableString spannable = new SpannableString("显示结果\n");
        Selection.setSelection(spannable, spannable.length());
        textView.setText(spannable, TextView.BufferType.SPANNABLE);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                textView.append(msg.getData().getString("result") + '\n');
                Editable editable = textView.getEditableText();
                Selection.setSelection(editable, editable.length());
            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void ConfirmDropdown(View view) {
        int i = Integer.parseInt(view.getTag().toString());
        MethodBean bean = (i == 1) ? game : device;
        String selectedText = String.valueOf(bean.spinner.getSelectedItem());
        Method method = bean.methodMap.get(selectedText);
        String result = invokePlatformMethod(method, bean);
        showInText(result);
    }

    private void showInText(String result) {
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("result", result);
        message.setData(bundle);
        handler.sendMessage(message);
    }

    private void showInText(String tag, String result) {
        showInText(result);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String invokePlatformMethod(Method method, MethodBean bean) {
        String result = null;
        try {
            Object[] params = null;
            int paramCount = method.getParameterCount();
            if (0 != paramCount) {
                result = method.getName() + " need params";
                params = bean.getParams(method.getName(), paramCount);
            }

            Object o = method.invoke(null, params);
            if (null != o) {
                Type type = method.getReturnType();
                if (Void.TYPE.equals(type)) {
                    result = "void return";
                } else if (type.equals(String.class))
                    result = (String) o;
                else if (type.equals(int.class))
                    result = "" + (int) o;
                else {
                    result = o.toString();
                }
            }


        } catch (Exception e) {
            result = e.toString();
        }
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void Travel(View view) {
        callAllMethod(game);
        callAllMethod(device);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void callAllMethod(MethodBean bean) {
        for (Method method : bean.methodMap.values()) {
            String result = invokePlatformMethod(method, bean);
            showInText(result);
        }
    }

    public void Clear(View view) {
        SpannableString spannable = new SpannableString("显示结果\n");
        Selection.setSelection(spannable, spannable.length());
        textView.setText(spannable, TextView.BufferType.SPANNABLE);
    }

    public void Init(View view) {
        SDKDataBean.uiActivity = this;
    }

    public void Pay(View view) {
    }

    public void Submit(View view) {
    }

    public void Login(View view) {
    }

    public void Logout(View view) {
    }

    private String phoneNumber;
    private String phoneModel;
    private String SdkVersion;
    private String OsVersion;
    private String board;
    private String brand;
    private String cpu_abi;
    private String display;
    private String fingerprint;
    private String host;
    private String updateId;
    private String manufacturer;
    private String product;
    private String tags;
    private long time;
    private String type;
    private String user;


    private static final String TAG = "MainActivity";

    public void DeviceInfo(View view) {
        TelephonyManager phoneMgr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        //经过测试，无法获取
//        phoneNumber = phoneMgr.getLine1Number();
//        showInText(TAG, "电话号码：" + phoneNumber);

        phoneModel = Build.MODEL;
        showInText(TAG, "手机型号：" + phoneModel);

        SdkVersion = Build.VERSION.SDK;
        showInText(TAG, "SDK版本：" + SdkVersion);

        OsVersion = Build.VERSION.RELEASE;
        showInText(TAG, "系统版本：" + OsVersion);

        board = Build.BOARD;
        showInText(TAG, "主板：" + board);

        brand = Build.BRAND;
        showInText(TAG, "android系统定制商：" + brand);

        cpu_abi = Build.CPU_ABI;
        showInText(TAG, "cpu指令集：" + cpu_abi);

        device1 = Build.DEVICE;
        showInText(TAG, "设备参数：" + device1);

        display = Build.DISPLAY;
        showInText(TAG, "显示屏参数：" + display);

        // 硬件名称
        fingerprint = Build.FINGERPRINT;
        showInText(TAG, "硬件名称：" + fingerprint);

        host = Build.HOST;
        showInText(TAG, "host：" + host);

        // 修订版本列表
        updateId = Build.ID;
        showInText(TAG, "修订版本列表：" + updateId);

        // 硬件制造商
        manufacturer = Build.MANUFACTURER;
        showInText(TAG, "硬件制造商：" + manufacturer);

        // 手机制造商
        product = Build.PRODUCT;
        showInText(TAG, "手机制造商：" + product);

        // 描述build的标签
        tags = Build.TAGS;
        showInText(TAG, "描述build的标签：" + tags);

        time = Build.TIME;
        showInText(TAG, "time：" + time);

        // builder类型
        type = Build.TYPE;
        showInText(TAG, "builder类型：" + type);

        user = Build.USER;
        showInText(TAG, "user型：" + user);
    }

    public void Update(View view) {
    }

    public void Helper(View view) {
    }

    public void Push(View view) {
    }

    class MethodBean {
        String name;
        Map<String, Method> methodMap = new HashMap<>();
        Spinner spinner;
        JSONObject paramsJson;

        public MethodBean(String name, Spinner spinner) {
            this.name = name;
            this.spinner = spinner;
        }

        private String[] initDropdownMethods() {
            String[] items = new String[0];
            try {
                Class<?> c = Class.forName(name);
                Method[] methods = c.getMethods();
                items = new String[methods.length];
                for (int i = 0; i < methods.length; i++) {
                    items[i] = methods[i].getName();
                    methodMap.put(items[i], methods[i]);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return items;
        }

        public Object[] getParams(String methodName, int paramCount) {
            Object[] params = new Object[paramCount];
            try {
                if (null == paramsJson) {
                    loadJSONFromAsset();
                }
                if (paramsJson.has(methodName)) {
                    if (1 == paramCount) {
                        params[0] = paramsJson.get(methodName);
                    } else {
                        JSONArray paramslist = paramsJson.getJSONArray(methodName);
                        for (int i = 0; i < paramslist.length(); i++) {
                            params[i] = paramslist.get(i);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return params;
        }

        public void loadJSONFromAsset() {
            try {
                InputStream is = MainActivity2.this.getAssets().open("functionparams.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                String json = new String(buffer, "UTF-8");
                JSONObject all = new JSONObject(json);
                paramsJson = all.getJSONObject(name);
            } catch (IOException | JSONException ex) {
                ex.printStackTrace();
            }
        }
    }

}