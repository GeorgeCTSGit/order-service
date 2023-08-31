package com.packagecentre.ms.orderservice.exception;

import java.time.LocalDateTime;

public class PkgCtrErrorMsg {
	
	private String errMsg;
	private String errDtl;
	private LocalDateTime errTime;
	
	public PkgCtrErrorMsg(String errMsg, String errDtl, LocalDateTime errTime) {
		super();
		this.errMsg = errMsg;
		this.errDtl = errDtl;
		this.errTime = errTime;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public String getErrDtl() {
		return errDtl;
	}
	public void setErrDtl(String errDtl) {
		this.errDtl = errDtl;
	}
	public LocalDateTime getErrTime() {
		return errTime;
	}
	public void setErrTime(LocalDateTime errTime) {
		this.errTime = errTime;
	}
	
	

}
