package com.nse.pms.standard.basic.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.ui.ModelMap;

public interface NseUnitService {
	/**
	* Unit 정보 조회
	* @param HashMap<String, Object>
	* @return 
	**/
	public List<HashMap<String, Object>> selectUnitList(HashMap<String, Object> param);
}
