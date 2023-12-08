package com.nse.pms.standard.basic.controller;

import com.nse.config.Common;
import com.nse.config.excel.download.ExcelCreator;
import com.nse.pms.standard.basic.service.NseProdService;
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
@RequestMapping(value = "prod")
public class NseProdController {

	@Autowired
	private NseProdService NseProdService;

	@RequestMapping(value = "selectProdList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> selectProdList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return NseProdService.selectProdList(param);
	}

	@RequestMapping(value = "selectProdOne", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> selectProdOne(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return NseProdService.selectProdOne(param);
	}

	@RequestMapping(value = "selectSpecProdList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> selectSpecProdList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return NseProdService.selectSpecProdList(param);
	}

	@RequestMapping(value = "selectSpecTagList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> selectSpecTagList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return NseProdService.selectSpecTagList(param);
	}

	@RequestMapping(value = "selectProdModalList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> selectProdModalList(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return NseProdService.selectProdModalList(param);
	}

	@RequestMapping(value = "selectTagClModalList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> selectTagClModalList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return NseProdService.selectTagClModalList(param);
	}

	@RequestMapping(value = "selectProcStabilityList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> selectProcStabilityList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return NseProdService.selectProcStabilityList(param);
	}

	@RequestMapping(value = "selectAutoProdSpecSetting", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> selectAutoProdSpecSetting(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return NseProdService.selectAutoProdSpecSetting(param);
	}

	@RequestMapping( value="updateAutoProdSpecSetting", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> updateAutoProdSpecSetting(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return NseProdService.updateAutoProdSpecSetting(param);
	}

	@RequestMapping( value="mappingAreaProd", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> mappingAreaProd(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return NseProdService.mappingAreaProd(param);
	}
}