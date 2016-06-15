package com.sxit.activity.th.living;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sxit.activity.base.MyApplication;
import com.sxit.entity.living.ViewPointVideo;
import com.sxit.http.ISoapService;
import com.sxit.http.SoapRes;
import com.sxit.http.SoapService;
import com.sxit.instance.Instance;
import com.sxit.utils.EventCache;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.XCRoundImageView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import lnpdit.lntv.tradingtime.R;

public class CcliveActivity extends FragmentActivity {

	/** soapService **/
	public ISoapService soapService = new SoapService();

	public MyApplication myApplication;
	Context context;
	TextView starttime_tv;
	TextView endtime_tv;
	TextView live_name;
	TextView live_title;
	TextView live_resume;
	TextView live_comein;
	XCRoundImageView live_headpic;
	
	String Id = "";
	String UserPic = "";
	
	String starttime = "";
	String endtime = "";
	String livetitle = "";
	String liveresume = "";
	String livehost = "";
	String userpic = "";
	
	ImageView img_back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myApplication = MyApplication.getInstance();
		EventCache.commandActivity.unregister(this);
		EventCache.commandActivity.register(this);
		EventCache.errorHttp.unregister(this);
		EventCache.errorHttp.register(this);
		
		setContentView(R.layout.activity_cclive);
		initView();
		initData();
	}

	private void initData() {
		String[] property_va = new String[] { Id };
		soapService.getCcLivingInfoSingle(property_va);
		

	}

	private void initView() {
		img_back = (ImageView) this.findViewById(R.id.img_back);
		img_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		live_headpic = (XCRoundImageView) findViewById(R.id.live_headpic);
		starttime_tv = (TextView) findViewById(R.id.starttime_tv);
		endtime_tv = (TextView) findViewById(R.id.endtime_tv);
		live_name = (TextView) findViewById(R.id.live_name);
		live_title = (TextView) findViewById(R.id.live_title);
		live_resume = (TextView) findViewById(R.id.live_resume);
		live_comein = (TextView) findViewById(R.id.live_comein);
		live_comein.setClickable(true);
		live_comein.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(context, "点击进入直播间", Toast.LENGTH_SHORT).show();
			}
		});
		
		SharedPreferences sp = getSharedPreferences("live", Context.MODE_PRIVATE); // 私有数据
		// live是新建的表名
        Id = sp.getString("Id", "");
        UserPic = sp.getString("UserPic", "");
	}
	public void onEvent(SoapRes obj) {
		if (obj.getCode().equals(SOAP_UTILS.METHOD.GETCCLIVINGINFOSINGLE)) {
			try {
			JSONArray nowliving_array = new JSONArray(obj.getObj().toString());
			
			List<ViewPointVideo> nowliving_list = new ArrayList<ViewPointVideo>();
			for (int i = 0; i < nowliving_array.length(); i++) {
				JSONObject json_nowliving = (JSONObject) nowliving_array
						.get(i);
				ViewPointVideo nowliving = new ViewPointVideo();
				nowliving.setAnswerCount(json_nowliving.get("AnswerCount").toString());
				nowliving.setAttention(json_nowliving.get("Attention").toString());
				endtime = json_nowliving.get("CcEndTime").toString();
				endtime_tv.setText("结束时间：" + endtime);
				nowliving.setCcEndTime(json_nowliving.get("CcEndTime").toString());
				starttime = json_nowliving.get("CcStartTime").toString();
				starttime_tv.setText("开始时间：" + starttime);
				nowliving.setCcStartTime(json_nowliving.get("CcStartTime").toString());
				nowliving.setCclive(json_nowliving.get("Cclive").toString());
				nowliving.setCcroomid(json_nowliving.get("Ccroomid").toString());
				nowliving.setCourseType(json_nowliving.get("CourseType").toString());
				nowliving.setCrtimeStr(json_nowliving.get("CrtimeStr").toString());
				nowliving.setDealAdvise(json_nowliving.get("DealAdvise").toString());
				nowliving.setDealControl(json_nowliving.get("DealControl").toString());
				nowliving.setDealOperate(json_nowliving.get("DealOperate").toString());
				liveresume = json_nowliving.get("DescribeCc").toString();
				live_resume.setText(liveresume);
				nowliving.setDescribeCc(json_nowliving.get("DescribeCc").toString());
				nowliving.setHotlive(json_nowliving.get("Hotlive").toString());
				nowliving.setId(json_nowliving.get("Id").toString());
				nowliving.setLaud(json_nowliving.get("Laud").toString());
				nowliving.setLiveContent(json_nowliving.get("LiveContent").toString());
				nowliving.setLiveCount(json_nowliving.get("LiveCount").toString());
				nowliving.setLiveUserId(json_nowliving.get("LiveUserId").toString());
				nowliving.setLiveUserName(json_nowliving.get("LiveUserName").toString());
				livehost = json_nowliving.get("LiveUserName").toString();
				live_name.setText(livehost);
				nowliving.setLivings(json_nowliving.get("Livings").toString());
				livetitle = json_nowliving.get("NameCc").toString();
				live_title.setText(livetitle);
				nowliving.setNameCc(json_nowliving.get("NameCc").toString());
				nowliving.setToplive(json_nowliving.get("Toplive").toString());
				userpic = SOAP_UTILS.HTTP_HEAD_PATH + json_nowliving.get("UserHeadpic").toString();
				nowliving.setUserHeadpic(SOAP_UTILS.HTTP_HEAD_PATH + json_nowliving.get("UserHeadpic").toString());
				Instance.imageLoader.displayImage(SOAP_UTILS.HTTP_HEAD_PATH + json_nowliving.get("UserHeadpic").toString(),
						live_headpic, Instance.user_s_options);
				nowliving.setUserResume(json_nowliving.get("UserResume").toString());
				
			}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	}
}
