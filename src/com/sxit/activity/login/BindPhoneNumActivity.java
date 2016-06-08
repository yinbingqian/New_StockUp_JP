package com.sxit.activity.login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sxit.activity.base.BaseActivity;
import com.sxit.db.DBHelper;
import com.sxit.entity.UserInfo;
import com.sxit.http.SoapRes;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.Utils;
import com.umeng.message.PushAgent;
import com.umeng.message.tag.TagManager.Result;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import lnpdit.lntv.tradingtime.R;

public class BindPhoneNumActivity extends BaseActivity implements OnClickListener {

	Context context;
	private ImageView img_back;
	private TextView tv_phonenum;
	AutoCompleteTextView actvCode;
	private AlertDialog dialog;
	private Button btn_Bind;
	private TextView tv_SendAg;
	String type = "";
	String userId = "";
	String phoneNum = "";
	UserInfo userInfo;
	String json_attuser;
	String imei;
	private DBHelper dbh;

	private PushAgent mPushAgent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;

		this.setContentView(R.layout.activity_setting_pwdcode);

		dbh = new DBHelper(this);
		mPushAgent = PushAgent.getInstance(this);
		mPushAgent.enable();

		Intent intent = this.getIntent();
		phoneNum = intent.getExtras().get("phoneNum").toString();
		type = intent.getExtras().get("type").toString();
		userInfo = (UserInfo) intent.getExtras().get("loginUser");
		TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		imei = mTelephonyMgr.getDeviceId();
		InitView();

	}

	void InitView() {
		img_back = (ImageView) findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		tv_phonenum = (TextView) findViewById(R.id.tv_phonenum);
		tv_phonenum.setText(phoneNum);
		tv_SendAg = (TextView) findViewById(R.id.tv_SendAg);
		tv_SendAg.setOnClickListener(this);
		actvCode = (AutoCompleteTextView) findViewById(R.id.actv_code);
		btn_Bind = (Button) findViewById(R.id.btn_Bind);
		btn_Bind.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.btn_Bind:
			final String code = actvCode.getText().toString();
			if (code.equals("")) {
				Utils.showTextToast(this, "验证码不能为空！");
				return;
			}
			if (type.equals("reg")) {
				String[] property_nm = {};
				Object[] property_va = { userInfo.getid(), phoneNum, code };
				soapService.bindPhoneNum(property_va);
			} else if (type.equals("pass")) {
				String[] property_nm = {};
				Object[] property_va = { phoneNum, code };
				soapService.codeVerify(property_va);
			}
			// new phoneBindPostTask().execute(property_nm, property_va);

			break;
		case R.id.tv_SendAg:
			Object[] property_va = { phoneNum };
			soapService.getCode(property_va);
			Utils.showTextToast(this, "验证码已经再次发送！");
			break;
		default:
			break;
		}
		super.onClick(v);
	}

	// class phoneBindPostTask extends AsyncTask<Object, Object, Object> {
	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	// }
	//
	// @Override
	// protected Object doInBackground(Object... params) {
	// System.out.println(">>>>>");
	// return res_obj;
	// }
	//
	// @Override
	// protected void onPostExecute(Object result) {
	// super.onPostExecute(result);
	// try {
	// JSONObject json_res = new JSONObject(result.toString());
	// JSONObject json_obj = new
	// JSONObject(json_res.get("QuestionSubmitPostResult").toString());
	// if(json_obj.get("Result").toString().equals("success")){
	// Toast.makeText(context, json_obj.get("Message").toString(),
	// Toast.LENGTH_SHORT).show();
	// finish();
	// }else if(json_obj.get("Result").toString().equals("error")){
	// Toast.makeText(context, json_obj.get("Message").toString(),
	// Toast.LENGTH_SHORT).show();
	// }
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// }

	public void onEvent(SoapRes obj) {
		super.onEvent(obj);
		if (obj.getCode().equals(SOAP_UTILS.METHOD.BINDPHONENUM)) {
			try {
				String object = obj.getObj().toString();
				JSONObject json_res;
				json_res = new JSONObject(object);
				String result = json_res.get("Result").toString();
				String message = json_res.get("Message").toString();
				if (result.equals("success")) {
					// 上传设备码
					loginSendDeviceId();

					dbh.clearUserInfoData();
					dbh.insUserInfo(userInfo);
					Utils.showTextToast(context, "手机号绑定成功！");

					try {
						// 解析attuser
						String userRes = userInfo.getattuser().toString();

						JSONArray attuser_array = new JSONArray(userRes);
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
					} catch (Exception exe) {
						// TODO Auto-generated catch block
						exe.printStackTrace();
					}

					finish();
				} else {
					Utils.showTextToast(context, message);
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (obj.getCode().equals(SOAP_UTILS.METHOD.CODEVERIFY)) {
			try {
				String object = obj.getObj().toString();
				JSONObject json_res;
				json_res = new JSONObject(object);
				String result = json_res.get("Result").toString();
				String message = json_res.get("Message").toString();
				if (result.equals("success")) {

					Intent intent = new Intent();
					intent.putExtra("phoneNum", phoneNum);
					intent.setClass(this, ResetPassword_Activity.class);
					startActivity(intent);
					finish();
				} else {
					Utils.showTextToast(context, message);
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// 上传设备码
	private void loginSendDeviceId() {

		Object[] property_va = { userId, imei };
		soapService.userInfoLoginUpdateImei(property_va);

	}

	/** 监听返回键 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			finish();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

}
