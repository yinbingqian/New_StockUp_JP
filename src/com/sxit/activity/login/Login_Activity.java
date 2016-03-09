package com.sxit.activity.login;

import org.json.JSONException;

import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.register.EditPassword_Activity;
import com.sxit.activity.register.Register_Activity;
import com.sxit.db.DBHelper;
import com.sxit.entity.UserInfo;
import com.sxit.http.SoapRes;
import com.sxit.md5.MD5Plus;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.Utils;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.message.PushAgent;
import com.umeng.message.proguard.aa.e;

import android.content.Context;
import android.database.CursorJoiner.Result;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import lnpdit.lntv.tradingtime.R;
import lnpdit.lntv.tradingtime.wxapi.WXEntryActivity;

public class Login_Activity extends BaseActivity {
	private Button login_btn, tv_register, login_wechat, login_qq;
	private ImageView img_back;
	private AutoCompleteTextView actv_username, actv_password;
	private TextView tv_password;
	private Object NULL = null;
	private DBHelper dbh;
	Context context;
	private IWXAPI mWeixinAPI;
	

	private PushAgent mPushAgent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_login);
		context = this;
		isParentActivity = false;
		dbh = new DBHelper(this);
		InitView();
//		PushAgent mPushAgent = PushAgent.getInstance(context);
//		mPushAgent.enable();
//		PushAgent.getInstance(context).onAppStart();//统计应用启动数据
//		mPushAgent.getTagManager().add("movie", "sport");
//		public Result add(String... tags)
		mPushAgent = PushAgent.getInstance(this);
		mPushAgent.enable();
	}

	private void loginWithWeixin() {
        if (mWeixinAPI == null) {
            mWeixinAPI = WXAPIFactory.createWXAPI(this, WXEntryActivity.APP_id, false);
        }

        if (!mWeixinAPI.isWXAppInstalled()) {
            //提醒用户没有按照微信
            return;
        }

        mWeixinAPI.registerApp(WXEntryActivity.APP_id);

        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        mWeixinAPI.sendReq(req);
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		/**
		 * 
		 * <string name="register_customer">投资者登录</string>
		 */
		case R.id.img_back:
			finish();
			break;
		case R.id.login_btn:
			if(actv_username.getText().toString().trim().equals("")){
				Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
			}else if(actv_password.getText().toString().trim().equals("")){
				Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
			}else{				
				login_validate(actv_username.getText().toString().trim(), MD5Plus.stringToMD5(actv_password.getText().toString().trim())); // 加密
			}
			
			try {
//				mPushAgent.addAlias("guzhang",getUserInfo().getid());
//				mPushAgent.addExclusiveAlias("guzhang",getUserInfo().getid());
				mPushAgent.addAlias(getUserInfo().getid(),"guzhang");
				mPushAgent.addExclusiveAlias(getUserInfo().getid(),"guzhang");
			} catch (e e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case R.id.tv_register:
			
			intent.setClass(Login_Activity.this, Register_Activity.class);
			startActivity(intent);
			try {
//				mPushAgent.addAlias("guzhang",getUserInfo().getid());
//				mPushAgent.addExclusiveAlias("guzhang",getUserInfo().getid());
				mPushAgent.addAlias(getUserInfo().getid(),"guzhang");
				mPushAgent.addExclusiveAlias(getUserInfo().getid(),"guzhang");
			} catch (e e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case R.id.tv_password:
			intent.setClass(Login_Activity.this, EditPassword_Activity.class);
			startActivity(intent);
			break;
		case R.id.tv_password_adv:
			intent.setClass(Login_Activity.this, EditPassword_Activity.class);
			startActivity(intent);
			break;
		case R.id.login_wechat:
			loginWithWeixin();
//			Utils.showTextToast(this, "微信");
			
			try {
//				mPushAgent.addAlias("guzhang",getUserInfo().getid());
//				mPushAgent.addExclusiveAlias("guzhang",getUserInfo().getid());
				mPushAgent.addAlias(getUserInfo().getid(),"guzhang");
				mPushAgent.addExclusiveAlias(getUserInfo().getid(),"guzhang");
			} catch (e e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case R.id.login_qq:
			Utils.showTextToast(this, "qq");
			break;
		default:
			break;
		}
		super.onClick(v);
	}

	private void InitView() {
		login_btn = (Button) findViewById(R.id.login_btn);
		actv_username = (AutoCompleteTextView) findViewById(R.id.actv_username);
		actv_password = (AutoCompleteTextView) findViewById(R.id.actv_password);
		tv_register = (Button) findViewById(R.id.tv_register);
		tv_password = (TextView) findViewById(R.id.tv_password);
		img_back = (ImageView) findViewById(R.id.img_back);
		login_wechat = (Button) findViewById(R.id.login_wechat);
		login_qq = (Button) findViewById(R.id.login_qq);

		img_back.setOnClickListener(this);
		login_btn.setOnClickListener(this);
		tv_register.setOnClickListener(this);
		tv_password.setOnClickListener(this);
		login_wechat.setOnClickListener(this);
		login_qq.setOnClickListener(this);
	}

	/**
	 * 用户登录
	 * 
	 * @param username
	 * @param password
	 */
	private void login_validate(String username, String password) {
		if (username == null || username.equals("")) {
			Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
		} else if (password == null || password.equals("")) {
			Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
		} else {
			// Object[] property_va = { username, password, Utils.getImei(this)
			// };
			Object[] property_va = { username, password };
			soapService.userInfoLogin(property_va);
		}
	}

	public void onEvent(SoapRes res) {
		if (res.getCode().equals(SOAP_UTILS.METHOD.USERINFOLOGIN)) {
			UserInfo loginUser = (UserInfo) res.getObj();
			if (loginUser != null) {
				if (loginUser.getid().equals("0")) {
					Utils.showTextToast(this, getString(R.string.userlogin_fail));
				} else {
					// {
					// "CheckNum": 0,
					// "CheckSta": 0,
					// "HeadPic": "1.jpg",
					// "Id": 12,
					// "Level": 0,
					// "Mark": 380,
					// "Name": "admin",
					// "Prestige": 0,
					// "Rank": 220,
					// "RealName": "演示"
					// }
					dbh.clearUserInfoData();
					dbh.insUserInfo(loginUser);
					System.out.println("");
					Utils.showTextToast(this, "登录成功");
//					setResult(RESULT_OK);
					finish();
				}
			} else {
				Utils.showTextToast(this, getString(R.string.userlogin_fail));
			}
		}else if(res.getCode().equals(SOAP_UTILS.METHOD.ONEKEYLOGIN)){
			UserInfo loginUser = (UserInfo) res.getObj();
			dbh.clearUserInfoData();
			dbh.insUserInfo(loginUser);
			System.out.println("");
			Utils.showTextToast(this, "登录成功");
//			setResult(RESULT_OK);
			finish();
		}
	}

}
