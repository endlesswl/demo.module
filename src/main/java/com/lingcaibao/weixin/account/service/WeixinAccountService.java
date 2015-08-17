package com.lingcaibao.weixin.account.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lingcaibao.weixin.account.entity.WeixinAccount;
import com.lingcaibao.weixin.account.repository.WeixinAccountDao;
import com.lingcaibao.weixin.core.util.DateProvider;
import com.lingcaibao.weixin.core.util.UUIDUtils;
import com.lingcaibao.weixin.maneger.service.WeixinAutoresponseService;
import com.lingcaibao.weixin.maneger.service.WeixinMenuService;



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
public class WeixinAccountService {

	private static Logger logger = LoggerFactory.getLogger(WeixinAccountService.class);

	@Autowired
	private WeixinAccountDao weixinAccountDao;
	
	@Autowired
	private WeixinAutoresponseService weixinAutoresponseService;
	@Autowired
	private WeixinMenuService weixinMenuService;

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
	public Page<WeixinAccount> searchPage(Map<String, Object> searchParams,
			Pageable pageable) {
		return weixinAccountDao.searchPage(searchParams, pageable);
	}

	/**
	 * 不分页查询
	 * 
	 * @param searchParas
	 *            查询条件
	 * @return
	 */
	public List<WeixinAccount> search(Map<String, Object> searchParas) {
		return weixinAccountDao.search(searchParas);
	}

	
	public WeixinAccount get(String id) {
		return weixinAccountDao.get(id);
	}

	public void insert(WeixinAccount weixinAccount) {
		weixinAccount.setId(UUIDUtils.getShortUUID());
		weixinAutoresponseService.defaultMessege(weixinAccount.getId());
		weixinMenuService.defaultMenu(weixinAccount.getId());
		weixinAccount.setCreateTime(dateProvider.getDate());
		weixinAccountDao.insert(weixinAccount);
	}
	
	public void update(WeixinAccount weixinAccount) {
		weixinAccount.setUpdateTime(dateProvider.getDate());
		weixinAccountDao.update(weixinAccount);
	}

	public void delete(String id) {
		weixinAccountDao.delete(id);
	}

	public void setDateProvider(DateProvider dateProvider) {
		this.dateProvider = dateProvider;
	}
	
}
