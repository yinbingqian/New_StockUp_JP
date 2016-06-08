package com.sxit.activity.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import com.sxit.activity.base.BaseActivity;
import com.sxit.entity.UserInfo;
import com.sxit.http.HttpPostService;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.Utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import lnpdit.lntv.tradingtime.R;

public class AddPhoneNumActivity extends BaseActivity implements OnClickListener {

	Context context;
	private ImageView img_back;
	private TextView tv_save;
	private TextView title;
	AutoCompleteTextView actvPhone;
	private AlertDialog dialog;
	String type = "";
	String userId = "";
	UserInfo userInfo;

	Bundle bundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;

		this.setContentView(R.layout.activity_setting_forgetpwd);

		InitView();

		Intent intent = this.getIntent();
		type = intent.getStringExtra("type");
		if (type.equals("reg")) {
			userInfo = (UserInfo) intent.getExtras().get("loginUser");
			title.setText("请绑定手机号");
		} else if (type.equals("pass")) {
			title.setText("忘记密码");
		}
	}

	void InitView() {
		img_back = (ImageView) findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		tv_save = (TextView) findViewById(R.id.tv_save);
		tv_save.setOnClickListener(this);
		title = (TextView) findViewById(R.id.img_title);
		actvPhone = (AutoCompleteTextView) findViewById(R.id.actv_phonenum);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.tv_save:
			final String phonenum = actvPhone.getText().toString();
			if (phonenum.equals("")) {
				Utils.showTextToast(this, "手机号码不能为空！");
				return;
			}
			// if (!isMobileNO(phonenum)) {
			// Utils.showTextToast(this, "请输入有效的手机号码！");
			// return;
			// }
			if (dialog == null) {
				dialog = new AlertDialog.Builder(this).setTitle("确认手机号码")
						.setMessage("我们将把验证码发送至" + phonenum + "，请注意查收！")
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {

								Object[] property_va = { phonenum };
								soapService.getCode(property_va);
								if (type.equals("reg")) {

									Intent intent = new Intent();
									intent.setClass(context, BindPhoneNumActivity.class);
									intent.putExtra("type", "reg");
									intent.putExtra("phoneNum", phonenum);
									intent.putExtra("loginUser", userInfo);
									startActivity(intent);
								} else if (type.equals("pass")) {

									Intent intent = new Intent();
									intent.setClass(context, BindPhoneNumActivity.class);
									intent.putExtra("type", "pass");
									intent.putExtra("phoneNum", phonenum);
									startActivity(intent);
								}
								AddPhoneNumActivity.this.finish();
							}
						}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub

							}
						}).show();
			}

			break;
		default:
			break;
		}
		super.onClick(v);
	}

	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
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
