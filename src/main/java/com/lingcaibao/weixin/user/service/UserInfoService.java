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
import com.lingcaibao.weixin.user.entity.UserInfo;
import com.lingcaibao.weixin.user.repository.UserInfoDao;

@Service
@Transactional
public class UserInfoService {

	private static Logger logger = LoggerFactory
			.getLogger(UserInfoService.class);

	@Autowired
	private UserInfoDao userInfoDao;

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
	public Page<UserInfo> searchPage(Map<String, Object> searchParams,
			Pageable pageable) {
		return userInfoDao.searchPage(searchParams, pageable);
	}

	/**
	 * 不分页查询
	 * 
	 * @param searchParas
	 *            查询条件
	 * @return
	 */
	public List<UserInfo> search(Map<String, Object> searchParas) {
		return userInfoDao.search(searchParas);
	}

	
	public UserInfo get(Long id) {
		return userInfoDao.get(id);
	}

	public void insert(UserInfo userInfo) {
		userInfo.setCreateTime(dateProvider.getDate());
		userInfoDao.insert(userInfo);
	}
	
	public void update(UserInfo userInfo) {
		userInfo.setModifyTime(dateProvider.getDate());
		userInfoDao.update(userInfo);
	}

	public void delete(Long id) {
		userInfoDao.delete(id);
	}

	public void setDateProvider(DateProvider dateProvider) {
		this.dateProvider = dateProvider;
	}
}
