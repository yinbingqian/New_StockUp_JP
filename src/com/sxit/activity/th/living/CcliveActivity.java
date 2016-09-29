package com.sxit.activity.th.living;

import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sxit.activity.base.MyApplication;
import com.sxit.activity.login.Login_Activity;
import com.sxit.activity.qanda.QandA_Edit_Activity;
import com.sxit.activity.th.adapter.CourseList_Adapter;
import com.sxit.customview.LoadingPage;
import com.sxit.db.DBHelper;
import com.sxit.entity.living.Course;
import com.sxit.http.ISoapService;
import com.sxit.http.SoapRes;
import com.sxit.http.SoapService;
import com.sxit.utils.EventCache;
import com.sxit.utils.SOAP_UTILS;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import lnpdit.lntv.tradingtime.R;

public class CcliveActivity extends FragmentActivity {
	Context context;
	/** soapService **/
	public ISoapService soapService = new SoapService();

	private PullToRefreshListView listView_onlinecourselist;
	private ListView listView;
	private int pageIndex = 1;

	private DBHelper dbh;
	private CourseList_Adapter adapter;
	private List<Course> list;
	private LoadingPage loading;
	private long exitTime;
	public MyApplication myApplication;
	private static final String TAG = "SU-JPush";

	String Id = "";
	String CourseId = "";
	String url= "";
	String roomid = "";
	String liveUserName = "";
	String password = "";

	ImageView img_back;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context=this;
		myApplication = MyApplication.getInstance();
		EventCache.commandActivity.unregister(this);
		EventCache.commandActivity.register(this);
		EventCache.errorHttp.unregister(this);
		EventCache.errorHttp.register(this);
		
		setContentView(R.layout.activity_cclive);

		initView();
		initData();
		setListeners();
	}
	
	private void initData() {
		String[] property_va = new String[] { Id };
		soapService.getCcLivingInfoSingle(property_va, false);

	}

	private void initView() {
		listView_onlinecourselist = (PullToRefreshListView)findViewById(R.id.listView_onlinecourselist);
		listView = listView_onlinecourselist.getRefreshableView();
		SharedPreferences sp = getSharedPreferences("live", Context.MODE_PRIVATE); // 私有数据
		// category是新建的表名
		Id = sp.getString("Id", "");
		img_back = (ImageView) findViewById(R.id.img_back);
		img_back.setClickable(true);
		img_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	}

	private void setListeners() {
		listView_onlinecourselist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

			}
		});
		listView_onlinecourselist.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {

				pageIndex = 1;
				dbh = new DBHelper(context);
				String[] property_va = new String[] { Id };
				soapService.getCcLivingInfoSingle(property_va, false);
			}
		});
		//
		// // end of list
		// listView_onlinecourselist.setOnLastItemVisibleListener(new
		// OnLastItemVisibleListener() {
		//
		// @Override
		// public void onLastItemVisible() {
		//
		// String[] property_va = new String[] { Id };
		// soapService.getCcLivingInfoSingle(property_va, true);
		// }
		// });
	}

	private void getDBData() {
		// list = dbh.queryAllLiving();

		adapter = new CourseList_Adapter(context, list, Id);
		listView.setAdapter(adapter);

	}

	public void onEvent(SoapRes obj) {
		if (obj.getCode().equals(SOAP_UTILS.METHOD.GETCCLIVINGINFOSINGLE)) {
			listView_onlinecourselist.onRefreshComplete();
			try {
				if (obj.isPage()) {
					for (Course bean : (List<Course>) obj.getObj()) {
						list.add(bean);
					}
					adapter.notifyDataSetChanged();
				} else {
					list = (List<Course>) obj.getObj();
					// for (Course bean : (List<Course>) obj.getObj()) {
					// list.add(bean);
					// }
					getDBData();
				}
			} catch (Exception e) {
				String err = e.toString();
			}
		}
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
