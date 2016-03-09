package com.sxit.entity.discuss;

import java.io.Serializable;

/**
 * 描述：广告信息</br>
 * @author Eden Cheng</br>
 * @version 2015年4月23日 上午11:32:53
 */
public class DiscussItem implements Serializable{
	String Id = "";
	String Column = "";
	String Content = "";
	String Crtime = "";
	String Downmark = "";
	String HeadPic = "";
	String Hot = "";
	String Img1 = "";
	String Img2 = "";
	String Imgthumbnail1 = "";
	String Imgthumbnail2 = "";
	String Mark = "";
	String RealName = "";
	String Replynum = "";
	String Rewardmark = "";
	String Sign = "";
	String Userid = "";
	
	public String getId() {
		return Id;
	}
	public void setId(String Id) {
		this.Id = Id;
	}
	public String getColumn() {
		return Column;
	}
	public void setColumn(String Column) {
		this.Column = Column;
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
	
	public String getDownmark() {
		return Downmark;
	}
	public void setDownmark(String Downmark) {
		this.Downmark = Downmark;
	}

	public String getHeadPic() {
		return HeadPic;
	}
	public void setHeadPic(String HeadPic) {
		this.HeadPic = HeadPic;
	}

	public String getHot() {
		return Hot;
	}
	public void setHot(String Hot) {
		this.Hot = Hot;
	}

	public String getImg1() {
		return Img1;
	}
	public void setImg1(String Img1) {
		this.Img1 = Img1;
	}

	public String getImg2() {
		return Img2;
	}
	public void setImg2(String Img2) {
		this.Img2 = Img2;
	}

	public String getImgthumbnail1() {
		return Imgthumbnail1;
	}
	public void setImgthumbnail1(String Imgthumbnail1) {
		this.Imgthumbnail1 = Imgthumbnail1;
	}

	public String getImgthumbnail2() {
		return Imgthumbnail2;
	}
	public void setImgthumbnail2(String Imgthumbnail2) {
		this.Imgthumbnail2 = Imgthumbnail2;
	}

	public String getMark() {
		return Mark;
	}
	public void setMark(String Mark) {
		this.Mark = Mark;
	}

	public String getRealName() {
		return RealName;
	}
	public void setRealName(String RealName) {
		this.RealName = RealName;
	}

	public String getReplynum() {
		return Replynum;
	}
	public void setReplynum(String Replynum) {
		this.Replynum = Replynum;
	}

	public String getRewardmark() {
		return Rewardmark;
	}
	public void setRewardmark(String Rewardmark) {
		this.Rewardmark = Rewardmark;
	}

	public String getSign() {
		return Sign;
	}
	public void setSign(String Sign) {
		this.Sign = Sign;
	}

	public String getUserid() {
		return Userid;
	}
	public void setUserid(String Userid) {
		this.Userid = Userid;
	}
}
