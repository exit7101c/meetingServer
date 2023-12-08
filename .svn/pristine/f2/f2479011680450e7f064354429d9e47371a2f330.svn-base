package com.nse.pms.standard.basic.service.impl;

import com.nse.pms.standard.basic.service.NsePlantService;
import com.nse.pms.standard.common.service.ConstraintService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import select.spring.exquery.service.ExqueryService;

@Service("NsePlantService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class NsePlantServiceImpl implements NsePlantService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;

	@Autowired
	private ConstraintService constraintService;
	
	/**
	* Plant 정보 조회
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public List<HashMap<String, Object>> selectPlantList(HashMap<String, Object> param){

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		resultList = exqueryService.selectList("nse.pms.basic.sqlPlant.selectPlantList", param);

		return resultList;
	}
	
	/**
	* Plant 정보 단건 조회
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> selectPlantOne(HashMap<String, Object> param){
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = exqueryService.selectOne("nse.pms.basic.sqlPlant.selectPlantOne", param);
		return resultMap;
	}
	
	/**
	* Plant 정보 등록
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> insertPlant(HashMap<String, Object> param) throws Exception {
			
		//constraint check
		// 수정해야됨
		String pk = "{'colName':'PLANT_CD','colValue':'"+param.get("plantCd")+"'}";
		String con = "["+"{'tableName':'COM_PLANT', 'pkList':["+pk+"], 'msgCode':'중복되었습니다.'}"+"]";
		String consResult = constraintService.checkExistsV2(con.replaceAll("'", "\""));
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultMsg", consResult);
		
		boolean isExist = false;
		
		if(consResult.equals("OK")){
			exqueryService.insert("nse.pms.basic.sqlPlant.insertPlant", param);
			isExist = true;
		}
		resultMap.put("isExist", isExist);
		return resultMap;
	}

	/**
	* Site 정보 수정
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> updatePlant(HashMap<String, Object> param){
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		exqueryService.update("nse.pms.basic.sqlPlant.updatePlant", param);
		return resultMap;
		
	}
	
	/**
	* Plant 정보 삭제
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> deletePlant(HashMap<String, Object> param){
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		exqueryService.update("nse.pms.basic.sqlPlant.deletePlant", param);
		return resultMap;
	}

	/**
	* Plant 정보 조회(공통)
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public List<HashMap<String, Object>> selectComPlantList(HashMap<String, Object> param){

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		resultList = exqueryService.selectList("nse.pms.basic.sqlPlant.selectComPlantList", param);

		return resultList;
	}
}
