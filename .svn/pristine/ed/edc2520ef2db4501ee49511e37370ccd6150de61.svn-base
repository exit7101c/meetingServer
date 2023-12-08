package com.nse.pms.standard.basic.service.impl;

import com.nse.pms.standard.basic.service.NseWcService;
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



@Service("wcService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class NseWcServiceImpl implements NseWcService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;

	@Autowired
	private ConstraintService constraintService;


	/**
	 * Wc List 를 조회 한다.
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public List<HashMap<String, Object>> selectWcList(HashMap<String, Object> param){
		
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		resultList = exqueryService.selectList("nse.pms.basic.sqlWc.selectWcList", param);
		return resultList;
		
	}

	/**
	 * @param 
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> selectWcOne(HashMap<String, Object> param){

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = exqueryService.selectOne("nse.pms.basic.sqlWc.selectWcOne", param);
		return resultMap;
	}
	
	/**
	 * @param 
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> insertWc(HashMap<String, Object> param) throws Exception {

		//constraint check
		String pk = "{'colName':'WORKCENTER_CD','colValue':'"+param.get("workcenterCd")+"'}";
		String con = "["+"{'tableName':'COM_WORKCENTER', 'pkList':["+pk+"], 'msgCode':'중복되었습니다.'}"+"]";
		String consResult = constraintService.checkExistsV2(con.replaceAll("'", "\""));
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultMsg", consResult);
		
		boolean isExist = false;
		if(consResult.equals("OK")){
			exqueryService.insert("nse.pms.basic.sqlWc.insertWc", param);
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
	public HashMap<String, Object> updateWc(HashMap<String, Object> param){
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		exqueryService.selectList("nse.pms.basic.sqlWc.updateWc", param);
		return resultMap;
	}
	
	/**
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> deleteWc(HashMap<String, Object> param){
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		exqueryService.selectList("nse.pms.basic.sqlWc.deleteWc", param);
		return resultMap;
		
	}
}
