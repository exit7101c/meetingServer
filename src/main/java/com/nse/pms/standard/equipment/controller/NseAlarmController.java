package com.nse.pms.standard.equipment.controller;

import com.nse.pms.standard.equipment.service.NseAlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value="alarm")
public class NseAlarmController {

	@Autowired
	private NseAlarmService alarmService;
	
	
	@RequestMapping(value ="selectEquipmentList", method = {RequestMethod.GET, RequestMethod.POST})
	public List<HashMap<String,Object>> selectEquipmentList(@RequestParam HashMap<String,Object> param, ModelMap model) throws Exception {
		return alarmService.selectEquipmentList(param);
	}
	
	@RequestMapping(value="selectEquipmentRunHis", method = {RequestMethod.GET, RequestMethod.POST})
	public List<HashMap<String,Object>> selectEquipmentRunHis(@RequestParam HashMap<String,Object> param, ModelMap model) throws Exception {
		return alarmService.selectEquipmentRunHis(param);
	}

	@RequestMapping(value="selectEquipmentRunHisList", method = {RequestMethod.GET, RequestMethod.POST})
	public List<HashMap<String,Object>> selectEquipmentRunHisList(@RequestParam HashMap<String,Object> param, ModelMap model) throws Exception {
		return alarmService.selectEquipmentRunHisList(param);
	}
	
	@RequestMapping(value ="selectEquipmentAlarmHis", method = {RequestMethod.GET, RequestMethod.POST})
	public List<HashMap<String,Object>> selectEquipmentAlarmHis(@RequestParam HashMap<String,Object> param, ModelMap model) throws Exception {
		return alarmService.selectEquipmentAlarmHis(param);
	}
	
	@RequestMapping(value ="selectEquipmentAlarmHisList", method = {RequestMethod.GET, RequestMethod.POST})
	public List<HashMap<String,Object>> selectEquipmentAlarmHisList(@RequestParam HashMap<String,Object> param, ModelMap model) throws Exception {
		return alarmService.selectEquipmentAlarmHisList(param);
	}

	@RequestMapping(value ="selectEquipmentClHisList", method = {RequestMethod.GET, RequestMethod.POST})
	public List<HashMap<String,Object>> selectEquipmentClHisList(@RequestParam HashMap<String,Object> param, ModelMap model) throws Exception {
		return alarmService.selectEquipmentClHisList(param);
	}

	@RequestMapping(value ="selectEquipmentAlarmTrend", method = {RequestMethod.GET, RequestMethod.POST})
	public List<HashMap<String,Object>> selectEquipmentAlarmTrend(@RequestParam HashMap<String,Object> param, ModelMap model) throws Exception {
		return alarmService.selectEquipmentAlarmTrend(param);
	}
	
	@RequestMapping(value ="selectEquipmentAlarmStatsList", method = {RequestMethod.GET, RequestMethod.POST})
	public List<HashMap<String,Object>> selectEquipmentAlarmStatsList(@RequestParam HashMap<String,Object> param, ModelMap model) throws Exception {
		return alarmService.selectEquipmentAlarmStatsList(param);
	}
	
	@RequestMapping(value ="selectAlarmsSats", method = {RequestMethod.GET, RequestMethod.POST})
	public List<HashMap<String,Object>> selectAlarmsSats(@RequestParam HashMap<String,Object> param, ModelMap model) throws Exception {
		return alarmService.selectAlarmsSats(param);
	}
	
	@RequestMapping(value ="selectEquipmentAlarmsSatsByDate", method = {RequestMethod.GET, RequestMethod.POST})
	public List<HashMap<String,Object>> selectEquipmentAlarmsSatsByDate(@RequestParam HashMap<String,Object> param, ModelMap model) throws Exception {
		return alarmService.selectEquipmentAlarmsSatsByDate(param);
	}
}