package com.cronies.smartmes.controller;

import com.cronies.smartmes.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(value = "mesManager")
public class ManagerController {

	@Autowired
	private ManagerService managerService;

	@RequestMapping(value = "selectManagerOne", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> selectManagerOne(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return managerService.selectManagerOne(param);
	}

	@RequestMapping(value = "insertManager", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> insertManager(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return managerService.insertManager(param);
	}

	@RequestMapping(value = "updateManager", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> updateManager(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return managerService.updateManager(param);
	}

	@RequestMapping(value = "deleteManager", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> deleteManager(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return managerService.deleteManager(param);
	}

}