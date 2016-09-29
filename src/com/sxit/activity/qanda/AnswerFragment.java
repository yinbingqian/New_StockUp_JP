package com.sxit.activity.qanda;

import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sxit.activity.base.MyApplication;
import com.sxit.activity.login.Login_Activity;
import com.sxit.activity.qanda.adapter.AnswerList_Adapter;
import com.sxit.customview.LoadingPage;
import com.sxit.db.DBHelper;
import com.sxit.entity.qanda.Answer;
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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import lnpdit.lntv.tradingtime.R;

public class AnswerFragment extends Fragment {

	Context context;
	View view;
	/** soapService **/
	public ISoapService soapService = new SoapService();

	private PullToRefreshListView listView_anwserlist;
	private ListView listView;
	private Button login_bt;
	private int pageIndex = 1;

	private AnswerList_Adapter adapter;
	private List<Answer> list;
	private LoadingPage loading;
	private long exitTime;
	public MyApplication myApplication;
	private static final String TAG = "SU-JPush";

	String userid = "";
	DBHelper dbh;

	public AnswerFragment() {
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

		view = inflater.inflate(R.layout.fragment_answer, container, false);
		initView();
		setListeners();
		
		dbh = new DBHelper(context);
		if(dbh.queryUserInfo().size()>0){
			login_bt.setVisibility(View.GONE);
			listView_anwserlist.setVisibility(View.VISIBLE);
			userid = dbh.queryUserInfo().get(0).getid();

			String[] property_va = new String[] { userid, "10", pageIndex + "" };
			soapService.getAnswerList(property_va, false);
		}else{
			listView_anwserlist.setVisibility(View.GONE);
			login_bt.setVisibility(View.VISIBLE);
			Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
//			Intent intent_login = new Intent();
//			intent_login.setClass(context, Login_Activity.class);
//			startActivityForResult(intent_login, 100);
		}
		
		return view;
	}
	
	

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if(dbh.queryUserInfo().size()>0){
			login_bt.setVisibility(View.GONE);
			listView_anwserlist.setVisibility(View.VISIBLE);
			userid = dbh.queryUserInfo().get(0).getid();
			
			pageIndex = 1;
			String[] property_va = new String[] { userid, "10", pageIndex + "" };
			soapService.getAnswerList(property_va, false);
		}else{
			listView_anwserlist.setVisibility(View.GONE);
			login_bt.setVisibility(View.VISIBLE);
			Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
//			Intent intent_login = new Intent();
//			intent_login.setClass(context, Login_Activity.class);
//			startActivityForResult(intent_login, 100);
		}
	}

	private void initView() {
		listView_anwserlist = (PullToRefreshListView) view
				.findViewById(R.id.listView_answerlist);
		listView = listView_anwserlist.getRefreshableView();
		login_bt = (Button) view.findViewById(R.id.login_bt);
		login_bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent_login = new Intent();
				intent_login.setClass(context, Login_Activity.class);
				startActivityForResult(intent_login, 100);
					
			}
		});
	}

	private void setListeners() {
		listView_anwserlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Intent intent = new Intent();
				// intent.setClass(context, Wap_Activity.class);
				// intent.putExtra("wap_url", list.get(position - 1).getId());
				// intent.putExtra("wap_name", list.get(position -
				// 1).getTitle());
				// startActivity(intent);

				// Intent intent = new Intent();
				// intent.setClass(context, Wap_Activity.class);
				// intent.putExtra("wap_url", SOAP_UTILS.HTTP_NEWSINFO_PATH
				// + list.get(position - 1).getId());
				// intent.putExtra("wap_share", SOAP_UTILS.HTTP_NEWSSHARE_PATH
				// + list.get(position - 1).getId());
				// intent.putExtra("wap_name", list.get(position -
				// 1).getTitle());
				// startActivity(intent);
			}
		});
		listView_anwserlist
				.setOnRefreshListener(new OnRefreshListener<ListView>() {

					@Override
					public void onRefresh(PullToRefreshBase<ListView> refreshView) {
						pageIndex = 1;
						String[] property_va = new String[] { userid, "10",
								pageIndex + "" };
						soapService.getAnswerList(property_va, false);
					}
				});

		// end of list
		listView_anwserlist
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {
						String[] property_va = new String[] { userid, "10",
								++pageIndex + "" };
						soapService.getAnswerList(property_va, true);

					}
				});
	}

	public void onEvent(SoapRes obj) {
		if (obj.getCode().equals(SOAP_UTILS.METHOD.GETANSWERLIST)) {
			if (obj.isPage()) {
				for (Answer bean : (List<Answer>) obj.getObj()) {
					list.add(bean);
				}
				adapter.notifyDataSetChanged();
			} else {
				list = (List<Answer>) obj.getObj();
				if (list != null) {
					if (list.size() != 0) {
						adapter = new AnswerList_Adapter(context, list);
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