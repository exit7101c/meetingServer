package com.nse.pms.standard.equipment.controller;

import com.nse.pms.standard.equipment.service.NseRepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value="repair")
public class NseRepairController {

	@Autowired
	private NseRepairService nseRepairService;
	
	
	@RequestMapping(value ="selectProcList", method = {RequestMethod.GET, RequestMethod.POST})
	public List<HashMap<String,Object>> selectProcList(@RequestParam HashMap<String,Object> param, ModelMap model) throws Exception {
		return nseRepairService.selectProcList(param);
	}
	
	@RequestMapping(value="selectEqpList", method = {RequestMethod.GET, RequestMethod.POST})
	public List<HashMap<String,Object>> selectEqpList(@RequestParam HashMap<String,Object> param, ModelMap model) throws Exception {
		return nseRepairService.selectEqpList(param);
	}

	@RequestMapping(value ="selectEquipmentAlarmHis", method = {RequestMethod.GET, RequestMethod.POST})
	public List<HashMap<String,Object>> selectEquipmentAlarmHis(@RequestParam HashMap<String,Object> param, ModelMap model) throws Exception {
		return nseRepairService.selectEquipmentAlarmHis(param);
	}

	@RequestMapping(value ="selectRepairList", method = {RequestMethod.GET, RequestMethod.POST})
	public List<HashMap<String,Object>> selectRepairList(@RequestParam HashMap<String,Object> param, ModelMap model) throws Exception {
		return nseRepairService.selectRepairList(param);
	}

	@RequestMapping( value="selectRepairOne", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> selectRepairOne(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseRepairService.selectRepairOne(param);
	}

	@RequestMapping( value="insertEqpRepair", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> insertEqpRepair(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseRepairService.insertEqpRepair(param);
	}

	@RequestMapping( value="updateEqpRepair", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> updateEqpRepair(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseRepairService.updateEqpRepair(param);
	}

	@RequestMapping( value="deleteEqpRepair", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> deleteEqpRepair(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseRepairService.deleteEqpRepair(param);
	}

	@RequestMapping(value ="selectRepairCharts", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String,Object> selectRepairCharts(@RequestParam HashMap<String,Object> param, ModelMap model) throws Exception {
		return nseRepairService.selectRepairCharts(param);
	}

	/**
	 * 설비 부품수명 관리
	 * **/
	@RequestMapping(value ="selectEqpPartList", method = {RequestMethod.GET, RequestMethod.POST})
	public List<HashMap<String,Object>> selectEqpPartList(@RequestParam HashMap<String,Object> param, ModelMap model) throws Exception {
		return nseRepairService.selectEqpPartList(param);
	}

	@RequestMapping( value="selectEqpPartOne", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> selectEqpPartOne(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseRepairService.selectEqpPartOne(param);
	}

	@RequestMapping( value="insertEqpPart", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> insertEqpPart(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseRepairService.insertEqpPart(param);
	}

	@RequestMapping( value="updateEqpPart", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> updateEqpPart(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseRepairService.updateEqpPart(param);
	}

	@RequestMapping( value="deleteEqpPart", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> deleteEqpPart(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseRepairService.deleteEqpPart(param);
	}

	@RequestMapping(value = "selectEqpPartMainPopup", method = { RequestMethod.GET, RequestMethod.POST })
	public List<HashMap<String, Object>> selectEqpPartMainPopup(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseRepairService.selectEqpPartMainPopup(param);
	}

	@RequestMapping( value="updateEqpPartAlarmOff", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> updateEqpPartAlarmOff(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseRepairService.updateEqpPartAlarmOff(param);
	}


}