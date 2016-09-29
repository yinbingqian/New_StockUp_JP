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

import com.sxit.entity.smarter.Expert;
import com.sxit.entity.smarter.ExpertDetail;
import com.sxit.instance.Instance;
import com.sxit.utils.SOAP_UTILS;

/**
 * 特邀嘉宾
 * 
 * @author huanyu 类名称：Information_Adapter 创建时间:2014-10-27 上午12:00:38
 */
public class ExpertDetailList_Adapter extends BaseAdapter {
	private Context context;
	private List<ExpertDetail> list;

	public ExpertDetailList_Adapter(Context context, List<ExpertDetail> list) {
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
					R.layout.item_expertdetail, null);
			viewHolder = new ViewHolder();
			viewHolder.content = (TextView) convertView
					.findViewById(R.id.expertdiscuss_content);
			viewHolder.time = (TextView) convertView
					.findViewById(R.id.expertdiscuss_time);
			viewHolder.up = (TextView) convertView
					.findViewById(R.id.expertdiscuss_reward);
			viewHolder.down = (TextView) convertView
					.findViewById(R.id.expertdiscuss_down);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.content.setText(list.get(position).getcontent());
		viewHolder.time.setText(list.get(position).getcrtime());
		viewHolder.up.setText(list.get(position).getrewardmark() + "顶");
		viewHolder.down.setText(list.get(position).getdownmark() + "踩");
		return convertView;
	}

	static class ViewHolder {
		public TextView content;
		public TextView time;
		public TextView up;
		public TextView down;
	}
}
