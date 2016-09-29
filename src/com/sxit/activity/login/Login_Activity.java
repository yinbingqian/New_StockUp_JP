package com.sxit.activity.login;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.register.EditPassword_Activity;
import com.sxit.activity.register.Register_Activity;
import com.sxit.activity.wap.Wap_Activity;
import com.sxit.db.DBHelper;
import com.sxit.entity.UserInfo;
import com.sxit.http.SoapRes;
import com.sxit.md5.MD5Plus;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.Utils;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.umeng.message.PushAgent;
import com.umeng.message.proguard.aa.e;
import com.umeng.message.tag.TagManager.Result;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
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
	String imei;
	String userId;
	String json_attuser;

	boolean isRefreshing;

	private PushAgent mPushAgent;
	private Tencent mTencent;
	private BaseUiListener baseuilistener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_login);
		context = this;
		isParentActivity = false;
		dbh = new DBHelper(this);

		baseuilistener = new BaseUiListener();

		// Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
		// 其中APP_ID是分配给第三方应用的appid，类型为String。
		// mTencent = Tencent.createInstance("100424468",
		// this.getApplicationContext());
		// 1.4版本:此处需新增参数，传入应用程序的全局context，可通过activity的getApplicationContext方法获取

		InitView();
		// PushAgent mPushAgent = PushAgent.getInstance(context);
		// mPushAgent.enable();
		// PushAgent.getInstance(context).onAppStart();//统计应用启动数据
		// mPushAgent.getTagManager().add("movie", "sport");
		// public Result add(String... tags)
		mPushAgent = PushAgent.getInstance(this);
		mPushAgent.enable();
		isRefreshing = true;
	}

	// 上传设备码
	private void loginSendDeviceId() {

		Object[] property_va = { userId, imei };
		soapService.userInfoLoginUpdateImei(property_va);

	}

	// //验证是否在不同设备登陆
	// private void loginCheckDeviceId(){
	//
	// Thread thread = new Thread(new stockDataRunnable());
	// thread.start();
	// }
	// 轮巡
	class stockDataRunnable implements Runnable {

		public void run() {
			// TODO Auto-generated method stub
			while (isRefreshing) {
				try {
					Thread.sleep(5000);

					Object[] property_va = { userId, imei };
					soapService.updateImei(property_va);

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	private void loginWithWeixin() {
		if (mWeixinAPI == null) {
			mWeixinAPI = WXAPIFactory.createWXAPI(this, WXEntryActivity.APP_id, false);
		}

		if (!mWeixinAPI.isWXAppInstalled()) {
			// 提醒用户没有按照微信
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
			if (actv_username.getText().toString().trim().equals("")) {
				Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
			} else if (actv_password.getText().toString().trim().equals("")) {
				Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
			} else {
				login_validate(actv_username.getText().toString().trim(),
						MD5Plus.stringToMD5(actv_password.getText().toString().trim())); // 加密
			}
			// 友盟
			try {
				// mPushAgent.addAlias("guzhang",getUserInfo().getid());
				// mPushAgent.addExclusiveAlias("guzhang",getUserInfo().getid());
				mPushAgent.addAlias(getUserInfo().getid(), "guzhang");
				mPushAgent.addExclusiveAlias(getUserInfo().getid(), "guzhang");
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
				// mPushAgent.addAlias("guzhang",getUserInfo().getid());
				// mPushAgent.addExclusiveAlias("guzhang",getUserInfo().getid());
				mPushAgent.addAlias(getUserInfo().getid(), "guzhang");
				mPushAgent.addExclusiveAlias(getUserInfo().getid(), "guzhang");
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
			intent.setClass(context, AddPhoneNumActivity.class);
			intent.putExtra("type", "pass");
			startActivity(intent);
			break;
		case R.id.tv_password_adv:
			intent.setClass(Login_Activity.this, EditPassword_Activity.class);
			startActivity(intent);
			break;
		case R.id.login_wechat:
			loginWithWeixin();
			// Utils.showTextToast(this, "微信");
			break;
		case R.id.login_qq:
			login();
			// Utils.showTextToast(this, "qq");
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

		TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		imei = mTelephonyMgr.getDeviceId();

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
					userId = loginUser.getid();

					// 上传设备码
					loginSendDeviceId();

					// 验证是否在不同设备登陆
					// loginCheckDeviceId();

					dbh.clearUserInfoData();
					dbh.insUserInfo(loginUser);
					System.out.println("");
					Utils.showTextToast(this, "登录成功");

					SharedPreferences sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE); // 私有数据
					// category是新建的表名
					Editor editor = sp.edit();// 获取编辑器
					editor.putString("password", MD5Plus.stringToMD5(actv_password.getText().toString().trim()));
					editor.putString("username", actv_username.getText().toString().trim());
					editor.putString("realname", loginUser.getrealname());
					editor.commit();
					try {
						// 解析attuser
						String result = loginUser.getattuser().toString();

						JSONArray attuser_array = new JSONArray(result);
						for (int i = 0; i < attuser_array.length(); i++) {
							json_attuser = attuser_array.get(i).toString();
							Thread thread = new Thread(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									try {
										Result result1 = mPushAgent.getTagManager().add(json_attuser);
										Log.d("@@@@@@@@@@@@@@@@@@@@@", "@@@@@@@@@@@@@@@@@@@@@@@@@@");
										Log.d("@@@@@@@@@@@@@@@@@@@@@", "@@@@@@@@@@@@@@@@@@@@@@@@@@");
										Log.d("@@@@@@@@@@@@@@@@@@@@@", "@@@@@@@@@@@@@@@@@@@@@@@@@@");
										Log.d("@@@@@@@@@@@@@@@@@@@@@", "@@@@@@@@@@@@@@@@@@@@@@@@@@");
										Log.d("@@@@@@@@@@@@@@@@@@@@@", result1.toString());
										Log.d("@@@@@@@@@@@@@@@@@@@@@", result1.toString());
										Log.d("@@@@@@@@@@@@@@@@@@@@@", result1.toString());
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							});
							thread.start();

						}

						// setResult(RESULT_OK);

						// mPushAgent.addAlias("guzhang",getUserInfo().getid());
						// mPushAgent.addExclusiveAlias("guzhang",getUserInfo().getid());
						mPushAgent.addAlias(getUserInfo().getid(), "guzhang");
						mPushAgent.addExclusiveAlias(getUserInfo().getid(), "guzhang");
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
					finish();
				}
			} else {
				Utils.showTextToast(this, getString(R.string.userlogin_fail));
			}
		} else if (res.getCode().equals(SOAP_UTILS.METHOD.ONEKEYLOGIN)) {

			UserInfo loginUser = (UserInfo) res.getObj();

			int pos = res.getPosition();

			if (pos == 0) {
				userId = loginUser.getid();
				// 上传设备码
				loginSendDeviceId();

				// 验证是否在不同设备登陆
				// loginCheckDeviceId();

				dbh.clearUserInfoData();
				dbh.insUserInfo(loginUser);
				System.out.println("");
				Utils.showTextToast(this, "登录成功");
				// setResult(RESULT_OK);
				SharedPreferences sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE); // 私有数据
				// category是新建的表名
				Editor editor = sp.edit();// 获取编辑器
				editor.putString("password", "1");
				editor.putString("username", loginUser.getname());
				editor.putString("realname", loginUser.getrealname());
				editor.commit();

				try {
					// 解析attuser
					String result = loginUser.getattuser().toString();

					JSONArray attuser_array = new JSONArray(result);
					for (int i = 0; i < attuser_array.length(); i++) {
						json_attuser = attuser_array.get(i).toString();
						Thread thread = new Thread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								try {
									Result result1 = mPushAgent.getTagManager().add(json_attuser);
									Log.d("@@@@@@@@@@@@@@@@@@@@@", "@@@@@@@@@@@@@@@@@@@@@@@@@@");
									Log.d("@@@@@@@@@@@@@@@@@@@@@", "@@@@@@@@@@@@@@@@@@@@@@@@@@");
									Log.d("@@@@@@@@@@@@@@@@@@@@@", "@@@@@@@@@@@@@@@@@@@@@@@@@@");
									Log.d("@@@@@@@@@@@@@@@@@@@@@", "@@@@@@@@@@@@@@@@@@@@@@@@@@");
									Log.d("@@@@@@@@@@@@@@@@@@@@@", result1.toString());
									Log.d("@@@@@@@@@@@@@@@@@@@@@", result1.toString());
									Log.d("@@@@@@@@@@@@@@@@@@@@@", result1.toString());
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						});
						thread.start();

					}
					// mPushAgent.addAlias("guzhang",getUserInfo().getid());
					// mPushAgent.addExclusiveAlias("guzhang",getUserInfo().getid());
					mPushAgent.addAlias(getUserInfo().getid(), "guzhang");
					mPushAgent.addExclusiveAlias(getUserInfo().getid(), "guzhang");
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
				mTencent.logout(context);
				finish();
			} else if (pos == 1) {

				Utils.showTextToast(this, "您需要绑定手机号！");
				Intent intent = new Intent();
				intent.setClass(context, AddPhoneNumActivity.class);
				intent.putExtra("loginUser", loginUser);
				intent.putExtra("type", "reg");
				startActivity(intent);
				this.finish();

			}
		} else if (res.getCode().equals(SOAP_UTILS.METHOD.USERINFOLOGINUPDATEIMEI)) {
			try {
				JSONObject json_obj = new JSONObject(res.getObj().toString());
				if (json_obj.get("Result").toString().equals("success")) {

					Toast.makeText(context, json_obj.get("Message").toString(), Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(context, json_obj.get("Message").toString(), Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	String openid = "";
	String access_token = "";

	public class BaseUiListener implements IUiListener {

		protected void doComplete(JSONObject values) {
		}

		@Override
		public void onError(UiError e) {
			// showResult("onError:", "code:" + e.errorCode + ", msg:"
			// + e.errorMessage + ", detail:" + e.errorDetail);
		}

		@Override
		public void onCancel() {
			// showResult("onCancel", "");
		}

		@Override
		public void onComplete(Object arg0) {
			// TODO Auto-generated method stub

			try {
				JSONObject res = new JSONObject(arg0.toString());
				openid = res.getString("openid");
				access_token = res.getString("access_token");
				new GetUserInfo().execute(access_token, openid);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Utils.showTextToast(context, "获取QQ用户信息失败");
			}

		}
	}

	class GetUserInfo extends AsyncTask<Object, Object, Object> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Object doInBackground(Object... params) {
			System.out.println(">>>>>");
			String access_token = (String) params[0];
			String openid = (String) params[1];

			String url = "https://graph.qq.com/user/get_user_info?access_token=" + access_token + "&oauth_consumer_key="
					+ "1103959364" + "&openid=" + openid;
			HttpGet httpGet = new HttpGet(url);
			HttpClient httpClient = new DefaultHttpClient();
			// 发送请求
			String result = "";
			try {
				HttpResponse response = httpClient.execute(httpGet);
				result = EntityUtils.toString(response.getEntity(), "UTF_8");
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			try {
				JSONObject token_json = new JSONObject(result.toString());
				String nickname = token_json.getString("nickname");
				String headpic = "";

				Object[] property_va = { openid, nickname };
				soapService.oneKeyLogin(property_va, headpic);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Utils.showTextToast(context, "获取QQ用户信息失败");
				mTencent.logout(context);
				finish();
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// mTencent.onActivityResultData(requestCode,resultCode,data,baseuilistener);
		mTencent.onActivityResult(requestCode, resultCode, data);
	}

	public void login() {
		mTencent = Tencent.createInstance("1103959364", this.getApplicationContext());

		if (!mTencent.isSessionValid()) {
			mTencent.login(this, "all", baseuilistener);
		}
	}
}
