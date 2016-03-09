package com.sxit.activity.analystteaminfo;

import lnpdit.lntv.tradingtime.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.chatmsg.Chat_Activity;
import com.sxit.entity.Adviser;
import com.sxit.instance.Instance;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.Utils;

/**
 * 顾问团队信息 Acy
 * 
 * @author huanyu 类名称：AnalystTeamInfo_Activity 创建时间:2014-10-28 下午7:38:35
 */
public class AnalystTeamInfo_Activity extends BaseActivity {
	private TextView tv_title;// title
	private ImageView img_back;// back
	private Adviser analystInfo;
	private TextView tv_analystName;
	private TextView tv_analystSpecialty;
	private TextView tv_analystResume;
	private TextView tv_analystOrg_and_ptitle;
	private Button bt_ask;
	private ImageView img_analystImg;
	private ToggleButton tb_followed;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_analystteaminfo);
		intent = getIntent();
		analystInfo = (Adviser) intent.getSerializableExtra("analystInfo");
		initView();
		setListeners();
	}

	private void initView() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_analystOrg_and_ptitle = (TextView) findViewById(R.id.tv_analystOrg_and_ptitle);
		tv_analystResume = (TextView) findViewById(R.id.tv_analystResume);
		tv_analystSpecialty = (TextView) findViewById(R.id.tv_analystSpecialty);
		tv_title.setText(analystInfo.getRealname());
		img_back = (ImageView) findViewById(R.id.img_back);
		img_analystImg = (ImageView) findViewById(R.id.img_analystImg);
		tv_analystName = (TextView) findViewById(R.id.tv_analystName);
		tv_analystName.setText(analystInfo.getRealname());
		bt_ask = (Button) findViewById(R.id.bt_ask);
		tv_analystOrg_and_ptitle.setText(analystInfo.getOrgname() + "  " + analystInfo.getPtitle());
		tv_analystSpecialty.setText("「" + analystInfo.getSpecialty() + "」");
		Instance.imageLoader.displayImage(SOAP_UTILS.HEADER_URL + analystInfo.getHeadpic(), img_analystImg,
				Instance.user_options);
		tv_analystResume.setText(analystInfo.getResume());
		tb_followed = (ToggleButton) findViewById(R.id.tb_followed);
	}

	private void setListeners() {
		img_back.setOnClickListener(this);
		bt_ask.setOnClickListener(this);
		tb_followed.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					Utils.showTextToast(AnalystTeamInfo_Activity.this, "已关注");
				} else {
					Utils.showTextToast(AnalystTeamInfo_Activity.this, "已取消关注");
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.bt_ask:
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putSerializable("analystInfo", analystInfo);
			intent.putExtras(bundle);
			intent.setClass(AnalystTeamInfo_Activity.this, Chat_Activity.class);
			startActivity(intent);
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
}
