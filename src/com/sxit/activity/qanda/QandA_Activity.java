package com.sxit.activity.qanda;

import java.util.ArrayList;

import com.sxit.activity.login.Login_Activity;
import com.sxit.activity.qanda.custom.TabSwipPager;
import com.sxit.db.DBHelper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import lnpdit.lntv.tradingtime.R;

public class QandA_Activity extends FragmentActivity {

	private LinearLayout askTabSwipPager;
	private TabSwipPager tabSwipPager;

	private ArrayList<Fragment> fragmentsList;
	private String[] tags;
	
	ImageView img_send;
	Context context;
	
	private long exitTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qanda);
		context = this;
		initView();
	}

	private void initView() {
		initData();

		askTabSwipPager = (LinearLayout) findViewById(R.id.askTabSwipPager);
		img_send = (ImageView) findViewById(R.id.img_send);
		
		img_send.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DBHelper dbh = new DBHelper(context);
				if(dbh.queryUserInfo().size()>0){
					Intent intent = new Intent();
					intent.setClass(context, QandA_Edit_Activity.class);
					startActivity(intent);
				}else{
					Intent intent_login = new Intent();
					intent_login.setClass(context, Login_Activity.class);
					startActivity(intent_login);
				}
			}
		});

		tabSwipPager = new TabSwipPager(getApplicationContext(),
				getSupportFragmentManager(), askTabSwipPager);
		if (!tabSwipPager.setFragmentList(fragmentsList, tags)) {
			System.out.println("初始化失败");
			finish();
		}
	}

	private void initData() {
		fragmentsList = new ArrayList<Fragment>();
		fragmentsList.add(new QuestionFragment());
		fragmentsList.add(new AnswerFragment());

		tags = new String[] { "我的提问", "我要回答" };

	}
	
	/** 监听返回键 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}
}
