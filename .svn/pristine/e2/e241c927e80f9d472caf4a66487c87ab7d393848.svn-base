package com.nse.pms.standard.basic.controller;

import com.nse.pms.standard.basic.service.NseEnterpriseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value="enterprise")
public class NseEnterpriseController {


	@Autowired
	private NseEnterpriseService enterpriseService;
	
	@RequestMapping( value="selectEnterpriseList", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<HashMap<String, Object>> selectEnterpriseList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return enterpriseService.selectEnterpriseList(param);
	}
	
	@RequestMapping( value="selectEnterpriseOne", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> selectEnterpriseOne(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return enterpriseService.selectEnterpriseOne(param);
	}
	
	@RequestMapping( value="insertEnterprise", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> insertEnterprise(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return enterpriseService.insertEnterprise(param);
	}

	@RequestMapping( value="updateEnterprise", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> updateEnterprise(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return enterpriseService.updateEnterprise(param);
	}
	
	@RequestMapping( value="deleteEnterprise", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> deleteEnterprise(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return enterpriseService.deleteEnterprise(param);
	}
	
	@RequestMapping( value="selectComEnterpriseList", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<HashMap<String, Object>> selectComEnterpriseList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return enterpriseService.selectComEnterpriseList(param);
	}
}