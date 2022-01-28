package com.whitefancy.test.tool.func;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.util.Log;

import org.json.JSONObject;

import static android.content.Context.CLIPBOARD_SERVICE;

public class FunctionUtils {
    private static String TAG = "FunctionUtils";
    private static Activity sActivity;

    public static String GetClipBoardString() {
        try {
            ClipboardManager clipboardManager = (ClipboardManager) sActivity.getSystemService(CLIPBOARD_SERVICE);
            ClipData clipData = clipboardManager.getPrimaryClip();
            ClipData.Item item = clipData.getItemAt(0);
            String clipText = item.getText().toString();
            JSONObject result = new JSONObject();
            result.put("data", clipText);
            Log.i(TAG, "GetClipBoardString " + result.toString());
            return result.toString();
        } catch (Exception e) {
            return String.format("{\"err_msg\": \"%s\"}", e.toString());
        }
    }

    public static String writeClipBoardString(String params) {
        try {
            ClipboardManager clipboard = null;
            clipboard = (ClipboardManager) sActivity.getSystemService(CLIPBOARD_SERVICE);
            ClipData mClipData = null;
            mClipData = ClipData.newPlainText("Label", params);
            clipboard.setPrimaryClip(mClipData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void init(Activity activity) {
        sActivity = activity;
    }
}
