package com.lingcaibao.weixin.core.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.lingcaibao.weixin.core.entity.FileResult;


public class FileuploadUtil {
	private static Logger logger = LoggerFactory
            .getLogger(FileuploadUtil.class);
	private static String temp = "/uploadfile";


	public static String realPath = "/home/pangxin/image";
	

	private static long fileMaxSize = 1024000;

	private static String errorMsg = "{\"resultCode\":\"50001\",\"retultMsg\":\"不是multipart/form-data类型\"}";
	private static String otherMsg = "{\"resultCode\":\"50002\",\"retultMsg\":\"请选择文件\"}";
	private static String noAuth = "{\"resultCode\":\"50003\",\"retultMsg\":\"该用户没有改权限\"}";

	public static String uploadFile(HttpServletRequest request) {
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().indexOf("windows") > -1) {
			FileuploadUtil.realPath = temp;
		}
//		StringBuilder uploadTemp = new StringBuilder();
//		uploadTemp.append(realPath).append(temp);
		boolean multipartContent = ServletFileUpload
				.isMultipartContent(request);// 检查输入请求是否为multipart
		InputStream inputStream = null;

		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		String format =DateFormatUtil.getCurrentDate("yyyyMMddHHmmss");
		StringBuilder uploadDir = new StringBuilder();

		uploadDir.append(FileuploadUtil.realPath).append("/").append(year)
				.append("/").append(month).append("/");
		logger.info("上传目录：{}", uploadDir.toString());

		try {
			if (multipartContent) {
				DiskFileItemFactory factory = new DiskFileItemFactory();//
				factory.setSizeThreshold(4096);// 设置缓冲区大小，这里是4kb
				// factory.setRepository(tempPathFile);// 设置缓冲区目录
				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setSizeMax(fileMaxSize);// 设置上传文件大小
				List<FileItem> items = upload.parseRequest(request);// 获取所有文件
				Iterator<FileItem> i = items.iterator();
				List<FileResult> fileList = new ArrayList<FileResult>();
				while (i.hasNext()) {
					FileItem fileItem = i.next();
					String fileName = fileItem.getName();
					if (fileName != null && !fileName.equals("")) {
						String subStr = fileName.substring(
								fileName.lastIndexOf("."), fileName.length());//文件后缀名
						int random = new Random().nextInt(10000);
						inputStream = fileItem.getInputStream();
						uploadDir.append(format).append("_").append(random)
								.append(subStr);
						
						File file = new File(uploadDir.toString());
						if (file.getParentFile() != null && !file.getParentFile().exists()) {
							file.getParentFile().mkdir();
						}
						FileResult fileResult = new FileResult();
						fileResult.setFileName(file.getName());

						/*String filePath = file.getAbsolutePath().substring(
								realPath.length(),
								file.getAbsolutePath().length());*/
						fileResult.setFilePath(file.getAbsolutePath());
						FileUtils.copyInputStreamToFile(inputStream, file);
						fileList.add(fileResult);
						logger.info("最终生成文件：{}", uploadDir.toString());
					}
				}
				if (fileList != null && !fileList.isEmpty()) {
					return returnOKMsg(fileList);
				} else {
					return otherMsg;
				}
			} else {
				return errorMsg;
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
		return "{\"resultCode\":\"500\",\"retultMsg\":\"上传失败\"}";
	}
	
	public static String returnOKMsg(List<FileResult> fileList) {
		StringBuilder sb = new StringBuilder();
		sb.append("{\"resultCode\":\"0\",\"retultMsg\":").append(JSON.toJSON(fileList)).append("}");
		return sb.toString();
	}

	public void downloadFile(HttpServletRequest request,
			HttpServletResponse response) {
		File file = new File("/tmp/test.xls");
		try {
			byte[] readFileToByteArray = FileUtils.readFileToByteArray(file);
			/** 重置response **/
			response.reset();
			/** 设置HTML头部信息 **/
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ "tttt" + "\"");
			response.addHeader("Content-Length", ""
					+ readFileToByteArray.length);
			response.setContentType("application/octet-stream;charset=UTF-8");
			/** 获得输出流 **/
			OutputStream outputStream = new BufferedOutputStream(
					response.getOutputStream());
			/** 从字节数组中将文件写到输出流中 **/
			outputStream.write(readFileToByteArray);
			/** 清空输出流 **/
			outputStream.flush();
			/*** 关闭输入流 */
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String format =DateFormatUtil.getCurrentDate("yyyyMMddHHmmss");
		System.out.println(format);
	}
	
	public static String getRealPath() {
		return realPath;
	}

	public static void setRealPath(String realPath) {
		FileuploadUtil.realPath = realPath;
	}
	
	public static String getTemp() {
		return temp;
	}

	public static void setTemp(String temp) {
		FileuploadUtil.temp = temp;
	}
}
