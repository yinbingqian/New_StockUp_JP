package com.sxit.entity.smarter;

import java.io.Serializable;

public class ExpertDetail implements Serializable {
	private String id;
	private String column;
	private String content;
	private String crtime;
	private String downmark;
	private String headpic;
	private String hot;
	private String webid;
	private String img1;
	private String img2;
	private String imgthumbnail1;
	private String imgthumbnail2;
	private String mark;
	private String realname;
	private String replynum;
	private String rewardmark;
	private String sign;
	private String userid;

	public ExpertDetail() {

	}

	public ExpertDetail(String column, String content, String crtime,
			String downmark, String headpic, String hot, String webid,
			String img1, String img2, String imgthumbnail1,
			String imgthumbnail2, String mark, String realname,
			String replynum, String rewardmark, String sign, String userid) {
		this.column = column;
		this.content = content;
		this.crtime = crtime;
		this.downmark = downmark;
		this.headpic = headpic;
		this.hot = hot;
		this.webid = webid;
		this.img1 = img1;
		this.img2 = img2;
		this.imgthumbnail1 = imgthumbnail1;
		this.imgthumbnail2 = imgthumbnail2;
		this.mark = mark;
		this.realname = realname;
		this.replynum = replynum;
		this.rewardmark = rewardmark;
		this.sign = sign;
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

	public String getdownmark() {
		return downmark;
	}

	public void setdownmark(String downmark) {
		this.downmark = downmark;
	}
	public String getheadpic() {
		return headpic;
	}

	public void setheadpic(String headpic) {
		this.headpic = headpic;
	}
	public String gethot() {
		return hot;
	}

	public void sethot(String hot) {
		this.hot = hot;
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

	public String getmark() {
		return mark;
	}

	public void setmark(String mark) {
		this.mark = mark;
	}

	public String getrealname() {
		return realname;
	}

	public void setrealname(String realname) {
		this.realname = realname;
	}


	public String getreplynum() {
		return replynum;
	}

	public void setreplynum(String replynum) {
		this.replynum = replynum;
	}
	public String getrewardmark() {
		return rewardmark;
	}

	public void setrewardmark(String rewardmark) {
		this.rewardmark = rewardmark;
	}
	public String getsign() {
		return sign;
	}

	public void setsign(String sign) {
		this.sign = sign;
	}
	public String getuserid() {
		return userid;
	}

	public void setuserid(String userid) {
		this.userid = userid;
	}
}
