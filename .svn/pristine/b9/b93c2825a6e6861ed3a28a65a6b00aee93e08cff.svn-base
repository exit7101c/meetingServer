package com.nse.pms.standard.basic.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.ui.ModelMap;

public interface NseProcessService {

	public List<HashMap<String, Object>> selectApiAreaLineProcEqp(HashMap<String, Object> param);
	
	public List<HashMap<String, Object>> selectApiLineProcList(HashMap<String, Object> param);
	
	public List<HashMap<String, Object>> selectApiLineProcEqpList(HashMap<String, Object> param);
	
	/**
	* Process 정보 조회
	* @param HashMap<String, Object>
	* @return 
	**/
	public List<HashMap<String, Object>> selectProcessList(HashMap<String, Object> param);
	
	/**
	* Process 정보 단건 조회
	* @param HashMap<String, Object>
	* @return 
	**/
	public HashMap<String, Object> selectProcessOne(HashMap<String, Object> param);
	
	/**
	* Process 정보 조회
	* @param HashMap<String, Object>
	* @return 
	**/
	public List<HashMap<String, Object>> selectProcessTypeList(HashMap<String, Object> param);	
	/**
	* Process 정보 등록
	* @param HashMap<String, Object>
	* @return 
	**/
	public HashMap<String, Object> insertProcess(HashMap<String, Object> param) throws Exception;
	
	/**
	* Process 정보 수정
	* @param HashMap<String, Object>
	* @return 
	**/
	public HashMap<String, Object> updateProcess(HashMap<String, Object> param) throws Exception;
	
	/**
	* Process 정보 삭제
	* @param HashMap<String, Object>
	* @return 
	**/
	public HashMap<String, Object> deleteProcess(HashMap<String, Object> param) throws Exception;
	
	
	/**
	* LINE - PROCESS TREE LIST
	* @param HashMap<String, Object>
	* @return 
	**/
	public List<HashMap<String, Object>> selectAreaLineProcList(HashMap<String, Object> param) throws Exception;
	
	/**
	* 공정-설비 Mapping
	* @param 
	* @return 
	**/
	public HashMap<String, Object> updateProcEqpMapping(HashMap<String, Object> param) throws Exception;

	/**
	* 제품-상하한 Mapping
	* @param
	* @return
	**/
	public HashMap<String, Object> updateProdSpecMapping(HashMap<String, Object> param) throws Exception;

	/**
	* 공정-설비가동차트
	* @param
	* @return
	**/
	public HashMap<String, Object> selectEqpStateHisOnChart(HashMap<String, Object> param) throws Exception;

	/**
	* MES Process 정보 조회
	* @param HashMap<String, Object>
	* @return 
	**/
	public List<HashMap<String, Object>> selectMESProcessList(HashMap<String, Object> param);
}
