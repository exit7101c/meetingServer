package com.nse.pms.standard.equipment.service;

import java.util.HashMap;
import java.util.List;

public interface NseRepairService {

	public List<HashMap<String,Object>> selectProcList(HashMap<String, Object> param);
	public List<HashMap<String,Object>> selectEqpList(HashMap<String, Object> param);
	public List<HashMap<String,Object>> selectEquipmentAlarmHis(HashMap<String, Object> param);
	public List<HashMap<String,Object>> selectRepairList(HashMap<String, Object> param);
	public HashMap<String,Object> selectRepairOne(HashMap<String, Object> param);
	public HashMap<String,Object> insertEqpRepair(HashMap<String, Object> param);
	public HashMap<String,Object> updateEqpRepair(HashMap<String, Object> param);
	public HashMap<String,Object> deleteEqpRepair(HashMap<String, Object> param);
	public HashMap<String,Object> selectRepairCharts(HashMap<String, Object> param);

	public List<HashMap<String,Object>> selectEqpPartList(HashMap<String, Object> param) throws Exception;
	public HashMap<String,Object> selectEqpPartOne(HashMap<String, Object> param);
	public HashMap<String,Object> insertEqpPart(HashMap<String, Object> param);
	public HashMap<String,Object> updateEqpPart(HashMap<String, Object> param);
	public HashMap<String,Object> deleteEqpPart(HashMap<String, Object> param);
	public List<HashMap<String, Object>> selectEqpPartMainPopup(HashMap<String, Object> param);
	public HashMap<String,Object> updateEqpPartAlarmOff(HashMap<String, Object> param);

}
