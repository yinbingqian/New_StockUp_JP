package com.sxit.activity.register;

import org.json.JSONArray;
import org.json.JSONException;
import lnpdit.lntv.tradingtime.R;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.register.adapter.Age_Adapter;
import com.sxit.utils.EventCache;
import com.sxit.utils.Utils;

/**
 * 城市
 * 
 * @author huanyu 类名称：Province_Activity 创建时间:2014-11-11 下午1:22:54
 */
public class CityNew_Activity extends BaseActivity {
	private ImageView img_back;// back
	private TextView tv_title;
	private ListView list_province;
	private String[] citys;
	private String city;
	private String province;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_province);
		this.isParentActivity = false;
		EventCache.opAnswerEvent.unregister(this);
		EventCache.opAnswerEvent.register(this);
		intent = getIntent();
		province = intent.getStringExtra("province");
		citys = Utils.getCitys(province, Utils.getProvinces(this));
		initView();
		setListeners();
	}

	private void initView() {
		img_back = (ImageView) findViewById(R.id.img_back);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText(province);
		list_province = (ListView) findViewById(R.id.list_province);
		list_province.setAdapter(new Age_Adapter(this, citys));
	}

	private void setListeners() {
		img_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finish();
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
		EventCache.opAnswerEvent.unregister(this);
		EventCache.commandActivity.post(new String[] { this.getClass().getName(), province, city });
	}

	/**
	 * radio点击回调
	 * 
	 * @param time
	 *            选中时间
	 */
	public void onEvent(String city) {
		this.city = city;
		finish();
	}
}
