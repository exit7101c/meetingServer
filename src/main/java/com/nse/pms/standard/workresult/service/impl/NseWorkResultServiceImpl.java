package com.nse.pms.standard.workresult.service.impl;

import com.nse.pms.standard.workresult.service.NseWorkResultService;
import com.nse.pms.standard.basic.service.NseProcessService;
import com.nse.pms.standard.basic.service.NseLineService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
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

@Service("NseWorkResultService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class NseWorkResultServiceImpl implements NseWorkResultService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;

	@Autowired
	private NseProcessService nseProcessService;

	@Autowired
	private NseLineService nseLineService;

	/**
	 * 작업지시를 조회한다.
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>> selectWorkResultList(HashMap<String, Object> param) {

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		resultList = exqueryService.selectList("nse.pms.workResult.sqlWorkResult.selectWorkResultList", param);

		return resultList;
	}

	/**
	 * MES 작업지시 수신.
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> executeIFRWorkResult(HashMap<String, Object> param) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		resultMap = exqueryService.selectOne("nse.pms.workResult.sqlWorkResult.executeIFRWorkResult", param);

		return resultMap;
	}

	/**
	 * Lot tracking.
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>> selectWorkResultTree(HashMap<String, Object> param) {

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		resultList = exqueryService.selectList("nse.pms.workResult.sqlWorkResult.selectWorkResultTree", param);

		return resultList;
	}

	/**
	 * Line - Tag List
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>> selectLineProcEqpTagList(HashMap<String, Object> param) {

		// 1. 구역에 따른 라인 리스트
		List<HashMap<String, Object>> LineList = exqueryService
				.selectList("nse.pms.workResult.sqlWorkResult.selectWorkOrderLineList", param);

		if (LineList.size() > 0) {

			for (HashMap<String, Object> LineMap : LineList) {
				List<HashMap<String, Object>> ProcessList = new ArrayList<HashMap<String, Object>>();
				LineMap.put("ssUserLang", param.get("ssUserLang"));

				ProcessList = nseProcessService.selectApiLineProcList(LineMap); // 해당 라인의 공정 리스트

				if (ProcessList.size() > 0) {
					LineMap.put("children", ProcessList);
					for (HashMap<String, Object> procMap : ProcessList) {
						procMap.put("ssUserLang", param.get("ssUserLang"));
						List<HashMap<String, Object>> EquipmentList = nseProcessService
								.selectApiLineProcEqpList(procMap); // 해당 공정의 설비 리스트
						if (EquipmentList.size() > 0) {
							procMap.put("children", EquipmentList); // 조회된 설비를 공정에 담는다.

							for (HashMap<String, Object> eqpMap : EquipmentList) {
								eqpMap.put("ssUserLang", param.get("ssUserLang"));
								eqpMap.put("collectType",
										param.containsKey("collectType") ? param.get("collectType") : "");

								List<HashMap<String, Object>> tagList = exqueryService
										.selectList("nse.pms.basic.sqlTagCode.selectEqpTagList", eqpMap);
								if (tagList.size() > 0) {
									eqpMap.put("children", tagList);
								}
							}
						}
					}
				}
			}
		}

		return LineList;
	}

	/**
	 * Work Condition
	 **/
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>> selectWorkConditionList(HashMap<String, Object> param) throws Exception {
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		// json ArrayList를 Java List로 변환
		List<HashMap<String, Object>> ctqList = new ArrayList<HashMap<String, Object>>();
		ObjectMapper mapper = new ObjectMapper();

		// 조회기간 String 조회
		HashMap<String, Object> dateMap = exqueryService
				.selectOne("nse.pms.workResult.sqlWorkResult.selectSearchRangeStr", param);

		String queryStr = "nse.pms.workResult.sqlWorkResult.selectWorkConditionList";

		ctqList = mapper.readValue(param.get("ctqList").toString(),
				new TypeReference<ArrayList<HashMap<String, Object>>>() {
				});

		List<HashMap<String, Object>> chartData = new ArrayList<HashMap<String, Object>>();

		for (int idx = 0, len = ctqList.size(); idx < len; idx++) {
			ctqList.get(idx).put("fromDate", dateMap.get("fromDate"));
			ctqList.get(idx).put("toDate", dateMap.get("toDate"));
			ctqList.get(idx).put("ssUserLang", param.get("ssUserLang"));

			chartData = exqueryService.selectList(queryStr, ctqList.get(idx));

			HashMap<String, Object> chartMap = new HashMap<String, Object>();

			chartMap.put("startTimeStr", dateMap.get("startTimeStr"));
			chartMap.put("endTimeStr", dateMap.get("endTimeStr"));
			chartMap.put("ctqCd", ctqList.get(idx).get("ctqCd"));
			chartMap.put("ctqNm", ctqList.get(idx).get("ctqNm"));
			chartMap.put("tagCd", ctqList.get(idx).get("tagCd"));
			chartMap.put("unit", ctqList.get(idx).get("unit"));
			chartMap.put("chartType", ctqList.get(idx).get("chartType"));
			chartMap.put("fillYn", ctqList.get(idx).get("fillYn"));
			chartMap.put("chartData", chartData);

			resultList.add(chartMap);
		}

		// HashMap<String, Object> chartMap = new HashMap<String, Object>();

		// param.put("fromDate", dateMap.get("fromDate"));
		// param.put("toDate", dateMap.get("toDate"));
		// chartData =
		// exqueryService.selectList("nse.pms.workResult.sqlWorkResult.selectWeatherConditionTempList",
		// param);

		// chartMap.put("startTimeStr", dateMap.get("startTimeStr"));
		// chartMap.put("endTimeStr", dateMap.get("endTimeStr"));
		// chartMap.put("ctqCd", "weather");
		// chartMap.put("ctqNm", chartData.get(0).get("tagNm"));
		// chartMap.put("tagCd", "weather");
		// chartMap.put("unit", "weather");
		// chartMap.put("chartType", "trend");
		// chartMap.put("fillYn", "N");
		// chartMap.put("chartData", chartData);

		// resultList.add(chartMap);
		// chartMap.clear();

		// chartData =
		// exqueryService.selectList("nse.pms.workResult.sqlWorkResult.selectWeatherConditionHumidList",
		// param);

		// chartMap.put("startTimeStr", dateMap.get("startTimeStr"));
		// chartMap.put("endTimeStr", dateMap.get("endTimeStr"));
		// chartMap.put("ctqCd", "weather");
		// chartMap.put("ctqNm", chartData.get(0).get("tagNm"));
		// chartMap.put("tagCd", "weather");
		// chartMap.put("unit", "weather");
		// chartMap.put("chartType", "trend");
		// chartMap.put("fillYn", "N");
		// chartMap.put("chartData", chartData);

		// resultList.add(chartMap);
		// chartMap.clear();

		// chartData =
		// exqueryService.selectList("nse.pms.workResult.sqlWorkResult.selectWeatherConditionPressList",
		// param);

		// chartMap.put("startTimeStr", dateMap.get("startTimeStr"));
		// chartMap.put("endTimeStr", dateMap.get("endTimeStr"));
		// chartMap.put("ctqCd", "weather");
		// chartMap.put("ctqNm", chartData.get(0).get("tagNm"));
		// chartMap.put("tagCd", "weather");
		// chartMap.put("unit", "weather");
		// chartMap.put("chartType", "trend");
		// chartMap.put("fillYn", "N");
		// chartMap.put("chartData", chartData);

		// resultList.add(chartMap);

		return resultList;
	}
}
