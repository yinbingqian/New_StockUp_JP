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

import com.sxit.activity.th.item.bean.Information_ListBean;
import com.sxit.entity.HomePageNews;
import com.sxit.entity.news.FinNews;
import com.sxit.instance.Instance;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.XCRoundImageView;

/**
 * info
 * 
 * @author huanyu 类名称：Information_Adapter 创建时间:2014-10-27 上午12:00:38
 */
public class HomePageNews_Adapter extends BaseAdapter {
	private Context context;
	private List<HomePageNews> list;

	public HomePageNews_Adapter(Context context, List<HomePageNews> list) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_homepage_news, null);
			viewHolder = new ViewHolder();
			viewHolder.news_headpic = (XCRoundImageView) convertView.findViewById(R.id.news_headpic);
			viewHolder.news_name = (TextView) convertView.findViewById(R.id.news_name);
			viewHolder.news_org = (TextView) convertView.findViewById(R.id.news_org);
			viewHolder.news_title = (TextView) convertView.findViewById(R.id.news_title);
			viewHolder.news_content = (TextView) convertView.findViewById(R.id.news_content);
			viewHolder.news_time = (TextView) convertView.findViewById(R.id.news_time);
			viewHolder.news_tag = (TextView) convertView.findViewById(R.id.news_tag);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Instance.imageLoader.displayImage(SOAP_UTILS.HTTP_HEAD_PATH + list.get(position).getHeadPic(),
				viewHolder.news_headpic, Instance.user_s_options);
		viewHolder.news_name.setText(list.get(position).getRealName());
		viewHolder.news_org.setText(list.get(position).getOrgName());
		viewHolder.news_title.setText(list.get(position).getTitle());
		viewHolder.news_content.setText(list.get(position).getSource());
		viewHolder.news_time.setText(list.get(position).getCrtime());
		viewHolder.news_tag.setText(list.get(position).getColtitle());
		return convertView;
	}

	static class ViewHolder {
		public XCRoundImageView news_headpic;//
		public TextView news_name;//
		public TextView news_org;//
		public TextView news_title;//
		public TextView news_content;//
		public TextView news_time;//
		public TextView news_tag;//
	}
}
