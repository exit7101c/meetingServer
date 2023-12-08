package com.nse.pms.standard.system.service.impl;

import com.nse.pms.standard.common.service.ConstraintService;
import com.nse.pms.standard.system.service.NseLogService;
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

@Service("NseLogService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class NseLogServiceImpl implements NseLogService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;

	@Autowired
	private ConstraintService constraintService;

	/**
	 * 리스트 조회 (메뉴활용도)
	 * 
	 * @param
	 * @return
	 **/
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>> selectLogMenuList(HashMap<String, Object> param) throws Exception {

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		if (param.get("searchType").equals("A")) {
			resultList = exqueryService.selectList("nse.pms.system.sqlLog.selectLogMenuListA", param);
		} else {
			resultList = exqueryService.selectList("nse.pms.system.sqlLog.selectLogMenuListB", param);
		}

		return resultList;
	}

	/**
	 * USER 로그인 이력 리스트 조회
	 * 
	 * @param
	 * @return
	 **/
	// @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	// public List<HashMap<String, Object>> selectLogUserAccess(HashMap<String,
	// Object> param) throws Exception {

	// List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String,
	// Object>>();

	// resultList =
	// exqueryService.selectList("nse.pms.system.sqlLog.selectLogUserAccess",
	// param);

	// return resultList;
	// }

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> selectLogUserAccess(HashMap<String, Object> param) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = exqueryService.selectPagingList("nse.pms.system.sqlLog.selectLogUserAccess", param,
				Integer.parseInt(param.get("pageNo").toString()), Integer.parseInt(param.get("pageSize").toString()));
		return resultMap;
	}

	/**
	 * 이벤트 로그 조회
	 * 
	 * @param
	 * @return
	 **/
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>> selectLogErrorHis(HashMap<String, Object> param) throws Exception {

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		resultList = exqueryService.selectList("nse.pms.system.sqlLog.selectLogErrorHis", param);
		return resultList;
	}

	/**
	 * 한건 조회 (다국어-화면용어)
	 * 
	 * @param
	 * @return
	 **/
	// @Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	// public HashMap<String, Object> selectLogMenuOne(HashMap<String, Object>
	// param){
	//
	// HashMap<String, Object> resultMap = new HashMap<String, Object>();
	//
	// resultMap =
	// exqueryService.selectOne("nse.pms.system.sqlLog.selectLogMenuOne", param);
	//
	//
	// return resultMap;
	// }
	//
	//
	// /**
	// * 한건을 등록한다. (다국어-화면용어)
	// * @param
	// * @return
	// */
	// @Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	// public HashMap<String, Object> insertLogMenu(HashMap<String, Object> param)
	// throws Exception {
	//
	// //constraint check
	// String pk = "{'colName':'KEY','colValue':'"+param.get("key")+"'}";
	// String con = "["
	// +"{'tableName':'COM_MESSAGE_LABEL', 'pkList':["+pk+"], 'msgCode':'중복되었습니다.'}"
	// +"]";
	// String consResult = constraintService.checkExistsV2(con.replaceAll("'",
	// "\""));
	// HashMap<String, Object> resultMap = new HashMap<>();
	// resultMap.put("resultMsg", consResult);
	//
	// boolean isExist = false;
	// if(consResult.equals("OK")){
	// exqueryService.insert("nse.pms.system.sqlLog.insertLogMenuEn", param);
	// exqueryService.insert("nse.pms.system.sqlLog.insertLogMenuKo", param);
	// exqueryService.insert("nse.pms.system.sqlLog.insertLogMenuCn", param);
	// isExist = true;
	// }
	// resultMap.put("isExist", isExist);
	// return resultMap;
	// }
	//
	//
	// /**
	// * 한건을 수정한다. (다국어-화면용어)
	// * @param
	// * @return
	// */
	// @Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	// public HashMap<String, Object> updateLogMenu(HashMap<String, Object> param){
	//
	// HashMap<String, Object> resultMap = new HashMap<String, Object>();
	//
	// exqueryService.update("nse.pms.system.sqlLog.updateLogMenuEn", param);
	// exqueryService.update("nse.pms.system.sqlLog.updateLogMenuKo", param);
	// exqueryService.update("nse.pms.system.sqlLog.updateLogMenuCn", param);
	//
	// return resultMap;
	// }
	//
	// /**
	// * 한건을 사용하지 않는다. (다국어-화면용어)
	// * @param
	// * @return
	// */
	// @Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	// public HashMap<String, Object> disableLogMenu(HashMap<String, Object> param){
	//
	// HashMap<String, Object> resultMap = new HashMap<String, Object>();
	//
	// exqueryService.update("nse.pms.system.sqlLog.disableLogMenu", param);
	//
	// return resultMap;
	// }

}
