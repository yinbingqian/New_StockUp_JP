package com.sxit.activity.main;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.login.Login_Activity;
import com.sxit.activity.main.adapter.DuelPoint_Adapter;
import com.sxit.activity.th.HomePageActivity;
import com.sxit.activity.th.adapter.HomePageNews_Adapter;
import com.sxit.db.DBHelper;
import com.sxit.entity.DuelPoint;
import com.sxit.http.SoapRes;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.ShareTool;
import com.sxit.utils.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import lnpdit.lntv.tradingtime.R;

/**
 * 新闻详情
 * 
 * @author huanyu 类名称：NewsInfo_Activity 创建时间:2014-10-29 下午3:18:47
 */
public class Duel_Activity extends BaseActivity {
	Context context;

	private PullToRefreshListView listView_newslist;
	private ListView listView;
	private int pageIndex = 1;

	private DBHelper dbh;
	private DuelPoint_Adapter adapter;
	private List<DuelPoint> list;

	View headerView = null;
	ImageView img_back;
	ImageView img_send;
	ProgressBar stock_progressbar;
	Button stock_up_bt;
	Button bt_gushidongtai;
	Button stock_down_bt;
	Button bt_jiabinguandian;
	TextView up_text;
	TextView down_text;
	Button bt_share;

	int total_count = 0;
	int up_count = 0;
	int down_count = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_duel);
		this.isParentActivity = false;
		context = this;
		initView();
		setListeners();
		initData();
	}

	private void initData() {
		String[] property_va = new String[] {};
		soapService.getDuelVote(property_va);
	}
	
	private void duelVote(String point){
		dbh = new DBHelper(this);
		if(dbh.queryUserInfo().size()>0){
			String[] property_va = new String[] {getUserInfo().getid(),point};
			soapService.duelVote(property_va);
		}else{			
			Intent intent_login = new Intent();
			intent_login.setClass(Duel_Activity.this, Login_Activity.class);
			startActivity(intent_login);
		}
	}

	private void initView() {
		headerView = getLayoutInflater().inflate(R.layout.view_duel_head, null);
		stock_up_bt = (Button) headerView.findViewById(R.id.stock_up_bt);
		bt_gushidongtai = (Button) headerView.findViewById(R.id.bt_gushidongtai);
		stock_down_bt = (Button) headerView.findViewById(R.id.stock_down_bt);
		bt_jiabinguandian = (Button) headerView.findViewById(R.id.bt_jiabinguandian);
		up_text = (TextView) headerView.findViewById(R.id.up_text);
		down_text = (TextView) headerView.findViewById(R.id.down_text);
		stock_progressbar = (ProgressBar) headerView.findViewById(R.id.duel_progress);
		listView_newslist = (PullToRefreshListView) findViewById(R.id.listView_newslist);
		bt_share = (Button) headerView.findViewById(R.id.bt_share);
		listView = listView_newslist.getRefreshableView();
		
		img_back = (ImageView) this.findViewById(R.id.img_back);
		img_send = (ImageView) this.findViewById(R.id.img_send);
		img_back.setOnClickListener(this);
		img_send.setOnClickListener(this);
		bt_share.setOnClickListener(this);
		listView.addHeaderView(headerView);
		listView.setAdapter(null);
		
		stock_progressbar.setProgress(50);
	}
	
	private void setUI(){
		int up_int = up_count*100/total_count;
		int down_int = 100-up_int;
		up_text.setText(up_int + "%");
		down_text.setText(down_int + "%");
		stock_progressbar.setProgress(up_count*100/total_count);
		
		adapter = new DuelPoint_Adapter(this, list);
		listView.setAdapter(adapter);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initData();
	}

	private void setListeners() {
		img_back.setOnClickListener(this);
		stock_up_bt.setOnClickListener(this);
		bt_gushidongtai.setOnClickListener(this);
		stock_down_bt.setOnClickListener(this);
		bt_jiabinguandian.setOnClickListener(this);
		listView_newslist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				System.out.println("Click News");
//				Intent intent = new Intent();
//				intent.setClass(context, Wap_Activity.class);
//				intent.putExtra("wap_url", list.get(position - 1).getId());
//				intent.putExtra("wap_name", list.get(position - 1).getTitle());
//				startActivity(intent);
			}
		});
		listView_newslist.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//				String[] property_va = new String[] {"10", pageIndex + ""};
//				soapService.getListByGuest(property_va, false);
				pageIndex = 1;
				String[] property_va = new String[] {};
				soapService.getDuelVote(property_va);
			}
		});
		// end of list
		listView_newslist.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				pageIndex = pageIndex + 1;
				String[] property_va = new String[] { "10", pageIndex + "" };
				soapService.getDuelVotePage(property_va);

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
		case R.id.img_send:
			Intent intent = new Intent();
			intent.setClass(Duel_Activity.this, Duel_Edit_Activity.class);
			startActivity(intent);
			break;
		case R.id.stock_up_bt:
			duelVote("up");
			break;
		case R.id.bt_gushidongtai:
			duelVote("up");
			break;
		case R.id.stock_down_bt:
			duelVote("down");
			break;
		case R.id.bt_jiabinguandian:
			duelVote("down");
			break;
		case R.id.bt_share:
			int up_int = up_count*100/total_count;
			int down_int = 100-up_int;
			String content = "今日股市多空对决，当前看涨"+up_int+"%vs看跌"+down_int+"%，您怎么看？来投一票吧！";
			ShareTool.getInstance(this,this, "股涨多空对决", content, SOAP_UTILS.HTTP_DUELSHARE_PATH,null).shareMsg(this);
			break;
		default:
			break;
		}
	}
	
	public void onEvent(SoapRes obj) {
		super.onEvent(obj);
		if(obj.getCode().equals(SOAP_UTILS.METHOD.VIEWPOINTSUPPORT)){
			try {
				JSONObject json_obj = new JSONObject(obj.getObj().toString());
				String result = json_obj.get("Result").toString();
				if(result.equals("success")){
					int position = obj.getPosition();
					int supportrate = Integer.valueOf(list.get(position).getSupportrate()) + 1;
					list.get(position).setSupportrate(supportrate+"");
					adapter.notifyDataSetChanged();
//					Utils.showTextToast(context, "支持成功");
				}else{
					Utils.showTextToast(context, json_obj.getString("Message"));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Utils.showTextToast(context, "数据错误");
			} catch (Exception e){
				// TODO Auto-generated catch block
				e.printStackTrace();
				Utils.showTextToast(context, "数据错误");
			}
		}else if(obj.getCode().equals(SOAP_UTILS.METHOD.DUELVOTE)){
			try {
				JSONObject json_obj = new JSONObject(obj.getObj().toString());
				String result = json_obj.get("Result").toString();
				if(result.equals("success")){					
					JSONObject msg_obj = new JSONObject(json_obj.getString("Message"));
					up_count = msg_obj.getInt("up");
					down_count = msg_obj.getInt("down");
					total_count = up_count + down_count;
					
					int up_int = up_count*100/total_count;
					int down_int = 100-up_int;
					up_text.setText(up_int + "%");
					down_text.setText(down_int + "%");
					stock_progressbar.setProgress(up_count*100/total_count);
					Utils.showTextToast(context, "投票成功");
				}else{
					Utils.showTextToast(context, json_obj.getString("Message"));
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if (obj.getCode().equals(SOAP_UTILS.METHOD.GETDUELVOTEPAGE)) {
			for (DuelPoint bean : (List<DuelPoint>) obj.getObj()) {
				list.add(bean);
			}
			adapter.notifyDataSetChanged();
		}else if(obj.getCode().equals(SOAP_UTILS.METHOD.GETDUELVOTE)){
			list = new ArrayList<DuelPoint>();
			listView_newslist.onRefreshComplete();
			try {
				JSONObject json_obj = new JSONObject(obj.getObj().toString());
				String result = json_obj.get("Result").toString();
				if(result.equals("success")){					
					JSONObject msg_obj = new JSONObject(json_obj.getString("Message"));
					JSONArray point_array = msg_obj.getJSONArray("point");
					up_count = msg_obj.getInt("up");
					down_count = msg_obj.getInt("down");
					total_count = up_count + down_count;
					
					for (int i = 0; i < point_array.length(); i++) {
						JSONObject json_point = (JSONObject) point_array.get(i);
						DuelPoint dp = new DuelPoint();
						dp.setId(json_point.get("Id").toString());
						dp.setUserid(json_point.get("Userid").toString());
						dp.setContent(json_point.get("Content").toString());
						dp.setViewpoint(json_point.get("Viewpoint").toString());
						dp.setSupportrate(json_point.get("Supportrate").toString());
						dp.setRealName(json_point.get("RealName").toString());
						dp.setHeadPic(json_point.get("HeadPic").toString());
						
						list.add(dp);
					}
					setUI();
				}else{
					Utils.showTextToast(context, "数据请求错误");
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
