package com.cronies.smartmes.controller;

import com.cronies.smartmes.service.ProcService;
import com.cronies.smartmes.service.ProdRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "proc")
public class ProcController {

	@Autowired
	private ProcService procService;

	/**
	 * 	공정 한건 조회
	 * */
	@RequestMapping(value = "selectProcOne", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> selectProcOne(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return procService.selectProcOne(param);
	}

	@RequestMapping(value = "insertProcess", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> insertProcess(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return procService.insertProcess(param);
	}

	@RequestMapping(value = "updateProcess", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> updateProcess(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return procService.updateProcess(param);
	}

	@RequestMapping(value = "deleteProcess", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> deleteProcess(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return procService.deleteProcess(param);
	}


}