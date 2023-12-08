package com.cronies.smartmes.service.impl;

import com.cronies.smartmes.service.ProcService;
import com.cronies.smartmes.service.ProdRecordService;
import com.nse.pms.standard.common.service.ConstraintService;
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

@Service("procService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ProcServiceImpl implements ProcService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;

	@Autowired
	private ConstraintService constraintService;

	/**
	 *  공정 하나를 조회한다.
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> selectProcOne(HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		//공정 하나를 조회한다.
		resultMap = exqueryService.selectOne("nse.pms.smartmes.mesProcess.selectProcessOne", param);

		return resultMap;
	}

	/**
	 *  공정을 등록한다.
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> insertProcess(HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		//공정을 등록한다.
		exqueryService.insert("nse.pms.smartmes.mesProcess.insertProcess", param);

		//기본설비를 등록한다.
		exqueryService.insert("nse.pms.smartmes.mesEquipment.insertEquipmentByProc", param);

		//기본설비의 기본가동정보를 등록한다.
		//com_eqp_state MERGE,
		exqueryService.update("nse.pms.smartmes.mesManager.mergeEqpStateNew", param);
		//com_eqp_state_his INSERT
		exqueryService.insert("nse.pms.smartmes.mesManager.insertEqpStateHisNew", param);

		return resultMap;
	}

	/**
	 *  공정을 수정한다.
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> updateProcess(HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		//공정을 수정한다.
		exqueryService.update("nse.pms.smartmes.mesProcess.updateProcess", param);
		return resultMap;
	}

	/**
	 *  공정을 삭제한다.
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> deleteProcess(HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		List<HashMap<String, Object>> managerList = new ArrayList<HashMap<String, Object>>();

		// 공정에 해당하는 매니저 리스트를 조회한다.
		managerList = exqueryService.selectList("nse.pms.smartmes.mesManager.selectManagerListByProcCd", param);
		//매니저리스트가 존재한다면
		if(managerList.size() > 0){
			for(int i=0,len=managerList.size(); i < len; i++){
				// 해당 라인 및 공정에 관련된 담당자들 삭제
				HashMap<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("managerId", managerList.get(i).get("managerId"));
				exqueryService.delete("nse.pms.smartmes.mesManager.deleteManager", paramMap);
			}
		}
		// 공정에 연결된 설비 미사용처리
		exqueryService.update("nse.pms.smartmes.mesEquipment.deleteEquipmentByProc", param);
		//공정을 삭제한다.
		exqueryService.update("nse.pms.smartmes.mesProcess.deleteProcess", param);
		return resultMap;
	}



}