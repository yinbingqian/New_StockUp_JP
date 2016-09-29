package com.sxit.activity.th.living;

import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sxit.activity.base.MyApplication;
import com.sxit.activity.login.Login_Activity;
import com.sxit.activity.th.adapter.CourseList_Adapter;
import com.sxit.customview.LoadingPage;
import com.sxit.db.DBHelper;
import com.sxit.entity.living.Course;
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

public class OnlineCourseFragment extends Fragment {

	Context context;
	View view;
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
	
	public OnlineCourseFragment() {
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		System.out.println("onCreateView");

		context = this.getActivity();

		view = inflater.inflate(R.layout.fragment_onlinecourse, container, false);

		initView();
		initData();
		setListeners();
		return view;
	}

	private void initData() {
		String[] property_va = new String[] { Id };
		soapService.getCcLivingInfoSingle(property_va, false);

	}

	private void initView() {
		listView_onlinecourselist = (PullToRefreshListView) view.findViewById(R.id.listView_onlinecourselist);
		listView = listView_onlinecourselist.getRefreshableView();
		SharedPreferences sp = getActivity().getSharedPreferences("live", Context.MODE_PRIVATE); // 私有数据
		// category是新建的表名
		Id = sp.getString("Id", "");
	}

	private void setListeners() {
		listView_onlinecourselist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				DBHelper dbh = new DBHelper(context);
//				if(dbh.queryUserInfo().size()>0){
//					CourseId = list.get(position - 1).getId();
//					 roomid = list.get(position - 1).getLiveRoomId();
//					 liveUserName = list.get(position - 1).getLiveUserName();
//				
//					 SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
//					 password = sharedPreferences.getString("password", "");
//					 
//					url =" http://view.csslcloud.net/api/view/index?roomid="+roomid +"&userid=4E4064DCDE2BD0B8&autoLogin=true&viewername=app," + CourseId +"," + liveUserName +"&viewertoken=" + password;
//				
//					 Intent intent = new Intent();
//					 intent.putExtra("url", url);
//					 intent.setClass(context, OnlineVideoActivity.class);
//					 startActivity(intent);
//
//				}else{
//					Intent intent_login = new Intent();
//					intent_login.setClass(context, Login_Activity.class);
//					startActivity(intent_login);
//				}
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
	
}