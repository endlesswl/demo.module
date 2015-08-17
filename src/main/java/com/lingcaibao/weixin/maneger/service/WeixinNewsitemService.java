package com.lingcaibao.weixin.maneger.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lingcaibao.weixin.core.entity.Result;
import com.lingcaibao.weixin.core.exceptions.ExceptionCode;
import com.lingcaibao.weixin.core.exceptions.ImportException;
//import com.lingcaibao.weixin.core.util.DatabaseUtil;
import com.lingcaibao.weixin.core.util.DateProvider;
import com.lingcaibao.weixin.core.util.UUIDUtils;
import com.lingcaibao.weixin.maneger.entity.WeixinAutoresponse;
import com.lingcaibao.weixin.maneger.entity.WeixinNewsitem;
import com.lingcaibao.weixin.maneger.repository.WeixinNewsitemDao;

/**
* @Title: 
* @Description: 
* @Author jhe   
* @Date 2013 - 2014
* @Version V1.0
* @Copyright © 2013 掌信彩通信息科技(中国)有限公司. All rights reserved.
*/
// Spring Service Bean的标识.
@Service
public class WeixinNewsitemService {

	private static Logger logger = LoggerFactory
			.getLogger(WeixinNewsitemService.class);

	@Autowired
	private WeixinNewsitemDao weixinNewsitemDao;
	
	@Autowired
	private WeixinAutoresponseService weixinAutoresponseService;
	
//	@Autowired
//	private DatabaseUtil databaseUtil;

	private DateProvider dateProvider = DateProvider.DEFAULT;

	/**
	 * 分页查询
	 * 
	 * @param searchParams
	 *            查询条件
	 * @param pageable
	 *            分页参数
	 * @return
	 */
	public Page<WeixinNewsitem> searchPage(Map<String, Object> searchParams,
			Pageable pageable) {
		return weixinNewsitemDao.searchPage(searchParams, pageable);
	}

	/**
	 * 不分页查询
	 * 
	 * @param searchParas
	 *            查询条件
	 * @return
	 */
	public List<WeixinNewsitem> search(Map<String, Object> searchParas) {
		return weixinNewsitemDao.search(searchParas);
	}

	
	public WeixinNewsitem get(String id) {
		return weixinNewsitemDao.get(id);
	}

	public void insert(WeixinNewsitem weixinNewsitem) {
		weixinNewsitem.setId(UUIDUtils.getShortUUID());
		weixinNewsitem.setCreateTime(dateProvider.getDate());
		weixinNewsitemDao.insert(weixinNewsitem);
	}
	
	public void update(WeixinNewsitem weixinNewsitem) {
		weixinNewsitem.setUpdateTime(dateProvider.getDate());
		weixinNewsitemDao.update(weixinNewsitem);
	}

	public void delete(String id) {
		weixinNewsitemDao.delete(id);
	}

	public void setDateProvider(DateProvider dateProvider) {
		this.dateProvider = dateProvider;
	}
	
	public List<WeixinNewsitem> searchByIds(String id){
		if (StringUtils.isBlank(id)) {
			return null;
		}	
		logger.debug("WeixinNewsitem searchByIds :{}"+id);
		return weixinNewsitemDao.searchByIds(id);
	}
	
	
	/**
	 * 导入项目经理
	 * @param fileJson
	 * @param accoutId
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public Result uploadFileAndInsertDb(String fileJson, String accoutId) throws ImportException {
		JSONObject obj = (JSONObject) JSONObject.parse(fileJson);
		JSONArray jsonArray = obj.getJSONArray("retultMsg");
		// 默认取得第一个
		JSONObject jsonObject = jsonArray.getJSONObject(0);
		String filePath = jsonObject.getString("filePath");
//		Connection connection=null;
//		File file=new File(filePath);
//		String chaType = getFilecharset(file);
		try {
			Workbook rwb=Workbook.getWorkbook(new File(filePath));
            Sheet rs=rwb.getSheet(0);//或者rwb.getSheet(0)
//            int clos=rs.getColumns();//得到所有的列
            int rows=rs.getRows();//得到所有的行
			WeixinNewsitem weixinNewsitem = new WeixinNewsitem();
			WeixinAutoresponse weixinAutoresponse = new WeixinAutoresponse();
            for (int i = 1; i < rows; i++) {//去除第一行
            	String uuid = UUIDUtils.getUUID();
            	weixinNewsitem.setId(uuid);
            	weixinNewsitem.setName(rs.getCell(0, i).getContents());//关键词
            	weixinNewsitem.setFlag("启用");
            	weixinNewsitem.setTitle(rs.getCell(2, i).getContents());//消息标题
            	weixinNewsitem.setDescription(rs.getCell(3, i).getContents());//消息内容
            	weixinNewsitem.setImagepath(rs.getCell(4, i).getContents());//消息图片
            	weixinNewsitem.setUrl(rs.getCell(5, i).getContents());//消息链接
            	weixinNewsitem.setCreateTime(dateProvider.getDate());
            	weixinNewsitem.setAccountId(accoutId);
            	weixinNewsitemDao.insert(weixinNewsitem);
            	
            	weixinAutoresponse.setAccountId(accoutId);
            	weixinAutoresponse.setId(UUIDUtils.getUUID());
            	weixinAutoresponse.setKeyword(rs.getCell(0, i).getContents());
            	weixinAutoresponse.setFlag("启用");
            	weixinAutoresponse.setMsgtype("图文消息");
            	weixinAutoresponse.setMessageId(uuid);
            	weixinAutoresponse.setCreateTime(dateProvider.getDate());
            	weixinAutoresponseService.insert(weixinAutoresponse);
            }
		}catch (IOException e) {
			e.printStackTrace();
			throw new ImportException("导入失败，导入文件不存在！", ExceptionCode.FILR_UNEXIT);
		} catch (BiffException e) {
			e.printStackTrace();
			throw new ImportException("导入失败，excel格式不对！", ExceptionCode.FILE_TYPE_ERROR);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ImportException("未知异常，请重新导入或联系系统管理员！");
		}
		return new Result(true, "导入成功，请勿重复操作！");
	}
}
