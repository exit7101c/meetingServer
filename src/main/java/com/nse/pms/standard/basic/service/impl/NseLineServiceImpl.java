package com.nse.pms.standard.basic.service.impl;

import com.nse.pms.standard.basic.service.NseLineService;
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

@Service("NseLineService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class NseLineServiceImpl implements NseLineService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;

	@Autowired
	private ConstraintService constraintService;
	
	/**
	* Line 정보 조회
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public List<HashMap<String, Object>> selectLineList(HashMap<String, Object> param){

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		resultList = exqueryService.selectList("nse.pms.basic.sqlLine.selectLineList", param);

		return resultList;
	}
	
	/**
	* Line 정보 단건 조회
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> selectLineOne(HashMap<String, Object> param){

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = exqueryService.selectOne("nse.pms.basic.sqlLine.selectLineOne", param);
		return resultMap;
	}
	
	/**
	* Line 정보 등록
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> insertLine(HashMap<String, Object> param) throws Exception {
			
		//constraint check
		// 수정해야됨
		String pk = "{'colName':'LINE_CD','colValue':'"+param.get("lineCd")+"'}";
		String con = "["+"{'tableName':'COM_LINE', 'pkList':["+pk+"], 'msgCode':'중복되었습니다.'}"+"]";
		String consResult = constraintService.checkExistsV2(con.replaceAll("'", "\""));
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultMsg", consResult);
		
		boolean isExist = false;
		
		if(consResult.equals("OK")){
			exqueryService.insert("nse.pms.basic.sqlLine.insertLine", param);
			isExist = true;
		}
		resultMap.put("isExist", isExist);
		return resultMap;
	}

	/**
	* Line 정보 수정
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> updateLine(HashMap<String, Object> param){
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		exqueryService.update("nse.pms.basic.sqlLine.updateLine", param);
		return resultMap;
		
	}
	
	/**
	* Line 정보 삭제
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> deleteLine(HashMap<String, Object> param){
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		exqueryService.update("nse.pms.basic.sqlLine.deleteLine", param);
		return resultMap;
		
	}
	
	/**
	* Line 정보 조회(공통)
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public List<HashMap<String, Object>> selectComLineList(HashMap<String, Object> param){

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		resultList = exqueryService.selectList("nse.pms.basic.sqlLine.selectComLineList", param);

		return resultList;
	}
	
	/**
	* Line 정보 조회(Api)
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public List<HashMap<String, Object>> selectApiLineList(HashMap<String, Object> param){

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		resultList = exqueryService.selectList("nse.pms.basic.sqlLine.selectApiLineList", param);

		return resultList;
	}	
	
}
