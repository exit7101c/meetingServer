package com.nse.pms.standard.monitoring.service;

import java.util.HashMap;
import java.util.List;

public interface NseEnergyService {


	public List<HashMap<String, Object>> selectPowerUsageList(HashMap<String, Object> param) throws Exception;
	public List<HashMap<String, Object>> selectPowerUsageStateList(HashMap<String, Object> param) throws Exception;
	public List<HashMap<String, Object>> selectEngTagCurrentList(HashMap<String, Object> param) throws Exception;

}
