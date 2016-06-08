package com.sxit.activity.th.living;

import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sxit.activity.base.MyApplication;
import com.sxit.activity.th.adapter.NowLivingList_Adapter;
import com.sxit.activity.th.adapter.ViewPointList_Adapter;
import com.sxit.activity.wap.Wap_Activity;
import com.sxit.customview.LoadingPage;
import com.sxit.db.DBHelper;
import com.sxit.entity.smarter.GuestDetail;
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
import lnpdit.lntv.tradingtime.R;

public class ViewPointFragment extends Fragment {

	Context context;
	View expertView;
	/** soapService **/
	public ISoapService soapService = new SoapService();

	private PullToRefreshListView listView_viewpointlist;
	private ListView viewpointListView;
	private int pageIndex = 1;

	private DBHelper dbh;
	private ViewPointList_Adapter viewpointAdapter;
	private List<GuestDetail> viewpointList;
	private LoadingPage loading;
	private long exitTime;
	public MyApplication myApplication;
	private static final String TAG = "SU-JPush";

	String Id = "";
	String UserPic = "";
	public ViewPointFragment() {
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
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("onCreateView");

		context = this.getActivity().getApplicationContext();

		expertView = inflater.inflate(R.layout.fragment_viewpoint, container, false);
//		initDB();
		initView();
		setListeners();
		initData();
		return expertView;
	}
//
//	private void initDB() {
//		dbh = new DBHelper(context);
//	}

	private void initData() {
		String[] property_va = new String[] {Id, "10", pageIndex + "" };
		soapService.getGuestDetail(property_va, false);
//		getDBData();

	}

	private void initView() {
		listView_viewpointlist = (PullToRefreshListView) expertView
				.findViewById(R.id.listView_viewpointlist);
		viewpointListView = listView_viewpointlist.getRefreshableView();
		SharedPreferences sp = getActivity().getSharedPreferences("live", Context.MODE_PRIVATE); // 私有数据
		// category是新建的表名
        Id = sp.getString("Id", "");
        UserPic = sp.getString("UserPic", "");
	}

	private void setListeners() {
		listView_viewpointlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println("Click News");
				Intent intent = new Intent();
				intent.putExtra("wap_url", SOAP_UTILS.HTTP_NEWSINFO_PATH + viewpointList.get(position - 2).getid());
				intent.putExtra("wap_share", SOAP_UTILS.HTTP_NEWSSHARE_PATH + viewpointList.get(position - 2).getid());
				intent.putExtra("id", viewpointList.get(position - 2).getid());
				intent.putExtra("wap_name", viewpointList.get(position - 2).gettitle());
				intent.putExtra("NewsComCount", viewpointList.get(position - 2).getnewscomcount());
				intent.putExtra("HeadPic", viewpointList.get(position - 2).getheadpic());
				intent.putExtra("isComment", true);
				intent.setClass(context, Wap_Activity.class);
				startActivity(intent);
			}
		});
		listView_viewpointlist
				.setOnRefreshListener(new OnRefreshListener<ListView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						pageIndex = 1;
						String[] property_va = new String[] { Id,"10",
								pageIndex + "" };
						soapService.getGuestDetail(property_va, false);


					}
				});

		// end of list
		listView_viewpointlist
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {
						String[] property_va = new String[] {Id, "10",
								++pageIndex + "" };
						soapService.getGuestDetail(property_va, true);

					}
				});
	}


	public void onEvent(SoapRes obj) {
		if (obj.getCode().equals(SOAP_UTILS.METHOD.GETGUESTDETAIL)) {
			listView_viewpointlist.onRefreshComplete();
			if (obj.isPage()) {
				for (GuestDetail bean : (List<GuestDetail>) obj.getObj()) {
					viewpointList.add(bean);
				}
				viewpointAdapter.notifyDataSetChanged();
			} else {
				viewpointList = (List<GuestDetail>) obj.getObj();
//			
				viewpointAdapter = new ViewPointList_Adapter(context, viewpointList,UserPic);
				viewpointListView.setAdapter(viewpointAdapter);
			}
		}
	}
}