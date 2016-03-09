package com.sxit.entity.smarter;

import java.io.Serializable;

public class Expert implements Serializable {
	private String id;
	private String rewardmark;
	private String headpic;
	private String webid;
	private String level;
	private String mark;
	private String realname;
	private String stockstyle;
	private String resume;

	public Expert() {

	}

	public Expert(String rewardmark, String headpic, String webid,
			String level, String mark, String realname, String stockstyle,
			String resume) {
		this.rewardmark = rewardmark;
		this.headpic = headpic;
		this.webid = webid;
		this.level = level;
		this.mark = mark;
		this.realname = realname;
		this.stockstyle = stockstyle;
		this.resume = resume;
	}

	public String getid() {
		return id;
	}

	public void setid(String id) {
		this.id = id;
	}

	public String getrewardmark() {
		return rewardmark;
	}

	public void setrewardmark(String rewardmark) {
		this.rewardmark = rewardmark;
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

	public String getlevel() {
		return level;
	}

	public void setlevel(String level) {
		this.level = level;
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

	public String getstockstyle() {
		return stockstyle;
	}

	public void setstockstyle(String stockstyle) {
		this.stockstyle = stockstyle;
	}

	public String getresume() {
		return resume;
	}

	public void setresume(String resume) {
		this.resume = resume;
	}
}
