package com.sxit.activity.smarter;

import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.smarter.adapter.ExpertDetailLists_Adapter;
import com.sxit.activity.wap.Wap_Activity;
import com.sxit.entity.smarter.UserExpertDetailChanged;
import com.sxit.http.SoapRes;
import com.sxit.instance.Instance;
import com.sxit.utils.SOAP_UTILS;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import lnpdit.lntv.tradingtime.R;

/**
 * 详情页
 * 
 * @author huanyu 类名称：NewsInfo_Activity 创建时间:2014-10-29 下午3:18:47
 */
public class ExpertDetails_Activity extends BaseActivity {
	private ImageView img_back;
	private TextView tv_title;
	private ImageView head_pic;
	private String expertId;
	private TextView expertdetail_name;
	private TextView expertdetail_style;
	private TextView expertdetail_resume;
	private TextView expertdetail_mark;
	private TextView expertdetail_rewardmark;
	 private PullToRefreshListView listView_expertdetail;
	private ExpertDetailLists_Adapter adapter;
	private ListView listView;
	private List<UserExpertDetailChanged> list;
	private int pageIndex = 1;
	View headerView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_expertdetail);
		super.onCreate(savedInstanceState);
		this.isParentActivity = false;
		intent = getIntent();
		expertId = intent.getStringExtra("id");
		initView();
		setListeners();
		String[] property_va = new String[] { expertId, "10", pageIndex + "" };
		soapService.userExpertDetailChanged(property_va, false);
	}

	private void initView() {
		headerView = getLayoutInflater().inflate(
				R.layout.activity_expertdetailhead, null);
		img_back = (ImageView) findViewById(R.id.img_back);
		head_pic = (ImageView) headerView
				.findViewById(R.id.expertdetail_headpic);
		expertdetail_name = (TextView) headerView
				.findViewById(R.id.expertdetail_name);
		expertdetail_style = (TextView) headerView
				.findViewById(R.id.expertdetail_style);
		expertdetail_resume = (TextView) headerView
				.findViewById(R.id.expertdetail_resume);
		expertdetail_mark = (TextView) headerView
				.findViewById(R.id.expertdetail_mark);
		expertdetail_rewardmark = (TextView) headerView
				.findViewById(R.id.expertdetail_rewardmark);

		Instance.imageLoader.displayImage(
				SOAP_UTILS.HTTP_HEAD_PATH + intent.getStringExtra("headpic"),
				head_pic, Instance.user_s_options);
		expertdetail_name.setText(intent.getStringExtra("name"));
		expertdetail_style.setText(intent.getStringExtra("style"));
		expertdetail_resume.setText(intent.getStringExtra("resume"));
		expertdetail_mark.setText(intent.getStringExtra("mark"));
		expertdetail_rewardmark.setText(intent.getStringExtra("rewardmark"));

		listView_expertdetail = (PullToRefreshListView) findViewById(R.id.listView_expertdetail);
		listView = listView_expertdetail.getRefreshableView();
		listView.addHeaderView(headerView);
	}

	private void setListeners() {
		img_back.setOnClickListener(this);
		listView_expertdetail.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				Intent intent = new Intent();
//				intent.setClass(ExpertDetails_Activity.this, Discuss_Reply_Activity.class);
//				Bundle bundle = new Bundle();
//				UserExpertDetailChanged disitem = new UserExpertDetailChanged();
//				disitem.setAdminid(list.get(position).getAdminid());
//				disitem.setColtitle(list.get(position).getColtitle());
//				disitem.setCrtime(list.get(position).getCrtime());
//				disitem.setHeadPic(list.get(position).getHeadPic());
//				disitem.setId(list.get(position).getId());
//				disitem.setOrders(list.get(position).getOrders());
//				disitem.setOrgName(list.get(position).getOrgName());
//				disitem.setPicture(list.get(position).getPicture());
//				disitem.setRealName(list.get(position).getRealName());
//				disitem.setSource(list.get(position).getSource());
//				disitem.setThumbnail(list.get(position).getThumbnail());
//				disitem.setTitle(list.get(position).getTitle());
				if(position > 1){
				Intent intent = new Intent();
				intent.setClass(ExpertDetails_Activity.this, Wap_Activity.class);
				intent.putExtra("wap_url", SOAP_UTILS.HTTP_NEWSINFO_PATH + list.get(position-2).getId());
				intent.putExtra("wap_share", SOAP_UTILS.HTTP_NEWSSHARE_PATH + list.get(position-2).getId());
				intent.putExtra("wap_name", list.get(position-2).getTitle());
//				intent.putExtra("NewsComCount", list.get(position-2).get);
				startActivity(intent);
				}
//				bundle.putSerializable("discuss", disitem);
//				intent.putExtras(bundle);
//				startActivity(intent);
			}
		});
		listView_expertdetail.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				pageIndex = 1;
				String[] property_va = new String[] { expertId, "10", pageIndex + "" };
				soapService.userExpertDetailChanged(property_va, false);
			}
		});
		// end of list
		listView_expertdetail.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				String[] property_va = new String[] { expertId, "10", ++pageIndex + "" };
				soapService.userExpertDetailChanged(property_va, true);

			}
		});
	}

	/**
	 * 列表刷新
	 * 
	 * @author why
	 * 
	 */
	private class GetDataTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
			}
			return "";
		}

		@Override
		protected void onPostExecute(String result) {
			pageIndex = 1;
			String[] property_va = new String[] { expertId, "10",
					pageIndex + "" };
			soapService.userExpertDetailChanged(property_va, false);
			super.onPostExecute(result);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public void onEvent(SoapRes obj) {
		super.onEvent(obj);
		if (obj.getCode().equals(SOAP_UTILS.METHOD.USEREXPERTDETAILCHANGED)) {
			listView_expertdetail.onRefreshComplete();
			if (obj.isPage()) {
				for (UserExpertDetailChanged bean : (List<UserExpertDetailChanged>) obj.getObj()) {
					list.add(bean);
				}
				adapter.notifyDataSetChanged();
			} else {
				list = (List<UserExpertDetailChanged>) obj.getObj();
				if (list != null) {
					if (list.size() != 0) {
						adapter = new ExpertDetailLists_Adapter(this, list);
						listView.setAdapter(adapter);
					} else {
						listView.setAdapter(null);
					}
				}
			}
			// listView_expertdetail.onRefreshComplete();
		}
	}

	@Override
	public void onEventMainThread(String method) {
		if (method.equals(SOAP_UTILS.METHOD.USEREXPERTDETAILCHANGED)) {
			String[] property_va = new String[] { expertId, "10",
					pageIndex + "" };
			soapService.userExpertDetailChanged(property_va, false);
		}
	}
}
