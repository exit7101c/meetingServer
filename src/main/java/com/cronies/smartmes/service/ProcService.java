package com.cronies.smartmes.service;

import java.util.HashMap;
import java.util.List;

public interface ProcService {

	public HashMap<String, Object> selectProcOne(HashMap<String, Object> param) throws Exception;
	public HashMap<String, Object> insertProcess(HashMap<String, Object> param) throws Exception;
	public HashMap<String, Object> updateProcess(HashMap<String, Object> param) throws Exception;
	public HashMap<String, Object> deleteProcess(HashMap<String, Object> param) throws Exception;

}