package com.lingcaibao.weixin.maneger.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.lingcaibao.weixin.maneger.entity.WeixinNewsitem;

@Repository("weixinNewsitemDao")
public interface WeixinNewsitemDao {

	WeixinNewsitem get(String id);

	List<WeixinNewsitem> search(Map<String, Object> parameters);
	
	List<WeixinNewsitem> searchByIds(@Param(value="id") String id);

	Page<WeixinNewsitem> searchPage(@Param("searchFields") Map<String, Object> searchParams, Pageable pageRequest);

	void insert(WeixinNewsitem weixinNewsitem);

	void delete(String id);

	void update(WeixinNewsitem weixinNewsitem);

}
