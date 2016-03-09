package com.sxit.activity.wap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import com.sxit.activity.base.BaseActivity;
import com.sxit.http.HttpPostService;
import com.sxit.utils.SOAP_UTILS;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import lnpdit.lntv.tradingtime.R;


public class NewsAddCommentActivity extends BaseActivity implements
		OnClickListener {

	Context context;
	Button cancel_bt;
	Button send_bt;
	EditText addcomment_et;

	String NewsId = "";
	String comment_str = "";
	String userId = "";

    private ProgressDialog progressDialog;

	ArrayList<HashMap<String, Object>> remoteWindowItem = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		
		setContentView(R.layout.activity_addnewscomment);

		WindowManager m = getWindowManager();
		Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高

		LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参数值
//		p.height = (int) (d.getHeight() * 0.5); // 高度设置为屏幕的1.0
		p.height = (int) (d.getHeight() * 1.0); // 高度设置为屏幕的1.0
		p.width = (int) (d.getWidth() * 1.0); // 宽度设置为屏幕的0.8
		p.alpha = 1.0f; // 设置本身透明度
		p.dimAmount = 0.0f; // 设置黑暗度

		getWindow().setAttributes(p); // 设置生效
		getWindow().setGravity(Gravity.RIGHT);

		Intent intent = this.getIntent();
		NewsId = intent.getStringExtra("NewsId");
		InitView();
		userId = getUserInfo().getid();
		
		
		//定时器
		Timer timer = new Timer(); timer.schedule(new TimerTask() 
		{  @Override public void run() 
		{  InputMethodManager m = (InputMethodManager) getWindow().getContext().getSystemService(Context.INPUT_METHOD_SERVICE);  
		m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);  
		} 
		}, 300); 

	}

	void InitView() {
		cancel_bt = (Button) this.findViewById(R.id.cancel_bt);
		send_bt = (Button) this.findViewById(R.id.send_bt);
		send_bt.setOnClickListener(this);
		addcomment_et = (EditText) findViewById(R.id.addcomment_et);
		//设置EditText的显示方式为多行文本输入  
		addcomment_et.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);  
		//文本显示的位置在EditText的最上方  
		addcomment_et.setGravity(Gravity.TOP);
		cancel_bt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.cancel_bt:
			finish();
			break;
		case R.id.send_bt:
			comment_str = addcomment_et.getText().toString();
			String[] property_nm = { "userid","newsid", "content" };
			Object[] property_va = { userId, NewsId, comment_str };
			new commentAddPostTask().execute(property_nm, property_va);
			progressDialog = new ProgressDialog(this);
            String stri = getResources().getString(R.string.Is_sending_a_request);
            progressDialog.setMessage(stri);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

			break;
		default:
			break;
		}
		super.onClick(v);
	}

	class commentAddPostTask extends AsyncTask<Object, Object, Object> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            send_bt.setEnabled(false);
            cancel_bt.setEnabled(false);
        }

        @Override
        protected Object doInBackground(Object... params) {
            System.out.println(">>>>>");
            Object res_obj = (Object) HttpPostService.data(SOAP_UTILS.METHOD.ADDNEWSCOMMENT, (String[]) params[0],(Object[]) params[1]);
            return res_obj;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            send_bt.setEnabled(true);
            cancel_bt.setEnabled(true);
            progressDialog.dismiss();

            try {
				JSONObject json_res = new JSONObject(result.toString());
				JSONObject json_obj = new JSONObject(json_res.get("AddNewsCommentResult").toString());
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
	
}
