package com.lingcaibao.weixin.user.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.lingcaibao.weixin.user.entity.UserInfo;

@Repository("userInfoDao")
public interface UserInfoDao {

	UserInfo get(Long id);

	List<UserInfo> search(Map<String, Object> parameters);

	Page<UserInfo> searchPage(@Param("searchFields") Map<String, Object> searchParams, Pageable pageRequest);

	void insert(UserInfo userInfo);

	void delete(Long id);

	void update(UserInfo userInfo);

}
