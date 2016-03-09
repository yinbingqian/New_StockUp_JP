package com.sxit.activity.discuss.adapter;

import java.util.ArrayList;

import com.sxit.activity.discuss.Discuss_ReplyOther_Activity;
import com.sxit.activity.login.Login_Activity;
import com.sxit.db.DBHelper;
import com.sxit.entity.discuss.DiscussReply;
import com.sxit.instance.Instance;
import com.sxit.utils.XCRoundImageView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import lnpdit.lntv.tradingtime.R;

/**
 * 首页ListView的数据适配器
 * 
 * @author Administrator
 * 
 */
public class DiscussReplyAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<DiscussReply> items;
	private String hostid;

	public DiscussReplyAdapter(Context ctx, ArrayList<DiscussReply> items, String hostid) {
		this.mContext = ctx;
		this.items = items;
		this.hostid = hostid;
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
			convertView = View.inflate(mContext, R.layout.item_discuss_reply, null);
			holder.iv_avatar = (XCRoundImageView) convertView.findViewById(R.id.iv_avatar);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			holder.tv_label = (TextView) convertView.findViewById(R.id.tv_label);
			holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
			holder.bt_reply = (Button) convertView.findViewById(R.id.bt_reply);
			holder.tv_floor = (TextView) convertView.findViewById(R.id.tv_floor);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final DiscussReply itemEntity = items.get(position);
		holder.tv_name.setText(itemEntity.getRealName());
		holder.tv_time.setText(itemEntity.getCrtime());
		holder.tv_content.setText(itemEntity.getContent());
		holder.tv_label.setText("威望：+"+itemEntity.getMark());
		holder.tv_floor.setText(itemEntity.getFloor());
		Instance.imageLoader.displayImage(itemEntity.getHeadPic(), holder.iv_avatar, Instance.user_s_options);
		
		holder.bt_reply.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DBHelper dbh = new DBHelper(mContext);
				if(dbh.queryUserInfo().size()>0){
					Intent intent = new Intent();
					intent.setClass(mContext, Discuss_ReplyOther_Activity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("discussreply", itemEntity);
					intent.putExtra("hostid", hostid);
					intent.putExtras(bundle);
					mContext.startActivity(intent);
				}else{
					Intent intent_login = new Intent();
					intent_login.setClass(mContext, Login_Activity.class);
					mContext.startActivity(intent_login);
				}
			}
		});
		
		return convertView;
	}

	/**
	 * listview组件复用，防止“卡顿”
	 * 
	 * @author Administrator
	 * 
	 */
	class ViewHolder {
		private XCRoundImageView iv_avatar;
		private TextView tv_name;
		private TextView tv_time;
		private TextView tv_floor;
		private TextView tv_label;
		private TextView tv_content;
		private Button bt_reply;
	}
}
