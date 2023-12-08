package com.nse.pms.standard.basic.service;

import java.util.HashMap;
import java.util.List;

public interface NseProductGroupService {

	/**
	* ProductGroup 정보 조회
	* @param HashMap<String, Object>
	* @return 
	**/
	public List<HashMap<String, Object>> selectProductGroupList(HashMap<String, Object> param);
	
	/**
	* ProductGroup 정보 단건 조회
	* @param HashMap<String, Object>
	* @return 
	**/
	public HashMap<String, Object> selectProductGroupOne(HashMap<String, Object> param);
	
	/**
	* ProductGroup 정보 등록
	* @param HashMap<String, Object>
	* @return 
	**/
	public HashMap<String, Object> insertProductGroup(HashMap<String, Object> param) throws Exception;
	
	/**
	* ProductGroup 정보 수정
	* @param HashMap<String, Object>
	* @return 
	**/
	public HashMap<String, Object> updateProductGroup(HashMap<String, Object> param) throws Exception;
	
	/**
	* ProductGroup 정보 삭제
	* @param HashMap<String, Object>
	* @return 
	**/
	public HashMap<String, Object> deleteProductGroup(HashMap<String, Object> param) throws Exception;
}
