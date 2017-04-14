package com.example.xiongapp.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xiongapp.R;
import com.example.xiongapp.db.User;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

/**  
 * 首页  
 * @class  MainActivity  
 * @author smile   
 * @date   2015-6-5 下午3:02:13  
 *   
 */
public class UserActivity extends Activity {
	private static final String TAG = "MainActivity";
	private static String mSenderIdTest; //发送信息者ID
	private static String mSenderNameTest; //发送信息者的昵称
	private static String mPortraitUriTest = "http://static.yingyonghui.com/screenshots/1657/1657011_5.jpg";
	String token;
	@InjectView(R.id.iv_left)
	ImageView iv_left;
	@InjectView(R.id.tv_title)
	TextView tv_title;
	
	@InjectView(R.id.tv_user)
	TextView tv_user;
	
	@InjectView(R.id.btn_bind)
	Button btn_bind;
	@InjectView(R.id.lianjie_rongyun)
	Button login_rongyun;
	@InjectView(R.id.btn_reset)
	Button btn_reset;
	String from;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uesr );
		ButterKnife.inject(this);
		from = getIntent().getStringExtra("from");
		if(from.equals("login")){
			btn_bind.setVisibility(View.VISIBLE);
		}else{
			btn_bind.setVisibility(View.GONE);
		}
		iv_left.setVisibility(View.VISIBLE);
		tv_title.setText("首页");
	}

	private void UpdateUser(){
		User user = BmobUser.getCurrentUser(this, User.class);
		//用户只有绑定过手机号或者用手机号注册登录过就可以直接通过手机号码来重置用户密码了，所以加下这个判断
		if(user!=null && user.getMobilePhoneNumberVerified()!=null && user.getMobilePhoneNumberVerified()){
			btn_reset.setVisibility(View.VISIBLE);
		}else{
			btn_reset.setVisibility(View.INVISIBLE);
		}
		tv_user.setText(user.getUsername()+"++++-"+user.getAge()+"+-"+user.getMobilePhoneNumberVerified()+"-"+user.getMobilePhoneNumber());
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		UpdateUser();
	}
	
	@OnClick(R.id.iv_left)
	public void back(View view) {
		finish();
	}
	
	@OnClick(R.id.btn_bind)
	public void bind(View view) {
		startActivity(new Intent(this, UserBindPhoneActivity.class));
	}
	
	@OnClick(R.id.btn_reset)
	public void resetPasswordByCode(View view) {
		startActivity(new Intent(this, ResetPasswordActivity.class));
	}
//	@OnClick(R.id.lianjie_rongyun)
//	public void loginRong(View view) {
//		getToken();
//	}
//	public String getSenderIdTest() {
//		User user = BmobUser.getCurrentUser(this, User.class);
//		UserActivity.mSenderIdTest=user.getUsername();
//		return mSenderIdTest;
//	}
//	public String getSenderName(){
//		User user = BmobUser.getCurrentUser(this, User.class);
//		UserActivity.mSenderNameTest=user.getUsername();
//		return mSenderNameTest;
//	}
//	private void getToken() {
//
//		FakeServer.getToken(getSenderIdTest(),getSenderName(), mPortraitUriTest, new HttpUtil.OnResponse() {
//			@Override
//			public void onResponse(int code, String body) {
//				if (code == 200) {
//					JSONObject jsonObj = null;
//					try {
//						jsonObj = new JSONObject(body);
//					} catch (JSONException e) {
//						e.printStackTrace();
//					}
//					token = jsonObj.optString("token");
//					connectRongServer(token);
//					Log.i(TAG,"获取的 token 值为:\n"+ '\n');
//					Log.i(TAG, "获取的 token 值为:\n" + token + '\n');
//				} else {
//					Log.i(TAG, "获取 token 失败" + '\n');
//				}
//			}
//		});
//	}
//	private void connectRongServer(String token) {
//		RongIM.connect( token, new RongIMClient.ConnectCallback() {
//			@Override
//			public void onSuccess(String userId) {
//				//                if (userId.equals( "1" )) {
//				//                    mUser1.setText( "用户1连接服务器成功" );
//				startActivity( new Intent( UserActivity.this, Home.class ) );
//				Toast.makeText( UserActivity.this, "connect server success "+userId, Toast.LENGTH_SHORT ).show();
//				//                } else {
//				//                    startActivity( new Intent( MainActivity.this, Home.class ) );
//				//                    Toast.makeText( MainActivity.this, "connect server success 2", Toast.LENGTH_SHORT ).show();
//				//                }
//			}
//
//			@Override
//			public void onError(RongIMClient.ErrorCode errorCode) {
//				Log.e( TAG, "connect failure errorCode is : " + errorCode.getValue() );
//			}
//
//			@Override
//			public void onTokenIncorrect() {
//				Log.e( TAG, "token is error ,please check token and app key" );
//			}
//		} );
//	}

}
