package com.whitefancy.sdk.device.api;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.UUID;
import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;
import static com.whitefancy.sdk.service.bean.SDKDataBean.imei;
import static com.whitefancy.sdk.service.bean.SDKDataBean.sharedPreferences;
import static com.whitefancy.sdk.service.bean.SDKDataBean.uiActivity;

/**
 * Created by Administrator on 2018/3/9.
 */

public class CabbageUtil {

    //判断是否为数字，
    public static boolean isNumeric(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断SDCard是否存在
     *
     * @return
     */
    public static boolean isSdCardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SD卡根目录路径
     *
     * @return
     */
    public static String getSdCardPath() {
        boolean exist = isSdCardExist();
        String sdCartdPath = "";
        if (exist) {
            sdCartdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return sdCartdPath;
    }

    /**
     * 手机号验证
     *
     * @param phone
     * @return
     */
    public static boolean verifyPhone(String phone) {
        return phone.matches("^[1][3,4,5,8][0-9]{9}$");
    }


    /**
     * 判断网络是否可用
     *
     * @param context
     * @return
     */
    public static String isWifiOrMobile(Context context) {
//        ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        //Gprs数据
//        NetworkInfo mobNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//        //WiFi数据
//        NetworkInfo wifiNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//        if(wifiNetInfo.isConnected()){
//            return "wifi" ;
//        }
//        else if(mobNetInfo.isConnected()&&!wifiNetInfo.isConnected()){
//            return "GPRS" ;
//        }
//        return "" ;
        String strNetworkType = "";
        ConnectivityManager connectMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = "WIFI";
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                strNetworkType = "GPRS";
            }
        }
        return strNetworkType;
    }


    /**
     * 判断网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    // 获取当前时间，格式yyyy-MM-dd HH:mm:ss
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    // 获取时间戳
    public static long getTimeStamp() {
        return (new Date().getTime()) / 1000;
    }

    /**
     * 获取Mac地址：需要获取网络状态权限
     **/
    public static String getLocalMacAddress(Context context) {
//       return null;
        String address = null;
        // 把当前机器上的访问网络接口的存入 Enumeration集合中
        Enumeration<NetworkInterface> interfaces = null;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface netWork = interfaces.nextElement();
                // 如果存在硬件地址并可以使用给定的当前权限访问，则返回该硬件地址（通常是 MAC）。
                byte[] by = netWork.getHardwareAddress();
                if (by == null || by.length == 0) {
                    continue;
                }
                StringBuilder builder = new StringBuilder();
                for (byte b : by) {
                    builder.append(String.format("%02X:", b));
                }
                if (builder.length() > 0) {
                    builder.deleteCharAt(builder.length() - 1);
                }
                String mac = builder.toString();

                // 从路由器上在线设备的MAC地址列表，可以印证设备Wifi的 name 是 wlan0
                if (netWork.getName().equals("wlan0")) {

                    address = mac;
                }

            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return address;
    }

    /**
     * 获取手机屏幕大小
     *
     * @param mContext
     * @return
     */
    public static final String getScreenSize(Context mContext) {

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return "" + outMetrics.widthPixels + "*" + outMetrics.heightPixels;
    }


    /**
     * 获取手机IMEI
     *
     * @param context
     * @return
     */
    public static final String getIMEI(Context context) {
        try {
            //实例化TelephonyManager对象
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //获取IMEI号
            String imei = telephonyManager.getDeviceId();
            //在次做个验证，也不是什么时候都能获取到的啊
            if (imei == null) {
                imei = "";
            }
            return imei;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }


    /**
     * 获取手机ANDROID_ID
     *
     * @param context
     * @return
     */
    public static String getAndroidID(Context context) {
        return Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }


    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 吐司
     *
     * @param context
     * @param msg
     */
    public static void showToast(final Context context, final String msg) {

        uiActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }


    /**
     * 获取设备IP
     */
    public static String getPhoneIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        // if (!inetAddress.isLoopbackAddress() && inetAddress
                        // instanceof Inet6Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 获取设备序列号SerialNumber
     */
    public static String getSerialNumber() {
        return Build.SERIAL;
    }


    /**
     * Return pseudo unique ID ：需要 api>=9
     *
     * @return ID
     */
    public static String getUniquePsuedoID() {
        // If all else fails, if the user does have lower than API 9 (lower
        // than Gingerbread), has reset their device or 'Secure.ANDROID_ID'
        // returns 'null', then simply the ID returned will be solely based
        // off their Android device information. This is where the collisions
        // can happen.
        // Thanks http://www.pocketmagic.net/?p=1662!
        // Try not to use DISPLAY, HOST or ID - these items could change.
        // If there are collisions, there will be overlapping data
        String m_szDevIDShort = "35" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10)
                + (Build.CPU_ABI.length() % 10) + (Build.DEVICE.length() % 10) +
                (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() % 10) +
                (Build.PRODUCT.length() % 10);

        // Thanks to @Roman SL!
        // http://stackoverflow.com/a/4789483/950427
        // Only devices with API >= 9 have android.os.Build.SERIAL
        // http://developer.android.com/reference/android/os/Build.html#SERIAL
        // If a user upgrades software or roots their device, there will be a duplicate entry
        String serial;
        try {
            if (Build.VERSION.SDK_INT >= 26) {
                serial = Build.getSerial();
            } else {
                serial = Build.SERIAL;
            }
            Log.d("QGQ", "serial_1 =" + serial);
//            //API>=9 使用serial号
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            serial = "serial";
        }
        Log.d("QGQ", "serial_2 =" + serial);
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

    /**
     * 数据流转string
     **/
    public static String inputStreamToString(InputStream in) {

        String str = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuffer sb = new StringBuffer();

            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return str;
    }

    public static void loopingMechanism(Context mContext) {
        imei = CabbageGetImeiUtil.getImei1(mContext);
        if (imei == null || imei.isEmpty()) {
            imei = CabbageGetImeiUtil.getImei2(mContext);
        }
        if (imei == null || imei.isEmpty()) {
            imei = CabbageGetImeiUtil.getMeid(mContext);
        }
        if (imei != null && !imei.isEmpty() && CabbageUtil.isNetworkConnected(mContext)) {
//            CabbageGameReport.reportFirtActivation(mContext);
        }
    }

    public static String getSignMd5Str(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];
            String signStr = encryptionMD5(sign.toByteArray());
            return signStr;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String encryptionMD5(byte[] byteStr) {
        MessageDigest messageDigest = null;
        StringBuffer md5StrBuff = new StringBuffer();
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(byteStr);
            byte[] byteArray = messageDigest.digest();
//            return Base64.encodeToString(byteArray,Base64.NO_WRAP);
            for (int i = 0; i < byteArray.length; i++) {
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                    md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
                } else {
                    md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5StrBuff.toString();
    }

    //防止过快点击
    private static long mLoginLastClickTime;// 用户判断多次点击的时间

    public static boolean isLoginFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (Math.abs(time - mLoginLastClickTime) < 2000) {
            return true;
        }
        mLoginLastClickTime = time;
        return false;
    }

    private static long mPayLastClickTime;// 用户判断多次点击的时间

    public static boolean isPayFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (Math.abs(time - mPayLastClickTime) < 4000) {
            return true;
        }
        mPayLastClickTime = time;
        return false;
    }

    //设置间距
    public static float setLineSpace(int height) {
        if (((float) (height * 0.04)) > 22) {
            return 17;
        } else {
            return (float) (height * 0.04);
        }
    }

    private static String isAgree;

    public static String checkIsAgreePrivacy() {
        sharedPreferences = uiActivity.getSharedPreferences("AGREEMENT_RESULT", MODE_PRIVATE);
        isAgree = sharedPreferences.getString("is_agree", "-1");
        Log.d("xcc", "isAgree =" + isAgree);
        return isAgree;
    }

    public static void setIsAgreePrivacy() {
        //0表示同意协议
        sharedPreferences = uiActivity.getSharedPreferences("AGREEMENT_RESULT", MODE_PRIVATE);
        sharedPreferences
                .edit()
                .putString("is_agree", "0")
                .commit();
    }

}
