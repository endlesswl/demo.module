package com.lingcaibao.weixin.account.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.lingcaibao.weixin.account.entity.WeixinAccount;

@Repository("weixinAccountDao")
public interface WeixinAccountDao {

	WeixinAccount get(String id);

	List<WeixinAccount> search(Map<String, Object> parameters);

	Page<WeixinAccount> searchPage(@Param("searchFields") Map<String, Object> searchParams, Pageable pageRequest);

	void insert(WeixinAccount weixinAccount);

	void delete(String id);

	void update(WeixinAccount weixinAccount);

}
