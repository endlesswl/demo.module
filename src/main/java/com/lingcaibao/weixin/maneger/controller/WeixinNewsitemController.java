package com.lingcaibao.weixin.maneger.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
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

import com.google.common.collect.Maps;
import com.lingcaibao.weixin.account.service.WeixinAccountService;
import com.lingcaibao.weixin.core.entity.DataTablesPage;
import com.lingcaibao.weixin.core.entity.Result;
import com.lingcaibao.weixin.core.exceptions.ExceptionCode;
import com.lingcaibao.weixin.core.exceptions.ImportException;
import com.lingcaibao.weixin.core.util.DateEditor;
import com.lingcaibao.weixin.core.util.FileuploadUtil;
import com.lingcaibao.weixin.core.util.Servlets;
import com.lingcaibao.weixin.maneger.entity.WeixinAutoresponse;
import com.lingcaibao.weixin.maneger.entity.WeixinNewsitem;
import com.lingcaibao.weixin.maneger.service.WeixinAutoresponseService;
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
@RequestMapping(value = "/admin/weixinNewsitem")
public class WeixinNewsitemController {
	private static Logger logger = LoggerFactory.getLogger(WeixinNewsitemController.class);
	@Autowired
	private WeixinNewsitemService weixinNewsitemService;
	@Autowired
	private WeixinAccountService weixinAccountService;
	@Autowired
	private WeixinAutoresponseService weixinAutoresponseService;

	@RequestMapping(value = "/{accountId}",method = RequestMethod.GET)
	public String list(@PathVariable("accountId") String accountId, Model model) {
		model.addAttribute("weixinAccount", weixinAccountService.get(accountId));
		return "weixinNewsitem/weixinNewsitemList";
	}

	@ResponseBody
	@RequestMapping(value = "/{accountId}/list")
	public DataTablesPage listByJson(@PathVariable("accountId") String accountId,@RequestParam("sEcho") Integer sEcho,
			@RequestParam("iDisplayLength") Integer pageSize,
			@RequestParam("iDisplayStart") Integer firstIndex,
			ServletRequest request) throws ParseException {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, "s_");
		searchParams.put("accountId", accountId);
		searchParams.put("sortColumns", "create_time desc");
		Pageable pageable = new PageRequest(firstIndex / pageSize, pageSize);
		Page<WeixinNewsitem> page  = weixinNewsitemService.searchPage(searchParams, pageable);
		DataTablesPage dataTablesPage = new DataTablesPage(sEcho, page);
		return dataTablesPage;
	}

	@RequestMapping(value = "/{accountId}/create", method = RequestMethod.GET)
	public String createForm(@PathVariable("accountId") String accountId,Model model) {
		model.addAttribute("weixinNewsitem", new WeixinNewsitem());
		model.addAttribute("weixinAccount", weixinAccountService.get(accountId));
		model.addAttribute("action", "create");
		return "weixinNewsitem/weixinNewsitemForm";
	}

	@RequestMapping(value = "/{accountId}/create", method = RequestMethod.POST)
	public String create(@PathVariable("accountId") String accountId,@Valid WeixinNewsitem newWeixinNewsitem,
			RedirectAttributes redirectAttributes) {
		weixinNewsitemService.insert(newWeixinNewsitem);
		redirectAttributes.addFlashAttribute("message", "创建成功");
		return "redirect:/admin/weixinNewsitem/"+accountId;
	}

	@RequestMapping(value = "/{accountId}/update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("accountId") String accountId,@PathVariable("id") String id, Model model) {
		model.addAttribute("weixinAccount", weixinAccountService.get(accountId));
		model.addAttribute("weixinNewsitem", weixinNewsitemService.get(id));
		model.addAttribute("action", "update");
		return "weixinNewsitem/weixinNewsitemForm";
	}

	@RequestMapping(value = "/{accountId}/update", method = RequestMethod.POST)
	public String update(@PathVariable("accountId") String accountId,@Valid @ModelAttribute("weixinNewsitem") WeixinNewsitem weixinNewsitem,
			RedirectAttributes redirectAttributes) {
		weixinNewsitemService.update(weixinNewsitem);
		redirectAttributes.addFlashAttribute("message", "更新成功");
		return "redirect:/admin/weixinNewsitem/"+accountId;
	}
	
	
	/**
	 * 上传接口
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/upload")
	@ResponseBody
	public String uploadFile(HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		String fileJson = FileuploadUtil.uploadFile(request);
		return fileJson;
	}
	
	/**
	 * 上传
	 * @param model
	 * @param customerId
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/upload/{accountId}", method = RequestMethod.GET)
    public String testJsp(Model model,@PathVariable("accountId") String accountId,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		model.addAttribute("accountId", accountId);
		model.addAttribute("weixinAccount", weixinAccountService.get(accountId));
		return "weixinNewsitem/weixinNewsitemUpload";
    
	}
	
	/**
	 * 处理接口
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/upload/{accountId}", method = RequestMethod.POST)
	@ResponseBody
	public Result uploadFile(HttpServletRequest request,
			@PathVariable("accountId") String accountId,
			RedirectAttributes redirectAttributes) {
		String fileJson = request.getParameter("filePath");
		Result apiResult =null;
		try {
			apiResult = weixinNewsitemService.uploadFileAndInsertDb(fileJson,accountId);
		} catch (ImportException e) {
			return new Result(false, e.getMessage() );
		}
		return apiResult;
	}
	
	@ResponseBody
	@RequestMapping(value = "delete/{id}")
	public Result delete(@PathVariable("id") String id,
			RedirectAttributes redirectAttributes) {
		Map map = Maps.newHashMap();
		map.put("messageId", id);
		List<WeixinAutoresponse> weixinAutoresponses = weixinAutoresponseService.search(map);
		
		if (weixinAutoresponses != null && weixinAutoresponses.size() != 0) {
			return new Result(false, "删除失败，该消息已经被绑定！", null);
		} else {
			weixinNewsitemService.delete(id);
			redirectAttributes.addFlashAttribute("message", "删除成功");
			return new Result(true);
		}
	}

	
	/**
	 * 时间格式字段预处理
	 * @param binder
	 * @throws Exception
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) throws Exception {
		// 对于需要转换为Date类型的属性，使用DateEditor进行处理
		binder.registerCustomEditor(Date.class, new DateEditor());
	}

}
