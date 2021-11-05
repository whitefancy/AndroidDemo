package com.whitefancy.sdk.service.bean;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Timer;

public class SDKDataBean {
    /**
     * uiActivity
     */
    public static Activity uiActivity;
    /**
     * 用户UID
     **/
    public static String userUID;

    // 用户Token
    public static String accesstoken;

    // toufang
    public static String packageId;
    //toufang
    public static String o_package_id;
    //头条channel
    public static String tt_channel;

    // AppKey由尤达分配给CP
    public static String appKey;

    //由尤达分配给CP
    public static String gameID;

    //由尤达分配给CP的安全密钥
    public static String sAppSecret;

    public static String openUID;

    //设备mac地址
    public static String mac;

    //设备唯一识别码
    public static String uuid;

    /**
     * 推广员信息
     **/
    public static String sPromoter;
    /**
     * 渠道信息
     **/
    public static String sChannel;

    /**
     * 游戏时间记录id
     **/
    public static String sGamePlayId;

    public static final String account_type = "1";
    //包名
    public static String packageName;
    //签名文件MD5
    public static String signMd5;
    /**
     * 渠道ID
     **/
    public static String thirdChannelID;
    /**
     * 渠道名
     **/
    public static String thirdChannelFlag;
    /**
     * 设备ANDROID_ID
     **/
    public static String androidID;

    /**
     * 设备ip
     **/
    public static String ip;

    /**
     * 设备序列号
     **/
    public static String serial;

    /**
     * 悬浮窗初始化的方向
     **/
    public static int popPosition;

    /**
     * 设备信息  1 android， cbg_btn_red ios
     **/
    public static int device_type = 1;

    /**
     * Android imei
     **/
    public static String imei;
    /**
     * 设备唯一码 android传imei
     **/
    public static String device_num;
    /**
     * 设备机型
     **/
    public static String device_os;
    /**
     * 设备版本
     **/
    public static String device_system;
    /**
     * 设备的机型厂商
     **/
    public static String device_factory;
    /**
     * 屏幕大小 num x num
     **/
    public static String device_screen;
    /**
     * 使用的网络
     **/
    public static String net_work;
    /**
     * 是否显示调试框
     **/
    public static String is_show_debug_ui;
    /**
     * 是否上报数据成功
     **/
    public static String isActivation = "0";
    //OAID
    public static String oaid;
    //VAID
    public static String vaid;
    //AAID
    public static String aaid;
    //是否进行广点通上报
    public static boolean isNeed2Report;
    //是否进行百度上报
    public static boolean bdNeed2Report;
    //渠道用户id
    public static String jh_userid;
    //用户是否为注册用户
    public static int channel_is_reg;
    //是否显示隐私权限,1是显示0不显示
    public static String isShowPrivacy = "1";
    public static String isGuideShow;
    public static SharedPreferences sharedPreferences;
    public static Timer timer;
    public static Context context;
}
