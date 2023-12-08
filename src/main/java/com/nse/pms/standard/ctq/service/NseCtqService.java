package com.nse.pms.standard.ctq.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.ui.ModelMap;

public interface NseCtqService {

	public List<HashMap<String, Object>> selectCtqTreeList(HashMap<String, Object> param);

	public List<HashMap<String, Object>> selectCtqTreeListUseCtrl(HashMap<String, Object> param);

	public List<HashMap<String, Object>> selectCtqCtrlChart(HashMap<String, Object> param);

	public List<HashMap<String, Object>> selectCtqTrendChart(HashMap<String, Object> param) throws Exception;
	public List<HashMap<String, Object>> selectCtqTrendChart2(HashMap<String, Object> param) throws Exception;

	public List<HashMap<String, Object>> selectCorrelTagDataList(HashMap<String, Object> param) throws Exception;

	public List<HashMap<String, Object>> selectCtqHistoryList(HashMap<String, Object> param) throws Exception;
	
	public List<HashMap<String, Object>> selectCtqComparisonList(HashMap<String, Object> param) throws Exception;

	public List<HashMap<String, Object>> selectCtqMultiChart(HashMap<String, Object> param) throws Exception;
	
	public List<HashMap<String, Object>> selectCtqValueList(HashMap<String, Object> param) throws Exception;

	public HashMap<String, Object> insertCtqFavorite(HashMap<String, Object> param) throws Exception;

	public HashMap<String, Object> insertCtqMoniCond(HashMap<String, Object> param) throws Exception;

	public List<HashMap<String, Object>> selectCtqFavorite(HashMap<String, Object> param) throws Exception;

	public List<HashMap<String, Object>> selectCtqMoniCond(HashMap<String, Object> param) throws Exception;

	public List<HashMap<String, Object>> selectCtqCompareList(HashMap<String, Object> param) throws Exception;

	public HashMap<String, Object> selectTagInfo(HashMap<String, Object> param) throws Exception;

	public HashMap<String, Object> deleteCtqMultipleCorrelate(HashMap<String, Object> param) throws Exception;

    public List<HashMap<String, Object>> selectCorrelDataIdx(HashMap<String, Object> param) throws Exception;

	public HashMap<String, Object> insertCtqMultipleCorrelate(HashMap<String, Object> param) throws Exception;

    public List<HashMap<String, Object>> selectCorrelateChartList(HashMap<String, Object> param) throws Exception;

	public List<HashMap<String, Object>> selectWeekList(HashMap<String, Object> param) throws Exception;

}

