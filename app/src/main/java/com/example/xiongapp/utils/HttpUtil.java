package com.example.xiongapp.utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import okhttp3.OkHttpClient;
import okhttp3.Request;
/**
 * Created by Beyond on 18/10/2016.
 */

public class HttpUtil {

    public interface OnResponse {
        void onResponse(int code, String body);
    }

    private OnResponse callback;

    public void setOnResponse(OnResponse callback) {
        this.callback = callback;
    }

    public void post(final String appKey, final String appSecret, final String registerData, final OnResponse callback) {
        final String path = "https://api.cn.ronghub.com/user/getToken.json";
        final String nonce = Integer.toString(new Random().nextInt(1000));
        final String timeStamp = Long.toString( System.currentTimeMillis());
        final String signature = SHA1Tool.SHA1(appSecret + nonce + timeStamp);

        new Thread() {
            private HttpURLConnection httpURLConnection;
            @Override
            public void run() {
                try {
                    URL url = new URL(path);
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setConnectTimeout(5000);

                    httpURLConnection.setRequestProperty("RC-App-Key", appKey);
                    httpURLConnection.setRequestProperty("RC-Nonce", nonce);
                    httpURLConnection.setRequestProperty("RC-Timestamp", timeStamp);
                    httpURLConnection.setRequestProperty("RC-Signature", signature);
                    httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    httpURLConnection.setRequestProperty("Content_Length", registerData.length() + "");

                    httpURLConnection.setDoOutput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();//得到输出流
                    outputStream.write(registerData.getBytes());
                    outputStream.close();

                    InputStream inputStream;
                    int code = httpURLConnection.getResponseCode();
                    if(code == 200)
                        inputStream = httpURLConnection.getInputStream();
                    else
                        inputStream = httpURLConnection.getErrorStream();
                    String json = StreamTools.readInputStream(inputStream);
                    inputStream.close();

                    if (callback != null)
                        callback.onResponse(code, json);

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    httpURLConnection.disconnect();
                }
            }
        }.start();
    }
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

}