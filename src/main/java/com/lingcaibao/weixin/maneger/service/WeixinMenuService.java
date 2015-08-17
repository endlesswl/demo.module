package com.lingcaibao.weixin.maneger.service;

import java.util.List;
import java.util.Map;

import org.nutz.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import weixin.lingcaibao.api.MenuAPI;
import weixin.lingcaibao.api.TokenAPI;
import weixin.lingcaibao.bean.BaseResult;
import weixin.lingcaibao.bean.MenuButtons;
import weixin.lingcaibao.bean.MenuButtons.Button;

import com.google.common.collect.Maps;
import com.lingcaibao.weixin.account.entity.WeixinAccount;
import com.lingcaibao.weixin.account.repository.WeixinAccountDao;
import com.lingcaibao.weixin.core.entity.Result;
import com.lingcaibao.weixin.core.service.RedisService;
import com.lingcaibao.weixin.core.util.DateProvider;
import com.lingcaibao.weixin.core.util.UUIDUtils;
import com.lingcaibao.weixin.maneger.entity.WeixinMenu;
import com.lingcaibao.weixin.maneger.repository.WeixinMenuDao;

/**
* @Title: 
* @Description: 
* @Author jhe   
* @Date 2013 - 2014
* @Version V1.0
* @Copyright © 2013 掌信彩通信息科技(中国)有限公司. All rights reserved.
*/
// Spring Service Bean的标识.
@Service
@Transactional
public class WeixinMenuService {

	private static Logger logger = LoggerFactory
			.getLogger(WeixinMenuService.class);

	@Autowired
	private WeixinMenuDao weixinMenuDao;
	@Autowired
	private WeixinAccountDao weixinAccountDao;
	@Autowired
	private MenuAPI menuApi;
	@Autowired
	private TokenAPI tokenAPI;
	@Autowired
	private RedisService redisService;
	@Autowired
	private WeixinAutoresponseService weixinAutoresponseService;
	private DateProvider dateProvider = DateProvider.DEFAULT;

	/**
	 * 分页查询
	 * 
	 * @param searchParams
	 *            查询条件
	 * @param pageable
	 *            分页参数
	 * @return
	 */
	public Page<WeixinMenu> searchPage(Map<String, Object> searchParams,
			Pageable pageable) {
		return weixinMenuDao.searchPage(searchParams, pageable);
	}

	/**
	 * 不分页查询
	 * 
	 * @param searchParas
	 *            查询条件
	 * @return
	 */
	public List<WeixinMenu> search(Map<String, Object> searchParas) {
		return weixinMenuDao.search(searchParas);
	}

	
	public WeixinMenu get(String id) {
		return weixinMenuDao.get(id);
	}

	public void insert(WeixinMenu weixinMenu) {
		weixinMenu.setId(UUIDUtils.getShortUUID());
		weixinMenu.setCreateTime(dateProvider.getDate());
		if (!weixinMenu.getPrentsId().equals("0") && weixinMenu.getOrders()==1) {
			WeixinMenu weixinMenu2 = this.get(weixinMenu.getPrentsId());
			if (weixinMenu2!=null) {
				weixinMenu2.setKey("");
				weixinMenu2.setType("");
				weixinMenu2.setUrl("");
				this.update(weixinMenu2);
			}
		}
		weixinMenuDao.insert(weixinMenu);
	}
	
	public void update(WeixinMenu weixinMenu) {
		weixinMenu.setUpdateTime(dateProvider.getDate());
		weixinMenuDao.update(weixinMenu);
	}

	public void delete(String id) {
		weixinMenuDao.delete(id);
	}

	public void setDateProvider(DateProvider dateProvider) {
		this.dateProvider = dateProvider;
	}

	public Result defaultMenu(String id) {
		WeixinMenu weixinMenu = new WeixinMenu();
		weixinMenu.setAccountId(id);
		weixinMenu.setPrentsId("0");
		for (int i = 1; i < 4; i++) {
			weixinMenu.setName("目录_"+i);
			weixinMenu.setOrders(i);
//			weixinMenu.setType("view");
			this.insert(weixinMenu);
		}
		
		return new Result(true);
		
	}

	public boolean addSortNo(String id) {
		WeixinMenu weixinMenu = this.get(id);
		Map<String, Object> searchParams = Maps.newConcurrentMap();
		searchParams.put("accountId", weixinMenu.getAccountId());
		searchParams.put("prentsId", weixinMenu.getPrentsId());
		List<WeixinMenu> list = this.search(searchParams);
		int i=1;
		int sortNo=weixinMenu.getOrders();
		for (WeixinMenu weixinMenu2 : list) {
			if (i==sortNo-1) {
				weixinMenu2.setOrders(sortNo);
			} else if (i==sortNo) {
				weixinMenu2.setOrders(sortNo-1);
			}else {
				if (weixinMenu2.getOrders()!=null&&i==weixinMenu2.getOrders().intValue()) {
					i++;
					continue;
				}
				weixinMenu2.setOrders(i);
			}
			this.update(weixinMenu2);
			i++;
		}
		return true;
	}

	public Result publishMenuToWeixin(String id) {
		WeixinAccount weixinAccount = weixinAccountDao.get(id);
		if (weixinAccount==null) {
			return new Result(false, "微信账号不存在！！！", null);
		}
		Map<String, Object> searchParams = Maps.newHashMap();
		searchParams.put("accountId", id);
		searchParams.put("prentsId", "0");
		List<WeixinMenu> weixinMenus =this.search(searchParams);
		MenuButtons menuButtons = new MenuButtons();
		Button[] buttons =new Button[3];
		int i=0;
		for (WeixinMenu weixinMenu : weixinMenus) {
			buttons[i]=weixinMenu.toMenuButton();
			i++;
		}
		menuButtons.setButton(buttons);
		//redisService.set(weixinAccount.getAccountAppid()+"_access_token", access_token);
//		Token token = tokenAPI.token(weixinAccount.getAccountAppid(), weixinAccount.getAccountAppsecret());
//		logger.info("token:{}",Json.toJson(token));
//		if (token==null||StringUtils.isBlank(token.getAccess_token())) {
//			return new Result(false, "菜单创建失败，帐号appid或secret不匹配，请确认帐号中的appid和secret设置正确。", Json.toJson(token));
//		}
		String access_token=redisService.get(weixinAccount.getAccountAppid()+"_access_token");
		BaseResult baseResult =  menuApi.menuCreate(access_token, menuButtons);
		Result result;
		if (baseResult!=null&&baseResult.getErrcode().equals("0")) {
			weixinAutoresponseService.publishMenuResponse(weixinMenus);
			result = new Result(true, "菜单创建成功！", null);
		}else {
			result = new Result(false, "菜单创建失败，请重新发布或联系管理员。", null);
		}
		return result;
	}
	
	public Result cancelWeixinMenu(String id){
		
		BaseResult baseResult=menuApi.menuDelete("SdBiyYpoLYix4VW9vQJaXJNJQkk7wyAchP5eApKBWUVGYDYZ9jyMjC8A-VMADntVix6mwtNhBDxmIf38SKQ_Tgx9WKyvbSkV6vtWoas1zhs");
		System.out.println(Json.toJson(baseResult));
		return new Result();
	}
}
