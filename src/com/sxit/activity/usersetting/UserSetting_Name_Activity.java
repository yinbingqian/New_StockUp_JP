package com.sxit.activity.usersetting;

import com.sxit.activity.base.BaseActivity;
import com.sxit.db.DBHelper;
import com.sxit.http.SoapRes;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.Utils;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import lnpdit.lntv.tradingtime.R;

/**
 * 昵称
 * 
 * @author huanyu 类名称：UserSetting_Name_Activity 创建时间:2014-11-3 上午11:54:23
 */
public class UserSetting_Name_Activity extends BaseActivity {
	private ImageView img_back;
	private AutoCompleteTextView actv_name;
	private TextView tv_save;
	private String name;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usersetting_name);
		context = this;
		intent = getIntent();
		name = intent.getStringExtra("name");
		initView();
		setListeners();
	}

	private void initView() {
		img_back = (ImageView) findViewById(R.id.img_back);
		actv_name = (AutoCompleteTextView) findViewById(R.id.actv_name);
		tv_save = (TextView) findViewById(R.id.tv_save);
		actv_name.setText(name);
		actv_name.setEnabled(true);
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
			if (actv_name.getText().toString().trim().equals("")) {
				Utils.showTextToast(UserSetting_Name_Activity.this, getString(R.string.alter_empty));
			} else {
				Object[] property_va = { getUserInfo().getid(), actv_name.getText().toString().trim() };
				soapService.userEditor_RealName(property_va);
			}
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
		if (obj.getCode().equals(SOAP_UTILS.METHOD.USEREDITOR_REALNAME)) {
			String result = obj.getObj().toString();
			if(result.equals("success")){
				DBHelper dbh = new DBHelper(context);
				dbh.updateUser(getUserInfo().getid(), "REALNAME", actv_name.getText().toString().trim());
				Utils.showTextToast(context, "修改成功");
				finish();
			}else{
				Utils.showTextToast(context, result);
			}
		}
	}

}
