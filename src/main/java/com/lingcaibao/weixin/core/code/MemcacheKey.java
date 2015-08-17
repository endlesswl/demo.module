package com.lingcaibao.weixin.core.code;
/**
 * @Title: MemcacheKey.java
 * @Description: 统一定义Memcache存储的各种对象的Key前缀和超时时间
 * @author kelylmall
 * @email ming.li@lingcaibao.com
 * @date 2014年8月10日 下午1:48:12
 * @version V1.0
 */
public enum MemcacheKey {
	TEST_KEY("test_key:", 60*60*60);
	
	private String key;//key
	private int expireTime;//过期时间
	
	private MemcacheKey(String key, int expireTime) {
		this.key = key;
		this.expireTime = expireTime;
	}

	public String getKey() {
		return key;
	}

	public int getExpireTime() {
		return expireTime;
	}

}
