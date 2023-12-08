package com.nse.pms.standard.basic.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.ui.ModelMap;

public interface NseWcService {

	public List<HashMap<String, Object>> selectWcList(HashMap<String, Object> param);
	public HashMap<String, Object> selectWcOne(HashMap<String, Object> param);
	public HashMap<String, Object> insertWc(HashMap<String, Object> param) throws Exception;
	public HashMap<String, Object> updateWc(HashMap<String, Object> param);
	public HashMap<String, Object> deleteWc(HashMap<String, Object> param);
	

}
