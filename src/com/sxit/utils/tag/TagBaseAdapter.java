package com.sxit.utils.tag;

import java.util.List;

import com.sxit.entity.discuss.DiscussTag;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;
import lnpdit.lntv.tradingtime.R;

/**
 * @author fyales
 * @since 8/26/15.
 */
public class TagBaseAdapter extends BaseAdapter {

    private Context mContext;
    private List<DiscussTag> mList;

    public TagBaseAdapter(Context context, List<DiscussTag> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public DiscussTag getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.tagview, null);
            holder = new ViewHolder();
            holder.tagBtn = (ToggleButton) convertView.findViewById(R.id.tag_btn);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        final DiscussTag text = getItem(position);
        holder.tagBtn.setText(text.getTitle());
        holder.tagBtn.setTextOff(text.getTitle());
        holder.tagBtn.setTextOn(text.getTitle());
        holder.tagBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                	holder.tagBtn.setTextColor(Color.rgb(255, 255, 255));
//                    if(NetwortAlarmActivity.label_id.size()<3){                        
//                        NetwortAlarmActivity.label_id.add(text.getId());
//                        NetwortAlarmActivity.label_name.add(text.getTagName());
//                    }else{
//                        Toast.makeText(mContext, "最多选择三个标签", Toast.LENGTH_SHORT).show();
//                        holder.tagBtn.setChecked(false);
//                        return;
//                    }
                }else{
                    holder.tagBtn.setTextColor(Color.rgb(211, 7, 0));
//                    for (int i = 0; i < NetwortAlarmActivity.label_id.size(); i++) {
//                        if(NetwortAlarmActivity.label_name.get(i).equals(text.getTagName())){
//                            NetwortAlarmActivity.label_id.remove(i);
//                            NetwortAlarmActivity.label_name.remove(i);
//                        }
//                    }
                }
            }
        });
        return convertView;
    }

    static class ViewHolder {
        ToggleButton tagBtn;
    }
}
