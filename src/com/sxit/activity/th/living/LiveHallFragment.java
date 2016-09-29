package com.sxit.activity.th.living;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sxit.activity.base.MyApplication;
import com.sxit.activity.th.adapter.LivingDetailList_Adapter;
import com.sxit.activity.th.adapter.NowLivingList_Adapter;
import com.sxit.customview.LoadingPage;
import com.sxit.db.DBHelper;
import com.sxit.entity.living.LiveHall;
import com.sxit.entity.living.LivingDetail;
import com.sxit.entity.living.NowLiving;
import com.sxit.http.ISoapService;
import com.sxit.http.SoapRes;
import com.sxit.http.SoapService;
import com.sxit.utils.EventCache;
import com.sxit.utils.SOAP_UTILS;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import lnpdit.lntv.tradingtime.R;

public class LiveHallFragment extends Fragment {

	Context context;
	View expertView;
	/** soapService **/
	public ISoapService soapService = new SoapService();

	private PullToRefreshListView listView_livehalllist;
	private ListView livehallListView;
	private int pageIndex = 1;

	View headerView = null;
	TextView control_tv;
	TextView operate_tv;
	TextView date_tv;
	TextView advice_tv;
	String control_str;
	String operate_str;
	String date_str;
	String advice_str;
	String Id = "";
	String nowId = "no";
	boolean isRefreshing;
	private LivingDetailList_Adapter livingDetailAdapter;
	private ArrayList<LiveHall> liveHallList;
//	private List<LiveHall> livingupdate_list;
	private LoadingPage loading;
	private long exitTime;
	public MyApplication myApplication;
	private static final String TAG = "SU-JPush";

	public LiveHallFragment() {
		super();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		System.out.println("onAttach");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myApplication = MyApplication.getInstance();
		EventCache.commandActivity.unregister(this);
		EventCache.commandActivity.register(this);
		EventCache.errorHttp.unregister(this);
		EventCache.errorHttp.register(this);
		isRefreshing = true;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		System.out.println("onCreateView");

		context = this.getActivity().getApplicationContext();

		expertView = inflater.inflate(R.layout.fragment_livehall, container, false);
		initView();
		setListeners();
		initData();
		return expertView;
	}

	private void initData() {
		String[] property_va = new String[] { Id, "10", pageIndex + "" };
		soapService.getLivingDetail(property_va, false);

		// control_tv.setText(control_str);
		// operate_tv.setText(operate_str);
		// date_tv.setText(date_str);
		// advice_tv.setText(advice_str);
		Thread thread = new Thread(new stockDataRunnable());
	     thread.start();
	}

	private void setHeaderUI() {
		control_tv.setText(control_str);
		operate_tv.setText(operate_str);
		date_tv.setText(date_str);
		advice_tv.setText(advice_str);
		
	}

	private void initView() {
		headerView = getActivity().getLayoutInflater().inflate(R.layout.view_livehall_head, null);
		control_tv = (TextView) headerView.findViewById(R.id.control_tv);
		operate_tv = (TextView) headerView.findViewById(R.id.operate_tv);
		date_tv = (TextView) headerView.findViewById(R.id.date_tv);
		advice_tv = (TextView) headerView.findViewById(R.id.advice_tv);

		listView_livehalllist = (PullToRefreshListView) expertView.findViewById(R.id.listView_nowlivinglist);
		livehallListView = listView_livehalllist.getRefreshableView();
		livehallListView.addHeaderView(headerView);
		SharedPreferences sp = getActivity().getSharedPreferences("live", Context.MODE_PRIVATE); // 私有数据
																									// category是新建的表名
		Id = sp.getString("Id", "");
		
	}

	private void setListeners() {
		listView_livehalllist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

			}
		});
		listView_livehalllist.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				pageIndex = 1;
				String[] property_va = new String[] { Id, "10", pageIndex + "" };
				soapService.getLivingDetail(property_va, false);
			}
		});

		// end of list
		listView_livehalllist.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				String[] property_va = new String[] { Id, "10", ++pageIndex + "" };
				soapService.getLivingDetail(property_va, true);

			}
		});
	}

	public void onEvent(SoapRes obj) {
		if (obj.getCode().equals(SOAP_UTILS.METHOD.GETLIVINGDETAIL)) {
			isRefreshing = false;
			listView_livehalllist.onRefreshComplete();
			if (obj.isPage()) {
//				for (LiveHall bean : (ArrayList<LiveHall>) obj.getObj()) {
//					liveHallList.add(bean);
//				}
				try {
					JSONObject json_detail = new JSONObject(obj.getObj().toString());
					JSONArray livingdetail_array = json_detail.getJSONArray("living");
					for (int i = 0; i < livingdetail_array.length(); i++) {
						JSONObject json_nowliving = (JSONObject) livingdetail_array.get(i);
						LivingDetail livingdetail = new LivingDetail();
						livingdetail.setAnswerCount(json_nowliving.get("AnswerCount").toString());
						livingdetail.setAttention(json_nowliving.get("Attention").toString());
						livingdetail.setCcEndTime(json_nowliving.get("CcEndTime").toString());
						livingdetail.setCcStartTime(json_nowliving.get("CcStartTime").toString());
						livingdetail.setCclive(json_nowliving.get("Cclive").toString());
						livingdetail.setCcroomid(json_nowliving.get("Ccroomid").toString());
						livingdetail.setCourseType(json_nowliving.get("CourseType").toString());

						date_str = json_nowliving.get("CrtimeStr").toString();
						livingdetail.setCrtimeStr(date_str);

						advice_str = json_nowliving.get("DealAdvise").toString();
						livingdetail.setDealAdvise(advice_str);
						
						control_str = json_nowliving.get("DealControl").toString();
						livingdetail.setDealControl(control_str);

						operate_str = json_nowliving.get("DealOperate").toString();
						livingdetail.setDealOperate(operate_str);

						livingdetail.setDescribeCc(json_nowliving.get("DescribeCc").toString());
						livingdetail.setHotlive(json_nowliving.get("Hotlive").toString());
						livingdetail.setId(json_nowliving.get("Id").toString());
						livingdetail.setLaud(json_nowliving.get("Laud").toString());
						livingdetail.setLiveContent(json_nowliving.get("LiveContent").toString());
						livingdetail.setLiveCount(json_nowliving.get("LiveCount").toString());
						livingdetail.setLiveUserId(json_nowliving.get("LiveUserId").toString());
						livingdetail.setLiveUserName(json_nowliving.get("LiveUserName").toString());
						livingdetail.setLivings(json_nowliving.get("Livings").toString());
						livingdetail.setNameCc(json_nowliving.get("NameCc").toString());
						livingdetail.setToplive(json_nowliving.get("Toplive").toString());
						livingdetail.setUserHeadpic(json_nowliving.get("UserHeadpic").toString());
						livingdetail.setUserResume(json_nowliving.get("UserResume").toString());
					}
					setHeaderUI();
					JSONArray livehall_array = json_detail.getJSONArray("Imagpic");
					List<LiveHall> livehall_list = new ArrayList<LiveHall>();
					for (int i = 0; i < livehall_array.length(); i++) {
						JSONObject json_nowliving = (JSONObject) livehall_array.get(i);
						LiveHall livehall = new LiveHall();
						livehall.setContent(json_nowliving.get("Content").toString());
						livehall.setCrtime(json_nowliving.get("Crtime").toString());
						livehall.setCrtimeStr(json_nowliving.get("CrtimeStr").toString());
						livehall.setHeadPic(SOAP_UTILS.HTTP_HEAD_PATH + json_nowliving.get("HeadPic").toString());
						livehall.setId(json_nowliving.get("Id").toString());

						JSONArray magpic_array = new JSONArray(json_nowliving.get("Magpic").toString());
						if(magpic_array.length()!=0){
							JSONObject json_qapic = (JSONObject) magpic_array.get(0);
							
							livehall.setPicId(json_qapic.get(
									"Id").toString());
							livehall.setPicMagID(json_qapic.get(
									"MagID").toString());
							livehall.setPicOrders(json_qapic.get(
									"Orders").toString());
							livehall.setPicPic(json_qapic.get(
									"Pic").toString());
							livehall.setPicThumbnail(json_qapic.get(
									"Thumbnail").toString());
							livehall.setRealName(json_qapic.get(
									"RealName").toString());
							livehall.setTitle(json_qapic.get(
									"Title").toString());
							
						}
						
						ArrayList<String> mag_urls = new ArrayList<String>();
						for (int j = 0; j < magpic_array.length(); j++) {
							JSONObject jsonmag = (JSONObject) magpic_array.get(j);
							mag_urls.add(SOAP_UTILS.HTTP_MAG_PATH + jsonmag.get("Pic").toString());
						}

						livehall.setRealName(json_nowliving.get("RealName").toString());
						livehall.setTitle(json_nowliving.get("Title").toString());
						liveHallList.add(livehall);

						// liveHallList = (ArrayList<LiveHall>) livehall_list;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				livingDetailAdapter.notifyDataSetChanged();
				
			} else {
				// liveHallList = (ArrayList<LiveHall>) obj.getObj();
				liveHallList = new ArrayList<LiveHall>();
				try {
					JSONObject json_detail = new JSONObject(obj.getObj().toString());
					JSONArray livingdetail_array = json_detail.getJSONArray("living");
					for (int i = 0; i < livingdetail_array.length(); i++) {
						JSONObject json_nowliving = (JSONObject) livingdetail_array.get(i);
						LivingDetail livingdetail = new LivingDetail();
						livingdetail.setAnswerCount(json_nowliving.get("AnswerCount").toString());

						date_str = json_nowliving.get("CrtimeStr").toString();
						livingdetail.setCrtimeStr(date_str);

						advice_str = json_nowliving.get("DealAdvise").toString();
						livingdetail.setDealAdvise(advice_str);

						control_str = json_nowliving.get("DealControl").toString();
						livingdetail.setDealControl(control_str);

						operate_str = json_nowliving.get("DealOperate").toString();
						livingdetail.setDealOperate(operate_str);

						livingdetail.setHotlive(json_nowliving.get("Hotlive").toString());
						livingdetail.setId(json_nowliving.get("Id").toString());
						livingdetail.setLaud(json_nowliving.get("Laud").toString());
						livingdetail.setLiveContent(json_nowliving.get("LiveContent").toString());
						livingdetail.setLiveCount(json_nowliving.get("LiveCount").toString());
						livingdetail.setLiveUserId(json_nowliving.get("LiveUserId").toString());
						livingdetail.setLiveUserName(json_nowliving.get("LiveUserName").toString());
						livingdetail.setLivings(json_nowliving.get("Livings").toString());

						livingdetail.setDescribeCc(json_nowliving.get("DescribeCc").toString());

						livingdetail.setToplive(json_nowliving.get("Toplive").toString());
						livingdetail.setUserHeadpic(json_nowliving.get("UserHeadpic").toString());
						livingdetail.setUserResume(json_nowliving.get("UserResume").toString());
					}
					setHeaderUI();
					JSONArray livehall_array = json_detail.getJSONArray("Imagpic");
					List<LiveHall> livehall_list = new ArrayList<LiveHall>();
					for (int i = 0; i < livehall_array.length(); i++) {
						JSONObject json_nowliving = (JSONObject) livehall_array.get(i);
						LiveHall livehall = new LiveHall();
						livehall.setContent(json_nowliving.get("Content").toString());
						livehall.setCrtime(json_nowliving.get("Crtime").toString());
						livehall.setCrtimeStr(json_nowliving.get("CrtimeStr").toString());
						livehall.setHeadPic(SOAP_UTILS.HTTP_HEAD_PATH + json_nowliving.get("HeadPic").toString());
						livehall.setId(json_nowliving.get("Id").toString());
						JSONArray magpic_array = new JSONArray(json_nowliving.get("Magpic").toString());
						if(magpic_array.length()!=0){
							JSONObject json_qapic = (JSONObject) magpic_array.get(0);
							
							livehall.setPicId(json_qapic.get(
									"Id").toString());
							livehall.setPicMagID(json_qapic.get(
									"MagID").toString());
							livehall.setPicOrders(json_qapic.get(
									"Orders").toString());
							livehall.setPicPic(json_qapic.get(
									"Pic").toString());
							livehall.setPicThumbnail(json_qapic.get(
									"Thumbnail").toString());
							
						}
						
						livehall.setRealName(json_nowliving.get("RealName").toString());
						livehall.setTitle(json_nowliving.get("Title").toString());
						liveHallList.add(livehall);

						// liveHallList = (ArrayList<LiveHall>) livehall_list;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				livingDetailAdapter = new LivingDetailList_Adapter(context, liveHallList);
				livehallListView.setAdapter(livingDetailAdapter);
			}
		}else if (obj.getCode().equals(SOAP_UTILS.METHOD.GETLIVINGUPDATE)){
				try {
					
//					livingupdate_list = new ArrayList<LiveHall>();
//					livingupdate_list = (ArrayList<LiveHall>) liveHallList;
					nowId = liveHallList.get(0).getId();
					
					JSONArray nowliving_array = new JSONArray(obj.getObj().toString());
					List<LiveHall> nowliving_list = new ArrayList<LiveHall>();
					for (int i = 0; i < nowliving_array.length(); i++) {
						JSONObject json_nowliving = (JSONObject) nowliving_array
								.get(i);
						LiveHall nowliving = new LiveHall();
						nowliving.setContent(json_nowliving.get(
								"Content").toString());
						nowliving.setCrtime(json_nowliving.get(
								"Crtime").toString());
						nowliving.setCrtimeStr(json_nowliving.get("CrtimeStr")
								.toString());
						nowliving.setHeadPic(SOAP_UTILS.HTTP_HEAD_PATH + json_nowliving.get("HeadPic").toString());
						nowliving.setId(json_nowliving.get(
								"Id").toString());
						
						JSONArray qapic_array = new JSONArray(json_nowliving.get("Magpic").toString());
						if(qapic_array.length()!=0){
							JSONObject json_qapic = (JSONObject) qapic_array.get(0);
							
							nowliving.setPicId(json_qapic.get(
									"Id").toString());
							nowliving.setPicMagID(json_qapic.get(
									"MagID").toString());
							nowliving.setPicOrders(json_qapic.get(
									"Orders").toString());
							nowliving.setPicPic(json_qapic.get(
									"Pic").toString());
							nowliving.setPicThumbnail(json_qapic.get(
									"Thumbnail").toString());
						}
						
						ArrayList<String> mag_urls = new ArrayList<String>();
						for (int j = 0; j < qapic_array.length(); j++) {
							JSONObject jsonmag = (JSONObject) qapic_array.get(j);
							mag_urls.add(SOAP_UTILS.HTTP_MAG_PATH + jsonmag.get("Pic").toString());
						}

						
						nowliving.setRealName(json_nowliving.get("RealName")
								.toString());
						nowliving.setTitle(json_nowliving.get("Title")
								.toString());
						nowliving_list.add(nowliving);
						
					}

					liveHallList.addAll(0,nowliving_list);
					nowId = liveHallList.get(0).getId();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				livingDetailAdapter.notifyDataSetChanged();
		}
		isRefreshing = true;
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		isRefreshing = true;
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		isRefreshing = false;
	}

	@Override
	public void onResume() {
		super.onResume();
		isRefreshing = true;
	};

	@Override
	public void onPause() {
		super.onPause();
		isRefreshing = false;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		isRefreshing = false;
	}
	class stockDataRunnable implements Runnable {

		public void run() {
			// TODO Auto-generated method stub
			while (isRefreshing) {
				try {
					Thread.sleep(10000);
					pageIndex = 1; 
					String[] property_va = new String[] { Id, nowId, "100", pageIndex + "" };
					soapService.getLivingUpdate(property_va, false);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
}