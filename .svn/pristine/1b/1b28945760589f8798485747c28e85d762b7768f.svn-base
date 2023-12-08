package com.nse.pms.standard.common.service.impl;

import com.nse.pms.standard.common.service.ConstraintService;
import com.nse.pms.standard.common.service.CommonModalService;
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

@Service("commonModalService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CommonModalServiceImpl implements CommonModalService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;

	@Autowired
	private ConstraintService constraintService;

	/**
	 * [공통 모달창] 접점 상세보기 - 테이블,차트 데이터 조회
	 * @throws Exception
	 **/
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> selectTagChartList(HashMap<String, Object> param) throws Exception {

		HashMap<String, Object> dataMap = new HashMap<String, Object>();

		// 조회기간 String 조회
		HashMap<String, Object> dateMap = exqueryService.selectOne("nse.pms.ctq.sqlCtq.selectSearchRangeStr", param);

		// 접점 정보 조회
		HashMap<String, Object> tagMap = exqueryService.selectOne("nse.pms.common.sqlCommonModal.selectTagOne", param);

		//데이터조회
		List<HashMap<String, Object>> dataList = exqueryService.selectList("nse.pms.common.sqlCommonModal.selectTagChartList", param);

		//SV가 있는 접점인지 조회한다.
		HashMap<String, Object> svMap = exqueryService.selectOne("nse.pms.common.sqlCommonModal.selectTagSvInfo", param);

		List<HashMap<String, Object>> svList = new ArrayList<HashMap<String, Object>>();
		if(svMap != null){
			//sv가 있음 데이터 추가
			param.put("svTagCd", svMap.get("svTagCd"));
			svList = exqueryService.selectList("nse.pms.common.sqlCommonModal.selectSvTagChartList", param);
		}
		//해당 접점이 설비에 매핑되어있고 해당설비에 알람,가동 정보가 있을경우 같이 조회한다.

		//설비 가동정보
		List<HashMap<String, Object>> stateList = exqueryService.selectList("nse.pms.common.sqlCommonModal.selectTagChartStateInfo", param);
		//설비알람정보 조회
		List<HashMap<String, Object>> alarmList = exqueryService.selectList("nse.pms.common.sqlCommonModal.selectTagChartAlarmInfo", param);
		//제품변경 정보
		List<HashMap<String, Object>> productionList = exqueryService.selectList("nse.pms.common.sqlCommonModal.selectTagChartProductionInfo", param);

		dataMap.put("startTimeStr", dateMap.get("startTimeStr"));
		dataMap.put("endTimeStr", dateMap.get("endTimeStr"));
		dataMap.put("tagCd", tagMap.get("tagCd"));
		dataMap.put("tagNm", tagMap.get("tagNm"));
		dataMap.put("unit", tagMap.get("unit"));
		dataMap.put("chartType", tagMap.get("chartType"));
		dataMap.put("chartData", dataList);
		dataMap.put("svData", svList);

		dataMap.put("stateList", stateList);
		dataMap.put("alarmList", alarmList);
		dataMap.put("productionList", productionList);


		return dataMap;

	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>> selectTagChartListExcel(HashMap<String, Object> param) throws Exception {

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		resultList = exqueryService.selectList("nse.pms.common.sqlCommonModal.selectTagChartList", param);

		return resultList;

	}

	/**
	 * [공통 모달창] 알람 상세보기 - 테이블 데이터 조회
	 * @throws Exception
	 **/
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> selectAlarmList(HashMap<String, Object> param) throws Exception {

		HashMap<String, Object> dataMap = new HashMap<String, Object>();

		// 조회기간 String 조회
		HashMap<String, Object> dateMap = exqueryService.selectOne("nse.pms.ctq.sqlCtq.selectSearchRangeStr", param);

		// 접점 정보 조회
		HashMap<String, Object> tagMap = exqueryService.selectOne("nse.pms.common.sqlCommonModal.selectEqpOne", param);

		List<HashMap<String, Object>> dataList = exqueryService.selectList("nse.pms.common.sqlCommonModal.selectEqpAlarmList", param);

		dataMap.put("startTimeStr", dateMap.get("startTimeStr"));
		dataMap.put("endTimeStr", dateMap.get("endTimeStr"));
		dataMap.put("eqpCd", tagMap.get("eqpCd"));
		dataMap.put("eqpNm", tagMap.get("eqpNm"));
		dataMap.put("dataList", dataList);


		return dataMap;

	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>> selectAlarmListExcel(HashMap<String, Object> param) throws Exception {

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		resultList = exqueryService.selectList("nse.pms.common.sqlCommonModal.selectEqpAlarmList", param);

		return resultList;

	}


	/**
	 * [공통 모달창] 가동 상세보기 - 테이블 데이터 조회
	 * @throws Exception
	 **/
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> selectEqpStateList(HashMap<String, Object> param) throws Exception {

		HashMap<String, Object> dataMap = new HashMap<String, Object>();

		// 조회기간 String 조회
		HashMap<String, Object> dateMap = exqueryService.selectOne("nse.pms.ctq.sqlCtq.selectSearchRangeStr", param);

		// 접점 정보 조회
		HashMap<String, Object> tagMap = exqueryService.selectOne("nse.pms.common.sqlCommonModal.selectEqpOne", param);

		List<HashMap<String, Object>> dataList = exqueryService.selectList("nse.pms.common.sqlCommonModal.selectEqpStateList", param);

		dataMap.put("startTimeStr", dateMap.get("startTimeStr"));
		dataMap.put("endTimeStr", dateMap.get("endTimeStr"));
		dataMap.put("eqpCd", tagMap.get("eqpCd"));
		dataMap.put("eqpNm", tagMap.get("eqpNm"));
		dataMap.put("dataList", dataList);


		return dataMap;

	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>> selectEqpStateListExcel(HashMap<String, Object> param) throws Exception {

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		resultList = exqueryService.selectList("nse.pms.common.sqlCommonModal.selectEqpStateList", param);

		return resultList;

	}
}
