package com.sxit.entity;

import java.util.ArrayList;

public class LiveEntity {
	private String content; // 用户头像URL
	private String crtime; // 标题
	private String headpic; // 内容
	private String id;
	private String orgname;
	private String realname;
	private ArrayList<String> magpic; // 九宫格图片的URL集合

//	public LiveEntity(String content, String crtime, String headpic, String id, String orgname, String realname, ArrayList<String> magpic) {
//		super();
//		this.content = content;
//		this.crtime = crtime;
//		this.headpic = headpic;
//		this.id = id;
//		this.orgname = orgname;
//		this.realname = realname;
//		this.magpic = magpic;
//	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCrtime() {
		return crtime;
	}

	public void setCrtime(String crtime) {
		this.crtime = crtime;
	}

	public String getHeadpic() {
		return headpic;
	}

	public void setHeadpic(String headpic) {
		this.headpic = headpic;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	
	public String getRealName() {
		return realname;
	}

	public void setRealName(String realname) {
		this.realname = realname;
	}

	public ArrayList<String> getMagPics() {
		return magpic;
	}

	public void setMagPics(ArrayList<String> magpic) {
		this.magpic = magpic;
	}

	@Override
	public String toString() {
		return "LiveEntity [avatar=" + headpic + ", title=" + realname + ", content=" + content + ", imageUrls=" + magpic + "]";
	}

}
