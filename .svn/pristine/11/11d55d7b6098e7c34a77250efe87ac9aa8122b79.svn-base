package com.nse.pms.standard.basic.controller;

import com.nse.pms.standard.basic.service.NseWcService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value="wc")
public class NseWcController {


	@Autowired
	private NseWcService NseWcService;
	
	@RequestMapping( value="selectWcList", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<HashMap<String, Object>> selectWcList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return NseWcService.selectWcList(param);
	}
	
	@RequestMapping( value="selectWcOne", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> selectWcOne(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return NseWcService.selectWcOne(param);
	}
	
	@RequestMapping( value="insertWc", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> insertWc(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return NseWcService.insertWc(param);
	}

	@RequestMapping( value="updateWc", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> updateWc(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return NseWcService.updateWc(param);
	}
	
	@RequestMapping( value="deleteWc", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> deleteWc(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return NseWcService.deleteWc(param);
	}
	
}