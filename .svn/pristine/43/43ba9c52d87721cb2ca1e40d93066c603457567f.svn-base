package com.nse.pms.standard.ctq.service.impl;

import com.nse.pms.standard.common.service.ConstraintService;
import com.nse.pms.standard.ctq.service.NseCtqService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import select.spring.exquery.service.ExqueryService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("ctqService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class NseCtqServiceImpl implements NseCtqService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExqueryService exqueryService;

	@Autowired
	private ConstraintService constraintService;

	/**
	 * CTQ Tree List
	 * 
	 * @param
	 * @return List<HashMap<String, Object>>
	 **/
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>> selectCtqTreeList(HashMap<String, Object> param) {

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		resultList = exqueryService.selectList("nse.pms.ctq.sqlCtq.selectCtqTreeList", param);

		return resultList;
	}

	/**
	 * CTQ Tree List
	 * 
	 * @param
	 * @return List<HashMap<String, Object>>
	 **/
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>> selectCtqTreeListUseCtrl(HashMap<String, Object> param) {

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		resultList = exqueryService.selectList("nse.pms.ctq.sqlCtq.selectCtqTreeListUseCtrl", param);

		return resultList;
	}

	/**
	 * I-MR Chrt, Ctq Chart
	 * 
	 * @param
	 * @return List<HashMap<String, Object>>
	 **/
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>> selectCtqCtrlChart(HashMap<String, Object> param) {

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		// 조회기간 String 조회
		HashMap<String, Object> dateMap = exqueryService.selectOne("nse.pms.ctq.sqlCtq.selectSearchRangeStr", param);

		// 차트종류에 따른 분기처리
		String ctrlType = param.get("ctrlType").toString();

		if (ctrlType.equals("IMR")) {
			HashMap<String, Object> iChartMap = new HashMap<String, Object>();
			iChartMap = selectIChart(param);
			iChartMap.put("title", "I-CHART");
			iChartMap.put("startTimeStr", dateMap.get("startTimeStr"));
			iChartMap.put("endTimeStr", dateMap.get("endTimeStr"));
			resultList.add(iChartMap);

			HashMap<String, Object> mrChartMap = new HashMap<String, Object>();
			mrChartMap = selectMrChart(param);
			mrChartMap.put("title", "MR-CHART");
			mrChartMap.put("startTimeStr", dateMap.get("startTimeStr"));
			mrChartMap.put("endTimeStr", dateMap.get("endTimeStr"));
			resultList.add(mrChartMap);

		} else if (ctrlType.equals("XBARS")) {

		}

		HashMap<String, Object> ctqChartMap = new HashMap<String, Object>();
		
		ctqChartMap.put("chartData", selectCtqValueList(param));
		ctqChartMap.put("title", "CTQ-CHART");
		ctqChartMap.put("startTimeStr", dateMap.get("startTimeStr"));
		ctqChartMap.put("endTimeStr", dateMap.get("endTimeStr"));
		resultList.add(ctqChartMap);
		// 기본 CTQ데이터 처리

		return resultList;
	}

	/**
	 * I Chrt
	 * 
	 * @param
	 * @return List<HashMap<String, Object>>
	 **/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HashMap<String, Object> selectIChart(HashMap<String, Object> param) {

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> resultlMap = new HashMap<String, Object>(); // Cl, UCL, UCl

		// Type : I (측정별)
		if (param.get("searchType").equals("I")) {
			resultList = exqueryService.selectList("nse.pms.ctq.sqlCtq.selectIchartListMeasure", param);
			resultlMap = exqueryService.selectOne("nse.pms.ctq.sqlCtq.selectIChartClMeasure", param);
		} else {
			resultList = exqueryService.selectList("nse.pms.ctq.sqlCtq.selectIChartList", param);
			resultlMap = exqueryService.selectOne("nse.pms.ctq.sqlCtq.selectIChartCl", param);
		}
		if (resultlMap == null) {
			resultlMap = new HashMap<String, Object>();
			resultlMap.put("chartData", resultList);
		} else {
			resultlMap.put("chartData", resultList);
		}

		return resultlMap;
	}

	/**
	 * Mr Chrt
	 * 
	 * @param
	 * @return List<HashMap<String, Object>>
	 **/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HashMap<String, Object> selectMrChart(HashMap<String, Object> param) {

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> resultlMap = new HashMap<String, Object>(); // Cl, UCL, UCl

		// Type : I (측정별)
		if (param.get("searchType").equals("I")) {
			resultList = exqueryService.selectList("nse.pms.ctq.sqlCtq.selectMrChartListMeasure", param);
			resultlMap = exqueryService.selectOne("nse.pms.ctq.sqlCtq.selectMrChartClMeasure", param);
		} else {
			resultList = exqueryService.selectList("nse.pms.ctq.sqlCtq.selectMrChartList", param);
			resultlMap = exqueryService.selectOne("nse.pms.ctq.sqlCtq.selectMrChartCl", param);
		}
		if (resultlMap == null) {
			resultlMap = new HashMap<String, Object>();
			resultlMap.put("chartData", resultList);
		} else {
			resultlMap.put("chartData", resultList);
		}

		return resultlMap;
	}

	/**
	 * Ctq Data
	 * 
	 * @param
	 * @return List<HashMap<String, Object>>
	 **/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<HashMap<String, Object>> selectCtqValueList(HashMap<String, Object> param) {

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		// Type : I (측정별)
		if (param.get("searchType").equals("I")) {
			resultList = exqueryService.selectList("nse.pms.ctq.sqlCtq.selectIchartListMeasure", param);
		} else {
			resultList = exqueryService.selectList("nse.pms.ctq.sqlCtq.selectIChartList", param);
		}
		

		return resultList;
	}

	/**
	 * CTQ Trend Chart List
	 * 
	 * @param
	 * @return List<HashMap<String, Object>>
	 **/
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>> selectCtqTrendChart(HashMap<String, Object> param) throws Exception {
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		// json ArrayList를 Java List로 변환
		List<HashMap<String, Object>> ctqList = new ArrayList<HashMap<String, Object>>();
		ObjectMapper mapper = new ObjectMapper();

		// 조회기간 String 조회
		HashMap<String, Object> dateMap = exqueryService.selectOne("nse.pms.ctq.sqlCtq.selectSearchRangeStr", param);

		// Type : I (측정별),
		String queryStr = "";
		if (param.get("searchType").equals("I")) {
			queryStr = "nse.pms.ctq.sqlCtq.selectTrendChartListMeasure";
		} else {
			queryStr = "nse.pms.ctq.sqlCtq.selectTrendChartListSum";
		}

		ctqList = mapper.readValue(param.get("ctqList").toString(), new TypeReference<ArrayList<HashMap<String, Object>>>(){});
		for (int idx = 0, len = ctqList.size(); idx < len; idx++) {
			ctqList.get(idx).put("fromDate", param.get("fromDate"));
			ctqList.get(idx).put("toDate", param.get("toDate"));
			ctqList.get(idx).put("ssUserLang", param.get("ssUserLang"));
			ctqList.get(idx).put("searchType", param.get("searchType"));

			List<HashMap<String, Object>> chartData = exqueryService.selectList(queryStr, ctqList.get(idx));
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

		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>> selectCtqTrendChart2(HashMap<String, Object> param) throws Exception {
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		// json ArrayList를 Java List로 변환
		List<HashMap<String, Object>> ctqList = new ArrayList<HashMap<String, Object>>();
		ObjectMapper mapper = new ObjectMapper();


		// Type : I (측정별),
		String queryStr = "";
		if (param.get("searchType").equals("I")) {
			queryStr = "nse.pms.ctq.sqlCtq.selectTrendChartListMeasure";
		} else {
			queryStr = "nse.pms.ctq.sqlCtq.selectTrendChartListSum";
		}

		int index = 0;

		ctqList = mapper.readValue(param.get("ctqList").toString(), new TypeReference<ArrayList<HashMap<String, Object>>>(){});
		for (int idx = 0, len = ctqList.size(); idx < len; idx++) {

			ctqList.get(idx).put("fromDate", ctqList.get(idx).get("fromDateStr"));
			ctqList.get(idx).put("toDate", ctqList.get(idx).get("toDateStr"));
			ctqList.get(idx).put("ssUserLang", param.get("ssUserLang"));
			ctqList.get(idx).put("searchType", param.get("searchType"));

			// 조회기간 String 조회
			HashMap<String, Object> dateMap = exqueryService.selectOne("nse.pms.ctq.sqlCtq.selectSearchRangeStr", ctqList.get(idx));

			List<HashMap<String, Object>> chartData = exqueryService.selectList(queryStr, ctqList.get(idx));
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

			index = index+1;
			chartMap.put("index", index);

			resultList.add(chartMap);
		}

		return resultList;
	}

    /**
     * CTQ Trend Chart List
     *
     * @param
     * @return List<HashMap<String, Object>>
     **/
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<HashMap<String, Object>> selectCorrelTagDataList(HashMap<String, Object> param) throws Exception {
        List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

        // json ArrayList를 Java List로 변환
        List<HashMap<String, Object>> ctqList = new ArrayList<HashMap<String, Object>>();
        ObjectMapper mapper = new ObjectMapper();

        // 조회기간 String 조회
        HashMap<String, Object> dateMap = exqueryService.selectOne("nse.pms.ctq.sqlCtq.selectSearchRangeStr", param);

        // Type : I (측정별),
        String queryStr = "";
        if (param.get("searchType").equals("I")) {
            queryStr = "nse.pms.ctq.sqlCtq.selectTrendChartListMeasure";
        } else {
            queryStr = "nse.pms.ctq.sqlCtq.selectTrendChartListSum";
        }

        ctqList = mapper.readValue(param.get("ctqList").toString(), new TypeReference<ArrayList<HashMap<String, Object>>>(){});
        for (int idx = 0, len = ctqList.size(); idx < len; idx++) {
            ctqList.get(idx).put("fromDate", param.get("fromDate"));
            ctqList.get(idx).put("toDate", param.get("toDate"));
            ctqList.get(idx).put("ssUserLang", param.get("ssUserLang"));
            ctqList.get(idx).put("searchType", param.get("searchType"));

            List<HashMap<String, Object>> realData = exqueryService.selectList(queryStr, ctqList.get(idx));

            HashMap<String, Object> pagingData = new HashMap<String, Object>();
            pagingData = exqueryService.selectPagingList(queryStr, ctqList.get(idx),
                    Integer.parseInt(param.get("pageNo").toString()),
                    Integer.parseInt(param.get("pageSize").toString()));

            HashMap<String, Object> chartMap = new HashMap<String, Object>();

            chartMap.put("startTimeStr", dateMap.get("startTimeStr"));
            chartMap.put("endTimeStr", dateMap.get("endTimeStr"));
            chartMap.put("ctqCd", ctqList.get(idx).get("ctqCd"));
            chartMap.put("ctqNm", ctqList.get(idx).get("ctqNm"));
            chartMap.put("tagCd", ctqList.get(idx).get("tagCd"));
            chartMap.put("unit", ctqList.get(idx).get("unit"));
            chartMap.put("chartType", ctqList.get(idx).get("chartType"));
            chartMap.put("fillYn", ctqList.get(idx).get("fillYn"));
            chartMap.put("pagingData", pagingData);
            chartMap.put("realData", realData);

            resultList.add(chartMap);
        }

        return resultList;
    }
	
	
	/**
	 * CTQ HISTORY List
	 * @param
	 * @return List<HashMap<String, Object>>
	 **/
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>> selectCtqHistoryList(HashMap<String, Object> param) throws Exception {

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>(); 
		List<HashMap<String, Object>> ctqList = new ArrayList<HashMap<String, Object>>();    // json ArrayList => java List로 변환
		ObjectMapper mapper = new ObjectMapper();
		String mapId = "";
		
		// 조회기간 String 조회
		HashMap<String, Object> dateMap = exqueryService.selectOne("nse.pms.ctq.sqlCtq.selectSearchRangeStr", param);
		ctqList = mapper.readValue(param.get("ctqList").toString(), new TypeReference<ArrayList<HashMap<String, Object>>>() {});
		
		// Type : I (측정별)
		if (param.get("searchType").equals("I")) {
			mapId = "nse.pms.ctq.sqlCtq.selectCtqChartListMeasure";
		} else {
			mapId = "nse.pms.ctq.sqlCtq.selectCtqChartList";
		}
		
		
		for (int idx = 0, len = ctqList.size(); idx < len; idx++) {
			
			ctqList.get(idx).put("ssUserLang", param.get("ssUserLang"));
			ctqList.get(idx).put("fromDate", param.get("fromDate"));
			ctqList.get(idx).put("toDate", param.get("toDate"));
			ctqList.get(idx).put("searchType", param.get("searchType"));
			
			//CtqHistory List
			List<HashMap<String, Object>> chartData = exqueryService.selectList(mapId, ctqList.get(idx));
			HashMap<String, Object> chartMap = new HashMap<String, Object>();

			chartMap.put("startTimeStr", dateMap.get("startTimeStr"));
			chartMap.put("endTimeStr", dateMap.get("endTimeStr"));
			chartMap.put("ctqCd", ctqList.get(idx).get("ctqCd"));
			chartMap.put("ctqNm", ctqList.get(idx).get("ctqNm"));
			chartMap.put("tagCd", ctqList.get(idx).get("tagCd"));
			chartMap.put("ctqHisList", chartData);

			resultList.add(chartMap);
		}

		return resultList;
	}
	
	
	/**
	 * Ctq Compare List [시간별 ctq 차이 조회]
	 * 
	 * @param
	 * @return List<HashMap<String, Object>>
	 **/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<HashMap<String, Object>> selectCtqComparisonList(HashMap<String, Object> param) {

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultList = exqueryService.selectList("nse.pms.ctq.sqlCtq.selectCtqComparisonList", param);
		
		return resultList;
	}


	/**
	 * [CTQ간 비교조회] CTQ Multi Chart List
	 *
	 * @param
	 * @return List<HashMap<String, Object>>
	 **/
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>> selectCtqMultiChart(HashMap<String, Object> param) throws Exception {
		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		// json ArrayList를 Java List로 변환
		List<HashMap<String, Object>> ctqList = new ArrayList<HashMap<String, Object>>();
		ObjectMapper mapper = new ObjectMapper();

		// 조회기간 String 조회
		HashMap<String, Object> dateMap = exqueryService.selectOne("nse.pms.ctq.sqlCtq.selectSearchRangeStr", param);

		ctqList = mapper.readValue(param.get("ctqList").toString(),
				new TypeReference<ArrayList<HashMap<String, Object>>>() {
				});

		for (int idx = 0, len = ctqList.size(); idx < len; idx++) {
			ctqList.get(idx).put("fromDate", param.get("fromDate"));
			ctqList.get(idx).put("toDate", param.get("toDate"));
			ctqList.get(idx).put("ssUserLang", param.get("ssUserLang"));

			List<HashMap<String, Object>> chartData = exqueryService.selectList("nse.pms.ctq.sqlCtq.selectTrendChartListMeasure", ctqList.get(idx));
			HashMap<String, Object> chartMap = new HashMap<String, Object>();

			chartMap.put("startTimeStr", dateMap.get("startTimeStr"));
			chartMap.put("endTimeStr", dateMap.get("endTimeStr"));
			chartMap.put("ctqCd", ctqList.get(idx).get("ctqCd"));
			chartMap.put("ctqNm", ctqList.get(idx).get("ctqNm"));
			chartMap.put("tagCd", ctqList.get(idx).get("tagCd"));
			chartMap.put("chartData", chartData);

			resultList.add(chartMap);
		}

		return resultList;
	}


	/**
	 * CTQ 즐겨찾기 추가
	 * @param
	 * @return HashMap<String, Object>
	 **/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String,Object> insertCtqFavorite(HashMap<String, Object> param) throws Exception {

		HashMap<String,Object> resultHashMap = new HashMap<String,Object>();
		List<HashMap<String,Object>> dataList = new ArrayList<HashMap<String,Object>>();

		ObjectMapper mapper = new ObjectMapper();
		dataList =  mapper.readValue(param.get("ctqList").toString(), new TypeReference<ArrayList<HashMap<String, Object>>>() {});

		// 1. 삭체 처리
		exqueryService.delete("nse.pms.ctq.sqlCtq.deleteCtqFavorite", param);

		// 2. Checked 데이터 INSERT
		if(dataList.size()>0) {
			for(int i=0; i<dataList.size(); i++) {
				dataList.get(i).put("ssUserId", param.get("ssUserId"));
				exqueryService.insert("nse.pms.ctq.sqlCtq.insertCtqFavorite", dataList.get(i));
			}
		}
		resultHashMap.put("isUpdate", true);
		return resultHashMap;
	}

	/**
	 * CTQ 컨티션 모니터링 추가
	 * @param
	 * @return HashMap<String, Object>
	 **/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String,Object> insertCtqMoniCond(HashMap<String, Object> param) throws Exception {

		HashMap<String,Object> resultHashMap = new HashMap<String,Object>();
		List<HashMap<String,Object>> dataList = new ArrayList<HashMap<String,Object>>();

		ObjectMapper mapper = new ObjectMapper();
		dataList =  mapper.readValue(param.get("ctqList").toString(), new TypeReference<ArrayList<HashMap<String, Object>>>() {});

		// 1. 삭체 처리
		exqueryService.delete("nse.pms.ctq.sqlCtq.deleteCtqMoniCond", param);

		// 2. Checked 데이터 INSERT
		if(dataList.size()>0) {
			for(int i=0; i<dataList.size(); i++) {
				dataList.get(i).put("ssUserId", param.get("ssUserId"));
				dataList.get(i).put("type", param.get("type"));
				exqueryService.insert("nse.pms.ctq.sqlCtq.insertCtqMoniCond", dataList.get(i));
			}
		}
		resultHashMap.put("isUpdate", true);
		return resultHashMap;
	}


	/**
	 * CTQ 즐겨찾기 조회
	 * @param
	 * @return HashMap<String, Object>
	 **/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public List<HashMap<String, Object>> selectCtqFavorite(HashMap<String, Object> param){

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		resultList = exqueryService.selectList("nse.pms.ctq.sqlCtq.selectCtqFavorite", param);

		return resultList;
	}


	/**
	 * CTQ 즐겨찾기 조회
	 * @param
	 * @return HashMap<String, Object>
	 **/
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public List<HashMap<String, Object>> selectCtqMoniCond(HashMap<String, Object> param){

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();

		resultList = exqueryService.selectList("nse.pms.ctq.sqlCtq.selectCtqMoniCond", param);

		return resultList;
	}
	
	/**
	 * 시간별 CTQ 비교조회
	 * @param
	 * @return List<HashMap<String, Object>>
	 **/
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>> selectCtqCompareList(HashMap<String, Object> param) throws Exception {

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>(); 
		List<HashMap<String, Object>> searchList = new ArrayList<HashMap<String, Object>>();
		ObjectMapper mapper = new ObjectMapper();
		searchList = mapper.readValue(param.get("ctqList").toString(), new TypeReference<ArrayList<HashMap<String, Object>>>() {});
		param.put("list", searchList);
		
		// Type : I (측정별)
		if (param.get("searchType").equals("I")) {
			resultList = exqueryService.selectList("nse.pms.ctq.sqlCtq.selectCtqCompareValueList", param);
		} else {
			resultList = exqueryService.selectList("nse.pms.ctq.sqlCtq.selectCtqCompareSumList", param);
		}
				
		return resultList;
	}


	/**
	 * 접점 정보조회
	 *
	 * @param
	 * @return List<HashMap<String, Object>>
	 **/
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> selectTagInfo(HashMap<String, Object> param) throws Exception{

		HashMap<String, Object> resultlMap = new HashMap<String, Object>();

		resultlMap = exqueryService.selectOne("nse.pms.ctq.sqlCtq.selectTagInfo", param);

		return resultlMap;
	}

	/**
	 * 상관분석 데이터 삭제
	 *sqlCtqsqlCtq
	 * @param
	 * @return List<HashMap<String, Object>>
	 **/
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public HashMap<String, Object> deleteCtqMultipleCorrelate(HashMap<String, Object> param) throws Exception{

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		exqueryService.selectList("nse.pms.ctq.sqlCtq.deleteCtqMultipleCorrelate", param);
		return resultMap;

	}

    /**
     * CTQ 상관분석 SEQ 조회
     *
     * @param
     * @return List<HashMap<String, Object>>
     **/
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<HashMap<String, Object>> selectCorrelDataIdx(HashMap<String, Object> param) {

        List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
        resultList = exqueryService.selectList("nse.pms.ctq.sqlCtq.selectCorrelDataIdx", param);
        return resultList;

    }

	/**
	 * CTQ 상관분석 INSERT
	 */
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public HashMap<String,Object> insertCtqMultipleCorrelate(HashMap<String, Object> param) throws Exception {
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, Object>> tagList = mapper.readValue(param.get("tagList").toString(), new TypeReference<ArrayList<HashMap<String, Object>>>(){});


		java.util.Random rdKey = new java.util.Random(System.currentTimeMillis());
		String keyValue =  String.valueOf(rdKey);
		int seq =  Integer.parseInt(param.get("seq").toString());

		param.put("key",keyValue);
		param.put("list",tagList);
		exqueryService.insert("nse.pms.basic.sqlTagCode.insertCtqMultipleCorrelate", param);

		resultMap.put("isUpdate", true);
		resultMap.put("resultKey",keyValue);

		return resultMap;
	}

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<HashMap<String, Object>> selectCorrelateChartList(HashMap<String, Object> param) {


        List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
        resultList = exqueryService.selectList("nse.pms.ctq.sqlCtq.selectCorrelateChartList", param);



        return resultList;
    }

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<HashMap<String, Object>> selectWeekList(HashMap<String, Object> param) {

		List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		resultList = exqueryService.selectList("nse.pms.ctq.sqlCtq.selectWeekList", param);

		return resultList;
	}

}
