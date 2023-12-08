package com.nse.pms.standard.report.controller;

import com.nse.pms.standard.report.service.NseReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "report")
public class NseReportController {

	@Autowired
	private NseReportService NseReportService;

	/**
	 * 일지 Document Tree List
	 */
	@RequestMapping(value = "selectDocTreeList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> selectDocTreeList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return NseReportService.selectDocTreeList(param);
	}

	@RequestMapping(value = "selectPtsDocumentList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> selectPtsDocumentList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return NseReportService.selectPtsDocumentList(param);
	}

	/**
	 * 일지 데이터 등록
	 */
	@RequestMapping( value="insertReport", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> insertReport(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return NseReportService.insertReport(param);
	}

	/**
	 * 일지 데이터 조회
	 */
	@RequestMapping( value="selectPtsDocData", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> selectPtsDocData(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return NseReportService.selectPtsDocData(param);
	}

	/**
	 * 일지 데이터 수정
	 */
	@RequestMapping( value="updatePtsDoc", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> updatePtsDoc(@RequestParam HashMap<String, Object> param) throws Exception {
		return NseReportService.updatePtsDoc(param);
	}

	/**
	 * 일지삭제 (삭제여부 'Y'으로 변경)
	 */
	@RequestMapping( value="disablePtsDoc", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> disablePtsDoc(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return NseReportService.disablePtsDoc(param);
	}


}