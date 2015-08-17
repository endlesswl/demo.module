package com.lingcaibao.weixin.core.code;
/**
 * 
 * @Title: CommonStatus.java
 * @Description: 统一定义公共中存储的各种对象的Key前缀和超时时间
 * @author kelylmall
 * @email ming.li@lingcaibao.com
 * @date 2014年8月10日 下午1:48:12
 * @version V1.0
 */
public enum CommonStatus {
	KAPTCHA_SESSION_KEY("kaptcha_session_key", "验证码key", 60 * 60);
	
	private String key;//key
	private String msg;//key描述
	private int expireTime;//过期时间
	private CommonStatus(String key, String msg, int expireTime) {
		this.key = key;
		this.msg = msg;
		this.expireTime = expireTime;
	}

	public String getKey() {
		return key;
	}

	public String getMsg() {
		return msg;
	}

	public int getExpireTime() {
		return expireTime;
	}

}
