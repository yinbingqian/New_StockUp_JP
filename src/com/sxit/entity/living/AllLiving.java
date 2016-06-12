package com.sxit.entity.living;

import java.io.Serializable;

public class AllLiving implements Serializable {
	private String AnswerCount;
	private String Attention;
	private String CrtimeStr;
	private String DealAdvise;
	private String DealControl;
	private String DealOperate;
	private String Hotlive;
	private String Id;
	private String Laud;
	private String LiveContent;
	private String LiveCount;
	private String LiveUserId;
	private String LiveUserName;
	private String Livings;
	private String DescribeCc;
	private String Toplive;
	private String UserHeadpic;
	private String UserResume;
    	
	public String getAttention() {
		return Attention;
	}

	public void setAttention(String attention) {
		Attention = attention;
	}

	public String getHotlive() {
		return Hotlive;
	}

	public void setHotlive(String hotlive) {
		Hotlive = hotlive;
	}

	public String getToplive() {
		return Toplive;
	}

	public void setToplive(String toplive) {
		Toplive = toplive;
	}

	public AllLiving() {

	}

	public AllLiving(String AnswerCount, String CrtimeStr, String DealAdvise,
			String DealControl, String DealOperate,String Hotlive, String Id, String Laud,
			String LiveContent, String LiveCount, String LiveUserId, String LiveUserName, 
			String Livings, String DescribeCc,String Toplive, String UserHeadpic, String UserResume) {
		this.AnswerCount = AnswerCount;
		this.CrtimeStr = CrtimeStr;
		this.DealAdvise = DealAdvise;
		this.DealControl = DealControl;
		this.DealOperate = DealOperate;
		this.Hotlive = Hotlive;
		this.Id = Id;
		this.Laud = Laud;
		this.LiveContent = LiveContent;
		this.LiveCount = LiveCount;
		this.LiveUserId = LiveUserId;
		this.LiveUserName = LiveUserName;
		this.Livings = Livings;
		this.DescribeCc = DescribeCc;
		this.Toplive = Toplive;
		this.UserHeadpic = UserHeadpic;
		this.UserResume = UserResume;
	}

	public String getAnswerCount() {
		return AnswerCount;
	}

	public void setAnswerCount(String answerCount) {
		AnswerCount = answerCount;
	}

	public String getCrtimeStr() {
		return CrtimeStr;
	}

	public void setCrtimeStr(String crtimeStr) {
		CrtimeStr = crtimeStr;
	}

	public String getDealAdvise() {
		return DealAdvise;
	}

	public void setDealAdvise(String dealAdvise) {
		DealAdvise = dealAdvise;
	}

	public String getDealControl() {
		return DealControl;
	}

	public void setDealControl(String dealControl) {
		DealControl = dealControl;
	}

	public String getDealOperate() {
		return DealOperate;
	}

	public void setDealOperate(String dealOperate) {
		DealOperate = dealOperate;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getLaud() {
		return Laud;
	}

	public void setLaud(String laud) {
		Laud = laud;
	}

	public String getLiveContent() {
		return LiveContent;
	}

	public void setLiveContent(String liveContent) {
		LiveContent = liveContent;
	}

	public String getLiveCount() {
		return LiveCount;
	}

	public void setLiveCount(String liveCount) {
		LiveCount = liveCount;
	}

	public String getLiveUserId() {
		return LiveUserId;
	}

	public void setLiveUserId(String liveUserId) {
		LiveUserId = liveUserId;
	}

	public String getLiveUserName() {
		return LiveUserName;
	}

	public void setLiveUserName(String liveUserName) {
		LiveUserName = liveUserName;
	}

	public String getLivings() {
		return Livings;
	}

	public void setLivings(String livings) {
		Livings = livings;
	}

	public String getDescribeCc() {
		return DescribeCc;
	}

	public void setDescribeCc(String todayViewPoint) {
		DescribeCc = todayViewPoint;
	}

	public String getUserHeadpic() {
		return UserHeadpic;
	}

	public void setUserHeadpic(String userHeadpic) {
		UserHeadpic = userHeadpic;
	}

	public String getUserResume() {
		return UserResume;
	}

	public void setUserResume(String userResume) {
		UserResume = userResume;
	}

}
