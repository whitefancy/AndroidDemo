package com.whitefancy.sdk.game.api;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;


public class MyPlatform {

    private static int callbackLuaFuncLogin = 0;
    private static int callbackLuaFuncPay = 0;
    private static int callbackLuaFuncProductList = 0;
    private static boolean sNaverCafeOpen = false;
    private static String mChannelName = "default";
    private static String platformId = "";
    private static int sLuaFuncRealName;
    private static int sLuaFuncQuery;
    private static String TAG = "MyPlatform";

    public static void PlatformInit() {
    }

    public static String PlatformInitNaverCafe() {
        return "";
    }

    public static String getPlanIdAndChannel() {
        return "";
    }

    public static void PlatformRealNameData(final int luaFunc) {
    }


    public static void onRealNameCallback(final String playInfo) {
    }

    public static void GetRealNameDataTotalPay(int luaFunc) {
    }

    public static void onSettingQueryCallback(final String playInfo) {

    }


    public static void RefreshTotalTime(int ts, int ol_time) {
    }


    public static void PlatformLogin(final int luaFunc) {
        callbackLuaFuncLogin = luaFunc;

    }

    public static void PlatformBindAccount(String channel, final int luaFunc) {

    }

    public static void LoginBtnClick(String channel) {

    }

    public static void PlatformOnLogin(final int loginCode, final String userId, final String userToken, final String channelName) {
        mChannelName = channelName;
        if (callbackLuaFuncLogin > 0) {
            if (loginCode == 1) {
                platformId = userId;

                return;
            }
            callbackLuaFuncLogin = 0;
        }

    }

    public static void PlatformLogout() {
        Log.d("platform logout", "called");
    }

    public static void PlatformPay(final String params, final int luaFunc) {
        if (callbackLuaFuncPay > 0) {
            callbackLuaFuncPay = 0;
        }
        Log.d("PlatformPay", params);
        callbackLuaFuncPay = luaFunc;
        JSONObject payInfo;
        try {
            payInfo = new JSONObject(params);
        } catch (JSONException e) {
        }
    }

    public static double getCurrency(String productCode) {
        double currencyCount = 0;
        if (productCode.equals("yb0001")) {
            currencyCount = 4.99;
        } else if (productCode.equals("yb0002")) {
            currencyCount = 9.99;
        } else if (productCode.equals("yb0003")) {
            currencyCount = 19.99;
        } else if (productCode.equals("yb0004")) {
            currencyCount = 49.99;
        } else if (productCode.equals("yb0005")) {
            currencyCount = 99.99;
        } else if (productCode.equals("sp0001")) {
            currencyCount = 4.99;
        } else if (productCode.equals("sp0002")) {
            currencyCount = 19.99;
        }
        return currencyCount;
    }

    private static String getItemName(String productCode) {
        return "";
    }

    private static String getProductID(int id) {
        return "";
    }

    public static void PlatformOnPayEnd(int payCode, String productId) {
        if (payCode == 1) {
            Log.d("on pay end", "after");
        }
    }

    public static String PlatformGetName() {
        return "luobo_cn";
    }

    public static String PlatformGetChannel() {
        return "luobo_cn";
    }

    public static int PlatformIsNeedExitReLogin() {
        return 1;
    }

    public static int PlatformIsNeedShowUserCenter() {
        return 0;
    }

    public static void PlatformShowUserCenter() {

    }

    public static int PlatformIsNeedSubmitUinfo() {
        return 1;
    }

    public static void PlatformSubmitUinfo(String jsonInfos) {
    }

    public static int PlatformIsNeedSubUinfoCreateRole() {
        return 1;
    }

    public static void PlatformNeedSubUinfoCreateRole(String jsonInfos) {
        JSONObject ob;

    }

    public static int PlatformIsNeedPlatformExit() {
        return 0;
    }

    public static void PlatformExitGame(final int luaFunc) {

    }

    public static void PlatformLogEvent(String eventName, String uid, String value) {

    }

    private static int luaFuncShareLinkCallback = 0;
    private static int luaFuncInviteFriendCallback = 0;

    public static void PlatformShareLink(final int luaFunc, final String linkUrl, final String contentDescription, final String contentTitle) {

    }

    public static void PlatformInviteFriend(final int luaFunc) {

    }


    public static void PlatformLikeClick() {

    }

    public static void logMolPay(final int money) {

    }

    public static String getNotificationDeviceId() {
        return getDevicePushId();
    }

    public static String getDevicePushId() {
        return "";
    }

    //--------------------------------------------------------------------------------------------------------
    public static void logEvent(String eventName, String eventValue) {

    }

    public static void logThirdPay(String channel, final int money) {

    }

    public static String getMainServerUrl() {
        return "http://120.55.38.130";
    }

    public static void LogError(String errorinfo, String userid, String extraInfo, String version) {
    }

    private static String deviceAAID = null;

    public static String getDeviceAAID() {
        return "";
    }

    public static String getLanguage() {
        String language = Locale.getDefault().getLanguage() + "_" + Locale.getDefault().getCountry();
        if (language == null)
            return "";
        return language;
    }

    public static String getPlayerArea() {
        String area = Locale.getDefault().getCountry();
        if (area == null)
            return "";
        return area;
    }


    public static void PlatformShareScreenshots(String imgUrl) {

    }

    public static void PlatformOpenNaverCafe(int id) {

    }

    public static String getProductList(String jsonStr, int luaFunc) {


        return "";
    }


    public static void setGameVolume(int value) {
    }

    public static void GameInitSuccess() {

    }
}
