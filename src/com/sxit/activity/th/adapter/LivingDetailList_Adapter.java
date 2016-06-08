package com.sxit.activity.th.adapter;

import java.util.ArrayList;

import org.json.JSONObject;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sxit.activity.main.ImagePagerActivity;
import com.sxit.entity.LiveEntity;
import com.sxit.entity.living.LiveHall;
import com.sxit.entity.living.LivingDetail;
import com.sxit.instance.Instance;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.XCRoundImageView;
import com.sxit.utils.live.NoScrollGridAdapter;
import com.sxit.utils.live.NoScrollGridView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap.Config;
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
public class LivingDetailList_Adapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<LiveHall> items;

	String picImg = "";
	String picImgthumbnail = "";
	public LivingDetailList_Adapter(Context ctx, ArrayList<LiveHall> items) {
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
			convertView = View.inflate(mContext, R.layout.item_livehalllist, null);
			holder.iv_avatar = (XCRoundImageView) convertView.findViewById(R.id.iv_avatar);
			holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
			holder.tv_time = (TextView) convertView.findViewById(R.id.time_text);
			holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
			holder.gridview = (NoScrollGridView) convertView.findViewById(R.id.gridview);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		LiveHall itemEntity = items.get(position);
		holder.tv_title.setText(itemEntity.getRealName());
		holder.tv_time.setText(itemEntity.getCrtimeStr());
		holder.tv_content.setText(itemEntity.getContent());
		Instance.imageLoader.displayImage(itemEntity.getHeadPic(), holder.iv_avatar, Instance.user_s_options);
		//获取图片
		  picImg = itemEntity.getPicPic();
		  picImgthumbnail =  itemEntity.getPicThumbnail();
		final ArrayList<String> imageUrls = new ArrayList<String>();
		final ArrayList<String> imagethumbnailUrls = new ArrayList<String>();
		if(picImg!=null){
		imageUrls.add(SOAP_UTILS.HTTP_DISCUSS_PATH + picImg);
		}
		if(picImgthumbnail!=null){
		imagethumbnailUrls.add(SOAP_UTILS.HTTP_DISCUSS_PATH + picImgthumbnail);
		}
		holder.gridview.setTag(imagethumbnailUrls);
		if (imageUrls == null || imageUrls.size() == 0) { // 没有图片资源就隐藏GridView
			holder.gridview.setVisibility(View.GONE);
			holder.gridview.setAdapter(null);
		} else {
			holder.gridview.setVisibility(View.VISIBLE);
			holder.gridview.setAdapter(new NoScrollGridAdapter(mContext, imagethumbnailUrls));
		}
		// 点击回帖九宫格，查看大图
		holder.gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				imageBrower(position, imageUrls);
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
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
		private TextView tv_content;
		private TextView tv_time;
		private NoScrollGridView gridview;
	}
}
