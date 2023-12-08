package com.nse.pms.standard.monitoring.controller;

import com.nse.pms.standard.monitoring.service.NseEnergyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "energy")
public class NseEnergyController {

	@Autowired
	private NseEnergyService nseEnergyService;

	@RequestMapping(value = "selectPowerUsageList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> selectPowerUsageList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseEnergyService.selectPowerUsageList(param);
	}

	@RequestMapping(value = "selectPowerUsageStateList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> selectPowerUsageStateList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseEnergyService.selectPowerUsageStateList(param);
	}

	@RequestMapping(value = "selectEngTagCurrentList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> selectEngTagCurrentList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseEnergyService.selectEngTagCurrentList(param);
	}


}