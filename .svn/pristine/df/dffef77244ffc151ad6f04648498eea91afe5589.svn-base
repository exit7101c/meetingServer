package com.nse.pms.standard.monitoring.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nse.pms.standard.common.service.ConstraintService;
import com.nse.pms.standard.monitoring.service.MainService;
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

@Service("mainService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MainServiceImpl implements MainService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;

	@Autowired
	private ConstraintService constraintService;

	/**
	 * 라인모니터링 데이터조회
	 * @throws Exception
	 **/
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> selectLineMoni(HashMap<String, Object> param) throws Exception {
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		String areaCd = "QD2U";//청도신공장 스프라인
		//조회대상 라인 CVD, SD
		List<HashMap<String, Object>> lineList = new ArrayList<HashMap<String, Object>>();

		// json ArrayList를 Java List로 변환
		//ObjectMapper mapper = new ObjectMapper();
		//lineList = mapper.readValue(param.get("lineList").toString(), new TypeReference<ArrayList<HashMap<String, Object>>>(){});

		HashMap<String, Object> aMap = new HashMap<String, Object>();
		aMap.put("lineCd","L03");
		lineList.add(aMap);

		for(int i=0, len=lineList.size(); i < len; i++){
			HashMap<String, Object> resMap = new HashMap<String, Object>();

			//구역설정
			lineList.get(i).put("areaCd", areaCd);
			//언어설정
			lineList.get(i).put("ssUserLang", "ko");

			//설비별 가동 데이터 조회
			List<HashMap<String, Object>> eqpStateList = exqueryService.selectList("nse.pms.main.sqlMain.selectEquipmentState", lineList.get(i));

			//설비별 알림데이터 조회
			List<HashMap<String, Object>> eqpAlarmList = exqueryService.selectList("nse.pms.main.sqlMain.selectEquipmentAlarm", lineList.get(i));

			//전체 CTQ목록 조회 (관리 상하한 포함)
			List<HashMap<String, Object>> ctqList = exqueryService.selectList("nse.pms.main.sqlMain.selectCtqTreeList", lineList.get(i));

			//설비별 경고상하한 알람 갯수
			List<HashMap<String, Object>> eqpWarningCnt = exqueryService.selectList("nse.pms.main.sqlMain.eqpWarningCnt", lineList.get(i));

			//라인 생산제품 조회
			List<HashMap<String, Object>> lineProdList = exqueryService.selectList("nse.pms.main.sqlMain.selectLineMoniProd", lineList.get(i));

			//결과저장
			resMap.put("lineCd", lineList.get(i).get("lineCd"));
			resMap.put("eqpStateList", eqpStateList);
			resMap.put("eqpAlarmList", eqpAlarmList);
			resMap.put("lineProdList", lineProdList);
			resMap.put("ctqList", ctqList);
			resMap.put("eqpWarningCnt", eqpWarningCnt);

			resultList.add(resMap);
		}

		//CIP상태 조회
		List<HashMap<String, Object>> cipList = exqueryService.selectList("nse.pms.main.sqlMain.selectCipList", new HashMap<>());


		resultMap.put("cipList", cipList);
		resultMap.put("data", resultList);

		return resultMap;
	}

	/**
	 * CTQ 상하한관리 데이터조회
	 * @throws Exception
	 **/
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> selectCtqMoniRange(HashMap<String, Object> param) throws Exception {
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		String areaCd = "QD2U";//청도신공장 스프라인
		//조회대상 라인 CVD, SD
		List<HashMap<String, Object>> lineList = new ArrayList<HashMap<String, Object>>();

		// json ArrayList를 Java List로 변환
		ObjectMapper mapper = new ObjectMapper();
		lineList = mapper.readValue(param.get("lineList").toString(), new TypeReference<ArrayList<HashMap<String, Object>>>(){});


		for(int i=0, len=lineList.size(); i < len; i++){
			HashMap<String, Object> resMap = new HashMap<String, Object>();

			//구역설정
			lineList.get(i).put("areaCd", areaCd);
			//언어설정
			lineList.get(i).put("ssUserLang", param.get("ssUserLang"));

			//전체 CTQ목록 조회 (관리 상하한 포함)
			List<HashMap<String, Object>> ctqList = exqueryService.selectList("nse.pms.main.sqlMain.selectCtqGaugeList", lineList.get(i));

			//결과저장
			resMap.put("lineCd", lineList.get(i).get("lineCd"));
			resMap.put("ctqList", ctqList);

			resultList.add(resMap);
		}


		resultMap.put("data", resultList);

		return resultMap;
	}

	/**
	 * 설비정보 데이터조회
	 * @throws Exception
	 **/
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> selectEqpInfo(HashMap<String, Object> param) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		String areaCd = "QD2U";//청도신공장 스프라인
		//설비명조회
		HashMap<String, Object> eqpInfoMap = exqueryService.selectOne("nse.pms.main.sqlMain.selectEquipmentInfo", param);
		resultMap.put("eqpInfoMap", eqpInfoMap);

		//설비가동상태 조회
		HashMap<String, Object> eqpStateMap = exqueryService.selectOne("nse.pms.main.sqlMain.selectEquipmentStateInfoOne", param);
		resultMap.put("eqpStateMap", eqpStateMap);

		//설비 접점목록 조회
		List<HashMap<String, Object>> eqpTagList = exqueryService.selectList("nse.pms.main.sqlMain.selectEquipmentTagList", param);
		resultMap.put("eqpTagList", eqpTagList);

		//알람정보 조회 (최근1일)
		HashMap<String, Object> eqpAlarmMap = exqueryService.selectOne("nse.pms.main.sqlMain.selectEquipmentAlarmCount", param);
		resultMap.put("eqpAlarmMap", eqpAlarmMap);

		//가동정보 조회 (차트)
		HashMap<String,Object> chkMap = exqueryService.selectOne("nse.pms.basic.sqlProcess.selectCheckDataFormat", param);
		resultMap.put("startStr", chkMap.get("startTimeStr"));
		resultMap.put("endStr", chkMap.get("endTimeStr"));

		List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
		List<HashMap<String,Object>> resultAllList = new ArrayList<HashMap<String,Object>>();

		List<HashMap<String,Object>> eqpList = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> eMap = new HashMap<String, Object>();
		eMap.put("eqpCd", eqpInfoMap.get("eqpCd"));
		eMap.put("eqpNm", eqpInfoMap.get("eqpNm"));
		eqpList.add(eMap);

		if(eqpList.size()>0) {
			for(int i=0; i<eqpList.size(); i++) {
				HashMap<String,Object> eqpMap = new HashMap<String,Object>();
				eqpMap.put("eqpCd", eqpList.get(i).get("eqpCd"));
				eqpMap.put("eqpNm", eqpList.get(i).get("eqpNm"));
				eqpMap.put("fromDate", param.get("fromDate"));
				eqpMap.put("toDate", param.get("toDate"));
				eqpMap.put("idx", i);
				eqpMap.put("ssUserLang", param.get("ssUserLang"));
				List<HashMap<String,Object>> runList = new ArrayList<HashMap<String,Object>>();
				runList = exqueryService.selectList("nse.pms.main.sqlMain.selectEquipmentStateInfoList", eqpMap);
				resultAllList.addAll(runList);
				resultList.add(eqpMap);
			}
		}
		resultMap.put("result", resultList);
		resultMap.put("resultAll", resultAllList);

		return resultMap;

	}

	/**
	 * 시계 정보 조회
	 * @param
	 * @return
	 **/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> selectClock(HashMap<String, Object> param){

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = exqueryService.selectOne("nse.pms.main.sqlMain.selectClock", param);
		return resultMap;
	}

    /**
     * 상하한값 조회
     * @param
     * @return
     **/
    @Transactional(propagation = Propagation.REQUIRED , readOnly = false)
    public HashMap<String, Object> selectTagCl(HashMap<String, Object> param){

        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        resultMap = exqueryService.selectOne("nse.pms.main.sqlMain.selectTagCl", param);
        if(resultMap == null){
        	resultMap = new HashMap<String, Object>();
		}
        return resultMap;
    }

	/**
	 * 상하한값 수정
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> updateTagCl(HashMap<String, Object> param) throws Exception {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		List<HashMap<String, Object>> prodList = new ArrayList<HashMap<String, Object>>();

		// json ArrayList를 Java List로 변환
		ObjectMapper mapper = new ObjectMapper();
		prodList = mapper.readValue(param.get("prodList").toString(), new TypeReference<ArrayList<HashMap<String, Object>>>(){});

		if(param.get("prodCd").equals("all")){

			for(int i=0, len=prodList.size(); i < len; i++){
				prodList.get(i).put("tagCd", param.get("tagCd"));
				prodList.get(i).put("usl", param.get("usl"));
				prodList.get(i).put("ucl", param.get("ucl"));
				prodList.get(i).put("cl", param.get("cl"));
				prodList.get(i).put("lcl", param.get("lcl"));
				prodList.get(i).put("lsl", param.get("lsl"));
				prodList.get(i).put("autoLimitYn", param.get("autoLimitYn"));
				prodList.get(i).put("ssUserId", param.get("ssUserId"));
				prodList.get(i).put("ssUserLang", param.get("ssUserLang"));

				exqueryService.selectList("nse.pms.main.sqlMain.updateTagCl", prodList.get(i));
				exqueryService.selectList("nse.pms.main.sqlMain.updateTagClAutoLimit", prodList.get(i));

				//로그저장
				exqueryService.insert("nse.pms.main.sqlMain.insertTagClLog", prodList.get(i));
			}
		} else {

			exqueryService.selectList("nse.pms.main.sqlMain.updateTagCl", param);
			exqueryService.selectList("nse.pms.main.sqlMain.updateTagClAutoLimit", param);

			//로그저장
			exqueryService.insert("nse.pms.main.sqlMain.insertTagClLog", param);
		}

		return resultMap;
	}


	/**
	 * 상하한값 수정팝업 (제품조회)
	 **/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public List<HashMap<String, Object>> selectProdList(HashMap<String, Object> param){

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		resultList = exqueryService.selectList("nse.pms.main.sqlMain.selectProdList", param);

		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String, Object> selectProdOne(HashMap<String, Object> param){

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = exqueryService.selectOne("nse.pms.main.sqlMain.selectProdOne", param);
		return resultMap;
	}


	/**
	 * 상하한값 수정
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> updateEqpAlarmOff(HashMap<String, Object> param) {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		exqueryService.selectList("nse.pms.main.sqlMain.updateEqpAlarmOff", param);

		return resultMap;
	}


	/**
	 * 라인모니터링 데이터조회
	 * @throws Exception
	 **/
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> selectMoniCondition(HashMap<String, Object> param) throws Exception {
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		List<HashMap<String, Object>> eqpGrpList = new ArrayList<HashMap<String, Object>>();

		// json ArrayList를 Java List로 변환
		ObjectMapper mapper = new ObjectMapper();
		eqpGrpList = mapper.readValue(param.get("eqpGrpList").toString(), new TypeReference<ArrayList<HashMap<String, Object>>>(){});

		for(int i=0, len=eqpGrpList.size(); i < len; i++){
			HashMap<String, Object> resMap = new HashMap<String, Object>();

			eqpGrpList.get(i).put("lineCd", param.get("lineCd"));
			eqpGrpList.get(i).put("ssUserLang", param.get("ssUserLang"));

			List<HashMap<String, Object>> dataList = exqueryService.selectList("nse.pms.main.sqlMain.selectMoniCondition", eqpGrpList.get(i));
			HashMap<String, Object> scoreAvg = exqueryService.selectOne("nse.pms.main.sqlMain.selectMoniConditionAvg", eqpGrpList.get(i));

			resMap.put("eqpGroup", eqpGrpList.get(i).get("eqpGroup"));
			resMap.put("dataList", dataList);
			resMap.put("scoreAvg", scoreAvg.get("scoreAvg"));

			resultList.add(resMap);
		}

		HashMap<String, Object> product = exqueryService.selectOne("nse.pms.main.sqlMain.selectMoniConditionProd", param);
		List<HashMap<String, Object>> eqpStateList = exqueryService.selectList("nse.pms.main.sqlMain.selectMoniConditionState", param);

		if(product != null){
			resultMap.put("prodCd", product.get("prodCd"));
			resultMap.put("prodNm", product.get("prodNm"));
		}
		resultMap.put("data", resultList);
		resultMap.put("eqpStateList", eqpStateList);

		return resultMap;
	}

}
