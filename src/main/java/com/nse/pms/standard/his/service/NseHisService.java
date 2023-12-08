package com.nse.pms.standard.his.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.ui.ModelMap;

public interface NseHisService {


	public HashMap<String, Object> selectHisWeatherChart(HashMap<String, Object> param) throws Exception;

}
