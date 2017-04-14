package com.example.xiongapp.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import com.example.xiongapp.R;
import com.example.xiongapp.db.User;
import com.example.xiongapp.server.FakeServer;
import com.example.xiongapp.utils.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

public class Home extends FragmentActivity {
    private ViewPager mViewPager;
    private FragmentPagerAdapter mFragmentPagerAdapter;
    private Fragment mConversationList;
    private Fragment mConversationFragment = null;
    private List<Fragment> mFragment = new ArrayList<>();
    private static final String TAG = "MainActivity";
    private static String mPortraitUriTest = "http://static.yingyonghui.com/screenshots/1657/1657011_5.jpg";
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.home );
        mConversationList = initConversationList();
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        LiaotianFragment liaotianFragment=new LiaotianFragment();
        mFragment.add(mConversationList);           // 加入会话列表
        mFragment.add(liaotianFragment);// 加入第二页

        // 配置ViewPager的适配器
        mFragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            @Override
            public int getCount() {
                return mFragment.size();
            }
        };
        mViewPager.setAdapter(mFragmentPagerAdapter);
        getToken();
    }
    private void getToken() {
        User user = BmobUser.getCurrentUser(this, User.class);
        FakeServer.getToken(user.getUsername(),user.getUsername(), mPortraitUriTest, new HttpUtil.OnResponse() {
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
                    Log.i(TAG,"获取的 token 值为:\n" + '\n');
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
               // startActivity( new Intent( MainActivity.this, Home.class ) );
                Toast.makeText(Home.this, "connect server success+++++++++++++++ "+userId, Toast.LENGTH_SHORT ).show();
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
    private Fragment initConversationList() {
        // appendQueryParameter 对具体的会话列表做展示
        if (mConversationFragment == null) {
            ConversationListFragment listFragment = new ConversationListFragment();
            Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                    .appendPath("conversationList")
                    .appendQueryParameter(Conversation.ConversationType.CUSTOMER_SERVICE.getName(),"false" )//客服
                    .appendQueryParameter(Conversation.ConversationType.PUSH_SERVICE.getName(),"false" )    //推送
                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false")     // 设置私聊会话是否聚合显示
                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")
                    .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")    // 公共服务号
                    .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")// 公共服务号
                    .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")  // 设置私聊会话是否聚合显示
                    .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")      // 设置私聊会是否聚合显示
                    .build();
            listFragment.setUri(uri);
            return listFragment;
        } else {
            return mConversationFragment;
        }
    }
}
