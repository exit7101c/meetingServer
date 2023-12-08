package com.nse.pms.standard.workorder.controller;

import com.nse.config.Common;
import com.nse.config.excel.download.ExcelCreator;
import com.nse.pms.standard.workorder.service.NseWorkOrderService;
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
@RequestMapping(value="workOrder")
public class NseWorkOrderController {
	@Autowired
	private NseWorkOrderService nseWorkOrderService;

	/**
	 * 작업지시를 조회한다.
	 */
	@RequestMapping( value="selectWorkOrderList", method = {RequestMethod.GET, RequestMethod.POST})
	public List<HashMap<String, Object>> selectWorkOrderList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseWorkOrderService.selectWorkOrderList(param);
	}

	@RequestMapping(value = "selectWorkOrderOne", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> selectWorkOrderOne(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseWorkOrderService.selectWorkOrderOne(param);
	}

	/**
	 * MES 작업지시 수신.
	 */
	@RequestMapping(value = "executeIFRWorkOrder", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> executeIFRWorkOrder(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseWorkOrderService.executeIFRWorkOrder(param);
	}

	@RequestMapping(value = "insertWorkOrder", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> insertWorkOrder(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseWorkOrderService.insertWorkOrder(param);
	}

	@RequestMapping(value = "updateWorkOrder", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> updateWorkOrder(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseWorkOrderService.updateWorkOrder(param);
	}

	@RequestMapping(value = "deleteWorkOrder", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> deleteWorkOrder(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseWorkOrderService.deleteWorkOrder(param);
	}
}