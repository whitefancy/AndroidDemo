package com.whitefancy.sdk.device.api;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/30/030.
 */

public class CabbageGetImeiUtil {
    //获取第一个IMEI
    public static String getImei1(Context context) {
        if (Build.VERSION.SDK_INT < 21) {//api小于21时只有这一个方法，所以获取到的值可能是meid,也可能是imei
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Activity.TELEPHONY_SERVICE);
            String imei = tm.getDeviceId();
            return imei != null ? imei : "";
        } else {
            try {
                List<String> list = new ArrayList<String>();
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                boolean ret = false;
                if (tm != null) {
                    for (Method m : tm.getClass().getDeclaredMethods()) {
                        if (m.getName().equals("getPhoneCount")) {
                            ret = true;
                            break;
                        }
                    }
                }
                if (!ret) return ""; //没有那个方法,主要是模拟器没这个方法会崩溃
                Map<String, String> map = getImeiAndMeid(context);
                return map.get("imei1");
            } catch (Exception e) {
                return "";
            }
        }

    }

    //获取第二个IMEI，MEID。可能为空（单卡时）
    @SuppressLint("NewApi")
    public static String getImei2(Context context) {
        if (Build.VERSION.SDK_INT < 21) return "";
        try {
            List<String> list = new ArrayList<String>();
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            boolean ret = false;
            if (tm != null) {
                for (Method m : tm.getClass().getDeclaredMethods()) {
                    if (m.getName().equals("getPhoneCount")) {
                        ret = true;
                        break;
                    }
                }
            }
            if (!ret) return ""; //没有那个方法,主要是模拟器没这个方法会崩溃
            Map<String, String> map = getImeiAndMeid(context);
            return map.get("imei2");

        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 5.0 6.0统一使用这个获取IMEI IMEI2 MEID
     *
     * @param ctx
     * @return
     */
    @SuppressLint("NewApi")
    public static Map getImeiAndMeid(Context ctx) {
        Map<String, String> map = new HashMap<String, String>();
        TelephonyManager mTelephonyManager = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
        Class<?> clazz = null;
        Method method = null;//(int slotId)

        try {
            clazz = Class.forName("android.os.SystemProperties");
            method = clazz.getMethod("get", String.class, String.class);
            String gsm = (String) method.invoke(null, "ril.gsm.imei", "");
            String meid = (String) method.invoke(null, "ril.cdma.meid", "");
            map.put("meid", meid);
            if (!TextUtils.isEmpty(gsm)) {
                //the value of gsm like:xxxxxx,xxxxxx
                String imeiArray[] = gsm.split(",");
                if (imeiArray != null && imeiArray.length > 0) {
                    map.put("imei1", imeiArray[0]);
                    if (imeiArray.length > 1) {
                        map.put("imei2", imeiArray[1]);
                    } else {
                        map.put("imei2", mTelephonyManager.getDeviceId(1));
                    }
                } else {
                    map.put("imei1", mTelephonyManager.getDeviceId(0));
                    map.put("imei2", mTelephonyManager.getDeviceId(1));
                }
            } else {
                map.put("imei1", mTelephonyManager.getDeviceId(0));
                map.put("imei2", mTelephonyManager.getDeviceId(1));
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return map;
    }


    /**
     * 拿到imei或者meid后判断是有多少位数
     *
     * @param ctx
     * @return
     */
    public static int getNumber(Context ctx) {
        int count = 0;
        long number = Long.parseLong(getImei1(ctx).trim());

        while (number > 0) {
            number = number / 10;
            count++;
        }
        return count;
    }

    //获取meid
    public static String getMeid(Context context) {
        if (Build.VERSION.SDK_INT < 21) return "";
        try {
            List<String> list = new ArrayList<String>();
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            boolean ret = false;
            if (tm != null) {
                for (Method m : tm.getClass().getDeclaredMethods()) {
                    if (m.getName().equals("getPhoneCount")) {
                        ret = true;
                        break;
                    }
                }
            }
            if (!ret) return ""; //没有那个方法,主要是模拟器没这个方法会崩溃
            Map<String, String> map = getImeiAndMeid(context);
            return map.get("meid");

        } catch (Exception e) {
            return "";
        }
    }
}
