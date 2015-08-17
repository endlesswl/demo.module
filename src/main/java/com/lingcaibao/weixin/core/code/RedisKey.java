package com.lingcaibao.weixin.core.code;

/**
 * @Title: MemcacheKey.java
 * @Description: 统一定义Redis存储的各种对象的Key前缀和超时时间
 * @author kelylmall
 * @email ming.li@lingcaibao.com
 * @date 2014年8月10日 下午1:48:12
 * @version V1.0
 */
public enum RedisKey {
	TEST_KEY("test_key:", 60 * 60 * 60), 
	C_A_PACKAGE("c_a:", 60 * 60 * 60),
	//渠道活动列表中活动
	CHANNEL_ACTION_GAME("channel_action_game:", 60 * 60 * 60);

	private String prefix;// key
	private int expireTime;// 过期时间

	private RedisKey(String prefix, int expireTime) {
		this.prefix = prefix;
		this.expireTime = expireTime;
	}

	public String getPrefix() {
		return prefix;
	}

	public int getExpireTime() {
		return expireTime;
	}

}
