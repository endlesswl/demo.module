package com.lingcaibao.weixin.weixinuser.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lingcaibao.weixin.core.util.DateProvider;
import com.lingcaibao.weixin.core.util.UUIDUtils;
import com.lingcaibao.weixin.weixinuser.entity.WeixinUserinfo;
import com.lingcaibao.weixin.weixinuser.repository.WeixinUserinfoDao;

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
public class WeixinUserinfoService {

	private static Logger logger = LoggerFactory
			.getLogger(WeixinUserinfoService.class);

	@Autowired
	private WeixinUserinfoDao weixinUserinfoDao;

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
	public Page<WeixinUserinfo> searchPage(Map<String, Object> searchParams,
			Pageable pageable) {
		return weixinUserinfoDao.searchPage(searchParams, pageable);
	}

	/**
	 * 不分页查询
	 * 
	 * @param searchParas
	 *            查询条件
	 * @return
	 */
	public List<WeixinUserinfo> search(Map<String, Object> searchParas) {
		return weixinUserinfoDao.search(searchParas);
	}

	
	public WeixinUserinfo get(String id) {
		return weixinUserinfoDao.get(id);
	}

	public void insert(WeixinUserinfo weixinUserinfo) {
		weixinUserinfo.setId(UUIDUtils.getShortUUID());
		weixinUserinfo.setCreateTime(dateProvider.getDate());
		weixinUserinfoDao.insert(weixinUserinfo);
	}
	
	public void update(WeixinUserinfo weixinUserinfo) {
		weixinUserinfoDao.update(weixinUserinfo);
	}

	public void delete(String id) {
		weixinUserinfoDao.delete(id);
	}

	public void setDateProvider(DateProvider dateProvider) {
		this.dateProvider = dateProvider;
	}
}
