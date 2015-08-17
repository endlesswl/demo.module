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
import com.lingcaibao.weixin.user.entity.UserRole;
import com.lingcaibao.weixin.user.repository.UserRoleDao;

@Service
@Transactional
public class UserRoleService {

	private static Logger logger = LoggerFactory
			.getLogger(UserRoleService.class);

	@Autowired
	private UserRoleDao userRoleDao;

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
	public Page<UserRole> searchPage(Map<String, Object> searchParams,
			Pageable pageable) {
		return userRoleDao.searchPage(searchParams, pageable);
	}

	/**
	 * 不分页查询
	 * 
	 * @param searchParas
	 *            查询条件
	 * @return
	 */
	public List<UserRole> search(Map<String, Object> searchParas) {
		return userRoleDao.search(searchParas);
	}

	
	public UserRole get(Long id) {
		return userRoleDao.get(id);
	}

	public void insert(UserRole userRole) {
		userRoleDao.insert(userRole);
	}
	
	public void update(UserRole userRole) {
		userRoleDao.update(userRole);
	}

	public void delete(Long id) {
		userRoleDao.delete(id);
	}

	public void setDateProvider(DateProvider dateProvider) {
		this.dateProvider = dateProvider;
	}
}
