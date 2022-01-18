package com.whitefancy.demo.home.items.server;

import android.util.Base64;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.GZIPOutputStream;

public class Server {


    public static String httpGet(String urlStr) {
        URL url = null;
        StringBuffer response = new StringBuffer();
        try {
            url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (
                MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
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

}
