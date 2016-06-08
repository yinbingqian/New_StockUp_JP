package com.sxit.activity.th.living;

import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sxit.activity.base.MyApplication;
import com.sxit.activity.login.Login_Activity;
import com.sxit.activity.qanda.QandA_Activity;
import com.sxit.activity.qanda.QandA_Edit_Activity;
import com.sxit.activity.th.adapter.QAList_Adapter;
import com.sxit.customview.LoadingPage;
import com.sxit.db.DBHelper;
import com.sxit.entity.living.QA;
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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import lnpdit.lntv.tradingtime.R;

public class QAFragment extends Fragment {

	Context context;
	View expertView;
	/** soapService **/
	public ISoapService soapService = new SoapService();

	private PullToRefreshListView listView_qalist;
	private ListView QAListView;
	private Button ZoomInButton;
	private int pageIndex = 1;

	private QAList_Adapter QAAdapter;
	private List<QA> QAList;
	private LoadingPage loading;
	private long exitTime;
	public MyApplication myApplication;
	private static final String TAG = "SU-JPush";
	String Id = "";

	public QAFragment() {
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

		expertView = inflater.inflate(R.layout.fragment_qa, container, false);
		initView();
		setListeners();
		initData();
		return expertView;
	}

	private void initData() {
		String[] property_va = new String[] { Id,"10", pageIndex + "" };
		soapService.getInteractQA(property_va, false);

	}

	private void initView() {
		ZoomInButton = (Button) expertView
				.findViewById(R.id.ZoomInButton);
		ZoomInButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DBHelper dbh = new DBHelper(context);
				if(dbh.queryUserInfo().size()>0){
					Intent intent = new Intent();
					intent.setClass(context, QandA_Edit_Activity.class);
					startActivity(intent);
				}else{
					Intent intent_login = new Intent();
					intent_login.setClass(context, Login_Activity.class);
					startActivity(intent_login);
				}
			}
		});
		listView_qalist = (PullToRefreshListView) expertView
				.findViewById(R.id.listView_qalist);
		QAListView = listView_qalist.getRefreshableView();
		SharedPreferences sp = getActivity().getSharedPreferences("live", Context.MODE_PRIVATE); // 私有数据
		// category是新建的表名
        Id = sp.getString("Id", "");
	}

	private void setListeners() {
		listView_qalist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent();
				intent.setClass(context, NowLivingDetails_Activity.class);
//				intent.putExtra("id", expertList.get(position - 1).getwebid());
				startActivity(intent);
			}
		});
		listView_qalist
				.setOnRefreshListener(new OnRefreshListener<ListView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						pageIndex = 1;
						String[] property_va = new String[] { Id,"10",
								pageIndex + "" };
						soapService.getInteractQA(property_va, false);


					}
				});

		// end of list
		listView_qalist
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {
						String[] property_va = new String[] { Id,"10",
								++pageIndex + "" };
						soapService.getInteractQA(property_va, true);

					}
				});
	}

	public void onEvent(SoapRes obj) {
		if (obj.getCode().equals(SOAP_UTILS.METHOD.GETINTERACTQA)) {
			listView_qalist.onRefreshComplete();
			if (obj.isPage()) {
				for (QA bean : (List<QA>) obj.getObj()) {
					QAList.add(bean);
				}
				QAAdapter.notifyDataSetChanged();
			} else {
				QAList = (List<QA>) obj.getObj();
				QAAdapter = new QAList_Adapter(context, QAList);
				QAListView.setAdapter(QAAdapter);
			}
		}
	}
}