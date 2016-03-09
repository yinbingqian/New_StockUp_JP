package com.sxit.activity.smarter;

import java.util.List;

import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.discuss.Discuss_Reply_Activity;
import com.sxit.activity.smarter.adapter.ExpertDetailList_Adapter;
import com.sxit.entity.discuss.DiscussItem;
import com.sxit.entity.smarter.ExpertDetail;
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
public class ExpertDetail_Activity extends BaseActivity {
	private ImageView img_back;
	private TextView tv_title;
	private ImageView head_pic;
	private String expertId;
	private TextView expertdetail_name;
	private TextView expertdetail_style;
	private TextView expertdetail_resume;
	private TextView expertdetail_mark;
	private TextView expertdetail_rewardmark;
	// private PullToRefreshListView listView_expertdetail;
	private ExpertDetailList_Adapter adapter;
	private ListView listView;
	private List<ExpertDetail> list;
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
		soapService.getExpertDetail(property_va, false);
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

		listView = (ListView) findViewById(R.id.listView_expertdetail);
		listView.addHeaderView(headerView);
		// listView = listView_expertdetail.getRefreshableView();
	}

	private void setListeners() {
		img_back.setOnClickListener(this);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.setClass(ExpertDetail_Activity.this, Discuss_Reply_Activity.class);
				Bundle bundle = new Bundle();
				DiscussItem disitem = new DiscussItem();
				disitem.setColumn(list.get(position).getcolumn());
				disitem.setContent(list.get(position).getcontent());
				disitem.setCrtime(list.get(position).getcrtime());
				disitem.setDownmark(list.get(position).getdownmark());
				disitem.setHeadPic(list.get(position).getheadpic());
				disitem.setHot(list.get(position).gethot());
				disitem.setId(list.get(position).getwebid());
				disitem.setImg1(list.get(position).getimg1());
				disitem.setImg2(list.get(position).getimg2());
				disitem.setImgthumbnail1(list.get(position).getimgthumbnail1());
				disitem.setImgthumbnail2(list.get(position).getimgthumbnail2());
				disitem.setMark(list.get(position).getmark());
				disitem.setRealName(list.get(position).getrealname());
				disitem.setReplynum(list.get(position).getreplynum());
				disitem.setRewardmark(list.get(position).getrewardmark());
				disitem.setSign(list.get(position).getsign());
				disitem.setUserid(list.get(position).getuserid());
				
				bundle.putSerializable("discuss", disitem);
				intent.putExtras(bundle);
				startActivity(intent);
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
			soapService.getExpertDetail(property_va, false);
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
		if (obj.getCode().equals(SOAP_UTILS.METHOD.GETEXPERTDETAIL)) {
			if (obj.isPage()) {
				for (ExpertDetail bean : (List<ExpertDetail>) obj.getObj()) {
					list.add(bean);
				}
				adapter.notifyDataSetChanged();
			} else {
				list = (List<ExpertDetail>) obj.getObj();
				if (list != null) {
					if (list.size() != 0) {
						adapter = new ExpertDetailList_Adapter(this, list);
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
		if (method.equals(SOAP_UTILS.METHOD.GETEXPERTDETAIL)) {
			String[] property_va = new String[] { expertId, "10",
					pageIndex + "" };
			soapService.getExpertDetail(property_va, false);
		}
	}
}
