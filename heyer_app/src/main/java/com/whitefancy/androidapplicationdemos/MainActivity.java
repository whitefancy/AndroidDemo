package com.whitefancy.androidapplicationdemos;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Selection;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.iflytek.voicecloud.rtasr.DraftWithOrigin;
import com.iflytek.voicecloud.rtasr.MyWebSocketClient;
import com.iflytek.voicecloud.rtasr.util.EncryptUtil;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;


public class MainActivity<handler> extends AppCompatActivity {
    String status = "语音翻译";
    boolean isRecording = false;
    private AudioRecord recorder;
    private Thread recordingThread;
    int sampleRateInHz = 16000;
    int audioChannel = AudioFormat.CHANNEL_IN_MONO;
    int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;

    int BufferElements2Rec = 1024;
    int BytesPerElement = 2;

    private String TAG = "MyWebSocketClient";

    private boolean VoiceInited;
    // appid
    private static final String APPID = "b849528e";

    // appid对应的secret_key
    private static final String SECRET_KEY = "b30e35fedc9e3419d84d57e44c053b2f";

    // 请求地址
    private static final String HOST = "rtasr.xfyun.cn/v1/ws";

    private static final String BASE_URL = "wss://" + HOST;

    private static final String ORIGIN = "https://" + HOST;

    // 音频文件路径
    private static final String AUDIO_PATH = "./resource/test_1.pcm";

    // 每次发送的数据大小 1280 字节
    private static final int CHUNCKED_SIZE = 1280;
    private MyWebSocketClient client;
    public TextView textView;
    public static Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.content1);
        textView.setMovementMethod(new ScrollingMovementMethod());
        SpannableString spannable = new SpannableString("点击按钮开始实时翻译。");
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

    @Override
    protected void onStart() {
        super.onStart();
        int buffersize = AudioRecord.getMinBufferSize(sampleRateInHz,
                audioChannel,
                audioEncoding);
        Toast.makeText(MainActivity.this, "采样率：" + buffersize, Toast.LENGTH_SHORT).show();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    1234);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopRecording();
    }

    public void click(View view) {
        if (!isRecording) {
            Toast.makeText(MainActivity.this, "翻译中", Toast.LENGTH_SHORT).show();
            status = "正在翻译";
            startRecording();
        } else {
            Toast.makeText(MainActivity.this, "翻译结束", Toast.LENGTH_SHORT).show();
            status = "语音翻译";
            stopRecording();
        }
        ((Button) view).setText(status);
    }

    private void stopRecording() {
        if (null != recorder) {
            isRecording = false;
            recorder.stop();
            recorder.release();
            recordingThread = null;
        }
        closeVoice();
    }

    private void closeVoice() {
        if (VoiceInited) {
            send(client, "{\"end\": true}".getBytes());
            Log.d(TAG, MyWebSocketClient.getCurrentTimeStr() + "\t发送结束标识完成");
            // 等待连接关闭
            try {
                MyWebSocketClient.connectClose.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            VoiceInited = false;
        }
    }

    public static void send(WebSocketClient client, byte[] bytes) {
        if (client.isClosed()) {
            throw new RuntimeException("client connect closed!");
        }

        client.send(bytes);
    }


    private void startRecording() {
        recorder = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRateInHz,
                audioChannel,
                audioEncoding, BufferElements2Rec * BytesPerElement
        );
        recorder.startRecording();
        isRecording = true;
        recordingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                translate();
            }

        });
        recordingThread.start();
    }

    private void translate() {
        initVoice();

        long lastTs = 0;
        short sData[] = new short[BufferElements2Rec];
        while (isRecording) {
            recorder.read(sData, 0, BufferElements2Rec);
            byte bData[] = short2byte(sData);
            long curTs = System.currentTimeMillis();
            if (lastTs == 0) {
                lastTs = System.currentTimeMillis();
            } else {
                long s = curTs - lastTs;
                if (s < 40) {
                    Log.d(TAG, "error time interval: " + s + " ms");
                }
            }
            send(client, bData);
            // 每隔40毫秒发送一次数据
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    // 生成握手参数
    public static String getHandShakeParams(String appId, String secretKey) {
        String ts = System.currentTimeMillis() / 1000 + "";
        String signa = "";
        try {
            signa = EncryptUtil.HmacSHA1Encrypt(EncryptUtil.MD5(appId + ts), secretKey);
            return "?appid=" + appId + "&ts=" + ts + "&signa=" + URLEncoder.encode(signa, "UTF-8") + "&lang=cn";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    private void initVoice() {
        if (VoiceInited) {
            return;
        }
        URI url = null;
        try {
            url = new URI(BASE_URL + getHandShakeParams(APPID, SECRET_KEY));
            DraftWithOrigin draft = new DraftWithOrigin(ORIGIN);
            CountDownLatch handshakeSuccess = new CountDownLatch(1);
            MyWebSocketClient.connectClose = new CountDownLatch(1);
            client = new MyWebSocketClient(url, draft, handshakeSuccess, MyWebSocketClient.connectClose);
            client.trustAllHosts(client);
            client.connect();

            while (!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
                Log.d(TAG, MyWebSocketClient.getCurrentTimeStr() + "\t连接中");
                Thread.sleep(1000);
            }

            // 等待握手成功
            handshakeSuccess.await();
            Log.d(TAG, sdf.format(new Date()) + " 开始发送音频数据");
            VoiceInited = true;
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        } catch (URISyntaxException uriSyntaxException) {
            uriSyntaxException.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] short2byte(short[] sData) {
        int shortArrSize = sData.length;
        byte[] bytes = new byte[shortArrSize * 2];
        for (int i = 0; i < shortArrSize; i++) {
            bytes[i * 2] = (byte) (sData[i] & 0x00FF);
            bytes[i * 2 + 1] = (byte) (sData[i] >> 8);
            sData[i] = 0;
        }
        return bytes;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1234: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Log.d("TAG", "permission denied by user");
                }
                return;
            }
        }
    }

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss.SSS");

    public static String getCurrentTimeStr() {
        return sdf.format(new Date());
    }

}
