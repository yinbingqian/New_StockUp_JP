package com.sxit.entity.living;

import java.io.Serializable;

public class Attention implements Serializable {
	private String Message;
	private String Result;
	
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public String getResult() {
		return Result;
	}
	public void setResult(String result) {
		Result = result;
	}
	
}
