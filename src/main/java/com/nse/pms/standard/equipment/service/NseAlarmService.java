package com.nse.pms.standard.equipment.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.ui.ModelMap;

public interface NseAlarmService {

	public List<HashMap<String,Object>> selectEquipmentList(HashMap<String, Object> param);
	public List<HashMap<String,Object>> selectEquipmentRunHis(HashMap<String, Object> param)  throws Exception ;
	public List<HashMap<String,Object>> selectEquipmentRunHisList(HashMap<String, Object> param)  throws Exception ;
	public List<HashMap<String,Object>> selectEquipmentAlarmHis(HashMap<String, Object> param) throws Exception;
	public List<HashMap<String,Object>> selectEquipmentAlarmHisList(HashMap<String, Object> param) throws Exception;
	public List<HashMap<String,Object>> selectEquipmentClHisList(HashMap<String, Object> param) throws Exception;
	public List<HashMap<String,Object>> selectEquipmentAlarmTrend(HashMap<String, Object> param) throws Exception;
	public List<HashMap<String,Object>> selectEquipmentAlarmStatsList(HashMap<String, Object> param) throws Exception;
	
	public List<HashMap<String,Object>> selectAlarmsSats(HashMap<String, Object> param) throws Exception;
	public List<HashMap<String,Object>> selectEquipmentAlarmsSatsByDate(HashMap<String, Object> param) throws Exception;
	
}
