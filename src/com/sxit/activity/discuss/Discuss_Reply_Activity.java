package com.sxit.activity.discuss;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.discuss.adapter.DiscussReplyAdapter;
import com.sxit.activity.login.Login_Activity;
import com.sxit.activity.main.ImagePagerActivity;
import com.sxit.activity.usersetting.DatePickerDialogActivity;
import com.sxit.db.DBHelper;
import com.sxit.entity.discuss.DiscussItem;
import com.sxit.entity.discuss.DiscussReply;
import com.sxit.http.SoapRes;
import com.sxit.instance.Instance;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.Utils;
import com.sxit.utils.XCRoundImageView;
import com.sxit.utils.live.NoScrollGridAdapter;
import com.sxit.utils.live.NoScrollGridView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import lnpdit.lntv.tradingtime.R;

/**
 * 新闻详情
 * 
 * @author huanyu 类名称：NewsInfo_Activity 创建时间:2014-10-29 下午3:18:47
 */
public class Discuss_Reply_Activity extends BaseActivity {
	Context context;

	private PullToRefreshListView listView_newslist;
	private ListView listView;
	private int pageIndex = 1;

	private DiscussReplyAdapter adapter;
	private ArrayList<DiscussReply> list;

	View headerView = null;
	ImageView img_back;
	Button bt_send;

	DiscussItem disitem;
	private DBHelper dbh;
	
	private XCRoundImageView iv_avatar;
	private TextView tv_name;
	private TextView tv_time;
	private NoScrollGridView gridview;
	private TextView tv_label;
	private TextView tv_up;
	private TextView tv_down;
	private TextView tv_content;
	
	String down_num = "0";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_discuss_reply);
		this.isParentActivity = false;
		context = this;
		
		Intent intent = this.getIntent();
		disitem = (DiscussItem) intent.getSerializableExtra("discuss");
		
		initView();
		setListeners();
		initData();
	}

	private void initData() {
		pageIndex = 1;
		String[] property_va = new String[] {disitem.getId() ,"10", pageIndex + ""};
		soapService.getDiscussionReply(property_va, false);
	}
	
	private void initView() {
		headerView = getLayoutInflater().inflate(R.layout.view_discuss_reply_head, null);
		listView_newslist = (PullToRefreshListView) findViewById(R.id.listView_newslist);
		listView = listView_newslist.getRefreshableView();
		
		iv_avatar = (XCRoundImageView) headerView.findViewById(R.id.iv_avatar);
		tv_name = (TextView) headerView.findViewById(R.id.tv_name);
		tv_time = (TextView) headerView.findViewById(R.id.tv_time);
		tv_label = (TextView) headerView.findViewById(R.id.label);
		tv_up = (TextView) headerView.findViewById(R.id.up);
		tv_down = (TextView) headerView.findViewById(R.id.down);
		tv_content = (TextView) headerView.findViewById(R.id.tv_content);
		gridview = (NoScrollGridView) headerView.findViewById(R.id.gridview);
		bt_send = (Button) this.findViewById(R.id.bt_send);
		
		img_back = (ImageView) this.findViewById(R.id.img_back);
		
		Instance.imageLoader.displayImage(disitem.getHeadPic(), iv_avatar, Instance.user_s_options);
		tv_name.setText(disitem.getRealName());
		tv_time.setText(disitem.getCrtime());
		tv_label.setText("威望：" + disitem.getMark());
		tv_up.setText("赞：" + disitem.getRewardmark());
		tv_down.setText("踩：" + down_num);
		tv_content.setText(disitem.getContent());
		
		final ArrayList<String> imageUrls = new ArrayList<String>();
		final ArrayList<String> imageUrls_thumbnail = new ArrayList<String>();
		if(!disitem.getImg1().equals("")){
			imageUrls.add(SOAP_UTILS.HTTP_DISCUSS_PATH + disitem.getImg1());
			imageUrls_thumbnail.add(SOAP_UTILS.HTTP_DISCUSS_PATH + disitem.getImgthumbnail1());
		}
		if(!disitem.getImg2().equals("")){
			imageUrls.add(SOAP_UTILS.HTTP_DISCUSS_PATH + disitem.getImg2());
			imageUrls_thumbnail.add(SOAP_UTILS.HTTP_DISCUSS_PATH + disitem.getImgthumbnail2());
		}
		if (imageUrls == null || imageUrls.size() == 0) { // 没有图片资源就隐藏GridView
			gridview.setVisibility(View.GONE);
			gridview.setAdapter(null);
		} else {
			gridview.setVisibility(View.VISIBLE);
			gridview.setAdapter(new NoScrollGridAdapter(context, imageUrls_thumbnail));
		}
		
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				imageBrower(position, imageUrls);
			}
		});
		
		listView.addHeaderView(headerView);
		listView.setAdapter(null);
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initData();
	}

	private void setListeners() {
		tv_up.setClickable(true);
		tv_down.setClickable(true);
		tv_label.setOnClickListener(this);
		tv_up.setOnClickListener(this);
		tv_down.setOnClickListener(this);
		bt_send.setOnClickListener(this);
		
		img_back.setOnClickListener(this);
		listView_newslist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				System.out.println("Click News");
			}
		});
		listView_newslist.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				initData();
			}
		});
		// end of list
		listView_newslist.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				pageIndex = pageIndex + 1;
				String[] property_va = new String[] { disitem.getId(), "10", pageIndex + "" };
				soapService.getDiscussionReply(property_va, true);

			}
		});
	}
	
	private void supportPoint(String type){
		dbh = new DBHelper(this);
		if(dbh.queryUserInfo().size()>0){
			String[] property_va = new String[] {disitem.getId(), getUserInfo().getid(), type};
			soapService.discussionUserSupport(property_va,type.equals("up")?true:false);
		}else{
			Intent intent_login = new Intent();
			intent_login.setClass(Discuss_Reply_Activity.this, Login_Activity.class);
			startActivity(intent_login);
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
		case R.id.up:
			supportPoint("up");
			break;
		case R.id.down:
			supportPoint("down");
			break;
		case R.id.bt_send:
			dbh = new DBHelper(this);
			if(dbh.queryUserInfo().size()>0){
				Intent intent = new Intent();
				intent.setClass(context, Discuss_ReplyOwner_Activity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("discuss", disitem);
				intent.putExtras(bundle);
				startActivity(intent);
			}else{
				Intent intent_login = new Intent();
				intent_login.setClass(Discuss_Reply_Activity.this, Login_Activity.class);
				startActivity(intent_login);
			}
			break;
		default:
			break;
		}
	}
	
	/**
	 * 打开图片查看器
	 * 
	 * @param position
	 * @param urls2
	 */
	protected void imageBrower(int position, ArrayList<String> urls2) {
		Intent intent = new Intent(context, ImagePagerActivity.class);
		// 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
		startActivity(intent);
	}
	
	public void onEvent(SoapRes obj) {
		super.onEvent(obj);
		if(obj.getCode().equals(SOAP_UTILS.METHOD.GETDISCUSSIONREPLY)){
			listView_newslist.onRefreshComplete();
			if(obj.isPage()){
				for (DiscussReply bean : (List<DiscussReply>) obj.getObj()) {
					list.add(bean);
				}
				adapter.notifyDataSetChanged();
			}else{				
				list = (ArrayList<DiscussReply>)obj.getObj();
				if(list.size()!=0){					
					pageIndex = 1;
					down_num = list.get(0).getHostid();
					adapter = new DiscussReplyAdapter(this, list, disitem.getId());
					listView.setAdapter(adapter);
				}
			}
		}else if(obj.getCode().equals(SOAP_UTILS.METHOD.DISCUSSIONUSERSUPPORT)){
			String result = obj.getObj().toString();
			if (result.equals("success")) {
				if(obj.isPage()){
					Utils.showTextToast(context, "点赞成功");
					int up_int = Integer.valueOf(disitem.getRewardmark())+1;
					tv_up.setText("赞：" + up_int);
				}else{					
					Utils.showTextToast(context, "点踩成功");
					int down_int = Integer.valueOf(down_num)+1;
					tv_down.setText("踩：" + down_int);
				}
			} else {
				Utils.showTextToast(context, result);
			}
		}
	}
}
