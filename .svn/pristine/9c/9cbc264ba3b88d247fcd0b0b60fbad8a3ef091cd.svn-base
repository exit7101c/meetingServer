package com.cronies.smartmes.controller;

import com.cronies.smartmes.service.MoniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "mesMoni")
public class MoniController {

	@Autowired
	private MoniService moniService;

	@RequestMapping(value = "selectLineProc", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> selectLineProc(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return moniService.selectLineProc(param);
	}

	@RequestMapping(value = "selectLineProcRun", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> selectLineProcRun(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return moniService.selectLineProcRun(param);
	}

	@RequestMapping(value = "sendLineProcManager", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> sendLineProcManager(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return moniService.sendLineProcManager(param);
	}

}