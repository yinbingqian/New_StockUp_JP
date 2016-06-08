package com.sxit.activity.register;

import lnpdit.lntv.tradingtime.R;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sxit.activity.agreement.AgreementActivity;
import com.sxit.activity.base.BaseActivity;
import com.sxit.http.SoapRes;
import com.sxit.md5.MD5Plus;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.Utils;

/**
 * 注册新用户
 * 
 * @author huanyu 类名称：Register_Activity 创建时间:2014-11-7 下午2:51:16
 */
public class Register_Activity extends BaseActivity {
	private ImageView img_back;
	private AutoCompleteTextView actv_name, actv_phone, actv_pwd, actv_pwdagain;// 昵称，设置密码，再次输入，输入手机
	private Button bt_submit;// 提交
	private LinearLayout agree_layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_register);
		this.isParentActivity = false;
		initView();
		setListeners();
	}

	@SuppressLint("NewApi")
	private void initView() {
		img_back = (ImageView) findViewById(R.id.img_back);
		actv_name = (AutoCompleteTextView) findViewById(R.id.actv_name);

		actv_name = (AutoCompleteTextView) findViewById(R.id.actv_name);
		actv_phone = (AutoCompleteTextView) findViewById(R.id.actv_phone);
		actv_pwd = (AutoCompleteTextView) findViewById(R.id.actv_pwd);
		actv_pwdagain = (AutoCompleteTextView) findViewById(R.id.actv_pwdagain);
		bt_submit = (Button) findViewById(R.id.bt_submit);
		agree_layout = (LinearLayout) findViewById(R.id.agree_layout);  
		agree_layout.setClickable(true);
	}

	private void setListeners() {
		img_back.setOnClickListener(this);
		bt_submit.setOnClickListener(this);
		agree_layout.setOnClickListener(this);
//		actv_name.setOnFocusChangeListener(new OnFocusChangeListener() {
//
//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//				if (hasFocus) {
//
//				} else {
//					if (actv_name.getText().toString().trim() != null) {
//						String[] property_va = new String[] { actv_name.getText().toString() };
//						soapService.userNameCheck(property_va);
//					}
//				}
//			}
//		});
		

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.agree_layout:
            Intent intent_agree = new Intent();
            intent_agree.setClass(Register_Activity.this, Agree_Activity.class);
            startActivity(intent_agree);
			
			break;
		case R.id.bt_submit:
			// 昵称校验
			if (actv_name.getText().toString().trim().equals("")) {
				Utils.showTextToast(this, getString(R.string.admin_empty));
				break;
			}
			// 手机号校验
			if(!actv_phone.getText().toString().trim().matches(getString(R.string.is_phone))){
				Utils.showTextToast(this, getString(R.string.phone_error));
				break;
			}
			// 密码校验
			if (actv_pwd.getText().toString().trim().equals("")) {
				Utils.showTextToast(this, getString(R.string.password_empty));
				break;
			}
			// 密码二次校验
			if (actv_pwdagain.getText().toString().trim().equals("")) {
				Utils.showTextToast(this, getString(R.string.password_empty));
				break;
			}
			if (!actv_pwd.getText().toString().trim().equals(actv_pwdagain.getText().toString().trim())) {
				Utils.showTextToast(this, getString(R.string.password_error));
				break;
			}
			Object[] property_va = { actv_phone.getText().toString(), MD5Plus.stringToMD5(actv_pwd.getText().toString()), 
					actv_name.getText().toString() };
			soapService.userRegistered(property_va);
			break;
		default:
			break;
		}
		super.onClick(v);
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

	public void onEvent(SoapRes res) {
		if (res.getCode().equals(SOAP_UTILS.METHOD.USERREGISTERED)) {
			if (res.getObj() != null) {
				if (res.getObj().toString().equals("success")) {
					Utils.showTextToast(this, getString(R.string.register_success));
					finish();
				} else {
					Utils.showTextToast(this, res.getObj().toString());
				}
			} else {
				Utils.showTextToast(this, getString(R.string.register_fail));
			}
		}
	}
}
