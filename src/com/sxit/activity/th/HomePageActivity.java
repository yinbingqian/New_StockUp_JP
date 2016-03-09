package com.sxit.activity.th;

import java.util.ArrayList;
import java.util.List;

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
import com.sxit.activity.wap.Wap_Activity;
import com.sxit.db.DBHelper;
import com.sxit.entity.ADInfo;
import com.sxit.entity.HomePageNews;
import com.sxit.http.HttpService;
import com.sxit.http.SoapRes;
import com.sxit.instance.Instance;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.ToastSign;
import com.sxit.utils.advert.ImageCycleView;
import com.sxit.utils.advert.ImageCycleView.ImageCycleViewListener;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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
	
	boolean isComment = true ;
	int live_index = 0 ;
	List<String> live_list = new ArrayList<String>();
	private ImageCycleView mAdView;
	private ArrayList<ADInfo> infos = new ArrayList<ADInfo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homepage);
		context = this;
		checkVersion();
		initView();
		initDB();
		initData();
		setListeners();
		
		
	}
	
	/** 版本检测 */
  private void checkVersion() {
      UmengUpdateAgent.setUpdateOnlyWifi(false);
//      UmengUpdateAgent.setUpdateAutoPopup(false);
      UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {

          @Override
          public void onUpdateReturned(int updateStatus,
                  UpdateResponse updateInfo) {
//              if (updateStatus == 0 && updateInfo != null) {
//                  showUpdateDialog(updateInfo.path, updateInfo.updateLog);
//              }
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

	private void initData() {
		getDBData();

		String[] property_va = new String[] {};
		soapService.getList(property_va, false);
	}
	
	private void sign(){
		if(dbh.queryUserInfo().size() > 0){
			String[] property_nm = { "userid" };
			Object[] property_va = { getUserInfo().getid() };
			new SignTask().execute(property_nm, property_va);
		}else{			
			Intent intent_login = new Intent();
			intent_login.setClass(HomePageActivity.this, Login_Activity.class);
			startActivity(intent_login);
		}
	}
	
	private void checkUserToPersonal(){
		if(dbh.queryUserInfo().size()>0){
			Intent intent_personal = new Intent();
			intent_personal.setClass(HomePageActivity.this, MyZone_Activity.class);
			startActivity(intent_personal);
		}else{			
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
		default:
			break;
		}
	}

	private void getDBData() {
		infos = dbh.queryAdtInfo();
		list = dbh.queryNewsInfo();

		adapter = new HomePageNews_Adapter(this, list);
		listView.setAdapter(adapter);
		
		if(live_list.size()!=0){			
			scrolltext_tv.setText(live_list.get(0));
		}
		
		mAdView = (ImageCycleView) findViewById(R.id.ad_view);
		if(infos.size()!=0){			
			mAdView.setImageResources(infos, mAdCycleViewListener);
		}
		
	}
	
	private ImageCycleViewListener mAdCycleViewListener = new ImageCycleViewListener() {

		@Override
		public void onImageClick(ADInfo info, int position, View imageView) {
//			Toast.makeText(HomePageActivity.this, "content->"+info.getContent(), Toast.LENGTH_SHORT).show();
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
//			ImageLoader.getInstance().displayImage(imageURL, imageView);// 使用ImageLoader对图片进行加装！
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
            Object res_obj = (Object) HttpService.data(SOAP_UTILS.METHOD.USERCHECK, (String[]) params[0],(Object[]) params[1]);
            return res_obj;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            img_sign.setEnabled(true);
            try {
				JSONObject json_obj = new JSONObject(result.toString());
				if(json_obj.get("Result").toString().equals("success")){					
					ToastSign.makeText(context, json_obj.get("Message").toString(), "您的威望+5", Toast.LENGTH_SHORT).show();
				}else if(json_obj.get("Result").toString().equals("error")){
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
				List<HomePageNews> adt_list = new ArrayList<HomePageNews>();
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
					String text = "【" + json_pic.get("CrtimeStr").toString() +"】" + json_pic.get("Content").toString();
					live_list.add(text);
				}

				dbh.clearNewsData();
				dbh.clearAdtData();
				dbh.insNewsInfo(list);
				dbh.insAdtInfo(adt_list);
				getDBData();
				
				if(live_index == live_list.size()-1){	
					live_index = 0;
				}else{
					live_index++;
				}
				scrolltext_tv.setText(live_list.get(live_index));
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
			if(live_index == live_list.size()-1){	
				live_index = 0;
			}else{
				live_index++;
			}
			scrolltext_tv.setText(live_list.get(live_index));
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mAdView.startImageCycle();
	};

	@Override
	protected void onPause() {
		super.onPause();
		mAdView.pushImageCycle();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mAdView.pushImageCycle();
	}

}
