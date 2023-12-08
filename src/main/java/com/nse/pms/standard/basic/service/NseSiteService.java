package com.nse.pms.standard.basic.service;

import java.util.HashMap;
import java.util.List;

public interface NseSiteService {

	/**
	* Site 정보 조회
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	public List<HashMap<String, Object>> selectSiteList(HashMap<String, Object> param);
	
	/**
	* Site 정보 단건 조회
	* @param HashMap<String, Object>
	* @return 
	**/
	public HashMap<String, Object> selectSiteOne(HashMap<String, Object> param);
	
	/**
	* Site 정보 등록
	* @param HashMap<String, Object>
	* @return 
	**/
	public HashMap<String, Object> insertSite(HashMap<String, Object> param) throws Exception;
	
	/**
	* Site 정보 수정
	* @param HashMap<String, Object>
	* @return 
	**/
	public HashMap<String, Object> updateSite(HashMap<String, Object> param) throws Exception;
	
	/**
	* Site 정보 삭제
	* @param HashMap<String, Object>
	* @return 
	**/
	public HashMap<String, Object> deleteSite(HashMap<String, Object> param) throws Exception;
}
