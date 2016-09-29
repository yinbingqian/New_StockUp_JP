package com.sxit.activity.th.living;

import java.util.ArrayList;

import com.sxit.activity.smarter.custom.TabSwipPager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import lnpdit.lntv.tradingtime.R;

public class MoreLiving_Activity extends FragmentActivity {

	private long exitTime;
	private LinearLayout llTabSwipPager;
	private ImageView img_back;
	private TabSwipPager tabSwipPager;

	private ArrayList<Fragment> fragmentsList;
	private String[] tags;
	boolean friendpage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_living);

		Intent intent = this.getIntent();
		friendpage = intent.getBooleanExtra("friendpage", true);

		initView();
	}

	private void initView() {
		initData();

		img_back = (ImageView) findViewById(R.id.img_back);

		if (!friendpage) {
			img_back.setVisibility(8);
		} else {
			img_back.setVisibility(1);
		}

		img_back.setClickable(true);
		img_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		llTabSwipPager = (LinearLayout) findViewById(R.id.llTabSwipPager);

		tabSwipPager = new TabSwipPager(getApplicationContext(), getSupportFragmentManager(), llTabSwipPager);
		if (!tabSwipPager.setFragmentList(fragmentsList, tags)) {
			System.out.println("初始化失败");
			finish();
		}
	}

	private void initData() {
		fragmentsList = new ArrayList<Fragment>();
		fragmentsList.add(new NowLivingFragment());
		fragmentsList.add(new AllLivingFragment());
		fragmentsList.add(new HotLivingFragment());

		tags = new String[] { "正在直播", "全部直播", "热门直播" };

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
