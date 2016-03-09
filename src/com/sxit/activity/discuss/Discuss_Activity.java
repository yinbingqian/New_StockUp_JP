package com.sxit.activity.discuss;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.discuss.adapter.DiscussItemAdapter;
import com.sxit.activity.login.Login_Activity;
import com.sxit.db.DBHelper;
import com.sxit.entity.discuss.DiscussItem;
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
import android.widget.Toast;
import lnpdit.lntv.tradingtime.R;

/**
 * 新闻详情
 * 
 * @author huanyu 类名称：NewsInfo_Activity 创建时间:2014-10-29 下午3:18:47
 */
public class Discuss_Activity extends BaseActivity {
	Context context;

	private PullToRefreshListView listView_newslist;
	private ListView listView;
	private int pageIndex = 1;

	private DiscussItemAdapter adapter;
	
	ImageView img_share;

	private ArrayList<DiscussItem> itemEntities;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_discuss);
		this.isParentActivity = true;
		context = this;
		initView();
		setListeners();
		initData();
	}

	private void initData() {
		String[] property_va = new String[] {"10", pageIndex + ""};
		soapService.getDiscussionList(property_va, false);
	}

	private void initView() {
		listView_newslist = (PullToRefreshListView) findViewById(R.id.listView_newslist);
		listView = listView_newslist.getRefreshableView();
		
		img_share = (ImageView) this.findViewById(R.id.img_share);
	}

	private void setListeners() {
		img_share.setOnClickListener(this);
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
				String[] property_va = new String[] {"10", pageIndex + ""};
				soapService.getDiscussionList(property_va, false);
			}
		});
		// end of list
		listView_newslist.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				String[] property_va = new String[] { "10", ++pageIndex + "" };
				soapService.getDiscussionList(property_va, true);

			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.img_share:
			DBHelper dbh = new DBHelper(this);
			if(dbh.queryUserInfo().size()>0){
				Intent intent = new Intent();
				intent.setClass(context, Discuss_Edit_Activity.class);
				startActivity(intent);
			}else{
				Intent intent_login = new Intent();
				intent_login.setClass(Discuss_Activity.this, Login_Activity.class);
				startActivity(intent_login);
			}
			break;
		default:
			break;
		}
	}
	
	public void onEvent(SoapRes obj) {
		super.onEvent(obj);
		if (obj.getCode().equals(SOAP_UTILS.METHOD.GETDISCUSSIONLIST)) {
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
					adapter = new DiscussItemAdapter(this, itemEntities);
					listView.setAdapter(adapter);
				}
			}
		}
	}
	
}
