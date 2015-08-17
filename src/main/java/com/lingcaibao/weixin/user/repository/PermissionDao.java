package com.lingcaibao.weixin.user.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.lingcaibao.weixin.user.entity.Permission;

@Repository("permissionDao")
public interface PermissionDao {

	Permission get(Long id);

	List<Permission> search(Map<String, Object> parameters);

	Page<Permission> searchPage(@Param("searchFields") Map<String, Object> searchParams, Pageable pageRequest);

	void insert(Permission permission);

	void delete(Long id);

	void update(Permission permission);

}
