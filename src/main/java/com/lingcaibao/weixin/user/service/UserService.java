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
import com.lingcaibao.weixin.user.entity.User;
import com.lingcaibao.weixin.user.repository.UserDao;
@Service
@Transactional
public class UserService {
	
	private static Logger logger = LoggerFactory
			.getLogger(UserService.class);
	
	public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;
    private static final int SALT_SIZE = 8;
	@Autowired
	private UserDao userDao;

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
	public Page<User> searchPage(Map<String, Object> searchParams,
			Pageable pageable) {
		return userDao.searchPage(searchParams, pageable);
	}

	/**
	 * 不分页查询
	 * 
	 * @param searchParas
	 *            查询条件
	 * @return
	 */
	public List<User> search(Map<String, Object> searchParas) {
		return userDao.search(searchParas);
	}

	
	public User get(Long id) {
		return userDao.get(id);
	}

	public void insert(User user) {
		user.setCreateTime(dateProvider.getDate());
		userDao.insert(user);
	}
	
	public void update(User user) {
		user.setModifyTime(dateProvider.getDate());
		userDao.update(user);
	}

	public void delete(Long id) {
		userDao.delete(id);
	}

	public void setDateProvider(DateProvider dateProvider) {
		this.dateProvider = dateProvider;
	}
	
	/**
	 * 根据登录账号查询账户信息
	 * @param username
	 * @return user
	 */
	public User findUserByLoginName(String username) {
		return userDao.findUserByLoginName(username);
	}
	
	/**
	 * 更新用户登录次数
	 * @param user
	 */
	public void updateLogintimes(User user) {
		userDao.updateLogintimes(user);
	}
}
