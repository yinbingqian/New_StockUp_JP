package com.sxit.activity.discuss.adapter;

import java.util.ArrayList;

import com.sxit.activity.discuss.Discuss_Reply_Activity;
import com.sxit.activity.main.ImagePagerActivity;
import com.sxit.entity.discuss.DiscussItem;
import com.sxit.instance.Instance;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.XCRoundImageView;
import com.sxit.utils.live.NoScrollGridAdapter;
import com.sxit.utils.live.NoScrollGridView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import lnpdit.lntv.tradingtime.R;

/**
 * 首页ListView的数据适配器
 * 
 * @author Administrator
 * 
 */
public class DiscussItemAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<DiscussItem> items;

	public DiscussItemAdapter(Context ctx, ArrayList<DiscussItem> items) {
		this.mContext = ctx;
		this.items = items;
	}

	@Override
	public int getCount() {
		return items == null ? 0 : items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(mContext, R.layout.item_discuss, null);
			holder.iv_avatar = (XCRoundImageView) convertView.findViewById(R.id.iv_avatar);
			holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
			holder.tv_label = (TextView) convertView.findViewById(R.id.tv_label);
			holder.tv_time = (TextView) convertView.findViewById(R.id.time_text);
			holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
			holder.gridview = (NoScrollGridView) convertView.findViewById(R.id.gridview);
			holder.tv_hot = (TextView) convertView.findViewById(R.id.hot_text);
			holder.tv_discuss = (TextView) convertView.findViewById(R.id.discuss_text);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final DiscussItem itemEntity = items.get(position);
		holder.tv_title.setText(itemEntity.getRealName());
		holder.tv_time.setText(itemEntity.getCrtime());
		holder.tv_content.setText(itemEntity.getContent());
		holder.tv_label.setText("[" + itemEntity.getColumn() + "]");
		holder.tv_hot.setText(itemEntity.getHot().equals("false")?"":"HOT");
		holder.tv_discuss.setText("威望  "+itemEntity.getMark() + "    赞  " + itemEntity.getRewardmark() + "    讨论  " + itemEntity.getReplynum());
		Instance.imageLoader.displayImage(itemEntity.getHeadPic(), holder.iv_avatar, Instance.user_s_options);
		
		final ArrayList<String> imageUrls = new ArrayList<String>();
		final ArrayList<String> imageUrls_thumbnail = new ArrayList<String>();
		if(!itemEntity.getImg1().equals("")){
			imageUrls.add(SOAP_UTILS.HTTP_DISCUSS_PATH + itemEntity.getImg1());
			imageUrls_thumbnail.add(SOAP_UTILS.HTTP_DISCUSS_PATH + itemEntity.getImgthumbnail1());
		}
		if(!itemEntity.getImg2().equals("")){
			imageUrls.add(SOAP_UTILS.HTTP_DISCUSS_PATH + itemEntity.getImg2());
			imageUrls_thumbnail.add(SOAP_UTILS.HTTP_DISCUSS_PATH + itemEntity.getImgthumbnail2());
		}
		holder.gridview.setTag(imageUrls);
		
		
		if (imageUrls == null || imageUrls.size() == 0) { // 没有图片资源就隐藏GridView
			holder.gridview.setVisibility(View.GONE);
			holder.gridview.setAdapter(null);
		} else {
			holder.gridview.setVisibility(View.VISIBLE);
			holder.gridview.setAdapter(new NoScrollGridAdapter(mContext, imageUrls_thumbnail));
		}
		// 点击回帖九宫格，查看大图
		holder.gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				imageBrower(position, imageUrls);
			}
		});
		convertView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(mContext, Discuss_Reply_Activity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("discuss", itemEntity);
				intent.putExtras(bundle);
				mContext.startActivity(intent);
			}
		});
		return convertView;
	}

	/**
	 * 打开图片查看器
	 * 
	 * @param position
	 * @param urls2
	 */
	protected void imageBrower(int position, ArrayList<String> urls2) {
		Intent intent = new Intent(mContext, ImagePagerActivity.class);
		// 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
		mContext.startActivity(intent);
	}

	/**
	 * listview组件复用，防止“卡顿”
	 * 
	 * @author Administrator
	 * 
	 */
	class ViewHolder {
		private XCRoundImageView iv_avatar;
		private TextView tv_title;
		private TextView tv_label;
		private TextView tv_content;
		private NoScrollGridView gridview;
		private TextView tv_time;
		private TextView tv_hot;
		private TextView tv_discuss;
	}
}
