package com.sxit.activity.qanda;

import java.util.ArrayList;
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
import com.sxit.activity.main.ImagePagerActivity;
import com.sxit.activity.qanda.adapter.MyAnswerDetail_Adapter;
import com.sxit.activity.qanda.adapter.QuestionDetail_Adapter;
import com.sxit.entity.qanda.QuestionDetail;
import com.sxit.entity.smarter.GuestDetail;
import com.sxit.http.SoapRes;
import com.sxit.instance.Instance;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.live.NoScrollGridAdapter;
import com.sxit.utils.live.NoScrollGridView;

/**
 * 互动详情页
 * 
 * @author huanyu 类名称：NewsInfo_Activity 创建时间:2014-10-29 下午3:18:47
 */
public class MyAnswerDetail_Activity extends BaseActivity {
	private ImageView img_back;
	private TextView tv_title;
	private ImageView head_pic;
	private String questionId;
	private ImageView myanswerdetail_head_pic;
	private TextView myanswerdetail_title;
	private TextView myanswerdetail_content;
	private TextView myanswerdetail_time;
	private TextView myanswerdetail_type;
	private NoScrollGridView gridview;

	private MyAnswerDetail_Adapter adapter;
	private PullToRefreshListView listView_myanswerdetail;
	private ListView listView;
	private List<QuestionDetail> list;
	private int pageIndex = 1;
	View headerView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_myanswerdetail);
		super.onCreate(savedInstanceState);
		this.isParentActivity = false;
		intent = getIntent();
		questionId = intent.getStringExtra("id");
		initView();
		setListeners();
		String[] property_va = new String[] { questionId, "10", pageIndex + "" };
		soapService.getMyAnswerDetail(property_va, false);
	}

	private void initView() {
		img_back = (ImageView) findViewById(R.id.img_back);

		headerView = getLayoutInflater().inflate(
				R.layout.activity_myanswerdetailhead, null);
		myanswerdetail_head_pic = (ImageView) headerView
				.findViewById(R.id.myanswerdetail_headpic);
		myanswerdetail_title = (TextView) headerView
				.findViewById(R.id.myanswerdetail_title);
		myanswerdetail_content = (TextView) headerView
				.findViewById(R.id.myanswerdetail_content);
		myanswerdetail_time = (TextView) headerView
				.findViewById(R.id.myanswerdetail_time);
		myanswerdetail_type = (TextView) headerView
				.findViewById(R.id.myanswerdetail_type);

		gridview = (NoScrollGridView) headerView
				.findViewById(R.id.myanswerdetailhead_gridview);
		String img1 = intent.getStringExtra("img1");
		String imgthumbnail1 = intent.getStringExtra("imgthumbnail1");
		String img2 = intent.getStringExtra("img2");
		String imgthumbnail2 = intent.getStringExtra("imgthumbnail2");
		final ArrayList<String> imgUrls = new ArrayList<String>();
		final ArrayList<String> imgthumbnailUrls = new ArrayList<String>();
		if (!imgthumbnail1.equals("")) {
			imgUrls.add(SOAP_UTILS.HTTP_DISCUSS_PATH + img1);
			imgthumbnailUrls.add(SOAP_UTILS.HTTP_DISCUSS_PATH + imgthumbnail1);
		}
		if (!imgthumbnail2.equals("")) {
			imgUrls.add(SOAP_UTILS.HTTP_DISCUSS_PATH + img2);
			imgthumbnailUrls.add(SOAP_UTILS.HTTP_DISCUSS_PATH + imgthumbnail2);
		}
		if (imgthumbnailUrls == null || imgthumbnailUrls.size() == 0) { // 没有图片资源就隐藏GridView
			gridview.setVisibility(View.GONE);
		} else {
			gridview.setVisibility(View.VISIBLE);
			gridview.setAdapter(new NoScrollGridAdapter(this, imgthumbnailUrls));
		}

		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				imageBrower(position, imgUrls);
			}
		});

		Instance.imageLoader.displayImage(
				SOAP_UTILS.HTTP_HEAD_PATH + intent.getStringExtra("headpic"),
				myanswerdetail_head_pic, Instance.user_s_options);
		myanswerdetail_title.setText(intent.getStringExtra("title"));
		myanswerdetail_content.setText(intent.getStringExtra("content"));
		myanswerdetail_time.setText(intent.getStringExtra("time"));
		myanswerdetail_type.setText(intent.getStringExtra("type"));

		listView_myanswerdetail = (PullToRefreshListView) findViewById(R.id.listView_myanswerdetail);
		listView = listView_myanswerdetail.getRefreshableView();
		listView.addHeaderView(headerView);
	}

	private void setListeners() {
		img_back.setOnClickListener(this);
		listView_myanswerdetail
				.setOnRefreshListener(new OnRefreshListener<ListView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						new GetDataTask().execute();
					}
				});
		// end of list
		listView_myanswerdetail
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {
						String[] property_va = new String[] { questionId, "10",
								++pageIndex + "" };
						soapService.getMyAnswerDetail(property_va, true);

					}
				});
	}

	/**
	 * 打开图片查看器
	 * 
	 * @param position
	 * @param urls2
	 */
	protected void imageBrower(int position, ArrayList<String> urls2) {
		Intent intent = new Intent(this, ImagePagerActivity.class);
		// 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		this.startActivity(intent);
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
			String[] property_va = new String[] { questionId, "10",
					pageIndex + "" };
			soapService.getMyAnswerDetail(property_va, false);
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
		if (obj.getCode().equals(SOAP_UTILS.METHOD.MYANSWERJSON)) {
			if (obj.isPage()) {
				for (QuestionDetail bean : (List<QuestionDetail>) obj.getObj()) {
					list.add(bean);
				}
				adapter.notifyDataSetChanged();
			} else {
				list = (List<QuestionDetail>) obj.getObj();
				if (list != null) {
					if (list.size() != 0) {
						adapter = new MyAnswerDetail_Adapter(this, list);
						listView.setAdapter(adapter);
					} else {
						listView.setAdapter(null);
					}
				}
			}
			listView_myanswerdetail.onRefreshComplete();
		}
	}

	@Override
	public void onEventMainThread(String method) {
		if (method.equals(SOAP_UTILS.METHOD.MYANSWERJSON)) {
			String[] property_va = new String[] { questionId, "10",
					pageIndex + "" };
			soapService.getMyAnswerDetail(property_va, false);
		}
	}
}
