package com.nse.pms.standard.basic.controller;

import com.nse.pms.standard.basic.service.NseMtrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value="mtrl")
public class NseMtrlController {

	@Autowired
	private NseMtrlService nseMtrlService;

	/**
	* 자재그룹 (Mtrl Group) 정보 조회
	* @param 
	* @param 
	* @return 
	**/
	@RequestMapping( value="selectMtrlGroupList", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public HashMap<String, Object> selectMtrlGroupList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseMtrlService.selectMtrlGroupList(param);
	}

	/**
	 * 자재그룹 (Mtrl Group) 정보 조회
	 * @param
	 * @param
	 * @return
	 **/
	@RequestMapping( value="selectNonPageMtrlGroupList", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<HashMap<String, Object>> selectNonPageMtrlGroupList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseMtrlService.selectNonPageMtrlGroupList(param);
	}
	
	/**
	* 자재그룹 (Mtrl Group) 정보 단건 조회
	* @param 
	* @param 
	* @return 
	**/
	@RequestMapping( value="selectMtrlGroupOne", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> selectMtrlGroupOne(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseMtrlService.selectMtrlGroupOne(param);
	}
	
	/**
	* 자재그룹 (Mtrl Group) 정보 등록
	* @param 
	* @param 
	* @return 
	**/
	@RequestMapping( value="insertMtrlGroup", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> insertMtrlGroup(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseMtrlService.insertMtrlGroup(param);
	}

	/**
	* 자재그룹 (Mtrl Group) 정보 수정
	* @param 
	* @param 
	* @return 
	**/
	@RequestMapping( value="updateMtrlGroup", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> updateMtrlGroup(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseMtrlService.updateMtrlGroup(param);
	}
	
	/**
	* 자재그룹 (Mtrl Group) 정보 삭제
	* @param 
	* @param 
	* @return 
	**/
	@RequestMapping( value="deleteMtrlGroup", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> deleteMtrlGroup(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseMtrlService.deleteMtrlGroup(param);
	}
	
	/**
	* 자재유형 (Mtrl Type) 정보 조회
	* @param 
	* @param 
	* @return 
	**/
	@RequestMapping( value="selectMtrlTypeList", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<HashMap<String, Object>> selectMtrlTypeList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseMtrlService.selectMtrlTypeList(param);
	}
	
	/**
	* 자재유형 (Mtrl Type) 정보 단건 조회
	* @param 
	* @param 
	* @return 
	**/
	@RequestMapping( value="selectMtrlTypeOne", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> selectMtrlTypeOne(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseMtrlService.selectMtrlTypeOne(param);
	}
	
	/**
	* 자재유형 (Mtrl Type) 정보 등록
	* @param 
	* @param 
	* @return 
	**/
	@RequestMapping( value="insertMtrlType", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> insertMtrlType(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseMtrlService.insertMtrlType(param);
	}

	/**
	* 자재유형 (Mtrl Type) 정보 수정
	* @param 
	* @param 
	* @return 
	**/
	@RequestMapping( value="updateMtrlType", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> updateMtrlType(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseMtrlService.updateMtrlType(param);
	}
	
	/**
	* 자재유형 (Mtrl Type) 정보 삭제
	* @param 
	* @param 
	* @return 
	**/
	@RequestMapping( value="deleteMtrlType", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> deleteMtrlType(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseMtrlService.deleteMtrlType(param);
	}
}