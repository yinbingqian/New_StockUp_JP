package com.sxit.activity.th.living;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sxit.activity.base.MyApplication;
import com.sxit.activity.smarter.adapter.GuestList_Adapter;
import com.sxit.activity.th.adapter.AllLivingList_Adapter;
import com.sxit.customview.LoadingPage;
import com.sxit.db.DBHelper;
import com.sxit.entity.living.AllLiving;
import com.sxit.http.ISoapService;
import com.sxit.http.SoapRes;
import com.sxit.http.SoapService;
import com.sxit.utils.EventCache;
import com.sxit.utils.SOAP_UTILS;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import lnpdit.lntv.tradingtime.R;

public class AllLivingFragment extends Fragment {

	Context context;
	View view;
	/** soapService **/
	public ISoapService soapService = new SoapService();

	private PullToRefreshListView listView_alllivinglist;
	private ListView listView;
	private int pageIndex = 1;

	private DBHelper dbh;
	private AllLivingList_Adapter adapter;
	private List<AllLiving> list;
	private LoadingPage loading;
	private long exitTime;
	public MyApplication myApplication;
	private static final String TAG = "SU-JPush";

	String Id = "";
	String UserPic = "";
	String UserId = "";
	public AllLivingFragment() {
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

		view = inflater.inflate(R.layout.fragment_allliving, container, false);
//		initDB();
		initView();
		setListeners();
		initData();
		return view;
	}

//	private void initDB() {
//		dbh = new DBHelper(context);
//	}

	private void initData() {
		dbh = new DBHelper(context);
		if(dbh.queryUserInfo().size()>0){
			UserId = dbh.queryUserInfo().get(0).getid();

			String[] property_va = new String[] {"10", pageIndex + ""  , UserId};
			soapService.getLivingALl(property_va, false);
		}else{

			String[] property_va = new String[] {"10", pageIndex + "","0" };
			soapService.getLivingALl(property_va, false);
		}
		
	}

	private void initView() {
		listView_alllivinglist = (PullToRefreshListView) view
				.findViewById(R.id.listView_alllivinglist);
		listView = listView_alllivinglist.getRefreshableView();
	}

	private void setListeners() {
		listView_alllivinglist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Id = list.get(position - 1).getLiveUserId();
				UserPic = list.get(position - 1).getUserHeadpic();
				 SharedPreferences sp = getActivity().getSharedPreferences("live",Context.MODE_PRIVATE); // 私有数据 category是新建的表名
				    Editor editor = sp.edit();// 获取编辑器
				    editor.putString("Id", Id);
				    editor.putString("UserPic", UserPic);
				    editor.commit();

			if(!list.get(position - 1).getLivings().equals("0")){
				Intent intent = new Intent();
				intent.putExtra("LiveUserName", list.get(position - 1).getLiveUserName());
				intent.setClass(context, NowLivingDetails_Activity.class);
				startActivity(intent);
		   }else if(list.get(position - 1).getLivings().equals("0") && !list.get(position - 1).getCclive().equals("1")){
						Toast.makeText(context, "直播未开启", Toast.LENGTH_SHORT).show();
		   }else if(list.get(position - 1).getLivings().equals("0") && list.get(position - 1).getCclive().equals("1")){
			   Intent intent = new Intent();
				intent.setClass(context, CcliveActivity.class);
				startActivity(intent);
				}
			}
		});
		listView_alllivinglist
				.setOnRefreshListener(new OnRefreshListener<ListView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {

						pageIndex = 1;
						dbh = new DBHelper(context);
						if(dbh.queryUserInfo().size()>0){
							UserId = dbh.queryUserInfo().get(0).getid();
							String[] property_va = new String[] {"10",
									pageIndex + ""  , UserId};
							soapService.getLivingALl(property_va, false);

						}else{
							String[] property_va = new String[] {"10",
									pageIndex + "" ,"0"};
							soapService.getLivingALl(property_va, false);

						}
					}
				});

		// end of list
		listView_alllivinglist
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {
						
						dbh = new DBHelper(context);
						if(dbh.queryUserInfo().size()>0){
							UserId = dbh.queryUserInfo().get(0).getid();
							String[] property_va = new String[] {"10",
									++pageIndex + ""  , UserId};
							soapService.getLivingALl(property_va, true);

						}else{
							String[] property_va = new String[] {"10",
									++pageIndex + "" ,"0"};
							soapService.getLivingALl(property_va, true);

						}
					}
				});
	}

	private void getDBData() {
//		list = dbh.queryAllLiving();

		adapter = new AllLivingList_Adapter(context, list,UserId);
		listView.setAdapter(adapter);

	}

	public void onEvent(SoapRes obj) {
		if (obj.getCode().equals(SOAP_UTILS.METHOD.GETLIVINGALL)) {
			listView_alllivinglist.onRefreshComplete();
			if (obj.isPage()) {
				for (AllLiving bean : (List<AllLiving>) obj.getObj()) {
					list.add(bean);
				}
				adapter.notifyDataSetChanged();
			} else {
				list = (List<AllLiving>) obj.getObj();
//				if (list.size() != 0) {
//
//					dbh.clearAllLiving();
//					dbh.insAllLivingList(list);
//					pageIndex = 1;
//				}
				getDBData();
			}
		}else if(obj.getCode().equals(SOAP_UTILS.METHOD.GETATTENTIONLIVEUSER)){
			try {
				JSONObject	json_obj = new JSONObject(obj.getObj().toString());
			
			if(json_obj.get("Result").toString().equals("sucess")){
				
				Toast.makeText(context, json_obj.get("Message").toString(), Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(context, json_obj.get("Message").toString(), Toast.LENGTH_SHORT).show();
			}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}