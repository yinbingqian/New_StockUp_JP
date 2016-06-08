package com.sxit.activity.login;

import org.json.JSONException;
import org.json.JSONObject;

import com.sxit.activity.base.BaseActivity;
import com.sxit.db.DBHelper;
import com.sxit.http.SoapRes;
import com.sxit.md5.MD5Plus;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import lnpdit.lntv.tradingtime.R;

/**
 * 密码
 * 
 * @author huanyu 类名称：ResetPassword_Activity 创建时间:2014-11-3 上午11:54:35
 */
public class ResetPassword_Activity extends BaseActivity {
	private ImageView img_back;
	private String phoneNum;
	private TextView tv_save;
	private AutoCompleteTextView actv_password_enable ,actv_password_again;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_resetpwd);
		context = this;
		Intent intent = this.getIntent();
		phoneNum = intent.getExtras().get("phoneNum").toString();
		initView();
		setListeners();
	}

	private void initView() {
		img_back = (ImageView) findViewById(R.id.img_back);
		tv_save = (TextView) findViewById(R.id.tv_save);
		actv_password_again = (AutoCompleteTextView) findViewById(R.id.actv_password_again);
		actv_password_enable = (AutoCompleteTextView) findViewById(R.id.actv_password_enable);
		actv_password_enable.setEnabled(true);
		actv_password_again.setEnabled(true);
	}

	private void setListeners() {
		img_back.setOnClickListener(this);
		tv_save.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.tv_save:
			if ( actv_password_enable.getText().toString().trim().equals("")
					|| actv_password_again.getText().toString().trim().equals("")) {
				Utils.showTextToast(ResetPassword_Activity.this, getString(R.string.password_empty));
				break;
			}
			if (!actv_password_enable.getText().toString().trim().equals(actv_password_again.getText().toString().trim())) {
				Utils.showTextToast(ResetPassword_Activity.this, "新密码输入不一致");
				break;
			}
			String newpwd = MD5Plus.stringToMD5(actv_password_enable.getText().toString().trim());
			String[] property_va = new String[] { phoneNum,newpwd };
			soapService.userPasswordFind(property_va);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
		}
		return true;
	}

	public void onEvent(SoapRes obj) {
		if (obj.getCode().equals(SOAP_UTILS.METHOD.USERPASSWORDFIND)) {
	try {
		JSONObject json_obj = new JSONObject(obj.getObj().toString());

			
			if(json_obj.get("Result").toString().equals("success")){
				
				Toast.makeText(context, json_obj.get("Message").toString(), Toast.LENGTH_SHORT).show();
				finish();
			}else{
				Toast.makeText(context, json_obj.get("Message").toString(), Toast.LENGTH_SHORT).show();
			}
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
		}
	}
}
