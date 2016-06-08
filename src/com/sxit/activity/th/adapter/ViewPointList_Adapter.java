package com.sxit.activity.th.adapter;

import java.util.List;

import lnpdit.lntv.tradingtime.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sxit.activity.th.adapter.NowLivingList_Adapter.ViewHolder;
import com.sxit.entity.smarter.Guest;
import com.sxit.entity.smarter.GuestDetail;
import com.sxit.instance.Instance;
import com.sxit.utils.SOAP_UTILS;

/**
 * 特邀嘉宾
 * 
 * @author huanyu 类名称：Information_Adapter 创建时间:2014-10-27 上午12:00:38
 */
public class ViewPointList_Adapter extends BaseAdapter {
	private Context context;
	private List<GuestDetail> list;
	String UserPic;

	public ViewPointList_Adapter(Context context, List<GuestDetail> list, String userPic) {
		this.context = context;
		this.list = list;
		this.UserPic = userPic;
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
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_viewpoint, null);
			viewHolder = new ViewHolder();
			viewHolder.content = (TextView) convertView
					.findViewById(R.id.expert_content);
			viewHolder.name = (TextView) convertView
					.findViewById(R.id.expert_name);
			viewHolder.head_img = (ImageView) convertView
					.findViewById(R.id.expert_headpic);
			viewHolder.time_tv = (TextView) convertView
					.findViewById(R.id.time_tv);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		Instance.imageLoader.displayImage(UserPic,
				viewHolder.head_img, Instance.user_s_options);
		viewHolder.name.setText(list.get(position).gettitle());
		// viewHolder.intro.setTextColor(context.getResources().getColor(R.color.text_color));
		viewHolder.content.setText(list.get(position).getsource());
		viewHolder.time_tv.setText(list.get(position).getcrtime());
		return convertView;
	}

	static class ViewHolder {
		public TextView name;
		public TextView content;
		public ImageView head_img;
		public TextView time_tv;
	}
}
