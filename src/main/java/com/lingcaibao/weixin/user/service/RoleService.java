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
import com.lingcaibao.weixin.user.entity.Role;
import com.lingcaibao.weixin.user.repository.RoleDao;

@Service
@Transactional
public class RoleService {

	private static Logger logger = LoggerFactory
			.getLogger(RoleService.class);

	@Autowired
	private RoleDao roleDao;

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
	public Page<Role> searchPage(Map<String, Object> searchParams,
			Pageable pageable) {
		return roleDao.searchPage(searchParams, pageable);
	}

	/**
	 * 不分页查询
	 * 
	 * @param searchParas
	 *            查询条件
	 * @return
	 */
	public List<Role> search(Map<String, Object> searchParas) {
		return roleDao.search(searchParas);
	}

	
	public Role get(Long id) {
		return roleDao.get(id);
	}

	public void insert(Role role) {
		roleDao.insert(role);
	}
	
	public void update(Role role) {
		roleDao.update(role);
	}

	public void delete(Long id) {
		roleDao.delete(id);
	}

	public void setDateProvider(DateProvider dateProvider) {
		this.dateProvider = dateProvider;
	}
}
