package com.nse.pms.standard.workresult.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.ui.ModelMap;

public interface NseWorkResultService {
	/**
	 * 작업지시를 조회한다.
	 */
	public List<HashMap<String, Object>> selectWorkResultList(HashMap<String, Object> param);

	/**
	 * MES 작업지시 수신.
	 */
	public HashMap<String, Object> executeIFRWorkResult(HashMap<String, Object> param);

	/**
	 * Lot tracking.
	 */
	public List<HashMap<String, Object>> selectWorkResultTree(HashMap<String, Object> param);

	/**
	 * Line - Tag List
	 */
	public List<HashMap<String, Object>> selectLineProcEqpTagList(HashMap<String, Object> param);

	/**
	 * Work Condition
	 */
	public List<HashMap<String, Object>> selectWorkConditionList(HashMap<String, Object> param) throws Exception;
}
