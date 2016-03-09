package com.sxit.entity.smarter;

import java.io.Serializable;

public class UserExpertDetailChanged implements Serializable {
	private String Adminid;
	private String Coltitle;
	private String Crtime;
	private String HeadPic;
	private String Id;
	private String Orders;
	private String OrgName;
	private String Picture;
	private String RealName;
	private String Source;
	private String Thumbnail;
	private String Title;

	public UserExpertDetailChanged() {

	}

	public UserExpertDetailChanged(String Adminid, String Coltitle, String Crtime,
			String HeadPic, String Id, String Orders, String OrgName,
			String Picture, String RealName, String Source,
			String Thumbnail, String Title) {
		this.Adminid = Adminid;
		this.Coltitle = Coltitle;
		this.Crtime = Crtime;
		this.HeadPic = HeadPic;
		this.Id = Id;
		this.Orders = Orders;
		this.OrgName = OrgName;
		this.Picture = Picture;
		this.RealName = RealName;
		this.Source = Source;
		this.Thumbnail = Thumbnail;
		this.Title = Title;
	}

	public String getAdminid() {
		return Adminid;
	}

	public void setAdminid(String adminid) {
		Adminid = adminid;
	}

	public String getColtitle() {
		return Coltitle;
	}

	public void setColtitle(String coltitle) {
		Coltitle = coltitle;
	}

	public String getCrtime() {
		return Crtime;
	}

	public void setCrtime(String crtime) {
		Crtime = crtime;
	}

	public String getHeadPic() {
		return HeadPic;
	}

	public void setHeadPic(String headPic) {
		HeadPic = headPic;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getOrders() {
		return Orders;
	}

	public void setOrders(String orders) {
		Orders = orders;
	}

	public String getOrgName() {
		return OrgName;
	}

	public void setOrgName(String orgName) {
		OrgName = orgName;
	}

	public String getPicture() {
		return Picture;
	}

	public void setPicture(String picture) {
		Picture = picture;
	}

	public String getRealName() {
		return RealName;
	}

	public void setRealName(String realName) {
		RealName = realName;
	}

	public String getSource() {
		return Source;
	}

	public void setSource(String source) {
		Source = source;
	}

	public String getThumbnail() {
		return Thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		Thumbnail = thumbnail;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

}
