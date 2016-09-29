package com.sxit.activity.qanda;

import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sxit.activity.base.MyApplication;
import com.sxit.activity.qanda.adapter.MyAnswer_Adapter;
import com.sxit.customview.LoadingPage;
import com.sxit.db.DBHelper;
import com.sxit.entity.UserInfo;
import com.sxit.entity.qanda.Answer;
import com.sxit.http.ISoapService;
import com.sxit.http.SoapRes;
import com.sxit.http.SoapService;
import com.sxit.utils.EventCache;
import com.sxit.utils.SOAP_UTILS;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import lnpdit.lntv.tradingtime.R;

public class MyAnswerUseFragment extends Fragment {

	Context context;
	View view;
	/** soapService **/
	public ISoapService soapService = new SoapService();

	private PullToRefreshListView listView_anwserlist;
	private ListView listView;
	private int pageIndex = 1;

	private MyAnswer_Adapter adapter;
	private List<Answer> list;
	private LoadingPage loading;
	private long exitTime;
	public MyApplication myApplication;
	private static final String TAG = "SU-JPush";
	
	UserInfo user;

	public MyAnswerUseFragment() {
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
		context = this.getActivity().getApplicationContext();
		EventCache.commandActivity.unregister(this);
		EventCache.commandActivity.register(this);
		EventCache.errorHttp.unregister(this);
		EventCache.errorHttp.register(this);
		
		DBHelper dbh = new DBHelper(context);
		user = dbh.queryUserInfo().get(0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("onCreateView");

		context = this.getActivity().getApplicationContext();

		view = inflater.inflate(R.layout.fragment_answer, container, false);
		initView();
		setListeners();
		String[] property_va = new String[] { user.getid(), "10", pageIndex + "" };
		soapService.getMyAnswerUse(property_va, false);
		return view;
	}

	private void initView() {
		listView_anwserlist = (PullToRefreshListView) view
				.findViewById(R.id.listView_answerlist);
		listView = listView_anwserlist.getRefreshableView();

	}

	private void setListeners() {
		listView_anwserlist
				.setOnRefreshListener(new OnRefreshListener<ListView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						pageIndex = 1;
						String[] property_va = new String[] { "20", "10",
								pageIndex + "" };
						soapService.getMyAnswerUse(property_va, false);


					}
				});

		// end of list
		listView_anwserlist
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {
						String[] property_va = new String[] { "20", "10",
								++pageIndex + "" };
						soapService.getMyAnswerUse(property_va, true);

					}
				});
	}

	public void onEvent(SoapRes obj) {
		if (obj.getCode().equals(SOAP_UTILS.METHOD.MYANSWERUSEFUL)) {
			if (obj.isPage()) {
				for (Answer bean : (List<Answer>) obj.getObj()) {
					list.add(bean);
				}
				adapter.notifyDataSetChanged();
			} else {
				list = (List<Answer>) obj.getObj();
				if (list != null) {
					if (list.size() != 0) {
						adapter = new MyAnswer_Adapter(context, list);
						listView.setAdapter(adapter);
					} else {
						listView.setAdapter(null);
					}
				}
			}
			listView_anwserlist.onRefreshComplete();
		}
	}

}