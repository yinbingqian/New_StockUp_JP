package com.sxit.activity.register;

import com.sxit.activity.base.BaseActivity;
import com.sxit.http.SoapRes;
import com.sxit.utils.SOAP_UTILS;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import lnpdit.lntv.tradingtime.R;

/**
 * 注册新用户
 * 
 * @author huanyu 类名称：Register_Activity 创建时间:2014-11-7 下午2:51:16
 */
public class Agree_Activity extends BaseActivity {
	private ImageView img_back;
	private WebView agreewebview;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_agree);
		this.isParentActivity = false;
		initView();
		setListeners();
		setUI();
	}

	@SuppressLint("NewApi")
	private void initView() {
		img_back = (ImageView) findViewById(R.id.img_back);
		agreewebview = (WebView) findViewById(R.id.agreewebview);
		
	    
	}
	public void setUI() {
//		agreewebview.loadDataWithBaseURL( null, "http://backlogin.guzhang.tv/agreement/index.html","text/html", "utf-8", null);
		agreewebview.loadUrl("http://backlogin.guzhang.tv/agreement/index.html");
	}
	private void setListeners() {
		img_back.setOnClickListener(this);


	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finish();
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
		
		
	}
}
