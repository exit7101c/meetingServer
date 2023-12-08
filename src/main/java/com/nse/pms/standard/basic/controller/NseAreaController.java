package com.nse.pms.standard.basic.controller;

import com.nse.config.Common;
import com.nse.pms.standard.basic.service.NseAreaService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping(value="area")
public class NseAreaController {

	@Autowired
	private NseAreaService nseAreaService;

	/**
	* Plant 정보 조회
	* @param 
	* @param 
	* @return 
	**/
	@RequestMapping( value="selectAreaList", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<HashMap<String, Object>> selectAreaList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseAreaService.selectAreaList(param);
	}
	
	@RequestMapping( value="selectAreaOne", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> selectAreaOne(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseAreaService.selectAreaOne(param);
	}
	
	@RequestMapping( value="insertArea", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> insertArea(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseAreaService.insertArea(param);
	}

	@RequestMapping( value="updateArea", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> updateArea(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseAreaService.updateArea(param);
	}
	
	@RequestMapping( value="deleteArea", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> deleteArea(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseAreaService.deleteArea(param);
	}
	
	@RequestMapping( value="selectComAreaList", method = {RequestMethod.GET, RequestMethod.POST})
	public List<HashMap<String, Object>> selectComAreaList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseAreaService.selectComAreaList(param);
	}
}