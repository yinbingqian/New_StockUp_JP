package com.sxit.activity.wap;

import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.login.Login_Activity;
import com.sxit.customview.FocusedtrueTV;
import com.sxit.customview.ProgressWebView;
import com.sxit.db.DBHelper;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.ShareTool;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import lnpdit.lntv.tradingtime.R;

public class Wap_Activity extends BaseActivity {
	private ProgressWebView webview;
	private FocusedtrueTV tv_title;
	private ImageView img_back;
	private ImageView img_share;
	private Button newscomment_bt;
	private TextView commentcount_tv;
	private RelativeLayout comment_layout;
	private String url;
	private String url_share;
	private String name;
	private String content;
	Context context;
	String id = "";
	String NewsComCount = "";
	String HeadPic = "";
	boolean isComment ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wap_layout);
		context = this;
		this.isParentActivity = false;
		url = this.getIntent().getStringExtra("wap_url");
		url_share = this.getIntent().getStringExtra("wap_share");
		id = this.getIntent().getStringExtra("id");
		NewsComCount = this.getIntent().getStringExtra("NewsComCount");
		HeadPic = this.getIntent().getStringExtra("HeadPic");
		isComment = this.getIntent().getBooleanExtra("isComment", true);
		System.out.println(">>>> loading url " + url);
		// url = "http://www.baidu.com";
		name = this.getIntent().getStringExtra("wap_name");
		content = name;
//		content = this.getIntent().getStringExtra("wap_content");
		// ~~~ 绑定控件
		webview = (ProgressWebView) findViewById(R.id.webview);
		img_back = (ImageView) findViewById(R.id.img_back);
		img_share = (ImageView) findViewById(R.id.more_fun);
		img_back.setOnClickListener(this);
		img_share.setOnClickListener(this);
		tv_title = (FocusedtrueTV) findViewById(R.id.tv_title);
		comment_layout = (RelativeLayout) this.findViewById(R.id.comment_layout);
		if(!isComment){
			comment_layout.setVisibility(8);
		}else{
			comment_layout.setVisibility(1);
		}
		commentcount_tv = (TextView) this.findViewById(R.id.commentcount_tv);
		commentcount_tv.setText("评论: " + NewsComCount);
		commentcount_tv.setClickable(true);
		commentcount_tv.setOnClickListener(this);
		newscomment_bt = (Button) this.findViewById(R.id.newscomment_bt);
		newscomment_bt.setOnClickListener(this);
		
		// ~~~ 设置数据
		tv_title.setText(name);
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

		// webview.setWebViewClient(new WebViewClient() {
		// public boolean shouldOverrideUrlLoading(WebView view, String url) {
		// view.loadUrl(url);
		// return true;
		// }
		// });

		webview.loadUrl(url);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.more_fun:
//			ShareTool.getInstance(this, this).TITLE = name;
//			ShareTool.getInstance(this, this).CONTENT = name;
//			ShareTool.getInstance(this, this).TARGETURL = url;
//			ShareTool.getInstance(this, this).setParser(name, name, url);
//			ShareTool.getInstance(this,this, name, content, url,HeadPic).shareMsg(this);
			ShareTool.getInstance(this,this, name, content, url_share,HeadPic).shareMsg(this);
			break;
		case R.id.newscomment_bt:
			DBHelper dbh = new DBHelper(this);
			if(dbh.queryUserInfo().size()>0){
				Intent intent = new Intent();
				intent.putExtra("NewsId", id);
				intent.setClass(context, NewsAddCommentActivity.class);
				startActivity(intent);
			}else{			
				Intent intent_login = new Intent();
				intent_login.setClass(context, Login_Activity.class);
				startActivity(intent_login);
			}
			break;
		case R.id.commentcount_tv:
			Intent intent_count = new Intent();
			intent_count.putExtra("NewsId", id);
			intent_count.putExtra("NewsComCount", NewsComCount);
			intent_count.setClass(context, WapComment_Activity.class);
			startActivity(intent_count);
			break;
		default:
			break;
		}
		super.onClick(v);
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

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
			webview.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
