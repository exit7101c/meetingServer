package com.cronies.smartmes.service.impl;

import com.cronies.smartmes.service.MoniService;
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

@Service("moniService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MoniServiceImpl implements MoniService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;

	@Autowired
	private ConstraintService constraintService;

	@Autowired
	private SmsService smsService;

	/**
	 *  구역(법인)에 해당하는 라인 및 공정 리스트를 조회한다.
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> selectLineProc(HashMap<String, Object> param) throws Exception {

		List<HashMap<String, Object>> lineList = new ArrayList<HashMap<String, Object>>();
		List<HashMap<String, Object>> procList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		// 라인 리스트를 조회한다.
		lineList = exqueryService.selectList("nse.pms.smartmes.mesLine.selectLineList", param);
		//라인리스트가 존재한다면
		if(lineList.size() > 0){
			for(int i=0,len=lineList.size(); i < len; i++){
				param.put("lineCd", lineList.get(i).get("lineCd"));
				//공정을 조회한다.
				procList = exqueryService.selectList("nse.pms.smartmes.mesProcess.selectProcListByLineCd", param);
				//공정이 존재한다면
				if(procList.size() > 0){
					//공정의 수만큼 반복한다.
					for(int j=0,len2=procList.size(); j < len2; j++){
						param.put("procCd", procList.get(j).get("procCd"));
						//공정별 가동정보, 생산제품, 생산량, 불량량, 양품률, TT을 조회한다.
						procList.get(j).put("runYn", exqueryService.selectOne("nse.pms.smartmes.mesEquipment.selectEquipmentOneRunYn", param).get("runYn"));
						HashMap<String, Object> procMap = new HashMap<String, Object>();
						procMap = exqueryService.selectOne("nse.pms.smartmes.mesEquipment.selectProcInfoMap", procList.get(j));
						if(procMap == null){
							procMap = new HashMap<String, Object>();
							procMap.put("tt", "0");
							procMap.put("badCnt", "0");
							procMap.put("goodsRate", "0");
							procMap.put("startTimeStr", "-");
							procMap.put("procCd", "");
							procMap.put("prodNm", "생산정지");
							procMap.put("prodCnt", "0");
						}
						procList.get(j).put("procMap", procMap);

						//해당공정의 담당자를 불러온다.
						List<HashMap<String, Object>> managerList = new ArrayList<HashMap<String, Object>>();
						managerList = exqueryService.selectList("nse.pms.smartmes.mesProcess.selectLineProcManagerList", param);

						procList.get(j).put("managerList", managerList);
					}
				}
				lineList.get(i).put("procList", procList);
			}
		}

		resultMap.put("result", lineList);
		return resultMap;
	}

	/**
	 *  구역(법인)에 해당하는 라인 및 공정 리스트를 조회한다.
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> selectLineProcRun(HashMap<String, Object> param) throws Exception {

		List<HashMap<String, Object>> lineList = new ArrayList<HashMap<String, Object>>();
		List<HashMap<String, Object>> procList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		// 라인 리스트를 조회한다.
		lineList = exqueryService.selectList("nse.pms.smartmes.mesLine.selectLineList", param);
		//라인리스트가 존재한다면
		if(lineList.size() > 0){
			for(int i=0,len=lineList.size(); i < len; i++){
				param.put("lineCd", lineList.get(i).get("lineCd"));
				//공정을 조회한다.
				procList = exqueryService.selectList("nse.pms.smartmes.mesProcess.selectProcListByLineCd", param);
				//공정이 존재한다면
				if(procList.size() > 0){
					//공정의 수만큼 반복한다.
					for(int j=0,len2=procList.size(); j < len2; j++){
						param.put("procCd", procList.get(j).get("procCd"));
						//공정별 가동정보, 생산제품, 생산량, 불량량, 양품률, TT을 조회한다.
						procList.get(j).put("runYn", exqueryService.selectOne("nse.pms.smartmes.mesEquipment.selectEquipmentOneRunYn", param).get("runYn"));
						HashMap<String, Object> procMap = new HashMap<String, Object>();
						procMap = exqueryService.selectOne("nse.pms.smartmes.mesEquipment.selectProcInfoMap", procList.get(j));
						if(procMap == null){
							procMap = new HashMap<String, Object>();
							procMap.put("tt", "0");
							procMap.put("badCnt", "0");
							procMap.put("goodsRate", "0");
							procMap.put("startTimeStr", "-");
							procMap.put("procCd", "");
							procMap.put("prodNm", "생산정지");
							procMap.put("prodCnt", "0");
						}
						procList.get(j).put("procMap", procMap);

						//가동정보 조회
						List<HashMap<String, Object>> runList = new ArrayList<HashMap<String, Object>>();
						List<HashMap<String, Object>> prodList = new ArrayList<HashMap<String, Object>>();
						runList = exqueryService.selectList("nse.pms.smartmes.mesProcess.selectProcEquipRunList", param);
						prodList = exqueryService.selectList("nse.pms.smartmes.mesProcess.selectProcEquipProdList", param);

						//현재시간을 담아준다 (leftpx로)
						HashMap<String, Object> timeMap = new HashMap<String, Object>();
						timeMap = exqueryService.selectOne("nse.pms.smartmes.mesProcess.selectProcCurruntTime", procList.get(j));

						procList.get(j).put("nowTime", timeMap);
						procList.get(j).put("runList", runList);
						procList.get(j).put("prodList", prodList);
					}
				}
				lineList.get(i).put("procList", procList);
			}
		}

		resultMap.put("result", lineList);
		return resultMap;
	}

	/**
	 *  해당 라인공정 담당자에게 생산등록요청 메시지를 발송한다.
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> sendLineProcManager(HashMap<String, Object> param) throws Exception {

		List<HashMap<String, Object>> managerList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		String msg = "";
		boolean isExist = false;

		// 매니저 리스트를 조회한다.
		managerList = exqueryService.selectList("nse.pms.smartmes.mesManager.sendLineProcManager", param);
		//매니저리스트가 존재한다면
		if(managerList.size() > 0){
			for(int i=0,len=managerList.size(); i < len; i++){
				HashMap<String, Object> msgMap = new HashMap<String, Object>();
				msgMap.put("managerKey", managerList.get(i).get("managerKey"));
				msgMap.put("phonNumber", managerList.get(i).get("phone"));
				msgMap.put("enterpriseCd", managerList.get(i).get("enterpriseCd"));

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
		}

		resultMap.put("isExist", isExist);
		resultMap.put("msg", msg);
		return resultMap;
	}


}