package com.sxit.activity.main;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.th.adapter.HomePageNews_Adapter;
import com.sxit.activity.wap.Wap_Activity;
import com.sxit.db.DBHelper;
import com.sxit.entity.HomePageNews;
import com.sxit.http.SoapRes;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.ToastSign;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import lnpdit.lntv.tradingtime.R;

/**
 * 新闻详情
 * 
 * @author huanyu 类名称：NewsInfo_Activity 创建时间:2014-10-29 下午3:18:47
 */
public class StockNews_Activity extends BaseActivity {
	Context context;

	private PullToRefreshListView listView_newslist;
	private ListView listView;
	private int pageIndex = 1;

	private DBHelper dbh;
	private HomePageNews_Adapter adapter;
	private List<HomePageNews> list;
	
	ImageView img_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stocknews);
		this.isParentActivity = false;
		context = this;
//
//		NewsComCount = this.getIntent().getStringExtra("NewsComCount");
		
		initDB();
		initView();
		initData();
		setListeners();
	}

	private void initDB() {
		dbh = new DBHelper(this);
	}

	private void initData() {
		getDBData();
		pageIndex = 1;
		String[] property_va = new String[] {"10", pageIndex + ""};
		soapService.getListByStock(property_va, false);
	}

	private void initView() {
		listView_newslist = (PullToRefreshListView) findViewById(R.id.listView_newslist);
		listView = listView_newslist.getRefreshableView();
		
		img_back = (ImageView) this.findViewById(R.id.img_back);
	}

	private void setListeners() {
		img_back.setOnClickListener(this);
		listView_newslist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				System.out.println("Click News");
//				Intent intent = new Intent();
//				intent.setClass(context, Wap_Activity.class);
//				intent.putExtra("wap_url", list.get(position - 1).getId());
//				intent.putExtra("wap_name", list.get(position - 1).getTitle());
//				startActivity(intent);
				
				Intent intent = new Intent();
				intent.setClass(context, Wap_Activity.class);
				intent.putExtra("wap_url", SOAP_UTILS.HTTP_NEWSINFO_PATH + list.get(position - 1).getId());
				intent.putExtra("wap_share", SOAP_UTILS.HTTP_NEWSSHARE_PATH + list.get(position - 1).getId());
				intent.putExtra("id", list.get(position - 1).getId());
				intent.putExtra("wap_name", list.get(position - 1).getTitle());
				intent.putExtra("NewsComCount", list.get(position - 1).getNewsComCount());
				intent.putExtra("HeadPic", list.get(position - 1).getHeadPic());
				startActivity(intent);
			}
		});
		listView_newslist.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				pageIndex = 1;
				String[] property_va = new String[] {"10", pageIndex + ""};
				soapService.getListByStock(property_va, false);
			}
		});
		// end of list
		listView_newslist.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				String[] property_va = new String[] { "10", ++pageIndex + "" };
				soapService.getListByStock(property_va, true);

			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		default:
			break;
		}
	}

	private void getDBData() {
		list = dbh.queryStockNewsInfo();

		adapter = new HomePageNews_Adapter(this, list);
		listView.setAdapter(adapter);
		
	}
	
	public void onEvent(SoapRes obj) {
		super.onEvent(obj);
		if (obj.getCode().equals(SOAP_UTILS.METHOD.GETLISTBYSTOCK)) {
			listView_newslist.onRefreshComplete();
			if(obj.isPage()){
				for (HomePageNews bean : (List<HomePageNews>) obj.getObj()) {
					list.add(bean);
				}
				adapter.notifyDataSetChanged();
			}else{				
				list = (List<HomePageNews>)obj.getObj();
				dbh.clearStockNewsData();
				if(list.size()!=0){					
					
					dbh.insStockNewsInfo(list);
					pageIndex = 1;
				}
				getDBData();
			}
		}
	}
}
