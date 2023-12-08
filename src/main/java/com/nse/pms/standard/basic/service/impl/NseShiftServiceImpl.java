package com.nse.pms.standard.basic.service.impl;

import com.nse.pms.standard.basic.service.NseShiftService;
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

@Service("NseShiftService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class NseShiftServiceImpl implements NseShiftService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;

	@Autowired
	private ConstraintService constraintService;
	
	/**
	* Shift 정보 조회
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public List<HashMap<String, Object>> selectShiftList(HashMap<String, Object> param){

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		resultList = exqueryService.selectList("nse.pms.basic.sqlShift.selectShiftList", param);

		return resultList;
	}
	
	/**
	* Shift 정보 단건 조회
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> selectShiftOne(HashMap<String, Object> param){
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = exqueryService.selectOne("nse.pms.basic.sqlShift.selectShiftOne", param);
		return resultMap;
	}
	
	/**
	* Shift 정보 등록
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> insertShift(HashMap<String, Object> param) throws Exception {
			
		//constraint check
		String pk = "{'colName':'Shift_CD','colValue':'"+param.get("ShiftCd")+"'}";
		String con = "["+"{'tableName':'COM_Shift', 'pkList':["+pk+"], 'msgCode':'중복되었습니다.'}"+"]";
		String consResult = constraintService.checkExistsV2(con.replaceAll("'", "\""));
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultMsg", consResult);
		
		boolean isExist = false;
		
		if(consResult.equals("OK")){
			exqueryService.insert("nse.pms.basic.sqlShift.insertShift", param);
			isExist = true;
		}
		resultMap.put("isExist", isExist);
		return resultMap;
	}

	/**
	* Shift 정보 수정
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> updateShift(HashMap<String, Object> param){
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		exqueryService.update("nse.pms.basic.sqlShift.updateShift", param);
		return resultMap;
		
	}
	
	/**
	* Shift 정보 삭제
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> deleteShift(HashMap<String, Object> param){

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		exqueryService.update("nse.pms.basic.sqlShift.deleteShift", param);
		return resultMap;
	}
}
