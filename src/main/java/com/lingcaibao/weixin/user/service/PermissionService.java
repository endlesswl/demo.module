package com.lingcaibao.weixin.user.service;

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
import com.lingcaibao.weixin.user.entity.Permission;
import com.lingcaibao.weixin.user.repository.PermissionDao;

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
public class PermissionService {

	private static Logger logger = LoggerFactory
			.getLogger(PermissionService.class);

	@Autowired
	private PermissionDao permissionDao;

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
	public Page<Permission> searchPage(Map<String, Object> searchParams,
			Pageable pageable) {
		return permissionDao.searchPage(searchParams, pageable);
	}

	/**
	 * 不分页查询
	 * 
	 * @param searchParas
	 *            查询条件
	 * @return
	 */
	public List<Permission> search(Map<String, Object> searchParas) {
		return permissionDao.search(searchParas);
	}

	
	public Permission get(Long id) {
		return permissionDao.get(id);
	}

	public void insert(Permission permission) {
		permissionDao.insert(permission);
	}
	
	public void update(Permission permission) {
		permissionDao.update(permission);
	}

	public void delete(Long id) {
		permissionDao.delete(id);
	}

	public void setDateProvider(DateProvider dateProvider) {
		this.dateProvider = dateProvider;
	}
}
