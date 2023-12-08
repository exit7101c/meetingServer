package com.nse.pms.standard.basic.controller;

import com.nse.pms.standard.basic.service.NseProductGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value="productGroup")
public class NseProductGroupController {

	@Autowired
	private NseProductGroupService nseProductGroupService;

	/**
	* 제품군코드 정보 조회
	* @param 
	* @param 
	* @return 
	**/
	@RequestMapping( value="selectProductGroupList", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<HashMap<String, Object>> selectProductGroupList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseProductGroupService.selectProductGroupList(param);
	}
	
	/**
	* 제품군코드 정보 단건 조회
	* @param 
	* @param 
	* @return 
	**/
	@RequestMapping( value="selectProductGroupOne", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> selectProductGroupOne(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseProductGroupService.selectProductGroupOne(param);
	}
	
	/**
	* 제품군코드 정보 등록
	* @param 
	* @param 
	* @return 
	**/
	@RequestMapping( value="insertProductGroup", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> insertProductGroup(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseProductGroupService.insertProductGroup(param);
	}

	/**
	* 제품군코드 정보 수정
	* @param 
	* @param 
	* @return 
	**/
	@RequestMapping( value="updateProductGroup", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> updateProductGroup(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseProductGroupService.updateProductGroup(param);
	}
	
	/**
	* 제품군코드 정보 삭제
	* @param 
	* @param 
	* @return 
	**/
	@RequestMapping( value="deleteProductGroup", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> deleteProductGroup(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseProductGroupService.deleteProductGroup(param);
	}
}