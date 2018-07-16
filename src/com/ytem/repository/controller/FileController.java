package com.ytem.repository.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ytem.repository.common.Const;
import com.ytem.repository.common.JsonResult;
import com.ytem.repository.common.ResponseCode;
import com.ytem.repository.utils.DateTimeUtil;

/**
 * 处理文件
 * @author 陈康敬💪
 * @date 2018年6月7日下午11:22:05
 * @description
 */
@Controller
@RequestMapping("file")
public class FileController {
	
	private final Logger logger = Logger.getLogger(UserController.class);
	
	/**
	 * 上传文件.
	 * @param file
	 * @return
	 */
	@RequestMapping("upload.do")
	@ResponseBody
	public JsonResult upload(MultipartFile file) {
		JsonResult result;
		
		// 校验
		if (file == null || file.isEmpty()) {
			result = new JsonResult(ResponseCode.ERROR.getCode(), "请选择导入的文件");
			return result;
		}
		
		try {
			// 生成随机名称
			String fileName = file.getOriginalFilename();
			String ext = fileName.substring(fileName.lastIndexOf("."));
			String newFileName = UUID.randomUUID().toString().replace("-", "") + ext;
			
			String path = getUploadFileTmpDir();
			
			// 构建写出文件
			File newFile = new File(path + newFileName);
			FileOutputStream fos = new FileOutputStream(newFile);
			
			FileCopyUtils.copy(file.getInputStream(), fos);
			
			String msg = path + newFileName + "|" + fileName;
			result = new JsonResult(ResponseCode.SUCCESS.getCode(), msg);
		} catch (Exception e) {
			logger.error("异常信息-上传文件失败", e);
			result = new JsonResult(ResponseCode.ERROR.getCode(), "上传文件失败");
		}
		
		return result;
	}
	
	/**
	 * 获取上传文件临时目录
	 * @return
	 */
	private String getUploadFileTmpDir() {
		String rootDir = Const.UPLOAD_FILE_PATH;
		String tempDir = Const.SUFFIX_TMPFILE_SAVEPATH;
		
		// 按照指定日期规则生成路径.
		int year = DateTimeUtil.getYear();
		int month = DateTimeUtil.getMonth();
		int day = DateTimeUtil.getDay();
		
		tempDir = tempDir.replace("@year", Integer.toString(year))
						 .replace("@month", Integer.toString(month))
						 .replace("@day", Integer.toString(day));
		
		String filePath = rootDir + tempDir;
		
		// 构建文件夹
		File fileDir = new File(filePath);
		if ( !fileDir.exists() ) {
			fileDir.mkdirs();
		}
		
		return filePath;
	}
}
