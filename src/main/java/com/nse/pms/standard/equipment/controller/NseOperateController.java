package com.nse.pms.standard.equipment.controller;

import com.nse.pms.standard.equipment.service.NseOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value="operate")
public class NseOperateController {

	@Autowired
	private NseOperateService nseOperateService;
	
	
	@RequestMapping(value ="selectEquipmentOperationList", method = {RequestMethod.GET, RequestMethod.POST})
	public List<HashMap<String,Object>> selectEquipmentOperationList(@RequestParam HashMap<String,Object> param, ModelMap model) throws Exception {
		return nseOperateService.selectEquipmentList(param);
	}
	
	@RequestMapping(value="selectEquipmentRunHis", method = {RequestMethod.GET, RequestMethod.POST})
	public List<HashMap<String,Object>> selectEquipmentRunHis(@RequestParam HashMap<String,Object> param, ModelMap model) throws Exception {
		return nseOperateService.selectEquipmentRunHis(param);
	}

	@RequestMapping(value ="selectEquipmentAlarmHis", method = {RequestMethod.GET, RequestMethod.POST})
	public List<HashMap<String,Object>> selectEquipmentAlarmHis(@RequestParam HashMap<String,Object> param, ModelMap model) throws Exception {
		return nseOperateService.selectEquipmentAlarmHis(param);
	}
	
}