package com.lingcaibao.weixin.core.util;

import java.util.ArrayList;
import java.util.Collections;

import org.nutz.lang.Lang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WeixinUtil {
	private static Logger logger = LoggerFactory.getLogger(WeixinUtil.class);
	/**
	 * 检查signature是否合法
	 */
	public static boolean check(String token, String signature, String timestamp, String nonce) {
		// 防范长密文攻击
		if (signature == null || signature.length() > 128 
				|| timestamp == null || timestamp.length() > 128
				|| nonce == null || nonce.length() > 128) {
			logger.warn("bad check : signature=%s,timestamp=%s,nonce=%s", signature, timestamp, nonce);
			return false;
		}
		ArrayList<String> tmp = new ArrayList<String>();
		tmp.add(token);
		tmp.add(timestamp);
		tmp.add(nonce);
		Collections.sort(tmp);
		String key = Lang.concat("", tmp).toString();
		return Lang.sha1(key).equalsIgnoreCase(signature);
	}
	
	/**
	 * sha1加密
	 */
	public static String check(ArrayList<String> tmp) {
		Collections.sort(tmp);
		String key = Lang.concat("", tmp).toString();
		return Lang.sha1(key);
	}
}
