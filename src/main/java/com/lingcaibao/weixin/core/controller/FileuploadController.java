package com.lingcaibao.weixin.core.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lingcaibao.weixin.core.entity.Result;
import com.lingcaibao.weixin.core.util.FileuploadUtil;
import com.palm.lingcai.api.Image;


@Controller
@RequestMapping(value = "/admin/fileupload")
public class FileuploadController {
    private static Logger logger = LoggerFactory
            .getLogger(FileuploadController.class);

    @Autowired
    private Image imageFtpApi;
    
    private String temp = "/uploadfile";
    private long fileMaxSize = 1024000;

    private String errorMsg = "{\"resultCode\":\"50001\",\"retultMsg\":\"不是multipart/form-data类型\"}";
    private String otherMsg = "{\"resultCode\":\"50002\",\"retultMsg\":\"请选择文件\"}";
    private String noAuth = "{\"resultCode\":\"50003\",\"retultMsg\":\"该用户没有改权限\"}";
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFile(HttpServletRequest request, RedirectAttributes redirectAttributes) {

//        boolean hasAdmin = ShiroUtils.hasRole("administrator");
//        if (!hasAdmin) {
//            return noAuth;
//        }
    	logger.debug("文件上传.........................");
        return  FileuploadUtil.uploadFile(request);
    }
    
    
    @RequestMapping(value = "/uploadImgRU", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFileReturnUrl(HttpServletRequest request, RedirectAttributes redirectAttributes) {

//        boolean hasAdmin = ShiroUtils.hasRole("administrator");
//        if (!hasAdmin) {
//            return noAuth;
//        }
    	 Result result;
    	logger.debug("图片上传.........................");
    	String path = FileuploadUtil.uploadFile(request);
    	logger.debug("path"+path);
    	JSONObject  json = JSONObject.parseObject(path);
    	if (json==null||"500".equals(json.get("resultCode"))) {
    		result= new Result(false, "文件上传失败，请重新上传或联系管理员！", null);
    		return JSON.toJSONString(result);
		}
    	JSONArray jsonArray = json.getJSONArray("retultMsg");
    	JSONObject info=jsonArray.getJSONObject(0);
    	logger.debug("info:{}",info.toJSONString());
    	String fileName =info.getString("filePath");
        logger.debug("fileName:{}",fileName);
        String url = "";
        try {
        	url = imageFtpApi.uploadReturnUrl(fileName);
		} catch (Exception e) {
			e.printStackTrace();
			return JSON.toJSONString(new Result(false, "文件上传失败，请重新上传或联系管理员！", null));
		}
        logger.debug("url:{}",url);
        result= new Result(true, url, null);
        logger.info("response:{}",JSON.toJSONString(result));
        
        return  JSON.toJSONString(result);
    }

    
   
    

  
}
