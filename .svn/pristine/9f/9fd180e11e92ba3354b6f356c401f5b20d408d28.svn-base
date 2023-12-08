package com.nse.pms.standard.his.controller;

import com.nse.pms.standard.his.service.NseHisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(value = "his")
public class NseHisController {

	@Autowired
	private NseHisService NseHisService;

	@RequestMapping(value = "selectHisWeatherChart", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> selectHisWeatherChart(@RequestParam HashMap<String, Object> param,
			ModelMap model) throws Exception {
		return NseHisService.selectHisWeatherChart(param);
	}

}