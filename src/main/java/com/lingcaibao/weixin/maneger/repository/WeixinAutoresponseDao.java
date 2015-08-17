package com.lingcaibao.weixin.maneger.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.lingcaibao.weixin.maneger.entity.WeixinAutoresponse;


@Repository("weixinAutoresponseDao")
public interface WeixinAutoresponseDao {

	WeixinAutoresponse get(String id);

	List<WeixinAutoresponse> search(Map<String, Object> parameters);

	Page<WeixinAutoresponse> searchPage(@Param("searchFields") Map<String, Object> searchParams, Pageable pageRequest);

	void insert(WeixinAutoresponse weixinAutoresponse);

	void delete(String id);

	void update(WeixinAutoresponse weixinAutoresponse);

}
