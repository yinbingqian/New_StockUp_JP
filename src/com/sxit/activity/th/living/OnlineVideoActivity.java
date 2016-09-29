package com.sxit.activity.th.living;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import lnpdit.lntv.tradingtime.R;

public class OnlineVideoActivity extends Activity {
	Context context;

	private WebView webview;

	String url = "";

	ImageView img_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_onlinevideo);
		context = this;

		Intent intent = getIntent();
		url = intent.getStringExtra("url");
		initView();
		setUI();
	}

	private void initView() {
		webview = (WebView) findViewById(R.id.onlinevideo_wv);

		img_back = (ImageView) findViewById(R.id.img_back);
		img_back.setClickable(true);
		img_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}

	private void setUI() {

		webview.loadUrl(url);

		// ~~~ 设置数据
		webview.getSettings().setBuiltInZoomControls(true);
		webview.getSettings().setSupportZoom(true);
		webview.getSettings().setUseWideViewPort(true);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.setDownloadListener(new DownloadListener() {
			@Override
			public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
					long contentLength) {
				if (url != null && url.startsWith("http://"))
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
			}
		});

		webview.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});

		webview.loadUrl(url);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		webview.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		webview.onPause();
	}
}
