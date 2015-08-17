package com.lingcaibao.weixin.user.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.lingcaibao.weixin.user.entity.Role;

@Repository("roleDao")
public interface RoleDao {

	Role get(Long id);

	List<Role> search(Map<String, Object> parameters);

	Page<Role> searchPage(@Param("searchFields") Map<String, Object> searchParams, Pageable pageRequest);

	void insert(Role role);

	void delete(Long id);

	void update(Role role);

}
