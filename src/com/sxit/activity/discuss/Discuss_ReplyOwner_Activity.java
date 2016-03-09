package com.sxit.activity.discuss;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.discuss.adapter.DiscussReplyAdapter;
import com.sxit.entity.discuss.DiscussItem;
import com.sxit.entity.discuss.DiscussReply;
import com.sxit.http.HttpPostService;
import com.sxit.http.SoapRes;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import lnpdit.lntv.tradingtime.R;

/**
 * 窗口
 * 
 * @author
 *
 */
public class Discuss_ReplyOwner_Activity extends BaseActivity implements OnClickListener {

	AutoCompleteTextView edit_content;
	RadioGroup radiogroup;
	RadioButton reply0;
	RadioButton reply5;
	RadioButton reply10;
	RadioButton reply20;
	RadioButton reply50;
	Button bt_send;

	DiscussItem disitem;
	
	String weiwang = "0";
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_discuss_replyowner);
		isParentActivity = false;
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.width = LayoutParams.FILL_PARENT;
		lp.gravity = Gravity.BOTTOM;
		getWindow().setAttributes(lp);
		context = this;

		Intent intent = this.getIntent();
		disitem = (DiscussItem) intent.getSerializableExtra("discuss");
		
		viewInit();
	}
	
	private void viewInit(){
		radiogroup = (RadioGroup) this.findViewById(R.id.radiogroup);
		reply0 = (RadioButton) this.findViewById(R.id.reply0);
		reply5 = (RadioButton) this.findViewById(R.id.reply5);
		reply10 = (RadioButton) this.findViewById(R.id.reply10);
		reply20 = (RadioButton) this.findViewById(R.id.reply20);
		reply50 = (RadioButton) this.findViewById(R.id.reply50);
		edit_content = (AutoCompleteTextView) this.findViewById(R.id.content);
		bt_send = (Button) this.findViewById(R.id.reply_send);
		bt_send.setOnClickListener(this);
		reply0.setChecked(true);
		radiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.reply0:
					weiwang = "0";
					break;
				case R.id.reply5:
					weiwang = "5";
					break;
				case R.id.reply10:
					weiwang = "10";
					break;
				case R.id.reply20:
					weiwang = "20";
					break;
				case R.id.reply50:
					weiwang = "50";
					break;
				default:
					break;
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.reply_send:
			bt_send.setEnabled(false);
//			String[] property_va = new String[] {disitem.getId(), getUserInfo().getid(), "回复楼主：" + edit_content.getText().toString(), weiwang};
//			soapService.discussionReplyHost(property_va);
			if(edit_content.getText().toString().trim().equals("")){
				Toast.makeText(context, "回复内容不能为空", Toast.LENGTH_SHORT).show();
			}else{	
				String[] property_nm = { "hostid","userid", "content", "mark" };
				Object[] property_va = { disitem.getId(), getUserInfo().getid(), "回复楼主：\n" + edit_content.getText().toString(), weiwang };
				new discussionReplyHostPostTask().execute(property_nm, property_va);
			}
			break;
		default:
			break;
		}
	}
	

	class discussionReplyHostPostTask extends AsyncTask<Object, Object, Object> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            bt_send.setEnabled(false);
        }

        @Override
        protected Object doInBackground(Object... params) {
            System.out.println(">>>>>");
            Object res_obj = (Object) HttpPostService.data(SOAP_UTILS.METHOD.DISCUSSIONREPLYHOSTPOST, (String[]) params[0],(Object[]) params[1]);
            return res_obj;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            bt_send.setEnabled(true);
            try {
				JSONObject json_res = new JSONObject(result.toString());
				JSONObject json_obj = new JSONObject(json_res.get("DiscussionReplyHostPostResult").toString());
				if(json_obj.get("Result").toString().equals("success")){
					Toast.makeText(context, json_obj.get("Message").toString(), Toast.LENGTH_SHORT).show();
					finish();
				}else if(json_obj.get("Result").toString().equals("error")){
					Toast.makeText(context, json_obj.get("Message").toString(), Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
	
	public void onEvent(SoapRes obj) {
		super.onEvent(obj);
		bt_send.setEnabled(true);
		if(obj.getCode().equals(SOAP_UTILS.METHOD.DISCUSSIONREPLYHOST)){
			String result = obj.getObj().toString();
			if (result.equals("success")) {
				Toast.makeText(context, "发表成功", Toast.LENGTH_SHORT).show();
				finish();
			} else {
				Utils.showTextToast(context, result);
			}
		}
	}

}
