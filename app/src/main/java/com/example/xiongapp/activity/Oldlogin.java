package com.example.xiongapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.xiongapp.R;
import com.example.xiongapp.db.RongUser;
import com.example.xiongapp.fragment.Home;
import com.example.xiongapp.server.FakeServer;
import com.example.xiongapp.utils.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

public class Oldlogin extends AppCompatActivity implements View.OnClickListener, RongIM.UserInfoProvider {
    private static final String token1 = "+FMxHiaGpuT8gvRQn4jzjqgg8tifpt/Ebsl+FwYkaf240x/WQ2YnC6W9YKGglSRl+d9gBD4D+myf+eB3eixwtA==";
    private static final String token2 = "vjxQh1C5QmfrCGkUZyWvO7YC4Q425yp9vyfUFQqMl4Lr6z9eP9EznvNLMufUdl1rUldiXGnUAp8=";
    private List<RongUser> userIdList;
    private static final String TAG = "MainActivity";
    private EditText userid;
    private EditText uname;
    String token;
    private Button mUser1, mUser2,login;
    private static String mSenderIdTest; //发送信息者ID
    private static String mSenderNameTest; //发送信息者的昵称
    private static String mPortraitUriTest = "http://static.yingyonghui.com/screenshots/1657/1657011_5.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.oldlogin );
//        mUser1 = (Button) findViewById( R.id.connect1 );
//        mUser2 = (Button) findViewById( R.id.connect2 );
        userid=(EditText) findViewById( R.id.userID );
        uname=(EditText) findViewById( R.id.uname );
        login=(Button) findViewById( R.id.login );
//        mUser1.setOnClickListener( this );
//        mUser2.setOnClickListener( this );
        login.setOnClickListener( this );

        initUserInfo();

    }
    public String getSenderIdTest() {
        if (!"".equals(userid.getText().toString().trim())) {
            Oldlogin.mSenderIdTest=userid.getText().toString().trim();

        }
        return mSenderIdTest;
    }
    public String getSenderName(){
        mSenderNameTest=uname.getText().toString().trim();
        return mSenderNameTest;
    }

    private void getToken() {

        FakeServer.getToken(getSenderIdTest(),getSenderName(), mPortraitUriTest, new HttpUtil.OnResponse() {
            @Override
            public void onResponse(int code, String body) {
                if (code == 200) {
                    JSONObject jsonObj = null;
                    try {
                        jsonObj = new JSONObject(body);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    token = jsonObj.optString("token");
                    connectRongServer(token);
                    Log.i(TAG,"获取的 token 值为:\n"+mSenderNameTest + '\n');
                    Log.i(TAG, "获取的 token 值为:\n" + token + '\n');
                } else {
                    Log.i(TAG, "获取 token 失败" + '\n');
                }
            }
        });
    }
    private void connectRongServer(String token) {
        RongIM.connect( token, new RongIMClient.ConnectCallback() {
            @Override
            public void onSuccess(String userId) {
//                if (userId.equals( "1" )) {
//                    mUser1.setText( "用户1连接服务器成功" );
                    startActivity( new Intent( Oldlogin.this, Home.class ) );
                 Toast.makeText( Oldlogin.this, "connect server success "+userId, Toast.LENGTH_SHORT ).show();
//                } else {
//                    startActivity( new Intent( MainActivity.this, Home.class ) );
//                    Toast.makeText( MainActivity.this, "connect server success 2", Toast.LENGTH_SHORT ).show();
//                }
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e( TAG, "connect failure errorCode is : " + errorCode.getValue() );
            }

            @Override
            public void onTokenIncorrect() {
                Log.e( TAG, "token is error ,please check token and app key" );
            }
        } );
    }

    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.connect1:
//                connectRongServer( token1 );
//                break;
//            case R.id.connect2:
//                connectRongServer( token2 );
//                break;
            case R.id.login:
                getToken();
                break;
        }
    }
    private void initUserInfo() {
        userIdList = new ArrayList<>();
        userIdList.add( new RongUser( "11", "CX", "http://www.51zxw.net/bbs/UploadFile/2013-4/201341122335711220.jpg" ) );     // 联通图标
        userIdList.add( new  RongUser( "12", "CC", "http://img02.tooopen.com/Download/2010/5/22/20100522103223994012.jpg" ) );  // 移动图标
        userIdList.add( new  RongUser( "KEFU149136384467630", "在线客服Xiong", "http://img02.tooopen.com/Download/2010/5/22/20100522103223994012.jpg" ) );
        RongIM.setUserInfoProvider( this, true );
    }

    @Override
    public UserInfo getUserInfo(String s) {
        for (RongUser i : userIdList) {
            if (i.getUserId().equals( s )) {
                Log.e( TAG, i.getPortraitUri() );
                return new UserInfo( i.getUserId(), i.getName(), Uri.parse( i.getPortraitUri() ) );
            }
        }
        Log.e( TAG, "UserId is : " + s );
        return null;
    }

}
