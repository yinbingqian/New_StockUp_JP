package com.sxit.activity.base;

import java.util.LinkedList;
import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.sxit.activity.adviser.th.AMainTabHostActivity;
import com.sxit.activity.th.MainTabHostActivity;
import com.sxit.db.DBHelper;
import com.sxit.entity.LoginUser;
import com.umeng.message.MiPushRegistar;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.Toast;
import lnpdit.lntv.tradingtime.R;

public class MyApplication extends Application {
	/** 全局常量 **/
	public static LoginUser loginUser;
	//
	public String sender_id;
	public static final String TAG = MyApplication.class.getSimpleName();
	public MainTabHostActivity mainTabHostActivity;
	public AMainTabHostActivity aMainTabHostActivity;
	private static MyApplication instance = null;
	private DBHelper dbh;
	public static String a_heartcount = "1";

	private PushAgent mPushAgent;

	public synchronized static MyApplication getInstance() {
		if (null == instance) {
			instance = new MyApplication();
		}
		return instance;
	}

	// 运用list来保存们每一个activity是关键
	private List<Activity> mList = new LinkedList<Activity>();

	// add Activity
	public void addActivity(Activity activity) {
		mList.add(activity);
	}

	// 关闭每一个list内的activity
	public void exit() {
		try {
			for (Activity activity : mList) {
				if (activity != null)
					activity.finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// finally {
		// System.exit(0);
		// }
	}

	@Override
	public void onCreate() {
		super.onCreate();
		instance = getInstance();
		ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
		ImageLoader.getInstance().init(configuration);
		dbh = new DBHelper(this);

		mPushAgent = PushAgent.getInstance(this);
		mPushAgent.setDebugMode(false);
	}

	public void exitApp() {
		if (mainTabHostActivity != null) {
			mainTabHostActivity.finish();
		}
		if (aMainTabHostActivity != null) {
			aMainTabHostActivity.finish();
		}
	}

	public LoginUser getLoginUser() {
		return dbh.queryLoginUserInfo();
	}

	public void setLoginUser(LoginUser loginUser) {
		dbh.insLoginUserInfo(loginUser);
		// this.loginUser = loginUser;
	}

	public String getSender_id() {
		return sender_id;
	}

	public void setSender_id(String sender_id) {
		this.sender_id = sender_id;
	}

	public static String getA_heartcount() {
		return a_heartcount;
	}

	public static void setA_heartcount(String a_heartcount) {
		MyApplication.a_heartcount = a_heartcount;
	}

}
