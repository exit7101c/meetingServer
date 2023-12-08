package com.cronies.smartmes.service.impl;

import com.cronies.smartmes.service.ManagerService;
import com.cronies.smartmes.service.SmsService;
import com.nse.pms.standard.common.service.ConstraintService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import select.spring.exquery.service.ExqueryService;

import java.util.HashMap;
import java.util.UUID;

@Service("managerService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ManagerServiceImpl implements ManagerService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;

	@Autowired
	private ConstraintService constraintService;

	@Autowired
	private SmsService smsService;

	/**
	 * 해당 담당자를 조회한다.
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> selectManagerOne(HashMap<String, Object> param) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        resultMap = exqueryService.selectOne("nse.pms.smartmes.mesManager.selectManagerOne", param);
		return resultMap;
	}

	/**
	 * 담당자를 등록 한다.
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> insertManager(HashMap<String, Object> param) throws Exception {

        HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean isExist = false;
		boolean isExist2 = true;
		String managerKey = "";
		String msg = "";
		while(isExist2){
			// managerKey 생성
			managerKey = UUID.randomUUID().toString().replaceAll("-","").substring(0,6);
			// managerKey 중복체크
			if(exqueryService.selectOne("nse.pms.smartmes.mesManager.selectManagerByManagerKey", managerKey) == null){
				// 담당자 등록
				param.put("managerKey", managerKey);
				if(exqueryService.insert("nse.pms.smartmes.mesManager.insertManager", param) == 1){
					isExist = true;
					HashMap<String, Object> msgMap = new HashMap<String, Object>();
					msgMap.put("managerKey", managerKey);
					msgMap.put("phonNumber", param.get("phone"));

					HashMap<String, Object> returnMap = new HashMap<String, Object>();
					returnMap = smsService.sendManagerProdReg(msgMap);
					if((boolean) returnMap.get("isExist")){
						isExist = true;
						msg = "발송성공";
					} else {
						isExist = false;
						msg = (String) returnMap.get("msg");
					}
				}
				isExist2 = false;
			}
		}

		resultMap.put("isExist", isExist);
		resultMap.put("msg", msg);
		return resultMap;
	}

	/**
	 * 해당 담당자를 수정한다.
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> updateManager(HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		exqueryService.selectOne("nse.pms.smartmes.mesManager.updateManager", param);
		return resultMap;
	}

	/**
	 * 해당 담당자를 삭제한다.
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> deleteManager(HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		exqueryService.selectOne("nse.pms.smartmes.mesManager.deleteManager", param);
		return resultMap;
	}

}