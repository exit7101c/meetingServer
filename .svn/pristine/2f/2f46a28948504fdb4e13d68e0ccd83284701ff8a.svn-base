package com.nse.pms.standard.basic.controller;

import com.nse.config.Common;
import com.nse.pms.standard.basic.service.NseSiteService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping(value="site")
public class NseSiteController {

	@Autowired
	private NseSiteService nseSiteService;

	/**
	* SITE 정보 조회
	* @param userID 검사 항목에 대한 구분자
	* @param role 사용자 권한정보 구분
	* @return 사용자명
	**/
	@RequestMapping( value="selectSiteList", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<HashMap<String, Object>> selectSiteList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseSiteService.selectSiteList(param);
	}
	
	@RequestMapping( value="selectSiteOne", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> selectSiteOne(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseSiteService.selectSiteOne(param);
	}
	
	@RequestMapping( value="insertSite", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> insertSite(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseSiteService.insertSite(param);
	}

	@RequestMapping( value="updateSite", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> updateSite(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseSiteService.updateSite(param);
	}
	
	@RequestMapping( value="deleteSite", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> deleteSite(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseSiteService.deleteSite(param);
	}

}