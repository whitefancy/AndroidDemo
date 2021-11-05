package com.whitefancy.sdk.report.api;

import android.content.Context;

import com.whitefancy.sdk.service.bean.SDKDataBean;

import static android.content.Context.MODE_PRIVATE;
import static com.whitefancy.sdk.service.bean.SDKDataBean.isGuideShow;
import static com.whitefancy.sdk.service.bean.SDKDataBean.sharedPreferences;

public class CabbageGameReport {
    /**
     * 首次打开应用初始化sdk时上报激活
     **/
    public static void reportFirtActivation(Context mContext) {
        SDKDataBean.context = mContext;
        //偏好设置
        sharedPreferences = mContext.getSharedPreferences("FIRST_Activation", MODE_PRIVATE);
        //获取偏好设置中的数据 is_activation ,0为未上传 ， 1为上传
        isGuideShow = sharedPreferences.getString("is_activation", "nullData");
        SDKDataBean.isActivation = isGuideShow;
        if (isGuideShow.equals("nullData")) {
            //文件不存在
            //设置偏好
            sharedPreferences
                    .edit()
                    .putString("is_activation", "0")
                    .commit();
            //二次获取
            isGuideShow = sharedPreferences.getString("is_activation", "nullData");
        }
        if (isGuideShow.equals("0") || isGuideShow.equals("nullData")) {
//            new CabbageHttp().sendGet(CabbageConf.CABBAGE_SDK_FIRST, CabbageClientParams.firstActivation(), 1, new CabbageSDKCallback() {
//                @Override
//                public void onResult(String flag, JSONObject json) {
//                    CabbageLog.debug("activation_json :" + json.toString());
//                    if (flag == CabbageBean.HTTP_REQUEST_SUCCESS) {
//                        sharedPreferences.edit().putString("is_activation", "1").commit();
//                        CabbageSDKAPP.isActivation = "1";
//                    }
//                }
//            });
        }
    }

}
