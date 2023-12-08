package com.nse.pms.standard.basic.controller;

import com.nse.config.Common;
import com.nse.pms.standard.basic.service.NsePlantService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping(value="plant")
public class NsePlantController {

	@Autowired
	private NsePlantService nsePlantService;

	/**
	* Plant 정보 조회
	* @param 
	* @param 
	* @return 
	**/
	@RequestMapping( value="selectPlantList", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<HashMap<String, Object>> selectPlantList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nsePlantService.selectPlantList(param);
	}
	
	@RequestMapping( value="selectPlantOne", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> selectPlantOne(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nsePlantService.selectPlantOne(param);
	}
	
	@RequestMapping( value="insertPlant", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> insertPlant(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nsePlantService.insertPlant(param);
	}

	@RequestMapping( value="updatePlant", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> updatePlant(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nsePlantService.updatePlant(param);
	}
	
	@RequestMapping( value="deletePlant", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> deletePlant(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nsePlantService.deletePlant(param);
	}
	
	@RequestMapping( value="selectComPlantList", method = {RequestMethod.GET, RequestMethod.POST})
	public List<HashMap<String, Object>> selectComPlantList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nsePlantService.selectComPlantList(param);
	}
}