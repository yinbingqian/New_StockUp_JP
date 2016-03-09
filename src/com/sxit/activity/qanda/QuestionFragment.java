package com.sxit.activity.qanda;

import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sxit.activity.base.MyApplication;
import com.sxit.activity.login.Login_Activity;
import com.sxit.activity.qanda.adapter.QuestionList_Adapter;
import com.sxit.customview.LoadingPage;
import com.sxit.db.DBHelper;
import com.sxit.entity.qanda.Question;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import lnpdit.lntv.tradingtime.R;

public class QuestionFragment extends Fragment {

	Context context;
	View view;
	/** soapService **/
	public ISoapService soapService = new SoapService();

	private PullToRefreshListView listView_questionlist;
	private Button login_bt;
	private RelativeLayout login_layout;
	private ListView listView;
	private int pageIndex = 1;

	private QuestionList_Adapter adapter;
	private List<Question> list;
	private LoadingPage loading;
	private long exitTime;
	public MyApplication myApplication;
	private static final String TAG = "SU-JPush";

	String userid = "";
	DBHelper dbh;
	
	public QuestionFragment() {
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

		view = inflater.inflate(R.layout.fragment_question, container, false);
		initView();
		setListeners();
		
		dbh = new DBHelper(context);
		if(dbh.queryUserInfo().size()>0){
			login_layout.setVisibility(View.GONE);
			listView_questionlist.setVisibility(View.VISIBLE);
			userid = dbh.queryUserInfo().get(0).getid();
			
			String[] property_va = new String[] { userid, "10", pageIndex + "" };
			soapService.getQuestionList(property_va, false);
		}else{
			listView_questionlist.setVisibility(View.GONE);
			login_layout.setVisibility(View.VISIBLE);
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
			login_layout.setVisibility(View.GONE);
			listView_questionlist.setVisibility(View.VISIBLE);
			userid = dbh.queryUserInfo().get(0).getid();
			pageIndex = 1;
			String[] property_va = new String[] { userid, "10", pageIndex + "" };
			soapService.getQuestionList(property_va, false);
		}else{
			listView_questionlist.setVisibility(View.GONE);
			login_layout.setVisibility(View.VISIBLE);
			Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
//			Intent intent_login = new Intent();
//			intent_login.setClass(context, Login_Activity.class);
//			startActivityForResult(intent_login, 100);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
			if(requestCode == 102){
				userid = dbh.queryUserInfo().get(0).getid();

				pageIndex = 1;
				String[] property_va = new String[] { userid, "10", pageIndex + "" };
				soapService.getQuestionList(property_va, false);
			}
	}

	private void initView() {
		listView_questionlist = (PullToRefreshListView) view.findViewById(R.id.listView_questionlist);
		login_layout = (RelativeLayout) view.findViewById(R.id.login_layout);
		login_bt = (Button) view.findViewById(R.id.login_bt);
		listView = listView_questionlist.getRefreshableView();
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
		
		listView_questionlist
				.setOnRefreshListener(new OnRefreshListener<ListView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						if(!userid.equals("")){							
							pageIndex = 1;
							String[] property_va = new String[] { userid, "10",
									pageIndex + "" };
							soapService.getQuestionList(property_va, false);
						}


					}
				});

		// end of list
		listView_questionlist
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {
						if(!userid.equals("")){							
							String[] property_va = new String[] { "20", "10",
									++pageIndex + "" };
							soapService.getQuestionList(property_va, true);
						}

					}
				});
	}

	public void onEvent(SoapRes obj) {
		if (obj.getCode().equals(SOAP_UTILS.METHOD.GETQUESTIONLIST)) {
			if (obj.isPage()) {
				for (Question bean : (List<Question>) obj.getObj()) {
					list.add(bean);
				}
				adapter.notifyDataSetChanged();
			} else {
				list = (List<Question>) obj.getObj();
				if (list != null) {
					if (list.size() != 0) {
						adapter = new QuestionList_Adapter(context, list);
						listView.setAdapter(adapter);
					} else {
						listView.setAdapter(null);
					}
				}
			}
			listView_questionlist.onRefreshComplete();
		}
	}

}