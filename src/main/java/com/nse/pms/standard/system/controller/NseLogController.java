package com.nse.pms.standard.system.controller;

import com.nse.pms.standard.system.service.NseLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "log")
public class NseLogController {

	@Autowired
	private NseLogService nseLogService;

	/**
	 * 리스트 조회 (메뉴활용도 조회)
	 **/
	@RequestMapping(value = "selectLogMenuList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> selectLogMenuList(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseLogService.selectLogMenuList(param);
	}

	/**
	 * 한건 조회 (메뉴활용도 조회)
	 */
	// @RequestMapping( value="selectLogMenuOne", method = {RequestMethod.GET,
	// RequestMethod.POST})
	// public HashMap<String, Object> selectLogMenuOne(@RequestParam HashMap<String,
	// Object> param, ModelMap model) throws Exception {
	// return nseLogService.selectLogMenuOne(param);
	// }
	//
	//
	// /**
	// * 한건을 등록한다. (메뉴활용도 조회)
	// */
	// @RequestMapping( value="insertLogMenu", method = {RequestMethod.GET,
	// RequestMethod.POST})
	// public HashMap<String, Object> insertLogMenu(@RequestParam HashMap<String,
	// Object> param, ModelMap model) throws Exception {
	// return nseLogService.insertLogMenu(param);
	// }
	//
	// /**
	// * 한건을 수정한다. (메뉴활용도 조회)
	// */
	// @RequestMapping( value="updateLogMenu", method = {RequestMethod.GET,
	// RequestMethod.POST})
	// public HashMap<String, Object> updateLogMenu(@RequestParam HashMap<String,
	// Object> param, ModelMap model) throws Exception {
	// return nseLogService.updateLogMenu(param);
	// }
	//
	// /**
	// * 한건을 사용하지 않는다. (메뉴활용도 조회)
	// */
	// @RequestMapping( value="disableLogMenu", method = {RequestMethod.GET,
	// RequestMethod.POST})
	// public HashMap<String, Object> disableLogMenu(@RequestParam HashMap<String,
	// Object> param, ModelMap model) throws Exception {
	// return nseLogService.disableLogMenu(param);
	// }

	@RequestMapping(value = "selectLogUserAccess", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> selectLogUserAccess(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseLogService.selectLogUserAccess(param);
	}

	@RequestMapping(value = "selectLogErrorHis", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<HashMap<String, Object>> selectLogErrorHis(@RequestParam HashMap<String, Object> param, ModelMap model)
			throws Exception {
		return nseLogService.selectLogErrorHis(param);
	}
}