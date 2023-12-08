package com.cronies.smartmes.service;

import java.util.HashMap;

public interface MobileService {

	public HashMap<String, Object> getBaseInfo(HashMap<String, Object> param) throws Exception;
	public HashMap<String, Object> startRun(HashMap<String, Object> param) throws Exception;
	public HashMap<String, Object> stopRun(HashMap<String, Object> param) throws Exception;
	public HashMap<String, Object> regProduction(HashMap<String, Object> param) throws Exception;
	public HashMap<String, Object> getTodayProduction(HashMap<String, Object> param) throws Exception;
	public HashMap<String, Object> checkRunYnState(HashMap<String, Object> param) throws Exception;
	public HashMap<String, Object> deleteInput(HashMap<String, Object> param) throws Exception;
	public HashMap<String, Object> getProcListByLineChange(HashMap<String, Object> param) throws Exception;

}