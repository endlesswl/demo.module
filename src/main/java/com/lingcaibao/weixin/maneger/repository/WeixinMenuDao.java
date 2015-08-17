package com.lingcaibao.weixin.maneger.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.lingcaibao.weixin.maneger.entity.WeixinMenu;

@Repository("weixinMenuDao")
public interface WeixinMenuDao {

	WeixinMenu get(String id);

	List<WeixinMenu> search(Map<String, Object> parameters);

	Page<WeixinMenu> searchPage(@Param("searchFields") Map<String, Object> searchParams, Pageable pageRequest);

	void insert(WeixinMenu weixinMenu);

	void delete(String id);

	void update(WeixinMenu weixinMenu);

}
