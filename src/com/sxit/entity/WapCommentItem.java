package com.sxit.entity;

import java.io.Serializable;

/**
 * 描述：广告信息</br>
 * @author Eden Cheng</br>
 * @version 2015年4月23日 上午11:32:53
 */
public class WapCommentItem implements Serializable{
	String Commentcont = "";
	String Crtime = "";
	String Id = "";
	String Laud = "";
	String Name = "";
	String NewsId = "";
	String Oppose = "";
	String Prestige = "";
	String StrTime = "";
	String UserPic = "";
	String Userid = "";
	public String getCommentcont() {
		return Commentcont;
	}
	public void setCommentcont(String commentcont) {
		Commentcont = commentcont;
	}
	public String getCrtime() {
		return Crtime;
	}
	public void setCrtime(String crtime) {
		Crtime = crtime;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getLaud() {
		return Laud;
	}
	public void setLaud(String laud) {
		Laud = laud;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getNewsId() {
		return NewsId;
	}
	public void setNewsId(String newsId) {
		NewsId = newsId;
	}
	public String getOppose() {
		return Oppose;
	}
	public void setOppose(String oppose) {
		Oppose = oppose;
	}
	public String getPrestige() {
		return Prestige;
	}
	public void setPrestige(String prestige) {
		Prestige = prestige;
	}
	public String getStrTime() {
		return StrTime;
	}
	public void setStrTime(String strTime) {
		StrTime = strTime;
	}
	public String getUserPic() {
		return UserPic;
	}
	public void setUserPic(String userPic) {
		UserPic = userPic;
	}
	public String getUserid() {
		return Userid;
	}
	public void setUserid(String userid) {
		Userid = userid;
	}
	

}
