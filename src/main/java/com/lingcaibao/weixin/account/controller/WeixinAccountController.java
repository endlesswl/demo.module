package com.lingcaibao.weixin.account.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

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

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.lingcaibao.weixin.account.entity.WeixinAccount;
import com.lingcaibao.weixin.account.service.WeixinAccountService;
import com.lingcaibao.weixin.core.entity.DataTablesPage;
import com.lingcaibao.weixin.core.entity.Result;
import com.lingcaibao.weixin.core.util.DateEditor;
import com.lingcaibao.weixin.core.util.Servlets;
import com.lingcaibao.weixin.core.util.ShiroUtils;
import com.lingcaibao.weixin.user.security.ShiroDbRealm.ShiroUser;


/**
 * @Title:
 * @Description:
 * @Author jhe
 * @Date 2013-12-8
 * @Version V1.0
 * @Copyright © 2013 掌信彩通信息科技(中国)有限公司. All rights reserved.
 */
@Controller
@RequestMapping(value = "/admin/weixinAccount")
public class WeixinAccountController {
	private static Logger logger = LoggerFactory.getLogger(WeixinAccountController.class);
	
	@Autowired
	private WeixinAccountService weixinAccountService;

	private static final String PAGE_SIZE = "10";

	private final ObjectMapper  objectMapper = new ObjectMapper();
	// 允许排序的字段
	private static final List<String> ALLOW_SORT_COLUMNS = ImmutableList.of("id");

	@RequestMapping(method = RequestMethod.GET)
	public String list(ServletRequest request, Model model) {
		return "weixinAccount/accountList";
	}

	@ResponseBody
	@RequestMapping(value = "/list")
	public DataTablesPage listByJson(
			@RequestParam("sEcho") Integer sEcho,
			@RequestParam("iDisplayLength") Integer pageSize,
			@RequestParam("iDisplayStart") Integer firstIndex,
			ServletRequest request) throws ParseException {
		logger.debug("登录成功···············");
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, "s_");
		// new Page
		ShiroUser shiroUser = ShiroUtils.getCurrentUser();
		searchParams.put("createrId", shiroUser.getId()+"");
		Pageable pageable = new PageRequest(firstIndex / pageSize, pageSize);
		Page<WeixinAccount> page = weixinAccountService.searchPage(searchParams, pageable);
		DataTablesPage dataTablesPage = new DataTablesPage(sEcho, page);
		return dataTablesPage;
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("weixinAccount", new WeixinAccount());
		model.addAttribute("action", "create");
		return "weixinAccount/weixinAccountForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(WeixinAccount newWeixinAccount, RedirectAttributes redirectAttributes) {
		ShiroUser shiroUser = ShiroUtils.getCurrentUser();
		newWeixinAccount.setOwnerId(shiroUser.getId()+"");
		newWeixinAccount.setCreaterId(shiroUser.getId()+"");
		weixinAccountService.insert(newWeixinAccount);
		redirectAttributes.addFlashAttribute("message", "创建成功");
		return "redirect:/admin/weixinAccount";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") String id, Model model) {
		WeixinAccount weixinAccount = weixinAccountService.get(id);
		logger.info("weixinAccount:{}",JSON.toJSONString(weixinAccount));
		model.addAttribute("weixinAccount", weixinAccount);
		model.addAttribute("action", "update");
		return "weixinAccount/weixinAccountForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("weixinAccount") WeixinAccount weixinAccount,
			RedirectAttributes redirectAttributes) {
		weixinAccountService.update(weixinAccount);
		redirectAttributes.addFlashAttribute("message", "更新成功");
		return "redirect:/admin/weixinAccount";
	}

	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public Result delete(@PathVariable("id") String id,
			RedirectAttributes redirectAttributes) {
		
		weixinAccountService.delete(id);
//		redirectAttributes.addFlashAttribute("message","删除成功");
		return new Result(true);
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

	/**
	 * 构建排序SQL
	 * 
	 * 排序，默认按ID倒序，参数名称必须为数据库字段名，多个排序字段以-间隔，行如：_id-name_
	 * 
	 * @param sort
	 * @return
	 */
	public static String buildSort(String sort) {
		StringBuffer strf = new StringBuffer();
		for (String sortStr : sort.split("-")) {
			// 如果传入的字段不允许排序，则跳出
			if (!ALLOW_SORT_COLUMNS.contains(sortStr.replace("_", ""))) {
				continue;
			}
			// 去除以下划线开头结尾的参数，只能以固定格式
			if (sortStr.startsWith("_") && sortStr.endsWith("_")) {
				continue;
			}
			if (sortStr.startsWith("_")) {// 以下划线开始为升序
				strf.append(sortStr.substring(1)).append(" ASC");
			} else if (sortStr.endsWith("_")) {// 以下划线结束为降序
				strf.append(sortStr.substring(0, sortStr.length() - 1)).append(
						" DESC");
			}
		}
		return strf.toString();
	}
}
