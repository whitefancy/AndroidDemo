package com.u8.sdk.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class U8HttpUtils {

    public String httpGet(String url, Map<String, String> params) {

        List<NameValuePair> lst = new ArrayList<NameValuePair>();
        if (params != null) {
            Iterator<String> keyItors = params.keySet().iterator();
            while (keyItors.hasNext()) {
                String key = keyItors.next();
                String val = params.get(key);
                lst.add(new NameValuePair(key, val));
            }
        }

        return httpGet(url);
    }

    public String httpPost(String url, Map<String, String> params) {

        List<NameValuePair> lst = new ArrayList<NameValuePair>();
        if (params != null) {
            Iterator<String> keyItors = params.keySet().iterator();
            while (keyItors.hasNext()) {
                String key = keyItors.next();
                String val = params.get(key);
                lst.add(new NameValuePair(key, val));
            }
        }

        return httpPost(url, lst);
    }

    public static String httpGet(String urlStr) {

        String paramsEncoded = "";

        String fullUrl = urlStr + "?" + paramsEncoded;

        String result = null;
        URL url = null;
        HttpURLConnection connection = null;
        InputStreamReader in = null;
        try {
            url = new URL(fullUrl);
            connection = (HttpURLConnection) url.openConnection();
            in = new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(in);
            StringBuffer strBuffer = new StringBuffer();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                strBuffer.append(line);
            }
            result = strBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }

    public static String httpPost(String urlStr, List<NameValuePair> params) {

        String paramsEncoded = "";
        String result = null;
        URL url = null;
        HttpURLConnection connection = null;
        InputStreamReader in = null;
        try {
            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Charset", "utf-8");
            DataOutputStream dop = new DataOutputStream(
                    connection.getOutputStream());
            dop.writeBytes(paramsEncoded);
            dop.flush();
            dop.close();

            in = new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(in);
            StringBuffer strBuffer = new StringBuffer();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                strBuffer.append(line);
            }
            result = strBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }

    class NameValuePair {
        String name;
        String value;

        public NameValuePair(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }
}
