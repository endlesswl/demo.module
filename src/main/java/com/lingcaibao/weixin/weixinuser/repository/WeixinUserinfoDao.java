package com.lingcaibao.weixin.weixinuser.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.lingcaibao.weixin.weixinuser.entity.WeixinUserinfo;

@Repository("weixinUserinfoDao")
public interface WeixinUserinfoDao {

	WeixinUserinfo get(String id);

	List<WeixinUserinfo> search(Map<String, Object> parameters);

	Page<WeixinUserinfo> searchPage(@Param("searchFields") Map<String, Object> searchParams, Pageable pageRequest);

	void insert(WeixinUserinfo weixinUserinfo);

	void delete(String id);

	void update(WeixinUserinfo weixinUserinfo);

}
