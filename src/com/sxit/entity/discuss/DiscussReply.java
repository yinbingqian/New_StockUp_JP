package com.sxit.entity.discuss;

import java.io.Serializable;

/**
 * 描述：广告信息</br>
 * @author Eden Cheng</br>
 * @version 2015年4月23日 上午11:32:53
 */
public class DiscussReply implements Serializable{
	String Id = "";
	String Floor = "";
	String Content = "";
	String Crtime = "";
	String Mark = "";
	String HeadPic = "";
	String Hostid = "";
	String RealName = "";
	String Replyid = "";
	String Userid = "";
	
	public String getId() {
		return Id;
	}
	public void setId(String Id) {
		this.Id = Id;
	}
	public String getFloor() {
		return Floor;
	}
	public void setFloor(String Floor) {
		this.Floor = Floor;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String Content) {
		this.Content = Content;
	}
	
	public String getCrtime() {
		return Crtime;
	}
	public void setCrtime(String Crtime) {
		this.Crtime = Crtime;
	}
	
	public String getMark() {
		return Mark;
	}
	public void setMark(String Mark) {
		this.Mark = Mark;
	}

	public String getHeadPic() {
		return HeadPic;
	}
	public void setHeadPic(String HeadPic) {
		this.HeadPic = HeadPic;
	}

	public String getHostid() {
		return Hostid;
	}
	public void setHostid(String Hostid) {
		this.Hostid = Hostid;
	}

	public String getRealName() {
		return RealName;
	}
	public void setRealName(String RealName) {
		this.RealName = RealName;
	}

	public String getReplyid() {
		return Replyid;
	}
	public void setReplyid(String Replyid) {
		this.Replyid = Replyid;
	}

	public String getUserid() {
		return Userid;
	}
	public void setUserid(String Userid) {
		this.Userid = Userid;
	}
}
