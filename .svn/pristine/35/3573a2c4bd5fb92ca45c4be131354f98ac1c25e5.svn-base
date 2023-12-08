package com.cronies.smartmes.service.impl;

import com.cronies.smartmes.service.MobileService;
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

@Service("mobileService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MobileServiceImpl implements MobileService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;

	@Autowired
	private ConstraintService constraintService;

	/**
	 * 담당자를 기준으로 데이터를 조회한다.
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> getBaseInfo(HashMap<String, Object> param) throws Exception {

        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        HashMap<String, Object> managerInfo = new HashMap<String, Object>();
        HashMap<String, Object> enterpriseInfo = new HashMap<String, Object>();
        List<HashMap<String, Object>> lineInfo = new ArrayList<HashMap<String, Object>>();
        List<HashMap<String, Object>> procInfo = new ArrayList<HashMap<String, Object>>();
        List<HashMap<String, Object>> prodInfo = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> runMap = new HashMap<String, Object>();

		boolean isExist = false;
		boolean isRun = false;

		// managerKey 등록되어 있는지 확인
		managerInfo = exqueryService.selectOne("nse.pms.smartmes.mesManager.selectManagerByManagerKey", param);
		// 존재 할 경우
		if(managerInfo != null){
			// 법인 정보 조회
			enterpriseInfo = exqueryService.selectOne("nse.pms.smartmes.mesEnterprise.selectEnterpriseOneByEnterCd", managerInfo.get("enterpriseCd"));
			//라인 리스트 조회
			lineInfo = exqueryService.selectList("nse.pms.smartmes.mesLine.selectLineListByEnterCd", managerInfo.get("enterpriseCd"));
			//공정 리스트 조회
			procInfo = exqueryService.selectList("nse.pms.smartmes.mesProcess.selectProcListByLineCd", managerInfo.get("lineCd"));
			//제품 리스트 조회
			prodInfo = exqueryService.selectList("nse.pms.smartmes.mesProduct.selectProdListByEnterCd", managerInfo.get("enterpriseCd"));
			//설비 가동유무 조회
			runMap = exqueryService.selectOne("nse.pms.smartmes.mesEquipment.selectEquipmentOneRunYn", managerInfo.get("procCd"));
			if(runMap == null){
				isRun = false;
			} else {
				if(runMap.get("runYn").equals("Y")){
					isRun = true;
					HashMap<String, Object> prodMap = new HashMap<String, Object>();
					prodMap = exqueryService.selectOne("nse.pms.smartmes.mesMobile.selectProdIdBySummary", managerInfo.get("procCd"));
					if(prodMap == null){
						prodMap = new HashMap<String, Object>();
//						prodMap.put("prodId", "0");
						resultMap.put("prodId", "0");
					}
					resultMap.put("prodId", prodMap.get("prodId"));
				} else {
					isRun = false;
				}
			}
			// return 값 저장
			resultMap.put("enterpriseCd", enterpriseInfo.get("enterpriseCd"));
			resultMap.put("enterpriseNm", enterpriseInfo.get("enterpriseNm"));
			resultMap.put("managerId", managerInfo.get("managerId"));
			resultMap.put("managerNm", managerInfo.get("managerNm"));
			resultMap.put("lineCd", managerInfo.get("lineCd"));
			resultMap.put("procCd", managerInfo.get("procCd"));
			resultMap.put("lineList", lineInfo);
			resultMap.put("procList", procInfo);
			resultMap.put("prodList", prodInfo);
			resultMap.put("isRun", isRun);
			isExist = true;
		}

		resultMap.put("isExist", isExist);
		return resultMap;
	}

	/**
	 * 담당자를 기준으로 가동시작 처리를 한다.
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> startRun(HashMap<String, Object> param) throws Exception {

        HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> managerInfo = new HashMap<String, Object>();
		HashMap<String, Object> runMap = new HashMap<String, Object>();
		HashMap<String, Object> eqpMap = new HashMap<String, Object>();
		boolean isExist = false;
		String reason = "";

        //담당자키가 정상적인지 확인한다.
		managerInfo = exqueryService.selectOne("nse.pms.smartmes.mesManager.selectManagerByManagerKey", param);
		if(managerInfo != null){
			//현재 설비가 가동상태인지 체크한다.
			runMap = exqueryService.selectOne("nse.pms.smartmes.mesManager.selectEqpRunInfo", param);

			if(runMap == null || runMap.get("runYn").equals("N")){
				//현재 설비를 가져온다.
				eqpMap = exqueryService.selectOne("nse.pms.smartmes.mesManager.selectEquipmentCd", param);
				eqpMap.put("managerId", managerInfo.get("managerId"));
				eqpMap.put("phone", managerInfo.get("phone"));
				eqpMap.put("runYn", "Y");
				eqpMap.put("stateCd", param.get("stateCd"));
				//설비가동이 처음인 상태, 현재 설비가 정지상태일 경우 가동시작을 처리한다.
				//com_eqp_state MERGE,
				exqueryService.update("nse.pms.smartmes.mesManager.mergeEqpState", eqpMap);
				//com_eqp_state_his UPDATE
				exqueryService.update("nse.pms.smartmes.mesManager.updateEqpStateHis", eqpMap);
				//com_eqp_state_his INSERT
				exqueryService.insert("nse.pms.smartmes.mesManager.insertEqpStateHis", eqpMap);

				reason = "작업이 시작되었습니다.";
				isExist = true;
			} else if(runMap.get("runYn").equals("Y")){
				//현재 설비가 가동일경우 처리하지 않고 메시지 알림만 띄운다.
				reason = "이미 가동중 입니다.";
				isExist = false;
			}

		} else {
			reason = "주소가 정상적이지 않습니다. 다시 시도해 주세요.";
			isExist = false;
		}

		resultMap.put("isExist", isExist);
		resultMap.put("reason", reason);
		return resultMap;
	}

	/**
	 * 담당자를 기준으로 가동정지 처리를 한다.
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> stopRun(HashMap<String, Object> param) throws Exception {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> managerInfo = new HashMap<String, Object>();
		HashMap<String, Object> runMap = new HashMap<String, Object>();
		HashMap<String, Object> eqpMap = new HashMap<String, Object>();
		boolean isExist = false;
		String reason = "";

		//담당자키가 정상적인지 확인한다.
		managerInfo = exqueryService.selectOne("nse.pms.smartmes.mesManager.selectManagerByManagerKey", param);
		if(managerInfo != null){
			//현재 설비가 가동상태인지 체크한다.
			runMap = exqueryService.selectOne("nse.pms.smartmes.mesManager.selectEqpRunInfo", param);

			if(runMap == null || runMap.get("runYn").equals("N")){
				//현재 설비가 가동일경우 처리하지 않고 메시지 알림만 띄운다.
				reason = "이미 정지중 입니다.";
				isExist = false;

			} else if(runMap.get("runYn").equals("Y")){
				//현재 설비를 가져온다.
				eqpMap = exqueryService.selectOne("nse.pms.smartmes.mesManager.selectEquipmentCd", param);
				eqpMap.put("managerId", managerInfo.get("managerId"));
				eqpMap.put("phone", managerInfo.get("phone"));
				eqpMap.put("runYn", "N");
				eqpMap.put("stateCd", param.get("stateCd"));

				//설비가동이 처음인 상태, 현재 설비가 정지상태일 경우 가동시작을 처리한다.
				//com_eqp_state MERGE,
				exqueryService.update("nse.pms.smartmes.mesManager.mergeEqpState", eqpMap);
				//com_eqp_state_his UPDATE
				exqueryService.update("nse.pms.smartmes.mesManager.updateEqpStateHis", eqpMap);
				//com_eqp_state_his INSERT
				exqueryService.insert("nse.pms.smartmes.mesManager.insertEqpStateHis", eqpMap);

				reason = "작업이 정지 되었습니다.";
				isExist = true;
			}

		} else {
			reason = "주소가 정상적이지 않습니다. 다시 시도해 주세요.";
			isExist = false;
		}

		resultMap.put("isExist", isExist);
		resultMap.put("reason", reason);
		return resultMap;
	}

	/**
	 * 담당자를 기준으로 생산량 및 불량량을 등록한다.
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> regProduction(HashMap<String, Object> param) throws Exception {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> managerInfo = new HashMap<String, Object>();
		boolean isExist = false;
		String reason = "";

		//담당자키가 정상적인지 확인한다.
		managerInfo = exqueryService.selectOne("nse.pms.smartmes.mesManager.selectManagerByManagerKey", param);
		if(managerInfo != null){
			param.put("managerId", managerInfo.get("managerId"));
			param.put("phone", managerInfo.get("phone"));
			//생산량을 등록한다.
			if(exqueryService.insert("nse.pms.smartmes.mesManager.regProduction", param) == 1){
				reason = "생산량이 입력되었습니다.";
				isExist = true;
			} else {
				reason = "생산량 입력 중 오류가 발생하였습니다. 다시 시도해 주세요.";
			}

		} else {
			reason = "주소가 정상적이지 않습니다. 다시 시도해 주세요.";
			isExist = false;
		}

		resultMap.put("isExist", isExist);
		resultMap.put("reason", reason);
		return resultMap;
	}

	/**
	 * 담당자를 기준으로 당일 등록 된 생산량 리스트를 조회한다.
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> getTodayProduction(HashMap<String, Object> param) throws Exception {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> managerInfo = new HashMap<String, Object>();
		List<HashMap<String, Object>> productionList = new ArrayList<HashMap<String, Object>>();
		boolean isExist = false;
		String reason = "";

		//담당자키가 정상적인지 확인한다.
		managerInfo = exqueryService.selectOne("nse.pms.smartmes.mesManager.selectManagerByManagerKey", param);
		if(managerInfo != null){
			param.put("managerId", managerInfo.get("managerId"));
			//생산량 리스트를 조회한다.
			productionList = exqueryService.selectList("nse.pms.smartmes.mesManager.getTodayProduction", param);
			isExist = true;
		} else {
			reason = "주소가 정상적이지 않습니다. 다시 시도해 주세요.";
			isExist = false;
		}

		resultMap.put("isExist", isExist);
		resultMap.put("reason", reason);
		resultMap.put("productionList", productionList);
		resultMap.put("startEndDate", exqueryService.selectOne("nse.pms.smartmes.mesManager.getStartEndDate", param));
		return resultMap;
	}

	/**
	 *  설비의 가동상태를 체크한다.
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> checkRunYnState(HashMap<String, Object> param) throws Exception {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> managerInfo = new HashMap<String, Object>();
		boolean isExist = false;
		String reason = "";


		//담당자키가 정상적인지 확인한다.
		managerInfo = exqueryService.selectOne("nse.pms.smartmes.mesManager.selectManagerByManagerKey", param);
		if(managerInfo != null){
			//설비의 가동상태를 조회한다.
			resultMap = exqueryService.selectOne("nse.pms.smartmes.mesManager.selectEqpRunInfo", param);
			if((resultMap.get("runYn")).equals("Y")){
				isExist = true;
			} else {
				reason = "정지중 입니다. 가동 후 등록하여 주세요.";
			}
		} else {
			reason = "주소가 정상적이지 않습니다. 다시 시도해 주세요.";
			isExist = false;
		}

		resultMap.put("isExist", isExist);
		resultMap.put("reason", reason);
		return resultMap;
	}

	/**
	 *  설비의 가동상태를 체크한다.
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> deleteInput(HashMap<String, Object> param) throws Exception {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> managerInfo = new HashMap<String, Object>();
		boolean isExist = false;
		String reason = "";


		//담당자키가 정상적인지 확인한다.
		managerInfo = exqueryService.selectOne("nse.pms.smartmes.mesManager.selectManagerByManagerKey", param);
		if(managerInfo != null){
			//등록된 생산량을 삭제한다.
			exqueryService.delete("nse.pms.smartmes.mesMobile.deleteInput", param);
		} else {
			reason = "주소가 정상적이지 않습니다. 다시 시도해 주세요.";
			isExist = false;
		}

		resultMap.put("isExist", isExist);
		resultMap.put("reason", reason);
		return resultMap;
	}

	/**
	 * 라인 변경 시 공정목록 불러오기
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> getProcListByLineChange(HashMap<String, Object> param) throws Exception {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> managerInfo = new HashMap<String, Object>();
		List<HashMap<String, Object>> procInfo = new ArrayList<HashMap<String, Object>>();

		boolean isExist = false;

		// managerKey 등록되어 있는지 확인
		managerInfo = exqueryService.selectOne("nse.pms.smartmes.mesManager.selectManagerByManagerKey", param);
		// 존재 할 경우
		if(managerInfo != null){
			//공정 리스트 조회
			procInfo = exqueryService.selectList("nse.pms.smartmes.mesProcess.selectProcListByLineCd", param);
			// return 값 저장
			resultMap.put("procList", procInfo);
			isExist = true;
		}

		resultMap.put("isExist", isExist);
		return resultMap;
	}
}