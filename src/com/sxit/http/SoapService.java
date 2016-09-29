package com.sxit.http;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sxit.entity.DuelPoint;
import com.sxit.entity.HomePageNews;
import com.sxit.entity.LiveEntity;
import com.sxit.entity.UserInfo;
import com.sxit.entity.WapCommentItem;
import com.sxit.entity.anwser.Anwser;
import com.sxit.entity.discuss.DiscussItem;
import com.sxit.entity.discuss.DiscussReply;
import com.sxit.entity.discuss.DiscussTag;
import com.sxit.entity.living.AllLiving;
import com.sxit.entity.living.Course;
import com.sxit.entity.living.HotLiving;
import com.sxit.entity.living.NowLiving;
import com.sxit.entity.living.QA;
import com.sxit.entity.qanda.Answer;
import com.sxit.entity.qanda.Question;
import com.sxit.entity.qanda.QuestionDetail;
import com.sxit.entity.smarter.Expert;
import com.sxit.entity.smarter.ExpertDetail;
import com.sxit.entity.smarter.Guest;
import com.sxit.entity.smarter.GuestDetail;
import com.sxit.entity.smarter.UserExpertDetailChanged;
import com.sxit.http.AsyncTaskBase.HttpObjectResult;
import com.sxit.utils.EventCache;
import com.sxit.utils.SOAP_UTILS;

public class SoapService implements ISoapService {
	private AsyncTaskBase asynTaskBase = new AsyncTaskBase();
	private SoapRes soapRes = new SoapRes();

	@Override
	public void getList(Object[] property_va, final boolean isPage) {
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETLIST);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				soapRes.setObj(obj);
				soapRes.setPage(isPage);
				soapRes.setCode(SOAP_UTILS.METHOD.GETLIST);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETLIST);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getListPage(Object[] property_va, final boolean isPage) {
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETLISTPAGE);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONArray news_array = new JSONArray(obj.toString());
					List<HomePageNews> news_list = new ArrayList<HomePageNews>();

					for (int i = 0; i < news_array.length(); i++) {
						JSONObject json_news = (JSONObject) news_array.get(i);
						HomePageNews hpn = new HomePageNews();
						hpn.setAdminid(json_news.get("Adminid").toString());
						hpn.setColtitle(json_news.get("Coltitle").toString());
						hpn.setCrtime(json_news.get("Crtime").toString());
						hpn.setHeadPic(json_news.get("HeadPic").toString());
						hpn.setId(json_news.get("Id").toString());
						hpn.setNewsComCount(json_news.get("NewsComCount").toString());
						hpn.setOrders(json_news.get("Orders").toString());
						hpn.setOrgName(json_news.get("OrgName").toString());
						hpn.setPicture(json_news.get("Picture").toString());
						hpn.setRealName(json_news.get("RealName").toString());
						hpn.setSource(json_news.get("Source").toString());
						hpn.setThumbnail(json_news.get("Thumbnail").toString());
						hpn.setTitle(json_news.get("Title").toString());

						news_list.add(hpn);
					}

					soapRes.setObj(news_list);
					soapRes.setPage(isPage);
					soapRes.setCode(SOAP_UTILS.METHOD.GETLISTPAGE);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.GETLISTPAGE);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETLISTPAGE);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getListByStock(Object[] property_va, final boolean isPage) {
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETLISTBYSTOCK);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONArray news_array = new JSONArray(obj.toString());
					List<HomePageNews> news_list = new ArrayList<HomePageNews>();

					for (int i = 0; i < news_array.length(); i++) {
						JSONObject json_news = (JSONObject) news_array.get(i);
						HomePageNews hpn = new HomePageNews();
						hpn.setAdminid(json_news.get("Adminid").toString());
						hpn.setColtitle("");
						// hpn.setColtitle(json_news.get("Coltitle").toString());
						hpn.setCrtime(json_news.get("Crtime").toString());
						hpn.setHeadPic(json_news.get("HeadPic").toString());
						hpn.setId(json_news.get("Id").toString());
						hpn.setNewsComCount(json_news.get("NewsComCount").toString());
						hpn.setOrders(json_news.get("Orders").toString());
						hpn.setOrgName(json_news.get("OrgName").toString());
						hpn.setPicture(json_news.get("Picture").toString());
						hpn.setRealName(json_news.get("RealName").toString());
						hpn.setSource(json_news.get("Source").toString());
						hpn.setThumbnail(json_news.get("Thumbnail").toString());
						hpn.setTitle(json_news.get("Title").toString());

						news_list.add(hpn);
					}

					soapRes.setObj(news_list);
					soapRes.setPage(isPage);
					soapRes.setCode(SOAP_UTILS.METHOD.GETLISTBYSTOCK);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.GETLISTBYSTOCK);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETLISTBYSTOCK);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getListByGuest(Object[] property_va, final boolean isPage) {
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETLISTBYGUEST);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONArray news_array = new JSONArray(obj.toString());
					List<HomePageNews> news_list = new ArrayList<HomePageNews>();

					for (int i = 0; i < news_array.length(); i++) {
						JSONObject json_news = (JSONObject) news_array.get(i);
						HomePageNews hpn = new HomePageNews();
						hpn.setAdminid(json_news.get("Adminid").toString());
						hpn.setColtitle("");
						// hpn.setColtitle(json_news.get("Coltitle").toString());
						hpn.setCrtime(json_news.get("Crtime").toString());
						hpn.setHeadPic(json_news.get("HeadPic").toString());
						hpn.setId(json_news.get("Id").toString());
						hpn.setNewsComCount(json_news.get("NewsComCount").toString());
						hpn.setOrders(json_news.get("Orders").toString());
						hpn.setOrgName(json_news.get("OrgName").toString());
						hpn.setPicture(json_news.get("Picture").toString());
						hpn.setRealName(json_news.get("RealName").toString());
						hpn.setSource(json_news.get("Source").toString());
						hpn.setThumbnail(json_news.get("Thumbnail").toString());
						hpn.setTitle(json_news.get("Title").toString());

						news_list.add(hpn);
					}

					soapRes.setObj(news_list);
					soapRes.setPage(isPage);
					soapRes.setCode(SOAP_UTILS.METHOD.GETLISTBYGUEST);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.GETLISTBYGUEST);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETLISTBYGUEST);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getListLive(Object[] property_va, final boolean isPage) {
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETLISTLIVE);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONArray news_array = new JSONArray(obj.toString());
					List<LiveEntity> list = new ArrayList<LiveEntity>();

					for (int i = 0; i < news_array.length(); i++) {
						JSONObject jsonobject = (JSONObject) news_array.get(i);
						LiveEntity le = new LiveEntity();
						le.setContent(jsonobject.get("Content").toString());
						le.setCrtime(jsonobject.get("Crtime").toString());
						le.setHeadpic(SOAP_UTILS.HTTP_HEAD_PATH + jsonobject.get("HeadPic").toString());
						le.setId(jsonobject.get("Id").toString());
						JSONArray magpic_array = (JSONArray) jsonobject.get("Magpic");
						ArrayList<String> mag_urls = new ArrayList<String>();
						for (int j = 0; j < magpic_array.length(); j++) {
							JSONObject jsonmag = (JSONObject) magpic_array.get(j);
							mag_urls.add(SOAP_UTILS.HTTP_MAG_PATH + jsonmag.get("Pic").toString());
						}
						le.setMagPics(mag_urls);
						le.setOrgname(jsonobject.get("OrgName").toString());
						le.setRealName(jsonobject.get("RealName").toString());

						list.add(le);
					}

					soapRes.setObj(list);
					soapRes.setPage(isPage);
					soapRes.setCode(SOAP_UTILS.METHOD.GETLISTLIVE);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.GETLISTLIVE);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETLISTLIVE);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void userInfoLogin(Object[] property_va) {
		// TODO Auto-generated method stub
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.USERINFOLOGIN);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {

					JSONObject user_obj = new JSONObject(obj.toString());
					UserInfo user = new UserInfo();
					user.setchecknum(user_obj.get("CheckNum").toString());
					user.setchecksta(user_obj.get("CheckSta").toString());
					user.setheadpic(SOAP_UTILS.HTTP_HEAD_PATH + user_obj.get("HeadPic").toString());
					user.setid(user_obj.get("Id").toString());
					user.setlevel(user_obj.get("Level").toString());
					user.setmark(user_obj.get("Redheart").toString());
					user.setname(user_obj.get("Name").toString());
					user.setprestige(user_obj.get("Mark").toString());
					user.setrank(user_obj.get("Rank").toString());
					user.setrealname(user_obj.get("RealName").toString());
					user.setbirth(user_obj.get("StockAge").toString());
					user.setsex(user_obj.get("Sex").toString());
					user.setNewprestige(user_obj.get("Prestige").toString());
					user.setRewardmark(user_obj.get("Rewardmark").toString());
					user.setattuser(user_obj.get("AttUser").toString());
					// String result = user_obj.get("AttUser").toString();
					//
					// JSONArray attuser_array = new JSONArray(result);
					// for(int i = 0; attuser_array.length()>0;i++){
					//
					// JSONObject json_attuser = (JSONObject)
					// attuser_array.get(i);
					//
					// }
					//
					soapRes.setObj(user);
					soapRes.setCode(SOAP_UTILS.METHOD.USERINFOLOGIN);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.USERINFOLOGIN);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.USERINFOLOGIN);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void userInfoById(Object[] property_va, final String headpic) {
		// TODO Auto-generated method stub
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.USERINFOBYID);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					// {
					// "CheckNum": 0,
					// "CheckSta": 0,
					// "HeadPic": "1.jpg",
					// "Id": 12,
					// "Level": 0,
					// "Mark": 380,
					// "Name": "admin",
					// "Prestige": 0,
					// "Rank": 220,
					// "RealName": "演示"
					// }

					JSONObject user_obj = new JSONObject(obj.toString());
					UserInfo user = new UserInfo();
					user.setchecknum(user_obj.get("CheckNum").toString());
					user.setchecksta(user_obj.get("CheckSta").toString());
					// user.setheadpic(user_obj.get("HeadPic").toString());
					user.setheadpic(headpic);
					user.setid(user_obj.get("Id").toString());
					user.setlevel(user_obj.get("Level").toString());
					user.setmark(user_obj.get("Redheart").toString());
					user.setname(user_obj.get("Name").toString());
					user.setprestige(user_obj.get("Mark").toString());
					user.setrank(user_obj.get("Rank").toString());
					user.setrealname(user_obj.get("RealName").toString());
					user.setbirth(user_obj.get("StockAge").toString());
					user.setsex(user_obj.get("Sex").toString());

					soapRes.setObj(user);
					soapRes.setCode(SOAP_UTILS.METHOD.USERINFOBYID);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.USERINFOBYID);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.USERINFOBYID);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getDuelVote(Object[] property_va) {
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETDUELVOTE);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				soapRes.setObj(obj);
				soapRes.setCode(SOAP_UTILS.METHOD.GETDUELVOTE);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETDUELVOTE);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getDuelVotePage(Object[] property_va) {
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETDUELVOTEPAGE);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONObject json_obj = new JSONObject(obj.toString());
					String result = json_obj.get("Result").toString();
					if (result.equals("success")) {
						JSONObject msg_obj = new JSONObject(json_obj.getString("Message"));
						JSONArray point_array = msg_obj.getJSONArray("point");
						List<DuelPoint> list = new ArrayList<DuelPoint>();
						for (int i = 0; i < point_array.length(); i++) {
							JSONObject json_point = (JSONObject) point_array.get(i);
							DuelPoint dp = new DuelPoint();
							dp.setId(json_point.get("Id").toString());
							dp.setUserid(json_point.get("Userid").toString());
							dp.setContent(json_point.get("Content").toString());
							dp.setViewpoint(json_point.get("Viewpoint").toString());
							dp.setSupportrate(json_point.get("Supportrate").toString());
							dp.setRealName(json_point.get("RealName").toString());
							dp.setHeadPic(json_point.get("HeadPic").toString());

							list.add(dp);
						}
						soapRes.setObj(list);
						soapRes.setCode(SOAP_UTILS.METHOD.GETDUELVOTEPAGE);
						EventCache.commandActivity.post(soapRes);
					} else {
						soapRes.setObj(null);
						soapRes.setCode(SOAP_UTILS.METHOD.GETDUELVOTEPAGE);
						EventCache.commandActivity.post(soapRes);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETDUELVOTEPAGE);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void duelVote(Object[] property_va) {
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.DUELVOTE);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				soapRes.setObj(obj);
				soapRes.setCode(SOAP_UTILS.METHOD.DUELVOTE);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.DUELVOTE);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void viewPointSupport(Object[] property_va, final int position) {
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.VIEWPOINTSUPPORT);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				soapRes.setObj(obj);
				soapRes.setPosition(position);
				soapRes.setCode(SOAP_UTILS.METHOD.VIEWPOINTSUPPORT);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setPosition(position);
				soapRes.setCode(SOAP_UTILS.METHOD.VIEWPOINTSUPPORT);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void oneKeyLogin(Object[] property_va, final String headpic) {
		// TODO Auto-generated method stub
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.ONEKEYLOGIN);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONObject json_obj = new JSONObject(obj.toString());
					String result = json_obj.get("Result").toString();
					if (result.equals("success")) {
						JSONObject user_obj = new JSONObject(json_obj.getString("Message"));
						UserInfo user = new UserInfo();
						user.setchecknum(user_obj.get("CheckNum").toString());
						user.setchecksta(user_obj.get("CheckSta").toString());
						// user.setheadpic(user_obj.get("HeadPic").toString());
						user.setheadpic(headpic);
						user.setid(user_obj.get("Id").toString());
						user.setlevel(user_obj.get("Level").toString());
						user.setmark(user_obj.get("Mark").toString());
						user.setname(user_obj.get("Name").toString());
						user.setprestige(user_obj.get("Prestige").toString());
						user.setrank(user_obj.get("Rank").toString());
						user.setrealname(user_obj.get("RealName").toString());

						soapRes.setPosition(0);
						soapRes.setObj(user);
						soapRes.setCode(SOAP_UTILS.METHOD.ONEKEYLOGIN);
						EventCache.commandActivity.post(soapRes);
					} else if (result.equals("NoExist")) {
						JSONObject user_obj = new JSONObject(json_obj.getString("Message"));
						UserInfo user = new UserInfo();
						user.setchecknum(user_obj.get("CheckNum").toString());
						user.setchecksta(user_obj.get("CheckSta").toString());
						// user.setheadpic(user_obj.get("HeadPic").toString());
						user.setheadpic(headpic);
						user.setid(user_obj.get("Id").toString());
						user.setlevel(user_obj.get("Level").toString());
						user.setmark(user_obj.get("Mark").toString());
						user.setname(user_obj.get("Name").toString());
						user.setprestige(user_obj.get("Prestige").toString());
						user.setrank(user_obj.get("Rank").toString());
						user.setrealname(user_obj.get("RealName").toString());

						soapRes.setPosition(1);
						soapRes.setObj(user);
						soapRes.setCode(SOAP_UTILS.METHOD.ONEKEYLOGIN);
						EventCache.commandActivity.post(soapRes);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.ONEKEYLOGIN);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.ONEKEYLOGIN);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void userRegistered(Object[] property_va) {
		// TODO Auto-generated method stub
		String[] property_nm = { "name", "password", "realName" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.USERREGISTERED);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONObject json_obj = new JSONObject(obj.toString());
					String result = json_obj.get("Result").toString();
					String message = json_obj.get("Message").toString();
					if (result.equals("success")) {
						message = "success";
					}
					soapRes.setObj(message);
					soapRes.setCode(SOAP_UTILS.METHOD.USERREGISTERED);
					EventCache.commandActivity.post(soapRes);
					// }
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.USERREGISTERED);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.USERREGISTERED);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void viewPointAdd(Object[] property_va) {
		// TODO Auto-generated method stub
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.VIEWPOINTADD);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONObject json_obj = new JSONObject(obj.toString());
					String result = json_obj.get("Result").toString();
					String message = json_obj.get("Message").toString();
					if (result.equals("success")) {
						message = "success";
					}
					soapRes.setObj(message);
					soapRes.setCode(SOAP_UTILS.METHOD.VIEWPOINTADD);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.VIEWPOINTADD);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.VIEWPOINTADD);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void userEditor_RealName(Object[] property_va) {
		// TODO Auto-generated method stub
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.USEREDITOR_REALNAME);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONObject json_obj = new JSONObject(obj.toString());
					String result = json_obj.get("Result").toString();
					String message = json_obj.get("Message").toString();
					if (result.equals("success")) {
						message = "success";
					}
					soapRes.setObj(message);
					soapRes.setCode(SOAP_UTILS.METHOD.USEREDITOR_REALNAME);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.USEREDITOR_REALNAME);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.USEREDITOR_REALNAME);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void userEditor_Password(Object[] property_va) {
		// TODO Auto-generated method stub
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.USEREDITOR_PASSWORD);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONObject json_obj = new JSONObject(obj.toString());
					String result = json_obj.get("Result").toString();
					String message = json_obj.get("Message").toString();
					if (result.equals("success")) {
						message = "success";
					}
					soapRes.setObj(message);
					soapRes.setCode(SOAP_UTILS.METHOD.USEREDITOR_PASSWORD);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.USEREDITOR_PASSWORD);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.USEREDITOR_PASSWORD);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void userEditor_StockAge(Object[] property_va) {
		// TODO Auto-generated method stub
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.USEREDITOR_STOCKAGE);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONObject json_obj = new JSONObject(obj.toString());
					String result = json_obj.get("Result").toString();
					String message = json_obj.get("Message").toString();
					if (result.equals("success")) {
						message = "success";
					}
					soapRes.setObj(message);
					soapRes.setCode(SOAP_UTILS.METHOD.USEREDITOR_STOCKAGE);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.USEREDITOR_STOCKAGE);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.USEREDITOR_STOCKAGE);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void userEditor_Sex(Object[] property_va) {
		// TODO Auto-generated method stub
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.USEREDITOR_SEX);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONObject json_obj = new JSONObject(obj.toString());
					String result = json_obj.get("Result").toString();
					String message = json_obj.get("Message").toString();
					if (result.equals("success")) {
						message = "success";
					}
					soapRes.setObj(message);
					soapRes.setCode(SOAP_UTILS.METHOD.USEREDITOR_SEX);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.USEREDITOR_SEX);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.USEREDITOR_SEX);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getGuestTeam(Object[] property_va, final boolean isPage) {
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETGUESTTEAM);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONArray guest_array = new JSONArray(obj.toString());
					List<Guest> guest_list = new ArrayList<Guest>();

					for (int i = 0; i < guest_array.length(); i++) {
						JSONObject json_guest = (JSONObject) guest_array.get(i);
						Guest guest = new Guest();
						guest.setanalysis(json_guest.get("Analysis").toString());
						guest.setheadpic(json_guest.get("HeadPic").toString());
						guest.setwebid(json_guest.get("Userid").toString());
						guest.setlevel(json_guest.get("Level").toString());
						guest.setpaidmark(json_guest.get("Rewardmark").toString());
						guest.setrealname(json_guest.get("RealName").toString());
						guest.setstockstyle(json_guest.get("StockStyle").toString());
						guest.setresume(json_guest.get("Resume").toString());

						guest_list.add(guest);
					}

					soapRes.setObj(guest_list);
					soapRes.setPage(isPage);
					soapRes.setCode(SOAP_UTILS.METHOD.GETGUESTTEAM);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.GETGUESTTEAM);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETGUESTTEAM);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getExpertTeam(Object[] property_va, final boolean isPage) {
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETEXPERTTEAM);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONArray expert_array = new JSONArray(obj.toString());
					List<Expert> expert_list = new ArrayList<Expert>();

					for (int i = 0; i < expert_array.length(); i++) {
						JSONObject json_expert = (JSONObject) expert_array.get(i);
						Expert expert = new Expert();
						expert.setrewardmark(json_expert.get("Rewardmark").toString());
						expert.setheadpic(json_expert.get("HeadPic").toString());
						expert.setwebid(json_expert.get("Userid").toString());
						expert.setlevel(json_expert.get("Level").toString());
						expert.setmark(json_expert.get("Mark").toString());
						expert.setrealname(json_expert.get("RealName").toString());
						expert.setstockstyle(json_expert.get("StockStyle").toString());
						expert.setresume(json_expert.get("Resume").toString());

						expert_list.add(expert);
					}

					soapRes.setObj(expert_list);
					soapRes.setPage(isPage);
					soapRes.setCode(SOAP_UTILS.METHOD.GETEXPERTTEAM);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.GETEXPERTTEAM);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETEXPERTTEAM);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getGuestDetail(Object[] property_va, final boolean isPage) {
		String[] property_nm = { "userid", "pagesize", "pageindex" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETGUESTDETAIL);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONObject json_obj = new JSONObject(obj.toString());
					String result = json_obj.get("GuestViewlist").toString();
					JSONArray guestDetail_array = new JSONArray(result);
					List<GuestDetail> guestDetail_list = new ArrayList<GuestDetail>();

					for (int i = 0; i < guestDetail_array.length(); i++) {
						JSONObject json_guestDetail = (JSONObject) guestDetail_array.get(i);
						GuestDetail guestDetail = new GuestDetail();
						guestDetail.setadminid(json_guestDetail.get("Adminid").toString());
						guestDetail.setcoltitle(json_guestDetail.get("Coltitle").toString());
						guestDetail.setcrtime(json_guestDetail.get("Crtime").toString());
						guestDetail.setheadpic(json_guestDetail.get("HeadPic").toString());
						guestDetail.setwebid(json_guestDetail.get("Id").toString());
						guestDetail.setnewscomcount(json_guestDetail.get("NewsComCount").toString());
						guestDetail.setorders(json_guestDetail.get("Orders").toString());
						guestDetail.setorgname(json_guestDetail.get("OrgName").toString());
						guestDetail.setpicture(json_guestDetail.get("Picture").toString());
						guestDetail.setrealname(json_guestDetail.get("RealName").toString());
						guestDetail.setsource(json_guestDetail.get("Source").toString());
						guestDetail.setthumbnail(json_guestDetail.get("Thumbnail").toString());
						guestDetail.settitle(json_guestDetail.get("Title").toString());

						guestDetail_list.add(guestDetail);
					}

					soapRes.setObj(guestDetail_list);
					soapRes.setPage(isPage);
					soapRes.setCode(SOAP_UTILS.METHOD.GETGUESTDETAIL);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.GETGUESTDETAIL);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETGUESTDETAIL);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getExpertDetail(Object[] property_va, final boolean isPage) {
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETEXPERTDETAIL);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONObject json_obj = new JSONObject(obj.toString());
					String result = json_obj.get("Discussionlist").toString();
					JSONArray expertDetail_array = new JSONArray(result);
					List<ExpertDetail> expertDetail_list = new ArrayList<ExpertDetail>();

					for (int i = 0; i < expertDetail_array.length(); i++) {
						JSONObject json_expertDetail = (JSONObject) expertDetail_array.get(i);
						ExpertDetail expertDetail = new ExpertDetail();
						expertDetail.setcolumn(json_expertDetail.get("Column").toString());
						expertDetail.setcontent(json_expertDetail.get("Content").toString());
						expertDetail.setcrtime(json_expertDetail.get("Crtime").toString());
						expertDetail.setdownmark(json_expertDetail.get("Downmark").toString());
						expertDetail.setheadpic(json_expertDetail.get("HeadPic").toString());
						expertDetail.sethot(json_expertDetail.get("Hot").toString());
						expertDetail.setwebid(json_expertDetail.get("Id").toString());
						expertDetail.setimg1(json_expertDetail.get("Img1").toString());
						expertDetail.setimg2(json_expertDetail.get("Img2").toString());
						expertDetail.setimgthumbnail1(json_expertDetail.get("Imgthumbnail1").toString());
						expertDetail.setimgthumbnail2(json_expertDetail.get("Imgthumbnail2").toString());
						expertDetail.setmark(json_expertDetail.get("Mark").toString());
						expertDetail.setrealname(json_expertDetail.get("RealName").toString());
						expertDetail.setreplynum(json_expertDetail.get("Replynum").toString());
						expertDetail.setrewardmark(json_expertDetail.get("Rewardmark").toString());
						expertDetail.setsign(json_expertDetail.get("Sign").toString());
						expertDetail.setuserid(json_expertDetail.get("Userid").toString());

						expertDetail_list.add(expertDetail);
					}

					soapRes.setObj(expertDetail_list);
					soapRes.setPage(isPage);
					soapRes.setCode(SOAP_UTILS.METHOD.GETEXPERTDETAIL);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.GETEXPERTDETAIL);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETEXPERTDETAIL);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getDiscussionList(Object[] property_va, final boolean isPage) {
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETDISCUSSIONLIST);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONArray json_array = new JSONArray(obj.toString());
					List<DiscussItem> dis_list = new ArrayList<DiscussItem>();
					for (int i = 0; i < json_array.length(); i++) {
						JSONObject json_obj = (JSONObject) json_array.get(i);
						DiscussItem disitem = new DiscussItem();
						disitem.setColumn(json_obj.get("Column").toString());
						disitem.setContent(json_obj.get("Content").toString());
						disitem.setCrtime(json_obj.get("Crtime").toString());
						disitem.setDownmark(json_obj.get("Downmark").toString());
						disitem.setHeadPic(SOAP_UTILS.HTTP_HEAD_PATH + json_obj.get("HeadPic").toString());
						disitem.setHot(json_obj.get("Hot").toString());
						disitem.setId(json_obj.get("Id").toString());
						disitem.setImg1(json_obj.get("Img1").toString());
						disitem.setImg2(json_obj.get("Img2").toString());
						disitem.setImgthumbnail1(json_obj.get("Imgthumbnail1").toString());
						disitem.setImgthumbnail2(json_obj.get("Imgthumbnail2").toString());
						disitem.setMark(json_obj.get("Mark").toString());
						disitem.setRealName(json_obj.get("RealName").toString());
						disitem.setReplynum(json_obj.get("Replynum").toString());
						disitem.setRewardmark(json_obj.get("Rewardmark").toString());
						disitem.setSign(json_obj.get("Sign").toString());
						disitem.setUserid(json_obj.get("Userid").toString());

						dis_list.add(disitem);
					}

					soapRes.setObj(dis_list);
					soapRes.setPage(isPage);
					soapRes.setCode(SOAP_UTILS.METHOD.GETDISCUSSIONLIST);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.GETDISCUSSIONLIST);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETDISCUSSIONLIST);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getMyDiscussionList(Object[] property_va, final boolean isPage) {
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETMYDISCUSSIONLIST);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONArray json_array = new JSONArray(obj.toString());
					List<DiscussItem> dis_list = new ArrayList<DiscussItem>();
					for (int i = 0; i < json_array.length(); i++) {
						JSONObject json_obj = (JSONObject) json_array.get(i);
						DiscussItem disitem = new DiscussItem();
						disitem.setColumn(json_obj.get("Column").toString());
						disitem.setContent(json_obj.get("Content").toString());
						disitem.setCrtime(json_obj.get("Crtime").toString());
						disitem.setDownmark(json_obj.get("Downmark").toString());
						disitem.setHeadPic(SOAP_UTILS.HTTP_HEAD_PATH + json_obj.get("HeadPic").toString());
						disitem.setHot(json_obj.get("Hot").toString());
						disitem.setId(json_obj.get("Id").toString());
						disitem.setImg1(json_obj.get("Img1").toString());
						disitem.setImg2(json_obj.get("Img2").toString());
						disitem.setImgthumbnail1(json_obj.get("Imgthumbnail1").toString());
						disitem.setImgthumbnail2(json_obj.get("Imgthumbnail2").toString());
						disitem.setMark(json_obj.get("Mark").toString());
						disitem.setRealName(json_obj.get("RealName").toString());
						disitem.setReplynum(json_obj.get("Replynum").toString());
						disitem.setRewardmark(json_obj.get("Rewardmark").toString());
						disitem.setSign(json_obj.get("Sign").toString());
						disitem.setUserid(json_obj.get("Userid").toString());

						dis_list.add(disitem);
					}

					soapRes.setObj(dis_list);
					soapRes.setPage(isPage);
					soapRes.setCode(SOAP_UTILS.METHOD.GETMYDISCUSSIONLIST);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.GETMYDISCUSSIONLIST);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETMYDISCUSSIONLIST);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getDiscussionColumn(Object[] property_va) {
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETDISCUSSIONCOLUMN);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONArray json_array = new JSONArray(obj.toString());
					List<DiscussTag> dis_list = new ArrayList<DiscussTag>();
					for (int i = 0; i < json_array.length(); i++) {
						JSONObject json_obj = (JSONObject) json_array.get(i);
						DiscussTag disitem = new DiscussTag();
						disitem.setId(json_obj.get("Id").toString());
						disitem.setOrders(json_obj.get("Orders").toString());
						disitem.setTitle(json_obj.get("Title").toString());
						disitem.setValue(json_obj.get("Value").toString());

						dis_list.add(disitem);
					}

					soapRes.setObj(dis_list);
					soapRes.setCode(SOAP_UTILS.METHOD.GETDISCUSSIONCOLUMN);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.GETDISCUSSIONCOLUMN);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETDISCUSSIONCOLUMN);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getQuestionColumn(Object[] property_va) {
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETQUESTIONCOLUMN);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONArray json_array = new JSONArray(obj.toString());
					List<DiscussTag> dis_list = new ArrayList<DiscussTag>();
					for (int i = 0; i < json_array.length(); i++) {
						JSONObject json_obj = (JSONObject) json_array.get(i);
						DiscussTag disitem = new DiscussTag();
						disitem.setId(json_obj.get("Id").toString());
						disitem.setOrders(json_obj.get("Orders").toString());
						disitem.setTitle(json_obj.get("Title").toString());
						disitem.setValue(json_obj.get("Value").toString());

						dis_list.add(disitem);
					}

					soapRes.setObj(dis_list);
					soapRes.setCode(SOAP_UTILS.METHOD.GETQUESTIONCOLUMN);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.GETQUESTIONCOLUMN);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETQUESTIONCOLUMN);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void discussionSubmit(Object[] property_va) {
		// TODO Auto-generated method stub
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.DISCUSSIONSUBMIT);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONObject json_obj = new JSONObject(obj.toString());
					String result = json_obj.get("Result").toString();
					String message = json_obj.get("Message").toString();
					if (result.equals("success")) {
						message = "success";
					}
					soapRes.setObj(message);
					soapRes.setCode(SOAP_UTILS.METHOD.DISCUSSIONSUBMIT);
					EventCache.commandActivity.post(soapRes);
					// }
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.DISCUSSIONSUBMIT);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.DISCUSSIONSUBMIT);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void questionSubmit(Object[] property_va) {
		// TODO Auto-generated method stub
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.QUESTIONSUBMIT);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONObject json_obj = new JSONObject(obj.toString());
					String result = json_obj.get("Result").toString();
					String message = json_obj.get("Message").toString();
					if (result.equals("success")) {
						message = "success";
					}
					soapRes.setObj(message);
					soapRes.setCode(SOAP_UTILS.METHOD.QUESTIONSUBMIT);
					EventCache.commandActivity.post(soapRes);
					// }
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.QUESTIONSUBMIT);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.QUESTIONSUBMIT);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void answerSubmit(Object[] property_va) {
		// TODO Auto-generated method stub
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.ANSWERSUBMIT);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONObject json_obj = new JSONObject(obj.toString());
					String result = json_obj.get("Result").toString();
					String message = json_obj.get("Message").toString();
					if (result.equals("success")) {
						message = "success";
					}
					soapRes.setObj(message);
					soapRes.setCode(SOAP_UTILS.METHOD.ANSWERSUBMIT);
					EventCache.commandActivity.post(soapRes);
					// }
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.ANSWERSUBMIT);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.ANSWERSUBMIT);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getQuestionList(Object[] property_va, final boolean isPage) {
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETQUESTIONLIST);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONArray question_array = new JSONArray(obj.toString());
					List<Question> question_list = new ArrayList<Question>();

					for (int i = 0; i < question_array.length(); i++) {
						JSONObject json_question = (JSONObject) question_array.get(i);
						Question question = new Question();
						question.setanswernum(json_question.get("Answernum").toString());
						question.setcolumn(json_question.get("Column").toString());
						question.setcontent(json_question.get("Content").toString());
						question.setcrtime(json_question.get("Crtime").toString());
						question.setwebid(json_question.get("Id").toString());
						question.setimg1(json_question.get("Img1").toString());
						question.setimg2(json_question.get("Img2").toString());
						question.setimgthumbnail1(json_question.get("Imgthumbnail1").toString());
						question.setimgthumbnail2(json_question.get("Imgthumbnail2").toString());
						question.settitle(json_question.get("Title").toString());
						question.setuserid(json_question.get("Userid").toString());

						question_list.add(question);
					}

					soapRes.setObj(question_list);
					soapRes.setPage(isPage);
					soapRes.setCode(SOAP_UTILS.METHOD.GETQUESTIONLIST);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.GETQUESTIONLIST);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETQUESTIONLIST);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getAnswerList(Object[] property_va, final boolean isPage) {
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETANSWERLIST);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONArray anwser_array = new JSONArray(obj.toString());
					List<Answer> anwser_list = new ArrayList<Answer>();

					for (int i = 0; i < anwser_array.length(); i++) {
						JSONObject json_anwser = (JSONObject) anwser_array.get(i);
						Answer anwser = new Answer();
						anwser.setcolumn(json_anwser.get("Column").toString());
						anwser.setcontent(json_anwser.get("Content").toString());
						anwser.setcrtime(json_anwser.get("Crtime").toString());
						anwser.setheadpic(json_anwser.get("HeadPic").toString());
						anwser.setwebid(json_anwser.get("Id").toString());
						anwser.setimg1(json_anwser.get("Img1").toString());
						anwser.setimg2(json_anwser.get("Img2").toString());
						anwser.setimgthumbnail1(json_anwser.get("Imgthumbnail1").toString());
						anwser.setimgthumbnail2(json_anwser.get("Imgthumbnail2").toString());
						anwser.setrealname(json_anwser.get("RealName").toString());
						anwser.settitle(json_anwser.get("Title").toString());
						anwser.setuserid(json_anwser.get("Userid").toString());

						anwser_list.add(anwser);
					}

					soapRes.setObj(anwser_list);
					soapRes.setPage(isPage);
					soapRes.setCode(SOAP_UTILS.METHOD.GETANSWERLIST);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.GETANSWERLIST);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETANSWERLIST);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getQuestionDetail(Object[] property_va, final boolean isPage) {
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETQUESTIONJSON);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONObject json_obj = new JSONObject(obj.toString());
					String result = json_obj.get("Answerlist").toString();
					JSONArray questiondetail_array = new JSONArray(result);
					List<QuestionDetail> questiondetail_list = new ArrayList<QuestionDetail>();

					for (int i = 0; i < questiondetail_array.length(); i++) {
						JSONObject json_questiondetail = (JSONObject) questiondetail_array.get(i);
						QuestionDetail questiondetail = new QuestionDetail();
						questiondetail.setquesid(json_questiondetail.get("Quesid").toString());
						questiondetail.setcontent(json_questiondetail.get("Content").toString());
						questiondetail.setcrtime(json_questiondetail.get("Crtime").toString());
						questiondetail.setheadpic(json_questiondetail.get("HeadPic").toString());
						questiondetail.setwebid(json_questiondetail.get("Id").toString());
						questiondetail.setimg1(json_questiondetail.get("Img1").toString());
						questiondetail.setimg2(json_questiondetail.get("Img2").toString());
						questiondetail.setimgthumbnail1(json_questiondetail.get("Imgthumbnail1").toString());
						questiondetail.setimgthumbnail2(json_questiondetail.get("Imgthumbnail2").toString());
						questiondetail.setrealname(json_questiondetail.get("RealName").toString());
						questiondetail.setuseful(json_questiondetail.get("Useful").toString());
						questiondetail.setuserid(json_questiondetail.get("Userid").toString());

						questiondetail_list.add(questiondetail);
					}

					soapRes.setObj(questiondetail_list);
					soapRes.setPage(isPage);
					soapRes.setCode(SOAP_UTILS.METHOD.GETQUESTIONJSON);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.GETQUESTIONJSON);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETQUESTIONJSON);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void setAnswerUseful(Object[] property_va) {
		// TODO Auto-generated method stub
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.SETANSWERUSEFUL);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONObject json_obj = new JSONObject(obj.toString());
					String result = json_obj.get("Result").toString();
					String message = json_obj.get("Message").toString();
					if (result.equals("success")) {
						message = "success";
					}
					soapRes.setObj(message);
					soapRes.setCode(SOAP_UTILS.METHOD.SETANSWERUSEFUL);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.SETANSWERUSEFUL);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.SETANSWERUSEFUL);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getDiscussionReply(Object[] property_va, final boolean isPage) {
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETDISCUSSIONREPLY);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONObject json = new JSONObject(obj.toString());
					String down = json.getString("Downmark");
					JSONArray json_array = json.getJSONArray("Replylist");
					List<DiscussReply> dis_list = new ArrayList<DiscussReply>();
					for (int i = 0; i < json_array.length(); i++) {
						JSONObject json_obj = (JSONObject) json_array.get(i);
						DiscussReply disitem = new DiscussReply();
						disitem.setContent(json_obj.get("Content").toString());
						disitem.setCrtime(json_obj.get("Crtime").toString());
						disitem.setFloor(json_obj.get("Floor").toString());
						disitem.setHeadPic(SOAP_UTILS.HTTP_HEAD_PATH + json_obj.get("HeadPic").toString());
						// disitem.setHostid(json_obj.get("Hostid").toString());
						disitem.setHostid(down);
						disitem.setId(json_obj.get("Id").toString());
						disitem.setMark(json_obj.get("Mark").toString());
						disitem.setRealName(json_obj.get("RealName").toString());
						disitem.setReplyid(json_obj.get("Replyid").toString());
						disitem.setUserid(json_obj.get("Userid").toString());

						dis_list.add(disitem);
					}

					soapRes.setObj(dis_list);
					soapRes.setPage(isPage);
					soapRes.setCode(SOAP_UTILS.METHOD.GETDISCUSSIONREPLY);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.GETDISCUSSIONREPLY);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETDISCUSSIONREPLY);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void discussionUserSupport(Object[] property_va, final boolean isUp) {
		// TODO Auto-generated method stub
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.DISCUSSIONUSERSUPPORT);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONObject json_obj = new JSONObject(obj.toString());
					String result = json_obj.get("Result").toString();
					String message = json_obj.get("Message").toString();
					if (result.equals("success")) {
						message = "success";
					}
					soapRes.setObj(message);
					soapRes.setPage(isUp);
					soapRes.setCode(SOAP_UTILS.METHOD.DISCUSSIONUSERSUPPORT);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.DISCUSSIONUSERSUPPORT);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.DISCUSSIONUSERSUPPORT);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void discussionReplyHost(Object[] property_va) {
		// TODO Auto-generated method stub
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.DISCUSSIONREPLYHOST);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONObject json_obj = new JSONObject(obj.toString());
					String result = json_obj.get("Result").toString();
					String message = json_obj.get("Message").toString();
					if (result.equals("success")) {
						message = "success";
					}
					soapRes.setObj(message);
					soapRes.setCode(SOAP_UTILS.METHOD.DISCUSSIONREPLYHOST);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.DISCUSSIONREPLYHOST);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.DISCUSSIONREPLYHOST);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void discussionReplyOther(Object[] property_va) {
		// TODO Auto-generated method stub
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.DISCUSSIONREPLYOTHER);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONObject json_obj = new JSONObject(obj.toString());
					String result = json_obj.get("Result").toString();
					String message = json_obj.get("Message").toString();
					if (result.equals("success")) {
						message = "success";
					}
					soapRes.setObj(message);
					soapRes.setCode(SOAP_UTILS.METHOD.DISCUSSIONREPLYOTHER);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.DISCUSSIONREPLYOTHER);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.DISCUSSIONREPLYOTHER);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getMyAnswerUse(Object[] property_va, final boolean isPage) {
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.MYANSWERUSEFUL);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {

					JSONArray anwser_array = new JSONArray(obj.toString());
					List<Answer> anwser_list = new ArrayList<Answer>();

					for (int i = 0; i < anwser_array.length(); i++) {
						JSONObject json_anwser = (JSONObject) anwser_array.get(i);
						Answer anwser = new Answer();
						anwser.setcolumn(json_anwser.get("Column").toString());
						anwser.setcontent(json_anwser.get("Content").toString());
						anwser.setcrtime(json_anwser.get("Crtime").toString());
						anwser.setheadpic(json_anwser.get("HeadPic").toString());
						anwser.setwebid(json_anwser.get("Id").toString());
						anwser.setimg1(json_anwser.get("Img1").toString());
						anwser.setimg2(json_anwser.get("Img2").toString());
						anwser.setimgthumbnail1(json_anwser.get("Imgthumbnail1").toString());
						anwser.setimgthumbnail2(json_anwser.get("Imgthumbnail2").toString());
						anwser.setrealname(json_anwser.get("RealName").toString());
						anwser.settitle(json_anwser.get("Title").toString());
						anwser.setuserid(json_anwser.get("Userid").toString());

						anwser_list.add(anwser);
					}

					soapRes.setObj(anwser_list);
					soapRes.setPage(isPage);
					soapRes.setCode(SOAP_UTILS.METHOD.MYANSWERUSEFUL);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.MYANSWERUSEFUL);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.MYANSWERUSEFUL);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getMyAnswerIng(Object[] property_va, final boolean isPage) {
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.MYANSWERING);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {

					JSONArray anwser_array = new JSONArray(obj.toString());
					List<Answer> anwser_list = new ArrayList<Answer>();

					for (int i = 0; i < anwser_array.length(); i++) {
						JSONObject json_anwser = (JSONObject) anwser_array.get(i);
						Answer anwser = new Answer();
						anwser.setcolumn(json_anwser.get("Column").toString());
						anwser.setcontent(json_anwser.get("Content").toString());
						anwser.setcrtime(json_anwser.get("Crtime").toString());
						anwser.setheadpic(json_anwser.get("HeadPic").toString());
						anwser.setwebid(json_anwser.get("Id").toString());
						anwser.setimg1(json_anwser.get("Img1").toString());
						anwser.setimg2(json_anwser.get("Img2").toString());
						anwser.setimgthumbnail1(json_anwser.get("Imgthumbnail1").toString());
						anwser.setimgthumbnail2(json_anwser.get("Imgthumbnail2").toString());
						anwser.setrealname(json_anwser.get("RealName").toString());
						anwser.settitle(json_anwser.get("Title").toString());
						anwser.setuserid(json_anwser.get("Userid").toString());

						anwser_list.add(anwser);
					}

					soapRes.setObj(anwser_list);
					soapRes.setPage(isPage);
					soapRes.setCode(SOAP_UTILS.METHOD.MYANSWERING);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.MYANSWERING);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.MYANSWERING);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getMyAnswerDetail(Object[] property_va, final boolean isPage) {
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.MYANSWERJSON);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONObject json_obj = new JSONObject(obj.toString());
					String result = json_obj.get("Answerlist").toString();
					JSONArray questiondetail_array = new JSONArray(result);
					List<QuestionDetail> questiondetail_list = new ArrayList<QuestionDetail>();

					for (int i = 0; i < questiondetail_array.length(); i++) {
						JSONObject json_questiondetail = (JSONObject) questiondetail_array.get(i);
						QuestionDetail questiondetail = new QuestionDetail();
						questiondetail.setquesid(json_questiondetail.get("Quesid").toString());
						questiondetail.setcontent(json_questiondetail.get("Content").toString());
						questiondetail.setcrtime(json_questiondetail.get("Crtime").toString());
						questiondetail.setheadpic(json_questiondetail.get("HeadPic").toString());
						questiondetail.setwebid(json_questiondetail.get("Id").toString());
						questiondetail.setimg1(json_questiondetail.get("Img1").toString());
						questiondetail.setimg2(json_questiondetail.get("Img2").toString());
						questiondetail.setimgthumbnail1(json_questiondetail.get("Imgthumbnail1").toString());
						questiondetail.setimgthumbnail2(json_questiondetail.get("Imgthumbnail2").toString());
						questiondetail.setrealname(json_questiondetail.get("RealName").toString());
						questiondetail.setuseful(json_questiondetail.get("Useful").toString());
						questiondetail.setuserid(json_questiondetail.get("Userid").toString());

						questiondetail_list.add(questiondetail);
					}

					soapRes.setObj(questiondetail_list);
					soapRes.setPage(isPage);
					soapRes.setCode(SOAP_UTILS.METHOD.MYANSWERJSON);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.MYANSWERJSON);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.MYANSWERJSON);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void userExpertDetailChanged(Object[] property_va, final boolean isPage) {
		String[] property_nm = { "userid", "pagesize", "pageindex" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.USEREXPERTDETAILCHANGED);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONObject json_obj = new JSONObject(obj.toString());
					String result = json_obj.get("DiscussionlistNew").toString();
					JSONArray questiondetail_array = new JSONArray(result);
					List<UserExpertDetailChanged> questiondetail_list = new ArrayList<UserExpertDetailChanged>();
					for (int i = 0; i < questiondetail_array.length(); i++) {
						JSONObject json_questiondetail = (JSONObject) questiondetail_array.get(i);
						UserExpertDetailChanged questiondetail = new UserExpertDetailChanged();
						questiondetail.setAdminid(json_questiondetail.get("Adminid").toString());
						questiondetail.setColtitle(json_questiondetail.get("Coltitle").toString());
						questiondetail.setCrtime(json_questiondetail.get("Crtime").toString());
						questiondetail.setHeadPic(json_questiondetail.get("HeadPic").toString());
						questiondetail.setId(json_questiondetail.get("Id").toString());
						questiondetail.setOrders(json_questiondetail.get("Orders").toString());
						questiondetail.setOrgName(json_questiondetail.get("OrgName").toString());
						questiondetail.setPicture(json_questiondetail.get("Picture").toString());
						questiondetail.setRealName(json_questiondetail.get("RealName").toString());
						questiondetail.setSource(json_questiondetail.get("Source").toString());
						questiondetail.setThumbnail(json_questiondetail.get("Thumbnail").toString());
						questiondetail.setTitle(json_questiondetail.get("Title").toString());

						questiondetail_list.add(questiondetail);
					}

					soapRes.setObj(questiondetail_list);
					soapRes.setPage(isPage);
					soapRes.setCode(SOAP_UTILS.METHOD.USEREXPERTDETAILCHANGED);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.USEREXPERTDETAILCHANGED);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.USEREXPERTDETAILCHANGED);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getNewsComment(Object[] property_va, final boolean isPage) {
		String[] property_nm = { "newsid", "pagesize", "pageindex" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETNEWSCOMMENT);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONArray questiondetail_array = new JSONArray(obj.toString());
					List<WapCommentItem> questiondetail_list = new ArrayList<WapCommentItem>();
					for (int i = 0; i < questiondetail_array.length(); i++) {
						JSONObject json_questiondetail = (JSONObject) questiondetail_array.get(i);
						WapCommentItem questiondetail = new WapCommentItem();
						questiondetail.setCommentcont(json_questiondetail.get("Commentcont").toString());
						questiondetail.setCrtime(json_questiondetail.get("Crtime").toString());
						questiondetail.setId(json_questiondetail.get("Id").toString());
						questiondetail.setLaud(json_questiondetail.get("Laud").toString());
						questiondetail.setName(json_questiondetail.get("Name").toString());
						questiondetail.setNewsId(json_questiondetail.get("NewsId").toString());
						questiondetail.setOppose(json_questiondetail.get("Oppose").toString());
						questiondetail.setPrestige(json_questiondetail.get("Prestige").toString());
						questiondetail.setStrTime(json_questiondetail.get("StrTime").toString());
						questiondetail.setUserPic(json_questiondetail.get("UserPic").toString());
						questiondetail.setUserid(json_questiondetail.get("Userid").toString());

						questiondetail_list.add(questiondetail);
					}

					soapRes.setObj(questiondetail_list);
					soapRes.setPage(isPage);
					soapRes.setCode(SOAP_UTILS.METHOD.GETNEWSCOMMENT);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.GETNEWSCOMMENT);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETNEWSCOMMENT);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getLiving(Object[] property_va, final boolean isPage) {
		String[] property_nm = { "pagesize", "pageindex", "UserId" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETLIVING);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONArray nowliving_array = new JSONArray(obj.toString());
					List<NowLiving> nowliving_list = new ArrayList<NowLiving>();
					for (int i = 0; i < nowliving_array.length(); i++) {
						JSONObject json_nowliving = (JSONObject) nowliving_array.get(i);
						NowLiving nowliving = new NowLiving();
						nowliving.setAnswerCount(json_nowliving.get("AnswerCount").toString());
						nowliving.setAttention(json_nowliving.get("Attention").toString());
						nowliving.setCrtimeStr(json_nowliving.get("CrtimeStr").toString());
						// nowliving.setCcEndTime(json_nowliving.get("CcEndTime").toString());
						// nowliving.setCcStartTime(json_nowliving.get("CcStartTime").toString());
						nowliving.setCclive(json_nowliving.get("Cclive").toString());
						nowliving.setCcroomid(json_nowliving.get("Ccroomid").toString());
						nowliving.setCourseType(json_nowliving.get("CourseType").toString());
						nowliving.setCrtimeStr(json_nowliving.get("CrtimeStr").toString());
						nowliving.setDealAdvise(json_nowliving.get("DealAdvise").toString());
						nowliving.setDealControl(json_nowliving.get("DealControl").toString());
						nowliving.setDealOperate(json_nowliving.get("DealOperate").toString());
						nowliving.setDescribeCc(json_nowliving.get("DescribeCc").toString());
						nowliving.setHotlive(json_nowliving.get("Hotlive").toString());
						nowliving.setId(json_nowliving.get("Id").toString());
						nowliving.setLaud(json_nowliving.get("Laud").toString());
						nowliving.setLiveContent(json_nowliving.get("LiveContent").toString());
						nowliving.setLiveCount(json_nowliving.get("LiveCount").toString());
						nowliving.setLiveUserId(json_nowliving.get("LiveUserId").toString());
						nowliving.setLiveUserName(json_nowliving.get("LiveUserName").toString());
						nowliving.setLivings(json_nowliving.get("Livings").toString());
						nowliving.setNameCc(json_nowliving.get("NameCc").toString());
						nowliving.setToplive(json_nowliving.get("Toplive").toString());
						nowliving.setUserHeadpic(json_nowliving.get("UserHeadpic").toString());
						nowliving.setUserResume(json_nowliving.get("UserResume").toString());
						nowliving_list.add(nowliving);
					}

					soapRes.setObj(nowliving_list);
					soapRes.setPage(isPage);
					soapRes.setCode(SOAP_UTILS.METHOD.GETLIVING);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.GETLIVING);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETLIVING);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getLivingALl(Object[] property_va, final boolean isPage) {
		String[] property_nm = { "pagesize", "pageindex", "UserId" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETLIVINGALL);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONArray nowliving_array = new JSONArray(obj.toString());
					List<AllLiving> nowliving_list = new ArrayList<AllLiving>();
					for (int i = 0; i < nowliving_array.length(); i++) {
						JSONObject json_nowliving = (JSONObject) nowliving_array.get(i);
						AllLiving nowliving = new AllLiving();
						nowliving.setAnswerCount(json_nowliving.get("AnswerCount").toString());
						nowliving.setAttention(json_nowliving.get("Attention").toString());
						nowliving.setCrtimeStr(json_nowliving.get("CrtimeStr").toString());
						// nowliving.setCcEndTime(json_nowliving.get("CcEndTime").toString());
						// nowliving.setCcStartTime(json_nowliving.get("CcStartTime").toString());
						nowliving.setCclive(json_nowliving.get("Cclive").toString());
						nowliving.setCcroomid(json_nowliving.get("Ccroomid").toString());
						nowliving.setCourseType(json_nowliving.get("CourseType").toString());
						nowliving.setCrtimeStr(json_nowliving.get("CrtimeStr").toString());
						nowliving.setDealAdvise(json_nowliving.get("DealAdvise").toString());
						nowliving.setDealControl(json_nowliving.get("DealControl").toString());
						nowliving.setDealOperate(json_nowliving.get("DealOperate").toString());
						nowliving.setDescribeCc(json_nowliving.get("DescribeCc").toString());
						nowliving.setHotlive(json_nowliving.get("Hotlive").toString());
						nowliving.setId(json_nowliving.get("Id").toString());
						nowliving.setLaud(json_nowliving.get("Laud").toString());
						nowliving.setLiveContent(json_nowliving.get("LiveContent").toString());
						nowliving.setLiveCount(json_nowliving.get("LiveCount").toString());
						nowliving.setLiveUserId(json_nowliving.get("LiveUserId").toString());
						nowliving.setLiveUserName(json_nowliving.get("LiveUserName").toString());
						nowliving.setLivings(json_nowliving.get("Livings").toString());
						nowliving.setNameCc(json_nowliving.get("NameCc").toString());
						nowliving.setToplive(json_nowliving.get("Toplive").toString());
						nowliving.setUserHeadpic(json_nowliving.get("UserHeadpic").toString());
						nowliving.setUserResume(json_nowliving.get("UserResume").toString());
						nowliving_list.add(nowliving);
					}

					soapRes.setObj(nowliving_list);
					soapRes.setPage(isPage);
					soapRes.setCode(SOAP_UTILS.METHOD.GETLIVINGALL);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.GETLIVINGALL);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETLIVINGALL);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getLivingHot(Object[] property_va, final boolean isPage) {
		String[] property_nm = { "pagesize", "pageindex", "UserId" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETLIVINGHOT);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONArray nowliving_array = new JSONArray(obj.toString());
					List<HotLiving> nowliving_list = new ArrayList<HotLiving>();
					for (int i = 0; i < nowliving_array.length(); i++) {
						JSONObject json_nowliving = (JSONObject) nowliving_array.get(i);
						HotLiving nowliving = new HotLiving();
						nowliving.setAnswerCount(json_nowliving.get("AnswerCount").toString());
						nowliving.setAttention(json_nowliving.get("Attention").toString());
						nowliving.setCrtimeStr(json_nowliving.get("CrtimeStr").toString());
						// nowliving.setCcEndTime(json_nowliving.get("CcEndTime").toString());
						// nowliving.setCcStartTime(json_nowliving.get("CcStartTime").toString());
						nowliving.setCclive(json_nowliving.get("Cclive").toString());
						nowliving.setCcroomid(json_nowliving.get("Ccroomid").toString());
						nowliving.setCourseType(json_nowliving.get("CourseType").toString());
						nowliving.setCrtimeStr(json_nowliving.get("CrtimeStr").toString());
						nowliving.setDealAdvise(json_nowliving.get("DealAdvise").toString());
						nowliving.setDealControl(json_nowliving.get("DealControl").toString());
						nowliving.setDealOperate(json_nowliving.get("DealOperate").toString());
						nowliving.setDescribeCc(json_nowliving.get("DescribeCc").toString());
						nowliving.setHotlive(json_nowliving.get("Hotlive").toString());
						nowliving.setId(json_nowliving.get("Id").toString());
						nowliving.setLaud(json_nowliving.get("Laud").toString());
						nowliving.setLiveContent(json_nowliving.get("LiveContent").toString());
						nowliving.setLiveCount(json_nowliving.get("LiveCount").toString());
						nowliving.setLiveUserId(json_nowliving.get("LiveUserId").toString());
						nowliving.setLiveUserName(json_nowliving.get("LiveUserName").toString());
						nowliving.setLivings(json_nowliving.get("Livings").toString());
						nowliving.setNameCc(json_nowliving.get("NameCc").toString());
						nowliving.setToplive(json_nowliving.get("Toplive").toString());
						nowliving.setUserHeadpic(json_nowliving.get("UserHeadpic").toString());
						nowliving.setUserResume(json_nowliving.get("UserResume").toString());
						nowliving_list.add(nowliving);
					}

					soapRes.setObj(nowliving_list);
					soapRes.setPage(isPage);
					soapRes.setCode(SOAP_UTILS.METHOD.GETLIVINGHOT);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.GETLIVINGHOT);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETLIVINGHOT);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getLivingDetail(Object[] property_va, final boolean isPage) {
		String[] property_nm = { "Id", "pagesize", "pageindex" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETLIVINGDETAIL);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				soapRes.setObj(obj.toString());
				soapRes.setPage(isPage);
				soapRes.setCode(SOAP_UTILS.METHOD.GETLIVINGDETAIL);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETLIVINGDETAIL);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getCcLivingInfoSingle(Object[] property_va, final boolean isPage) {
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETCCLIVINGINFOSINGLE);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONArray jsonArray = new JSONArray(obj.toString());
					List<Course> arrayList = new ArrayList<Course>();

					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = (JSONObject) jsonArray.get(i);
						Course course = new Course();
						course.setId(jsonObject.get("Id").toString());
						course.setCcLiveTimetablesStatus(jsonObject.get("CcLiveTimetablesStatus").toString());
						course.setCourseName(jsonObject.get("CourseName").toString());
						course.setCourseType(jsonObject.get("CourseType").toString());
						course.setDescribe(jsonObject.get("Describe").toString());
						course.setEtime(jsonObject.get("Etime").toString());
						course.setEtimeStr(jsonObject.get("EtimeStr").toString());
						course.setLiveHeadPic(jsonObject.get("LiveHeadPic").toString());
						course.setLiveRoomId(jsonObject.get("LiveRoomId").toString());
						course.setLiveUserId(jsonObject.get("LiveUserId").toString());
						course.setLiveUserName(jsonObject.get("LiveUserName").toString());
						course.setMtime(jsonObject.get("Mtime").toString());
						course.setMtimeStr(jsonObject.get("MtimeStr").toString());
						course.setOnlineCount(jsonObject.get("OnlineCount").toString());
						course.setStime(jsonObject.get("Stime").toString());
						course.setStimeStr(jsonObject.get("StimeStr").toString());


						arrayList.add(course);
					}

					soapRes.setObj(arrayList);
					soapRes.setPage(isPage);
					soapRes.setCode(SOAP_UTILS.METHOD.GETCCLIVINGINFOSINGLE);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.GETCCLIVINGINFOSINGLE);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETCCLIVINGINFOSINGLE);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getInteractQA(Object[] property_va, final boolean isPage) {
		String[] property_nm = { "Id", "pagesize", "pageindex" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETINTERACTQA);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONArray nowliving_array = new JSONArray(obj.toString());
					List<QA> nowliving_list = new ArrayList<QA>();
					for (int i = 0; i < nowliving_array.length(); i++) {
						JSONObject json_nowliving = (JSONObject) nowliving_array.get(i);
						QA nowliving = new QA();
						nowliving.setAnswer(json_nowliving.get("Answer").toString());

						JSONArray qapic_array = new JSONArray(json_nowliving.get("Answpic").toString());
						if (qapic_array.length() != 0) {
							JSONObject json_qapic = (JSONObject) qapic_array.get(0);

							nowliving.setAnswpicId(json_qapic.get("Id").toString());
							nowliving.setAnswpicImg1(json_qapic.get("Img1").toString());
							nowliving.setAnswpicImg2(json_qapic.get("Img2").toString());
							nowliving.setAnswpicImgthumbnail1(json_qapic.get("Imgthumbnail1").toString());
							nowliving.setAnswpicImgthumbnail2(json_qapic.get("Imgthumbnail2").toString());

						}
						nowliving.setCrtime(json_nowliving.get("Crtime").toString());
						nowliving.setId(json_nowliving.get("Id").toString());
						nowliving.setQuContent(json_nowliving.get("QuContent").toString());
						nowliving.setQuUserName(json_nowliving.get("QuUserName").toString());
						nowliving.setTitle(json_nowliving.get("Title").toString());
						nowliving_list.add(nowliving);
					}

					soapRes.setObj(nowliving_list);
					soapRes.setPage(isPage);
					soapRes.setCode(SOAP_UTILS.METHOD.GETINTERACTQA);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.GETINTERACTQA);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETINTERACTQA);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getAttentionLiveUser(Object[] property_va) {
		// TODO Auto-generated method stub
		String[] property_nm = { "LiveUserId", "UserId", "type" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETATTENTIONLIVEUSER);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONObject json_obj = new JSONObject(obj.toString());
					String result = json_obj.get("Result").toString();
					String message = json_obj.get("Message").toString();
					// if (result.equals("success")) {
					// message = "success";
					// }
					soapRes.setObj(json_obj);
					soapRes.setCode(SOAP_UTILS.METHOD.GETATTENTIONLIVEUSER);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.GETATTENTIONLIVEUSER);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETATTENTIONLIVEUSER);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getLivingUpdate(Object[] property_va, final boolean isPage) {
		String[] property_nm = { "Id", "nowId", "pagesize", "pageindex" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETLIVINGUPDATE);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				soapRes.setObj(obj.toString());
				soapRes.setPage(isPage);
				soapRes.setCode(SOAP_UTILS.METHOD.GETLIVINGUPDATE);
				EventCache.commandActivity.post(soapRes);
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETLIVINGUPDATE);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void userInfoLoginUpdateImei(Object[] property_va) {
		// TODO Auto-generated method stub
		String[] property_nm = { "userid", "imei" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.USERINFOLOGINUPDATEIMEI);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONObject json_obj = new JSONObject(obj.toString());
					String result = json_obj.get("Result").toString();
					String message = json_obj.get("Message").toString();
					// if (result.equals("success")) {
					// message = "success";
					// }
					soapRes.setObj(json_obj);
					soapRes.setCode(SOAP_UTILS.METHOD.USERINFOLOGINUPDATEIMEI);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.USERINFOLOGINUPDATEIMEI);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.USERINFOLOGINUPDATEIMEI);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void updateImei(Object[] property_va) {
		// TODO Auto-generated method stub
		String[] property_nm = { "userid", "imei" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.UPDATEIMEI);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONObject json_obj = new JSONObject(obj.toString());
					String result = json_obj.get("Result").toString();
					String message = json_obj.get("Message").toString();
					// if (result.equals("success")) {
					// message = "success";
					// }
					soapRes.setObj(json_obj);
					soapRes.setCode(SOAP_UTILS.METHOD.UPDATEIMEI);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.UPDATEIMEI);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.UPDATEIMEI);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void getCode(Object[] property_va) {
		// TODO Auto-generated method stub
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETCODE);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {

					JSONObject json_obj = new JSONObject(obj.toString());
					soapRes.setObj(json_obj);
					soapRes.setCode(SOAP_UTILS.METHOD.GETCODE);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.GETCODE);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.GETCODE);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void bindPhoneNum(Object[] property_va) {
		// TODO Auto-generated method stub
		String[] property_nm = {};
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.BINDPHONENUM);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONObject json_obj = new JSONObject(obj.toString());
					String result = json_obj.get("Result").toString();
					String message = json_obj.get("Message").toString();
					// if (result.equals("success")) {
					// message = "success";
					// }
					soapRes.setObj(json_obj);
					soapRes.setCode(SOAP_UTILS.METHOD.BINDPHONENUM);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.BINDPHONENUM);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.BINDPHONENUM);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void codeVerify(Object[] property_va) {
		// TODO Auto-generated method stub
		String[] property_nm = { "sim", "code" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.CODEVERIFY);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONObject json_obj = new JSONObject(obj.toString());
					String result = json_obj.get("Result").toString();
					String message = json_obj.get("Message").toString();
					// if (result.equals("success")) {
					// message = "success";
					// }
					soapRes.setObj(json_obj);
					soapRes.setCode(SOAP_UTILS.METHOD.CODEVERIFY);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.CODEVERIFY);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.CODEVERIFY);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}

	@Override
	public void userPasswordFind(Object[] property_va) {
		// TODO Auto-generated method stub
		String[] property_nm = { "sim", "pwd_new" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.USERPASSWORDFIND);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new HttpObjectResult() {

			@Override
			public void soapResult(Object obj) {
				// TODO Auto-generated method stub
				try {
					JSONObject json_obj = new JSONObject(obj.toString());
					String result = json_obj.get("Result").toString();
					String message = json_obj.get("Message").toString();
					// if (result.equals("success")) {
					// message = "success";
					// }
					soapRes.setObj(json_obj);
					soapRes.setCode(SOAP_UTILS.METHOD.USERPASSWORDFIND);
					EventCache.commandActivity.post(soapRes);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					soapRes.setObj(null);
					soapRes.setCode(SOAP_UTILS.METHOD.USERPASSWORDFIND);
					EventCache.commandActivity.post(soapRes);
				}
			}

			@Override
			public void soapError() {
				soapRes.setObj(null);
				soapRes.setCode(SOAP_UTILS.METHOD.USERPASSWORDFIND);
				EventCache.commandActivity.post(soapRes);
			}
		});
	}


	@Override
	public void adminLogin(Object[] property_va) {
		// TODO Auto-generated method stub

	}

	@Override
	public void communReplyAdd(Object[] property_va) {
		// TODO Auto-generated method stub

	}

	@Override
	public void communicationAdd(Object[] property_va) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getAdmin(Object[] property_va) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getColumns(Object[] property_va, boolean isPage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getCommunReply(Object[] property_va, List<Anwser> list) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getCommunReplyAna(Object[] property_va) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getCommunReplyByID(Object[] property_va) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getCommunication(Object[] property_va) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getCommunicationALL(Object[] property_va, boolean isPage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getCommunicationAllUser(Object[] property_va, boolean isPage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getCommunicationAna(Object[] property_va) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getNewsContent(Object[] property_va) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getNewsTitle(Object[] property_va, boolean isPage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void interactionHomepage(Object[] property_va) {
		// TODO Auto-generated method stub

	}

	@Override
	public void interactionMessage(Object[] property_va) {
		// TODO Auto-generated method stub

	}

	@Override
	public void interactionMessageAna(Object[] property_va) {
		// TODO Auto-generated method stub

	}

	@Override
	public void interactionMessageByID(Object[] property_va) {
		// TODO Auto-generated method stub

	}

	@Override
	public void interactionSubmit(Object[] property_va) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updataBestAnswer(Object[] property_va) {
		// TODO Auto-generated method stub

	}

	@Override
	public void userPayment(Object[] property_va) {
		// TODO Auto-generated method stub

	}

	@Override
	public void userPasswordReset(Object[] property_va) {
		// TODO Auto-generated method stub

	}

	@Override
	public void userNameCheck(Object[] property_va) {
		// TODO Auto-generated method stub

	}

	@Override
	public void userEditor(Object[] property_va) {
		// TODO Auto-generated method stub

	}

	@Override
	public void userEditor_city(Object[] property_va) {
		// TODO Auto-generated method stub

	}

	@Override
	public void userEditor_head(Object[] property_va) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getReportList(Object[] property_va, boolean isPage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reportSubmit(Object[] property_va) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getReportContent(Object[] property_va) {
		// TODO Auto-generated method stub

	}

	@Override
	public void heartCountUpdate(Object[] property_va) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getOrderInfoService(Object[] property_va) {
		// TODO Auto-generated method stub

	}


}
