package com.sxit.activity.main;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.main.adapter.ListItemAdapter;
import com.sxit.activity.th.adapter.HomePageNews_Adapter;
import com.sxit.activity.wap.Wap_Activity;
import com.sxit.db.DBHelper;
import com.sxit.entity.HomePageNews;
import com.sxit.entity.LiveEntity;
import com.sxit.http.SoapRes;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.ShareTool;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import lnpdit.lntv.tradingtime.R;

/**
 * 新闻详情
 * 
 * @author huanyu 类名称：NewsInfo_Activity 创建时间:2014-10-29 下午3:18:47
 */
public class Live_Activity extends BaseActivity {
	Context context;

	private PullToRefreshListView listView_newslist;
	private ListView listView;
	private int pageIndex = 1;

	private ListItemAdapter adapter;
//	private List<LiveEntity> list;
	
	ImageView img_back;
	ImageView img_share;

	private ArrayList<LiveEntity> itemEntities;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_live);
		this.isParentActivity = false;
		context = this;
		initView();
		setListeners();
		initData();
	}

	private void initData() {
		pageIndex = 1;
		String[] property_va = new String[] {"10", pageIndex + ""};
		soapService.getListLive(property_va, false);
	}

	private void initView() {
		listView_newslist = (PullToRefreshListView) findViewById(R.id.listView_newslist);
		listView = listView_newslist.getRefreshableView();
		
		img_back = (ImageView) this.findViewById(R.id.img_back);
		img_share = (ImageView) this.findViewById(R.id.img_share);
	}

	private void setListeners() {
		img_back.setOnClickListener(this);
		img_share.setOnClickListener(this);
		listView_newslist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				System.out.println("Click News");
//				Intent intent = new Intent();
//				intent.setClass(context, Wap_Activity.class);
//				intent.putExtra("wap_url", list.get(position - 1).getId());
//				intent.putExtra("wap_name", list.get(position - 1).getTitle());
//				startActivity(intent);
			}
		});
		listView_newslist.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				pageIndex = 1;
				String[] property_va = new String[] {"10", pageIndex + ""};
				soapService.getListLive(property_va, false);
			}
		});
		// end of list
		listView_newslist.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				String[] property_va = new String[] { "10", ++pageIndex + "" };
				soapService.getListLive(property_va, true);

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
		case R.id.img_share:
			String content = "图文直播正在进行：与大盘同步实时解盘";
			ShareTool.getInstance(this,this, "股涨图文直播", content, SOAP_UTILS.HTTP_LIVESHARE_PATH,null).shareMsg(this);
			break;
		default:
			break;
		}
	}
	
	public void onEvent(SoapRes obj) {
		super.onEvent(obj);
		if (obj.getCode().equals(SOAP_UTILS.METHOD.GETLISTLIVE)) {
			listView_newslist.onRefreshComplete();
			if(obj.isPage()){
				for (LiveEntity bean : (List<LiveEntity>) obj.getObj()) {
					itemEntities.add(bean);
				}
				adapter.notifyDataSetChanged();
			}else{				
				itemEntities = (ArrayList<LiveEntity>)obj.getObj();
				if(itemEntities.size()!=0){					
					pageIndex = 1;
					adapter = new ListItemAdapter(this, itemEntities);
					listView.setAdapter(adapter);
				}
			}
		}
	}
	
//	/**
//	 * 初始化数据
//	 */
//	private void initLiveData() {
//		itemEntities = new ArrayList<LiveEntity>();
//		// 1.无图片
//		LiveEntity entity1 = new LiveEntity(//
//				"今天下雨了...","2015-12-10","http://img.my.csdn.net/uploads/201410/19/1413698883_5877.jpg", "12","银河证券","赵六", null);
//		itemEntities.add(entity1);
//		// 2.1张图片
//		ArrayList<String> urls_1 = new ArrayList<String>();
//		urls_1.add("http://img.my.csdn.net/uploads/201410/19/1413698883_5877.jpg");
//		LiveEntity entity2 = new LiveEntity(//
//				"今天下雨了...","2015-12-10","http://img.my.csdn.net/uploads/201410/19/1413698883_5877.jpg", "12","银河证券","赵六", urls_1);
//		itemEntities.add(entity2);
//		// 3.3张图片
//		ArrayList<String> urls_2 = new ArrayList<String>();
//		urls_2.add("http://img.my.csdn.net/uploads/201410/19/1413698867_8323.jpg");
//		urls_2.add("http://img.my.csdn.net/uploads/201410/19/1413698883_5877.jpg");
//		urls_2.add("http://img.my.csdn.net/uploads/201410/19/1413698837_5654.jpg");
//		urls_2.add("http://img.my.csdn.net/uploads/201410/19/1413698837_5654.jpg");
//		LiveEntity entity3 = new LiveEntity(//
//				"今天下雨了...","2015-12-10","http://img.my.csdn.net/uploads/201410/19/1413698883_5877.jpg", "12","银河证券","赵六", urls_2);
//		itemEntities.add(entity3);
//		// 4.6张图片
//		ArrayList<String> urls_3 = new ArrayList<String>();
//		urls_3.add("http://img.my.csdn.net/uploads/201410/19/1413698837_7507.jpg");
//		urls_3.add("http://img.my.csdn.net/uploads/201410/19/1413698865_3560.jpg");
//		urls_3.add("http://img.my.csdn.net/uploads/201410/19/1413698867_8323.jpg");
//		urls_3.add("http://img.my.csdn.net/uploads/201410/19/1413698837_5654.jpg");
//		urls_3.add("http://img.my.csdn.net/uploads/201410/19/1413698883_5877.jpg");
//		urls_3.add("http://img.my.csdn.net/uploads/201410/19/1413698839_2302.jpg");
//		LiveEntity entity4 = new LiveEntity(//
//				"今天下雨了...","2015-12-10","http://img.my.csdn.net/uploads/201410/19/1413698883_5877.jpg", "12","银河证券","赵六", urls_3);
//		itemEntities.add(entity4);
//	}
}
