package com.cronies.smartmes.service.impl;

import com.cronies.smartmes.service.SmsService;
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

@Service("smsService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SmsServiceImpl implements SmsService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;

	@Autowired
	private ConstraintService constraintService;


	/**
	 * 메시지 보내기 공통 서비스
	 *
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> sendMsgCommon(HashMap<String, Object> param) throws Exception {

		//메시지발송
		HashMap<String, Object> msgMap = new HashMap<String, Object>();
		msgMap.put("msg", param.get("msg")); // parameter
		msgMap.put("phonNumber", param.get("phonNumber")); // parameter

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = selectMessageHisByFiveMin(msgMap);
		boolean isExist = (boolean) resultMap.get("isExist");
		if(isExist){
			exqueryService.insert("cronies.message.insertMessageTranMms", msgMap);
			exqueryService.insert("nse.pms.smartmes.mesEnterprise.insertMessageHis", msgMap);
		}
		return resultMap;
	}

	/**
	 * 가입환영 문자메시지를 발송한다.
	 * 
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean sendJoinWelcome(HashMap<String, Object> param) throws Exception {

		//메시지발송
		HashMap<String, Object> msgMap = new HashMap<String, Object>();
		msgMap.put("msg", "[제조나라]를 이용해 주셔서 감사합니다.\n\n 스마트팩토리의 시작은 생산현황을 모니터링하는데 있습니다. \n\n 전송된 링크를 통해 생산 가동정보와 생산량, 불량량을 등록하고 생산현황을 확인해 보세요. \n\n 불량률이 과도하게 높지 않은지, 생산성이 현저히 저하되지 않는지, 분석하여 알려드립니다."); // parameter
		msgMap.put("phonNumber", param.get("phonNumber")); // parameter

		exqueryService.insert("cronies.message.insertMessageTranMms", msgMap);
		exqueryService.insert("nse.pms.smartmes.mesEnterprise.insertMessageHis", msgMap);

		return true;
	}

	/**
	 * 생산등록을 위한 SMS를 발송한다.
	 *
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> sendManagerProdReg(HashMap<String, Object> param) throws Exception {

		//메시지발송
		HashMap<String, Object> msgMap = new HashMap<String, Object>();
		String managerKey = param.get("managerKey").toString();

		//메니저KEY를 기준으로 회사명,라인명,공정명,담당자명을 조회한다.
		HashMap<String, Object> infoMap = new HashMap<String, Object>();
		infoMap = exqueryService.selectOne("cronies.message.selectManagerEnterInfo", param);

		msgMap.put("msg", "[제조나라] 아래링크를 통해 가동시작 및 생산등록을 해주세요. \n\n["+infoMap.get("enterpriseNm")+"]\n["+infoMap.get("lineNm")+"]["+infoMap.get("procNm")+"]\n["+infoMap.get("managerNm")+" 님] \n\nhttp://183.111.226.49/#/"+managerKey); // parameter
		msgMap.put("phonNumber", param.get("phonNumber")); // parameter

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = selectMessageYnByEnter(infoMap);
		boolean isExist = (boolean) resultMap.get("isExist");
		if(isExist){
			resultMap = selectMessageHisByFiveMin(msgMap);
			isExist = (boolean) resultMap.get("isExist");
			if(isExist){
				exqueryService.insert("cronies.message.insertMessageTranMms", msgMap);
				exqueryService.insert("nse.pms.smartmes.mesEnterprise.insertMessageHis", msgMap);
			}
		}

		return resultMap;

	}

	/**
	 * 최근5분동안 메시지전송이력 리스트 조회.
	 *
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> selectMessageHisByFiveMin(HashMap<String, Object> param) throws Exception {
		List<HashMap<String, Object>> msgCountList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		boolean isExist = false;
		String msg = "";

		if(param.get("phonNumber") != null){
			param.put("phone", param.get("phonNumber"));
		}
		msgCountList = exqueryService.selectList("nse.pms.smartmes.mesEnterprise.selectMessageHisByFiveMin", param);

		if(msgCountList.size() >= 5){
			msg = "[ "+param.get("phone")+" ] 최근 5분동안 5회이상 SMS서비스 이용으로 인해 제한되었습니다.";
			isExist = false;
		} else {
			msg = "Y";
			isExist = true;
		}

		resultMap.put("isExist", isExist);
		resultMap.put("msg", msg);
		return resultMap;
	}

	/**
	 * 해당 법인의 문자메시지 수신여부 확인
	 *
	 * @param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> selectMessageYnByEnter(HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> msgYnMap = new HashMap<String, Object>();
		boolean isExist = false;
		String msg = "";

		if(param.get("ssCustomerEnterCd") != null){
			param.put("enterpriseCd", param.get("ssCustomerEnterCd"));
		}
		msgYnMap = exqueryService.selectOne("nse.pms.smartmes.mesEnterprise.selectMessageYnByEnter", param);

		if(msgYnMap.get("messageYn").equals("N")){
			msg = "문자메시지 수신여부가 거부로 설정되어있어 발송되지 않았습니다. \r\n발송을 원하실 경우 설정에서 문자메시지 수신여부를 수신으로 수정해주시기 바랍니다.";
			isExist = false;
		} else {
			msg = "Y";
			isExist = true;
		}

		resultMap.put("isExist", isExist);
		resultMap.put("msg", msg);
		return resultMap;
	}


	/**
	 *  해당 법인 담당자들에게 생산등록요청 메시지를 발송한다.
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> sendManagerProdRegByEnter(HashMap<String, Object> param) throws Exception {

		List<HashMap<String, Object>> managerList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		String msg = "";
		boolean isExist = false;

		// 해당 법인의 매니저 리스트를 조회한다.
		managerList = exqueryService.selectList("nse.pms.smartmes.mesManager.selectManagerList", param);
		//매니저리스트가 존재한다면
		if(managerList.size() > 0){
			for(int i=0,len=managerList.size(); i < len; i++){
				HashMap<String, Object> msgMap = new HashMap<String, Object>();
				msgMap.put("managerKey", managerList.get(i).get("managerKey"));
				msgMap.put("phonNumber", managerList.get(i).get("phone"));
				msgMap.put("enterpriseCd", managerList.get(i).get("enterpriseCd"));

				HashMap<String, Object> returnMap = new HashMap<String, Object>();
				returnMap = sendManagerProdReg(msgMap);
				if((boolean) returnMap.get("isExist")){
					isExist = true;
					msg = "발송성공";
				} else {
					isExist = false;
					msg = (String) returnMap.get("msg");
				}

			}
		}

		resultMap.put("isExist", isExist);
		resultMap.put("msg", msg);
		return resultMap;
	}

}