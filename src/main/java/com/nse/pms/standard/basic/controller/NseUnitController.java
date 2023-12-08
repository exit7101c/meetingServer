package com.nse.pms.standard.basic.controller;

import com.nse.pms.standard.basic.service.NseUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value="unit")
public class NseUnitController {
	
	@Autowired
	private NseUnitService nseUnitService;
	
	/**
	* 단위코드 정보 조회
	* @param 
	* @param 
	* @return 
	**/
	@RequestMapping( value="selectUnitList", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<HashMap<String, Object>> selectUnitList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseUnitService.selectUnitList(param);
	}
}
	