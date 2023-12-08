package com.nse.pms.standard.common.service;

import java.util.HashMap;

import org.springframework.web.multipart.MultipartFile;

public interface CommonFileService {

	public int insertWithFile(String key, HashMap<String, Object> param, MultipartFile file);

	public int insertWithThumbnailFile(String key, HashMap<String, Object> param, MultipartFile file);

	public int updateWithFile(String key, HashMap<String, Object> param, MultipartFile file);

	public int deleteWithFile(String key, HashMap<String, Object> param);

	public int deleteWithFileId(String key, HashMap<String, Object> param);

	public HashMap<String, Object> readFile(String key, HashMap<String, Object> param);

	/**
	 * 이미지 파일 생성
	 */
	public HashMap<String, Object> createFile(HashMap<String, Object> param, MultipartFile file) throws Exception;

	public int insertFileInfo(HashMap<String, Object> param);

	//public int insertWithFileOnFtp(String key, HashMap<String, Object> param, MultipartFile file);
	

}
