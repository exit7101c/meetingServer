package com.nse.pms.standard.basic.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nse.pms.standard.basic.service.NseProductGroupService;
import com.nse.pms.standard.common.service.ConstraintService;

import select.spring.exquery.service.ExqueryService;



@Service("productGroupService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class NseProductGroupServiceImpl implements NseProductGroupService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;
	
	@Autowired
	private ConstraintService constraintService;
	
	/**
	* ProductGroup 정보 조회
	* @param List<HashMap<String, Object>>
	* @return HashMap<String, Object>
	**/	
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public List<HashMap<String, Object>> selectProductGroupList(HashMap<String, Object> param){
		
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		resultList = exqueryService.selectList("nse.pms.basic.sqlProductGroup.selectProductGroupList", param);
		return resultList;		
	}
	
	
	/**
	* ProductGroup 정보 단건 조회
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> selectProductGroupOne(HashMap<String, Object> param){

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = exqueryService.selectOne("nse.pms.basic.sqlProductGroup.selectProductGroupOne", param);
		return resultMap;
	}
	
	/**
	* Line 정보 등록
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> insertProductGroup(HashMap<String, Object> param) throws Exception {
			
		//constraint check
		// 수정해야됨
		String pk = "{'colName':'PROD_GROUP_CD','colValue':'"+param.get("prodGroupCd")+"'}";
		String con = "["+"{'tableName':'COM_PRODUCT_GROUP', 'pkList':["+pk+"], 'msgCode':'중복되었습니다.'}"+"]";
		String consResult = constraintService.checkExistsV2(con.replaceAll("'", "\""));
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultMsg", consResult);
		
		boolean isExist = false;
		
		if(consResult.equals("OK")){
			exqueryService.insert("nse.pms.basic.sqlProductGroup.insertProductGroup", param);
			isExist = true;
		}
		resultMap.put("isExist", isExist);
		return resultMap;
	}

	/**
	* ProductGroup 정보 수정
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> updateProductGroup(HashMap<String, Object> param){
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		exqueryService.update("nse.pms.basic.sqlProductGroup.updateProductGroup", param);
		return resultMap;
		
	}
	
	/**
	* ProductGroup 정보 삭제
	* @param HashMap<String, Object>
	* @return HashMap<String, Object>
	**/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> deleteProductGroup(HashMap<String, Object> param){
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		exqueryService.update("nse.pms.basic.sqlProductGroup.deleteProductGroup", param);
		return resultMap;
		
	}
}
