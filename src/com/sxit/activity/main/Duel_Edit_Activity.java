package com.sxit.activity.main;

import org.json.JSONException;
import org.json.JSONObject;

import com.sxit.activity.base.BaseActivity;
import com.sxit.http.HttpPostService;
import com.sxit.http.SoapRes;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.Utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import lnpdit.lntv.tradingtime.R;

/**
 * 新闻详情
 * 
 * @author huanyu 类名称：NewsInfo_Activity 创建时间:2014-10-29 下午3:18:47
 */
public class Duel_Edit_Activity extends BaseActivity {
	Context context;

	ImageView img_back;
	ImageView img_send;
	AutoCompleteTextView edit_content;
	RadioGroup rg_point;
	RadioButton rb_up;
	RadioButton rb_down;
	Button bt_send;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_duel_edit);
		this.isParentActivity = false;
		context = this;
		initView();
		setListeners();
	}

	private void sendPoint() {
		if(edit_content.getText().toString().trim().equals("")){
			Toast.makeText(context, "请填写您的观点", Toast.LENGTH_SHORT).show();
		}else{			
			String viewpoint = "";
			if(rb_up.isChecked()){
				viewpoint = "up";
			}else{
				viewpoint = "down";
			}
			String userid = getUserInfo().getid();
			String content = edit_content.getText().toString();
//		String[] property_va = new String[] {userid, content, viewpoint};
//		soapService.viewPointAdd(property_va);
			String[] property_nm = { "userid","content", "viewpoint" };
			Object[] property_va = { userid, content, viewpoint };
			new viewPointAddPostTask().execute(property_nm, property_va);
		}
	}

	private void initView() {
		img_back = (ImageView) findViewById(R.id.img_back);
		img_send = (ImageView) findViewById(R.id.img_send);
		edit_content = (AutoCompleteTextView) findViewById(R.id.point_edit);
		rb_up = (RadioButton) findViewById(R.id.rb_up);
		rb_down = (RadioButton) findViewById(R.id.rb_down);
		bt_send = (Button) findViewById(R.id.bt_send);
	}
	

	private void setListeners() {
		rb_up.isSelected();
		img_back.setOnClickListener(this);
		img_send.setOnClickListener(this);
		bt_send.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.img_send:
		case R.id.bt_send:
			sendPoint();
			break;
		default:
			break;
		}
	}
	
	class viewPointAddPostTask extends AsyncTask<Object, Object, Object> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            img_send.setEnabled(false);
            bt_send.setEnabled(false);
        }

        @Override
        protected Object doInBackground(Object... params) {
            System.out.println(">>>>>");
            Object res_obj = (Object) HttpPostService.data(SOAP_UTILS.METHOD.VIEWPOINTADDPOST, (String[]) params[0],(Object[]) params[1]);
            return res_obj;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            img_send.setEnabled(true);
            bt_send.setEnabled(true);
            try {
				JSONObject json_res = new JSONObject(result.toString());
				JSONObject json_obj = new JSONObject(json_res.get("ViewPointAddPostResult").toString());
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
		if(obj.getCode().equals(SOAP_UTILS.METHOD.VIEWPOINTADD)){
			String result = obj.getObj().toString();
			if(result.equals("success")){
				Utils.showTextToast(context, "发布成功");
				finish();
			}else{
				Utils.showTextToast(context, result);
			}
		}
	}
}
