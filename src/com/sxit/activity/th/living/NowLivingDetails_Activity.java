package com.sxit.activity.th.living;


import java.util.ArrayList;

import com.sxit.activity.smarter.custom.TabSwipPager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import lnpdit.lntv.tradingtime.R;

public class NowLivingDetails_Activity extends FragmentActivity {

	private long exitTime;
	private LinearLayout llTabSwipPager;
	private TabSwipPager tabSwipPager;

	ImageView img_back;
	TextView toptitle_tv;
	String Id = "";
	String LiveUserName="";
	private ArrayList<Fragment> fragmentsList;
	private String[] tags;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_livingdetail);
		Intent intent = this.getIntent();
		LiveUserName = intent.getStringExtra("LiveUserName");
		initView();

	}

	private void initView() {
		initData();
		
		toptitle_tv = (TextView) this.findViewById(R.id.toptitle_tv);
		try {
			if(LiveUserName!=null || LiveUserName.equals("")){
				toptitle_tv.setText(LiveUserName +"的直播间");
			}else{
				toptitle_tv.setText("直播间");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		img_back = (ImageView) this.findViewById(R.id.img_back);
		img_back.setClickable(true);
		img_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		llTabSwipPager = (LinearLayout) findViewById(R.id.llTabSwipPager);

		tabSwipPager = new TabSwipPager(getApplicationContext(),
				getSupportFragmentManager(), llTabSwipPager);
		if (!tabSwipPager.setFragmentList(fragmentsList, tags)) {
			System.out.println("初始化失败");
			finish();
		}
	}

	private void initData() {
		fragmentsList = new ArrayList<Fragment>();
		fragmentsList.add(new LiveHallFragment());
		fragmentsList.add(new QAFragment());
		fragmentsList.add(new ViewPointFragment());

		tags = new String[] { "直播大厅", "互动问答","播主观点" };

	}
	
	/** 监听返回键 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
//			if ((System.currentTimeMillis() - exitTime) > 2000) {
//				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
//				exitTime = System.currentTimeMillis();
//			} else {
				finish();
//				System.exit(0);
//			}
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}
}
