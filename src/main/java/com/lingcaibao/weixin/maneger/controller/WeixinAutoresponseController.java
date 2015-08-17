package com.lingcaibao.weixin.maneger.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import net.spy.memcached.MemcachedClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Maps;
import com.lingcaibao.weixin.account.service.WeixinAccountService;
import com.lingcaibao.weixin.core.entity.DataTablesPage;
import com.lingcaibao.weixin.core.entity.Result;
import com.lingcaibao.weixin.core.util.DateEditor;
import com.lingcaibao.weixin.core.util.Servlets;
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
@RequestMapping(value = "/admin/weixinAutoresponse")
public class WeixinAutoresponseController {
	private static Logger logger = LoggerFactory.getLogger(WeixinAutoresponseController.class);
	@Autowired
	private WeixinAutoresponseService weixinAutoresponseService;
	@Autowired
	private WeixinAccountService weixinAccountService;
	@Autowired
	private WeixinNewsitemService weixinNewsitemService;
	@Autowired
	private WeixinMenuService weixinMenuService;
	@Autowired
	private MemcachedClient memcachedClient;

	@RequestMapping(value = "/{accountId}",method = RequestMethod.GET)
	public String list(
			ServletRequest request,@PathVariable("accountId") String accountId,Model model) {
		model.addAttribute("accountId",accountId);
		model.addAttribute("weixinAccount", weixinAccountService.get(accountId));
		return "weixinAutoresponse/weixinAutoresponseList";
	}

	@ResponseBody
	@RequestMapping(value = "/{accountId}/list")
	public DataTablesPage listByJson(@PathVariable("accountId") String accountId,
			@RequestParam("sEcho") Integer sEcho,
			@RequestParam("iDisplayLength") Integer pageSize,
			@RequestParam("iDisplayStart") Integer firstIndex,
			ServletRequest request) throws ParseException {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, "s_");
		searchParams.put("accountId", accountId);
//		searchParams.put("flag", "启用");
		searchParams.put("sortColumns", "create_time desc");
		Pageable pageable = new PageRequest(firstIndex / pageSize, pageSize);
		Page<WeixinAutoresponse> page  = weixinAutoresponseService.searchPage(searchParams, pageable);
		
		List<WeixinAutoresponse> autoresponses = page.getContent();
		for (WeixinAutoresponse weixinAutoresponse : autoresponses) {
			if (weixinAutoresponse.getMsgtype().equals("图文消息")) {
				logger.debug("weixinAutoresponse name:{} messageId:{}"+weixinAutoresponse.getMessageId());
				if (weixinAutoresponse.getMessageId().contains(",")) {
					weixinAutoresponse.setWeixinNewsitems(weixinNewsitemService.searchByIds("'"+weixinAutoresponse.getMessageId().replace(",", "','")+"'"));
				}else {
					weixinAutoresponse.setWeixinNewsitems(weixinNewsitemService.searchByIds("'"+weixinAutoresponse.getMessageId()+"'"));
				}
			}
		}
		DataTablesPage dataTablesPage = new DataTablesPage(sEcho, page);
		return dataTablesPage;
	}

	@RequestMapping(value = "/{accountId}/create", method = RequestMethod.GET)
	public String createForm(@PathVariable("accountId") String accountId,Model model) {
		model.addAttribute("weixinAutoresponse", new WeixinAutoresponse());
		model.addAttribute("action", "create");
		model.addAttribute("accountId",accountId);
		model.addAttribute("weixinAccount", weixinAccountService.get(accountId));
		Map searchParas = Maps.newHashMap();
		searchParas.put("accountId", accountId);
		searchParas.put("flag", "启用");
		searchParas.put("sortColumns", "create_time desc");
		List<WeixinNewsitem> newsitems = weixinNewsitemService.search(searchParas);
		model.addAttribute("newsitemList", newsitems);
		return "weixinAutoresponse/weixinAutoresponseForm";
	}

	@RequestMapping(value = "/{accountId}/create", method = RequestMethod.POST)
	public String create(WeixinAutoresponse newWeixinAutoresponse,
			RedirectAttributes redirectAttributes) {
		weixinAutoresponseService.insert(newWeixinAutoresponse);
		redirectAttributes.addFlashAttribute("message", "创建成功");
		return "redirect:/admin/weixinAutoresponse/"+newWeixinAutoresponse.getAccountId();
	}

	@RequestMapping(value = "{accountId}/update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("accountId") String accountId,@PathVariable("id") String id, Model model) {
		model.addAttribute("weixinAccount", weixinAccountService.get(accountId));
		model.addAttribute("weixinAutoresponse", weixinAutoresponseService.get(id));
		model.addAttribute("action", "update");
		Map searchParas = Maps.newHashMap();
		searchParas.put("accountId", accountId);
		searchParas.put("flag", "启用");
		searchParas.put("sortColumns", "create_time desc");
		List<WeixinNewsitem> newsitems = weixinNewsitemService.search(searchParas);
		model.addAttribute("newsitemList", newsitems);
		return "weixinAutoresponse/weixinAutoresponseForm";
	}

	@RequestMapping(value = "{accountId}/update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("weixinAutoresponse") WeixinAutoresponse weixinAutoresponse,
			RedirectAttributes redirectAttributes) {
		logger.debug("weixinAutoresponse update messageId:{}"+weixinAutoresponse.getMessageId());
		weixinAutoresponseService.update(weixinAutoresponse);
		redirectAttributes.addFlashAttribute("message", "更新成功");
		return "redirect:/admin/weixinAutoresponse/"+weixinAutoresponse.getAccountId();
	}

	@ResponseBody
	@RequestMapping(value = "delete/{id}")
	public Result delete(@PathVariable("id") String id,
			RedirectAttributes redirectAttributes) {
		WeixinAutoresponse weixinAutoresponse = weixinAutoresponseService.get(id);
		if (weixinAutoresponse==null) {
			return new Result(false,"操作失败，该消息不存在！",null);
		}
		HashMap<String, Object> searchParas= Maps.newHashMap();
		searchParas.put("key", id);
		searchParas.put("accountId", weixinAutoresponse.getAccountId());
		searchParas.put("type", "click");
		List<WeixinMenu> weixinMenus = weixinMenuService.search(searchParas);
		if (weixinMenus!=null&&weixinMenus.size()!=0) {
			return new Result(false,"操作失败，该消息已经被菜单绑定，请解绑后操作！",null);
		}
		
		weixinAutoresponseService.delete(id);
		return new Result(true);
	}
	
	/**
	 * 发布单条自动回复消息
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "publishAutorespondMessege/{id}")
	public Result publishAutorespondMessege(@PathVariable("id") String id,
			RedirectAttributes redirectAttributes) {
		try {
			WeixinAutoresponse weixinAutoresponse = weixinAutoresponseService.get(id);
			if (weixinAutoresponse!=null) {
				if (weixinAutoresponse.getFlag().equals("停用")) {
					weixinAutoresponse.setFlag("启用");
					weixinAutoresponseService.update(weixinAutoresponse);
				}
				weixinAutoresponseService.publishAutoResponseMessege(weixinAutoresponse,1);
				return new Result(true);
			}else {
				return new Result(false,"发布失败，请重新发布或联系管理员！",weixinAutoresponse);
			}
		} catch (Exception e) {
			return new Result(false,"发布失败，请重新发布或联系管理员！",null);
		}
		
	}
	
	/**
	 * 发布所有自动回复消息
	 * @param accountId
	 * @param redirectAttributes
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "initMessage/{accountId}")
	public Result initMessage(@PathVariable("accountId") String accountId,
			RedirectAttributes redirectAttributes) {
		try {
			Map<String, Object> searchParas = new HashMap<String, Object>();
			searchParas.put("accountId", accountId);
			searchParas.put("flag", "启用");
			List<WeixinAutoresponse> autoresponses = weixinAutoresponseService.search(searchParas);
			for (WeixinAutoresponse weixinAutoresponse : autoresponses) {
				weixinAutoresponseService.publishAutoResponseMessege(weixinAutoresponse,1);
			}
			return new Result(true);
		} catch (Exception e) {
			return new Result(false,"发布失败，请重新发布或联系管理员！",null);
		}
		
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
