package com.lingcaibao.weixin.user.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.lingcaibao.weixin.user.entity.UserRole;

@Repository("userRoleDao")
public interface UserRoleDao {

	UserRole get(Long id);

	List<UserRole> search(Map<String, Object> parameters);

	Page<UserRole> searchPage(@Param("searchFields") Map<String, Object> searchParams, Pageable pageRequest);

	void insert(UserRole userRole);

	void delete(Long id);

	void update(UserRole userRole);

}
