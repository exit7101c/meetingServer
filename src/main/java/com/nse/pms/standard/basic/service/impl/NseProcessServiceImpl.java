package com.nse.pms.standard.basic.service.impl;

import com.nse.pms.standard.basic.service.NseProcessService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nse.pms.standard.basic.service.NseLineService;
import com.nse.pms.standard.common.service.ConstraintService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import select.spring.exquery.service.ExqueryService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



@Service("processService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class NseProcessServiceImpl implements NseProcessService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;
	
	@Autowired
	private NseLineService NseLineService;
	
	@Autowired
	private ConstraintService constraintService;
	/**
	* 라인 - 공정 - 설비 tree List
	* @param
	* @return 노윤경
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public List<HashMap<String, Object>> selectApiAreaLineProcEqp(HashMap<String ,Object> param){
		
		
		// 1. 구역에 따른 라인 리스트 
		List<HashMap<String,Object>> LineList =  NseLineService.selectApiLineList(param);
		
		if(LineList.size() > 0) {
			
			for (HashMap<String, Object> LineMap : LineList) {
				List<HashMap<String,Object>> ProcessList = new ArrayList<HashMap<String, Object>>();		
				LineMap.put("ssUserLang", param.get("ssUserLang"));
				
				ProcessList = selectApiLineProcList(LineMap);	 	   // 해당 라인의 공정 리스트
				
				if(ProcessList.size() > 0) {
					LineMap.put("children", ProcessList);
					for(HashMap<String, Object> procMap : ProcessList) {			
						procMap.put("ssUserLang", param.get("ssUserLang"));
						List<HashMap<String,Object>> EquipmentList = selectApiLineProcEqpList(procMap);	// 해당 공정의 설비 리스트
						if(EquipmentList.size() > 0) {
							procMap.put("children", EquipmentList);		//조회된 설비를 공정에 담는다.
						}
					}
				}
				
			} 
		}
		
		return LineList;
	}

	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public List<HashMap<String, Object>> selectApiLineProcList(HashMap<String, Object> param){
		
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		resultList = exqueryService.selectList("nse.pms.basic.sqlProcess.selectApiLineProcList", param);
		return resultList;
		
	}

	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public List<HashMap<String, Object>> selectApiLineProcEqpList(HashMap<String, Object> param){
		
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		resultList = exqueryService.selectList("nse.pms.basic.sqlProcess.selectApiLineProcEqpList", param);
		return resultList;
		
	}
	
	/**
	* Process 정보 조회
	* @param
	* @return HashMap<String, Object>
	**/	
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public List<HashMap<String, Object>> selectProcessList(HashMap<String, Object> param){
		
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		resultList = exqueryService.selectList("nse.pms.basic.sqlProcess.selectProcessList", param);
		return resultList;		
	}
	
	
	/**
	* Process 정보 단건 조회
	* @param
	* @return HashMap<String, Object>
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> selectProcessOne(HashMap<String, Object> param){

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = exqueryService.selectOne("nse.pms.basic.sqlProcess.selectProcessOne", param);
		return resultMap;
	}
	
	/**
	* Process type 정보 조회
	* @param
	* @return HashMap<String, Object>
	**/	
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public List<HashMap<String, Object>> selectProcessTypeList(HashMap<String, Object> param){
		
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		resultList = exqueryService.selectList("nse.pms.basic.sqlProcess.selectProcessTypeList", param);
		return resultList;		
	}
	
	/**
	* Line 정보 등록
	* @param
	* @return HashMap<String, Object>
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> insertProcess(HashMap<String, Object> param) throws Exception {
			
		//constraint check
		// 수정해야됨
		String pk = "{'colName':'PROC_CD','colValue':'"+param.get("procCd")+"'}";
		String con = "["+"{'tableName':'COM_PROCESS', 'pkList':["+pk+"], 'msgCode':'중복되었습니다.'}"+"]";
		String consResult = constraintService.checkExistsV2(con.replaceAll("'", "\""));
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultMsg", consResult);
		
		boolean isExist = false;
		
		if(consResult.equals("OK")){
			exqueryService.insert("nse.pms.basic.sqlProcess.insertProcess", param);
			isExist = true;
		}
		resultMap.put("isExist", isExist);
		return resultMap;
	}

	/**
	* Process 정보 수정
	* @param
	* @return HashMap<String, Object>
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> updateProcess(HashMap<String, Object> param){
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		exqueryService.update("nse.pms.basic.sqlProcess.updateProcess", param);
		return resultMap;
		
	}
	
	/**
	* Process 정보 삭제
	* @param
	* @return HashMap<String, Object>
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> deleteProcess(HashMap<String, Object> param){
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		exqueryService.update("nse.pms.basic.sqlProcess.deleteProcess", param);
		return resultMap;
		
	}
	
	
	/**
	* 라인 - 공정 -  tree List
	* @param 
	* @return 
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public List<HashMap<String, Object>> selectAreaLineProcList(HashMap<String ,Object> param){
		
		// 1. 구역에 따른 라인 리스트 
		List<HashMap<String,Object>> LineList =  NseLineService.selectApiLineList(param);
		
		if(LineList.size() > 0) {
			for (HashMap<String, Object> LineMap : LineList) {
				List<HashMap<String,Object>> ProcessList = new ArrayList<HashMap<String, Object>>();		
				LineMap.put("ssUserLang", param.get("ssUserLang"));
				LineMap.put("searchProc", param.containsKey("searchProc") ? param.get("searchProc") : "");
				
				ProcessList = selectApiLineProcList(LineMap);	 	   // 해당 라인의 공정 리스트
				LineMap.put("children", ProcessList);		//조회된 설비를 공정에 담는다.
			} 
		}
		
		return LineList;
	}
	
	/**
	* 공정-설비 Mapping
	* @param 
	* @return 
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String,Object> updateProcEqpMapping(HashMap<String, Object> param) throws Exception {
		
		HashMap<String,Object> resultHashMap = new HashMap<String,Object>();
		List<HashMap<String,Object>> dataList = new ArrayList<HashMap<String,Object>>();

		ObjectMapper mapper = new ObjectMapper();
		dataList =  mapper.readValue(param.get("data").toString(), new TypeReference<ArrayList<HashMap<String, Object>>>() {});
		
		// 1. 삭체 처리
		exqueryService.delete("nse.pms.basic.sqlProcess.deleteProcEqpMapping", param);
		
		// 2. Checked 데이터 INSERT						
		if(dataList.size()>0) {
			for(int i=0; i<dataList.size(); i++) {
				dataList.get(i).put("procCd", param.get("procCd"));
				dataList.get(i).put("ssUserId", param.get("ssUserId"));
				exqueryService.insert("nse.pms.basic.sqlProcess.insertProcEqpMapping", dataList.get(i));
			}
		}
		resultHashMap.put("isUpdate", true);
		return resultHashMap;
	}
	

	/**
	* 제품-상하한 Mapping
	* @param
	* @return
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String,Object> updateProdSpecMapping(HashMap<String, Object> param) throws Exception {

		HashMap<String,Object> resultHashMap = new HashMap<String,Object>();
		List<HashMap<String,Object>> dataList = new ArrayList<HashMap<String,Object>>();

		ObjectMapper mapper = new ObjectMapper();
		dataList =  mapper.readValue(param.get("data").toString(), new TypeReference<ArrayList<HashMap<String, Object>>>() {});

		// 1. 삭체 처리
		exqueryService.delete("nse.pms.basic.sqlProcess.deleteProdClEqpMapping", param);

		// 2. Checked 데이터 INSERT
		if(dataList.size()>0) {
			for(int i=0; i<dataList.size(); i++) {
				dataList.get(i).put("prodCd", param.get("prodCd"));
				dataList.get(i).put("ssUserId", param.get("ssUserId"));
				exqueryService.insert("nse.pms.basic.sqlProcess.insertProdSpecMapping", dataList.get(i));
				//로그저장
				exqueryService.insert("nse.pms.basic.sqlProcess.insertProdSpecMappingLog", dataList.get(i));
			}
		}
		resultHashMap.put("isUpdate", true);
		return resultHashMap;
	}


	/**
	* 공정-설비가동차트
	* @param
	* @return
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String,Object> selectEqpStateHisOnChart(HashMap<String, Object> param) throws Exception {

		HashMap<String,Object> resultHashMap = new HashMap<String,Object>();
		List<HashMap<String,Object>> eqpList = new ArrayList<HashMap<String,Object>>();

		ObjectMapper mapper = new ObjectMapper();
		eqpList =  mapper.readValue(param.get("eqpList").toString(), new TypeReference<ArrayList<HashMap<String, Object>>>() {});

		HashMap<String,Object> chkMap = exqueryService.selectOne("nse.pms.basic.sqlProcess.selectCheckDataFormat", param);

		List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
		List<HashMap<String,Object>> resultAllList = new ArrayList<HashMap<String,Object>>();

		//
		if(eqpList.size()>0) {
			for(int i=0; i<eqpList.size(); i++) {
				HashMap<String,Object> eqpMap = new HashMap<String,Object>();
				eqpMap.put("eqpCd", eqpList.get(i).get("eqpCd"));
				eqpMap.put("eqpNm", eqpList.get(i).get("eqpNm"));
				eqpMap.put("fromDate", param.get("fromDate"));
				eqpMap.put("toDate", param.get("toDate"));
				eqpMap.put("idx", i);
				eqpMap.put("ssUserLang", param.get("ssUserLang"));
				List<HashMap<String,Object>> runList = new ArrayList<HashMap<String,Object>>();
				runList = exqueryService.selectList("nse.pms.basic.sqlProcess.selectEqpRunStateList", eqpMap);
				resultAllList.addAll(runList);
				resultList.add(eqpMap);
			}
		}
		resultHashMap.put("result", resultList);
		resultHashMap.put("resultAll", resultAllList);
		resultHashMap.put("startStr", chkMap.get("startTimeStr"));
		resultHashMap.put("endStr", chkMap.get("endTimeStr"));
		return resultHashMap;
	}

	/**
	* MES Process 정보 조회
	* @param
	* @return HashMap<String, Object>
	**/	
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public List<HashMap<String, Object>> selectMESProcessList(HashMap<String, Object> param){
		
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		resultList = exqueryService.selectList("nse.pms.basic.sqlProcess.selectMESProcessList", param);
		return resultList;		
	}
}
