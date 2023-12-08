package com.nse.pms.standard.basic.controller;

import com.nse.config.Common;
import com.nse.pms.standard.basic.service.NseLineService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping(value="line")
public class NseLineController {

	@Autowired
	private NseLineService nseLineService;

	/**
	* Line 정보 조회
	* @param 
	* @param 
	* @return 
	**/
	@RequestMapping( value="selectLineList", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<HashMap<String, Object>> selectLineList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseLineService.selectLineList(param);
	}
	/**
	* Line 정보 단건 조회
	* @param 
	* @param 
	* @return 
	**/
	@RequestMapping( value="selectLineOne", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> selectLineOne(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseLineService.selectLineOne(param);
	}
	/**
	* Line 정보 등록
	* @param 
	* @param 
	* @return 
	**/
	@RequestMapping( value="insertLine", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> insertLine(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseLineService.insertLine(param);
	}
	/**
	* Line 정보 수정
	* @param 
	* @param 
	* @return 
	**/
	@RequestMapping( value="updateLine", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> updateLine(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseLineService.updateLine(param);
	}
	/**
	* Line 정보 삭제
	* @param 
	* @param 
	* @return 
	**/
	@RequestMapping( value="deleteLine", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> deleteLine(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseLineService.deleteLine(param);
	}
	/**
	* Line 정보 조회(공통)
	* @param 
	* @param 
	* @return 
	**/
	@RequestMapping( value="selectComLineList", method = {RequestMethod.GET, RequestMethod.POST})
	public List<HashMap<String, Object>> selectComLineList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseLineService.selectComLineList(param);
	}
	
	/**
	* Line 정보 조회(Api)
	* @param 
	* @param 
	* @return 
	**/
	@RequestMapping( value="selectApiLineList", method = {RequestMethod.GET, RequestMethod.POST})
	public List<HashMap<String, Object>> selectApiLineList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseLineService.selectApiLineList(param);
	}
	
}