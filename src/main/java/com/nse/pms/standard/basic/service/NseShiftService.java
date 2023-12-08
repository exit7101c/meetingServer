package com.nse.pms.standard.basic.service;

import java.util.HashMap;
import java.util.List;

public interface NseShiftService {

	/**
	* Shift 정보 조회
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	public List<HashMap<String, Object>> selectShiftList(HashMap<String, Object> param);
	
	/**
	* Shift 정보 단건 조회
	* @param HashMap<String, Object>
	* @return 
	**/
	public HashMap<String, Object> selectShiftOne(HashMap<String, Object> param);
	
	/**
	* Shift 정보 등록
	* @param HashMap<String, Object>
	* @return 
	**/
	public HashMap<String, Object> insertShift(HashMap<String, Object> param) throws Exception;
	
	/**
	* Shift 정보 수정
	* @param HashMap<String, Object>
	* @return 
	**/
	public HashMap<String, Object> updateShift(HashMap<String, Object> param) throws Exception;
	
	/**
	* Shift 정보 삭제
	* @param HashMap<String, Object>
	* @return 
	**/
	public HashMap<String, Object> deleteShift(HashMap<String, Object> param) throws Exception;
}
