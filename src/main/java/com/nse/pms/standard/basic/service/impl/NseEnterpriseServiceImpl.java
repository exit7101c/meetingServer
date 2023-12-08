package com.nse.pms.standard.basic.service.impl;

import com.nse.pms.standard.basic.service.NseEnterpriseService;
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



@Service("enterpriseService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class NseEnterpriseServiceImpl implements NseEnterpriseService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;

	@Autowired
	private ConstraintService constraintService;


	/**
	 * Enterprise List 를 조회 한다.
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public List<HashMap<String, Object>> selectEnterpriseList(HashMap<String, Object> param){
		
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		resultList = exqueryService.selectList("nse.pms.basic.sqlEnterprise.selectEnterpriseList", param);
		return resultList;
		
	}

	/**
	 * @param 
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> selectEnterpriseOne(HashMap<String, Object> param){

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = exqueryService.selectOne("nse.pms.basic.sqlEnterprise.selectEnterpriseOne", param);
		return resultMap;
	}
	
	/**
	 * @param 
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> insertEnterprise(HashMap<String, Object> param) throws Exception {

		//constraint check
		String pk = "{'colName':'ENTERPRISE_CD','colValue':'"+param.get("enterpriseCd")+"'}";
		String con = "["+"{'tableName':'COM_ENTERPRISE', 'pkList':["+pk+"], 'msgCode':'중복되었습니다.'}"+"]";
		String consResult = constraintService.checkExistsV2(con.replaceAll("'", "\""));
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultMsg", consResult);
		
		boolean isExist = false;
		if(consResult.equals("OK")){
			exqueryService.insert("nse.pms.basic.sqlEnterprise.insertEnterprise", param);
			isExist = true;
		}
		resultMap.put("isExist", isExist);
		return resultMap;
	}

	/**
	 * @param 
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> updateEnterprise(HashMap<String, Object> param){
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		exqueryService.selectList("nse.pms.basic.sqlEnterprise.updateEnterprise", param);
		return resultMap;
	}
	
	/**
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> deleteEnterprise(HashMap<String, Object> param){
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		exqueryService.selectList("nse.pms.basic.sqlEnterprise.deleteEnterprise", param);
		return resultMap;
		
	}
	
	/**
	 * Enterprise List 를 조회 한다.(공통)
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public List<HashMap<String, Object>> selectComEnterpriseList(HashMap<String, Object> param){
		
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		resultList = exqueryService.selectList("nse.pms.basic.sqlEnterprise.selectComEnterpriseList", param);
		return resultList;
		
	}
}
