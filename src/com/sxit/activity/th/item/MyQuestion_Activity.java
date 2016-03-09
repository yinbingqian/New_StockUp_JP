package com.sxit.activity.th.item;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.base.MyApplication;
import com.sxit.activity.discuss.adapter.DiscussItemAdapter;
import com.sxit.activity.login.Login_Activity;
import com.sxit.activity.qanda.adapter.QuestionList_Adapter;
import com.sxit.activity.th.item.adapter.MyArticleItemAdapter;
import com.sxit.customview.LoadingPage;
import com.sxit.db.DBHelper;
import com.sxit.entity.discuss.DiscussItem;
import com.sxit.entity.qanda.Question;
import com.sxit.http.ISoapService;
import com.sxit.http.SoapRes;
import com.sxit.http.SoapService;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.ShareTool;

import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
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
public class MyQuestion_Activity extends BaseActivity {
	Context context;
	/** soapService **/
	public ISoapService soapService = new SoapService();

	private PullToRefreshListView listView_questionlist;
	private ListView listView;
	private int pageIndex = 1;

	private QuestionList_Adapter adapter;
	private List<Question> list;
	private LoadingPage loading;
	private long exitTime;
	public MyApplication myApplication;
	private static final String TAG = "SU-JPush";

	String userid = "";
	DBHelper dbh;
	
	ImageView img_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myquestion);
		this.isParentActivity = true;
		context = this;
		initView();
		setListeners();
		
		dbh = new DBHelper(context);
		if(dbh.queryUserInfo().size()>0){
			userid = dbh.queryUserInfo().get(0).getid();
			
			String[] property_va = new String[] { userid, "10", pageIndex + "" };
			soapService.getQuestionList(property_va, false);
		}else{
			Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
			Intent intent_login = new Intent();
			intent_login.setClass(context, Login_Activity.class);
			startActivityForResult(intent_login, 100);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
			if(requestCode == 102){
				userid = dbh.queryUserInfo().get(0).getid();
				
				String[] property_va = new String[] { userid, "10", pageIndex + "" };
				soapService.getQuestionList(property_va, false);
			}
	}

	private void initView() {
		listView_questionlist = (PullToRefreshListView) findViewById(R.id.listView_questionlist);
		listView = listView_questionlist.getRefreshableView();
		
		img_back = (ImageView) findViewById(R.id.img_back);
		img_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	private void setListeners() {
		
		listView_questionlist
				.setOnRefreshListener(new OnRefreshListener<ListView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						if(!userid.equals("")){							
							pageIndex = 1;
							String[] property_va = new String[] { userid, "10",
									pageIndex + "" };
							soapService.getQuestionList(property_va, false);
						}


					}
				});

		// end of list
		listView_questionlist
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {
						if(!userid.equals("")){							
							String[] property_va = new String[] { "20", "10",
									++pageIndex + "" };
							soapService.getQuestionList(property_va, true);
						}

					}
				});
	}

	public void onEvent(SoapRes obj) {
		if (obj.getCode().equals(SOAP_UTILS.METHOD.GETQUESTIONLIST)) {
			if (obj.isPage()) {
				for (Question bean : (List<Question>) obj.getObj()) {
					list.add(bean);
				}
				adapter.notifyDataSetChanged();
			} else {
				list = (List<Question>) obj.getObj();
				if (list != null) {
					if (list.size() != 0) {
						adapter = new QuestionList_Adapter(context, list);
						listView.setAdapter(adapter);
					} else {
						listView.setAdapter(null);
					}
				}
			}
			listView_questionlist.onRefreshComplete();
		}
	}
	
}
