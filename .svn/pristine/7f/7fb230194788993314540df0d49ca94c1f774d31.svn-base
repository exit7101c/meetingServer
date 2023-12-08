package com.nse.pms.standard.basic.controller;

import com.nse.config.Common;
import com.nse.config.excel.download.ExcelCreator;
import com.nse.pms.standard.basic.service.NseAlarmCodeService;
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
@RequestMapping(value = "alarmCode")
public class NseAlarmCodeController {

	@Autowired
	private NseAlarmCodeService nseAlarmCodeService;

	@RequestMapping(value = "selectAlarmCodeList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> selectAlarmCodeList(@RequestParam HashMap<String, Object> param,
			ModelMap model) throws Exception {
		return nseAlarmCodeService.selectAlarmCodeList(param);
	}

	@RequestMapping(value = "selectAlarmCodeOne", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> selectAlarmCodeOne(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseAlarmCodeService.selectAlarmCodeOne(param);
	}

	@RequestMapping(value = "insertAlarmCode", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> insertAlarmCode(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseAlarmCodeService.insertAlarmCode(param);
	}

	@RequestMapping(value = "updateAlarmCode", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> updateAlarmCode(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseAlarmCodeService.updateAlarmCode(param);
	}

	@RequestMapping(value = "deleteAlarmCode", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> deleteAlarmCode(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseAlarmCodeService.deleteAlarmCode(param);
	}
	
	@RequestMapping(value = "selectStateCodeList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> selectStateCodeList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseAlarmCodeService.selectStateCodeList(param);
	}
	
	@RequestMapping(value = "selectStateCodeOne", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> selectStateCodeOne(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseAlarmCodeService.selectStateCodeOne(param);
	}
	
	@RequestMapping(value = "insertStateCode", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> insertStateCode(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseAlarmCodeService.insertStateCode(param);
	}
	
	@RequestMapping(value = "updateStateCode", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> updateStateCode(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseAlarmCodeService.updateStateCode(param);
	}
	
	@RequestMapping(value = "deleteStateCode", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> deleteStateCode(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseAlarmCodeService.deleteStateCode(param);
	}
		
}