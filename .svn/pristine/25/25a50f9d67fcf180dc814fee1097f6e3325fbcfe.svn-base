package com.nse.pms.standard.basic.controller;

import com.nse.pms.standard.basic.service.NseLineService;
import com.nse.pms.standard.basic.service.NseProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value="process")
public class NseProcessController {


	@Autowired
	private NseLineService nseLineService;
	
	@Autowired
	private NseProcessService nseProcessService;
	
	@RequestMapping( value="getAreaLineProcEqp", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<HashMap<String, Object>> selectApiAreaLineProcEqp(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseProcessService.selectApiAreaLineProcEqp(param);
	}
	
	/**
	* 공정코드 정보 조회
	* @param 
	* @param 
	* @return 
	**/
	@RequestMapping( value="selectProcessList", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<HashMap<String, Object>> selectProcessList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseProcessService.selectProcessList(param);
	}
	
	/**
	* 공정코드 정보 단건 조회
	* @param 
	* @param 
	* @return 
	**/
	@RequestMapping( value="selectProcessOne", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> selectProcessOne(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseProcessService.selectProcessOne(param);
	}
	
	/**
	* 공정코드 타입 정보 조회
	* @param 
	* @param 
	* @return 
	**/
	@RequestMapping( value="selectProcessTypeList", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<HashMap<String, Object>> selectProcessTypeList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseProcessService.selectProcessTypeList(param);
	}
	
	/**
	* 공정코드 정보 등록
	* @param 
	* @param 
	* @return 
	**/
	@RequestMapping( value="insertProcess", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> insertProcess(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseProcessService.insertProcess(param);
	}

	/**
	* 공정코드 정보 수정
	* @param 
	* @param 
	* @return 
	**/
	@RequestMapping( value="updateProcess", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> updateProcess(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseProcessService.updateProcess(param);
	}
	
	/**
	* 공정코드 정보 삭제
	* @param 
	* @param 
	* @return 
	**/
	@RequestMapping( value="deleteProcess", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> deleteProcess(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseProcessService.deleteProcess(param);
	}
	
	/**
	* LINE - PROCESS LIST
	* @param
	* @return 
	**/
	@RequestMapping( value="selectAreaLineProcList", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<HashMap<String, Object>> selectAreaLineProcList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseProcessService.selectAreaLineProcList(param);
	}

	
	/**
	* 공정-설비 Mapping
	* @param 
	* @return 
	**/
	@RequestMapping( value="updateProcEqpMapping", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> updateProcEqpMapping(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseProcessService.updateProcEqpMapping(param) ;
	}

	/**
	* 제품-상하한 Mapping
	* @param
	* @return
	**/
	@RequestMapping( value="updateProdSpecMapping", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> updateProdSpecMapping(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseProcessService.updateProdSpecMapping(param) ;
	}

	/**
	 * 공정-설비가동차트
	 * @param
	 * @return
	 **/
	@RequestMapping( value="selectEqpStateHisOnChart", method = {RequestMethod.GET, RequestMethod.POST})
	public HashMap<String, Object> selectEqpStateHisOnChart(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseProcessService.selectEqpStateHisOnChart(param) ;
	}

	/**
	* MES 공정코드 정보 조회
	* @param 
	* @param 
	* @return 
	**/
	@RequestMapping( value="selectMESProcessList", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<HashMap<String, Object>> selectMESProcessList(@RequestParam HashMap<String, Object> param, ModelMap model) throws Exception {
		return nseProcessService.selectMESProcessList(param);
	}
}