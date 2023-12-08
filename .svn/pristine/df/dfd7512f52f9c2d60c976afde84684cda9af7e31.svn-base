package com.nse.pms.standard.equipment.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nse.pms.standard.common.service.ConstraintService;
import com.nse.pms.standard.equipment.service.NseRepairService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import select.spring.exquery.service.ExqueryService;

import java.util.*;

@Service("repairService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class NseRepairServiceImpl implements NseRepairService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;

	@Autowired
	private ConstraintService constraintService;


	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String,Object>> selectProcList(HashMap<String,Object> param){
		List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
		resultList = exqueryService.selectList("nse.pms.equipment.sqlRepair.selectProcList", param);
		return resultList;		
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String,Object>> selectEqpList(HashMap<String,Object> param){
		List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
		resultList = exqueryService.selectList("nse.pms.equipment.sqlRepair.selectEqpList", param);
		return resultList;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String,Object>> selectEquipmentAlarmHis(HashMap<String,Object> param){
		List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
		resultList = exqueryService.selectList("nse.pms.equipment.sqlAlarm.selectEquipmentAlarmHis", param);
		return resultList;		
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String,Object>> selectRepairList(HashMap<String,Object> param){
		List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
		resultList = exqueryService.selectList("nse.pms.equipment.sqlRepair.selectRepairList", param);
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String,Object> selectRepairOne(HashMap<String,Object> param){
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		resultMap = exqueryService.selectOne("nse.pms.equipment.sqlRepair.selectRepairOne", param);
		return resultMap;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String,Object> insertEqpRepair(HashMap<String,Object> param){
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		exqueryService.insert("nse.pms.equipment.sqlRepair.insertEqpRepair", param);
		return resultMap;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String,Object> updateEqpRepair(HashMap<String,Object> param){
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		exqueryService.update("nse.pms.equipment.sqlRepair.updateEqpRepair", param);
		return resultMap;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String,Object> deleteEqpRepair(HashMap<String,Object> param){
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		exqueryService.delete("nse.pms.equipment.sqlRepair.deleteEqpRepair", param);
		return resultMap;
	}


	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String,Object> selectRepairCharts(HashMap<String,Object> param){
		HashMap<String,Object> resultMap = new HashMap<String,Object>();

		//기간별통계 (고장)
		List<HashMap<String, Object>> chartList1 = exqueryService.selectList("nse.pms.equipment.sqlRepair.selectRepairCharts1", param);

		//상새내역 조회
		if(chartList1.size() > 0){
			for(int idx=0, len=chartList1.size(); idx < len; idx++){
				int chkVal = Integer.parseInt(chartList1.get(idx).get("y").toString());
				if(chkVal > 0){
					//상세내역 조회
					chartList1.get(idx).put("ssUserLang", param.get("ssUserLang"));
					chartList1.get(idx).put("subData", exqueryService.selectList("nse.pms.equipment.sqlRepair.selectRepairCharts1Sub", chartList1.get(idx)));
				}
			}
		}


		//기간별통계 (보전)
		List<HashMap<String, Object>> chartList2 = exqueryService.selectList("nse.pms.equipment.sqlRepair.selectRepairCharts2", param);

		//상새내역 조회
		if(chartList2.size() > 0){
			for(int idx=0, len=chartList2.size(); idx < len; idx++){
				int chkVal = Integer.parseInt(chartList2.get(idx).get("y").toString());
				if(chkVal > 0){
					//상세내역 조회
					chartList2.get(idx).put("ssUserLang", param.get("ssUserLang"));
					chartList2.get(idx).put("subData", exqueryService.selectList("nse.pms.equipment.sqlRepair.selectRepairCharts1Sub", chartList2.get(idx)));
				}
			}
		}

		//공정설비별 통계 (고장)
		List<HashMap<String, Object>> chartList3 = exqueryService.selectList("nse.pms.equipment.sqlRepair.selectRepairCharts3", param);

		//공정설비별 통계 (보전)
		List<HashMap<String, Object>> chartList4 = exqueryService.selectList("nse.pms.equipment.sqlRepair.selectRepairCharts4", param);


		
		resultMap.put("chartList1", chartList1);
		resultMap.put("chartList2", chartList2);
		resultMap.put("chartList3", chartList3);
		resultMap.put("chartList4", chartList4);

		return resultMap;
	}



	/**
	 * 설비 부품수명 관리
	 * **/
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String,Object>> selectEqpPartList(HashMap<String,Object> param) throws Exception{
		List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
		List<HashMap<String,Object>> eqpList = new ArrayList<HashMap<String,Object>>();

		ObjectMapper mapper = new ObjectMapper();
		eqpList = mapper.readValue(param.get("eqpList").toString(), new TypeReference<ArrayList<HashMap<String, Object>>>() {});

		param.put("equipmentList",eqpList);

		resultList = exqueryService.selectList("nse.pms.equipment.sqlRepair.selectEqpPartList", param);
		return resultList;
	}


	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String,Object> selectEqpPartOne(HashMap<String,Object> param){
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		resultMap = exqueryService.selectOne("nse.pms.equipment.sqlRepair.selectEqpPartOne", param);
		return resultMap;
	}


	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String,Object> insertEqpPart(HashMap<String,Object> param){
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		exqueryService.insert("nse.pms.equipment.sqlRepair.insertEqpPart", param);
		return resultMap;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String,Object> updateEqpPart(HashMap<String,Object> param){
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		exqueryService.update("nse.pms.equipment.sqlRepair.updateEqpPart", param);
		return resultMap;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String,Object> deleteEqpPart(HashMap<String,Object> param){
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		exqueryService.delete("nse.pms.equipment.sqlRepair.deleteEqpPart", param);
		return resultMap;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>> selectEqpPartMainPopup(HashMap<String, Object> param) {

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		resultList = exqueryService.selectList("nse.pms.equipment.sqlRepair.selectEqpPartMainPopup", param);

//		if(resultList != null){
//
//			resultList.put("isExist", true);
//
//		}else {
//
//			resultMap = new HashMap<String, Object>();
//			resultMap.put("isExist", false);
//
//		}

		return resultList;
	}


	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String,Object> updateEqpPartAlarmOff(HashMap<String,Object> param){
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		exqueryService.update("nse.pms.equipment.sqlRepair.updateEqpPartAlarmOff", param);
		return resultMap;
	}

	
}
