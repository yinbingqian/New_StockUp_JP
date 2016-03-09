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
import com.sxit.instance.Instance;
import com.sxit.utils.SOAP_UTILS;

/**
 * 特邀嘉宾
 * 
 * @author huanyu 类名称：Information_Adapter 创建时间:2014-10-27 上午12:00:38
 */
public class ExpertList_Adapter extends BaseAdapter {
	private Context context;
	private List<Expert> list;

	public ExpertList_Adapter(Context context, List<Expert> list) {
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
					R.layout.item_expert, null);
			viewHolder = new ViewHolder();
			viewHolder.name = (TextView) convertView
					.findViewById(R.id.expert_name);
			viewHolder.intro = (TextView) convertView
					.findViewById(R.id.expert_content);
			viewHolder.head_img = (ImageView) convertView
					.findViewById(R.id.expert_headpic);
			viewHolder.mark = (TextView) convertView
					.findViewById(R.id.expert_mark);
			viewHolder.rewardmark = (TextView) convertView
					.findViewById(R.id.expert_rewardmark);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		// Instance.imageLoader.displayImage(list.get(position).getThumbnail(),
		// viewHolder.news_img, Instance.new_s_options);
		Instance.imageLoader.displayImage(
				SOAP_UTILS.HTTP_HEAD_PATH + list.get(position).getheadpic(),
				viewHolder.head_img, Instance.user_s_options);
		viewHolder.name.setText(list.get(position).getrealname());
		// viewHolder.intro.setTextColor(context.getResources().getColor(R.color.text_color));
		viewHolder.intro.setText(list.get(position).getstockstyle());
		viewHolder.mark.setText(list.get(position).getmark());
		viewHolder.rewardmark.setText(list.get(position).getrewardmark());
		return convertView;
	}

	static class ViewHolder {
		public TextView name;
		public TextView intro;
		public ImageView head_img;
		public TextView mark;
		public TextView rewardmark;
	}
}
