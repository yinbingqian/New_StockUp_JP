package com.sxit.activity.qanda.adapter;

import java.util.ArrayList;
import java.util.List;

import com.sxit.activity.main.ImagePagerActivity;
import com.sxit.activity.qanda.QuestionDetail_Activity;
import com.sxit.entity.qanda.Question;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.live.NoScrollGridAdapter;
import com.sxit.utils.live.NoScrollGridView;

import lnpdit.lntv.tradingtime.R;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 我的提问
 * 
 * @author huanyu 类名称：Information_Adapter 创建时间:2014-10-27 上午12:00:38
 */
public class QuestionList_Adapter extends BaseAdapter {
	private Context context;
	private List<Question> list;

	public QuestionList_Adapter(Context context, List<Question> list) {
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_question, null);
			viewHolder = new ViewHolder();
			viewHolder.title = (TextView) convertView
					.findViewById(R.id.question_title);
			viewHolder.content = (TextView) convertView
					.findViewById(R.id.question_content);
			viewHolder.time = (TextView) convertView
					.findViewById(R.id.question_time);
			viewHolder.result = (TextView) convertView
					.findViewById(R.id.question_result);
			viewHolder.gridview = (NoScrollGridView) convertView
					.findViewById(R.id.question_gridview);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.title.setText(list.get(position).gettitle());
		// viewHolder.intro.setTextColor(context.getResources().getColor(R.color.text_color));
		viewHolder.content.setText("问题描述：" + list.get(position).getcontent());
		viewHolder.time.setText(list.get(position).getcrtime());
		viewHolder.result.setText("共有" + list.get(position).getanswernum()
				+ "个解答");
		String img1 = list.get(position).getimg1();
		String imgthumbnail1 = list.get(position).getimgthumbnail1();
		String img2 = list.get(position).getimg2();
		String imgthumbnail2 = list.get(position).getimgthumbnail2();
		final ArrayList<String> imgUrls = new ArrayList<String>();
		final ArrayList<String> imgthumbnailUrls = new ArrayList<String>();
		if (!imgthumbnail1.equals("")) {
			imgUrls.add(SOAP_UTILS.HTTP_DISCUSS_PATH + img1);
			imgthumbnailUrls.add(SOAP_UTILS.HTTP_DISCUSS_PATH + imgthumbnail1);
		}
		if (!imgthumbnail2.equals("")) {
			imgUrls.add(SOAP_UTILS.HTTP_DISCUSS_PATH + img2);
			imgthumbnailUrls.add(SOAP_UTILS.HTTP_DISCUSS_PATH + imgthumbnail2);
		}
		if (imgthumbnailUrls == null || imgthumbnailUrls.size() == 0) { // 没有图片资源就隐藏GridView
			viewHolder.gridview.setVisibility(View.GONE);
		} else {
			if (imgthumbnailUrls.size() == 1) {
			}
			viewHolder.gridview.setVisibility(View.VISIBLE);
			viewHolder.gridview.setAdapter(new NoScrollGridAdapter(context,
					imgthumbnailUrls));
		}

		viewHolder.gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				imageBrower(position, imgUrls);
			}
		});

		// 点击事件
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent.setClass(context, QuestionDetail_Activity.class);
				intent.putExtra("id", list.get(position).getwebid());
				intent.putExtra("title", list.get(position).gettitle());
				intent.putExtra("content", list.get(position).getcontent());
				intent.putExtra("time", list.get(position).getcrtime());
				intent.putExtra("type", list.get(position).getcolumn());
				intent.putExtra("img1", list.get(position).getimg1());
				intent.putExtra("img2", list.get(position).getimg2());
				intent.putExtra("imgthumbnail1", list.get(position)
						.getimgthumbnail1());
				intent.putExtra("imgthumbnail2", list.get(position)
						.getimgthumbnail2());
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
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
		public TextView title;
		public TextView content;
		public TextView time;
		public TextView result;
		private NoScrollGridView gridview;
	}
}
