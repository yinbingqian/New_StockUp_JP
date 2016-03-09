package com.sxit.activity.main.adapter;

import java.util.List;

import com.sxit.activity.login.Login_Activity;
import com.sxit.db.DBHelper;
import com.sxit.entity.DuelPoint;
import com.sxit.entity.UserInfo;
import com.sxit.http.ISoapService;
import com.sxit.http.SoapService;
import com.sxit.instance.Instance;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.XCRoundImageView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import lnpdit.lntv.tradingtime.R;

/**
 * info
 * 
 * @author huanyu 类名称：Information_Adapter 创建时间:2014-10-27 上午12:00:38
 */
public class DuelPoint_Adapter extends BaseAdapter implements OnClickListener {
	private Context context;
	private List<DuelPoint> list;
    public ISoapService soapService = new SoapService();

	public DuelPoint_Adapter(Context context, List<DuelPoint> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_duel_point, null);
			viewHolder = new ViewHolder();
			viewHolder.news_headpic = (XCRoundImageView) convertView.findViewById(R.id.news_headpic);
			viewHolder.news_name = (TextView) convertView.findViewById(R.id.news_name);
			viewHolder.news_point = (TextView) convertView.findViewById(R.id.news_point);
			viewHolder.news_content = (TextView) convertView.findViewById(R.id.news_content);
			viewHolder.news_support = (TextView) convertView.findViewById(R.id.news_support);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Instance.imageLoader.displayImage(SOAP_UTILS.HTTP_HEAD_PATH + list.get(position).getHeadPic(),
				viewHolder.news_headpic, Instance.user_s_options);
		viewHolder.news_name.setText(list.get(position).getRealName());
		String viewpoint = list.get(position).getViewpoint();
		if(viewpoint.equals("up")){			
			viewHolder.news_point.setText("观点：看涨");
		} else {
			viewHolder.news_point.setText("观点：看跌");
		}
		viewHolder.news_content.setText(list.get(position).getContent());
		viewHolder.news_support.setText("支持：" + list.get(position).getSupportrate());
		viewHolder.news_support.setOnClickListener(this);
        viewHolder.news_support.setTag(""+position);
        viewHolder.news_support.setClickable(true);
		return convertView;
	}

	static class ViewHolder {
		public XCRoundImageView news_headpic;//
		public TextView news_name;//
		public TextView news_point;//
		public TextView news_content;//
		public TextView news_support;//
	}
	
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
        case R.id.news_support:
        	DBHelper dbh = new DBHelper(context);
        	List<UserInfo> userlist = dbh.queryUserInfo();
        	if(userlist.size()>0){        		
        		int position = Integer.valueOf(v.getTag().toString());
        		String[] property_va = new String[] { userlist.get(0).getid(), list.get(position).getId()};
        		soapService.viewPointSupport(property_va, position);
        	}else{
        		Intent intent = new Intent();
        		intent.setClass(context, Login_Activity.class);
        		context.startActivity(intent);
        	}
            break;
        default:
            break;
        }
    }
}
