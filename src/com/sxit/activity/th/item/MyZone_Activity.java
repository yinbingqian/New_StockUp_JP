package com.sxit.activity.th.item;

import org.json.JSONException;

import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.qanda.MyAnswer_Activity;
import com.sxit.activity.usersetting.UserSetting_Activity;
import com.sxit.db.DBHelper;
import com.sxit.entity.UserInfo;
import com.sxit.http.SoapRes;
import com.sxit.instance.Instance;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.Utils;
import com.sxit.utils.XCRoundImageView;
import com.umeng.message.PushAgent;
import com.umeng.message.proguard.aa.e;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import lnpdit.lntv.tradingtime.R;

/**
 * 我的空间
 * 
 * @author huanyu 类名称：MyZone_Activity 创建时间:2014-10-26 下午8:30:59
 */
public class MyZone_Activity extends BaseActivity {
	private ImageView img_back;// back,编辑资料
	private XCRoundImageView img_loginUser;// 图像
	private TextView tv_rank;
	private TextView tv_name;// name
	private TextView tv_level;//level
	private TextView tv_ps_prestige, tv_ps_mark, tv_ps_checknum;
	private LinearLayout layout_logout;
	private LinearLayout layout_personal;
	private LinearLayout myanswer_layout, myquestion_layout, myarticle_layout;
	
	Context context;
	
	private PushAgent mPushAgent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myzone);
		isParentActivity = false;
		context = this;
		initView();
		setListeners();
		
		Object[] property_va = { getUserInfo().getid() };
		soapService.userInfoById(property_va, getUserInfo().getheadpic());
		
		mPushAgent = PushAgent.getInstance(this);
		mPushAgent.enable();

		Utils.showTextToast(this, getUserInfo().getid());
	}

	private void logout(){
		DBHelper dbh = new DBHelper(this);
		dbh.clearUserInfoData();
		Utils.showTextToast(this, "退出登录成功");
		
		try {
//			mPushAgent.removeAlias("guzhang",getUserInfo().getid());

			mPushAgent.removeAlias(getUserInfo().getid(),"guzhang");
		} catch (e e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finish();
	}
	
	private void initView() {
		// title
		img_back = (ImageView) findViewById(R.id.img_back);
		img_loginUser = (XCRoundImageView) findViewById(R.id.img_loginUser);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_level = (TextView) findViewById(R.id.tv_level);
		tv_rank = (TextView) findViewById(R.id.tv_rank);
		tv_ps_prestige = (TextView) findViewById(R.id.tv_ps_money);
		tv_ps_mark = (TextView) findViewById(R.id.tv_ps_contribute);
		tv_ps_checknum = (TextView) findViewById(R.id.tv_ps_active);
		layout_logout = (LinearLayout) findViewById(R.id.logout_layout);
		myanswer_layout = (LinearLayout) findViewById(R.id.myanswer_layout);
		myquestion_layout = (LinearLayout) findViewById(R.id.myquestion_layout);
		myarticle_layout = (LinearLayout) findViewById(R.id.myarticle_layout);
		layout_personal = (LinearLayout) findViewById(R.id.personal_layout);
		
		layout_logout.setClickable(true);
		layout_personal.setClickable(true);
		myanswer_layout.setClickable(true);
		myarticle_layout.setClickable(true);
		myquestion_layout.setClickable(true);
	}

	public void setUI() {
		UserInfo user = getUserInfo();
		Instance.imageLoader.displayImage(user.getheadpic(), img_loginUser, Instance.user_options);
		tv_name.setText(user.getrealname());
		tv_level.setText(user.getlevel().equals("0")?"普通":"达人");
		tv_rank.setText("第" + user.getrank() + "位");
		
		tv_ps_prestige.setText(user.getprestige());//威望
		tv_ps_mark.setText(user.getmark());//红心
		tv_ps_checknum.setText(user.getchecknum());//签到
	}

	private void setListeners() {
		img_back.setOnClickListener(this);
		layout_logout.setOnClickListener(this);
		layout_personal.setOnClickListener(this);
		myanswer_layout.setOnClickListener(this);
		myarticle_layout.setOnClickListener(this);
		myquestion_layout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.logout_layout:
			logout();
			break;
		case R.id.personal_layout:
			Intent intent = new Intent();
			intent.setClass(MyZone_Activity.this, UserSetting_Activity.class);
			startActivity(intent);
			break;
		case R.id.myanswer_layout:
			Intent intent_answer = new Intent();
			intent_answer.setClass(MyZone_Activity.this, MyAnswer_Activity.class);
			startActivity(intent_answer);
			break;
		case R.id.myarticle_layout:
			Intent intent_article = new Intent();
			intent_article.setClass(MyZone_Activity.this, MyArticel_Activity.class);
			startActivity(intent_article);
			break;
		case R.id.myquestion_layout:
			Intent intent_question = new Intent();
			intent_question.setClass(MyZone_Activity.this, MyQuestion_Activity.class);
			startActivity(intent_question);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onResume() {
		setUI();
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
		if (res.getCode().equals(SOAP_UTILS.METHOD.USERINFOBYID)) {
			UserInfo loginUser = (UserInfo) res.getObj();
			if (loginUser != null) {
				if (loginUser.getid().equals("0")) {
//					Utils.showTextToast(this, getString(R.string.userlogin_fail));
				} else {
//					{
//					    "CheckNum": 0,
//					    "CheckSta": 0,
//					    "HeadPic": "1.jpg",
//					    "Id": 12,
//					    "Level": 0,
//					    "Mark": 380,
//					    "Name": "admin",
//					    "Prestige": 0,
//					    "Rank": 220,
//					    "RealName": "演示"
//					}
					DBHelper dbh = new DBHelper(context);
					dbh.clearUserInfoData();
					dbh.insUserInfo(loginUser);
					setUI();
					System.out.println("");
				}
			} else {
//				Utils.showTextToast(this, getString(R.string.userlogin_fail));
			}
		}
	}
}
