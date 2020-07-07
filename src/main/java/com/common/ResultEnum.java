package com.common;

public enum ResultEnum {
	UNKONW_ERROR(-1, "未知错误"), 
	ERROR(0, "失败"), 
	SUCCESS(1, "成功"), 
	BAD_REQUEST(2, "请求无效"), 
	NOT_FOUND(3, "无法找到文件"), 
	UNAUTHORIZED(4, "未授权：登录失败"), 
	FORBIDDEN(5, "禁止访问"), 
	INTERNAL_SERVER_ERROR(6, "内部服务器错误"), 
	OBJECT_TRANS_ERROR(7, "实体类转Map对象异常"),
	CODE_ERROR_OTHER(999999, "系统未知异常");
	ResultEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	private Integer code;
	private String msg;

	public Integer getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
}
