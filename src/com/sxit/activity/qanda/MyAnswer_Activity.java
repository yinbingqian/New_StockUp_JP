package com.sxit.activity.qanda;

import java.util.ArrayList;

import com.sxit.activity.qanda.custom.TabSwipPager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import lnpdit.lntv.tradingtime.R;

public class MyAnswer_Activity extends FragmentActivity {

	private LinearLayout myanswerTabSwipPager;
	private TabSwipPager tabSwipPager;

	private ArrayList<Fragment> fragmentsList;
	private String[] tags;
	
	ImageView img_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myanswer);
		initView();
	}

	private void initView() {
		img_back = (ImageView) findViewById(R.id.img_back);
		img_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		initData();

		myanswerTabSwipPager = (LinearLayout) findViewById(R.id.myanswerTabSwipPager);

		tabSwipPager = new TabSwipPager(getApplicationContext(),
				getSupportFragmentManager(), myanswerTabSwipPager);
		if (!tabSwipPager.setFragmentList(fragmentsList, tags)) {
			System.out.println("初始化失败");
			finish();
		}
	}

	private void initData() {
		fragmentsList = new ArrayList<Fragment>();
		fragmentsList.add(new MyAnswerUseFragment());
		fragmentsList.add(new MyAnswerIngFragment());

		tags = new String[] { "已采纳", "进行中" };

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
