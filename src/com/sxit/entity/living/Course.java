package com.sxit.entity.living;

import java.io.Serializable;

public class Course implements Serializable {
	private String id;
	private String ccLiveTimetablesStatus;
	private String courseName;
	private String courseType;
	private String describe;
	private String etime;
	private String etimeStr;
	private String liveHeadPic;
	private String liveRoomId;
	private String liveUserId;
	private String liveUserName;
	private String mtime;
	private String mtimeStr;
	private String onlineCount;
	private String stime;
	private String stimeStr;
	//
	// public Course() {
	//
	// }
	//
	// public Course(String id, String ccLiveTimetablesStatus, String
	// courseName, String courseType, String describe,
	// String etime, String etimeStr, String liveHeadPic, String liveRoomId,
	// String liveUserId,
	// String liveUserName, String mtime, String mtimeStr, String onlineCount,
	// String stime, String stimeStr) {
	// this.id = id;
	// this.ccLiveTimetablesStatus = ccLiveTimetablesStatus;
	// this.courseName = courseName;
	// this.courseType = courseType;
	// this.describe = describe;
	// this.etime = etime;
	// this.etimeStr = etimeStr;
	// this.liveHeadPic = liveHeadPic;
	// this.liveRoomId = liveRoomId;
	// this.liveUserId = liveUserId;
	// this.liveUserName = liveUserName;
	// this.mtime = mtime;
	// this.mtimeStr = mtimeStr;
	// this.onlineCount = onlineCount;
	// this.stime = stime;
	// this.stimeStr = stimeStr;
	// }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCcLiveTimetablesStatus() {
		return ccLiveTimetablesStatus;
	}

	public void setCcLiveTimetablesStatus(String ccLiveTimetablesStatus) {
		this.ccLiveTimetablesStatus = ccLiveTimetablesStatus;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseType() {
		return courseType;
	}

	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getEtime() {
		return etime;
	}

	public void setEtime(String etime) {
		this.etime = etime;
	}

	public String getEtimeStr() {
		return etimeStr;
	}

	public void setEtimeStr(String etimeStr) {
		this.etimeStr = etimeStr;
	}

	public String getLiveHeadPic() {
		return liveHeadPic;
	}

	public void setLiveHeadPic(String liveHeadPic) {
		this.liveHeadPic = liveHeadPic;
	}

	public String getLiveRoomId() {
		return liveRoomId;
	}

	public void setLiveRoomId(String liveRoomId) {
		this.liveRoomId = liveRoomId;
	}

	public String getLiveUserId() {
		return liveUserId;
	}

	public void setLiveUserId(String liveUserId) {
		this.liveUserId = liveUserId;
	}

	public String getLiveUserName() {
		return liveUserName;
	}

	public void setLiveUserName(String liveUserName) {
		this.liveUserName = liveUserName;
	}

	public String getMtime() {
		return mtime;
	}

	public void setMtime(String mtime) {
		this.mtime = mtime;
	}

	public String getMtimeStr() {
		return mtimeStr;
	}

	public void setMtimeStr(String mtimeStr) {
		this.mtimeStr = mtimeStr;
	}

	public String getOnlineCount() {
		return onlineCount;
	}

	public void setOnlineCount(String onlineCount) {
		this.onlineCount = onlineCount;
	}

	public String getStime() {
		return stime;
	}

	public void setStime(String stime) {
		this.stime = stime;
	}

	public String getStimeStr() {
		return stimeStr;
	}

	public void setStimeStr(String stimeStr) {
		this.stimeStr = stimeStr;
	}
}
