package com.whitefancy.test.tool.func;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Selection;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends Activity {
    public TextView textView;
    public static Handler handler;
    MethodBean game;
    MethodBean device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_main2);
        initTestMethods();
        initResultView();
        testOnCreat();
    }

    private void testOnCreat() {
        FunctionUtils.init(this);
    }

    private void initTestMethods() {
        game = new MethodBean("com.whitefancy.test.tool.func.FunctionUtils", (Spinner) findViewById(R.id.spinner1));
        initSpinner(game);
        device = new MethodBean("com.whitefancy.test.tool.func.FunctionUtils", (Spinner) findViewById(R.id.spinner2));
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

    public void ConfirmDropdown(View view) {
        int i = Integer.parseInt(view.getTag().toString());
        final MethodBean bean = (i == 1) ? game : device;
        String selectedText = String.valueOf(bean.spinner.getSelectedItem());
        final Method method = bean.methodMap.get(selectedText);
        this.runOnUiThread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                String result = invokePlatformMethod(method, bean);
                showInText(result);
            }
        });

    }

    private void showInText(String result) {
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("result", result);
        message.setData(bundle);
        handler.sendMessage(message);
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

    public void Travel(View view) {
        callAllMethod(game);
        callAllMethod(device);
    }

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
        FunctionUtils.init(this);
    }

    public void Pay(View view) {
    }

    public void Submit(View view) {
    }

    public void Login(View view) {

    }

    public void Logout(View view) {

    }

    public void DeviceInfo(View view) {
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
                InputStream is = MainActivity2.this.getAssets().open("test_functionparams.json");
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