package com.sxit.activity.th.item;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.discuss.adapter.DiscussItemAdapter;
import com.sxit.activity.login.Login_Activity;
import com.sxit.activity.th.item.adapter.MyArticleItemAdapter;
import com.sxit.db.DBHelper;
import com.sxit.entity.discuss.DiscussItem;
import com.sxit.http.SoapRes;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.ShareTool;

import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.KeyEvent;
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
public class MyArticel_Activity extends BaseActivity {
	Context context;

	private PullToRefreshListView listView_newslist;
	private ListView listView;
	private int pageIndex = 1;

	private MyArticleItemAdapter adapter;
	
	ImageView img_back;

	private ArrayList<DiscussItem> itemEntities;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myarticle);
		this.isParentActivity = true;
		context = this;
		initView();
		setListeners();
		initData();
	}

	private void initData() {
		String[] property_va = new String[] {getUserInfo().getid(), "10", pageIndex + ""};
		soapService.getMyDiscussionList(property_va, false);
	}

	private void initView() {
		listView_newslist = (PullToRefreshListView) findViewById(R.id.listView_newslist);
		listView = listView_newslist.getRefreshableView();
		
		img_back = (ImageView) this.findViewById(R.id.img_back);
	}

	private void setListeners() {
		img_back.setOnClickListener(this);
		img_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
//		listView_newslist.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				System.out.println("Click News");
//				Toast.makeText(context, itemEntities.get(position-1).getCrtime(), Toast.LENGTH_SHORT).show();
//			}
//		});
		listView_newslist.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				pageIndex = 1;
				String[] property_va = new String[] {getUserInfo().getid(), "10", pageIndex + ""};
				soapService.getMyDiscussionList(property_va, false);
			}
		});
		// end of list
		listView_newslist.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				String[] property_va = new String[] {getUserInfo().getid(), "10", ++pageIndex + "" };
				soapService.getMyDiscussionList(property_va, true);

			}
		});
	}
	
	public void onEvent(SoapRes obj) {
		super.onEvent(obj);
		if (obj.getCode().equals(SOAP_UTILS.METHOD.GETMYDISCUSSIONLIST)) {
			listView_newslist.onRefreshComplete();
			if(obj.isPage()){
				for (DiscussItem bean : (List<DiscussItem>) obj.getObj()) {
					itemEntities.add(bean);
				}
				adapter.notifyDataSetChanged();
			}else{				
				itemEntities = (ArrayList<DiscussItem>)obj.getObj();
				if(itemEntities.size()!=0){					
					pageIndex = 1;
					adapter = new MyArticleItemAdapter(this, itemEntities);
					listView.setAdapter(adapter);
				}
			}
		}
	}
	

	/** 监听返回键 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			finish();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}
	
}
