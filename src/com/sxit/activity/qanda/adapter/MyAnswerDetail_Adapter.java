package com.sxit.activity.qanda.adapter;

import java.util.ArrayList;
import java.util.List;

import com.sxit.activity.main.ImagePagerActivity;
import com.sxit.entity.qanda.QuestionDetail;
import com.sxit.http.ISoapService;
import com.sxit.http.SoapService;
import com.sxit.instance.Instance;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.live.NoScrollGridAdapter;
import com.sxit.utils.live.NoScrollGridView;

import lnpdit.lntv.tradingtime.R;
import android.R.string;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 我的解答详情
 * 
 * @author huanyu 类名称：Information_Adapter 创建时间:2014-10-27 上午12:00:38
 */
public class MyAnswerDetail_Adapter extends BaseAdapter {
	private Context context;
	private List<QuestionDetail> list;
	public ISoapService soapService = new SoapService();

	public MyAnswerDetail_Adapter(Context context, List<QuestionDetail> list) {
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
					R.layout.item_questiondetail, null);
			viewHolder = new ViewHolder();
			viewHolder.name = (TextView) convertView
					.findViewById(R.id.questiondetail_name);
			viewHolder.headpic = (ImageView) convertView
					.findViewById(R.id.questiondetail_headpic);
			viewHolder.content = (TextView) convertView
					.findViewById(R.id.questiondetail_content);
			viewHolder.time = (TextView) convertView
					.findViewById(R.id.questiondetail_time);
			viewHolder.btn = (Button) convertView
					.findViewById(R.id.questiondetail_btn);
			viewHolder.gridview = (NoScrollGridView) convertView
					.findViewById(R.id.questiondetail_gridview);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.name.setText(list.get(position).getrealname());
		Instance.imageLoader.displayImage(
				SOAP_UTILS.HTTP_HEAD_PATH + list.get(position).getheadpic(),
				viewHolder.headpic, Instance.user_s_options);
		viewHolder.content.setText(list.get(position).getcontent());
		viewHolder.time.setText(list.get(position).getcrtime());
		String useful = list.get(position).getuseful();

		final Button ansBtn = viewHolder.btn;
		if (useful.equals("1")) {
			ansBtn.setText("已经采纳");
			ansBtn.setEnabled(false);
			ansBtn.setBackgroundResource(R.color.grey);
		} else {
			ansBtn.setText("进行中...");
			ansBtn.setEnabled(false);
		}

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
		public TextView content;
		public TextView time;
		public Button btn;
		private NoScrollGridView gridview;
	}
}
