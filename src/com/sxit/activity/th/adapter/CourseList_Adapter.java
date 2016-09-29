package com.sxit.activity.th.adapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.sxit.activity.login.Login_Activity;
import com.sxit.activity.th.adapter.AllLivingList_Adapter.ButtonListener;
import com.sxit.activity.th.living.OnlineVideoActivity;
import com.sxit.db.DBHelper;
import com.sxit.entity.living.Course;
import com.sxit.http.ISoapService;
import com.sxit.http.SoapService;
import com.sxit.instance.Instance;
import com.sxit.utils.SOAP_UTILS;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import lnpdit.lntv.tradingtime.R;

/**
 * 特邀嘉宾
 * 
 * @author huanyu 类名称：Information_Adapter 创建时间:2014-10-27 上午12:00:38
 */
public class CourseList_Adapter extends BaseAdapter {
	private Context mcontext;
	private List<Course> list;
	String LiveUserId = "";
	String Id = "";
	String CourseId = "";
	String url = "";
	String roomid = "";
	String password = "";
	String username = "";
	String realname = "";
	public ISoapService soapService = new SoapService();

	public CourseList_Adapter(Context context, List<Course> list, String LiveUserId) {
		this.mcontext = context;
		this.list = list;
		this.LiveUserId = LiveUserId;
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
			convertView = LayoutInflater.from(mcontext).inflate(R.layout.item_course, null);
			viewHolder = new ViewHolder();
			viewHolder.kkrq = (TextView) convertView.findViewById(R.id.kkrq_text);
			viewHolder.kksj = (TextView) convertView.findViewById(R.id.kksj_text);
			viewHolder.mqrs = (TextView) convertView.findViewById(R.id.mq_text);
			viewHolder.type = (TextView) convertView.findViewById(R.id.type_text);
			viewHolder.head_img = (ImageView) convertView.findViewById(R.id.course_headpic);
			viewHolder.jinru = (Button) convertView.findViewById(R.id.btn_jinru);
			viewHolder.wks = (Button) convertView.findViewById(R.id.btn_wks);
			viewHolder.yjs = (Button) convertView.findViewById(R.id.btn_yjs);
			viewHolder.djhf = (Button) convertView.findViewById(R.id.btn_djhf);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		Instance.imageLoader.displayImage(SOAP_UTILS.HTTP_HEAD_PATH + list.get(position).getLiveHeadPic(),
				viewHolder.head_img, Instance.new_s_options);
		String StimeStr = list.get(position).getStimeStr();
		String Sdate = getDateHm(StimeStr, "Date");
		String Shm = getDateHm(StimeStr, "Hm");
		String EtimeStr = list.get(position).getEtimeStr();
		String Ehm = getDateHm(EtimeStr, "Hm");
		viewHolder.kkrq.setText(Sdate);
		viewHolder.kksj.setText(Shm + "-" + Ehm);
		viewHolder.mqrs.setText("目前:" + list.get(position).getOnlineCount() + "人");
		String type = list.get(position).getCourseType();
		if (type.equals("网络课")) {
			viewHolder.type.setText("网络课");
		} else if (type.equals("公开课")) {
			viewHolder.type.setText("公开课");
		}
		String status = list.get(position).getCcLiveTimetablesStatus();
		if (status.equals("1")) {
			viewHolder.jinru.setVisibility(Button.VISIBLE);
			viewHolder.wks.setVisibility(Button.GONE);
			viewHolder.yjs.setVisibility(Button.GONE);
			viewHolder.djhf.setVisibility(Button.GONE);
			viewHolder.mqrs.setVisibility(TextView.VISIBLE);
			viewHolder.type.setVisibility(TextView.VISIBLE);
		} else if (status.equals("2")) {
			viewHolder.jinru.setVisibility(Button.GONE);
			viewHolder.wks.setVisibility(Button.GONE);
			viewHolder.yjs.setVisibility(Button.VISIBLE);
			viewHolder.djhf.setVisibility(Button.VISIBLE);
			viewHolder.mqrs.setVisibility(TextView.GONE);
			viewHolder.type.setVisibility(TextView.GONE);
		} else if (status.equals("3")) {
			viewHolder.jinru.setVisibility(Button.GONE);
			viewHolder.wks.setVisibility(Button.VISIBLE);
			viewHolder.yjs.setVisibility(Button.GONE);
			viewHolder.djhf.setVisibility(Button.GONE);
			viewHolder.mqrs.setVisibility(TextView.GONE);
			viewHolder.type.setVisibility(TextView.GONE);
		} else if (status.equals("4")) {
			viewHolder.jinru.setVisibility(Button.GONE);
			viewHolder.wks.setVisibility(Button.GONE);
			viewHolder.yjs.setVisibility(Button.VISIBLE);
			viewHolder.djhf.setVisibility(Button.GONE);
			viewHolder.mqrs.setVisibility(TextView.GONE);
			viewHolder.type.setVisibility(TextView.GONE);
		}
		viewHolder.jinru.setOnClickListener(new ButtonListener(position));
		viewHolder.djhf.setOnClickListener(new ButtonListener(position));
		return convertView;
	}

	class ButtonListener implements OnClickListener {
		private int position;

		public ButtonListener(int pos) {
			// TODO Auto-generated constructor stub
			position = pos;

		}

		@Override
		public void onClick(View v) {
			DBHelper dbh = new DBHelper(mcontext);
			if (dbh.queryUserInfo().size() > 0) {
				CourseId = list.get(position).getId();
				roomid = list.get(position).getLiveRoomId();
				String type = list.get(position).getCourseType();

				SharedPreferences sharedPreferences = mcontext.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
				password = sharedPreferences.getString("password", "");
				username = sharedPreferences.getString("username", "");
				realname = sharedPreferences.getString("realname", "");

				if (type.equals("网络课")) {
					url = "http://view.csslcloud.net/api/view/index?roomid=" + roomid
							+ "&userid=4E4064DCDE2BD0B8&autoLogin=true&viewername=app," + CourseId + "," + username
							+ "&viewertoken=" + password;
				} else if (type.equals("公开课")) {
					url = "http://view.csslcloud.net/api/view/index?roomid=" + roomid
							+ "&userid=4E4064DCDE2BD0B8&autoLogin=true&viewername=" + realname;
				}

				Intent intent = new Intent();
				intent.putExtra("url", url);
				intent.setClass(mcontext, OnlineVideoActivity.class);
				mcontext.startActivity(intent);

			} else {
				Intent intent_login = new Intent();
				intent_login.setClass(mcontext, Login_Activity.class);
				mcontext.startActivity(intent_login);
			}
		}
	}

	private String getDateHm(String timeStr, String type) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
			Date date = sdf.parse(timeStr);
			if (type.endsWith("Date")) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String parse = dateFormat.format(date);
				return parse;
			} else {
				DateFormat dateFormat = new SimpleDateFormat("HH:mm");
				String parse = dateFormat.format(date);
				return parse;

			}
		} catch (Exception e) {
			return "";
		}
	}

	static class ViewHolder {
		public TextView kkrq;
		public TextView kksj;
		public ImageView head_img;
		public TextView mqrs;
		public TextView type;
		public Button jinru;
		public Button wks;
		public Button yjs;
		public Button djhf;
	}
}
