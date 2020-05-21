package com;

public class ErrorMessage {
	private Integer code;
	private String message;
	
	public ErrorMessage(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public Integer getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
}
