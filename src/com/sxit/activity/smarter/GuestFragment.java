package com.sxit.activity.smarter;

import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.sxit.activity.base.MyApplication;
import com.sxit.activity.smarter.adapter.GuestList_Adapter;
import com.sxit.activity.th.adapter.HomePageNews_Adapter;
import com.sxit.activity.wap.Wap_Activity;
import com.sxit.customview.LoadingPage;
import com.sxit.customview.LoadingPage.ILoadingDo;
import com.sxit.db.DBHelper;
import com.sxit.entity.HomePageNews;
import com.sxit.entity.smarter.Guest;
import com.sxit.http.ISoapService;
import com.sxit.http.SoapRes;
import com.sxit.http.SoapService;
import com.sxit.utils.EventCache;
import com.sxit.utils.SOAP_UTILS;
import com.umeng.message.PushAgent;

import lnpdit.lntv.tradingtime.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;

public class GuestFragment extends Fragment {

	Context context;
	View view;
	/** soapService **/
	public ISoapService soapService = new SoapService();

	private PullToRefreshListView listView_guestlist;
	private ListView listView;
	private int pageIndex = 1;

	private DBHelper dbh;
	private GuestList_Adapter adapter;
	private List<Guest> list;
	private LoadingPage loading;
	private long exitTime;
	public MyApplication myApplication;
	private static final String TAG = "SU-JPush";

	public GuestFragment() {
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

		view = inflater.inflate(R.layout.fragment_guest, container, false);
		initDB();
		initView();
		setListeners();
		initData();
		return view;
	}

	private void initDB() {
		dbh = new DBHelper(context);
	}

	private void initData() {
		getDBData();

		String[] property_va = new String[] { "10", pageIndex + "" };
		soapService.getGuestTeam(property_va, false);
	}

	private void initView() {
		listView_guestlist = (PullToRefreshListView) view
				.findViewById(R.id.listView_guestlist);
		listView = listView_guestlist.getRefreshableView();

	}

	private void setListeners() {
		listView_guestlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent();
				intent.setClass(context, GuestDetail_Activity.class);
				intent.putExtra("id", list.get(position - 1).getwebid());
				intent.putExtra("name", list.get(position - 1).getrealname());
				intent.putExtra("style", list.get(position - 1).getstockstyle());
				intent.putExtra("headpic", list.get(position - 1).getheadpic());
				intent.putExtra("resume", list.get(position - 1).getresume());
				startActivity(intent);
			}
		});
		listView_guestlist
				.setOnRefreshListener(new OnRefreshListener<ListView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						pageIndex = 1;
						String[] property_va = new String[] { "10",
								pageIndex + "" };
						soapService.getGuestTeam(property_va, false);


					}
				});

		// end of list
		listView_guestlist
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {
						String[] property_va = new String[] { "10",
								++pageIndex + "" };
						soapService.getGuestTeam(property_va, true);

					}
				});
	}

	private void getDBData() {
		list = dbh.queryGuest();

		adapter = new GuestList_Adapter(context, list);
		listView.setAdapter(adapter);

	}

	public void onEvent(SoapRes obj) {
		if (obj.getCode().equals(SOAP_UTILS.METHOD.GETGUESTTEAM)) {
			listView_guestlist.onRefreshComplete();
			if (obj.isPage()) {
				for (Guest bean : (List<Guest>) obj.getObj()) {
					list.add(bean);
				}
				adapter.notifyDataSetChanged();
			} else {
				list = (List<Guest>) obj.getObj();
				if (list.size() != 0) {

					dbh.clearGuest();
					dbh.insGuestList(list);
					pageIndex = 1;
				}
				getDBData();
			}
		}
	}
}