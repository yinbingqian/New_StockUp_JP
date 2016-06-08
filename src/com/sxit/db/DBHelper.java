package com.sxit.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.sxit.activity.th.item.bean.Information_ListBean;
import com.sxit.entity.ADInfo;
import com.sxit.entity.Adviser;
import com.sxit.entity.ChatMessage;
import com.sxit.entity.HomePageNews;
import com.sxit.entity.LoginUser;
import com.sxit.entity.UserInfo;
import com.sxit.entity.living.AllLiving;
import com.sxit.entity.living.HotLiving;
import com.sxit.entity.living.NowLiving;
import com.sxit.entity.smarter.Expert;
import com.sxit.entity.smarter.Guest;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private static final String DBNAME = "";
	private static final int VERSION = 1;
	private static final CursorFactory FACTORY = null;
	private static String DB_NAME = "stockup.db";
	private Context context;
	private static String DB_PATH = "/data/data/lnpdit.lntv.tradingtime/databases/";
	private static final int ASSETS_SUFFIX_BEGIN = 101;
	private static final int ASSETS_SUFFIX_END = 103;
	private SQLiteDatabase myDataBase = null;

	private String CHAT_DB = "";
	private String FRIE_DB = "";

	/**
	 * _id integer primary key autoincrement not null "byte_content blob "
	 * user_guid text "insert_date text "direction integer "type integer "
	 * user_clid bigint "target_type integer "child_clid text
	 * 
	 */
	private static final String CREATE_TABLE_T_SU_NEWS = "CREATE TABLE T_SU_NEWS (_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,ADMINID TEXT, COLTITLE TEXT,  CRTIME TEXT,HEADPIC TEXT, ID TEXT,ORDERS TEXT,ORGNAME TEXT,PICTURE TEXT,REALNAME TEXT,SOURCE TEXT,THUMBNAIL TEXT,TITLE TEXT, NEWSCOMCOUNT TEXT)";

	private static final String CREATE_TABLE_T_SU_ADT = "CREATE TABLE T_SU_ADT (_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,ID TEXT, ORDERS TEXT,  PICTURE TEXT, TITLE TEXT)";

	private static final String CREATE_TABLE_T_SU_STOCKNEWS = "CREATE TABLE T_SU_STOCKNEWS (_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,ADMINID TEXT, COLTITLE TEXT,  CRTIME TEXT,HEADPIC TEXT, ID TEXT,NEWSCOMCOUNT TEXT,ORDERS TEXT,ORGNAME TEXT,PICTURE TEXT,REALNAME TEXT,SOURCE TEXT,THUMBNAIL TEXT,TITLE TEXT)";
	
	private static final String CREATE_TABLE_T_SU_STANDPOINT = "CREATE TABLE T_SU_STANDPOINT (_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,ADMINID TEXT, COLTITLE TEXT,  CRTIME TEXT,HEADPIC TEXT, ID TEXT,NEWSCOMCOUNT TEXT,ORDERS TEXT,ORGNAME TEXT,PICTURE TEXT,REALNAME TEXT,SOURCE TEXT,THUMBNAIL TEXT,TITLE TEXT)";
	
	private static final String CREATE_TABLE_T_SU_USER = "CREATE TABLE T_SU_USER (_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,CHECKNUM TEXT, CHECKSTA TEXT,  HEADPIC TEXT,ID TEXT, LEVEL TEXT,MARK TEXT,NAME TEXT,PRESTIGE TEXT,RANK TEXT,REALNAME TEXT, BIRTH TEXT, SEX TEXT)";
	

	private static final String CREATE_TABLE_T_SU_GUEST = "CREATE TABLE T_SU_GUEST (_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,ANALYSIS TEXT,  HEADPIC TEXT,WEBID TEXT, LEVEL TEXT,PAIDMARK TEXT,REALNAME TEXT,STOCKSTYLE TEXT,RESUME TEXT)";

	private static final String CREATE_TABLE_T_SU_EXPERT = "CREATE TABLE T_SU_EXPERT (_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,REWARDMARK TEXT,  HEADPIC TEXT,WEBID TEXT, LEVEL TEXT,MARK TEXT,REALNAME TEXT,STOCKSTYLE TEXT,RESUME TEXT)";

	private static final String CREATE_TABLE_T_SU_NOWLIVING = "CREATE TABLE T_SU_NOWLIVING (_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,ANSWERCOUNT TEXT,CRTIMESTR TEXT,DEALADVISE TEXT, DEALCONTROL TEXT,DEALOPERATE TEXT,ID TEXT,LAUD TEXT,LIVECONTENT TEXT,LIVECOUNT TEXT,LIVEUSERID TEXT,LIVEUSERNAME TEXT,LIVINGS TEXT,TODAYVIEWPOINT TEXT,USERHEADPIC TEXT,USERRESUME TEXT)";
	
	private static final String CREATE_TABLE_T_SU_ALLLIVING = "CREATE TABLE T_SU_ALLLIVING (_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,ANSWERCOUNT TEXT,CRTIMESTR TEXT,DEALADVISE TEXT, DEALCONTROL TEXT,DEALOPERATE TEXT,ID TEXT,LAUD TEXT,LIVECONTENT TEXT,LIVECOUNT TEXT,LIVEUSERID TEXT,LIVEUSERNAME TEXT,LIVINGS TEXT,TODAYVIEWPOINT TEXT,USERHEADPIC TEXT,USERRESUME TEXT)";

	private static final String CREATE_TABLE_T_SU_HOTLIVING = "CREATE TABLE T_SU_hotLIVING (_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,ANSWERCOUNT TEXT,CRTIMESTR TEXT,DEALADVISE TEXT, DEALCONTROL TEXT,DEALOPERATE TEXT,ID TEXT,LAUD TEXT,LIVECONTENT TEXT,LIVECOUNT TEXT,LIVEUSERID TEXT,LIVEUSERNAME TEXT,LIVINGS TEXT,TODAYVIEWPOINT TEXT,USERHEADPIC TEXT,USERRESUME TEXT)";
	
	private static final String CREATE_TABLE_T_SU_MSG = "CREATE TABLE T_SU_MSG (_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,STATUS INTEGER, CONTENT1 TEXT,  CONTENT2 BLOB, CONTENT3_TITLE TEXT,CONTENT3_CONTENT TEXT,CONTENT3_URL TEXT,CONTENT3_IMG TEXT,MSG_DIRECTION INTEGER,MSG_TYPE INTEGER,MSG_DATE TEXT,LOGIN_ID TEXT,SENDER_ID TEXT)";

	private static final String CREATE_TABLE_T_SU_A_MSG = "CREATE TABLE T_SU_A_MSG (_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,STATUS INTEGER, CONTENT1 TEXT,  CONTENT2 BLOB, CONTENT3_TITLE TEXT,CONTENT3_CONTENT TEXT,CONTENT3_URL TEXT,CONTENT3_IMG TEXT,MSG_DIRECTION INTEGER,MSG_TYPE INTEGER,MSG_DATE TEXT,LOGIN_ID TEXT,SENDER_ID TEXT,HEADERPIC TEXT,SENDERNAME TEXT)";

	private static final String CREATE_TABLE_T_SU_LUI = "CREATE TABLE T_SU_LUI (_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,USER_ID TEXT,NAME TEXT,PASSWORD TEXT,REALNAME TEXT,ISLOCK INTEGER,SEX INTEGER,MARK TEXT,REWARDMARK TEXT,PAIDMARK TEXT,HEADPIC TEXT,GROUP_ID TEXT,MOBILEPHONE TEXT,EMAIL TEXT,CRTIME TEXT,ORGID TEXT,SPECIALTY TEXT,RESUME TEXT,LEVEL TEXT,STATUS TEXT,PTITLE TEXT,ORGNAME TEXT,IMEI TEXT,TYPE TEXT,SIM TEXT,REMARK TEXT,PROVINCE TEXT,CITY TEXT,STOCK_AGE TEXT,STOCK_STYLE TEXT,AUTOLOGIN INTEGER,LOGIN_TYPE INTEGER)";

	private static final String CREATE_TABLE_T_SU_ARI = "CREATE TABLE T_SU_ARI (_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,ADV_USER_ID TEXT,NAME TEXT,PASSWORD TEXT,REALNAME TEXT,ISLOCK INTEGER,SEX INTEGER,MARK TEXT,REWARDMARK TEXT,PAIDMARK TEXT,HEADPIC TEXT,GROUP_ID TEXT,MOBILEPHONE TEXT,EMAIL TEXT,CRTIME TEXT,ORGID TEXT,SPECIALTY TEXT,RESUME TEXT,LEVEL TEXT,STATUS TEXT,PTITLE TEXT,ORGNAME TEXT,OFFICIAL INTEGER, PAY_ATTENTION INTEGER,HEARTCOUNT TEXT)";

	private static final String CREATE_TABLE_T_SU_ARI_GZ = "CREATE TABLE T_SU_ARI_GZ (_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,ADV_USER_ID TEXT,NAME TEXT,PASSWORD TEXT,REALNAME TEXT,ISLOCK INTEGER,SEX INTEGER,MARK TEXT,REWARDMARK TEXT,PAIDMARK TEXT,HEADPIC TEXT,GROUP_ID TEXT,MOBILEPHONE TEXT,EMAIL TEXT,CRTIME TEXT,ORGID TEXT,SPECIALTY TEXT,RESUME TEXT,LEVEL TEXT,STATUS TEXT,PTITLE TEXT,ORGNAME TEXT,HEARTCOUNT TEXT)";

	public DBHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, null, version);
		this.context = context;
	}

	public DBHelper(Context context, String name, int version) {
		this(context, name, null, version);
	}

	public DBHelper(Context context, String name) {
		this(context, name, VERSION);
	}

	public DBHelper(Context context) {
		this(context, DB_PATH + DB_NAME);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		System.out.println("stockup.db ========== onCreate");
		db.execSQL(CREATE_TABLE_T_SU_NEWS);
		db.execSQL(CREATE_TABLE_T_SU_STOCKNEWS);
		db.execSQL(CREATE_TABLE_T_SU_STANDPOINT);
		db.execSQL(CREATE_TABLE_T_SU_USER);
		db.execSQL(CREATE_TABLE_T_SU_GUEST);
		db.execSQL(CREATE_TABLE_T_SU_EXPERT);
		db.execSQL(CREATE_TABLE_T_SU_NOWLIVING);
		db.execSQL(CREATE_TABLE_T_SU_ALLLIVING);
		db.execSQL(CREATE_TABLE_T_SU_HOTLIVING);
		db.execSQL(CREATE_TABLE_T_SU_ADT);
		db.execSQL(CREATE_TABLE_T_SU_MSG);
		db.execSQL(CREATE_TABLE_T_SU_LUI);
		db.execSQL(CREATE_TABLE_T_SU_ARI);
		db.execSQL(CREATE_TABLE_T_SU_A_MSG);
		db.execSQL(CREATE_TABLE_T_SU_ARI_GZ);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * "CREATE TABLE T_SU_NEWS (_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,ADMINID TEXT, COLTITLE TEXT,  CRTIME TEXT, ID TEXT,ORDERS TEXT,PICTURE TEXT,SOURCE TEXT,THUMBNAIL TEXT,TITLE TEXT)"
	 * ;
	 *
	 * @param data
	 */
	public void insNewsInfo(List<HomePageNews> data) {
		System.out.println("#SU DB# insAdviserInfo");
		SQLiteDatabase db = getWritableDatabase();
		for (int i = 0; i < data.size(); i++) {
			ContentValues values = new ContentValues();
			values.put("ADMINID", data.get(i).getAdminid());
			values.put("COLTITLE", data.get(i).getColtitle());
			values.put("CRTIME", data.get(i).getCrtime());
			values.put("HEADPIC", data.get(i).getHeadPic());
			values.put("ID", data.get(i).getId());
			values.put("ORDERS", data.get(i).getOrders());
			values.put("ORGNAME", data.get(i).getOrgName());
			values.put("PICTURE", data.get(i).getPicture());
			values.put("REALNAME", data.get(i).getRealName());
			values.put("SOURCE", data.get(i).getSource());
			values.put("THUMBNAIL", data.get(i).getThumbnail());
			values.put("TITLE", data.get(i).getTitle());
			values.put("NEWSCOMCOUNT", data.get(i).getNewsComCount());
			db.insert("T_SU_NEWS", "", values);
		}
		close();
	}

	public void insAdtInfo(List<HomePageNews> data) {
		System.out.println("#SU DB# insAdviserInfo");
		SQLiteDatabase db = getWritableDatabase();
		for (int i = 0; i < data.size(); i++) {
			ContentValues values = new ContentValues();
			values.put("ID", data.get(i).getId());
			values.put("ORDERS", data.get(i).getOrders());
			values.put("PICTURE", data.get(i).getPicture());
			values.put("TITLE", data.get(i).getTitle());
			db.insert("T_SU_ADT", "", values);
		}
		close();
	}
	
	public void insStockNewsInfo(List<HomePageNews> data) {
		System.out.println("#SU DB# insAdviserInfo");
		SQLiteDatabase db = getWritableDatabase();
		for (int i = 0; i < data.size(); i++) {
			ContentValues values = new ContentValues();
			values.put("ADMINID", data.get(i).getAdminid());
			values.put("COLTITLE", data.get(i).getColtitle());
//			values.put("COLTITLE", "");
			values.put("CRTIME", data.get(i).getCrtime());
			values.put("HEADPIC", data.get(i).getHeadPic());
			values.put("ID", data.get(i).getId());
			values.put("NEWSCOMCOUNT", data.get(i).getNewsComCount());
			values.put("ORDERS", data.get(i).getOrders());
			values.put("ORGNAME", data.get(i).getOrgName());
			values.put("PICTURE", data.get(i).getPicture());
			values.put("REALNAME", data.get(i).getRealName());
			values.put("SOURCE", data.get(i).getSource());
			values.put("THUMBNAIL", data.get(i).getThumbnail());
			values.put("TITLE", data.get(i).getTitle());
			db.insert("T_SU_STOCKNEWS", "", values);
		}
		close();
	}
	
	public void insStandPointInfo(List<HomePageNews> data) {
		System.out.println("#SU DB# insAdviserInfo");
		SQLiteDatabase db = getWritableDatabase();
		for (int i = 0; i < data.size(); i++) {
			ContentValues values = new ContentValues();
			values.put("ADMINID", data.get(i).getAdminid());
			values.put("COLTITLE", data.get(i).getColtitle());
//			values.put("COLTITLE", "");
			values.put("CRTIME", data.get(i).getCrtime());
			values.put("HEADPIC", data.get(i).getHeadPic());
			values.put("ID", data.get(i).getId());
			values.put("NEWSCOMCOUNT", data.get(i).getNewsComCount());
			values.put("ORDERS", data.get(i).getOrders());
			values.put("ORGNAME", data.get(i).getOrgName());
			values.put("PICTURE", data.get(i).getPicture());
			values.put("REALNAME", data.get(i).getRealName());
			values.put("SOURCE", data.get(i).getSource());
			values.put("THUMBNAIL", data.get(i).getThumbnail());
			values.put("TITLE", data.get(i).getTitle());
			db.insert("T_SU_STANDPOINT", "", values);
		}
		close();
	}
	
	public void insUserInfo(UserInfo data) {
		System.out.println("#SU DB# insUserInfo");
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("CHECKNUM", data.getchecknum());
		values.put("CHECKSTA", data.getchecksta());
		values.put("HEADPIC", data.getheadpic());
		values.put("ID", data.getid());
		values.put("LEVEL", data.getlevel());
		values.put("MARK", data.getmark());
		values.put("NAME", data.getname());
		values.put("PRESTIGE", data.getprestige());
		values.put("RANK", data.getrank());
		values.put("REALNAME", data.getrealname());
		values.put("BIRTH", data.getbirth());
		values.put("SEX", data.getsex());
		db.insert("T_SU_USER", "", values);
		close();
	}
	
	public void updateUser(String loginUserId, String name, String data) {
		ContentValues cv = new ContentValues();
		cv.put(name, data);
		SQLiteDatabase db = getWritableDatabase();
		db.update("T_SU_USER", cv, "ID=?", new String[] { loginUserId });
		close();
	}

	public List<HomePageNews> queryNewsInfo() {
		System.out.println("#SU DB# queryAdviserInfo");
		List<HomePageNews> list = new ArrayList<HomePageNews>();
		HomePageNews news = new HomePageNews();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM T_SU_NEWS", null);
		while (c.moveToNext()) {
			news = new HomePageNews();
			news.setAdminid(c.getString(c.getColumnIndex("ADMINID")));
			news.setColtitle(c.getString(c.getColumnIndex("COLTITLE")));
			news.setCrtime(c.getString(c.getColumnIndex("CRTIME")));
			news.setHeadPic(c.getString(c.getColumnIndex("HEADPIC")));
			news.setId(c.getString(c.getColumnIndex("ID")));
			news.setNewsComCount(c.getString(c.getColumnIndex("NEWSCOMCOUNT")));
			news.setOrders(c.getString(c.getColumnIndex("ORDERS")));
			news.setOrgName(c.getString(c.getColumnIndex("ORGNAME")));
			news.setPicture(c.getString(c.getColumnIndex("PICTURE")));
			news.setRealName(c.getString(c.getColumnIndex("REALNAME")));
			news.setSource(c.getString(c.getColumnIndex("SOURCE")));
			news.setThumbnail(c.getString(c.getColumnIndex("THUMBNAIL")));
			news.setTitle(c.getString(c.getColumnIndex("TITLE")));
			list.add(news);
		}
		close();
		return list;
	}

	public ArrayList<ADInfo> queryAdtInfo() {
		System.out.println("#SU DB# queryAdviserInfo");
		ArrayList<ADInfo> list = new ArrayList<ADInfo>();
		ADInfo adi = new ADInfo();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM T_SU_ADT", null);
		while (c.moveToNext()) {
			adi = new ADInfo();
			adi.setId(c.getString(c.getColumnIndex("ID")));
//			adi.setOrders(c.getString(c.getColumnIndex("ORDERS")));
			adi.setUrl(SOAP_UTILS.HTTP_ADT_PATH + c.getString(c.getColumnIndex("PICTURE")));
			adi.setTitle(c.getString(c.getColumnIndex("TITLE")));
			adi.setContent(c.getString(c.getColumnIndex("TITLE")));
			list.add(adi);
		}
		close();
		return list;
	}
	
	public List<HomePageNews> queryStockNewsInfo() {
		System.out.println("#SU DB# queryAdviserInfo");
		List<HomePageNews> list = new ArrayList<HomePageNews>();
		HomePageNews news = new HomePageNews();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM T_SU_STOCKNEWS", null);
		while (c.moveToNext()) {
			news = new HomePageNews();
			news.setAdminid(c.getString(c.getColumnIndex("ADMINID")));
			news.setColtitle(c.getString(c.getColumnIndex("COLTITLE")));
			news.setCrtime(c.getString(c.getColumnIndex("CRTIME")));
			news.setHeadPic(c.getString(c.getColumnIndex("HEADPIC")));
			news.setId(c.getString(c.getColumnIndex("ID")));
			news.setNewsComCount(c.getString(c.getColumnIndex("NEWSCOMCOUNT")));
			news.setOrders(c.getString(c.getColumnIndex("ORDERS")));
			news.setOrgName(c.getString(c.getColumnIndex("ORGNAME")));
			news.setPicture(c.getString(c.getColumnIndex("PICTURE")));
			news.setRealName(c.getString(c.getColumnIndex("REALNAME")));
			news.setSource(c.getString(c.getColumnIndex("SOURCE")));
			news.setThumbnail(c.getString(c.getColumnIndex("THUMBNAIL")));
			news.setTitle(c.getString(c.getColumnIndex("TITLE")));
			list.add(news);
		}
		close();
		return list;
	}
	
	public List<HomePageNews> queryStandPointInfo() {
		System.out.println("#SU DB# queryAdviserInfo");
		List<HomePageNews> list = new ArrayList<HomePageNews>();
		HomePageNews news = new HomePageNews();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM T_SU_STANDPOINT", null);
		while (c.moveToNext()) {
			news = new HomePageNews();
			news.setAdminid(c.getString(c.getColumnIndex("ADMINID")));
			news.setColtitle(c.getString(c.getColumnIndex("COLTITLE")));
			news.setCrtime(c.getString(c.getColumnIndex("CRTIME")));
			news.setHeadPic(c.getString(c.getColumnIndex("HEADPIC")));
			news.setId(c.getString(c.getColumnIndex("ID")));
			news.setNewsComCount(c.getString(c.getColumnIndex("NEWSCOMCOUNT")));
			news.setOrders(c.getString(c.getColumnIndex("ORDERS")));
			news.setOrgName(c.getString(c.getColumnIndex("ORGNAME")));
			news.setPicture(c.getString(c.getColumnIndex("PICTURE")));
			news.setRealName(c.getString(c.getColumnIndex("REALNAME")));
			news.setSource(c.getString(c.getColumnIndex("SOURCE")));
			news.setThumbnail(c.getString(c.getColumnIndex("THUMBNAIL")));
			news.setTitle(c.getString(c.getColumnIndex("TITLE")));
			list.add(news);
		}
		close();
		return list;
	}
	
	public List<UserInfo> queryUserInfo() {
		System.out.println("#SU DB# queryAdviserInfo");
		List<UserInfo> list = new ArrayList<UserInfo>();
		UserInfo user = new UserInfo();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM T_SU_USER", null);
		while (c.moveToNext()) {
			user = new UserInfo();
			user.setchecknum(c.getString(c.getColumnIndex("CHECKNUM")));
			user.setchecksta(c.getString(c.getColumnIndex("CHECKSTA")));
			user.setheadpic(c.getString(c.getColumnIndex("HEADPIC")));
			user.setid(c.getString(c.getColumnIndex("ID")));
			user.setlevel(c.getString(c.getColumnIndex("LEVEL")));
			user.setmark(c.getString(c.getColumnIndex("MARK")));
			user.setname(c.getString(c.getColumnIndex("NAME")));
			user.setprestige(c.getString(c.getColumnIndex("PRESTIGE")));
			user.setrank(c.getString(c.getColumnIndex("RANK")));
			user.setrealname(c.getString(c.getColumnIndex("REALNAME")));
			user.setsex(c.getString(c.getColumnIndex("SEX")));
			user.setbirth(c.getString(c.getColumnIndex("BIRTH")));
			list.add(user);
		}
		close();
		return list;
	}

	public void clearNewsData() {
		System.out.println("#SU DB# cheanLoginUserData");
		SQLiteDatabase db = getWritableDatabase();
		db.delete("T_SU_NEWS", null, null);
	}

	public void clearAdtData() {
		System.out.println("#SU DB# cheanLoginUserData");
		SQLiteDatabase db = getWritableDatabase();
		db.delete("T_SU_ADT", null, null);
	}
	
	public void clearStockNewsData() {
		System.out.println("#SU DB# cheanLoginUserData");
		SQLiteDatabase db = getWritableDatabase();
		db.delete("T_SU_STOCKNEWS", null, null);
	}
	
	public void clearStandPointData() {
		System.out.println("#SU DB# cheanLoginUserData");
		SQLiteDatabase db = getWritableDatabase();
		db.delete("T_SU_STANDPOINT", null, null);
	}
	
	public void clearUserInfoData() {
		System.out.println("#SU DB# cheanLoginUserData");
		SQLiteDatabase db = getWritableDatabase();
		db.delete("T_SU_USER", null, null);
	}

	public void insGuest(Guest data) {
		System.out.println("#SU DB# insGuest");
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("ANALYSIS", data.getanalysis());
		values.put("HEADPIC", data.getheadpic());
		values.put("WEBID", data.getwebid());
		values.put("LEVEL", data.getlevel());
		values.put("PAIDMARK", data.getpaidmark());
		values.put("REALNAME", data.getrealname());
		values.put("STOCKSTYLE", data.getstockstyle());
		values.put("RESUME", data.getresume());
		db.insert("T_SU_GUEST", "", values);
		close();
	}

	public void insGuestList(List<Guest> data) {
		System.out.println("#SU DB# insGuestList");
		SQLiteDatabase db = getWritableDatabase();
		for (int i = 0; i < data.size(); i++) {
			ContentValues values = new ContentValues();
			values.put("ANALYSIS", data.get(i).getanalysis());
			values.put("HEADPIC", data.get(i).getheadpic());
			values.put("WEBID", data.get(i).getwebid());
			values.put("LEVEL", data.get(i).getlevel());
			values.put("PAIDMARK", data.get(i).getpaidmark());
			values.put("REALNAME", data.get(i).getrealname());
			values.put("STOCKSTYLE", data.get(i).getstockstyle());
			values.put("RESUME", data.get(i).getresume());
			db.insert("T_SU_GUEST", "", values);
		}
		close();
	}

	public List<Guest> queryGuest() {
		System.out.println("#SU DB# queryGuest");
		List<Guest> list = new ArrayList<Guest>();
		Guest guest = new Guest();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM T_SU_GUEST", null);
		while (c.moveToNext()) {
			guest = new Guest();
			guest.setid(c.getString(c.getColumnIndex("_ID")));
			guest.setanalysis(c.getString(c.getColumnIndex("ANALYSIS")));
			guest.setheadpic(c.getString(c.getColumnIndex("HEADPIC")));
			guest.setwebid(c.getString(c.getColumnIndex("WEBID")));
			guest.setlevel(c.getString(c.getColumnIndex("LEVEL")));
			guest.setpaidmark(c.getString(c.getColumnIndex("PAIDMARK")));
			guest.setrealname(c.getString(c.getColumnIndex("REALNAME")));
			guest.setstockstyle(c.getString(c.getColumnIndex("STOCKSTYLE")));
			guest.setresume(c.getString(c.getColumnIndex("RESUME")));
			list.add(guest);
		}
		close();
		return list;
	}

	public void clearGuest() {
		System.out.println("#SU DB# clearGuest");
		SQLiteDatabase db = getWritableDatabase();
		db.delete("T_SU_GUEST", null, null);
	}

	public void insExpert(Expert data) {
		System.out.println("#SU DB# insExpert");
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("REWARDMARK", data.getrewardmark());
		values.put("HEADPIC", data.getheadpic());
		values.put("WEBID", data.getwebid());
		values.put("LEVEL", data.getlevel());
		values.put("MARK", data.getmark());
		values.put("REALNAME", data.getrealname());
		values.put("STOCKSTYLE", data.getstockstyle());
		values.put("RESUME", data.getresume());
		db.insert("T_SU_EXPERT", "", values);
		close();
	}

	public void insExpertList(List<Expert> data) {
		System.out.println("#SU DB# insExpertList");
		SQLiteDatabase db = getWritableDatabase();
		for (int i = 0; i < data.size(); i++) {
			ContentValues values = new ContentValues();
			values.put("REWARDMARK", data.get(i).getrewardmark());
			values.put("HEADPIC", data.get(i).getheadpic());
			values.put("WEBID", data.get(i).getwebid());
			values.put("LEVEL", data.get(i).getlevel());
			values.put("MARK", data.get(i).getmark());
			values.put("REALNAME", data.get(i).getrealname());
			values.put("STOCKSTYLE", data.get(i).getstockstyle());
			values.put("RESUME", data.get(i).getresume());
			db.insert("T_SU_EXPERT", "", values);
		}
		close();
	}

	public List<Expert> queryExpert() {
		System.out.println("#SU DB# queryExpert");
		List<Expert> list = new ArrayList<Expert>();
		Expert expert = new Expert();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM T_SU_EXPERT", null);
		while (c.moveToNext()) {
			expert = new Expert();
			expert.setid(c.getString(c.getColumnIndex("_ID")));
			expert.setrewardmark(c.getString(c.getColumnIndex("REWARDMARK")));
			expert.setheadpic(c.getString(c.getColumnIndex("HEADPIC")));
			expert.setwebid(c.getString(c.getColumnIndex("WEBID")));
			expert.setlevel(c.getString(c.getColumnIndex("LEVEL")));
			expert.setmark(c.getString(c.getColumnIndex("MARK")));
			expert.setrealname(c.getString(c.getColumnIndex("REALNAME")));
			expert.setstockstyle(c.getString(c.getColumnIndex("STOCKSTYLE")));
			expert.setresume(c.getString(c.getColumnIndex("RESUME")));
			list.add(expert);
		}
		close();
		return list;
	}

	public void clearExpert() {
		System.out.println("#SU DB# clearExpert");
		SQLiteDatabase db = getWritableDatabase();
		db.delete("T_SU_EXPERT", null, null);
	}
	
	
//正在直播
	public void insNowLiving(NowLiving data) {
		System.out.println("#SU DB# insNowLiving");
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("ANSWERCOUNT", data.getAnswerCount());
		values.put("CRTIMESTR", data.getCrtimeStr());
		values.put("DEALADVISE", data.getDealAdvise());
		values.put("DEALCONTROL", data.getDealControl());
		values.put("DEALOPERATE", data.getDealOperate());
		values.put("ID", data.getId());
		values.put("LAUD", data.getLaud());
		values.put("LIVECONTENT", data.getLiveContent());
		values.put("LIVECOUNT", data.getLiveCount());
		values.put("LIVEUSERID", data.getLiveUserId());
		values.put("LIVEUSERNAME", data.getLiveUserName());
		values.put("LIVINGS", data.getLivings());
		values.put("TODAYVIEWPOINT", data.getTodayViewPoint());
		values.put("USERHEADPIC", data.getUserHeadpic());
		values.put("USERRESUME", data.getUserResume());
		db.insert("T_SU_NOWLIVING", "", values);
		close();
	}

	public void insNowLivingList(List<NowLiving> data) {
		System.out.println("#SU DB# insNowLivingList");
		SQLiteDatabase db = getWritableDatabase();
		for (int i = 0; i < data.size(); i++) {
			ContentValues values = new ContentValues();
			values.put("ANSWERCOUNT", data.get(i).getAnswerCount());
			values.put("CRTIMESTR", data.get(i).getCrtimeStr());
			values.put("DEALADVISE", data.get(i).getDealAdvise());
			values.put("DEALCONTROL", data.get(i).getDealControl());
			values.put("DEALOPERATE", data.get(i).getDealOperate());
			values.put("ID", data.get(i).getId());
			values.put("LAUD", data.get(i).getLaud());
			values.put("LIVECONTENT", data.get(i).getLiveContent());
			values.put("LIVECOUNT", data.get(i).getLiveUserId());
			values.put("LIVEUSERID", data.get(i).getLiveUserName());
			values.put("LIVEUSERNAME", data.get(i).getLivings());
			values.put("LIVINGS", data.get(i).getTodayViewPoint());
			values.put("TODAYVIEWPOINT", data.get(i).getUserHeadpic());
			values.put("USERHEADPIC", data.get(i).getUserResume());
			values.put("USERRESUME", data.get(i).getUserResume());
			db.insert("T_SU_NOWLIVING", "", values);

		}
		close();
	}

	public List<NowLiving> queryNowLiving() {
		System.out.println("#SU DB# queryNowLiving");
		List<NowLiving> list = new ArrayList<NowLiving>();
		NowLiving nowliving = new NowLiving();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM T_SU_NOWLIVING", null);
		while (c.moveToNext()) {
			nowliving = new NowLiving();
			nowliving.setAnswerCount(c.getString(c.getColumnIndex("ANSWERCOUNT")));
			nowliving.setCrtimeStr(c.getString(c.getColumnIndex("CRTIMESTR")));
			nowliving.setDealAdvise(c.getString(c.getColumnIndex("DEALADVISE")));
			nowliving.setDealControl(c.getString(c.getColumnIndex("DEALCONTROL")));
			nowliving.setDealOperate(c.getString(c.getColumnIndex("DEALOPERATE")));
			nowliving.setId(c.getString(c.getColumnIndex("ID")));
			nowliving.setLaud(c.getString(c.getColumnIndex("LAUD")));
			nowliving.setLiveContent(c.getString(c.getColumnIndex("LIVECONTENT")));
			nowliving.setLiveCount(c.getString(c.getColumnIndex("LIVECOUNT")));
			nowliving.setLiveUserId(c.getString(c.getColumnIndex("LIVEUSERID")));
			nowliving.setLiveUserName(c.getString(c.getColumnIndex("LIVEUSERNAME")));
			nowliving.setLivings(c.getString(c.getColumnIndex("LIVINGS")));
			nowliving.setTodayViewPoint(c.getString(c.getColumnIndex("TODAYVIEWPOINT")));
			nowliving.setUserHeadpic(c.getString(c.getColumnIndex("USERHEADPIC")));
			nowliving.setUserResume(c.getString(c.getColumnIndex("USERRESUME")));
			list.add(nowliving);
		}
		close();
		return list;
	}

	public void clearNowLiving() {
		System.out.println("#SU DB# clearNowLiving");
		SQLiteDatabase db = getWritableDatabase();
		db.delete("T_SU_NOWLIVING", null, null);
	}
	

	//热门直播
		public void insHotLiving(HotLiving data) {
			System.out.println("#SU DB# insHotLiving");
			SQLiteDatabase db = getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("ANSWERCOUNT", data.getAnswerCount());
			values.put("CRTIMESTR", data.getCrtimeStr());
			values.put("DEALADVISE", data.getDealAdvise());
			values.put("DEALCONTROL", data.getDealControl());
			values.put("DEALOPERATE", data.getDealOperate());
			values.put("ID", data.getId());
			values.put("LAUD", data.getLaud());
			values.put("LIVECONTENT", data.getLiveContent());
			values.put("LIVECOUNT", data.getLiveCount());
			values.put("LIVEUSERID", data.getLiveUserId());
			values.put("LIVEUSERNAME", data.getLiveUserName());
			values.put("LIVINGS", data.getLivings());
			values.put("TODAYVIEWPOINT", data.getTodayViewPoint());
			values.put("USERHEADPIC", data.getUserHeadpic());
			values.put("USERRESUME", data.getUserResume());
			db.insert("T_SU_HOTLIVING", "", values);
			close();
		}

		public void insHotLivingList(List<HotLiving> data) {
			System.out.println("#SU DB# insHotLivingList");
			SQLiteDatabase db = getWritableDatabase();
			for (int i = 0; i < data.size(); i++) {
				ContentValues values = new ContentValues();
				values.put("ANSWERCOUNT", data.get(i).getAnswerCount());
				values.put("CRTIMESTR", data.get(i).getCrtimeStr());
				values.put("DEALADVISE", data.get(i).getDealAdvise());
				values.put("DEALCONTROL", data.get(i).getDealControl());
				values.put("DEALOPERATE", data.get(i).getDealOperate());
				values.put("ID", data.get(i).getId());
				values.put("LAUD", data.get(i).getLaud());
				values.put("LIVECONTENT", data.get(i).getLiveContent());
				values.put("LIVECOUNT", data.get(i).getLiveUserId());
				values.put("LIVEUSERID", data.get(i).getLiveUserName());
				values.put("LIVEUSERNAME", data.get(i).getLivings());
				values.put("LIVINGS", data.get(i).getTodayViewPoint());
				values.put("TODAYVIEWPOINT", data.get(i).getUserHeadpic());
				values.put("USERHEADPIC", data.get(i).getUserResume());
				values.put("USERRESUME", data.get(i).getUserResume());
				db.insert("T_SU_HOTLIVING", "", values);

			}
			close();
		}

		public List<HotLiving> queryHotLiving() {
			System.out.println("#SU DB# queryHotLiving");
			List<HotLiving> list = new ArrayList<HotLiving>();
			HotLiving allliving = new HotLiving();
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor c = db.rawQuery("SELECT * FROM T_SU_HOTLIVING", null);
			while (c.moveToNext()) {
				allliving = new HotLiving();
				allliving.setAnswerCount(c.getString(c.getColumnIndex("ANSWERCOUNT")));
				allliving.setCrtimeStr(c.getString(c.getColumnIndex("CRTIMESTR")));
				allliving.setDealAdvise(c.getString(c.getColumnIndex("DEALADVISE")));
				allliving.setDealControl(c.getString(c.getColumnIndex("DEALCONTROL")));
				allliving.setDealOperate(c.getString(c.getColumnIndex("DEALOPERATE")));
				allliving.setId(c.getString(c.getColumnIndex("ID")));
				allliving.setLaud(c.getString(c.getColumnIndex("LAUD")));
				allliving.setLiveContent(c.getString(c.getColumnIndex("LIVECONTENT")));
				allliving.setLiveCount(c.getString(c.getColumnIndex("LIVECOUNT")));
				allliving.setLiveUserId(c.getString(c.getColumnIndex("LIVEUSERID")));
				allliving.setLiveUserName(c.getString(c.getColumnIndex("LIVEUSERNAME")));
				allliving.setLivings(c.getString(c.getColumnIndex("LIVINGS")));
				allliving.setTodayViewPoint(c.getString(c.getColumnIndex("TODAYVIEWPOINT")));
				allliving.setUserHeadpic(c.getString(c.getColumnIndex("USERHEADPIC")));
				allliving.setUserResume(c.getString(c.getColumnIndex("USERRESUME")));
				list.add(allliving);
			}
			close();
			return list;
		}

		public void clearHotLiving() {
			System.out.println("#SU DB# clearHotLiving");
			SQLiteDatabase db = getWritableDatabase();
			db.delete("T_SU_HOTLIVING", null, null);
		}
		

		//全部直播
			public void insAllLiving(AllLiving data) {
				System.out.println("#SU DB# insAllLiving");
				SQLiteDatabase db = getWritableDatabase();
				ContentValues values = new ContentValues();
				values.put("ANSWERCOUNT", data.getAnswerCount());
				values.put("CRTIMESTR", data.getCrtimeStr());
				values.put("DEALADVISE", data.getDealAdvise());
				values.put("DEALCONTROL", data.getDealControl());
				values.put("DEALOPERATE", data.getDealOperate());
				values.put("ID", data.getId());
				values.put("LAUD", data.getLaud());
				values.put("LIVECONTENT", data.getLiveContent());
				values.put("LIVECOUNT", data.getLiveCount());
				values.put("LIVEUSERID", data.getLiveUserId());
				values.put("LIVEUSERNAME", data.getLiveUserName());
				values.put("LIVINGS", data.getLivings());
				values.put("TODAYVIEWPOINT", data.getTodayViewPoint());
				values.put("USERHEADPIC", data.getUserHeadpic());
				values.put("USERRESUME", data.getUserResume());
				db.insert("T_SU_ALLLIVING", "", values);
				close();
			}

			public void insAllLivingList(List<AllLiving> data) {
				System.out.println("#SU DB# insAllLivingList");
				SQLiteDatabase db = getWritableDatabase();
				for (int i = 0; i < data.size(); i++) {
					ContentValues values = new ContentValues();
					values.put("ANSWERCOUNT", data.get(i).getAnswerCount());
					values.put("CRTIMESTR", data.get(i).getCrtimeStr());
					values.put("DEALADVISE", data.get(i).getDealAdvise());
					values.put("DEALCONTROL", data.get(i).getDealControl());
					values.put("DEALOPERATE", data.get(i).getDealOperate());
					values.put("ID", data.get(i).getId());
					values.put("LAUD", data.get(i).getLaud());
					values.put("LIVECONTENT", data.get(i).getLiveContent());
					values.put("LIVECOUNT", data.get(i).getLiveUserId());
					values.put("LIVEUSERID", data.get(i).getLiveUserName());
					values.put("LIVEUSERNAME", data.get(i).getLivings());
					values.put("LIVINGS", data.get(i).getTodayViewPoint());
					values.put("TODAYVIEWPOINT", data.get(i).getUserHeadpic());
					values.put("USERHEADPIC", data.get(i).getUserResume());
					values.put("USERRESUME", data.get(i).getUserResume());
					db.insert("T_SU_ALLLIVING", "", values);

				}
				close();
			}

			public List<AllLiving> queryAllLiving() {
				System.out.println("#SU DB# queryAllLiving");
				List<AllLiving> list = new ArrayList<AllLiving>();
				AllLiving allliving = new AllLiving();
				SQLiteDatabase db = this.getReadableDatabase();
				Cursor c = db.rawQuery("SELECT * FROM T_SU_ALLLIVING", null);
				while (c.moveToNext()) {
					allliving = new AllLiving();
					allliving.setAnswerCount(c.getString(c.getColumnIndex("ANSWERCOUNT")));
					allliving.setCrtimeStr(c.getString(c.getColumnIndex("CRTIMESTR")));
					allliving.setDealAdvise(c.getString(c.getColumnIndex("DEALADVISE")));
					allliving.setDealControl(c.getString(c.getColumnIndex("DEALCONTROL")));
					allliving.setDealOperate(c.getString(c.getColumnIndex("DEALOPERATE")));
					allliving.setId(c.getString(c.getColumnIndex("ID")));
					allliving.setLaud(c.getString(c.getColumnIndex("LAUD")));
					allliving.setLiveContent(c.getString(c.getColumnIndex("LIVECONTENT")));
					allliving.setLiveCount(c.getString(c.getColumnIndex("LIVECOUNT")));
					allliving.setLiveUserId(c.getString(c.getColumnIndex("LIVEUSERID")));
					allliving.setLiveUserName(c.getString(c.getColumnIndex("LIVEUSERNAME")));
					allliving.setLivings(c.getString(c.getColumnIndex("LIVINGS")));
					allliving.setTodayViewPoint(c.getString(c.getColumnIndex("TODAYVIEWPOINT")));
					allliving.setUserHeadpic(c.getString(c.getColumnIndex("USERHEADPIC")));
					allliving.setUserResume(c.getString(c.getColumnIndex("USERRESUME")));
					list.add(allliving);
				}
				close();
				return list;
			}

			public void clearAllLiving() {
				System.out.println("#SU DB# clearAllLiving");
				SQLiteDatabase db = getWritableDatabase();
				db.delete("T_SU_ALLLIVING", null, null);
			}
			
	/**
	 * 顾问模块列表
	 */

	/**
	 * 
	 * CREATE TABLE T_SU_ARI (_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	 * ADV_USER_ID TEXT, NAME TEXT, PASSWORD TEXT ,REALNAME TEXT, ISLOCK
	 * INTEGER, SEX INTEGER, MARK TEXT, REWARDMARK TEXT, PAIDMARK TEXT, HEADPIC
	 * TEXT, GROUP_ID TEXT, MOBILEPHONE TEXT, EMAIL TEXT, CRTIME TEXT, ORGID
	 * TEXT, SPECIALTY TEXT ,RESUME TEXT, LEVEL TEXT ,STATUS TEXT, PTITLE TEXT,
	 * ORGNAME TEXT)
	 * 
	 * @param data
	 */
	public void insAdviserInfo(List<Adviser> data) {
		System.out.println("#SU DB# insAdviserInfo");
		SQLiteDatabase db = getWritableDatabase();
		for (int i = 0; i < data.size(); i++) {
			ContentValues values = new ContentValues();
			values.put("ADV_USER_ID", data.get(i).getAdv_user_id());
			values.put("NAME", data.get(i).getName());
			values.put("PASSWORD", data.get(i).getPassword());
			values.put("REALNAME", data.get(i).getRealname());
			values.put("ISLOCK", data.get(i).isIslock() == true ? 0 : 1);
			values.put("SEX", data.get(i).getSex());
			values.put("MARK", data.get(i).getMark());
			values.put("REWARDMARK", data.get(i).getRewardmark());
			values.put("PAIDMARK", data.get(i).getPaidmark());
			values.put("HEADPIC", data.get(i).getHeadpic());
			values.put("GROUP_ID", data.get(i).getGroupid());
			values.put("MOBILEPHONE", data.get(i).getMobilephone());
			values.put("EMAIL", data.get(i).getEmail());
			values.put("CRTIME", data.get(i).getCrtime());
			values.put("ORGID", data.get(i).getOrgid());
			values.put("SPECIALTY", data.get(i).getSpecialty());
			values.put("RESUME", data.get(i).getResume());
			values.put("LEVEL", data.get(i).getLevel());
			values.put("STATUS", data.get(i).getStatus());
			values.put("PTITLE", data.get(i).getPtitle());
			values.put("ORGNAME", data.get(i).getOrgname());
			values.put("HEARTCOUNT", data.get(i).getHeartcount());
			values.put("OFFICIAL", 0);
			values.put("PAY_ATTENTION", 0);
			db.insert("T_SU_ARI", "", values);
		}
		close();
	}

	public int clearAllAdviser() {
		System.out.println("#SU DB# clearAllAdviser");
		SQLiteDatabase db = getWritableDatabase();
		return db.delete("T_SU_ARI", null, null);
	}

	public List<Adviser> queryAdviserInfo() {
		System.out.println("#SU DB# queryAdviserInfo");
		List<Adviser> list = new ArrayList<Adviser>();
		Adviser adviser = new Adviser();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM T_SU_ARI", null);
		while (c.moveToNext()) {
			adviser = new Adviser();
			adviser.setAdv_user_id(c.getString(c.getColumnIndex("ADV_USER_ID")));
			adviser.setCrtime(c.getString(c.getColumnIndex("CRTIME")));
			adviser.setEmail(c.getString(c.getColumnIndex("EMAIL")));
			adviser.setGroupid(c.getString(c.getColumnIndex("GROUP_ID")));
			adviser.setHeadpic(c.getString(c.getColumnIndex("HEADPIC")));
			adviser.setSex(c.getString(c.getColumnIndex("SEX")));
			adviser.setIslock(c.getInt(c.getColumnIndex("ISLOCK")) == 0 ? true : false);
			adviser.setLevel(c.getString(c.getColumnIndex("LEVEL")));
			adviser.setMark(c.getString(c.getColumnIndex("MARK")));
			adviser.setMobilephone(c.getString(c.getColumnIndex("MOBILEPHONE")));
			adviser.setName(c.getString(c.getColumnIndex("NAME")));
			adviser.setOrgid(c.getString(c.getColumnIndex("ORGID")));
			adviser.setOrgname(c.getString(c.getColumnIndex("ORGNAME")));
			adviser.setPaidmark(c.getString(c.getColumnIndex("PAIDMARK")));
			adviser.setPassword(c.getString(c.getColumnIndex("PASSWORD")));
			adviser.setPtitle(c.getString(c.getColumnIndex("PTITLE")));
			adviser.setRealname(c.getString(c.getColumnIndex("REALNAME")));
			adviser.setRewardmark(c.getString(c.getColumnIndex("REWARDMARK")));
			adviser.setResume(c.getString(c.getColumnIndex("RESUME")));
			adviser.setSpecialty(c.getString(c.getColumnIndex("SPECIALTY")));
			adviser.setStatus(c.getString(c.getColumnIndex("STATUS")));
			adviser.setHeartcount(c.getString(c.getColumnIndex("HEARTCOUNT")));
			System.out.println(">>>??@@@" + adviser.getName());
			System.out.println(">>>??@@@" + adviser.getHeartcount());
			list.add(adviser);
		}
		close();
		return list;
	}

	public Adviser queryAdviserInfoById(String id) {
		System.out.println("#SU DB# queryAdviserInfo");
		List<Adviser> list = new ArrayList<Adviser>();
		Adviser adviser = new Adviser();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM T_SU_ARI WHERE ADV_USER_ID = " + id, null);
		while (c.moveToNext()) {
			adviser = new Adviser();
			adviser.setAdv_user_id(c.getString(c.getColumnIndex("ADV_USER_ID")));
			adviser.setCrtime(c.getString(c.getColumnIndex("CRTIME")));
			adviser.setEmail(c.getString(c.getColumnIndex("EMAIL")));
			adviser.setGroupid(c.getString(c.getColumnIndex("GROUP_ID")));
			adviser.setHeadpic(c.getString(c.getColumnIndex("HEADPIC")));
			adviser.setSex(c.getString(c.getColumnIndex("SEX")));
			adviser.setIslock(c.getInt(c.getColumnIndex("ISLOCK")) == 0 ? true : false);
			adviser.setLevel(c.getString(c.getColumnIndex("LEVEL")));
			adviser.setMark(c.getString(c.getColumnIndex("MARK")));
			adviser.setMobilephone(c.getString(c.getColumnIndex("MOBILEPHONE")));
			adviser.setName(c.getString(c.getColumnIndex("NAME")));
			adviser.setOrgid(c.getString(c.getColumnIndex("ORGID")));
			adviser.setOrgname(c.getString(c.getColumnIndex("ORGNAME")));
			adviser.setPaidmark(c.getString(c.getColumnIndex("PAIDMARK")));
			adviser.setPassword(c.getString(c.getColumnIndex("PASSWORD")));
			adviser.setPtitle(c.getString(c.getColumnIndex("PTITLE")));
			adviser.setRealname(c.getString(c.getColumnIndex("REALNAME")));
			adviser.setRewardmark(c.getString(c.getColumnIndex("REWARDMARK")));
			adviser.setResume(c.getString(c.getColumnIndex("RESUME")));
			adviser.setSpecialty(c.getString(c.getColumnIndex("SPECIALTY")));
			adviser.setStatus(c.getString(c.getColumnIndex("STATUS")));
			adviser.setHeartcount(c.getString(c.getColumnIndex("HEARTCOUNT")));
			System.out.println(">>>##@@@" + adviser.getName());
			System.out.println(">>>##@@@" + adviser.getHeartcount());
		}
		close();
		return adviser;
	}

	public String getAdviserHeader(String aid) {
		System.out.println("#SU DB# getAdviserHeader");
		String header = "";
		List<Adviser> list = new ArrayList<Adviser>();
		Adviser adviser = new Adviser();
		SQLiteDatabase db = this.getReadableDatabase();
		String[] str = { aid };
		Cursor c = db.rawQuery("SELECT * FROM T_SU_ARI WHERE ADV_USER_ID = ?", str);
		while (c.moveToNext()) {
			adviser = new Adviser();
			header = c.getString(c.getColumnIndex("HEADPIC"));

			System.out.println("> pic >>??" + header);
			list.add(adviser);
		}
		close();
		return header;
	}

	/**
	 * 聊天模块
	 */

	/**
	 * 
	 * CREATE TABLE T_SU_MSG (_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	 * STATUS INTEGER, CONTENT1 TEXT, CONTENT2 BLOB, CONTENT3_TITLE TEXT,
	 * CONTENT3_CONTENT TEXT, CONTENT3_URL TEXT, CONTENT3_IMG TEXT,
	 * MSG_DIRECTION INTEGER, MSG_TYPE INTEGER, MSG_DATE TEXT, LOGIN_ID TEXT,
	 * SENDER_ID TEXT
	 * 
	 * @param cm
	 */
	public void insMsgData(ChatMessage cm) {
		System.out.println("#SU DB# insMsgData");
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("STATUS", cm.getStatus());
		values.put("CONTENT1", cm.getContent1());
		values.put("CONTENT2", cm.getContent2());
		values.put("CONTENT3_TITLE", cm.getContent3_title());
		values.put("CONTENT3_CONTENT", cm.getContent3_content());
		values.put("CONTENT3_URL", cm.getContent3_url());
		values.put("CONTENT3_IMG", cm.getContent3_img());
		values.put("MSG_DIRECTION", cm.getMsg_direction());
		values.put("MSG_TYPE", cm.getMsg_type());
		values.put("MSG_DATE", cm.getMsg_send_date());
		values.put("LOGIN_ID", cm.getLogin_id());
		values.put("SENDER_ID", cm.getSender_id());
		// values.put("HEADERPIC", cm.getUserinfo().getUserheader());
		// values.put("SENDERNAME", cm.getUserinfo().getUsername());
		db.insert("T_SU_MSG", "", values);
		close();
	}

	public void insAMsgData(ChatMessage cm) {
		System.out.println("#SU DB# insAMsgData" + "id : " + cm.getLogin_id());
	}

	public List<ChatMessage> queryMsgData(Adviser adviser, LoginUser loginuser) {
		return null;
	}

	/**
	 * 顾问团消息表
	 * 
	 * @param adviser
	 * @param loginuser
	 * @return
	 */

	public List<ChatMessage> queryAMsgData(UserInfo ui, LoginUser loginuser) {
		System.out.println("#SU DB# queryAMsgData");
		List<ChatMessage> list = new ArrayList<ChatMessage>();
		return list;
	}

	public List<Information_ListBean> getRecentMsgList(LoginUser loginUser) {
		// SELECT
		// MAX(_ID),STATUS,CONTENT1,CONTENT2,CONTENT3_TITLE,CONTENT3_CONTENT,CONTENT3_URL,CONTENT3_IMG,MSG_DIRECTION,MSG_TYPE,MSG_DATE,SENDER_ID
		// FROM T_SU_MSG WHERE LOGIN_ID = ?
		System.out.println("#SU DB# getRecentMsgList");
		List<Information_ListBean> list = new ArrayList<Information_ListBean>();
		Information_ListBean ib = new Information_ListBean();
		List<Adviser> adv_list = new ArrayList<Adviser>();
		adv_list = queryAdviserInfo();
		SQLiteDatabase db = this.getReadableDatabase();
		if (adv_list != null) {

			for (int i = 0; i < adv_list.size(); i++) {
				String[] param = { loginUser.getUserid(), adv_list.get(i).getAdv_user_id() };
				Cursor c = db.rawQuery(
						"SELECT MAX(_ID) AS _ID,STATUS,CONTENT1,CONTENT2,CONTENT3_TITLE,CONTENT3_CONTENT,CONTENT3_URL,CONTENT3_IMG,MSG_DIRECTION,MSG_TYPE,MSG_DATE,SENDER_ID FROM T_SU_MSG WHERE LOGIN_ID = ? and SENDER_ID = ?",
						param);
				while (c.moveToNext()) {
					ib = new Information_ListBean();
					ib.setAnalyst(c.getString(c.getColumnIndex("SENDER_ID")));

					switch (c.getInt(c.getColumnIndex("MSG_TYPE"))) {
					case 0:
						ib.setContent(c.getString(c.getColumnIndex("CONTENT1")));
						break;
					case 1:
						ib.setContent("语音消息");
						break;
					case 2:
						ib.setContent(c.getString(c.getColumnIndex("CONTENT3_TITLE")));
						break;

					default:
						break;
					}
					ib.set_id(c.getInt(c.getColumnIndex("_ID")));
					ib.setDate(c.getString(c.getColumnIndex("MSG_DATE")));
					ib.setImgUrl(adv_list.get(i).getHeadpic());
					// TODO
					ib.setNotifType(2);
					ib.setAdviser(adv_list.get(i));
					ib.setTime(getUnreadMsgCount(loginUser, adv_list.get(i)) + "");
					ib.setTitle(adv_list.get(i).getRealname());
					if (ib.getContent() == null || ib.getContent().equals("")) {

					} else {
						list.add(ib);
					}
				}
			}
		}
		close();
		// for (Information_ListBean p : list) {
		// System.out.println(">!" + p.get_id());
		// }
		Collections.sort(list, new Comparator<Information_ListBean>() {
			public int compare(Information_ListBean arg0, Information_ListBean arg1) {
				int res = -2;
				int num1 = arg0.get_id();
				int num2 = arg1.get_id();
				// System.out.println(">>>" + num1 + " " + num2);
				if (num1 > num2) {
					res = 1;
				} else if (num1 < num2) {
					res = -1;
				} else {
					res = 0;
				}

				return res;
			}
		});

		//
		// list.add(queryAdviserGz());
		// list.add(queryAdviserNs());
		Collections.reverse(list);
		// for (Information_ListBean p : list) {
		// System.out.println(">?" + p.get_id());
		// }

		return list;
	}

	public List<Information_ListBean> getRecentMsgListSuper(LoginUser loginUser) {
		// SELECT
		// MAX(_ID),STATUS,CONTENT1,CONTENT2,CONTENT3_TITLE,CONTENT3_CONTENT,CONTENT3_URL,CONTENT3_IMG,MSG_DIRECTION,MSG_TYPE,MSG_DATE,SENDER_ID
		// FROM T_SU_MSG WHERE LOGIN_ID = ?
		List<Information_ListBean> tempdata = new ArrayList<Information_ListBean>();
		List<Information_ListBean> tempdata2 = new ArrayList<Information_ListBean>();
		System.out.println("#SU DB# getRecentMsgList");
		List<Information_ListBean> list = new ArrayList<Information_ListBean>();
		Information_ListBean ib = new Information_ListBean();
		List<Adviser> adv_list = new ArrayList<Adviser>();
		adv_list.add(queryAdviserGz());
		adv_list.add(queryAdviserNs());
		SQLiteDatabase db = this.getReadableDatabase();
		if (adv_list != null) {

			for (int i = 0; i < adv_list.size(); i++) {
				String[] param = { loginUser.getUserid(), adv_list.get(i).getAdv_user_id() };
				Cursor c = db.rawQuery(
						"SELECT MAX(_ID) AS _ID,STATUS,CONTENT1,CONTENT2,CONTENT3_TITLE,CONTENT3_CONTENT,CONTENT3_URL,CONTENT3_IMG,MSG_DIRECTION,MSG_TYPE,MSG_DATE,SENDER_ID FROM T_SU_MSG WHERE LOGIN_ID = ? and SENDER_ID = ?",
						param);
				while (c.moveToNext()) {
					ib = new Information_ListBean();
					ib.setAnalyst(c.getString(c.getColumnIndex("SENDER_ID")));

					switch (c.getInt(c.getColumnIndex("MSG_TYPE"))) {
					case 0:
						ib.setContent(c.getString(c.getColumnIndex("CONTENT1")));
						break;
					case 1:
						ib.setContent("语音消息");
						break;
					case 2:
						ib.setContent(c.getString(c.getColumnIndex("CONTENT3_TITLE")));
						break;

					default:
						break;
					}
					ib.set_id(c.getInt(c.getColumnIndex("_ID")));
					ib.setDate(c.getString(c.getColumnIndex("MSG_DATE")));
					ib.setImgUrl(adv_list.get(i).getHeadpic());
					// TODO
					if (adv_list.get(i).getAdv_user_id().toString().equals("-1")) {
						ib.setNotifType(1);
					} else if (adv_list.get(i).getAdv_user_id().toString().equals("1")) {
						ib.setNotifType(0);
					} else {
						ib.setNotifType(2);
					}

					ib.setAdviser(adv_list.get(i));
					ib.setTime(getUnreadMsgCount(loginUser, adv_list.get(i)) + "");
					ib.setTitle(adv_list.get(i).getRealname());
					if (ib.getContent() == null || ib.getContent().equals("")) {

					} else {
						list.add(ib);
					}
				}
			}
		}
		close();

		tempdata.add(list.get(0));
		tempdata.add(list.get(1));
		tempdata2 = getRecentMsgList(loginUser);
		for (int i = 0; i < tempdata2.size(); i++) {
			tempdata.add(tempdata2.get(i));
		}
		// tempdata = getRecentMsgList(loginUser);

		return tempdata;
	}

	public List<Information_ListBean> getRecentAMsgList(LoginUser loginUser) {
		System.out.println("#SU DB# getaARecentMsgList  id : " + loginUser.getUserid());
		List<Information_ListBean> list = new ArrayList<Information_ListBean>();

		return list;
	}

	public List<String> getCustomIds(String loginId) {
		List<String> list = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		String[] param = { loginId };
		Cursor c = db.rawQuery("SELECT DISTINCT SENDER_ID FROM T_SU_A_MSG WHERE LOGIN_ID = ?", param);
		while (c.moveToNext()) {
			list.add(c.getString(c.getColumnIndex("SENDER_ID")));
		}
		System.out.println("#SU DB# getCustomIds   size : " + list.size());
		return list;
	}

	public int getUnreadMsgCount(LoginUser loginUser, Adviser adviser) {
		int count = -1;
		SQLiteDatabase db = this.getReadableDatabase();
		String[] param = { loginUser.getUserid(), adviser.getAdv_user_id() };
		Cursor c = db.rawQuery(
				"SELECT COUNT(_ID) AS COUNT FROM T_SU_MSG WHERE STATUS = 0 AND LOGIN_ID = ? AND SENDER_ID = ?", param);
		while (c.moveToNext()) {
			count = c.getInt(c.getColumnIndex("COUNT"));
		}
		return count;
	}

	public int getUnreadAMsgCount(LoginUser loginUser, String senderid) {
		int count = -1;
		SQLiteDatabase db = this.getReadableDatabase();
		String[] param = { loginUser.getUserid(), senderid };
		Cursor c = db.rawQuery(
				"SELECT COUNT(_ID) AS COUNT FROM T_SU_A_MSG WHERE STATUS = 0 AND LOGIN_ID = ? AND SENDER_ID = ?",
				param);
		while (c.moveToNext()) {
			count = c.getInt(c.getColumnIndex("COUNT"));
		}
		return count;
	}

	public void setMsgReaded(String loginUserId, String senderId) {
		ContentValues cv = new ContentValues();
		cv.put("STATUS", "1");
		SQLiteDatabase db = getWritableDatabase();
		db.update("T_SU_MSG", cv, "LOGIN_ID=? AND SENDER_ID=?", new String[] { loginUserId, senderId });
		close();
	}

	public void setAMsgReaded(String loginUserId, String senderId) {
		ContentValues cv = new ContentValues();
		cv.put("STATUS", "1");
		SQLiteDatabase db = getWritableDatabase();
		db.update("T_SU_A_MSG", cv, "LOGIN_ID=? AND SENDER_ID=?", new String[] { loginUserId, senderId });
		close();
	}

	/**
	 * 登陆者
	 * 
	 * @param loginUser
	 */

	public void insLoginUserInfo(LoginUser loginUser) {
		System.out.println("#SU DB# insLoginUserInfo");
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("AUTOLOGIN", loginUser.getAutologin());
		values.put("CRTIME", loginUser.getCrtime());
		values.put("CITY", loginUser.getCity());
		values.put("EMAIL", loginUser.getEmail());
		values.put("GROUP_ID", loginUser.getGroupid());
		values.put("HEADPIC", loginUser.getHeadpic());
		values.put("IMEI", loginUser.getImei());
		values.put("ISLOCK", loginUser.getIslock());
		values.put("LEVEL", loginUser.getLevel());
		values.put("LOGIN_TYPE", loginUser.getLoginType());
		values.put("MARK", loginUser.getMark());
		values.put("MOBILEPHONE", loginUser.getMobilephone());
		values.put("NAME", loginUser.getName());
		values.put("ORGID", loginUser.getOrgid());
		values.put("ORGNAME", loginUser.getOrgname());
		values.put("PAIDMARK", loginUser.getPaidmark());
		values.put("PTITLE", loginUser.getPtitle());
		values.put("PASSWORD", loginUser.getPassword());
		values.put("PROVINCE", loginUser.getProvince());
		values.put("REALNAME", loginUser.getRealname());
		values.put("REMARK", loginUser.getRemark());
		values.put("RESUME", loginUser.getResume());
		values.put("REWARDMARK", loginUser.getRewardmark());
		values.put("SEX", loginUser.getSex());
		values.put("SIM", loginUser.getSim());
		values.put("SPECIALTY", loginUser.getSpecialty());
		values.put("STATUS", loginUser.getStatus());
		values.put("STOCK_AGE", loginUser.getStock_age());
		values.put("STOCK_STYLE", loginUser.getStock_style());
		values.put("TYPE", loginUser.getType());
		values.put("USER_ID", loginUser.getUserid());
		db.insert("T_SU_LUI", "", values);
		close();
	}

	public void cheanLoginUserData() {
		System.out.println("#SU DB# cheanLoginUserData");
		SQLiteDatabase db = getWritableDatabase();
		db.delete("T_SU_LUI", null, null);
	}

	public LoginUser queryLoginUserInfo() {
		System.out.println("#SU DB# queryLoginUserInfo");
		LoginUser loginUser = new LoginUser();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT MAX(_ID) , L.* FROM T_SU_LUI L", null);
		while (c.moveToNext()) {
			loginUser = new LoginUser();
			loginUser.setUserid(c.getString(c.getColumnIndex("USER_ID")));
			loginUser.setAutologin(c.getString(c.getColumnIndex("AUTOLOGIN")));
			loginUser.setCity(c.getString(c.getColumnIndex("CITY")));
			loginUser.setCrtime(c.getString(c.getColumnIndex("CRTIME")));
			loginUser.setEmail(c.getString(c.getColumnIndex("EMAIL")));
			loginUser.setGroupid(c.getString(c.getColumnIndex("GROUP_ID")));
			loginUser.setHeadpic(c.getString(c.getColumnIndex("HEADPIC")));
			loginUser.setImei(c.getString(c.getColumnIndex("IMEI")));
			loginUser.setIslock(c.getString(c.getColumnIndex("ISLOCK")));
			loginUser.setLevel(c.getString(c.getColumnIndex("LEVEL")));
			loginUser.setLoginType(c.getInt(c.getColumnIndex("LOGIN_TYPE")));
			loginUser.setMark(c.getString(c.getColumnIndex("MARK")));
			loginUser.setMobilephone(c.getString(c.getColumnIndex("MOBILEPHONE")));
			loginUser.setName(c.getString(c.getColumnIndex("NAME")));
			loginUser.setOrgid(c.getString(c.getColumnIndex("ORGID")));
			loginUser.setOrgname(c.getString(c.getColumnIndex("ORGNAME")));
			loginUser.setPaidmark(c.getString(c.getColumnIndex("PAIDMARK")));
			loginUser.setPassword(c.getString(c.getColumnIndex("PASSWORD")));
			loginUser.setProvince(c.getString(c.getColumnIndex("PROVINCE")));
			loginUser.setPtitle(c.getString(c.getColumnIndex("PTITLE")));
			loginUser.setRealname(c.getString(c.getColumnIndex("REALNAME")));
			loginUser.setRemark(c.getString(c.getColumnIndex("REMARK")));
			loginUser.setResume(c.getString(c.getColumnIndex("RESUME")));
			loginUser.setRewardmark(c.getString(c.getColumnIndex("REWARDMARK")));
			loginUser.setSex(c.getString(c.getColumnIndex("SEX")));
			loginUser.setSim(c.getString(c.getColumnIndex("SIM")));
			loginUser.setSpecialty(c.getString(c.getColumnIndex("SPECIALTY")));
			loginUser.setStatus(c.getString(c.getColumnIndex("STATUS")));
			loginUser.setStock_age(c.getString(c.getColumnIndex("STOCK_AGE")));
			loginUser.setStock_style(c.getString(c.getColumnIndex("STOCK_STYLE")));
			loginUser.setType(c.getString(c.getColumnIndex("TYPE")));
		}
		System.out.println("###   ###  " + loginUser.getUserid() + ":" + loginUser.getName() + ":"
				+ loginUser.getPassword() + ":" + loginUser.getRealname() + ":" + loginUser.getLoginType());
		close();
		return loginUser;
	}

	public void insAdviserGz(Adviser data) {

		if (queryAdviserGz() == null) {
			System.out.println("#SU DB# insAdviserGz");
			SQLiteDatabase db = getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("ADV_USER_ID", data.getAdv_user_id());
			values.put("NAME", data.getName());
			values.put("PASSWORD", data.getPassword());
			values.put("REALNAME", data.getRealname());
			values.put("ISLOCK", data.isIslock() == true ? 0 : 1);
			values.put("SEX", data.getSex());
			values.put("MARK", data.getMark());
			values.put("REWARDMARK", data.getRewardmark());
			values.put("PAIDMARK", data.getPaidmark());
			values.put("HEADPIC", "guwentuan.png");
			// values.put("HEADPIC", data.getHeadpic());
			values.put("GROUP_ID", data.getGroupid());
			values.put("MOBILEPHONE", data.getMobilephone());
			values.put("EMAIL", data.getEmail());
			values.put("CRTIME", data.getCrtime());
			values.put("ORGID", data.getOrgid());
			values.put("SPECIALTY", data.getSpecialty());
			values.put("RESUME", data.getResume());
			values.put("LEVEL", data.getLevel());
			values.put("STATUS", data.getStatus());
			values.put("PTITLE", data.getPtitle());
			values.put("ORGNAME", data.getOrgname());
			values.put("HEARTCOUNT", data.getHeartcount());
			System.out.println("@@@rqrqrq !!! " + data.getHeartcount());
			db.insert("T_SU_ARI_GZ", "", values);
		}
		close();
	}

	public void insAdviserNs() {

		if (queryAdviserNs() == null) {
			System.out.println("#SU DB# insAdviserGz");
			SQLiteDatabase db = getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("ADV_USER_ID", -1);
			values.put("NAME", "");
			values.put("PASSWORD", "");
			values.put("REALNAME", "财经要闻");
			values.put("ISLOCK", 1);
			values.put("SEX", "");
			values.put("MARK", "");
			values.put("REWARDMARK", "");
			values.put("PAIDMARK", "");
			values.put("HEADPIC", "caijing.png");
			values.put("GROUP_ID", "");
			values.put("MOBILEPHONE", "");
			values.put("EMAIL", "");
			values.put("CRTIME", "");
			values.put("ORGID", "");
			values.put("SPECIALTY", "");
			values.put("RESUME", "");
			values.put("LEVEL", "");
			values.put("STATUS", "");
			values.put("PTITLE", "");
			values.put("ORGNAME", "");
			db.insert("T_SU_ARI_GZ", "", values);
		}
		close();
	}

	public Adviser queryAdviserGz() {
		System.out.println("#SU DB# queryAdviserGz");
		Adviser adviser = null;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM T_SU_ARI_GZ WHERE ADV_USER_ID = 1", null);
		System.out.println(">>>??" + c.getCount());
		while (c.moveToNext()) {
			adviser = new Adviser();
			adviser.setAdv_user_id(c.getString(c.getColumnIndex("ADV_USER_ID")));
			adviser.setCrtime(c.getString(c.getColumnIndex("CRTIME")));
			adviser.setEmail(c.getString(c.getColumnIndex("EMAIL")));
			adviser.setGroupid(c.getString(c.getColumnIndex("GROUP_ID")));
			adviser.setHeadpic(c.getString(c.getColumnIndex("HEADPIC")));
			adviser.setSex(c.getString(c.getColumnIndex("SEX")));
			adviser.setIslock(c.getInt(c.getColumnIndex("ISLOCK")) == 0 ? true : false);
			adviser.setLevel(c.getString(c.getColumnIndex("LEVEL")));
			adviser.setMark(c.getString(c.getColumnIndex("MARK")));
			adviser.setMobilephone(c.getString(c.getColumnIndex("MOBILEPHONE")));
			adviser.setName(c.getString(c.getColumnIndex("NAME")));
			adviser.setOrgid(c.getString(c.getColumnIndex("ORGID")));
			adviser.setOrgname(c.getString(c.getColumnIndex("ORGNAME")));
			adviser.setPaidmark(c.getString(c.getColumnIndex("PAIDMARK")));
			adviser.setPassword(c.getString(c.getColumnIndex("PASSWORD")));
			adviser.setPtitle(c.getString(c.getColumnIndex("PTITLE")));
			adviser.setRealname(c.getString(c.getColumnIndex("REALNAME")));
			adviser.setRewardmark(c.getString(c.getColumnIndex("REWARDMARK")));
			adviser.setResume(c.getString(c.getColumnIndex("RESUME")));
			adviser.setSpecialty(c.getString(c.getColumnIndex("SPECIALTY")));
			adviser.setStatus(c.getString(c.getColumnIndex("STATUS")));
			adviser.setHeartcount(c.getString(c.getColumnIndex("HEARTCOUNT")));
			System.out.println(">>>?@@@?" + adviser.getName());
			System.out.println(">>>?@@@?" + adviser.getHeartcount());
		}
		close();
		return adviser;
	}

	public Adviser queryAdviserNs() {
		System.out.println("#SU DB# queryAdviserNs");
		Adviser adviser = null;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM T_SU_ARI_GZ WHERE ADV_USER_ID = -1", null);
		System.out.println(">>>??" + c.getCount());
		while (c.moveToNext()) {
			adviser = new Adviser();
			adviser.setAdv_user_id(c.getString(c.getColumnIndex("ADV_USER_ID")));
			adviser.setCrtime(c.getString(c.getColumnIndex("CRTIME")));
			adviser.setEmail(c.getString(c.getColumnIndex("EMAIL")));
			adviser.setGroupid(c.getString(c.getColumnIndex("GROUP_ID")));
			adviser.setHeadpic(c.getString(c.getColumnIndex("HEADPIC")));
			adviser.setSex(c.getString(c.getColumnIndex("SEX")));
			adviser.setIslock(c.getInt(c.getColumnIndex("ISLOCK")) == 0 ? true : false);
			adviser.setLevel(c.getString(c.getColumnIndex("LEVEL")));
			adviser.setMark(c.getString(c.getColumnIndex("MARK")));
			adviser.setMobilephone(c.getString(c.getColumnIndex("MOBILEPHONE")));
			adviser.setName(c.getString(c.getColumnIndex("NAME")));
			adviser.setOrgid(c.getString(c.getColumnIndex("ORGID")));
			adviser.setOrgname(c.getString(c.getColumnIndex("ORGNAME")));
			adviser.setPaidmark(c.getString(c.getColumnIndex("PAIDMARK")));
			adviser.setPassword(c.getString(c.getColumnIndex("PASSWORD")));
			adviser.setPtitle(c.getString(c.getColumnIndex("PTITLE")));
			adviser.setRealname(c.getString(c.getColumnIndex("REALNAME")));
			adviser.setRewardmark(c.getString(c.getColumnIndex("REWARDMARK")));
			adviser.setResume(c.getString(c.getColumnIndex("RESUME")));
			adviser.setSpecialty(c.getString(c.getColumnIndex("SPECIALTY")));
			adviser.setStatus(c.getString(c.getColumnIndex("STATUS")));
			System.out.println(">>>??" + adviser.getName());
		}
		close();
		return adviser;
	}

	public int queryGzAdminMsg() {

		return 0;

	}

	public int queryGzAdminMsg(String loginid) {
		System.out.println("#SU DB# queryMsgData");
		List<ChatMessage> list = new ArrayList<ChatMessage>();
		String[] param = { loginid };
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM T_SU_MSG WHERE LOGIN_ID  = ? AND SENDER_ID = 1", param);
		System.out.println(">>>>>" + c.getCount());
		return c.getCount();
	}

	public void initSysMsgData(String loginid, String[] wStr) {
		System.out.println(">>>　初始化消息数据...");
		System.out.println("#SU DB# initAMsgData" + "id : " + loginid);
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values1 = new ContentValues();
		values1.put("STATUS", 0);
		values1.put("CONTENT1", wStr[0]);
		values1.put("CONTENT2", "");
		values1.put("CONTENT3_TITLE", "");
		values1.put("CONTENT3_CONTENT", "");
		values1.put("CONTENT3_URL", "");
		values1.put("CONTENT3_IMG", "");
		values1.put("MSG_DIRECTION", 0);
		values1.put("MSG_TYPE", 0);
		values1.put("MSG_DATE", Utils.getSystemDate());
		values1.put("LOGIN_ID", loginid);
		values1.put("SENDER_ID", 1);
		db.insert("T_SU_MSG", "", values1);
		ContentValues values2 = new ContentValues();
		values2.put("STATUS", 0);
		values2.put("CONTENT1", wStr[1]);
		values2.put("CONTENT2", "");
		values2.put("CONTENT3_TITLE", "");
		values2.put("CONTENT3_CONTENT", "");
		values2.put("CONTENT3_URL", "");
		values2.put("CONTENT3_IMG", "");
		values2.put("MSG_DIRECTION", 0);
		values2.put("MSG_TYPE", 0);
		values2.put("MSG_DATE", Utils.getSystemDate());
		values2.put("LOGIN_ID", loginid);
		values2.put("SENDER_ID", -1);
		db.insert("T_SU_MSG", "", values2);
		close();
	}

}
