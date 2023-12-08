package com.nse.pms.standard.basic.service;

import java.util.HashMap;
import java.util.List;

public interface NseMtrlService {

	/**
	* 자재그룹 (Mtrl Group) 정보 조회
	* @param HashMap<String, Object>
	* @return 
	**/
	public HashMap<String, Object> selectMtrlGroupList(HashMap<String, Object> param);

	/**
	 * 자재그룹 (Mtrl Group) 정보 조회
	 * @param HashMap<String, Object>
	 * @return
	 **/
	public List<HashMap<String, Object>> selectNonPageMtrlGroupList(HashMap<String, Object> param);
	
	/**
	* 자재그룹 (Mtrl Group) 정보 단건 조회
	* @param HashMap<String, Object>
	* @return 
	**/
	public HashMap<String, Object> selectMtrlGroupOne(HashMap<String, Object> param);
	
	/**
	* 자재그룹 (Mtrl Group) 정보 등록
	* @param HashMap<String, Object>
	* @return 
	**/
	public HashMap<String, Object> insertMtrlGroup(HashMap<String, Object> param) throws Exception;
	
	/**
	* 자재그룹 (Mtrl Group) 정보 수정
	* @param HashMap<String, Object>
	* @return 
	**/
	public HashMap<String, Object> updateMtrlGroup(HashMap<String, Object> param) throws Exception;
	
	/**
	* 자재그룹 (Mtrl Group) 정보 삭제
	* @param HashMap<String, Object>
	* @return 
	**/
	public HashMap<String, Object> deleteMtrlGroup(HashMap<String, Object> param) throws Exception;
	
	/**
	* 자재유형 (Mtrl Type)  정보 조회
	* @param HashMap<String, Object>
	* @return 
	**/
	public List<HashMap<String, Object>> selectMtrlTypeList(HashMap<String, Object> param);
	
	/**
	* 자재유형 (Mtrl Type) 정보 단건 조회
	* @param HashMap<String, Object>
	* @return 
	**/
	public HashMap<String, Object> selectMtrlTypeOne(HashMap<String, Object> param);
	
	/**
	* 자재유형 (Mtrl Type)  정보 등록
	* @param HashMap<String, Object>
	* @return 
	**/
	public HashMap<String, Object> insertMtrlType(HashMap<String, Object> param) throws Exception;
	
	/**
	* 자재유형 (Mtrl Type) 정보 수정
	* @param HashMap<String, Object>
	* @return 
	**/
	public HashMap<String, Object> updateMtrlType(HashMap<String, Object> param) throws Exception;
	
	/**
	* 자재유형 (Mtrl Type)  정보 삭제
	* @param HashMap<String, Object>
	* @return 
	**/
	public HashMap<String, Object> deleteMtrlType(HashMap<String, Object> param) throws Exception;
	
}
