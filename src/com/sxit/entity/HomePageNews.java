package com.sxit.entity;

import java.io.Serializable;

public class HomePageNews implements Serializable {

	private String Adminid;
	private String Coltitle;
	private String Crtime;
	private String Id;
	private String NewsComCount;
	private String Orders;
	private String Picture;
	private String Source;
	private String Thumbnail;
	private String Title;
	private String HeadPic;
	private String OrgName;
	private String RealName;

	public String getNewsComCount() {
		return NewsComCount;
	}

	public void setNewsComCount(String newsComCount) {
		NewsComCount = newsComCount;
	}

	public String getAdminid() {
		return Adminid;
	}

	public void setAdminid(String Adminid) {
		this.Adminid = Adminid;
	}

	public String getColtitle() {
		return Coltitle;
	}

	public void setColtitle(String Coltitle) {
		this.Coltitle = Coltitle;
	}

	public String getCrtime() {
		return Crtime;
	}

	public void setCrtime(String Crtime) {
		this.Crtime = Crtime;
	}

	public String getId() {
		return Id;
	}

	public void setId(String Id) {
		this.Id = Id;
	}

	public String getOrders() {
		return Orders;
	}

	public void setOrders(String orders) {
		this.Orders = orders;
	}

	public String getPicture() {
		return Picture;
	}

	public void setPicture(String Picture) {
		this.Picture = Picture;
	}

	public String getSource() {
		return Source;
	}

	public void setSource(String Source) {
		this.Source = Source;
	}

	public String getThumbnail() {
		return Thumbnail;
	}

	public void setThumbnail(String Thumbnail) {
		this.Thumbnail = Thumbnail;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String Title) {
		this.Title = Title;
	}

	public String getHeadPic() {
		return HeadPic;
	}

	public void setHeadPic(String HeadPic) {
		this.HeadPic = HeadPic;
	}

	public String getOrgName() {
		return OrgName;
	}

	public void setOrgName(String OrgName) {
		this.OrgName = OrgName;
	}

	public String getRealName() {
		return RealName;
	}

	public void setRealName(String RealName) {
		this.RealName = RealName;
	}
}
