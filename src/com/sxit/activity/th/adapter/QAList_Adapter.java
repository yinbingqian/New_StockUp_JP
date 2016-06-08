package com.sxit.activity.th.adapter;

import java.util.ArrayList;
import java.util.List;

import com.sxit.activity.main.ImagePagerActivity;
import com.sxit.entity.living.QA;
import com.sxit.instance.Instance;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.XCRoundImageView;
import com.sxit.utils.live.NoScrollGridAdapter;
import com.sxit.utils.live.NoScrollGridView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import lnpdit.lntv.tradingtime.R;

/**
 * 特邀嘉宾
 * 
 * @author huanyu 类名称：Information_Adapter 创建时间:2014-10-27 上午12:00:38
 */
public class QAList_Adapter extends BaseAdapter {
	private Context context;
	private List<QA> list;
	String AnswpicImg1 = "";
	 String AnswpicImg2 = "";
	 String AnswpicImgthumbnail1 = "";
	 String AnswpicImgthumbnail2 = "";

	public QAList_Adapter(Context context, List<QA> list) {
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
					R.layout.item_qa, null);
			viewHolder = new ViewHolder();
			viewHolder.gridview = (NoScrollGridView) convertView.findViewById(R.id.gridview);
			viewHolder.name_tv = (TextView) convertView
					.findViewById(R.id.name_tv);
			viewHolder.question_tv = (TextView) convertView
					.findViewById(R.id.question_tv);
			viewHolder.qcontent_tv = (TextView) convertView
					.findViewById(R.id.qcontent_tv);
			viewHolder.time_tv = (TextView) convertView
					.findViewById(R.id.time_tv);
			viewHolder.qdetail_tv = (TextView) convertView
					.findViewById(R.id.qdetail_tv);
			viewHolder.acontent_tv = (TextView) convertView
					.findViewById(R.id.acontent_tv);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.name_tv.setText(list.get(position).getQuUserName());
		// viewHolder.intro.setTextColor(context.getResources().getColor(R.color.text_color));
		viewHolder.qcontent_tv.setText(list.get(position).getTitle());
		viewHolder.time_tv.setText(list.get(position).getCrtime());
		viewHolder.qdetail_tv.setText("详细描述：" + list.get(position).getQuContent());
		viewHolder.acontent_tv.setText(list.get(position).getAnswer());
		//获取图片
		  AnswpicImg1 = list.get(position).getAnswpicImg1();
		  AnswpicImg2 = list.get(position).getAnswpicImg2();
		  AnswpicImgthumbnail1 = list.get(position).getAnswpicImgthumbnail1();
		  AnswpicImgthumbnail2 = list.get(position).getAnswpicImgthumbnail2();
		final ArrayList<String> imageUrls = new ArrayList<String>();
		if(AnswpicImg2!=null){
			imageUrls.add(SOAP_UTILS.HTTP_DISCUSS_PATH + AnswpicImg1);
			imageUrls.add(SOAP_UTILS.HTTP_DISCUSS_PATH + AnswpicImg2);
			}else if (AnswpicImg1!=null){

				imageUrls.add(SOAP_UTILS.HTTP_DISCUSS_PATH + AnswpicImg1);
			}
		final ArrayList<String> Imgthumbnail= new ArrayList<String>();
    
		if(AnswpicImgthumbnail2!=null){
		Imgthumbnail.add(SOAP_UTILS.HTTP_DISCUSS_PATH + AnswpicImgthumbnail1);
		Imgthumbnail.add(SOAP_UTILS.HTTP_DISCUSS_PATH + AnswpicImgthumbnail2);
		}else if (AnswpicImgthumbnail1!=null){

			Imgthumbnail.add(SOAP_UTILS.HTTP_DISCUSS_PATH + AnswpicImgthumbnail1);
		}
		viewHolder.gridview.setTag(Imgthumbnail);
		if (Imgthumbnail == null || Imgthumbnail.size() == 0) { // 没有图片资源就隐藏GridView
			viewHolder.gridview.setVisibility(View.GONE);
			viewHolder.gridview.setAdapter(null);
		} else{
			viewHolder.gridview.setVisibility(View.VISIBLE);
			viewHolder.gridview.setAdapter(new NoScrollGridAdapter(context, Imgthumbnail));
		}
		// 点击回帖九宫格，查看大图
		viewHolder.gridview.setOnItemClickListener(new OnItemClickListener() {

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
		Intent intent = new Intent(context, ImagePagerActivity.class);
		// 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	
	static class ViewHolder {
		public TextView name_tv;
		public TextView question_tv;
		public TextView qcontent_tv;
		public TextView time_tv;
		public TextView qdetail_tv;
		public TextView acontent_tv;
		private NoScrollGridView gridview;
	}
}
