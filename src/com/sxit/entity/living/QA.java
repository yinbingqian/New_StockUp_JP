package com.sxit.entity.living;

import java.io.Serializable;

public class QA implements Serializable {
	private String Answer;
	private String AnswpicId;
	private String AnswpicImg1;
	private String AnswpicImg2;
	private String AnswpicImgthumbnail1;
	private String AnswpicImgthumbnail2;
	private String Crtime;
	private String Id;
	private String QuContent;
	private String QuUserName;
	private String Title;
	
	public String getAnswer() {
		return Answer;
	}
	public void setAnswer(String answer) {
		Answer = answer;
	}
	
	public String getAnswpicId() {
		return AnswpicId;
	}
	public void setAnswpicId(String answpicId) {
		AnswpicId = answpicId;
	}
	public String getAnswpicImg1() {
		return AnswpicImg1;
	}
	public void setAnswpicImg1(String answpicImg1) {
		AnswpicImg1 = answpicImg1;
	}
	public String getAnswpicImg2() {
		return AnswpicImg2;
	}
	public void setAnswpicImg2(String answpicImg2) {
		AnswpicImg2 = answpicImg2;
	}
	public String getAnswpicImgthumbnail1() {
		return AnswpicImgthumbnail1;
	}
	public void setAnswpicImgthumbnail1(String answpicImgthumbnail1) {
		AnswpicImgthumbnail1 = answpicImgthumbnail1;
	}
	public String getAnswpicImgthumbnail2() {
		return AnswpicImgthumbnail2;
	}
	public void setAnswpicImgthumbnail2(String answpicImgthumbnail2) {
		AnswpicImgthumbnail2 = answpicImgthumbnail2;
	}
	public String getCrtime() {
		return Crtime;
	}
	public void setCrtime(String crtime) {
		Crtime = crtime;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getQuContent() {
		return QuContent;
	}
	public void setQuContent(String quContent) {
		QuContent = quContent;
	}
	public String getQuUserName() {
		return QuUserName;
	}
	public void setQuUserName(String quUserName) {
		QuUserName = quUserName;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
    	
}
