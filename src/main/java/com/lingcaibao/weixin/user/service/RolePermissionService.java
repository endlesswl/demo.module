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
import com.lingcaibao.weixin.user.entity.RolePermission;
import com.lingcaibao.weixin.user.repository.RolePermissionDao;
@Service
@Transactional
public class RolePermissionService {

	private static Logger logger = LoggerFactory
			.getLogger(RolePermissionService.class);

	@Autowired
	private RolePermissionDao rolePermissionDao;

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
	public Page<RolePermission> searchPage(Map<String, Object> searchParams,
			Pageable pageable) {
		return rolePermissionDao.searchPage(searchParams, pageable);
	}

	/**
	 * 不分页查询
	 * 
	 * @param searchParas
	 *            查询条件
	 * @return
	 */
	public List<RolePermission> search(Map<String, Object> searchParas) {
		return rolePermissionDao.search(searchParas);
	}

	
	public RolePermission get(Long id) {
		return rolePermissionDao.get(id);
	}

	public void insert(RolePermission rolePermission) {
		rolePermissionDao.insert(rolePermission);
	}
	
	public void update(RolePermission rolePermission) {
		rolePermissionDao.update(rolePermission);
	}

	public void delete(Long id) {
		rolePermissionDao.delete(id);
	}

	public void setDateProvider(DateProvider dateProvider) {
		this.dateProvider = dateProvider;
	}
}
