package com.sxit.activity.qanda.adapter;

import java.util.ArrayList;
import java.util.List;

import com.sxit.activity.main.ImagePagerActivity;
import com.sxit.activity.qanda.MyAnswer_Edit_Activity;
import com.sxit.activity.qanda.QuestionDetail_Activity;
import com.sxit.entity.qanda.Answer;
import com.sxit.instance.Instance;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.live.NoScrollGridAdapter;
import com.sxit.utils.live.NoScrollGridView;

import lnpdit.lntv.tradingtime.R;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 我的回答
 * 
 * @author huanyu 类名称：Information_Adapter 创建时间:2014-10-27 上午12:00:38
 */
public class AnswerList_Adapter extends BaseAdapter {
	private Context context;
	private List<Answer> list;

	public AnswerList_Adapter(Context context, List<Answer> list) {
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
					R.layout.item_answer, null);
			viewHolder = new ViewHolder();
			viewHolder.name = (TextView) convertView
					.findViewById(R.id.answer_name);
			viewHolder.headpic = (ImageView) convertView
					.findViewById(R.id.answer_headpic);
			viewHolder.title = (TextView) convertView
					.findViewById(R.id.answer_title);
			viewHolder.content = (TextView) convertView
					.findViewById(R.id.answer_content);
			viewHolder.time = (TextView) convertView
					.findViewById(R.id.answer_time);
			viewHolder.btn = (Button) convertView.findViewById(R.id.answer_btn);
			viewHolder.gridview = (NoScrollGridView) convertView
					.findViewById(R.id.answer_gridview);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		final Answer answer = list.get(position);

		viewHolder.name.setText(list.get(position).getrealname());
		Instance.imageLoader.displayImage(
				SOAP_UTILS.HTTP_HEAD_PATH + list.get(position).getheadpic(),
				viewHolder.headpic, Instance.user_s_options);
		viewHolder.title.setText(list.get(position).gettitle());
		// viewHolder.intro.setTextColor(context.getResources().getColor(R.color.text_color));
		viewHolder.content.setText("问题描述：" + list.get(position).getcontent());
		viewHolder.time.setText(list.get(position).getcrtime() + "    ["
				+ list.get(position).getcolumn() + "]");

		final Button ansBtn = viewHolder.btn;

		ansBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 跳转到解答页面
			}
		});

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
		viewHolder.btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(context, MyAnswer_Edit_Activity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("answer", answer);
				intent.putExtras(bundle);
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
		public TextView name;
		public ImageView headpic;
		public TextView title;
		public TextView content;
		public TextView time;
		public Button btn;
		private NoScrollGridView gridview;
	}
}