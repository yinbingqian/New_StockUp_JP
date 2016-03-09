package com.sxit.activity.wap;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.wap.adapter.WapCommentItemAdapter;
import com.sxit.entity.WapCommentItem;
import com.sxit.http.SoapRes;
import com.sxit.utils.SOAP_UTILS;

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
public class WapComment_Activity extends BaseActivity {
	Context context;

	private PullToRefreshListView listView_newslist;
	private ListView listView;
	private int pageIndex = 1;

	private WapCommentItemAdapter adapter;
	
	ImageView img_back;
    String NewsId = "";
	private ArrayList<WapCommentItem> itemEntities;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wapcomment);
		this.isParentActivity = false;
		context = this;
		
		Intent intent = this.getIntent();
		NewsId = intent.getStringExtra("NewsId");
		
		initView();
		setListeners();
		initData();
	}

	private void initData() {
		String[] property_va = new String[] {NewsId,"10", pageIndex + ""};
		soapService.getNewsComment(property_va, false);
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
				Toast.makeText(context, itemEntities.get(position-1).getCrtime(), Toast.LENGTH_SHORT).show();
			}
		});
		listView_newslist.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				pageIndex = 1;
				String[] property_va = new String[] {NewsId,"10", pageIndex + ""};
				soapService.getNewsComment(property_va, false);
			}
		});
		// end of list
		listView_newslist.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				String[] property_va = new String[] { NewsId,"10", ++pageIndex + "" };
				soapService.getNewsComment(property_va, true);

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
	
	public void onEvent(SoapRes obj) {
		super.onEvent(obj);
		if (obj.getCode().equals(SOAP_UTILS.METHOD.GETNEWSCOMMENT)) {
			listView_newslist.onRefreshComplete();
			if(obj.isPage()){
				for (WapCommentItem bean : (List<WapCommentItem>) obj.getObj()) {
					itemEntities.add(bean);
				}
				adapter.notifyDataSetChanged();
			}else{				
				itemEntities = (ArrayList<WapCommentItem>)obj.getObj();
				if(itemEntities.size()!=0){					
					pageIndex = 1;
					adapter = new WapCommentItemAdapter(this, itemEntities);
					listView.setAdapter(adapter);
				}
			}
		}
	}
	
}
