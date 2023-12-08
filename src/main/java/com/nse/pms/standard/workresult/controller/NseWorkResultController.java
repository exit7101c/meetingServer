package com.nse.pms.standard.workresult.controller;

import com.nse.config.Common;
import com.nse.config.excel.download.ExcelCreator;
import com.nse.pms.standard.workresult.service.NseWorkResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import select.spring.exquery.service.ExqueryService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "workResult")
public class NseWorkResultController {
	@Autowired
	private NseWorkResultService nseWorkResultService;

	/**
	 * 작업지시를 조회한다.
	 */
	@RequestMapping(value = "selectWorkResultList", method = { RequestMethod.GET, RequestMethod.POST })
	public List<HashMap<String, Object>> selectWorkResultList(@RequestParam HashMap<String, Object> param,
			ModelMap model) throws Exception {
		return nseWorkResultService.selectWorkResultList(param);
	}

	/**
	 * MES 작업지시 수신.
	 */
	@RequestMapping(value = "executeIFRWorkResult", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> executeIFRWorkResult(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseWorkResultService.executeIFRWorkResult(param);
	}

	/**
	 * Lot tracking.
	 */
	@RequestMapping(value = "selectWorkResultTree", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> selectWorkResultTree(@RequestParam HashMap<String, Object> param,
			ModelMap model) throws Exception {
		return nseWorkResultService.selectWorkResultTree(param);
	}

	/**
	 * Line - Tag List
	 */
	@RequestMapping(value = "selectLineProcEqpTagList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> selectLineProcEqpTagList(@RequestParam HashMap<String, Object> param,
			ModelMap model) throws Exception {
		return nseWorkResultService.selectLineProcEqpTagList(param);
	}

	/**
	 * Work Condition
	 **/
	@RequestMapping(value = "selectWorkConditionList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> selectWorkConditionList(@RequestParam HashMap<String, Object> param,
			ModelMap model) throws Exception {
		return nseWorkResultService.selectWorkConditionList(param);
	}
}