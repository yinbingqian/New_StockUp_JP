package com.sxit.activity.welcome;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONException;
import org.ksoap2.serialization.SoapObject;

import com.sxit.activity.adviser.th.AMainTabHostActivity;
import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.login.Login_Activity;
import com.sxit.activity.th.MainTabHostActivity;
import com.sxit.db.DBHelper;
import com.sxit.entity.Adviser;
import com.sxit.entity.LoginUser;
import com.sxit.http.SoapRes;
import com.sxit.http.SoapWebService;
import com.sxit.md5.MD5;
import com.sxit.md5.MD5Plus;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.Utils;
import com.umeng.common.message.UmengMessageDeviceConfig;
import com.umeng.fb.model.UserInfo;
import com.umeng.message.ALIAS_TYPE;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengRegistrar;
import com.umeng.message.proguard.aa.e;
import com.umeng.message.tag.TagManager.Result;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;
import lnpdit.lntv.tradingtime.R;

/**
 * 欢迎页
 * 
 * @author huanyu 类名称：Welcome_Activity 创建时间:2014-10-26 下午10:05:03
 */
public class Welcome_Activity extends BaseActivity {
	private TextView tv_go;
	private TextView tv_register;
	private TextView tv_login;
	private Object NULL = null;
	private DBHelper dbh;
	private String[] property_anm = { "name", "password" };
	private Object[] property_ava = new Object[2];
	public static PushAgent mPushAgent;
	Context context;
    private String userId = "";
	// property_ava = { tempStr, tempStr };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		// initDB();
		// initView();
		// setListeners();
		context = this;
		Utils.getVersionName(Welcome_Activity.this);
//		UmengUpdateAgent.setUpdateOnlyWifi(true);
//		UmengUpdateAgent.update(this);
		
		mPushAgent = PushAgent.getInstance(this);
		mPushAgent.enable();
		
		//开启推送并设置注册的回调处理
		mPushAgent.enable(new IUmengRegisterCallback() {

			@Override
			public void onRegistered(String registrationId) {
				//onRegistered方法的参数registrationId即是device_token
				Log.d("device_token", registrationId);
				Log.d("!!!!!!!!!!!!!!!!!!!", registrationId);
				Log.d("!!!!!!!!!!!!!!!!!!!", registrationId);
				Log.d("!!!!!!!!!!!!!!!!!!!", registrationId);
				Log.d("!!!!!!!!!!!!!!!!!!!", registrationId);
				Log.d("!!!!!!!!!!!!!!!!!!!", registrationId);
				Log.d("!!!!!!!!!!!!!!!!!!!", registrationId);
			}
		});
//		
		
		//sdk开启通知声音
		mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER);
		mPushAgent.onAppStart();
		//开启推送并设置注册的回调处理
		PushAgent.sendSoTimeout(this, 600);	//设置护保进程间隔时间
	
		
		String device_token = UmengRegistrar.getRegistrationId(this);
		System.out.print(">>>>>>>>>>>>>>>>>>>>>>> "+device_token);
		System.out.print(">>>>>>>>>>>>>>>>>>>>>>> "+device_token);
		System.out.print(">>>>>>>>>>>>>>>>>>>>>>> "+device_token);
		System.out.print(">>>>>>>>>>>>>>>>>>>>>>> "+device_token);
		System.out.print(">>>>>>>>>>>>>>>>>>>>>>> "+device_token);
//		Toast.makeText(context,device_token, Toast.LENGTH_SHORT).show();

		try {
			Thread thread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub					
//					try {
//						Result result = mPushAgent.getTagManager().add("guzhang3790");
//						Log.d("##########################", result.jsonString);
//						Log.d("##########################", result.jsonString);
//						Log.d("##########################", result.jsonString);
//						Log.d("##########################", result.jsonString);
//						Log.d("##########################", result.jsonString);
//						Log.d("##########################", result.jsonString);
//						Log.d("##########################", result.jsonString);
//						Log.d("##########################", result.jsonString);
//						Log.d("##########################", result.jsonString);
//						Log.d("##########################", result.jsonString);
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
				}
			});
			thread.start();
			userId = getUserInfo().getid();
//			mPushAgent.addAlias("guzhang",userId);
//			mPushAgent.addExclusiveAlias("guzhang",userId);
			mPushAgent.addAlias(userId,"guzhang");
			mPushAgent.addExclusiveAlias(userId,"guzhang");
			
//			
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
//		mPushAgent.setAlias(userId, ALIAS_TYPE.SINA_WEIBO);
//		mPushAgent.setExclusiveAlias("guzhang", userId);
	
		
		new Handler().postDelayed(new Runnable() {

			public void run() {
				Intent intent = new Intent();
				intent.setClass(getBaseContext(), MainTabHostActivity.class);
//				intent.setClass(getBaseContext(), HomePageActivity.class);
				startActivity(intent);

				finish();
			}

		}, 1500);
		updateStatus();
		checkVersion();
	}
//	 /** 版本检测 */
    private void checkVersion() {
        UmengUpdateAgent.setUpdateOnlyWifi(false);
//        UmengUpdateAgent.setUpdateAutoPopup(false);
        UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {

            @Override
            public void onUpdateReturned(int updateStatus,
                    UpdateResponse updateInfo) {
//                if (updateStatus == 0 && updateInfo != null) {
//                    showUpdateDialog(updateInfo.path, updateInfo.updateLog);
//                }
                // case 0: // has update
                // case 1: // has no update
                // case 2: // none wifi
       
                // case 3: // time out
            }
        });

        UmengUpdateAgent.update(this);
    }
	private void updateStatus() {
		String pkgName = getApplicationContext().getPackageName();
		String info = String.format("enabled:%s  isRegistered:%s  DeviceToken:%s " + 
				"SdkVersion:%s AppVersionCode:%s AppVersionName:%s",
				mPushAgent.isEnabled(), mPushAgent.isRegistered(),
				mPushAgent.getRegistrationId(), MsgConstant.SDK_VERSION,
				UmengMessageDeviceConfig.getAppVersionCode(this), UmengMessageDeviceConfig.getAppVersionName(this));
//		Toast.makeText(context, info, Toast.LENGTH_LONG).show();
//		tvStatus.setText("应用包名："+pkgName+"\n"+info);
//		
//		btnEnable.setImageResource(mPushAgent.isEnabled()?R.drawable.open_button:R.drawable.close_button);
//		btnEnable.setClickable(true);
//		copyToClipBoard();
		
		Log.i("", "updateStatus:" + String.format("enabled:%s  isRegistered:%s",
				mPushAgent.isEnabled(), mPushAgent.isRegistered()));
		Log.i("", "=============================");
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
		}
		return true;
	}

	List<Adviser> data;

	class CheckStatusAT extends AsyncTask<Object, Object, Object> {
		@Override
		protected void onPreExecute() {
			data = new ArrayList<Adviser>();
			super.onPreExecute();
		}

		@Override
		protected Object doInBackground(Object... params) {
			// String[] property_nm = {"pagesize","pageindex"};
			// Object[] property_va = {10,1};
			Object res_obj = SoapWebService.data(SOAP_UTILS.METHOD.GETADMIN, (String[]) params[0],
					(Object[]) params[1]);
			if (null != res_obj) {
				SoapObject so = (SoapObject) res_obj;
				SoapObject soapchilds = (SoapObject) so.getProperty(0);
				SoapObject soapchildss = (SoapObject) soapchilds.getProperty(0);
				Adviser adviser;
				if (soapchilds != null) {
					for (int i = 0; i < soapchilds.getPropertyCount(); i++) {
						SoapObject soa = (SoapObject) soapchilds.getProperty(i);
						adviser = new Adviser();
						adviser.setAdv_user_id(soa.getProperty("Id").toString());
						adviser.setCrtime(soa.getProperty("CrTime").toString());
						adviser.setEmail(soa.getProperty("Email").toString());
						adviser.setGroupid(soa.getProperty("GroupId").toString());
						adviser.setHeadpic(soa.getProperty("HeadPic").toString());
						adviser.setSex(soa.getProperty("Sex").toString());
						adviser.setIslock(Boolean.parseBoolean(soa.getProperty("Islock").toString()));
						adviser.setLevel(soa.getProperty("Level").toString());
						adviser.setMark(soa.getProperty("Mark").toString());
						adviser.setMobilephone(soa.getProperty("Mobilephone").toString());
						adviser.setName(soa.getProperty("Name").toString());
						adviser.setOrgid(soa.getProperty("Orgid").toString());
						adviser.setOrgname(soa.getProperty("OrgName").toString());
						adviser.setPaidmark(soa.getProperty("Paidmark").toString());
						adviser.setPassword(soa.getProperty("Password").toString());
						adviser.setPtitle(soa.getProperty("PTitle").toString());
						adviser.setRealname(soa.getProperty("RealName").toString());
						adviser.setRewardmark(soa.getProperty("Rewardmark").toString());
						adviser.setResume(soa.getProperty("Resume").toString());
						adviser.setSpecialty(soa.getProperty("Specialty").toString());
						adviser.setStatus(soa.getProperty("Status").toString());
						adviser.setHeartcount(soa.getProperty("Heartcount").toString());
						data.add(adviser);
						
//						userId = soa.getProperty("Id").toString();
					}
				}
				dbh.clearAllAdviser();
				dbh.insAdviserInfo(data);
				System.out.println("-------------" + soapchildss);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			new CheckStatusAT3().execute(property_anm, property_ava);
			super.onPostExecute(result);
		}
	}

	class CheckStatusAT2 extends AsyncTask<Object, Object, Object> {
		@Override
		protected void onPreExecute() {
			data = new ArrayList<Adviser>();
			super.onPreExecute();
		}

		@Override
		protected Object doInBackground(Object... params) {
			// String[] property_nm = {"pagesize","pageindex"};
			// Object[] property_va = {10,1};
			Object res_obj = SoapWebService.data(SOAP_UTILS.METHOD.GETADMIN, (String[]) params[0],
					(Object[]) params[1]);
			if (null != res_obj) {
				SoapObject so = (SoapObject) res_obj;
				SoapObject soapchilds = (SoapObject) so.getProperty(0);
				SoapObject soapchildss = (SoapObject) soapchilds.getProperty(0);
				Adviser adviser;
				if (soapchilds != null) {
					for (int i = 0; i < soapchilds.getPropertyCount(); i++) {
						SoapObject soa = (SoapObject) soapchilds.getProperty(i);
						adviser = new Adviser();
						adviser.setAdv_user_id(soa.getProperty("Id").toString());
						adviser.setCrtime(soa.getProperty("CrTime").toString());
						adviser.setEmail(soa.getProperty("Email").toString());
						adviser.setGroupid(soa.getProperty("GroupId").toString());
						adviser.setHeadpic(soa.getProperty("HeadPic").toString());
						adviser.setSex(soa.getProperty("Sex").toString());
						adviser.setIslock(Boolean.parseBoolean(soa.getProperty("Islock").toString()));
						adviser.setLevel(soa.getProperty("Level").toString());
						adviser.setMark(soa.getProperty("Mark").toString());
						adviser.setMobilephone(soa.getProperty("Mobilephone").toString());
						adviser.setName(soa.getProperty("Name").toString());
						adviser.setOrgid(soa.getProperty("Orgid").toString());
						adviser.setOrgname(soa.getProperty("OrgName").toString());
						adviser.setPaidmark(soa.getProperty("Paidmark").toString());
						adviser.setPassword(soa.getProperty("Password").toString());
						adviser.setPtitle(soa.getProperty("PTitle").toString());
						adviser.setRealname(soa.getProperty("RealName").toString());
						adviser.setRewardmark(soa.getProperty("Rewardmark").toString());
						adviser.setResume(soa.getProperty("Resume").toString());
						adviser.setSpecialty(soa.getProperty("Specialty").toString());
						adviser.setStatus(soa.getProperty("Status").toString());
						adviser.setHeartcount(soa.getProperty("Heartcount").toString());
						data.add(adviser);
					}
				}
				dbh.clearAllAdviser();
				dbh.insAdviserInfo(data);

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println("-------------" + soapchildss);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			LoginUser loginUser = dbh.queryLoginUserInfo();

			System.out.println(">>>!!!! username : " + loginUser.getName());
			System.out.println(">>>!!!! password : " + loginUser.getPassword());
			System.out.println(">>>!!!! logintype : " + loginUser.getLoginType());
             userId = loginUser.getUserid();
			switch (loginUser.getLoginType()) {
			case 0:
				Object[] property_va = { loginUser.getName(), loginUser.getPassword() };
				System.out.println(">>> md5  " + MD5.value(loginUser.getPassword(), MD5.BIT32));

				soapService.adminLogin(property_va);
				break;
			case 1:
				Object[] property_vc = { loginUser.getName(), MD5Plus.stringToMD5(loginUser.getPassword()),
						Utils.getImei(Welcome_Activity.this) };
				// Object[] property_vc = { loginUser.getName(),
				// loginUser.getPassword(), Utils.getImei(Welcome_Activity.this)
				// };
				// System.out.println(">>> md5
				// "+MD5.value(loginUser.getPassword(),MD5.BIT32));
				soapService.userInfoLogin(property_vc);

				break;

			default:
				break;
			}

			super.onPostExecute(result);
		}
	}

	class CheckStatusAT3 extends AsyncTask<Object, Object, Object> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Object doInBackground(Object... params) {
			Object res_obj = SoapWebService.data(SOAP_UTILS.METHOD.ADMINLOGIN, (String[]) params[0],
					(Object[]) params[1]);

			if (null != res_obj) {
				SoapObject so = (SoapObject) res_obj;
				System.out.println("!!!!!" + res_obj);
				// SoapObject soapchilds = (SoapObject) so.getProperty(0);
				// SoapObject soapchildss = (SoapObject)
				// soapchilds.getProperty(0);
				Adviser adviser;
				// if (soapchilds != null) {
				// for (int i = 0; i < soapchilds.getPropertyCount(); i++) {
				SoapObject soa = (SoapObject) so.getProperty(0);
				adviser = new Adviser();
				adviser.setAdv_user_id(soa.getProperty("Id").toString());
				adviser.setCrtime(soa.getProperty("CrTime").toString());
				adviser.setEmail(soa.getProperty("Email").toString());
				adviser.setGroupid(soa.getProperty("GroupId").toString());
				adviser.setHeadpic(soa.getProperty("HeadPic").toString());
				adviser.setSex(soa.getProperty("Sex").toString());
				adviser.setIslock(Boolean.parseBoolean(soa.getProperty("Islock").toString()));
				adviser.setLevel(soa.getProperty("Level").toString());
				adviser.setMark(soa.getProperty("Mark").toString());
				adviser.setMobilephone(soa.getProperty("Mobilephone").toString());
				adviser.setName(soa.getProperty("Name").toString());
				adviser.setOrgid(soa.getProperty("Orgid").toString());
				adviser.setOrgname(soa.getProperty("OrgName").toString());
				adviser.setPaidmark(soa.getProperty("Paidmark").toString());
				adviser.setPassword(soa.getProperty("Password").toString());
				adviser.setPtitle(soa.getProperty("PTitle").toString());
				// adviser.setRealname("股长运营团队");
				adviser.setRealname(soa.getProperty("RealName").toString());
				adviser.setRewardmark(soa.getProperty("Rewardmark").toString());
				adviser.setResume(soa.getProperty("Resume").toString());
				adviser.setSpecialty(soa.getProperty("Specialty").toString());
				adviser.setStatus(soa.getProperty("Status").toString());
				adviser.setHeartcount(soa.getProperty("Heartcount").toString());
				System.out.println("!!!!!!!!!!!!!@@@" + adviser.getHeartcount());
				dbh.insAdviserGz(adviser);
				dbh.insAdviserNs();
				dbh.queryAdviserGz();
				System.out.println("-------------" + so + " ### " + adviser.getRealname());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			dbh.queryAdviserInfo();
			intent.setClass(Welcome_Activity.this, Login_Activity.class);
			startActivity(intent);
			finish();
			super.onPostExecute(result);
		}
	}

	Set<String> set = new HashSet<String>();

	public void onEvent(SoapRes res) {
		super.onEvent(res);
		if (res.getCode().equals(SOAP_UTILS.METHOD.USERINFOLOGIN)) {
			LoginUser loginUser = (LoginUser) res.getObj();
			if (loginUser != null) {
				if (loginUser.getUserid().equals("0")) {
					Utils.showTextToast(this, getString(R.string.userlogin_fail));
				} else {
					// Utils.showTextToast(this, "用户登录id " +
					// loginUser.getUserid());
					loginUser.setLoginType(SOAP_UTILS.USER);
					set.add("TradingUser");// 用户
					Object NULL = null;
					String wStr1 = getResources().getString(R.string.app_welcome_text1);
					String wStr2 = getResources().getString(R.string.app_welcome_text2);
					String[] wStr = { wStr1, wStr2 };
					if (!(dbh.queryGzAdminMsg(getLoginUser().getUserid()) > 0)) {
						dbh.initSysMsgData(getLoginUser().getUserid(), wStr);
					}
					intent.setClass(this, MainTabHostActivity.class);
					startActivity(intent);
					finish();
				}
			} else {
				Utils.showTextToast(this, getString(R.string.userlogin_fail));
			}
		} else if (res.getCode().equals(SOAP_UTILS.METHOD.ADMINLOGIN)) {
			LoginUser loginUser = (LoginUser) res.getObj();
			if (loginUser != null) {
				if (loginUser.getUserid().equals("0")) {
					Utils.showTextToast(this, getString(R.string.advlogin_fail));
				} else {
					intent.setClass(this, AMainTabHostActivity.class);
					startActivity(intent);
					finish();
				}
			} else {
				Utils.showTextToast(this, getString(R.string.advlogin_fail));
			}
		}
	}
}
