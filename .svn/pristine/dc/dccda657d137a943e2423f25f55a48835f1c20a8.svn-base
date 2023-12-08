package com.nse.pms.standard.basic.controller;

import com.nse.config.Common;
import com.nse.pms.standard.basic.service.NseShiftService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping(value="shift")
public class NseShiftController {

	@Autowired
	private NseShiftService nseShiftService;

	/**
	* Shift 정보 조회
	* @param userID 검사 항목에 대한 구분자
	* @param role 사용자 권한정보 구분
	* @return 사용자명
	**/
	@RequestMapping( value="selectShiftList", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<HashMap<String, Object>> selectShiftList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseShiftService.selectShiftList(param);
	}
	
	@RequestMapping( value="selectShiftOne", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> selectShiftOne(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseShiftService.selectShiftOne(param);
	}
	
	@RequestMapping( value="insertShift", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> insertShift(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseShiftService.insertShift(param);
	}

	@RequestMapping( value="updateShift", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> updateShift(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseShiftService.updateShift(param);
	}
	
	@RequestMapping( value="deleteShift", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> deleteShift(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseShiftService.deleteShift(param);
	}

}