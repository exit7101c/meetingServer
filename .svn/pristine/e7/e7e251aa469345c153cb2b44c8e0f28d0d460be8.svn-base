package com.nse.pms.standard.monitoring.controller;

import com.nse.pms.standard.his.service.NseHisService;
import com.nse.pms.standard.monitoring.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "monitoring")
public class MainController {

	@Autowired
	private MainService mainService;

	@RequestMapping(value = "selectLineMoni", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> selectLineMoni(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return mainService.selectLineMoni(param);
	}

	@RequestMapping(value = "selectCtqMoniRange", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> selectCtqMoniRange(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return mainService.selectCtqMoniRange(param);
	}

	@RequestMapping(value = "selectEqpInfo", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> selectEqpInfo(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return mainService.selectEqpInfo(param);
	}

	@RequestMapping(value = "selectClock", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> selectClock(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return mainService.selectClock(param);
	}

    @RequestMapping(value = "selectTagCl", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public HashMap<String, Object> selectTagCl(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
        return mainService.selectTagCl(param);
    }

	@RequestMapping(value = "updateTagCl", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> updateTagCl(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return mainService.updateTagCl(param);
	}

	@RequestMapping(value = "selectProdList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> selectProdList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return mainService.selectProdList(param);
	}

	@RequestMapping(value = "selectProdOne", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> selectProdOne(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return mainService.selectProdOne(param);
	}

	@RequestMapping(value = "updateEqpAlarmOff", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> updateEqpAlarmOff(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return mainService.updateEqpAlarmOff(param);
	}

	@RequestMapping(value = "selectMoniCondition", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> selectMoniCondition(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return mainService.selectMoniCondition(param);
	}

}