package com.example.xiongapp.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.xiongapp.R;

public class Huihua extends  FragmentActivity{
    private TextView mName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.huihua );
        mName = (TextView) findViewById(R.id.title1);

        String sId = getIntent().getData().getQueryParameter("targetId");   // targetId:单聊即对方ID，群聊即群组ID
        String sName = getIntent().getData().getQueryParameter("title");    // 获取昵称

        if (!TextUtils.isEmpty(sName)) {
            mName.setText(sName);
        } else {
            // sId
            // TODO 拿到id 去请求自己服务端
        }
    }
}
