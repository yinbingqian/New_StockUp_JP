package com.sxit.activity.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.main.adapter.ListItemAdapter;
import com.sxit.activity.th.adapter.HomePageNews_Adapter;
import com.sxit.activity.wap.Wap_Activity;
import com.sxit.db.DBHelper;
import com.sxit.entity.HomePageNews;
import com.sxit.entity.LiveEntity;
import com.sxit.http.SoapRes;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.ShareTool;
import com.sxit.utils.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import lnpdit.lntv.tradingtime.R;

/**
 * 新闻详情
 * 
 * @author huanyu 类名称：NewsInfo_Activity 创建时间:2014-10-29 下午3:18:47
 */
public class Live_Activity extends BaseActivity {
	Context context;

	private PullToRefreshListView listView_newslist;
	private ListView listView;
	private int pageIndex = 1;

	private ListItemAdapter adapter;
//	private List<LiveEntity> list;
	
	ImageView img_back;
	ImageView img_share;

	View headerView = null;
	
	TextView sz_tv;
	TextView sz_two_tv;
	TextView sz_three_tv;
	TextView sz_four_tv;
	TextView cy_tv;
	TextView cy_two_tv;
	TextView cy_three_tv;
	TextView cy_four_tv;
	TextView shz_tv;
	TextView shz_two_tv;
	TextView shz_three_tv;
	TextView shz_four_tv;
	String sz_str;
	String sz_two_str;
	String sz_three_str;
	String sz_four_str;
	String cy_str;
	String cy_two_str;
	String cy_three_str;
	String cy_four_str;
	String shz_str;
	String shz_two_str;
	String shz_three_str;
	String shz_four_str;
	
	boolean isRefreshing;
	
	private ArrayList<LiveEntity> itemEntities;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_live);
		this.isParentActivity = false;
		context = this;
		isRefreshing = true;
		initView();
		setListeners();
		initData();
	}

	private void initData() {
	    getSharePreferenceData();
		pageIndex = 1;
		String[] property_va = new String[] {"10", pageIndex + ""};
		soapService.getListLive(property_va, false);
		
		
        Thread thread = new Thread(new stockDataRunnable());
        thread.start();
	}

	private void initView() {
		headerView = getLayoutInflater().inflate(R.layout.view_live_head, null);
		sz_tv = (TextView) headerView.findViewById(R.id.sz_tv);
		sz_two_tv = (TextView) headerView.findViewById(R.id.sz_two_tv);
		sz_three_tv = (TextView) headerView.findViewById(R.id.sz_three_tv);
		sz_four_tv = (TextView) headerView.findViewById(R.id.sz_four_tv);
		cy_tv = (TextView) headerView.findViewById(R.id.cy_tv);
		cy_two_tv = (TextView) headerView.findViewById(R.id.cy_two_tv);
		cy_three_tv = (TextView) headerView.findViewById(R.id.cy_three_tv);
		cy_four_tv = (TextView) headerView.findViewById(R.id.cy_four_tv);
		shz_tv = (TextView) headerView.findViewById(R.id.shz_tv);
		shz_two_tv = (TextView) headerView.findViewById(R.id.shz_two_tv);
		shz_three_tv = (TextView) headerView.findViewById(R.id.shz_three_tv);
		shz_four_tv = (TextView) headerView.findViewById(R.id.shz_four_tv);
		
		listView_newslist = (PullToRefreshListView) findViewById(R.id.listView_newslist);
		listView = listView_newslist.getRefreshableView();
		listView.addHeaderView(headerView);
		
		img_back = (ImageView) this.findViewById(R.id.img_back);
		img_share = (ImageView) this.findViewById(R.id.img_share);
		
	}

	private void getSharePreferenceData() {

		SharedPreferences sp = getSharedPreferences("homepagelive", Context.MODE_PRIVATE); // 私有数据
		// category是新建的表名
		 sz_str= sp.getString("sz_str", "");
		 sz_two_str= sp.getString("sz_two_str", "");
		 sz_three_str= sp.getString("sz_three_str", "");
		 sz_four_str= sp.getString("sz_four_str", "");
		 cy_str= sp.getString("cy_str", "");
		 cy_two_str= sp.getString("cy_two_str", "");
		 cy_three_str= sp.getString("cy_three_str", "");
		 cy_four_str= sp.getString("cy_four_str", "");
		 shz_str= sp.getString("shz_str", "");
		 shz_two_str= sp.getString("shz_two_str", "");
		 shz_three_str= sp.getString("shz_three_str", "");
		 shz_four_str= sp.getString("shz_four_str", "");
	}

	private void setListeners() {
		img_back.setOnClickListener(this);
		img_share.setOnClickListener(this);
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
				pageIndex = 1;
				String[] property_va = new String[] {"10", pageIndex + ""};
				soapService.getListLive(property_va, false);
			}
		});
		// end of list
		listView_newslist.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				String[] property_va = new String[] { "10", ++pageIndex + "" };
				soapService.getListLive(property_va, true);

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
		case R.id.img_share:
			String content = "图文直播正在进行：与大盘同步实时解盘";
			ShareTool.getInstance(this,this, "股涨图文直播", content, SOAP_UTILS.HTTP_LIVESHARE_PATH,null).shareMsg(this);
			break;
		default:
			break;
		}
	}
	
	public void onEvent(SoapRes obj) {
		super.onEvent(obj);
		if (obj.getCode().equals(SOAP_UTILS.METHOD.GETLISTLIVE)) {
			listView_newslist.onRefreshComplete();
			if(obj.isPage()){
				for (LiveEntity bean : (List<LiveEntity>) obj.getObj()) {
					itemEntities.add(bean);
				}
				adapter.notifyDataSetChanged();
			}else{				
				itemEntities = (ArrayList<LiveEntity>)obj.getObj();
				if(itemEntities.size()!=0){					
					pageIndex = 1;
					adapter = new ListItemAdapter(this, itemEntities);
					listView.setAdapter(adapter);
				}
			}
		}
	}

	@SuppressLint("ResourceAsColor")
	class GetToken extends AsyncTask<Object, Object, Object> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object... params) {
            System.out.println(">>>>>");
            String url = "http://hq.sinajs.cn/list=s_sh000001,s_sz399001,s_sz399006";
    		HttpGet httpGet = new HttpGet(url);
    		HttpClient httpClient = new DefaultHttpClient();
    		// 发送请求
    		String result = "";
    		try {
    			HttpResponse response = httpClient.execute(httpGet);
    			result = EntityUtils.toString(response.getEntity(), "UTF_8");
    			return result;
    		} catch (Exception e) {
    			e.printStackTrace();
    			return null;
    		}
    		
    		
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
    		try {
    			
				String str = result.toString();	
				String[] array = str.split(";");
                String string1 = array[0];
                String string2 = array[1];
                String string3 = array[2];
                String newStr1 = string2.substring(24);
                String newStr2 = string3.substring(24);
                String newStr3 = string1.substring(23);
				
               String newarray1[] = newStr1.split(",");
               String newarray2[] = newStr2.split(",");
               String newarray3[] = newStr3.split(",");
               
         String str01 =  newarray1[0];     
         String str02 =  newarray1[1];     
         String str03 =  newarray1[2];     
         String str04 =  newarray1[3];   
         String str11 =  newarray2[0];     
         String str12 =  newarray2[1];     
         String str13 =  newarray2[2];     
         String str14 =  newarray2[3];
         String str21 =  newarray3[0];     
         String str22 =  newarray3[1];     
         String str23 =  newarray3[2];     
         String str24 =  newarray3[3];
         

  		Resources resources =(Resources)getBaseContext().getResources();
  		ColorStateList colorred = (ColorStateList)resources.getColorStateList(R.color.red);
  		ColorStateList colorgreen = (ColorStateList)resources.getColorStateList(R.color.green);
  		
        sz_tv.setText(str01);
        String[] b1 = str02.split("\\.");
     	String str02a = b1[0];
     	String str02b = b1[1];
     	sz_two_tv.setText(str02a + "." + str02b.substring(0,2));
     	if(str03.contains("-")){
     		sz_two_tv.setTextColor(colorgreen);
     		sz_three_tv.setTextColor(colorgreen);
     		sz_four_tv.setTextColor(colorgreen);
     	}else{
     		sz_two_tv.setTextColor(colorred);
     		sz_three_tv.setTextColor(colorred);
     		sz_four_tv.setTextColor(colorred);
     	}
     	
     	String[] a1 = str03.split("\\.");
     	String str03a = a1[0];
     	String str03b = a1[1];
     	sz_three_tv.setText(str03a + "." + str03b.substring(0,2));
     	sz_four_tv.setText(str04 + "%");
     	cy_tv.setText(str11);
     	String[] b2 = str12.split("\\.");
     	String str12a = b2[0];
     	String str12b = b2[1];
     	cy_two_tv.setText(str12a + "." + str12b.substring(0,2));
     	if(str13.contains("-")){
     		cy_two_tv.setTextColor(colorgreen);
     		cy_three_tv.setTextColor(colorgreen);
     		cy_four_tv.setTextColor(colorgreen);
     	}else{
     		cy_two_tv.setTextColor(colorred);
     		cy_three_tv.setTextColor(colorred);
     		cy_four_tv.setTextColor(colorred);
     	}
    	String[] a2 = str13.split("\\.");
     	String str13a = a2[0];
     	String str13b = a2[1];
     	cy_three_tv.setText(str13a + "." + str13b.substring(0,2));
     	cy_four_tv.setText(str14 + "%");
     	shz_tv.setText(str21);
     	String[] b3 = str22.split("\\.");
     	String str22a = b3[0];
     	String str22b = b3[1];
     	shz_two_tv.setText(str22a + "." + str22b.substring(0,2));
     	if(str23.contains("-")){
     		shz_two_tv.setTextColor(colorgreen);
     		shz_three_tv.setTextColor(colorgreen);
     		shz_four_tv.setTextColor(colorgreen);
     	}else{
     		shz_two_tv.setTextColor(colorred);
     		shz_three_tv.setTextColor(colorred);
     		shz_four_tv.setTextColor(colorred);
     	}
     	String[] a3 = str23.split("\\.");
     	String str23a = a3[0];
     	String str23b = a3[1];
     	shz_three_tv.setText(str23a + "." + str23b.substring(0,2));
     	shz_four_tv.setText(str24 + "%");
     	
   	 SharedPreferences sp = getSharedPreferences("homepagelive",Context.MODE_PRIVATE); // 私有数据 category是新建的表名
	    Editor editor = sp.edit();// 获取编辑器
	    editor.putString("sz_str", sz_str);
	    editor.putString("sz_two_str", sz_two_str);
	    editor.putString("sz_three_str", sz_three_str);
	    editor.putString("sz_four_str", sz_four_str);
	    editor.putString("cy_str", cy_str);
	    editor.putString("cy_two_str", cy_two_str);
	    editor.putString("cy_three_str", cy_three_str);
	    editor.putString("cy_four_str", cy_four_str);
	    editor.putString("shz_str", shz_str);
	    editor.putString("shz_two_str", shz_two_str);
	    editor.putString("shz_three_str", shz_three_str);
	    editor.putString("shz_four_str", shz_four_str);
	    editor.commit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Utils.showTextToast(context, "token获取失败");
				finish();
			}
        }
    }
	
	class stockDataRunnable implements Runnable {

		public void run() {
			// TODO Auto-generated method stub
			while (isRefreshing) {
				try {
					Thread.sleep(2000);

					new GetToken().execute();
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		isRefreshing = true;
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		isRefreshing = false;
	}

	@Override
	protected void onResume() {
		super.onResume();
		isRefreshing = true;
	};

	@Override
	protected void onPause() {
		super.onPause();
		isRefreshing = false;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		isRefreshing = false;
	}
	
}
