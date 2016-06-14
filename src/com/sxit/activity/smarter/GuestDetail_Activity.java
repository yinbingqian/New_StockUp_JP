package com.sxit.activity.smarter;

import java.util.List;

import lnpdit.lntv.tradingtime.R;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.smarter.adapter.GuestDetailList_Adapter;
import com.sxit.activity.wap.Wap_Activity;
import com.sxit.entity.smarter.GuestDetail;
import com.sxit.http.SoapRes;
import com.sxit.instance.Instance;
import com.sxit.utils.SOAP_UTILS;

/**
 * 详情页
 * 
 * @author huanyu 类名称：NewsInfo_Activity 创建时间:2014-10-29 下午3:18:47
 */
public class GuestDetail_Activity extends BaseActivity {
	private ImageView img_back;
	private TextView tv_title;
	private ImageView head_pic;
	private String guestId;
	private TextView guestdetail_name;
	private TextView guestdetail_style;
	private TextView guestdetail_resume;
	private ListView listView_guestdetail;
	private GuestDetailList_Adapter adapter;
	private ListView listView;
	private List<GuestDetail> list;
	private int pageIndex = 1;
	View headerView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_guestdetail);
		super.onCreate(savedInstanceState);
		this.isParentActivity = false;
		intent = getIntent();
		guestId = intent.getStringExtra("id");
		initView();
		setListeners();
		String[] property_va = new String[] { guestId, "10", pageIndex + "" };
		soapService.getGuestDetail(property_va, false);
	}

	private void initView() {
		headerView = getLayoutInflater().inflate(
				R.layout.activity_guestdetailhead, null);
		img_back = (ImageView) findViewById(R.id.img_back);
		head_pic = (ImageView) headerView
				.findViewById(R.id.guestdetail_headpic);
		guestdetail_name = (TextView) headerView
				.findViewById(R.id.guestdetail_name);
		guestdetail_style = (TextView) headerView
				.findViewById(R.id.guestdetail_style);
		guestdetail_resume = (TextView) headerView
				.findViewById(R.id.guestdetail_resume);

		Instance.imageLoader.displayImage(
				SOAP_UTILS.HTTP_HEAD_PATH + intent.getStringExtra("headpic"),
				head_pic, Instance.user_s_options);
		guestdetail_name.setText(intent.getStringExtra("name"));
		guestdetail_style.setText(intent.getStringExtra("style"));
		guestdetail_resume.setText(intent.getStringExtra("resume"));

		listView = (ListView) findViewById(R.id.listView_guestdetail);
		listView.addHeaderView(headerView);
		// listView = listView_guestdetail.getRefreshableView();
	}

	private void setListeners() {
		img_back.setOnClickListener(this);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(position > 0){
					Intent intent = new Intent();
					intent.setClass(GuestDetail_Activity.this, Wap_Activity.class);
					intent.putExtra("wap_url", SOAP_UTILS.HTTP_NEWSINFO_PATH + list.get(position-1).getwebid());
					intent.putExtra("wap_share", SOAP_UTILS.HTTP_NEWSSHARE_PATH + list.get(position-1).getwebid());
					intent.putExtra("wap_name", list.get(position-1).gettitle());
					intent.putExtra("NewsComCount", list.get(position-1).getnewscomcount());
//					intent.putExtra("wap_url", SOAP_UTILS.HTTP_NEWSINFO_PATH + list.get(position).getwebid());
//					intent.putExtra("wap_share", SOAP_UTILS.HTTP_NEWSSHARE_PATH + list.get(position).getwebid());
//					intent.putExtra("wap_name", list.get(position).gettitle());
					startActivity(intent);
				}
				
			}
		});
		// listView_guestdetail
		// .setOnRefreshListener(new OnRefreshListener<ListView>() {
		//
		// @Override
		// public void onRefresh(
		// PullToRefreshBase<ListView> refreshView) {
		// new GetDataTask().execute();
		// }
		// });
		// // end of list
		// listView_guestdetail
		// .setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
		//
		// @Override
		// public void onLastItemVisible() {
		// String[] property_va = new String[] { guestId, "10",
		// ++pageIndex + "" };
		// soapService.getGuestDetail(property_va, true);
		//
		// }
		// });
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
			String[] property_va = new String[] { guestId, "10", pageIndex + "" };
			soapService.getGuestDetail(property_va, false);
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
		if (obj.getCode().equals(SOAP_UTILS.METHOD.GETGUESTDETAIL)) {
			if (obj.isPage()) {
				for (GuestDetail bean : (List<GuestDetail>) obj.getObj()) {
					list.add(bean);
				}
				adapter.notifyDataSetChanged();
			} else {
				list = (List<GuestDetail>) obj.getObj();
				if (list != null) {
					if (list.size() != 0) {
						adapter = new GuestDetailList_Adapter(this, list);
						listView.setAdapter(adapter);
					} else {
						listView.setAdapter(null);
					}
				}
			}
			// listView_guestdetail.onRefreshComplete();
		}
	}

	@Override
	public void onEventMainThread(String method) {
		if (method.equals(SOAP_UTILS.METHOD.GETGUESTDETAIL)) {
			String[] property_va = new String[] { guestId, "10", pageIndex + "" };
			soapService.getGuestDetail(property_va, false);
		}
	}
}
