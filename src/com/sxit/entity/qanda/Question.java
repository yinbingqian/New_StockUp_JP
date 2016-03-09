package com.sxit.entity.qanda;

import java.io.Serializable;

public class Question implements Serializable {
	private String id;
	private String answernum;
	private String column;
	private String content;
	private String crtime;
	private String webid;
	private String img1;
	private String img2;
	private String imgthumbnail1;
	private String imgthumbnail2;
	private String title;
	private String userid;

	public Question() {

	}

	public Question(String answernum, String column, String content, String crtime,
			String webid, String img1, String img2, String imgthumbnail1,
			String imgthumbnail2, String title, String userid) {
		this.answernum = answernum;
		this.column = column;
		this.content = content;
		this.crtime = crtime;
		this.webid = webid;
		this.img1 = img1;
		this.img2 = img2;
		this.imgthumbnail1 = imgthumbnail1;
		this.imgthumbnail2 = imgthumbnail2;
		this.title = title;
		this.userid = userid;
	}

	public String getid() {
		return id;
	}

	public void setid(String id) {
		this.id = id;
	}

	public String getanswernum() {
		return answernum;
	}

	public void setanswernum(String answernum) {
		this.answernum = answernum;
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
