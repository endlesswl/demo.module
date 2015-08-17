package com.lingcaibao.weixin.core.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class DataTablesPage {
	private Integer sEcho;
	private Long iTotalRecords;
	private Long iTotalDisplayRecords;
	private List<?> aaData = new ArrayList<Object>();
	
	public DataTablesPage() {
		
	}
	
	@SuppressWarnings("rawtypes")
	public DataTablesPage(Integer sEcho, Page page) {
		this.sEcho = sEcho;
		this.iTotalRecords = page.getTotalElements();
		this.iTotalDisplayRecords = page.getTotalElements();
		this.aaData = page.getContent();
	}
	
	public Integer getsEcho() {
		return sEcho;
	}

	public void setsEcho(Integer sEcho) {
		this.sEcho = sEcho;
	}

	public Long getiTotalRecords() {
		return iTotalRecords;
	}

	public void setiTotalRecords(Long iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	public Long getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setiTotalDisplayRecords(Long iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public List<?> getAaData() {
		return aaData;
	}

	public void setAaData(List<?> aaData) {
		this.aaData = aaData;
	}
	
}
