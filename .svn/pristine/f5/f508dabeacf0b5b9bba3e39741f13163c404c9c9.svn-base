package com.nse.pms.standard.common.controller;

import com.nse.pms.standard.common.service.CommonModalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "commonModal")
public class CommonModalController {

	@Autowired
	private CommonModalService commonModalService;

	/**
	 * [공통 모달창] 접점 상세보기 - 테이블,차트 데이터 조회
	 * @throws Exception
	 **/
	@RequestMapping(value = "selectTagChartList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> selectTagChartList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return commonModalService.selectTagChartList(param);
	}

	@RequestMapping(value = "selectTagChartListExcel", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> selectTagChartListExcel(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return commonModalService.selectTagChartListExcel(param);
	}

	/**
	 * [공통 모달창] 알람 상세보기 - 테이블 데이터 조회
	 * @throws Exception
	 **/
	@RequestMapping(value = "selectAlarmList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> selectAlarmList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return commonModalService.selectAlarmList(param);
	}

	@RequestMapping(value = "selectAlarmListExcel", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> selectAlarmListExcel(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return commonModalService.selectAlarmListExcel(param);
	}

	/**
	 * [공통 모달창] 가동 상세보기 - 테이블 데이터 조회
	 * @throws Exception
	 **/
	@RequestMapping(value = "selectEqpStateList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> selectEqpStateList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return commonModalService.selectEqpStateList(param);
	}

	@RequestMapping(value = "selectEqpStateListExcel", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> selectEqpStateListExcel(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return commonModalService.selectEqpStateListExcel(param);
	}

}