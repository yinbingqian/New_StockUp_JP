package com.sxit.entity.qanda;

import java.io.Serializable;

public class Answer implements Serializable {
	private String id;
	private String column;
	private String content;
	private String crtime;
	private String headpic;
	private String webid;
	private String img1;
	private String img2;
	private String imgthumbnail1;
	private String imgthumbnail2;
	private String realname;
	private String title;
	private String userid;

	public Answer() {

	}

	public Answer(String column, String content, String crtime, String headpic,
			String webid, String img1, String img2, String imgthumbnail1,
			String imgthumbnail2, String realname,String title, String userid) {
		this.column = column;
		this.content = content;
		this.crtime = crtime;
		this.headpic = headpic;
		this.webid = webid;
		this.img1 = img1;
		this.img2 = img2;
		this.imgthumbnail1 = imgthumbnail1;
		this.imgthumbnail2 = imgthumbnail2;
		this.realname = realname;
		this.title = title;
		this.userid = userid;
	}

	public String getid() {
		return id;
	}

	public void setid(String id) {
		this.id = id;
	}

	public String getcolumn() {
		return column;
	}

	public void setcolumn(String column) {
		this.column = column;
	}

	public String getcontent() {
		return content;
	}

	public void setcontent(String content) {
		this.content = content;
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

	public String getimg1() {
		return img1;
	}

	public void setimg1(String img1) {
		this.img1 = img1;
	}

	public String getimg2() {
		return img2;
	}

	public void setimg2(String img2) {
		this.img2 = img2;
	}

	public String getimgthumbnail1() {
		return imgthumbnail1;
	}

	public void setimgthumbnail1(String imgthumbnail1) {
		this.imgthumbnail1 = imgthumbnail1;
	}

	public String getimgthumbnail2() {
		return imgthumbnail2;
	}

	public void setimgthumbnail2(String imgthumbnail2) {
		this.imgthumbnail2 = imgthumbnail2;
	}

	public String getrealname() {
		return realname;
	}

	public void setrealname(String realname) {
		this.realname = realname;
	}

	public String gettitle() {
		return title;
	}

	public void settitle(String title) {
		this.title = title;
	}

	public String getuserid() {
		return userid;
	}

	public void setuserid(String userid) {
		this.userid = userid;
	}
}
