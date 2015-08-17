package com.lingcaibao.weixin.user.controller;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.lingcaibao.weixin.core.task.AccessTokenService;
/**
 * 
 * @Title: RootController.java
 * @Description:(用一句话描述该文件做什么)
 * @author kelylmall
 * @email ming.li@lingcaibao.com
 * @date 2014年8月19日 上午9:45:40
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/admin")
public class RootController {

	private static Logger logger = LoggerFactory
			.getLogger(RootController.class);
	
	@Autowired
	private AccessTokenService accessTokenService;
	
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(RedirectAttributes redirectAttributes) {
		logger.info("logout");
		SecurityUtils.getSubject().logout();
		redirectAttributes.addFlashAttribute("message", "您已安全退出");
		return "redirect:/login";
	}
	
	@ResponseBody
	@RequestMapping(value = "refreshAllAccessToken", method = RequestMethod.GET)
	public String refreshAllAccessToken(RedirectAttributes redirectAttributes) {
		Map< String, String> accMap=accessTokenService.refreshAllAccessToken();
		return JSON.toJSONString(accMap);
	}

}
