package com.sxit.entity;

import java.io.Serializable;

public class DuelPoint implements Serializable {

	private String id;
	private String Userid;
	private String Content;
	private String Viewpoint;
	private String Supportrate;
	private String RealName;
	private String HeadPic;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getUserid() {
		return Userid;
	}

	public void setUserid(String Userid) {
		this.Userid = Userid;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String Content) {
		this.Content = Content;
	}

	public String getViewpoint() {
		return Viewpoint;
	}

	public void setViewpoint(String Viewpoint) {
		this.Viewpoint = Viewpoint;
	}

	public String getSupportrate() {
		return Supportrate;
	}

	public void setSupportrate(String Supportrate) {
		this.Supportrate = Supportrate;
	}

	public String getRealName() {
		return RealName;
	}

	public void setRealName(String RealName) {
		this.RealName = RealName;
	}

	public String getHeadPic() {
		return HeadPic;
	}

	public void setHeadPic(String HeadPic) {
		this.HeadPic = HeadPic;
	}

}
