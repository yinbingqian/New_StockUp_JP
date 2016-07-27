package com.sxit.activity.smarter.adapter;

import java.util.List;

import lnpdit.lntv.tradingtime.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sxit.entity.smarter.Guest;
import com.sxit.entity.smarter.GuestDetail;
import com.sxit.instance.Instance;
import com.sxit.utils.SOAP_UTILS;

/**
 * 特邀嘉宾
 * 
 * @author huanyu 类名称：Information_Adapter 创建时间:2014-10-27 上午12:00:38
 */
public class GuestDetailList_Adapter extends BaseAdapter {
	private Context context;
	private List<GuestDetail> list;

	public GuestDetailList_Adapter(Context context, List<GuestDetail> list) {
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
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_guestdetail, null);
			viewHolder = new ViewHolder();
			viewHolder.title = (TextView) convertView
					.findViewById(R.id.guestdetail_title);
			viewHolder.content = (TextView) convertView
					.findViewById(R.id.guestdetail_content);
			viewHolder.time = (TextView) convertView
					.findViewById(R.id.guestdetail_time);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.title.setText(list.get(position).gettitle());
		// viewHolder.intro.setTextColor(context.getResources().getColor(R.color.text_color));
		viewHolder.content.setText(list.get(position).getsource());
		viewHolder.time.setText(list.get(position).getcrtime());
		return convertView;
	}

	static class ViewHolder {
		public TextView title;
		public TextView content;
		public TextView time;
	}
}
