package com.sxit.activity.th;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.login.Login_Activity;
import com.sxit.activity.main.Duel_Activity;
import com.sxit.activity.main.Live_Activity;
import com.sxit.activity.main.StandPoint_Activity;
import com.sxit.activity.main.StockNews_Activity;
import com.sxit.activity.th.adapter.HomePageNews_Adapter;
import com.sxit.activity.th.item.MyZone_Activity;
import com.sxit.activity.th.living.CcliveActivity;
import com.sxit.activity.th.living.MoreLiving_Activity;
import com.sxit.activity.th.living.NowLivingDetails_Activity;
import com.sxit.activity.wap.Wap_Activity;
import com.sxit.db.DBHelper;
import com.sxit.entity.ADInfo;
import com.sxit.entity.HomePageNews;
import com.sxit.entity.living.HomePageLiving;
import com.sxit.http.HttpService;
import com.sxit.http.SoapRes;
import com.sxit.instance.Instance;
import com.sxit.service.LocalService;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.ToastSign;
import com.sxit.utils.Utils;
import com.sxit.utils.XCRoundImageView;
import com.sxit.utils.advert.ImageCycleView;
import com.sxit.utils.advert.ImageCycleView.ImageCycleViewListener;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import lnpdit.lntv.tradingtime.R;

public class HomePageActivity extends BaseActivity {

	Context context;

	private PullToRefreshListView listView_newslist;
	private ListView listView;
	private int pageIndex = 1;
	ImageView img_sign;
	ImageView img_personal;

	private DBHelper dbh;
	private HomePageNews_Adapter adapter;
	private List<HomePageNews> list;
	View headerView = null;
	Button bt_gushidongtai;
	Button bt_tuwenzhibo;
	Button bt_duokongduijue;
	Button bt_jiabinguandian;
	TextView scrolltext_tv;

	RelativeLayout more_layout;

	// 8个直播间
	TextView list1_name;
	TextView list1_zbj_text;
	TextView list1_intro;
	XCRoundImageView list1_head_img;
	TextView list1_watching_text;
	TextView list1_living_tv;
	TextView list2_name;
	TextView list2_zbj_text;
	TextView list2_intro;
	XCRoundImageView list2_head_img;
	TextView list2_watching_text;
	TextView list2_living_tv;
	TextView list3_name;
	TextView list3_zbj_text;
	TextView list3_intro;
	XCRoundImageView list3_head_img;
	TextView list3_watching_text;
	TextView list3_living_tv;
	TextView list4_name;
	TextView list4_zbj_text;
	TextView list4_intro;
	XCRoundImageView list4_head_img;
	TextView list4_watching_text;
	TextView list4_living_tv;
	LinearLayout list1_layout;
	LinearLayout list2_layout;
	LinearLayout list3_layout;
	LinearLayout list4_layout;

	List<HomePageLiving> living_list;

	boolean isRefreshing;
	boolean isComment = true;
	String Cclive = "";
	String Id = "";
	String livings1 = "";
	String livings2 = "";
	String livings3 = "";
	String livings4 = "";
	int live_index = 0;
	List<String> live_list = new ArrayList<String>();
	private ImageCycleView mAdView;
	private ArrayList<ADInfo> infos = new ArrayList<ADInfo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homepage);
		context = this;
		isRefreshing = true;

		Intent intent_service = new Intent();
		intent_service.setClass(context, LocalService.class);
		startService(intent_service);

		checkVersion();
		initView();
		initDB();
		initData();
		setListeners();

	}

	/** 版本检测 */
	private void checkVersion() {
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		// UmengUpdateAgent.setUpdateAutoPopup(false);
		UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {

			@Override
			public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
				// if (updateStatus == 0 && updateInfo != null) {
				// showUpdateDialog(updateInfo.path, updateInfo.updateLog);
				// }
				// case 0: // has update
				// case 1: // has no update
				// case 2: // none wifi

				// case 3: // time out
			}
		});

		UmengUpdateAgent.update(this);
	}

	private void initDB() {
		dbh = new DBHelper(this);
	}
	// private void getSharePreferenceData() {
	//
	// SharedPreferences sp = getSharedPreferences("homepagelive",
	// Context.MODE_PRIVATE); // 私有数据
	// // category是新建的表名
	// sz_str= sp.getString("sz_str", "");
	// sz_two_str= sp.getString("sz_two_str", "");
	// sz_three_str= sp.getString("sz_three_str", "");
	// sz_four_str= sp.getString("sz_four_str", "");
	// cy_str= sp.getString("cy_str", "");
	// cy_two_str= sp.getString("cy_two_str", "");
	// cy_three_str= sp.getString("cy_three_str", "");
	// cy_four_str= sp.getString("cy_four_str", "");
	// shz_str= sp.getString("shz_str", "");
	// shz_two_str= sp.getString("shz_two_str", "");
	// shz_three_str= sp.getString("shz_three_str", "");
	// shz_four_str= sp.getString("shz_four_str", "");
	// }

	private void initData() {
		// getSharePreferenceData();
		getDBData();

		String[] property_va = new String[] {};
		soapService.getList(property_va, false);

		// Thread thread = new Thread(new stockDataRunnable());
		// thread.start();
	}

	private void sign() {
		if (dbh.queryUserInfo().size() > 0) {
			String[] property_nm = { "userid" };
			Object[] property_va = { getUserInfo().getid() };
			new SignTask().execute(property_nm, property_va);
		} else {
			Intent intent_login = new Intent();
			intent_login.setClass(HomePageActivity.this, Login_Activity.class);
			startActivity(intent_login);
		}
	}

	private void checkUserToPersonal() {
		if (dbh.queryUserInfo().size() > 0) {
			Intent intent_personal = new Intent();
			intent_personal.setClass(HomePageActivity.this, MyZone_Activity.class);
			startActivity(intent_personal);
		} else {
			Intent intent_login = new Intent();
			intent_login.setClass(HomePageActivity.this, Login_Activity.class);
			startActivity(intent_login);
		}
	}

	private void initView() {
		headerView = getLayoutInflater().inflate(R.layout.view_homepage_head, null);
		bt_gushidongtai = (Button) headerView.findViewById(R.id.bt_gushidongtai);
		bt_tuwenzhibo = (Button) headerView.findViewById(R.id.bt_tuwenzhibo);
		bt_duokongduijue = (Button) headerView.findViewById(R.id.bt_duokongduijue);
		bt_jiabinguandian = (Button) headerView.findViewById(R.id.bt_jiabinguandian);
		scrolltext_tv = (TextView) headerView.findViewById(R.id.scrolltext_tv);
		// sz_tv = (TextView) headerView.findViewById(R.id.sz_tv);
		// sz_two_tv = (TextView) headerView.findViewById(R.id.sz_two_tv);
		// sz_three_tv = (TextView) headerView.findViewById(R.id.sz_three_tv);
		// sz_four_tv = (TextView) headerView.findViewById(R.id.sz_four_tv);
		// cy_tv = (TextView) headerView.findViewById(R.id.cy_tv);
		// cy_two_tv = (TextView) headerView.findViewById(R.id.cy_two_tv);
		// cy_three_tv = (TextView) headerView.findViewById(R.id.cy_three_tv);
		// cy_four_tv = (TextView) headerView.findViewById(R.id.cy_four_tv);
		// shz_tv = (TextView) headerView.findViewById(R.id.shz_tv);
		// shz_two_tv = (TextView) headerView.findViewById(R.id.shz_two_tv);
		// shz_three_tv = (TextView) headerView.findViewById(R.id.shz_three_tv);
		// shz_four_tv = (TextView) headerView.findViewById(R.id.shz_four_tv);

		//
		more_layout = (RelativeLayout) headerView.findViewById(R.id.more_layout);
		more_layout.setClickable(true);
		more_layout.setOnClickListener(this);
		scrolltext_tv.setMovementMethod(new ScrollingMovementMethod());
		scrolltext_tv.setClickable(true);
		scrolltext_tv.setOnClickListener(this);
		bt_gushidongtai.setOnClickListener(this);
		bt_tuwenzhibo.setOnClickListener(this);
		bt_duokongduijue.setOnClickListener(this);
		bt_jiabinguandian.setOnClickListener(this);

		img_sign = (ImageView) findViewById(R.id.img_sign);
		img_personal = (ImageView) findViewById(R.id.img_personal);
		listView_newslist = (PullToRefreshListView) findViewById(R.id.listView_newslist);
		listView = listView_newslist.getRefreshableView();
		listView.addHeaderView(headerView);

		// 新加4个直播间
		list1_name = (TextView) headerView.findViewById(R.id.expert_name1);
		list1_zbj_text = (TextView) headerView.findViewById(R.id.zbj_text1);
		list1_intro = (TextView) headerView.findViewById(R.id.expert_content1);
		list1_head_img = (XCRoundImageView) headerView.findViewById(R.id.expert_headpic1);
		list1_watching_text = (TextView) headerView.findViewById(R.id.watching_text1);
		list1_living_tv = (TextView) headerView.findViewById(R.id.living_tv1);

		list2_name = (TextView) headerView.findViewById(R.id.expert_name2);
		list2_zbj_text = (TextView) headerView.findViewById(R.id.zbj_text2);
		list2_intro = (TextView) headerView.findViewById(R.id.expert_content2);
		list2_head_img = (XCRoundImageView) headerView.findViewById(R.id.expert_headpic2);
		list2_watching_text = (TextView) headerView.findViewById(R.id.watching_text2);
		list2_living_tv = (TextView) headerView.findViewById(R.id.living_tv2);

		list3_name = (TextView) headerView.findViewById(R.id.expert_name3);
		list3_zbj_text = (TextView) headerView.findViewById(R.id.zbj_text3);
		list3_intro = (TextView) headerView.findViewById(R.id.expert_content3);
		list3_head_img = (XCRoundImageView) headerView.findViewById(R.id.expert_headpic3);
		list3_watching_text = (TextView) headerView.findViewById(R.id.watching_text3);
		list3_living_tv = (TextView) headerView.findViewById(R.id.living_tv3);

		list4_name = (TextView) headerView.findViewById(R.id.expert_name4);
		list4_zbj_text = (TextView) headerView.findViewById(R.id.zbj_text4);
		list4_intro = (TextView) headerView.findViewById(R.id.expert_content4);
		list4_head_img = (XCRoundImageView) headerView.findViewById(R.id.expert_headpic4);
		list4_watching_text = (TextView) headerView.findViewById(R.id.watching_text4);
		list4_living_tv = (TextView) headerView.findViewById(R.id.living_tv4);

		list1_layout = (LinearLayout) headerView.findViewById(R.id.list1_layout);
		list2_layout = (LinearLayout) headerView.findViewById(R.id.list2_layout);
		list3_layout = (LinearLayout) headerView.findViewById(R.id.list3_layout);
		list4_layout = (LinearLayout) headerView.findViewById(R.id.list4_layout);

		list1_layout.setClickable(true);
		list1_layout.setOnClickListener(this);
		list2_layout.setClickable(true);
		list2_layout.setOnClickListener(this);
		list3_layout.setClickable(true);
		list3_layout.setOnClickListener(this);
		list4_layout.setClickable(true);
		list4_layout.setOnClickListener(this);
	}

	private void setListeners() {
		img_sign.setOnClickListener(this);
		img_personal.setOnClickListener(this);
		listView_newslist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				System.out.println("Click News");
				Intent intent = new Intent();
				intent.putExtra("wap_url", SOAP_UTILS.HTTP_NEWSINFO_PATH + list.get(position - 2).getId());
				intent.putExtra("wap_share", SOAP_UTILS.HTTP_NEWSSHARE_PATH + list.get(position - 2).getId());
				intent.putExtra("id", list.get(position - 2).getId());
				intent.putExtra("wap_name", list.get(position - 2).getTitle());
				intent.putExtra("NewsComCount", list.get(position - 2).getNewsComCount());
				intent.putExtra("HeadPic", list.get(position - 2).getHeadPic());
				intent.putExtra("isComment", true);
				intent.setClass(context, Wap_Activity.class);
				startActivity(intent);
			}
		});
		listView_newslist.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				pageIndex = 1;
				String[] property_va = new String[] {};
				soapService.getList(property_va, false);

			}
		});
		// end of list
		listView_newslist.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				String[] property_va = new String[] { "10", ++pageIndex + "" };
				soapService.getListPage(property_va, true);

			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.img_sign:
			sign();
			break;
		case R.id.img_personal:
			checkUserToPersonal();
			break;
		case R.id.scrolltext_tv:
			Intent intent_scroll = new Intent();
			intent_scroll.setClass(HomePageActivity.this, Live_Activity.class);
			startActivity(intent_scroll);
			break;
		case R.id.bt_gushidongtai:
			Intent intent_stocknews = new Intent();
			intent.putExtra("isComment", true);
			intent_stocknews.setClass(HomePageActivity.this, StockNews_Activity.class);
			startActivity(intent_stocknews);
			break;
		case R.id.bt_tuwenzhibo:
			Intent intent_live = new Intent();
			intent_live.setClass(HomePageActivity.this, Live_Activity.class);
			startActivity(intent_live);
			break;
		case R.id.bt_duokongduijue:
			Intent intent_duel = new Intent();
			intent_duel.setClass(HomePageActivity.this, Duel_Activity.class);
			startActivity(intent_duel);
			break;
		case R.id.bt_jiabinguandian:
			Intent intent_standpoint = new Intent();
			intent.putExtra("isComment", true);
			intent_standpoint.setClass(HomePageActivity.this, StandPoint_Activity.class);
			startActivity(intent_standpoint);
			break;
		case R.id.more_layout:
			Intent intent_more = new Intent();
			intent.putExtra("friendpage", true);
			intent_more.setClass(HomePageActivity.this, MoreLiving_Activity.class);
			startActivity(intent_more);
			break;
		case R.id.list1_layout:
			Cclive = living_list.get(0).getCclive();
			Id = living_list.get(0).getLiveUserId();
			SharedPreferences sp = getSharedPreferences("live", Context.MODE_PRIVATE); // 私有数据
																						// category是新建的表名
			Editor editor = sp.edit();// 获取编辑器
			editor.putString("Cclive", Cclive);
			editor.putString("Id", Id);
			editor.commit();
			if (livings1.equals("1")) {
				Intent intent_list1 = new Intent();
				intent_list1.putExtra("LiveUserName", living_list.get(0).getLiveUserName());
				intent_list1.setClass(HomePageActivity.this, NowLivingDetails_Activity.class);
				startActivity(intent_list1);
			} else {
				if (Cclive.equals("1")) {
					Intent intent = new Intent();
					intent.setClass(context, CcliveActivity.class);
					startActivity(intent);
				} else {
					Toast.makeText(context, "直播未开启", Toast.LENGTH_SHORT).show();
				}
			}

			break;
		case R.id.list2_layout:
			Cclive = living_list.get(1).getCclive();
			Id = living_list.get(1).getLiveUserId();
			SharedPreferences sp2 = getSharedPreferences("live", Context.MODE_PRIVATE); // 私有数据
																						// category是新建的表名
			Editor editor2 = sp2.edit();// 获取编辑器
			editor2.putString("Cclive", Cclive);
			editor2.putString("Id", Id);
			editor2.commit();
			if (livings2.equals("1")) {
				Intent intent_list2 = new Intent();
				intent_list2.putExtra("LiveUserName", living_list.get(1).getLiveUserName());
				intent_list2.setClass(HomePageActivity.this, NowLivingDetails_Activity.class);
				startActivity(intent_list2);
			} else {
				if (Cclive.equals("1")) {
					Intent intent = new Intent();
					intent.setClass(context, CcliveActivity.class);
					startActivity(intent);
				} else {
					Toast.makeText(context, "直播未开启", Toast.LENGTH_SHORT).show();
				}
			}
			break;
		case R.id.list3_layout:
			Cclive = living_list.get(2).getCclive();
			Id = living_list.get(2).getLiveUserId();
			SharedPreferences sp3 = getSharedPreferences("live", Context.MODE_PRIVATE); // 私有数据
																						// category是新建的表名
			Editor editor3 = sp3.edit();// 获取编辑器
			editor3.putString("Cclive", Cclive);
			editor3.putString("Id", Id);
			editor3.commit();
			if (livings3.equals("1")) {
				Intent intent_list3 = new Intent();
				intent_list3.putExtra("LiveUserName", living_list.get(2).getLiveUserName());
				intent_list3.setClass(HomePageActivity.this, NowLivingDetails_Activity.class);
				startActivity(intent_list3);
			} else {
				if (Cclive.equals("1")) {
					Intent intent = new Intent();
					intent.setClass(context, CcliveActivity.class);
					startActivity(intent);
				} else {
					Toast.makeText(context, "直播未开启", Toast.LENGTH_SHORT).show();
				}
			}
			break;
		case R.id.list4_layout:
			Cclive = living_list.get(3).getCclive();
			Id = living_list.get(3).getLiveUserId();
			SharedPreferences sp4 = getSharedPreferences("live", Context.MODE_PRIVATE); // 私有数据
																						// category是新建的表名
			Editor editor4 = sp4.edit();// 获取编辑器
			editor4.putString("Cclive", Cclive);
			editor4.putString("Id", Id);
			editor4.commit();
			if (livings4.equals("1")) {
				Intent intent_list4 = new Intent();
				intent_list4.putExtra("LiveUserName", living_list.get(3).getLiveUserName());
				intent_list4.setClass(HomePageActivity.this, NowLivingDetails_Activity.class);
				startActivity(intent_list4);
			} else {
				if (Cclive.equals("1")) {
					Intent intent = new Intent();
					intent.setClass(context, CcliveActivity.class);
					startActivity(intent);
				} else {
					Toast.makeText(context, "直播未开启", Toast.LENGTH_SHORT).show();
				}
			}
			break;
		default:
			break;
		}
	}

	private void getDBData() {
		infos = dbh.queryAdtInfo();
		list = dbh.queryNewsInfo();

		adapter = new HomePageNews_Adapter(this, list);
		listView.setAdapter(adapter);

		if (live_list.size() != 0) {
			scrolltext_tv.setText(live_list.get(0));
		} else {
			scrolltext_tv.setText("暂无直播");
		}

		mAdView = (ImageCycleView) findViewById(R.id.ad_view);
		if (infos.size() != 0) {
			mAdView.setImageResources(infos, mAdCycleViewListener);
		}

	}

	private ImageCycleViewListener mAdCycleViewListener = new ImageCycleViewListener() {

		@Override
		public void onImageClick(ADInfo info, int position, View imageView) {
			// Toast.makeText(HomePageActivity.this,
			// "content->"+info.getContent(), Toast.LENGTH_SHORT).show();
			Intent intent = new Intent();
			intent.putExtra("wap_url", SOAP_UTILS.HTTP_ADVERTINFO_PATH + info.getId());
			intent.putExtra("wap_share", SOAP_UTILS.HTTP_ADVERTSHARE_PATH + info.getId());
			intent.putExtra("wap_name", info.getTitle());
			intent.putExtra("isComment", false);
			intent.setClass(context, Wap_Activity.class);
			startActivity(intent);
		}

		@Override
		public void displayImage(String imageURL, ImageView imageView) {
			// ImageLoader.getInstance().displayImage(imageURL, imageView);//
			// 使用ImageLoader对图片进行加装！
			Instance.imageLoader.displayImage(imageURL, imageView, Instance.advert_options);// 使用ImageLoader对图片进行加装！
		}
	};

	class SignTask extends AsyncTask<Object, Object, Object> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			img_sign.setEnabled(false);
		}

		@Override
		protected Object doInBackground(Object... params) {
			System.out.println(">>>>>");
			Object res_obj = (Object) HttpService.data(SOAP_UTILS.METHOD.USERCHECK, (String[]) params[0],
					(Object[]) params[1]);
			return res_obj;
		}

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			img_sign.setEnabled(true);
			try {
				JSONObject json_obj = new JSONObject(result.toString());
				if (json_obj.get("Result").toString().equals("success")) {
					ToastSign.makeText(context, json_obj.get("Message").toString(), "您的威望+5", Toast.LENGTH_SHORT)
							.show();
				} else if (json_obj.get("Result").toString().equals("error")) {
					ToastSign.makeText(context, json_obj.get("Message").toString(), "", Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void onEvent(SoapRes obj) {
		super.onEvent(obj);
		if (obj.getCode().equals(SOAP_UTILS.METHOD.GETLIST)) {
			list = new ArrayList<HomePageNews>();
			listView_newslist.onRefreshComplete();
			try {
				JSONObject json_obj = new JSONObject(obj.getObj().toString());
				JSONArray adt_array = json_obj.getJSONArray("guangGao");
				JSONArray news_array = json_obj.getJSONArray("homeNews");
				JSONArray pic_array = json_obj.getJSONArray("picContent");
				JSONArray living_array = json_obj.getJSONArray("living");
				List<HomePageNews> adt_list = new ArrayList<HomePageNews>();
				living_list = new ArrayList<HomePageLiving>();
				live_list = new ArrayList<String>();

				for (int i = 0; i < news_array.length(); i++) {
					JSONObject json_news = (JSONObject) news_array.get(i);
					HomePageNews hpn = new HomePageNews();
					hpn.setAdminid(json_news.get("Adminid").toString());
					hpn.setColtitle(json_news.get("Coltitle").toString());
					hpn.setCrtime(json_news.get("Crtime").toString());
					hpn.setHeadPic(json_news.get("HeadPic").toString());
					hpn.setId(json_news.get("Id").toString());
					hpn.setNewsComCount(json_news.get("NewsComCount").toString());
					hpn.setOrders(json_news.get("Orders").toString());
					hpn.setOrgName(json_news.get("OrgName").toString());
					hpn.setPicture(json_news.get("Picture").toString());
					hpn.setRealName(json_news.get("RealName").toString());
					hpn.setSource(json_news.get("Source").toString());
					hpn.setThumbnail(json_news.get("Thumbnail").toString());
					hpn.setTitle(json_news.get("Title").toString());

					list.add(hpn);
				}

				for (int i = 0; i < living_array.length(); i++) {
					JSONObject json_living = (JSONObject) living_array.get(i);
					HomePageLiving hpl = new HomePageLiving();
					hpl.setAnswerCount(json_living.get("AnswerCount").toString());
					hpl.setAttention(json_living.get("Attention").toString());
					hpl.setCcEndTime("");
					hpl.setCcStartTime("");
					hpl.setCclive(json_living.get("Cclive").toString());
					hpl.setCcroomid(json_living.get("Ccroomid").toString());
					hpl.setCourseType(json_living.get("CourseType").toString());
					hpl.setCrtimeStr(json_living.get("CrtimeStr").toString());
					hpl.setDealAdvise(json_living.get("DealAdvise").toString());
					hpl.setDealControl(json_living.get("DealControl").toString());
					hpl.setDealOperate(json_living.get("DealOperate").toString());
					hpl.setDescribeCc(json_living.get("DescribeCc").toString());
					hpl.setHotlive(json_living.get("Hotlive").toString());
					hpl.setId(json_living.get("Id").toString());
					hpl.setLaud(json_living.get("Laud").toString());
					hpl.setLiveContent(json_living.get("LiveContent").toString());
					hpl.setLiveCount(json_living.get("LiveCount").toString());
					hpl.setLiveUserId(json_living.get("LiveUserId").toString());
					hpl.setLiveUserName(json_living.get("LiveUserName").toString());
					hpl.setLivings(json_living.get("Livings").toString());
					hpl.setNameCc(json_living.get("NameCc").toString());
					hpl.setToplive(json_living.get("Toplive").toString());
					hpl.setUserHeadpic(json_living.get("UserHeadpic").toString());
					hpl.setUserResume(json_living.get("UserResume").toString());

					living_list.add(hpl);
				}

				for (int i = 0; i < adt_array.length(); i++) {
					JSONObject json_adt = (JSONObject) adt_array.get(i);
					HomePageNews hpn = new HomePageNews();
					hpn.setId(json_adt.get("Id").toString());
					hpn.setOrders(json_adt.get("Orders").toString());
					hpn.setPicture(json_adt.get("Thumbnail").toString());
					hpn.setTitle(json_adt.get("Title").toString());

					adt_list.add(hpn);
				}

				for (int i = 0; i < pic_array.length(); i++) {
					JSONObject json_pic = (JSONObject) pic_array.get(i);
					String text = "【" + json_pic.get("CrtimeStr").toString() + "】" + json_pic.get("Content").toString();
					live_list.add(text);
				}

				dbh.clearNewsData();
				dbh.clearAdtData();
				dbh.insNewsInfo(list);
				dbh.insAdtInfo(adt_list);
				getDBData();

				if (live_index == live_list.size() - 1) {
					live_index = 0;
				} else {
					live_index++;
				}
				scrolltext_tv.setText(live_list.get(live_index));

				if (living_list.size() > 0) {
					list1_name.setText(living_list.get(0).getLiveUserName());
					list1_intro.setText(living_list.get(0).getUserResume());
					Instance.imageLoader.displayImage(SOAP_UTILS.HTTP_HEAD_PATH + living_list.get(0).getUserHeadpic(),
							list1_head_img, Instance.user_s_options);

					// list1_watching_text.setText(living_list.get(0).getLiveCount()
					// + "人正在看");
					Cclive = living_list.get(0).getCclive();
					if (Cclive.equals("1")) {

						list1_watching_text.setText("Cc直播");
					} else {

						list1_watching_text.setText("");
					}

					livings1 = living_list.get(0).getLivings();
					if (livings1.equals("1")) {
						list1_living_tv.setVisibility(1);
					} else if (livings1.equals("0")) {
						list1_living_tv.setVisibility(8);
					}
				} else {
					list1_layout.setVisibility(8);
				}

				if (living_list.size() > 1) {
					list2_name.setText(living_list.get(1).getLiveUserName());
					list2_intro.setText(living_list.get(1).getUserResume());
					Instance.imageLoader.displayImage(SOAP_UTILS.HTTP_HEAD_PATH + living_list.get(1).getUserHeadpic(),
							list2_head_img, Instance.user_s_options);
					// list2_watching_text.setText(living_list.get(1).getLiveCount()
					// + "人正在看");
					Cclive = living_list.get(1).getCclive();
					if (Cclive.equals("1")) {

						list2_watching_text.setText("Cc直播");
					} else {

						list2_watching_text.setText("");
					}

					livings2 = living_list.get(1).getLivings();
					if (livings2.equals("1")) {
						list2_living_tv.setVisibility(1);
					} else {
						list2_living_tv.setVisibility(8);
					}
				} else {
					list2_layout.setVisibility(8);
				}

				if (living_list.size() > 2) {
					list3_name.setText(living_list.get(2).getLiveUserName());
					list3_intro.setText(living_list.get(2).getUserResume());
					Instance.imageLoader.displayImage(SOAP_UTILS.HTTP_HEAD_PATH + living_list.get(2).getUserHeadpic(),
							list3_head_img, Instance.user_s_options);
					// list3_watching_text.setText(living_list.get(2).getLiveCount()
					// + "人正在看");
					Cclive = living_list.get(2).getCclive();
					if (Cclive.equals("1")) {

						list3_watching_text.setText("Cc直播");
					} else {

						list3_watching_text.setText("");
					}

					livings3 = living_list.get(2).getLivings();
					if (livings3.equals("1")) {
						list3_living_tv.setVisibility(1);
					} else {
						list3_living_tv.setVisibility(8);
					}
				} else {
					list3_layout.setVisibility(8);
				}

				if (living_list.size() > 3) {
					list4_name.setText(living_list.get(3).getLiveUserName());
					list4_intro.setText(living_list.get(3).getUserResume());
					Instance.imageLoader.displayImage(SOAP_UTILS.HTTP_HEAD_PATH + living_list.get(3).getUserHeadpic(),
							list4_head_img, Instance.user_s_options);
					// list4_watching_text.setText(living_list.get(3).getLiveCount()
					// + "人正在看");
					Cclive = living_list.get(3).getCclive();
					if (Cclive.equals("1")) {

						list4_watching_text.setText("Cc直播");
					} else {

						list4_watching_text.setText("");
					}

					livings4 = living_list.get(3).getLivings();
					if (livings4.equals("1")) {
						list4_living_tv.setVisibility(1);
					} else {
						list4_living_tv.setVisibility(8);
					}
				} else {
					list4_layout.setVisibility(8);
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (obj.getCode().equals(SOAP_UTILS.METHOD.GETLISTPAGE)) {
			for (HomePageNews bean : (List<HomePageNews>) obj.getObj()) {
				list.add(bean);
			}
			adapter.notifyDataSetChanged();
		} else if (obj.getCode().equals(SOAP_UTILS.ACTION.LIVECIRCLE)) {
			if (live_index == live_list.size() - 1) {
				live_index = 0;
			} else {
				live_index++;
			}
			scrolltext_tv.setText(live_list.get(live_index));
		}
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		isRefreshing = true;
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		isRefreshing = false;
	}

	@Override
	protected void onResume() {
		super.onResume();
		mAdView.startImageCycle();
		isRefreshing = true;

		pageIndex = 1;
		String[] property_va = new String[] {};
		soapService.getList(property_va, false);
	};

	@Override
	protected void onPause() {
		super.onPause();
		mAdView.pushImageCycle();
		isRefreshing = false;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mAdView.pushImageCycle();
		isRefreshing = false;
		LocalService.ischeckingimei = false;
	}
	//
	// @SuppressLint("ResourceAsColor")
	// class GetToken extends AsyncTask<Object, Object, Object> {
	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	// }
	//
	// @Override
	// protected Object doInBackground(Object... params) {
	// System.out.println(">>>>>");
	// String url = "http://hq.sinajs.cn/list=s_sh000001,s_sz399001,s_sz399006";
	// HttpGet httpGet = new HttpGet(url);
	// HttpClient httpClient = new DefaultHttpClient();
	// // 发送请求
	// String result = "";
	// try {
	// HttpResponse response = httpClient.execute(httpGet);
	// result = EntityUtils.toString(response.getEntity(), "UTF_8");
	// return result;
	// } catch (Exception e) {
	// e.printStackTrace();
	// return null;
	// }
	//
	//
	// }
	//
	// @Override
	// protected void onPostExecute(Object result) {
	// super.onPostExecute(result);
	// try {
	//
	// String str = result.toString();
	// String[] array = str.split(";");
	// String string1 = array[0];
	// String string2 = array[1];
	// String string3 = array[2];
	// String newStr1 = string2.substring(24);
	// String newStr2 = string3.substring(24);
	// String newStr3 = string1.substring(23);
	//
	// String newarray1[] = newStr1.split(",");
	// String newarray2[] = newStr2.split(",");
	// String newarray3[] = newStr3.split(",");
	//
	// String str01 = newarray1[0];
	// String str02 = newarray1[1];
	// String str03 = newarray1[2];
	// String str04 = newarray1[3];
	// String str11 = newarray2[0];
	// String str12 = newarray2[1];
	// String str13 = newarray2[2];
	// String str14 = newarray2[3];
	// String str21 = newarray3[0];
	// String str22 = newarray3[1];
	// String str23 = newarray3[2];
	// String str24 = newarray3[3];
	//
	//
	// Resources resources =(Resources)getBaseContext().getResources();
	// ColorStateList colorred =
	// (ColorStateList)resources.getColorStateList(R.color.red);
	// ColorStateList colorgreen =
	// (ColorStateList)resources.getColorStateList(R.color.green);
	//
	// sz_tv.setText(str01);
	// String[] b1 = str02.split("\\.");
	// String str02a = b1[0];
	// String str02b = b1[1];
	// sz_two_tv.setText(str02a + "." + str02b.substring(0,2));
	// if(str03.contains("-")){
	// sz_two_tv.setTextColor(colorgreen);
	// sz_three_tv.setTextColor(colorgreen);
	// sz_four_tv.setTextColor(colorgreen);
	// }else{
	// sz_two_tv.setTextColor(colorred);
	// sz_three_tv.setTextColor(colorred);
	// sz_four_tv.setTextColor(colorred);
	// }
	//
	// String[] a1 = str03.split("\\.");
	// String str03a = a1[0];
	// String str03b = a1[1];
	// sz_three_tv.setText(str03a + "." + str03b.substring(0,2));
	// sz_four_tv.setText(str04 + "%");
	// cy_tv.setText(str11);
	// String[] b2 = str12.split("\\.");
	// String str12a = b2[0];
	// String str12b = b2[1];
	// cy_two_tv.setText(str12a + "." + str12b.substring(0,2));
	// if(str13.contains("-")){
	// cy_two_tv.setTextColor(colorgreen);
	// cy_three_tv.setTextColor(colorgreen);
	// cy_four_tv.setTextColor(colorgreen);
	// }else{
	// cy_two_tv.setTextColor(colorred);
	// cy_three_tv.setTextColor(colorred);
	// cy_four_tv.setTextColor(colorred);
	// }
	// String[] a2 = str13.split("\\.");
	// String str13a = a2[0];
	// String str13b = a2[1];
	// cy_three_tv.setText(str13a + "." + str13b.substring(0,2));
	// cy_four_tv.setText(str14 + "%");
	// shz_tv.setText(str21);
	// String[] b3 = str22.split("\\.");
	// String str22a = b3[0];
	// String str22b = b3[1];
	// shz_two_tv.setText(str22a + "." + str22b.substring(0,2));
	// if(str23.contains("-")){
	// shz_two_tv.setTextColor(colorgreen);
	// shz_three_tv.setTextColor(colorgreen);
	// shz_four_tv.setTextColor(colorgreen);
	// }else{
	// shz_two_tv.setTextColor(colorred);
	// shz_three_tv.setTextColor(colorred);
	// shz_four_tv.setTextColor(colorred);
	// }
	// String[] a3 = str23.split("\\.");
	// String str23a = a3[0];
	// String str23b = a3[1];
	// shz_three_tv.setText(str23a + "." + str23b.substring(0,2));
	// shz_four_tv.setText(str24 + "%");
	//
	// SharedPreferences sp =
	// getSharedPreferences("homepagelive",Context.MODE_PRIVATE); // 私有数据
	// category是新建的表名
	// Editor editor = sp.edit();// 获取编辑器
	// editor.putString("sz_str", sz_str);
	// editor.putString("sz_two_str", sz_two_str);
	// editor.putString("sz_three_str", sz_three_str);
	// editor.putString("sz_four_str", sz_four_str);
	// editor.putString("cy_str", cy_str);
	// editor.putString("cy_two_str", cy_two_str);
	// editor.putString("cy_three_str", cy_three_str);
	// editor.putString("cy_four_str", cy_four_str);
	// editor.putString("shz_str", shz_str);
	// editor.putString("shz_two_str", shz_two_str);
	// editor.putString("shz_three_str", shz_three_str);
	// editor.putString("shz_four_str", shz_four_str);
	// editor.commit();
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// Utils.showTextToast(context, "token获取失败");
	// finish();
	// }
	// }
	// }
	//
	// class stockDataRunnable implements Runnable {
	//
	// public void run() {
	// // TODO Auto-generated method stub
	// while (isRefreshing) {
	// try {
	// Thread.sleep(2000);
	//
	// new GetToken().execute();
	//
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// }
	//
	// }

}
