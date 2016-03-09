package com.sxit.activity.usersetting;

import com.sxit.activity.base.BaseActivity;
import com.sxit.db.DBHelper;
import com.sxit.http.SoapRes;
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
import lnpdit.lntv.tradingtime.R;

/**
 * 昵称
 * 
 * @author huanyu 类名称：UserSetting_Birth_Activity 创建时间:2014-11-3 上午11:54:23
 */
public class UserSetting_Birth_Activity extends BaseActivity {
	private ImageView img_back;
	private TextView actv_name;
	private TextView tv_save;
	private String name;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usersetting_birth);
		context = this;
		intent = getIntent();
		name = intent.getStringExtra("birth");
		initView();
		setListeners();
	}

	private void initView() {
		img_back = (ImageView) findViewById(R.id.img_back);
		actv_name = (TextView) findViewById(R.id.actv_name);
		tv_save = (TextView) findViewById(R.id.tv_save);
		actv_name.setText(name);
		// actv_name.setEnabled(false);
	}

	private void setListeners() {
		img_back.setOnClickListener(this);
		tv_save.setOnClickListener(this);
		actv_name.setOnClickListener(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) { // resultCode为回传的标记，我在B中回传的是RESULT_OK
		case RESULT_OK:
			try {
				if (data != null) {
					actv_name.setText(data.getStringExtra("birth"));
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.actv_name:
			Intent intent = new Intent();
			intent.setClass(context, DatePickerDialogActivity.class);
			intent.putExtra("birth", name);
			startActivityForResult(intent, 1);
			break;
		case R.id.tv_save:
			if (actv_name.getText().toString().trim().equals("")) {
				Utils.showTextToast(UserSetting_Birth_Activity.this, "生日不能为空");
			} else {
				Object[] property_va = { getUserInfo().getid(), actv_name.getText().toString().trim() };
				soapService.userEditor_StockAge(property_va);
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
		if (obj.getCode().equals(SOAP_UTILS.METHOD.USEREDITOR_STOCKAGE)) {
			String result = obj.getObj().toString();
			if(result.equals("success")){
				DBHelper dbh = new DBHelper(context);
				dbh.updateUser(getUserInfo().getid(), "BIRTH", actv_name.getText().toString().trim());
				Utils.showTextToast(context, "修改成功");
				finish();
			}else{
				Utils.showTextToast(context, result);
			}
		}
	}

}
