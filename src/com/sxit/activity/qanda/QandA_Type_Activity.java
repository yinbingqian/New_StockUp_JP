package com.sxit.activity.qanda;

import java.util.ArrayList;
import java.util.List;

import com.sxit.activity.base.BaseActivity;
import com.sxit.entity.discuss.DiscussTag;
import com.sxit.http.SoapRes;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.tag.TagBaseAdapter;
import com.sxit.utils.tag.TagCloudLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import lnpdit.lntv.tradingtime.R;

/**
 * 新闻详情
 * 
 * @author huanyu 类名称：NewsInfo_Activity 创建时间:2014-10-29 下午3:18:47
 */
public class QandA_Type_Activity extends BaseActivity {
	Context context;
	
	ImageView img_back;
	ImageView img_title;

	private List<DiscussTag> dis_list;
    private TagCloudLayout mContainer;
    private TagBaseAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_discuss_type);
		this.isParentActivity = false;
		context = this;
		initView();
		initData();
	}

	private void initData() {
		String[] property_va = new String[] {};
		soapService.getQuestionColumn(property_va);
	}

	private void initView() {
		mContainer = (TagCloudLayout) findViewById(R.id.container);
		img_back = (ImageView) findViewById(R.id.img_back);
		img_title = (ImageView) findViewById(R.id.img_title);
		
		img_title.setBackgroundResource(R.drawable.qanda_type_title_img);
		
		img_back.setOnClickListener(this);
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
		if (obj.getCode().equals(SOAP_UTILS.METHOD.GETQUESTIONCOLUMN)) {
			dis_list = (List<DiscussTag>) obj.getObj();
			mAdapter = new TagBaseAdapter(this, dis_list);
			mContainer.setAdapter(mAdapter);
	        mContainer.setItemClickListener(new TagCloudLayout.TagItemClickListener() {
	                    @Override
	                    public void itemClick(int position) {
	                        // Toast.makeText(NetwortAlarmActivity.this,mList.get(position),Toast.LENGTH_SHORT).show();
	                    	Intent intent = new Intent();
	                    	intent.putExtra("title", dis_list.get(position).getTitle());
	                    	intent.putExtra("value", dis_list.get(position).getValue());
	                    	setResult(RESULT_OK, intent);
	                    	finish();
	                    }
	                });
		}
	}
	
}
