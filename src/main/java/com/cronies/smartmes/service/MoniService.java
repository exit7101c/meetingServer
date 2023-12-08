package com.cronies.smartmes.service;

import java.util.HashMap;
import java.util.List;

public interface MoniService {

	public HashMap<String, Object> selectLineProc(HashMap<String, Object> param) throws Exception;
	public HashMap<String, Object> selectLineProcRun(HashMap<String, Object> param) throws Exception;
	public HashMap<String, Object> sendLineProcManager(HashMap<String, Object> param) throws Exception;

}