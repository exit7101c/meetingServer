package com.nse.pms.standard.workorder.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.ui.ModelMap;

public interface NseWorkOrderService {
	/**
	 * 작업지시를 조회한다.
	 */
	public List<HashMap<String, Object>> selectWorkOrderList(HashMap<String, Object> param);

	public HashMap<String, Object> selectWorkOrderOne(HashMap<String, Object> param);

	/**
	 * MES 작업지시 수신.
	 */
	public HashMap<String, Object> executeIFRWorkOrder(HashMap<String, Object> param);

	/**
	 * Local 작업지시 생성.
	 */
	public HashMap<String, Object> insertWorkOrder(HashMap<String, Object> param) throws Exception;

	/**
	 * 작업지시 Active, InActive.
	 */
	public HashMap<String, Object> updateWorkOrder(HashMap<String, Object> param);

	/**
	 * 작업지시 취소.
	 */
	public HashMap<String, Object> deleteWorkOrder(HashMap<String, Object> param);
}
