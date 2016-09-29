package com.sxit.activity.smarter;

import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sxit.activity.base.MyApplication;
import com.sxit.activity.smarter.adapter.ExpertList_Adapter;
import com.sxit.customview.LoadingPage;
import com.sxit.db.DBHelper;
import com.sxit.entity.smarter.Expert;
import com.sxit.http.ISoapService;
import com.sxit.http.SoapRes;
import com.sxit.http.SoapService;
import com.sxit.utils.EventCache;
import com.sxit.utils.SOAP_UTILS;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import lnpdit.lntv.tradingtime.R;

public class ExpertFragment extends Fragment {

	Context context;
	View expertView;
	/** soapService **/
	public ISoapService soapService = new SoapService();

	private PullToRefreshListView listView_expertlist;
	private ListView expertListView;
	private int pageIndex = 1;

	private DBHelper dbh;
	private ExpertList_Adapter expertAdapter;
	private List<Expert> expertList;
	private LoadingPage loading;
	private long exitTime;
	public MyApplication myApplication;
	private static final String TAG = "SU-JPush";

	public ExpertFragment() {
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

		expertView = inflater.inflate(R.layout.fragment_expert, container, false);
		initDB();
		initView();
		setListeners();
		initData();
		return expertView;
	}

	private void initDB() {
		dbh = new DBHelper(context);
	}

	private void initData() {
		getDBData();

		String[] property_va = new String[] { "10", pageIndex + "" };
		soapService.getExpertTeam(property_va, false);
	}

	private void initView() {
		listView_expertlist = (PullToRefreshListView) expertView
				.findViewById(R.id.listView_expertlist);
		expertListView = listView_expertlist.getRefreshableView();

	}

	private void setListeners() {
		listView_expertlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent();
				intent.setClass(context, ExpertDetails_Activity.class);
				intent.putExtra("id", expertList.get(position - 1).getwebid());
				intent.putExtra("name", expertList.get(position - 1).getrealname());
				intent.putExtra("style", expertList.get(position - 1).getstockstyle());
				intent.putExtra("headpic", expertList.get(position - 1).getheadpic());
				intent.putExtra("resume", expertList.get(position - 1).getresume());
				intent.putExtra("mark", expertList.get(position - 1).getmark());
				intent.putExtra("rewardmark", expertList.get(position - 1).getrewardmark());
				startActivity(intent);
			}
		});
		listView_expertlist
				.setOnRefreshListener(new OnRefreshListener<ListView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String[] property_va = new String[] { "10",
								pageIndex + "" };
						soapService.getExpertTeam(property_va, false);

						pageIndex = 1;

					}
				});

		// end of list
		listView_expertlist
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {
						String[] property_va = new String[] { "10",
								++pageIndex + "" };
						soapService.getExpertTeam(property_va, true);

					}
				});
	}

	private void getDBData() {
		expertList = dbh.queryExpert();

		expertAdapter = new ExpertList_Adapter(context, expertList);
		expertListView.setAdapter(expertAdapter);

	}

	public void onEvent(SoapRes obj) {
		if (obj.getCode().equals(SOAP_UTILS.METHOD.GETEXPERTTEAM)) {
			listView_expertlist.onRefreshComplete();
			if (obj.isPage()) {
				for (Expert bean : (List<Expert>) obj.getObj()) {
					expertList.add(bean);
				}
				expertAdapter.notifyDataSetChanged();
			} else {
				expertList = (List<Expert>) obj.getObj();
				if (expertList.size() != 0) {

					dbh.clearExpert();
					dbh.insExpertList(expertList);
					pageIndex = 1;
				}
				getDBData();
			}
		}
	}
}