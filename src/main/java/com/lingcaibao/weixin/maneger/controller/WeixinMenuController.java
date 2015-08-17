package com.lingcaibao.weixin.maneger.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Maps;
import com.lingcaibao.weixin.account.service.WeixinAccountService;
import com.lingcaibao.weixin.core.entity.Result;
import com.lingcaibao.weixin.core.util.DateEditor;
import com.lingcaibao.weixin.maneger.entity.WeixinAutoresponse;
import com.lingcaibao.weixin.maneger.entity.WeixinMenu;
import com.lingcaibao.weixin.maneger.entity.WeixinNewsitem;
import com.lingcaibao.weixin.maneger.service.WeixinAutoresponseService;
import com.lingcaibao.weixin.maneger.service.WeixinMenuService;
import com.lingcaibao.weixin.maneger.service.WeixinNewsitemService;

/**
 * @Title:
 * @Description:
 * @Author jhe
 * @Date 2013-12-8
 * @Version V1.0
 * @Copyright © 2013 掌信彩通信息科技(中国)有限公司. All rights reserved.
 */
@Controller
@RequestMapping(value = "/admin/weixinMenu")
public class WeixinMenuController {
	private static Logger logger = LoggerFactory.getLogger(WeixinMenuController.class);
	@Autowired
	private WeixinMenuService weixinMenuService;
	@Autowired
	private WeixinAccountService weixinAccountService;
	@Autowired
	private WeixinNewsitemService weixinNewsitemService;
	@Autowired
	private WeixinAutoresponseService weixinAutoresponseService;


	@RequestMapping(value = "{accountId}",method = RequestMethod.GET)
	public String list(ServletRequest request,@PathVariable("accountId") String accountId, Model model) {
		model.addAttribute("accountId",accountId);
		model.addAttribute("weixinAccount", weixinAccountService.get(accountId));
		Map<String, Object> searchParams = Maps.newHashMap();
		searchParams.put("accountId", accountId);
		searchParams.put("prentsId", "0");
		List<WeixinMenu> weixinMenus = weixinMenuService.search(searchParams);
		model.addAttribute("weixinMenus",weixinMenus);
		
		Map searchParas = Maps.newHashMap();
		searchParas.put("accountId", accountId);
		searchParas.put("flag", "启用");
		searchParas.put("sortColumns", "create_time desc");
		List<WeixinAutoresponse> wiAutoresponses = weixinAutoresponseService.search(searchParas);
		model.addAttribute("newsitemList", wiAutoresponses);
		
		return "weixinMenu/weixinMenuManeger";
	}

	

	@RequestMapping(value = "{accountId}/save", method = RequestMethod.POST)
	public String create(@Valid WeixinMenu newWeixinMenu,@PathVariable("accountId") String accountId,
			RedirectAttributes redirectAttributes) {
		if (newWeixinMenu.getId()!=null&&newWeixinMenu.getId().equals("0")) {
			weixinMenuService.insert(newWeixinMenu);
		}else {
			weixinMenuService.update(newWeixinMenu);
		}
		return "redirect:/admin/weixinMenu/"+accountId;
	}

	@RequestMapping(value = "{accountId}/delete/{id}")
	public String delete(@PathVariable("id") String id,@PathVariable("accountId") String accountId,
			RedirectAttributes redirectAttributes) {
		weixinMenuService.delete(id);
		redirectAttributes.addFlashAttribute("message","删除成功");
		return "redirect:/admin/weixinMenu/"+accountId;
	}
	
	@RequestMapping(value = "{accountId}/addSortNo/{id}")
	public String addSortNo(@PathVariable("id") String id,@PathVariable("accountId") String accountId,
			RedirectAttributes redirectAttributes) {
		boolean b=weixinMenuService.addSortNo(id);
		redirectAttributes.addFlashAttribute("message","上移成功");
		return "redirect:/admin/weixinMenu/"+accountId;
	}

	@ResponseBody
	@RequestMapping(value = "publishMenuToWeixin/{id}")
	public Result publishMenuToWeixin(@PathVariable("id") String id) {
		try {
			Result result = weixinMenuService.publishMenuToWeixin(id);
			return result;
		} catch (Exception e) {
			return new Result(false, "发布失败，请重新发布或联系管理员", null);
		}
		
	}
	
	@ResponseBody
	@RequestMapping(value = "cancelWeixinMenu/{id}")
	public Result cancelWeixinMenu(@PathVariable("id") String id) {
		Result deleteMenu = weixinMenuService.cancelWeixinMenu(id);
		return deleteMenu;
	}
	
	/**
	 * 时间格式字段预处理
	 * 
	 * @param binder
	 * @throws Exception
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) throws Exception {
		// 对于需要转换为Date类型的属性，使用DateEditor进行处理
		binder.registerCustomEditor(Date.class, new DateEditor());
	}

	
}
