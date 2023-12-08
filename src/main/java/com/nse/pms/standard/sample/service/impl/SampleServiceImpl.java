package com.nse.pms.standard.sample.service.impl;

import com.nse.pms.standard.common.service.ConstraintService;
import com.nse.pms.standard.sample.service.SampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import select.spring.exquery.service.ExqueryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("sampleService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SampleServiceImpl implements SampleService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;

	@Autowired
	private ConstraintService constraintService;


	/**
	 * 	 * @param String startDate, String endDate
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public List<HashMap<String, Object>> selectUserList(HashMap<String, Object> param){

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		resultList = exqueryService.selectList("nse.pmsCommon.selectUserList", param);

		return resultList;
	}

	/**
	 * 	 * @param String startDate, String endDate
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> selectUserOne(HashMap<String, Object> param){

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		resultMap = exqueryService.selectOne("nse.pmsCommon.selectUserOne", param);


		return resultMap;
	}

	/**
	 * 	 * @param String startDate, String endDate
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> insertUser(HashMap<String, Object> param) throws Exception {

		//constraint check
		String pk = "{'colName':'USER_ID','colValue':'"+param.get("userId")+"'}";
		String con = "["
				+"{'tableName':'COM_USER', 'pkList':["+pk+"], 'msgCode':'중복되었습니다.'}"
				+"]";
		String consResult = constraintService.checkExistsV2(con.replaceAll("'", "\""));
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultMsg", consResult);
		boolean isExist = false;
		if(consResult.equals("OK")){
			exqueryService.insert("nse.pmsCommon.insertUser", param);
			isExist = true;
		}
		resultMap.put("isExist", isExist);
		return resultMap;
	}

	/**
	 * 	 * @param String startDate, String endDate
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> updateUser(HashMap<String, Object> param){

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		exqueryService.selectList("nse.pmsCommon.updateUser", param);

		return resultMap;
	}


	/**
	 * 	 * @param String startDate, String endDate
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public List<HashMap<String, Object>> selectEnterpriseList(HashMap<String, Object> param){

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		resultList = exqueryService.selectList("nse.sqlSample.selectEnterpriseList", param);

		return resultList;
	}

	/**
	 * 	 * @param String startDate, String endDate
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> selectEnterpriseOne(HashMap<String, Object> param){

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		resultMap = exqueryService.selectOne("nse.sqlSample.selectEnterpriseOne", param);


		return resultMap;
	}

	/**
	 * 	 * @param String startDate, String endDate
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> insertEnterprise(HashMap<String, Object> param) throws Exception {

		//constraint check
		String pk = "{'colName':'ENTERPRISE_CD','colValue':'"+param.get("enterpriseCd")+"'}";
		String con = "["
				+"{'tableName':'COM_ENTERPRISE', 'pkList':["+pk+"], 'msgCode':'중복되었습니다.'}"
				+"]";
		String consResult = constraintService.checkExistsV2(con.replaceAll("'", "\""));
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultMsg", consResult);
		boolean isExist = false;
		if(consResult.equals("OK")){
			exqueryService.insert("nse.sqlSample.insertEnterprise", param);
			isExist = true;
		}
		resultMap.put("isExist", isExist);
		return resultMap;
	}

	/**
	 * 	 * @param String startDate, String endDate
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> updateEnterprise(HashMap<String, Object> param){

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		exqueryService.update("nse.sqlSample.updateEnterprise", param);

		return resultMap;
	}

	/**
	 * 	 * @param String startDate, String endDate
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> disableEnterprise(HashMap<String, Object> param){

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		exqueryService.update("nse.sqlSample.disableEnterprise", param);

		return resultMap;
	}

}
