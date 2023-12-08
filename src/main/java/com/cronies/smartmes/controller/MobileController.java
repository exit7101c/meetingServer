package com.cronies.smartmes.controller;

import com.cronies.smartmes.service.MobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(value = "mesMobile")
public class MobileController {

	@Autowired
	private MobileService mobileService;

	@RequestMapping(value = "getBaseInfo", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> getBaseInfo(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return mobileService.getBaseInfo(param);
	}

	@RequestMapping(value = "startRun", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> startRun(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return mobileService.startRun(param);
	}

	@RequestMapping(value = "stopRun", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> stopRun(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return mobileService.stopRun(param);
	}

	@RequestMapping(value = "regProduction", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> regProduction(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return mobileService.regProduction(param);
	}

	@RequestMapping(value = "getTodayProduction", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> getTodayProduction(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return mobileService.getTodayProduction(param);
	}

	@RequestMapping(value = "checkRunYnState", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> checkRunYnState(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return mobileService.checkRunYnState(param);
	}

	@RequestMapping(value = "deleteInput", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> deleteInput(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return mobileService.deleteInput(param);
	}

	@RequestMapping(value = "getProcListByLineChange", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> getProcListByLineChange(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return mobileService.getProcListByLineChange(param);
	}

}