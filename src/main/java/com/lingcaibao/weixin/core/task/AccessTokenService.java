package com.lingcaibao.weixin.core.task;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.nutz.http.Http;
import org.nutz.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import weixin.lingcaibao.api.TokenAPI;
import weixin.lingcaibao.bean.Token;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.lingcaibao.weixin.account.entity.WeixinAccount;
import com.lingcaibao.weixin.account.service.WeixinAccountService;
import com.lingcaibao.weixin.core.service.RedisServiceImpl;

@Service
public class AccessTokenService {

	private static Logger logger = LoggerFactory
			.getLogger(AccessTokenService.class);
	
	public static final String JS_API_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	@Autowired
	private TokenAPI tokenAPI;
	
	@Autowired
	private WeixinAccountService weixinAccountService;
	
	@Autowired
	private RedisServiceImpl redisService;
	
	public Map<String, String> refreshAllAccessToken() {
		
		Map<String, String> returnMap = Maps.newHashMap();
		Map<String, Object> searchParas = Maps.newHashMap();
		searchParas.put("status", 1);
		List<WeixinAccount> weixinAccountList = weixinAccountService.search(searchParas);
		for (WeixinAccount weixinAccount : weixinAccountList) {
			Token token = tokenAPI.token(weixinAccount.getAccountAppid(), weixinAccount.getAccountAppsecret());
			logger.info("token:{}",Json.toJson(token));
			int i=0;
			while (true) {
				if (token==null||StringUtils.isBlank(token.getAccess_token())) {
					token = tokenAPI.token(weixinAccount.getAccountAppid(), weixinAccount.getAccountAppsecret());
				}
				i++;
				if (i>2) {
					break;
				}
			}
			String access_token=token.getAccess_token();
			
			redisService.set(weixinAccount.getAccountAppid()+"_access_token", access_token);
			String a =getJsapi_ticket(access_token,weixinAccount.getAccountAppid());
			returnMap.put(weixinAccount.getAccountName()+"_accessToken", access_token);
			returnMap.put(weixinAccount.getAccountName()+"_jsapi_token", a);
		}
		return returnMap;
	}
	
	
	public String getJsapi_ticket(String access_token,String appid)
	{
		String key =appid+ "_jsapi_ticketKey";
		String ticket = null;
		
		String url = JS_API_TICKET.replace("ACCESS_TOKEN", access_token);
		int i=0;
		while (true) {
			String content = Http.get(url).getContent();
			logger.info("getJsapi_ticket content:{}", content);
			if (!StringUtils.isEmpty(content))
			{
				JSONObject parseObject = JSON.parseObject(content);
				ticket = (String) parseObject.get("ticket");
				if (parseObject.get("errmsg").equals("ok"))
				{
					redisService.set(key, ticket);
					logger.info("key:{}  ---  jsapi_accesstoken:{}",key,ticket);
					break;
				}
			}
			i++;
			if(i>2){
				break;
			}
			
		}
		
		return ticket;
	}

	public static void main(String[] args) {
		String aa="blbCuJQguQ0MvdlCzHNyTCwusa5k15_zLdlDg0g1WEWQdNKDs7Tc7sKu0w71kJ7yn1gG8aKvZA_XBgggQlVb_omTIU4dfi9_qmsgwzatzSo";
		String url = JS_API_TICKET.replace("ACCESS_TOKEN", aa);
		String content = Http.get(url).getContent();
		logger.info("getJsapi_ticket content:{}", content);
		if (!StringUtils.isEmpty(content))
		{
			JSONObject parseObject = JSON.parseObject(content);
		
		}
	
	}
	
}
