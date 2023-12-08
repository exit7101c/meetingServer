package com.nse.pms.standard.sample.service;

import java.util.HashMap;
import java.util.List;

public interface SampleService {

	public List<HashMap<String, Object>> selectUserList(HashMap<String, Object> param);
	public HashMap<String, Object> selectUserOne(HashMap<String, Object> param);
	public HashMap<String, Object> insertUser(HashMap<String, Object> param) throws Exception;
	public HashMap<String, Object> updateUser(HashMap<String, Object> param);

	public List<HashMap<String, Object>> selectEnterpriseList(HashMap<String, Object> param);
	public HashMap<String, Object> selectEnterpriseOne(HashMap<String, Object> param);
	public HashMap<String, Object> insertEnterprise(HashMap<String, Object> param) throws Exception;
	public HashMap<String, Object> updateEnterprise(HashMap<String, Object> param);
	public HashMap<String, Object> disableEnterprise(HashMap<String, Object> param);
}
