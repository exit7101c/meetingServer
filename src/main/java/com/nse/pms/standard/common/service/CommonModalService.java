package com.nse.pms.standard.common.service;

import java.util.HashMap;
import java.util.List;

public interface CommonModalService {

	/**
	 * [공통 모달창] 접점 상세보기 - 테이블,차트 데이터 조회
	 * @throws Exception
	 **/
	public HashMap<String, Object> selectTagChartList(HashMap<String, Object> param) throws Exception;
	public List<HashMap<String, Object>> selectTagChartListExcel(HashMap<String, Object> param) throws Exception;

	/**
	 * [공통 모달창] 알람 상세보기 - 테이블 데이터 조회
	 * @throws Exception
	 **/
	public HashMap<String, Object> selectAlarmList(HashMap<String, Object> param) throws Exception;
	public List<HashMap<String, Object>> selectAlarmListExcel(HashMap<String, Object> param) throws Exception;

	/**
	 * [공통 모달창] 가동 상세보기 - 테이블 데이터 조회
	 * @throws Exception
	 **/
	public HashMap<String, Object> selectEqpStateList(HashMap<String, Object> param) throws Exception;
	public List<HashMap<String, Object>> selectEqpStateListExcel(HashMap<String, Object> param) throws Exception;

}
