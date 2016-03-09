package com.sxit.entity.smarter;

import java.io.Serializable;

public class GuestDetail implements Serializable {
	private String id;
	private String adminid;
	private String coltitle;
	private String crtime;
	private String headpic;
	private String webid;
	private String orders;
	private String orgname;
	private String picture;
	private String realname;
	private String source;
	private String thumbnail;
	private String title;

	public GuestDetail() {

	}

	public GuestDetail(String adminid, String coltitle, String crtime,
			String headpic, String webid, String orders, String orgname,
			String picture, String realname, String source, String thumbnail,
			String title) {
		this.adminid = adminid;
		this.coltitle = coltitle;
		this.crtime = crtime;
		this.headpic = headpic;
		this.webid = webid;
		this.orders = orders;
		this.orgname = orgname;
		this.picture = picture;
		this.realname = realname;
		this.source = source;
		this.thumbnail = thumbnail;
		this.title = title;
	}

	public String getid() {
		return id;
	}

	public void setid(String id) {
		this.id = id;
	}

	public String getadminid() {
		return adminid;
	}

	public void setadminid(String adminid) {
		this.adminid = adminid;
	}

	public String getcoltitle() {
		return coltitle;
	}

	public void setcoltitle(String coltitle) {
		this.coltitle = coltitle;
	}

	public String getcrtime() {
		return crtime;
	}

	public void setcrtime(String crtime) {
		this.crtime = crtime;
	}

	public String getheadpic() {
		return headpic;
	}

	public void setheadpic(String headpic) {
		this.headpic = headpic;
	}

	public String getwebid() {
		return webid;
	}

	public void setwebid(String webid) {
		this.webid = webid;
	}

	public String getorders() {
		return orders;
	}

	public void setorders(String orders) {
		this.orders = orders;
	}

	public String getorgname() {
		return orgname;
	}

	public void setorgname(String orgname) {
		this.orgname = orgname;
	}

	public String getpicture() {
		return picture;
	}

	public void setpicture(String picture) {
		this.picture = picture;
	}


	public String getrealname() {
		return realname;
	}

	public void setrealname(String realname) {
		this.realname = realname;
	}

	public String getsource() {
		return source;
	}

	public void setsource(String source) {
		this.source = source;
	}
	public String getthumbnail() {
		return thumbnail;
	}

	public void setthumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String gettitle() {
		return title;
	}

	public void settitle(String title) {
		this.title = title;
	}
}
