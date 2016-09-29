package com.sxit.activity.th.living;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sxit.activity.base.MyApplication;
import com.sxit.activity.discuss.Discuss_Edit_Activity;
import com.sxit.activity.th.adapter.NowLivingList_Adapter;
import com.sxit.customview.LoadingPage;
import com.sxit.db.DBHelper;
import com.sxit.entity.living.NowLiving;
import com.sxit.http.ISoapService;
import com.sxit.http.SoapRes;
import com.sxit.http.SoapService;
import com.sxit.utils.EventCache;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import lnpdit.lntv.tradingtime.R;

public class NowLivingFragment extends Fragment {

	Context context;
	View expertView;
	/** soapService **/
	public ISoapService soapService = new SoapService();

	private PullToRefreshListView listView_nowlivinglist;
	private ListView nowListView;
	private int pageIndex = 1;

	private DBHelper dbh;
	private NowLivingList_Adapter nowLivingAdapter;
	private List<NowLiving> nowLivingList;
	private LoadingPage loading;
	private long exitTime;
	public MyApplication myApplication;
	private static final String TAG = "SU-JPush";

	String Id = "";
	String UserPic = "";
	String UserId = "";
	String Cclive = "";

	public NowLivingFragment() {
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

		context = this.getActivity().getApplicationContext();

		expertView = inflater.inflate(R.layout.fragment_nowliving, container, false);
		// initDB();
		initView();
		initData();
		setListeners();
		return expertView;
	}

	private void initDB() {
		dbh = new DBHelper(context);
	}

	private void initData() {
		dbh = new DBHelper(context);
		if (dbh.queryUserInfo().size() > 0) {
			UserId = dbh.queryUserInfo().get(0).getid();
			String[] property_va = new String[] { "10", pageIndex + "", UserId };
			soapService.getLiving(property_va, false);
		} else {
			String[] property_vas = new String[] { "10", pageIndex + "", "0" };
			soapService.getLiving(property_vas, false);
		}

	}

	private void initView() {
		listView_nowlivinglist = (PullToRefreshListView) expertView.findViewById(R.id.listView_nowlivinglist);
		nowListView = listView_nowlivinglist.getRefreshableView();

	}

	private void setListeners() {

		listView_nowlivinglist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				Id = nowLivingList.get(position - 1).getLiveUserId();
				UserPic = nowLivingList.get(position - 1).getUserHeadpic();
				Cclive = nowLivingList.get(position - 1).getCclive();
				SharedPreferences sp = getActivity().getSharedPreferences("live", Context.MODE_PRIVATE); // 私有数据
																											// category是新建的表名
				Editor editor = sp.edit();// 获取编辑器
				editor.putString("Id", Id);
				editor.putString("UserPic", UserPic);
				editor.putString("Cclive", Cclive);
				editor.commit();

				if (!nowLivingList.get(position - 1).getLivings().equals("0")) {
					Intent intent = new Intent();
					intent.putExtra("LiveUserName", nowLivingList.get(position - 1).getLiveUserName());
					intent.setClass(context, NowLivingDetails_Activity.class);
					startActivity(intent);
				} else if (nowLivingList.get(position - 1).getLivings().equals("0")
						&& !nowLivingList.get(position - 1).getCclive().equals("1")) {
					Toast.makeText(context, "直播暂时没有开启！", Toast.LENGTH_SHORT).show();
				} else if (nowLivingList.get(position - 1).getLivings().equals("0")
						&& nowLivingList.get(position - 1).getCclive().equals("1")) {
					Intent intent = new Intent();
					intent.setClass(context, CcliveActivity.class);
					startActivity(intent);
				}
			}
		});
		listView_nowlivinglist.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				pageIndex = 1;
				dbh = new DBHelper(context);
				if (dbh.queryUserInfo().size() > 0) {
					UserId = dbh.queryUserInfo().get(0).getid();
					String[] property_va = new String[] { "10", pageIndex + "", UserId };
					soapService.getLiving(property_va, false);
				} else {
					String[] property_va = new String[] { "10", pageIndex + "", "0" };
					soapService.getLiving(property_va, false);
				}
			}
		});

		// end of list
		listView_nowlivinglist.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				dbh = new DBHelper(context);
				if (dbh.queryUserInfo().size() > 0) {
					UserId = dbh.queryUserInfo().get(0).getid();
					String[] property_va = new String[] { "10", ++pageIndex + "", UserId };
					soapService.getLiving(property_va, true);
				} else {
					String[] property_va = new String[] { "10", ++pageIndex + "", "0" };
					soapService.getLiving(property_va, true);
				}

			}
		});
	}

	private void getDBData() {
		// nowLivingList = dbh.queryNowLiving();
		nowLivingAdapter = new NowLivingList_Adapter(context, nowLivingList, UserId);
		nowListView.setAdapter(nowLivingAdapter);

	}

	public void onEvent(SoapRes obj) {
		if (obj.getCode().equals(SOAP_UTILS.METHOD.GETLIVING)) {
			listView_nowlivinglist.onRefreshComplete();
			if (obj.isPage()) {
				for (NowLiving bean : (List<NowLiving>) obj.getObj()) {
					nowLivingList.add(bean);
				}
				nowLivingAdapter.notifyDataSetChanged();
			} else {
				nowLivingList = (List<NowLiving>) obj.getObj();
				getDBData();
			}
		} else if (obj.getCode().equals(SOAP_UTILS.METHOD.GETATTENTIONLIVEUSER)) {
			try {
				JSONObject json_obj = new JSONObject(obj.getObj().toString());

				if (json_obj.get("Result").toString().equals("sucess")) {

					Toast.makeText(context, json_obj.get("Message").toString(), Toast.LENGTH_SHORT).show();
				} else if (json_obj.get("Result").toString().equals("false")) {
					// Toast.makeText(context,
					// json_obj.get("Message").toString(),
					// Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}