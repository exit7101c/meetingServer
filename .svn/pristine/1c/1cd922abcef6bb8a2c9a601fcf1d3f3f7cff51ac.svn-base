package com.nse.pms.standard.workorder.service.impl;

import com.nse.pms.standard.common.service.ConstraintService;
import com.nse.pms.standard.workorder.service.NseWorkOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import select.spring.exquery.service.ExqueryService;

import javax.servlet.http.HttpSession;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("NseWorkOrderService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class NseWorkOrderServiceImpl implements NseWorkOrderService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;

	@Autowired
	private ConstraintService constraintService;


	/**
	 * 작업지시를 조회한다.
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public List<HashMap<String, Object>> selectWorkOrderList(HashMap<String, Object> param){

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		resultList = exqueryService.selectList("nse.pms.workOrder.sqlWorkOrder.selectWorkOrderList", param);

		return resultList;
	}

	/**
	 * @param String startDate, String endDate
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> selectWorkOrderOne(HashMap<String, Object> param) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		resultMap = exqueryService.selectOne("nse.pms.workOrder.sqlWorkOrder.selectWorkOrderOne", param);

		return resultMap;
	}

	/**
	 * MES 작업지시 수신.
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> executeIFRWorkOrder(HashMap<String, Object> param) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		resultMap = exqueryService.selectOne("nse.pms.workOrder.sqlWorkOrder.executeIFRWorkOrder", param);
		
		return resultMap;
	}

	/**
	 * * @param String startDate, String endDate
	 * 
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> insertWorkOrder(HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<>();
		HashMap<String, Object> validWo = new HashMap<String, Object>();
		HashMap<String, Object> resultWo = new HashMap<String, Object>();
		HashMap<String, Object> resultLot = new HashMap<String, Object>();

		String pk;
		String con;
		String consResult;

		boolean isExist = false;

		int insertCnt;

		//Valaidate whether WO exists
		validWo = exqueryService.selectOne("nse.pms.workOrder.sqlWorkOrder.validWorkOrder", param);

		if(validWo != null){
			isExist = false;
			resultMap.put("isExist", isExist);
			resultMap.put("resultMsg", "C001");

			return resultMap;
		}
		else{
			//Get the new order no
			resultWo = exqueryService.selectOne("nse.pms.workOrder.sqlWorkOrder.selectNewWorkOrder", param);
			param.put("woId", resultWo.get("woId"));

			// constraint check
			pk = "{'colName':'WO_ID','colValue':'" + param.get("woId") + "'}";
			con = "[" + "{'tableName':'COM_WORKORDER', 'pkList':[" + pk + "], 'msgCode':'중복되었습니다.'}" + "]";
			consResult = constraintService.checkExistsV2(con.replaceAll("'", "\""));

			resultMap.put("resultMsg", consResult);

			if (consResult.equals("OK")) {
				insertCnt = exqueryService.insert("nse.pms.workOrder.sqlWorkOrder.insertWorkOrder", param);

				if (insertCnt == 1){
					exqueryService.insert("nse.pms.workOrder.sqlWorkOrder.insertWorkOrderHis", param);

					//Get the new lot
					resultLot = exqueryService.selectOne("nse.pms.workOrder.sqlWorkOrder.selectNewLot", param);
					param.put("lotId", resultLot.get("lotId"));

					// constraint check
					pk = "{'colName':'LOT_ID','colValue':'" + param.get("lotId") + "'}";
					con = "[" + "{'tableName':'COM_PRODUCT_LOT', 'pkList':[" + pk + "], 'msgCode':'중복되었습니다.'}" + "]";
					consResult = constraintService.checkExistsV2(con.replaceAll("'", "\""));

					resultMap.put("resultMsg", consResult);

					if (consResult.equals("OK")) {
						exqueryService.insert("nse.pms.workOrder.sqlWorkOrder.insertLot", param);
						isExist = true;
					}
				}
			}

			resultMap.put("isExist", isExist);

			return resultMap;
		}
	}

	/**
	 * @param String startDate, String endDate
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> updateWorkOrder(HashMap<String, Object> param) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> validWo = new HashMap<String, Object>();

		int updateCnt;

		if(param.get("btnState").equals("start")){
			//Valaidate whether Active WO exists
			validWo = exqueryService.selectOne("nse.pms.workOrder.sqlWorkOrder.validWorkOrderActive", param);

			if(validWo != null){
				validWo.put("state", "InActive");
				validWo.put("btnState", "end");
				validWo.put("ssUserId", param.get("ssUserId"));

				updateCnt = exqueryService.update("nse.pms.workOrder.sqlWorkOrder.updateWorkOrder", validWo);

				if(updateCnt == 1){
					exqueryService.insert("nse.pms.workOrder.sqlWorkOrder.insertWorkOrderHis", param);
				}
			}

			param.put("state", "Active");
			updateCnt = exqueryService.update("nse.pms.workOrder.sqlWorkOrder.updateWorkOrder", param);

			HashMap<String, Object> chkMap = exqueryService.selectOne("nse.pms.workOrder.sqlWorkOrder.checkWorkOrder", param);
			if(chkMap != null && chkMap.get("procCd").equals("0010")){
				//생산시작 처리
				//라인생산제품 추가
				exqueryService.update("nse.pms.workOrder.sqlWorkOrder.mergeLineProduct", param);
				//히스토리 업데이트
				exqueryService.update("nse.pms.workOrder.sqlWorkOrder.updateHisLineProduct", param);
				exqueryService.insert("nse.pms.workOrder.sqlWorkOrder.insertHisLineProduct", param);
			}

			if(updateCnt == 1){
				exqueryService.insert("nse.pms.workOrder.sqlWorkOrder.insertWorkOrderHis", param);
			}

			return resultMap;
		} else {
			param.put("state", "InActive");
			updateCnt = exqueryService.update("nse.pms.workOrder.sqlWorkOrder.updateWorkOrder", param);

			HashMap<String, Object> chkMap = exqueryService.selectOne("nse.pms.workOrder.sqlWorkOrder.checkWorkOrder", param);
			if(chkMap != null && chkMap.get("procCd").equals("0010")){
				//생산종료 처리
				//라인생산제품 제거
				exqueryService.update("nse.pms.workOrder.sqlWorkOrder.deleteLineProduct", param);
				//히스토리 업데이트
				exqueryService.update("nse.pms.workOrder.sqlWorkOrder.updateHisLineProduct", param);
				exqueryService.insert("nse.pms.workOrder.sqlWorkOrder.insertHisLineProduct", param);
			}

			if(updateCnt == 1){
				exqueryService.insert("nse.pms.workOrder.sqlWorkOrder.insertWorkOrderHis", param);
			}
			return resultMap;
		}
	}

	/**
	 * @param String startDate, String endDate
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> deleteWorkOrder(HashMap<String, Object> param) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		int updateCnt = exqueryService.update("nse.pms.workOrder.sqlWorkOrder.deleteWorkOrder", param);

		if(updateCnt == 1){
			exqueryService.insert("nse.pms.workOrder.sqlWorkOrder.insertWorkOrderHis", param);
			exqueryService.update("nse.pms.workOrder.sqlWorkOrder.deleteLot", param);
		}

		return resultMap;
	}
}
