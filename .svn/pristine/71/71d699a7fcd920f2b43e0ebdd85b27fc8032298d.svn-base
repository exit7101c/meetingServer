package com.nse.pms.standard.equipment.service.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nse.pms.standard.common.service.ConstraintService;
import com.nse.pms.standard.equipment.service.NseAlarmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import select.spring.exquery.service.ExqueryService;

import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("alarmService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class NseAlarmServiceImpl implements NseAlarmService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;

	@Autowired
	private ConstraintService constraintService;


	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String,Object>> selectEquipmentList(HashMap<String,Object> param){
		List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
		resultList = exqueryService.selectList("nse.pms.equipment.sqlAlarm.selectEquipmentList", param);
		return resultList;		
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String,Object>> selectEquipmentRunHis(HashMap<String,Object> param)  throws Exception {
		List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
		resultList = exqueryService.selectList("nse.pms.equipment.sqlAlarm.selectEquipmentRunHis", param);
		return resultList;
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String,Object>> selectEquipmentRunHisList(HashMap<String,Object> param)  throws Exception {
		List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
		List<HashMap<String,Object>> eqpList = new ArrayList<HashMap<String,Object>>();
		ObjectMapper mapper = new ObjectMapper();
		eqpList = mapper.readValue(param.get("eqpList").toString(), new TypeReference<ArrayList<HashMap<String, Object>>>() {});
		
		for (int idx = 0, len = eqpList.size(); idx < len; idx++) {
		   eqpList.get(idx).put("ssUserLang", param.get("ssUserLang"));
		   eqpList.get(idx).put("fromDate", param.get("fromDate"));
		   eqpList.get(idx).put("toDate", param.get("toDate"));
		   eqpList.get(idx).put("searchStr1", eqpList.get(idx).get("code"));
			
		   List<HashMap<String, Object>> resultEqpList = exqueryService.selectList("nse.pms.equipment.sqlAlarm.selectEquipmentRunHis", eqpList.get(idx));
		   HashMap<String, Object> resultMap = new HashMap<String, Object>();
		   
		   resultMap.put("eqpNm", eqpList.get(idx).get("name"));
		   resultMap.put("eqpCd", eqpList.get(idx).get("code"));
		   resultMap.put("resultEqpList", resultEqpList);
		   resultList.add(resultMap);
		}

		
		return resultList;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String,Object>> selectEquipmentAlarmHis(HashMap<String,Object> param){
		List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
		resultList = exqueryService.selectList("nse.pms.equipment.sqlAlarm.selectEquipmentAlarmHis", param);
		return resultList;		
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String,Object>> selectEquipmentAlarmHisList(HashMap<String,Object> param) throws Exception{
		List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
		List<HashMap<String, Object>> eqpList = new ArrayList<HashMap<String, Object>>();
		ObjectMapper mapper = new ObjectMapper();
		eqpList = mapper.readValue(param.get("eqpList").toString(), new TypeReference<ArrayList<HashMap<String,Object>>>() {} );
		
		for(int i=0; i<eqpList.size(); i++) {
			List<HashMap<String,Object>> resultAlarmList = new ArrayList<HashMap<String,Object>>();
			HashMap<String,Object> resultAlarm = new HashMap<String ,Object>();
			
		    eqpList.get(i).put("ssUserLang", param.get("ssUserLang"));
		    eqpList.get(i).put("fromDate", param.get("fromDate"));
		    eqpList.get(i).put("toDate", param.get("toDate"));
		    eqpList.get(i).put("searchStr1", eqpList.get(i).get("code"));
		    eqpList.get(i).put("alarmCode", param.get("alarmCode"));
		    eqpList.get(i).put("alarmLevel", param.get("alarmLevel"));
		    
		    resultAlarmList = exqueryService.selectList("nse.pms.equipment.sqlAlarm.selectEquipmentAlarmHis", eqpList.get(i));	
		    resultAlarm.put("eqpName", eqpList.get(i).get("name"));
		    resultAlarm.put("eqpCd", eqpList.get(i).get("code"));
		    resultAlarm.put("resultAlarmList",resultAlarmList);
		    resultList.add(resultAlarm);
		}
		
		return resultList;		
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String,Object>> selectEquipmentClHisList(HashMap<String,Object> param) throws Exception{
		List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
		List<HashMap<String, Object>> eqpList = new ArrayList<HashMap<String, Object>>();
		ObjectMapper mapper = new ObjectMapper();
		eqpList = mapper.readValue(param.get("eqpList").toString(), new TypeReference<ArrayList<HashMap<String,Object>>>() {} );

		for(int i=0; i<eqpList.size(); i++) {
			List<HashMap<String,Object>> resultAlarmList = new ArrayList<HashMap<String,Object>>();
			HashMap<String,Object> resultAlarm = new HashMap<String ,Object>();

		    eqpList.get(i).put("ssUserLang", param.get("ssUserLang"));
		    eqpList.get(i).put("fromDate", param.get("fromDate"));
		    eqpList.get(i).put("toDate", param.get("toDate"));
		    eqpList.get(i).put("searchStr1", eqpList.get(i).get("code"));
		 //   eqpList.get(i).put("alarmCode", param.get("alarmCode"));
		//    eqpList.get(i).put("alarmLevel", param.get("alarmLevel"));

		    resultAlarmList = exqueryService.selectList("nse.pms.equipment.sqlAlarm.selectEquipmentClHis", eqpList.get(i));
		    resultAlarm.put("eqpName", eqpList.get(i).get("name"));
		    resultAlarm.put("eqpCd", eqpList.get(i).get("code"));
		    resultAlarm.put("resultAlarmList",resultAlarmList);
		    resultList.add(resultAlarm);
		}

		return resultList;
	}

	//설비 알람 이력 트랜드
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String,Object>> selectEquipmentAlarmTrend(HashMap<String,Object> param) throws Exception{
		List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
		List<HashMap<String, Object>> eqpList = new ArrayList<HashMap<String, Object>>();
		ObjectMapper mapper = new ObjectMapper();
		eqpList = mapper.readValue(param.get("eqpList").toString(), new TypeReference<ArrayList<HashMap<String,Object>>>() {} );
		HashMap<String,Object> chkMap = exqueryService.selectOne("nse.pms.basic.sqlProcess.selectCheckDataFormat", param);

		
		
		for(int i=0; i<eqpList.size(); i++) {
			List<HashMap<String,Object>> resultAlarmStatsList = new ArrayList<HashMap<String,Object>>();
			HashMap<String,Object> resultMap = new HashMap<String,Object>();
			
			
		    eqpList.get(i).put("ssUserLang", param.get("ssUserLang"));
		    eqpList.get(i).put("fromDate", param.get("fromDate"));
		    eqpList.get(i).put("toDate", param.get("toDate"));
		    eqpList.get(i).put("searchStr1", eqpList.get(i).get("code"));
		    eqpList.get(i).put("alarmCode", param.get("alarmCode"));
		    eqpList.get(i).put("alarmLevel", param.get("alarmLevel"));
		    eqpList.get(i).put("searchType", param.get("searchType"));
		    
	    	resultAlarmStatsList = exqueryService.selectList("nse.pms.equipment.sqlAlarm.selectEquipmentAlarmsSats", eqpList.get(i));
		
			resultMap.put("chartData", resultAlarmStatsList.size()>0 ? resultAlarmStatsList : new HashMap<String,Object>());
			resultMap.put("eqpCode",eqpList.get(i).get("code"));
			resultMap.put("eqpName",eqpList.get(i).get("name"));
			resultMap.put("startStr", chkMap.get("startTimeStr"));
			resultMap.put("endStr", chkMap.get("endTimeStr"));
			
			resultList.add(resultMap);
		}
		
		
		
		return resultList;		
	}
	
	/**
	 * 설비 알람 통계
	 * @param
	 * @return List<HashMap<String, Object>>
	 **/
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String,Object>> selectEquipmentAlarmStatsList(HashMap<String,Object> param) throws Exception{
		List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
		List<HashMap<String,Object>> dataList = new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		
		
		// 1.일자별 알람 통계
		dataList = selectEquipmentAlarmsSatsByDate(param);
		resultMap.put("chartData", dataList.size()>0 ? dataList : "");
		resultList.add(resultMap);
		
		
		// 2. 설비별 알람 통계
		resultMap = new HashMap<String,Object>();
		dataList = selectAlarmsSats(param);
		
		resultMap.put("chartData", dataList.size()>0 ? dataList : "");				 // 설비별 알람 통계
		resultList.add(resultMap);
		
		
		// 3. H레벨 통계
		resultMap = new HashMap<String,Object>();
		param.put("alarmLevel", "H");
		dataList = selectAlarmsSats(param);
		resultMap.put("chartData", dataList.size()>0 ? dataList : ""); // 일자별 알람 통계
		resultList.add(resultMap);
		
		// 4. L레벨 통계
		resultMap = new HashMap<String,Object>();
		param.put("alarmLevel", "M");
		dataList = selectAlarmsSats(param);
		resultMap.put("chartData", dataList.size()>0 ? dataList : ""); // 일자별 알람 통계
		resultList.add(resultMap);
		
		
		return resultList;		
	}
	
	
	
	/**
	 * 설비-알람 통계
	 * @param
	 * @return List<HashMap<String, Object>>
	 **/
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String,Object>> selectAlarmsSats(HashMap<String,Object> param){
		List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
		resultList = exqueryService.selectList("nse.pms.equipment.sqlAlarm.selectAlarmsSats", param);
		return resultList;		
	}
	
	/**
	 * 알람 일자별 통계
	 * @param
	 * @return List<HashMap<String, Object>>
	 **/
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String,Object>> selectEquipmentAlarmsSatsByDate(HashMap<String,Object> param){
		List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
		resultList = exqueryService.selectList("nse.pms.equipment.sqlAlarm.selectEquipmentAlarmsSatsByDate", param);
		return resultList;		
	}

	
	
}
