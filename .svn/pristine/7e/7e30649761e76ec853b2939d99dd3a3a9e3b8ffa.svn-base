package com.nse.pms.standard.common.service;

import java.util.HashMap;

import org.springframework.web.multipart.MultipartFile;

public interface ExcelUploadService {
	
	/**
	 * 접점일괄등록
	 * @return HashMap
	 */
	HashMap<String, Object> insertExcelUploadTag(HashMap<String, Object> param, MultipartFile file);
	
	/**
	 * 설비 일괄입력 
	 * @return HashMap
	 */
	HashMap<String, Object> insertExcelUploadEquip(HashMap<String, Object> param, MultipartFile file);
	
	/**
	 * 설비-접점 일괄입력 
	 * @return HashMap
	 */
	HashMap<String, Object> insertExcelUploadEquipTag(HashMap<String, Object> param, MultipartFile file);
	
	/**
	 * 사용자관리 일괄입력 
	 * @return HashMap
	 */
	HashMap<String, Object> insertExcelUploadUser(HashMap<String, Object> param, MultipartFile file);

	/**
	 * 생산계획 일괄입력
	 * @return HashMap
	 * */
	HashMap<String, Object> insertExcelUploadWorkPlan(HashMap<String, Object> param, MultipartFile file);

	/**
	 * 휴식시간관리 일괄 입력
	 * @return HashMap
	 * */
	HashMap<String, Object> insertExcelUploadRestPlan(HashMap<String, Object> param, MultipartFile file);

	/**
	 * CTQ 다중 상관 분석 데이터 UPLOAD
	 * @return HashMap
	 */
	HashMap<String, Object> insertExcelUploadCtqMultipleCorrelate(HashMap<String, Object> param, MultipartFile file) throws Exception;

}