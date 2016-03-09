package com.sxit.activity.discuss;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.discuss.adapter.DiscussSearch_Adapter;
import com.sxit.entity.discuss.DiscussTag;
import com.sxit.http.SoapRes;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.tag.TagBaseAdapter;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import lnpdit.lntv.tradingtime.R;

/**
 * 新闻详情
 * 
 * @author huanyu 类名称：NewsInfo_Activity 创建时间:2014-10-29 下午3:18:47
 */
public class Discuss_Type_Activity extends BaseActivity implements
SearchView.OnQueryTextListener {
	Context context;
	
	ImageView img_back;

	List<DiscussTag> dis_list;
	List<DiscussTag> mSearchList;
//    private TagCloudLayout mContainer;
    private DiscussSearch_Adapter mAdapter;
	private ListView disList;
	private SearchView searchView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_discuss_newtype);
		this.isParentActivity = false;
		context = this;
		initView();
		initData();
		
	}

	private void initData() {
		String[] property_va = new String[] {};
		soapService.getDiscussionColumn(property_va);
	}

	private void initView() {
//		mContainer = (TagCloudLayout) findViewById(R.id.container);
		
		disList = (ListView) findViewById(R.id.list_view_discuss);
		searchView = (SearchView) findViewById(R.id.search_view);
		img_back = (ImageView) findViewById(R.id.img_back);
		
		img_back.setOnClickListener(this);
		

		  searchView.setOnQueryTextListener(this);
		  searchView.setSubmitButtonEnabled(false);
		  
		  if (searchView != null) {     
				try {
					Class<?> argClass = searchView.getClass();      
					Field ownField = argClass.getDeclaredField("mSearchPlate");    
					ownField.setAccessible(true); View mView = (View) ownField.get(searchView);
					mView.setBackgroundColor(Color.TRANSPARENT);   
				}
				catch (Exception e) {      
					e.printStackTrace();  
				}
			}

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
		if (obj.getCode().equals(SOAP_UTILS.METHOD.GETDISCUSSIONCOLUMN)) {
			dis_list = (List<DiscussTag>) obj.getObj();
			mSearchList = (List<DiscussTag>) obj.getObj();
//			mAdapter = new DiscussSearch_Adapter(this, mSearchList);
			mAdapter = new DiscussSearch_Adapter(this, dis_list);
			disList.setAdapter(mAdapter);
			disList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	                        // Toast.makeText(NetwortAlarmActivity.this,mList.get(position),Toast.LENGTH_SHORT).show();
	                    	Intent intent = new Intent();
	                    	intent.putExtra("title", mSearchList.get(position).getTitle());
	                    	intent.putExtra("value", mSearchList.get(position).getValue());
//	                    	intent.putExtra("title", dis_list.get(position).getTitle());
//	                    	intent.putExtra("value", dis_list.get(position).getValue());
	                    	setResult(RESULT_OK, intent);
	                    	finish();
	                    }

	                });
		}
	}

	 @Override
	 public boolean onQueryTextChange(String newText) {
		 List<DiscussTag> obj = searchItem(newText);
		 updateLayout(obj);
		 return false;
	 }

	 @Override
	 public boolean onQueryTextSubmit(String query) {
	  // TODO Auto-generated method stub
	  return false;
	 }
	 
	 
	 
	 public List<DiscussTag> searchItem(String name) {
	  mSearchList = new ArrayList<DiscussTag>();/////////////////
	  for (int i = 0; i < dis_list.size(); i++) {
	   int index = dis_list.get(i).getTitle().indexOf(name);
	   // 存在匹配的数据
	   if (index != -1) {
	    mSearchList.add(dis_list.get(i));
	   }
	  }
	  return mSearchList;
	 }

	 public void updateLayout(List<DiscussTag> obj) {
			mAdapter = new DiscussSearch_Adapter(this, obj);
			disList.setAdapter(mAdapter);
	 }

	 // android4.0 SearchView去掉（修改）搜索框的背景 修改光标
	 public void setSearchViewBackground(SearchView searchView) {
	   try {
	    Class<?> argClass = searchView.getClass();
	    // 指定某个私有属性
	    Field ownField = argClass.getDeclaredField("mSearchPlate"); // 注意mSearchPlate的背景是stateListDrawable(不同状态不同的图片)
	                   // 所以不能用BitmapDrawable
	    // setAccessible 它是用来设置是否有权限访问反射类中的私有属性的，只有设置为true时才可以访问，默认为false
	    ownField.setAccessible(true);
	    View mView = (View) ownField.get(searchView);
	    mView.setBackgroundDrawable(getResources().getDrawable(
	      R.drawable.ic_action_search));

	    // 指定某个私有属性
	    Field mQueryTextView = argClass.getDeclaredField("mQueryTextView");
	    mQueryTextView.setAccessible(true);
	    Class<?> mTextViewClass = mQueryTextView.get(searchView).getClass()
	      .getSuperclass().getSuperclass().getSuperclass();

	    // mCursorDrawableRes光标图片Id的属性
	    // 这个属性是TextView的属性，所以要用mQueryTextView（SearchAutoComplete）的父类（AutoCompleteTextView）的父
	    // 类( EditText）的父类(TextView)
	    Field mCursorDrawableRes = mTextViewClass
	      .getDeclaredField("mCursorDrawableRes");

	    // setAccessible 它是用来设置是否有权限访问反射类中的私有属性的，只有设置为true时才可以访问，默认为false
	    mCursorDrawableRes.setAccessible(true);
	    mCursorDrawableRes.set(mQueryTextView.get(searchView),
	      R.drawable.ic_action_search);// 注意第一个参数持有这个属性(mQueryTextView)的对象(mSearchView)
	              // 光标必须是一张图片不能是颜色，因为光标有两张图片，一张是第一次获得焦点的时候的闪烁的图片，一张是后边有内容时候的图片，如果用颜色填充的话，就会失去闪烁的那张图片，颜色填充的会缩短文字和光标的距离（某些字母会背光标覆盖一部分）。
	   } catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	   }
	  }	 
	 
}
