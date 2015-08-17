package com.lingcaibao.weixin.user.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.lingcaibao.weixin.user.entity.RolePermission;

@Repository("rolePermissionDao")
public interface RolePermissionDao {

	RolePermission get(Long id);

	List<RolePermission> search(Map<String, Object> parameters);

	Page<RolePermission> searchPage(@Param("searchFields") Map<String, Object> searchParams, Pageable pageRequest);

	void insert(RolePermission rolePermission);

	void delete(Long id);

	void update(RolePermission rolePermission);

}
