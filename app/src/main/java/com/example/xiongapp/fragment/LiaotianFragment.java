package com.example.xiongapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.xiongapp.R;
import com.example.xiongapp.activity.WeatherMainActivity;

import java.util.ArrayList;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

import static io.rong.imlib.model.Conversation.ConversationType.CUSTOMER_SERVICE;

public class LiaotianFragment extends Fragment {
    public static LiaotianFragment instance = null;   // 单例模式
    private View mView;

    public static LiaotianFragment getInstance() {
        if (instance == null) {
            instance = new LiaotianFragment();
        }
        return instance;
    }

    private EditText user2;
    private Button mButton_Friend;
    private Button mButton_Customer;
    private Button taolunzhu;
    private Button weather;
    private Button uesr;
    //    CSCustomServiceInfo.Builder csBuilder = new CSCustomServiceInfo.Builder();
    //    CSCustomServiceInfo csInfo = csBuilder.nickName("融云").build();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ArrayList<String> userIds = new ArrayList<String>();
        userIds.add("18859833667");//增加 userId。
        userIds.add("1");//增加 userId。
        userIds.add("2");//增加 userId。
        userIds.add("11");//增加 userId。
        userIds.add("12");//增加 userId。
        RongIM.getInstance().addMemberToDiscussion("1001", userIds, new RongIMClient.OperationCallback() {

            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
       RongIM.getInstance().createDiscussionChat(getActivity(), userIds, "讨论组1001");
        mView = inflater.inflate( R.layout.friend, container, false );
        user2 = (EditText) mView.findViewById( R.id.user2 );
        mButton_Friend = (Button) mView.findViewById( R.id.friend );
        mButton_Friend.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RongIM.getInstance() != null) {
                    RongIM.getInstance().startPrivateChat( getActivity(), user2.getText().toString().trim(), "私人聊天" );
                }
            }
        } );

        mButton_Customer = (Button) mView.findViewById( R.id.customer );
        mButton_Customer.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RongIM.getInstance() != null) {
                    //                    RongIM.getInstance().startCustomerServiceChat(getActivity(), "KEFU149136384467630", "在线客服",csInfo);
                    RongIM.getInstance().startConversation( getActivity(), CUSTOMER_SERVICE, "KEFU149136384467630", "在线客服--Xiong" );
                }
            }
        } );
//        taolunzhu = (Button) mView.findViewById( R.id.taolun );
//        taolunzhu.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (RongIM.getInstance() != null) {
//                    RongIM.getInstance().startDiscussionChat( getActivity(), "1001", "标题" );
//                }
//            }
//        } );
        weather=(Button)mView.findViewById( R.id.coolweather );
        weather.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), WeatherMainActivity.class );
                startActivity( intent );
            }
        } );

        return mView;
    }




}
