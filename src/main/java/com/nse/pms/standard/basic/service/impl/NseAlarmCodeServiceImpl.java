package com.nse.pms.standard.basic.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nse.pms.standard.common.service.ConstraintService;
import com.nse.pms.standard.basic.service.NseAlarmCodeService;
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
import java.util.Map;

@Service("alarmCodeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class NseAlarmCodeServiceImpl implements NseAlarmCodeService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;

	@Autowired
	private ConstraintService constraintService;

	/**
	 * AlarmCode List 를 조회 한다.
	 * 
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> selectAlarmCodeList(HashMap<String, Object> param) {

        HashMap<String, Object> resultMap = new HashMap<String, Object>();

        resultMap = exqueryService.selectPagingList("nse.pms.basic.sqlAlarmCode.selectAlarmCodeList", param,
                Integer.parseInt(param.get("pageNo").toString()),
                Integer.parseInt(param.get("pageSize").toString()));

		return resultMap;
	}

	/**
	 * @param String startDate, String endDate
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> selectAlarmCodeOne(HashMap<String, Object> param) {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = exqueryService.selectOne("nse.pms.basic.sqlAlarmCode.selectAlarmCodeOne", param);
		return resultMap;
	}

	/**
	 * * @param String startDate, String endDate
	 * 
	 * @returng
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> insertAlarmCode(HashMap<String, Object> param) throws Exception {

		// constraint check
		String pk = "{'colName':'ALARM_CD','colValue':'" + param.get("alarmCd") + "'}";
		String con = "[" + "{'tableName':'COM_ALARM_CODE', 'pkList':[" + pk + "], 'msgCode':'중복되었습니다.'}" + "]";
		String consResult = constraintService.checkExistsV2(con.replaceAll("'", "\""));
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultMsg", consResult);

		boolean isExist = false;
		if (consResult.equals("OK")) {
			exqueryService.insert("nse.pms.basic.sqlAlarmCode.insertAlarmCode", param);
			isExist = true;
		}
		resultMap.put("isExist", isExist);
		return resultMap;
	}

	/**
	 * @param String startDate, String endDate
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> updateAlarmCode(HashMap<String, Object> param) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		exqueryService.selectList("nse.pms.basic.sqlAlarmCode.updateAlarmCode", param);
		return resultMap;

	}

	/**
	 * @param String startDate, String endDate
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> deleteAlarmCode(HashMap<String, Object> param) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		exqueryService.selectList("nse.pms.basic.sqlAlarmCode.deleteAlarmCode", param);
		return resultMap;
	}
	
	/**
	 * STATE CODE List 를 조회 한다.
	 * 
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>> selectStateCodeList(HashMap<String, Object> param) throws Exception{
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		List<HashMap<String,Object>> eqpList = new ArrayList<HashMap<String,Object>>();

		ObjectMapper mapper = new ObjectMapper();
		eqpList = mapper.readValue(param.get("eqpList").toString(), new TypeReference<ArrayList<HashMap<String, Object>>>() {});

		param.put("equipmentList",eqpList);

		resultList = exqueryService.selectList("nse.pms.basic.sqlAlarmCode.selectStateCodeList", param);
		return resultList;
	}

	/**
	 * STATE CODE 를 조회 한다.
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> selectStateCodeOne(HashMap<String, Object> param) {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = exqueryService.selectOne("nse.pms.basic.sqlAlarmCode.selectStateCodeOne", param);
		return resultMap;
	}
	
	/**
	 * STATE CODE를 Insert
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> insertStateCode(HashMap<String, Object> param) throws Exception {

		// constraint check
		String pk = "{'colName':'EQP_CD','colValue':'"+param.get("eqpCd")+"'}, {'colName':'STATE_CD','colValue':'"+param.get("stateCd")+"'}";
		String con = "["
				+"{'tableName':'COM_STATE_CODE', 'pkList':["+pk+"], 'msgCode':'중복되었습니다.'}"
				+"]";
		String consResult = constraintService.checkExistsV2(con.replaceAll("'", "\""));
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultMsg", consResult);

		boolean isExist = false;
		if (consResult.equals("OK")) {
			exqueryService.insert("nse.pms.basic.sqlAlarmCode.insertStateCode", param);
			isExist = true;
		}
		resultMap.put("isExist", isExist);
		return resultMap;
	}

	/**
	 * STATE CODE를 Update
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> updateStateCode(HashMap<String, Object> param) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		exqueryService.selectList("nse.pms.basic.sqlAlarmCode.updateStateCode", param);
		return resultMap;

	}

	/**
	 * STATE CODE를 Delete
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> deleteStateCode(HashMap<String, Object> param) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		exqueryService.selectList("nse.pms.basic.sqlAlarmCode.deleteStateCode", param);
		return resultMap;
	}
	
	
}