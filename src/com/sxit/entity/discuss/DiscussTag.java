package com.sxit.entity.discuss;

import java.io.Serializable;

/**
 * 描述：广告信息</br>
 * @author Eden Cheng</br>
 * @version 2015年4月23日 上午11:32:53
 */
public class DiscussTag implements Serializable{
	String Id = "";
	String Orders = "";
	String Title = "";
	String Value = "";
	
	public String getId() {
		return Id;
	}
	public void setId(String Id) {
		this.Id = Id;
	}
	public String getOrders() {
		return Orders;
	}
	public void setOrders(String Orders) {
		this.Orders = Orders;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String Title) {
		this.Title = Title;
	}
	
	public String getValue() {
		return Value;
	}
	public void setValue(String Value) {
		this.Value = Value;
	}
	
}
