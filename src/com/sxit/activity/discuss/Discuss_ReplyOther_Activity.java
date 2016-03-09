package com.sxit.activity.discuss;

import org.json.JSONException;
import org.json.JSONObject;

import com.sxit.activity.base.BaseActivity;
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
import android.widget.TextView;
import android.widget.Toast;
import lnpdit.lntv.tradingtime.R;

/**
 * 窗口
 * 
 * @author
 *
 */
public class Discuss_ReplyOther_Activity extends BaseActivity implements OnClickListener {

	AutoCompleteTextView edit_content;
	TextView tv_title;
	Button bt_send;

	DiscussReply disitem;
	
	String hostid = "";
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_discuss_replyother);
		isParentActivity = false;
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.width = LayoutParams.FILL_PARENT;
		lp.gravity = Gravity.BOTTOM;
		getWindow().setAttributes(lp);
		context = this;

		Intent intent = this.getIntent();
		disitem = (DiscussReply) intent.getSerializableExtra("discussreply");
		hostid = intent.getStringExtra("hostid");
		
		viewInit();
	}
	
	private void viewInit(){
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		edit_content = (AutoCompleteTextView) this.findViewById(R.id.content);
		bt_send = (Button) this.findViewById(R.id.reply_send);
		bt_send.setOnClickListener(this);
		
		tv_title.setText("回复" + disitem.getFloor() + "：");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.reply_send:
			bt_send.setEnabled(false);
//			String[] property_va = new String[] {hostid, disitem.getId(), getUserInfo().getid(), "回复" + disitem.getFloor() + "：" + edit_content.getText().toString()};
//			soapService.discussionReplyOther(property_va);
			if(edit_content.getText().toString().trim().equals("")){
				Toast.makeText(context, "回复内容不能为空", Toast.LENGTH_SHORT).show();
			}else{			
				String[] property_nm = { "hostid","replyid", "userid", "content" };
				Object[] property_va = { hostid, disitem.getId(), getUserInfo().getid(), "回复" + disitem.getFloor() + "：\n" + edit_content.getText().toString() };
				new viewPointAddPostTask().execute(property_nm, property_va);
			}
			break;
		default:
			break;
		}
	}
	

	class viewPointAddPostTask extends AsyncTask<Object, Object, Object> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            bt_send.setEnabled(false);
        }

        @Override
        protected Object doInBackground(Object... params) {
            System.out.println(">>>>>");
            Object res_obj = (Object) HttpPostService.data(SOAP_UTILS.METHOD.DISCUSSIONREPLYOTHERPOST, (String[]) params[0],(Object[]) params[1]);
            return res_obj;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            bt_send.setEnabled(true);
            try {
				JSONObject json_res = new JSONObject(result.toString());
				JSONObject json_obj = new JSONObject(json_res.get("DiscussionReplyOtherPostResult").toString());
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
		if(obj.getCode().equals(SOAP_UTILS.METHOD.DISCUSSIONREPLYOTHER)){
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
