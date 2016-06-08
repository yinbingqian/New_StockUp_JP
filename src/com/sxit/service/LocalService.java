package com.sxit.service;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.sxit.activity.base.MyApplication;
import com.sxit.activity.login.Login_Activity;
import com.sxit.activity.th.HomePageActivity;
import com.sxit.activity.th.item.MyZone_Activity;
import com.sxit.db.DBHelper;
import com.sxit.entity.UserInfo;
import com.sxit.entity.living.NowLiving;
import com.sxit.http.ISoapService;
import com.sxit.http.SoapRes;
import com.sxit.http.SoapService;
import com.sxit.utils.EventCache;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.Utils;
import com.umeng.message.PushAgent;
import com.umeng.message.proguard.aa.e;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class LocalService extends Service {
	public static boolean ischeckingimei;
	String imei;
	String userId;
	Context context;
	private DBHelper dbh;
	public MyApplication myApplication;
	private PushAgent mPushAgent;
	
	public ISoapService soapService = new SoapService();
    /**
     * 在 Local Service 中我们直接继承 Binder 而不是 IBinder,因为 Binder 实现了 IBinder 接口，这样我们可以少做很多工作。
     * @author newcj
     */
    public class SimpleBinder extends Binder{
        /**
         * 获取 Service 实例
         * @return
         */
        public LocalService getService(){
            return LocalService.this;
        }
         
        public int add(int a, int b){
            return a + b;
        }
    }
     
    public SimpleBinder sBinder;
 
    @Override
    public void onCreate() {
        super.onCreate();
        // 创建 SimpleBinder
        sBinder = new SimpleBinder();	
        context = this;
        
        myApplication = MyApplication.getInstance();
		EventCache.commandActivity.unregister(this);
		EventCache.commandActivity.register(this);
		EventCache.errorHttp.unregister(this);
		EventCache.errorHttp.register(this);
		
		mPushAgent = PushAgent.getInstance(this);
		mPushAgent.enable();
		
    	TelephonyManager mTelephonyMgr = (TelephonyManager) context  
                .getSystemService(Context.TELEPHONY_SERVICE);  
        imei = mTelephonyMgr.getDeviceId();
        
        ischeckingimei = true;
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub				
				while (ischeckingimei) {
					try {
						Thread.sleep(5000);
						Log.d("<<<<<<<<<<<<<<<<<<", ">>>>>>>>>>>>>>>>");
						Log.d("<<<<<<<<<<<<<<<<<<", ">>>>>>>>>>>>>>>>");
						Log.d("<<<<<<<<<<<<<<<<<<", ">>>>>>>>>>>>>>>>");
						Log.d("<<<<<<<<<<<<<<<<<<", ">>>>>>>>>>>>>>>>");
						Log.d("<<<<<<<<<<<<<<<<<<", ">>>>>>>>>>>>>>>>");
						Log.d("<<<<<<<<<<<<<<<<<<", ">>>>>>>>>>>>>>>>");
						dbh = new DBHelper(context);
						if(dbh.queryUserInfo().size()>0){
							userId = getUserInfo().getid();
							
							if(getUserInfo()!=null){
								Object[] property_va = { userId, imei };
								soapService.updateImei(property_va);
							}
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		thread.start();
    }
     
    @Override
    public IBinder onBind(Intent intent) {
        // 返回 SimpleBinder 对象
        return sBinder;
    }
 
	public void onEvent(SoapRes obj) {
		if (obj.getCode().equals(SOAP_UTILS.METHOD.UPDATEIMEI)) {
			try {
				JSONObject	json_obj = new JSONObject(obj.getObj().toString());
				if(json_obj.get("Result").toString().equals("success")){
					
					Toast.makeText(context, json_obj.get("Message").toString(), Toast.LENGTH_SHORT).show();
					logout();
				}else{
//					Toast.makeText(context, "不重复", Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public UserInfo getUserInfo() {
		DBHelper dbh = new DBHelper(this);
		List<UserInfo> userlist = dbh.queryUserInfo();
		if(userlist.size()>0){
			return userlist.get(0);
		}else{
			return null;
		}
	}
	
	private void logout(){
		DBHelper dbh = new DBHelper(this);
		dbh.clearUserInfoData();
		try {
//			mPushAgent.removeAlias("guzhang",getUserInfo().getid());

			mPushAgent.removeAlias(getUserInfo().getid(),"guzhang");
			mPushAgent.getTagManager().reset();
		} catch (e e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Intent intent_login = new Intent();
		intent_login.setClass(LocalService.this, Login_Activity.class);
		intent_login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS); 
		startActivity(intent_login);
	}
}
