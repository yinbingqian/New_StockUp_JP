package com.sxit.activity.discuss.adapter;

import java.util.List;

import com.sxit.entity.discuss.DiscussTag;

import lnpdit.lntv.tradingtime.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * info
 * 
 * @author huanyu 类名称：Information_Adapter 创建时间:2014-10-27 上午12:00:38
 */
public class DiscussSearch_Adapter extends BaseAdapter {
	private Context context;
	private List<DiscussTag> list;

	public DiscussSearch_Adapter(Context context, List<DiscussTag> list) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.newtagview, null);
			viewHolder = new ViewHolder();
			viewHolder.tag_btn = (TextView) convertView.findViewById(R.id.tag_btn);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tag_btn.setText(list.get(position).getTitle());
		return convertView;
	}

	static class ViewHolder {
		public TextView tag_btn;//
	}
}
